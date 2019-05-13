package co.com.ic2.actualizaInfo.portlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletSession;
import javax.portlet.ProcessAction;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import co.com.ic2.colciencias.gruplac.ClasificacionGrupo;
import co.com.ic2.colciencias.gruplac.GrupoInvestigacion;
import co.com.ic2.colciencias.gruplac.ProductoInvestigacion;
import co.com.ic2.colciencias.service.scraper.ExtraerGrupoInvestigacionResponse;
import co.com.ic2.colciencias.utilidades.properties.ParametrosProperties;
import co.com.ic2.colciencias.utilidades.usuario.UsuarioUtil;
import co.com.ic2.facade.GrupoInvestigacionFacade;
import co.com.ic2.facade.ScraperColcienciasFacade;

import com.google.gson.Gson;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;

public class ActualizaInfoPortlet extends GenericPortlet {

	private static Log LOG = LogFactoryUtil.getLog(ActualizaInfoPortlet.class);
	
	public void init() {
		viewTemplate = getInitParameter("view-template");
	}

	@ProcessAction(name = "seleccionMetodo")
	public void seleccionMetodo(ActionRequest actionRequest,
			ActionResponse actionResponse) throws IOException, PortletException {
		HttpServletRequest request = PortalUtil
				.getHttpServletRequest(actionRequest);

		request.setAttribute("view",
					"/html/actualiza_info/privado/informacion_gruplac.jsp");
	}

	@ProcessAction(name = "obtenerInformacionPrivada")
	public void obtenerInformacionPrivada(ActionRequest actionRequest,
			ActionResponse actionResponse) throws IOException, PortletException {

		LOG.info(actionRequest.getParameter("nacionalidad"));
		LOG.info(actionRequest.getParameter("paisNacimiento"));
		LOG.info(actionRequest.getParameter("nombre"));
		LOG.info(actionRequest.getParameter("numeroDocumento"));
		LOG.info(actionRequest.getParameter("contrasena"));

		ParametrosProperties.getInstance().limpiarParametros();
		
		ScraperColcienciasFacade scraperPrivado = new ScraperColcienciasFacade();

		String tipoNacionalidad = actionRequest.getParameter("nacionalidad");
		String paisNacimiento = actionRequest.getParameter("paisNacimiento");
		String nombre = actionRequest.getParameter("nombre");
		String identificacion = actionRequest.getParameter("numeroDocumento");
		String contrasena = actionRequest.getParameter("contrasena");

		
		
		ExtraerGrupoInvestigacionResponse response= scraperPrivado
				.extraerGrupoInvestigacionPrivado(tipoNacionalidad, paisNacimiento,
						nombre, identificacion, contrasena,0,Integer
						.parseInt(ParametrosProperties.getInstance()
								.getPropiedadesPortal()
								.getProperty("anoFinVentanaObservacion")));
		HttpServletRequest request = PortalUtil
				.getHttpServletRequest(actionRequest);
		if(response.getRespuesta()==0){
			GrupoInvestigacion grupoInvestigacion=response.getGrupoInvestigacion();
			LOG.info(new Gson().toJson(grupoInvestigacion));

			try {
				almacenarDatosGrupo(grupoInvestigacion,PortalUtil.getUser(actionRequest),actionRequest);
				
				
				UsuarioUtil.INSTANCE.asignarRol(PortalUtil.getUser(actionRequest).getCompanyId(),PortalUtil.getUser(actionRequest).getUserId(),"SeleccionObjetivo");
				
				GrupoInvestigacionFacade grupoInvestigacionFacade = new GrupoInvestigacionFacade();
				JSONArray productosSinClasificacion=JSONFactoryUtil.createJSONArray(grupoInvestigacionFacade.consultarProductosSinClasificacion(Integer.parseInt((String)PortalUtil.getUser(actionRequest).getExpandoBridge().getAttribute("codigoGrupo"))));
				
				if(productosSinClasificacion.length()>0){
					
					request.setAttribute("categorias",grupoInvestigacionFacade.consultarTiposProductosInvestigacion());
					request.setAttribute("productosNoClasificados",productosSinClasificacion);
					request.setAttribute("view",
							"/html/actualiza_info/privado/clasificacion_productos.jsp");
				}else{
					int anoFinVentanaObservacion=Integer.parseInt(ParametrosProperties.getInstance().getPropiedadesPortal().getProperty("anoFinVentanaObservacion"));
					
					ClasificacionGrupo clasificacionGrupo=
							new GrupoInvestigacionFacade().consultarGruposInvestigacion(Integer.parseInt((String) PortalUtil.getUser(actionRequest).getExpandoBridge().getAttribute("codigoGrupo")),
									anoFinVentanaObservacion);
					request.getSession(false).setAttribute("clasificacionGrupoInvestigacion", clasificacionGrupo);
					
					request.setAttribute("view",
						"/html/actualiza_info/privado/confirmacion.jsp");
				}
				
				
			} catch (PortalException | SystemException e) {
				e.printStackTrace();
			}
			
			
		}else if (response.getRespuesta()==1){//En este caso,debes guardar los datos del login en sesion
			actionRequest.setAttribute("gruposInvestigacion", response.getGruposInvestigacion().getGrupoInvestigacion());
			
			actionRequest.getPortletSession().setAttribute("nacionalidad", tipoNacionalidad,PortletSession.PORTLET_SCOPE);
			actionRequest.getPortletSession().setAttribute("paisNacimiento", paisNacimiento,PortletSession.PORTLET_SCOPE);
			actionRequest.getPortletSession().setAttribute("nombre", nombre,PortletSession.PORTLET_SCOPE);
			actionRequest.getPortletSession().setAttribute("numeroDocumento", identificacion,PortletSession.PORTLET_SCOPE);
			actionRequest.getPortletSession().setAttribute("contrasena", contrasena,PortletSession.PORTLET_SCOPE);
			
			LOG.info("seleccion grupo "+response.getGruposInvestigacion().getGrupoInvestigacion());
			request.setAttribute("view",
					"/html/actualiza_info/privado/seleccionGrupo.jsp");
		}
		
	}
	
	@ProcessAction(name = "seleccionarGrupoInvestigacion")
	public void seleccionarGrupoInvestigacion(ActionRequest actionRequest,
			ActionResponse actionResponse) throws IOException, PortletException {
		
		HttpServletRequest request = PortalUtil
				.getHttpServletRequest(actionRequest);
		
		String grupo=actionRequest.getParameter("seleccionGrupo");
		
		ScraperColcienciasFacade scraperPrivado = new ScraperColcienciasFacade();
		
		String tipoNacionalidad=(String)actionRequest.getPortletSession().getAttribute("nacionalidad",PortletSession.PORTLET_SCOPE);
		String paisNacimiento=(String)actionRequest.getPortletSession().getAttribute("paisNacimiento",PortletSession.PORTLET_SCOPE);
		String nombre=(String)actionRequest.getPortletSession().getAttribute("nombre",PortletSession.PORTLET_SCOPE);
		String identificacion=(String)actionRequest.getPortletSession().getAttribute("numeroDocumento",PortletSession.PORTLET_SCOPE);
		String contrasena=(String)actionRequest.getPortletSession().getAttribute("contrasena",PortletSession.PORTLET_SCOPE);
		
		LOG.info(tipoNacionalidad);
		
		ExtraerGrupoInvestigacionResponse response= scraperPrivado
				.extraerGrupoInvestigacionPrivado(tipoNacionalidad, paisNacimiento,
						nombre, identificacion, contrasena,Integer.parseInt(grupo),Integer
						.parseInt(ParametrosProperties.getInstance()
								.getPropiedadesPortal()
								.getProperty("anoFinVentanaObservacion")));
		
		LOG.info("seleccion grupo "+response.getGrupoInvestigacion().getProductosInvestigacion().getProductoInvestigacion().size());
		
		try {
			
			almacenarDatosGrupo(response.getGrupoInvestigacion(),PortalUtil.getUser(actionRequest),actionRequest);
	        
			
			UsuarioUtil.INSTANCE.asignarRol(PortalUtil.getUser(actionRequest).getCompanyId(),PortalUtil.getUser(actionRequest).getUserId(),"SeleccionObjetivo");
			
			GrupoInvestigacionFacade grupoInvestigacionFacade = new GrupoInvestigacionFacade();
			JSONArray productosSinClasificacion=JSONFactoryUtil.createJSONArray(grupoInvestigacionFacade.consultarProductosSinClasificacion(Integer.parseInt((String)PortalUtil.getUser(actionRequest).getExpandoBridge().getAttribute("codigoGrupo"))));
			
			if(productosSinClasificacion.length()>0){
				
				request.setAttribute("categorias",grupoInvestigacionFacade.consultarTiposProductosInvestigacion());
				request.setAttribute("productosNoClasificados",productosSinClasificacion);
				request.setAttribute("view",
						"/html/actualiza_info/privado/clasificacion_productos.jsp");
			}else{
				int anoFinVentanaObservacion=Integer.parseInt(ParametrosProperties.getInstance().getPropiedadesPortal().getProperty("anoFinVentanaObservacion"));
				
				ClasificacionGrupo clasificacionGrupo=
						new GrupoInvestigacionFacade().consultarGruposInvestigacion(Integer.parseInt((String) PortalUtil.getUser(actionRequest).getExpandoBridge().getAttribute("codigoGrupo")),
								anoFinVentanaObservacion);
				request.getSession(false).setAttribute("clasificacionGrupoInvestigacion", clasificacionGrupo);
				request.setAttribute("view",
					"/html/actualiza_info/privado/confirmacion.jsp");
			}
		} catch (PortalException | SystemException e) {
			e.printStackTrace();
		}
	}

	@ProcessAction(name = "guardarClasificaciones")
	public void guardarClasificaciones(ActionRequest actionRequest,
			ActionResponse actionResponse) throws IOException, PortletException {
		
		HttpServletRequest request = PortalUtil
				.getHttpServletRequest(actionRequest);
		
		LOG.info("Guardando clasificaciones manuales "+ParamUtil.getString(actionRequest, "clasificaciones"));
		
		try {
			GrupoInvestigacionFacade grupoInvestigacionFacade = new GrupoInvestigacionFacade();
			JSONArray clasificacionesArray=JSONFactoryUtil.createJSONArray(ParamUtil.getString(actionRequest, "clasificaciones"));
			if(clasificacionesArray.length()>0){
				ArrayList<ProductoInvestigacion> clasificaciones=new ArrayList<ProductoInvestigacion>();
				for(int i=0;i<clasificacionesArray.length();i++){
					ProductoInvestigacion producto=new ProductoInvestigacion();
					producto.setCodigo(clasificacionesArray.getJSONObject(i).getInt("k_codigo"));
					producto.setCategoria(clasificacionesArray.getJSONObject(i).getString("c_categoria"));
					clasificaciones.add(producto);
				}
				grupoInvestigacionFacade.actualizarProductosClasificacionManual(clasificaciones);
			}
		
			int anoFinVentanaObservacion=Integer.parseInt(ParametrosProperties.getInstance().getPropiedadesPortal().getProperty("anoFinVentanaObservacion"));
			
			ClasificacionGrupo clasificacionGrupo=
	        		new GrupoInvestigacionFacade().consultarGruposInvestigacion(Integer.parseInt((String) PortalUtil.getUser(actionRequest).getExpandoBridge().getAttribute("codigoGrupo")),
	   			 anoFinVentanaObservacion);
	        request.getSession(false).setAttribute("clasificacionGrupoInvestigacion", clasificacionGrupo);
	        
			request.setAttribute("view",
					"/html/actualiza_info/privado/confirmacion.jsp");
			
			
		} catch (SystemException | PortalException e) {
			e.printStackTrace();
		}
	}
	
	public void doView(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {

		HttpServletRequest request = PortalUtil
				.getHttpServletRequest(renderRequest);
		String vista = (String) request.getAttribute("view");
		LOG.info("vista:"+ vista);
		if (vista != null) {
			include(vista, renderRequest, renderResponse);
		} else {
			include(viewTemplate, renderRequest, renderResponse);
		}
	}

	protected void include(String path, RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {

		PortletRequestDispatcher portletRequestDispatcher = getPortletContext()
				.getRequestDispatcher(path);

		if (portletRequestDispatcher == null) {
			LOG.error(path + " is not a valid include");
		} else {
			portletRequestDispatcher.include(renderRequest, renderResponse);
		}
	}

	protected String viewTemplate;
	
	public void almacenarDatosGrupo(GrupoInvestigacion grupoInvestigacion,User usuario,ActionRequest request) throws PortalException, SystemException{
		
		LOG.info(new Gson().toJson(grupoInvestigacion));
		
		GrupoInvestigacionFacade grupoInvestigacionFacade = new GrupoInvestigacionFacade();
		
		String codigoGrupo=(String) usuario.getExpandoBridge().getAttribute("codigoGrupo");
		
		if(codigoGrupo==null || codigoGrupo.equals("")){
			String codigoGenerado=String.valueOf(grupoInvestigacionFacade.insertarGrupoInvestigacion(grupoInvestigacion));
			if(codigoGenerado!=null){
				usuario.getExpandoBridge().setAttribute("codigoGrupo", codigoGenerado);
			}
		}else{
			LOG.info("Actualizando grupo de investigacion");
			grupoInvestigacionFacade.actualizarGrupoInvestigacion(Integer.parseInt(codigoGrupo),grupoInvestigacion);
			usuario.getExpandoBridge().setAttribute("recomendacion", "");
			usuario.getExpandoBridge().setAttribute("clasificacionObjetivo", "");
			UsuarioUtil.INSTANCE.retirarRol(PortalUtil.getUser(request).getCompanyId(),PortalUtil.getUser(request).getUserId(),"UsuarioGrupo");
			UsuarioUtil.INSTANCE.retirarRol(PortalUtil.getUser(request).getCompanyId(),PortalUtil.getUser(request).getUserId(),"Recomendacion");
		}
	}

}
