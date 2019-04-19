package co.com.ic2.actualizaInfo.portlet;

import java.io.IOException;

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

import co.com.ic2.colciencias.gruplac.GrupoInvestigacion;
import co.com.ic2.colciencias.service.scraper.ExtraerGrupoInvestigacionResponse;
import co.com.ic2.colciencias.utilidades.properties.ParametrosProperties;
import co.com.ic2.facade.GrupoInvestigacionFacade;
import co.com.ic2.facade.ScraperColcienciasFacade;

import com.google.gson.Gson;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;

public class ActualizaInfoPortlet extends GenericPortlet {

	private static Log _log = LogFactoryUtil.getLog(ActualizaInfoPortlet.class);
	
	public void init() {
		viewTemplate = getInitParameter("view-template");
	}

	@ProcessAction(name = "seleccionMetodo")
	public void seleccionMetodo(ActionRequest actionRequest,
			ActionResponse actionResponse) throws IOException, PortletException {
		System.out.println("processAction");
		HttpServletRequest request = PortalUtil
				.getHttpServletRequest(actionRequest);

		request.setAttribute("view",
					"/html/actualiza_info/privado/informacion_gruplac.jsp");
	}

	@ProcessAction(name = "obtenerInformacionPrivada")
	public void obtenerInformacionPrivada(ActionRequest actionRequest,
			ActionResponse actionResponse) throws IOException, PortletException {

		_log.info(actionRequest.getParameter("nacionalidad"));
		_log.info(actionRequest.getParameter("paisNacimiento"));
		_log.info(actionRequest.getParameter("nombre"));
		_log.info(actionRequest.getParameter("numeroDocumento"));
		_log.info(actionRequest.getParameter("contrasena"));

		ParametrosProperties.getInstance().limpiarParametros();
		
		ScraperColcienciasFacade scraperPrivado = new ScraperColcienciasFacade();

		String tipoNacionalidad = actionRequest.getParameter("nacionalidad");
		String paisNacimiento = actionRequest.getParameter("paisNacimiento");
		String nombre = actionRequest.getParameter("nombre");
		String identificacion = actionRequest.getParameter("numeroDocumento");
		String contrasena = actionRequest.getParameter("contrasena");

		
		
		ExtraerGrupoInvestigacionResponse response= scraperPrivado
				.extraerGrupoInvestigacionPrivado(tipoNacionalidad, paisNacimiento,
						nombre, identificacion, contrasena,0,2016);
		HttpServletRequest request = PortalUtil
				.getHttpServletRequest(actionRequest);
		if(response.getRespuesta()==0){
			GrupoInvestigacion grupoInvestigacion=response.getGrupoInvestigacion();
			System.out.println(new Gson().toJson(grupoInvestigacion));

			try {
				almacenarDatosGrupo(grupoInvestigacion,PortalUtil.getUser(actionRequest));
			} catch (PortalException | SystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			request.setAttribute("view",
					"/html/actualiza_info/privado/confirmacion.jsp");
			
		}else if (response.getRespuesta()==1){//En este caso,debes guardar los datos del login en sesion
			actionRequest.setAttribute("gruposInvestigacion", response.getGruposInvestigacion().getGrupoInvestigacion());
			
			actionRequest.getPortletSession().setAttribute("nacionalidad", tipoNacionalidad,PortletSession.PORTLET_SCOPE);
			actionRequest.getPortletSession().setAttribute("paisNacimiento", paisNacimiento,PortletSession.PORTLET_SCOPE);
			actionRequest.getPortletSession().setAttribute("nombre", nombre,PortletSession.PORTLET_SCOPE);
			actionRequest.getPortletSession().setAttribute("numeroDocumento", identificacion,PortletSession.PORTLET_SCOPE);
			actionRequest.getPortletSession().setAttribute("contrasena", contrasena,PortletSession.PORTLET_SCOPE);
			
			System.out.println("seleccion grupo "+response.getGruposInvestigacion().getGrupoInvestigacion());
			request.setAttribute("view",
					"/html/actualiza_info/privado/seleccionGrupo.jsp");
		}
		
	}

	@ProcessAction(name = "obtenerInformacionPublica")
	public void obtenerInformacionPublica(ActionRequest actionRequest,
			ActionResponse actionResponse) throws IOException, PortletException {

//		ParametrosProperties
//		.getInstance().limpiarParametros();
		
		
		System.out.println(ParametrosProperties.getInstance().getPropiedadesPortal().getProperty("servicio1"));

//		System.out.println(actionRequest.getParameter("enlaceGrupo"));
//
//		ScraperColcienciasPublicoFacade scraperPublico = new ScraperColcienciasPublicoFacade();
//
//		String urlGruplac = actionRequest.getParameter("enlaceGrupo");
//
//		GrupoInvestigacion grupoInvestigacion = scraperPublico
//				.extraerGrupoInvestigacion(urlGruplac, true, true, true, true);
//
//		System.out.println(new Gson().toJson(grupoInvestigacion));

		HttpServletRequest request = PortalUtil
				.getHttpServletRequest(actionRequest);
		request.setAttribute("view",
				"/html/actualiza_info/publico/confirmacion.jsp");

	}
	
	@ProcessAction(name = "seleccionarGrupoInvestigacion")
	public void seleccionarGrupoInvestigacion(ActionRequest actionRequest,
			ActionResponse actionResponse) throws IOException, PortletException {
		
		HttpServletRequest request = PortalUtil
				.getHttpServletRequest(actionRequest);
		System.out.println(request.getParameter("seleccionGrupo"));
		
		String grupo=actionRequest.getParameter("seleccionGrupo");
		
		ScraperColcienciasFacade scraperPrivado = new ScraperColcienciasFacade();
		
		
		//Usar para obtener los atributos de sesion guardados anteriormente
		//String atributo=(String)actionRequest.getPortletSession().getAttribute("nombreAtributo",PortletSession.PORTLET_SCOPE);
		
		String tipoNacionalidad=(String)actionRequest.getPortletSession().getAttribute("nacionalidad",PortletSession.PORTLET_SCOPE);
		String paisNacimiento=(String)actionRequest.getPortletSession().getAttribute("paisNacimiento",PortletSession.PORTLET_SCOPE);
		String nombre=(String)actionRequest.getPortletSession().getAttribute("nombre",PortletSession.PORTLET_SCOPE);
		String identificacion=(String)actionRequest.getPortletSession().getAttribute("numeroDocumento",PortletSession.PORTLET_SCOPE);
		String contrasena=(String)actionRequest.getPortletSession().getAttribute("contrasena",PortletSession.PORTLET_SCOPE);
		
		System.out.println(tipoNacionalidad);
		
		ExtraerGrupoInvestigacionResponse response= scraperPrivado
				.extraerGrupoInvestigacionPrivado(tipoNacionalidad, paisNacimiento,
						nombre, identificacion, contrasena,Integer.parseInt(grupo),2016);
		
		System.out.println("seleccion grupo "+response.getGrupoInvestigacion().getProductosInvestigacion().getProductoInvestigacion().size());
		
		try {
			almacenarDatosGrupo(response.getGrupoInvestigacion(),PortalUtil.getUser(actionRequest));
			
//			Usuario usuario=new Usuario();
//	        usuario.setCodigoGrupo(Integer.parseInt((String)PortalUtil.getUser(actionRequest).getExpandoBridge().getAttribute("codigoGrupo")));
	        
//	        GrupoInvestigacionFacade facade=new GrupoInvestigacionFacade();
//	        
//	        int anoFinVentanaObservacion=Integer.parseInt(ParametrosProperties.getInstance().getPropiedadesPortal().getProperty("anoFinVentanaObservacion"));
	        
//	        usuario.setClasificacionGrupo(
//	        		facade.consultarGruposInvestigacion(usuario.getCodigoGrupo(),
//	        				anoFinVentanaObservacion));
//	        usuario.setRecomendacion((String)PortalUtil.getUser(actionRequest).getExpandoBridge().getAttribute("recomendacion"));
	        
	        
//	        PortletSession portletSession = actionRequest.getPortletSession();
//	        portletSession.setAttribute("usuario",usuario,PortletSession.APPLICATION_SCOPE);
			
		} catch (PortalException | SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.setAttribute("view",
				"/html/actualiza_info/privado/confirmacion.jsp");
	}

	public void doView(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {

		HttpServletRequest request = PortalUtil
				.getHttpServletRequest(renderRequest);
		String vista = (String) request.getAttribute("view");
		_log.info("vista:"+ vista);
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
			_log.error(path + " is not a valid include");
		} else {
			portletRequestDispatcher.include(renderRequest, renderResponse);
		}
	}

	protected String viewTemplate;
	
	public void almacenarDatosGrupo(GrupoInvestigacion grupoInvestigacion,User usuario){
		
		_log.info(new Gson().toJson(grupoInvestigacion));
		
		GrupoInvestigacionFacade grupoInvestigacionFacade = new GrupoInvestigacionFacade();
		
		String codigoGrupo=String.valueOf(grupoInvestigacionFacade.insertarGrupoInvestigacion(grupoInvestigacion));
		if(codigoGrupo!=null){
			usuario.getExpandoBridge().setAttribute("codigoGrupo", codigoGrupo);
		}
	}

}
