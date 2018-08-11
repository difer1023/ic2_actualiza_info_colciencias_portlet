package co.com.ic2.actualizaInfo.portlet;

import java.io.IOException;
import java.rmi.RemoteException;

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
import co.com.ic2.colciencias.scrapper.privado.ExtraerGrupoInvestigacionObject;
import co.com.ic2.colciencias.utilidades.properties.ParametrosProperties;
import co.com.ic2.facade.ApoyoProgramaFormacionFacade;
import co.com.ic2.facade.ArticuloInvestigacionFacade;
import co.com.ic2.facade.AsesoriaProgramaOndasFacade;
import co.com.ic2.facade.BoletinFacade;
import co.com.ic2.facade.CapituloLibroInvestigacionFacade;
import co.com.ic2.facade.ConsultoriaFacade;
import co.com.ic2.facade.DisenoIndustrialFacade;
import co.com.ic2.facade.DocumentoTrabajoFacade;
import co.com.ic2.facade.EdicionFacade;
import co.com.ic2.facade.EmpresaBaseTecnologicaFacade;
import co.com.ic2.facade.EspacioParticipacionCiudadanaCTIFacade;
import co.com.ic2.facade.EsquemaCircuitoFacade;
import co.com.ic2.facade.EstrategiaComunicacionConocimientoFacade;
import co.com.ic2.facade.EstrategiaPedagogicaFomentoCTIFacade;
import co.com.ic2.facade.EventoCientificoFacade;
import co.com.ic2.facade.GeneracionContenidoFacade;
import co.com.ic2.facade.GrupoInvestigacionFacade;
import co.com.ic2.facade.InformeFinalInvestigacionFacade;
import co.com.ic2.facade.InnovacionProcedimientoServicioFacade;
import co.com.ic2.facade.InvestigadorFacade;
import co.com.ic2.facade.LibroInvestigacionFacade;
import co.com.ic2.facade.ParticipacionCiudadanaProyectoCTIFacade;
import co.com.ic2.facade.PatenteFacade;
import co.com.ic2.facade.PlantaPilotoFacade;
import co.com.ic2.facade.PrototipoIndustrialFacade;
import co.com.ic2.facade.ProyectoExtensionCTIFacade;
import co.com.ic2.facade.ProyectoFacade;
import co.com.ic2.facade.RedConocimientoFacade;
import co.com.ic2.facade.ScraperColcienciasPrivadoFacade;
import co.com.ic2.facade.SecretoEmpresarialFacade;
import co.com.ic2.facade.SignoDistintivoFacade;
import co.com.ic2.facade.SoftwareFacade;
import co.com.ic2.facade.TrabajoGradoFacade;

import com.google.gson.Gson;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
		// super.processAction(actionRequest, actionResponse);
		HttpServletRequest request = PortalUtil
				.getHttpServletRequest(actionRequest);
		System.out.println(request.getParameter("tipoExtraccion"));

		String tipoExtraccion = request.getParameter("tipoExtraccion");
		if (tipoExtraccion.equalsIgnoreCase("automatico")) {
			request.setAttribute("view",
					"/html/actualiza_info/privado/informacion_gruplac.jsp");
		}
		if (tipoExtraccion.equalsIgnoreCase("manual")) {
			request.setAttribute("view",
					"/html/actualiza_info/publico/informacion_gruplac.jsp");
		}
		// request.setAttribute("view",
		// "/html/actualiza_info/privado/informacion_gruplac.jsp");
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
		
		ScraperColcienciasPrivadoFacade scraperPrivado = new ScraperColcienciasPrivadoFacade();

		String tipoNacionalidad = actionRequest.getParameter("nacionalidad");
		String paisNacimiento = actionRequest.getParameter("paisNacimiento");
		String nombre = actionRequest.getParameter("nombre");
		String identificacion = actionRequest.getParameter("numeroDocumento");
		String contrasena = actionRequest.getParameter("contrasena");

		
		
		ExtraerGrupoInvestigacionObject response= scraperPrivado
				.extraerGrupoInvestigacion(tipoNacionalidad, paisNacimiento,
						nombre, identificacion, contrasena,0,2016);
		HttpServletRequest request = PortalUtil
				.getHttpServletRequest(actionRequest);
		if(response.getRespuesta()==0){
			GrupoInvestigacion grupoInvestigacion=response.getGrupoInvestigacion();
			System.out.println(new Gson().toJson(grupoInvestigacion));

			almacenarDatosGrupo(grupoInvestigacion);
			
			request.setAttribute("view",
					"/html/actualiza_info/privado/confirmacion.jsp");
			
		}else if (response.getRespuesta()==1){//En este caso,debes guardar los datos del login en sesion
			actionRequest.setAttribute("gruposInvestigacion", response.getGruposInvestigacion());
			
			actionRequest.getPortletSession().setAttribute("nacionalidad", tipoNacionalidad,PortletSession.PORTLET_SCOPE);
			actionRequest.getPortletSession().setAttribute("paisNacimiento", paisNacimiento,PortletSession.PORTLET_SCOPE);
			actionRequest.getPortletSession().setAttribute("nombre", nombre,PortletSession.PORTLET_SCOPE);
			actionRequest.getPortletSession().setAttribute("numeroDocumento", identificacion,PortletSession.PORTLET_SCOPE);
			actionRequest.getPortletSession().setAttribute("contrasena", contrasena,PortletSession.PORTLET_SCOPE);
			
			System.out.println("seleccion grupo "+response.getGruposInvestigacion());
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
		
		ScraperColcienciasPrivadoFacade scraperPrivado = new ScraperColcienciasPrivadoFacade();
		
		
		//Usar para obtener los atributos de sesion guardados anteriormente
		//String atributo=(String)actionRequest.getPortletSession().getAttribute("nombreAtributo",PortletSession.PORTLET_SCOPE);
		
		String tipoNacionalidad=(String)actionRequest.getPortletSession().getAttribute("nacionalidad",PortletSession.PORTLET_SCOPE);
		String paisNacimiento=(String)actionRequest.getPortletSession().getAttribute("paisNacimiento",PortletSession.PORTLET_SCOPE);
		String nombre=(String)actionRequest.getPortletSession().getAttribute("nombre",PortletSession.PORTLET_SCOPE);
		String identificacion=(String)actionRequest.getPortletSession().getAttribute("numeroDocumento",PortletSession.PORTLET_SCOPE);
		String contrasena=(String)actionRequest.getPortletSession().getAttribute("contrasena",PortletSession.PORTLET_SCOPE);
		
		System.out.println(tipoNacionalidad);
		
		ExtraerGrupoInvestigacionObject response= scraperPrivado
				.extraerGrupoInvestigacion(tipoNacionalidad, paisNacimiento,
						nombre, identificacion, contrasena,Integer.parseInt(grupo),2016);
		
		System.out.println("seleccion grupo "+response.getGrupoInvestigacion().getArticulosInvestigacion().size());
		
		almacenarDatosGrupo(response.getGrupoInvestigacion());
		
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
	
	public void almacenarDatosGrupo(GrupoInvestigacion grupoInvestigacion){
		
		_log.info(new Gson().toJson(grupoInvestigacion));
		
		GrupoInvestigacionFacade grupoInvestigacionFacade = new GrupoInvestigacionFacade();
		InvestigadorFacade investigadorFacade = new InvestigadorFacade();
		ApoyoProgramaFormacionFacade apoyoProgramaFormacionFacade = new ApoyoProgramaFormacionFacade();
		ArticuloInvestigacionFacade articuloInvestigacionFacade = new ArticuloInvestigacionFacade();
		AsesoriaProgramaOndasFacade asesoriaProgramaOndasFacade = new AsesoriaProgramaOndasFacade();
		BoletinFacade boletinFacade = new BoletinFacade();
		CapituloLibroInvestigacionFacade capituloLibroFacade = new CapituloLibroInvestigacionFacade();
		ConsultoriaFacade consultoriaFacade = new ConsultoriaFacade();
		DisenoIndustrialFacade disenoIndustrialFacade = new DisenoIndustrialFacade();
		DocumentoTrabajoFacade documentoTrabajoFacade = new DocumentoTrabajoFacade();
		EdicionFacade edicionFacade = new EdicionFacade();
		EmpresaBaseTecnologicaFacade empresaBaseTecnologicaFacade = new EmpresaBaseTecnologicaFacade();
		EspacioParticipacionCiudadanaCTIFacade espacioParticipacionCiudadanaFacade = new EspacioParticipacionCiudadanaCTIFacade();
		EsquemaCircuitoFacade esquemaCircuitoFacade = new EsquemaCircuitoFacade();
		EstrategiaComunicacionConocimientoFacade estrategiaComunicacionFacade = new EstrategiaComunicacionConocimientoFacade();
		EstrategiaPedagogicaFomentoCTIFacade estrategiaPedagogicaFacade = new EstrategiaPedagogicaFomentoCTIFacade();
		EventoCientificoFacade eventoCientificoFacade = new EventoCientificoFacade();
		GeneracionContenidoFacade generacionContenidoFacade = new GeneracionContenidoFacade();
		InformeFinalInvestigacionFacade informeFinalFacade = new InformeFinalInvestigacionFacade();
		InnovacionProcedimientoServicioFacade innovacionFacade = new InnovacionProcedimientoServicioFacade();
		LibroInvestigacionFacade libroFacade = new LibroInvestigacionFacade();
		ParticipacionCiudadanaProyectoCTIFacade participacionCiudadanaProyectoCTIFacade = new ParticipacionCiudadanaProyectoCTIFacade();
		PatenteFacade patenteFacade = new PatenteFacade();
		PlantaPilotoFacade plantaPilotoFacade = new PlantaPilotoFacade();
		PrototipoIndustrialFacade prototipoIndustrialFacade = new PrototipoIndustrialFacade();
		ProyectoExtensionCTIFacade proyectoExtensionFacade = new ProyectoExtensionCTIFacade();
		ProyectoFacade proyectoFacade = new ProyectoFacade();
		RedConocimientoFacade redConocimientoFacade = new RedConocimientoFacade();
		SecretoEmpresarialFacade secretoEmpresarialFacade = new SecretoEmpresarialFacade();
		SignoDistintivoFacade signoDistintivoFacade = new SignoDistintivoFacade();
		SoftwareFacade softwareFacade = new SoftwareFacade();
		TrabajoGradoFacade trabajoGradoFacade = new TrabajoGradoFacade();
		
		String codigoGrupo=String.valueOf(grupoInvestigacionFacade.insertarGrupoInvestigacion(grupoInvestigacion.getNombre()));
		
		investigadorFacade.insertarInvestigadores(codigoGrupo, grupoInvestigacion.getIntegrantes());
		
		try {
			if(!grupoInvestigacion.getApoyoProgramaFormacion().isEmpty()){apoyoProgramaFormacionFacade.insertarApoyosProgramaFormacion(codigoGrupo, grupoInvestigacion.getApoyoProgramaFormacion());}
			if(!grupoInvestigacion.getArticulosInvestigacion().isEmpty()){articuloInvestigacionFacade.insertarArticulosInvestigacion(codigoGrupo, grupoInvestigacion.getArticulosInvestigacion());}
			if(!grupoInvestigacion.getAsesoriaProgramaOndas().isEmpty()){asesoriaProgramaOndasFacade.insertarAsesoriasProgramaOndas(codigoGrupo, grupoInvestigacion.getAsesoriaProgramaOndas());}
			if(!grupoInvestigacion.getCapituloDeLibro().isEmpty()){capituloLibroFacade.insertarCapitulosLibroInvestigacion(codigoGrupo, grupoInvestigacion.getCapituloDeLibro());}
			if(!grupoInvestigacion.getConsultoria().isEmpty()){consultoriaFacade.insertarConsultorias(codigoGrupo, grupoInvestigacion.getConsultoria());}
			if(!grupoInvestigacion.getDisenoIndustrial().isEmpty()){disenoIndustrialFacade.insertarDisenosIndustriales(codigoGrupo, grupoInvestigacion.getDisenoIndustrial());}
			if(!grupoInvestigacion.getDocumentoTrabajo().isEmpty()){documentoTrabajoFacade.insertarDocumentosTrabajo(codigoGrupo, grupoInvestigacion.getDocumentoTrabajo());}
			if(!grupoInvestigacion.getEdicion().isEmpty()){edicionFacade.insertarEdiciones(codigoGrupo, grupoInvestigacion.getEdicion());}
			if(!grupoInvestigacion.getEmpresaBaseTecnologica().isEmpty()){empresaBaseTecnologicaFacade.insertarEmpresasBaseTecnologica(codigoGrupo, grupoInvestigacion.getEmpresaBaseTecnologica());}
			if(!grupoInvestigacion.getEspacioParticipacionCiudadana().isEmpty()){espacioParticipacionCiudadanaFacade.insertarParticipacionesCiudadanasProyectosCTI(codigoGrupo, grupoInvestigacion.getEspacioParticipacionCiudadana());}
			if(!grupoInvestigacion.getEsquemaCircuito().isEmpty()){esquemaCircuitoFacade.insertarEsquemasCircuito(codigoGrupo, grupoInvestigacion.getEsquemaCircuito());}
			if(!grupoInvestigacion.getEstrategiaComunicacionConocimiento().isEmpty()){estrategiaComunicacionFacade.insertarEstrategiasComunicacionConocimiento(codigoGrupo, grupoInvestigacion.getEstrategiaComunicacionConocimiento());}
			if(!grupoInvestigacion.getEstrategiaPedagogicaFomentoCTI().isEmpty()){estrategiaPedagogicaFacade.insertarEstrategiasPedagogicasFomentoCTI(codigoGrupo,grupoInvestigacion.getEstrategiaPedagogicaFomentoCTI());}
			if(!grupoInvestigacion.getEventoCientifico().isEmpty()){eventoCientificoFacade.insertarEventosCientificos(codigoGrupo, grupoInvestigacion.getEventoCientifico());}
			if(!grupoInvestigacion.getGeneracionContenidoMultimedia().isEmpty()){generacionContenidoFacade.insertarGeneracionContenidosMultimedia(codigoGrupo, grupoInvestigacion.getGeneracionContenidoMultimedia());}
			if(!grupoInvestigacion.getGeneracionContenidoVirtual().isEmpty()){generacionContenidoFacade.insertarGeneracionContenidosVirtuales(codigoGrupo, grupoInvestigacion.getGeneracionContenidoVirtual());}
			if(!grupoInvestigacion.getGeneracionContenidoImpreso().isEmpty()){generacionContenidoFacade.insertarGeneracionContenidosImpresos(codigoGrupo, grupoInvestigacion.getGeneracionContenidoImpreso());}
			if(!grupoInvestigacion.getInformeInvestigacion().isEmpty()){informeFinalFacade.insertarInformesFinalesInvestigacion(codigoGrupo, grupoInvestigacion.getInformeInvestigacion());}
			if(!grupoInvestigacion.getInnovacionProceso().isEmpty()){innovacionFacade.insertarInnovacionesProcedimientosServicios(codigoGrupo, grupoInvestigacion.getInnovacionProceso());}
			if(!grupoInvestigacion.getLibrosResultadoInvestigacion().isEmpty()){libroFacade.insertarLibrosInvestigacion(codigoGrupo, grupoInvestigacion.getLibrosResultadoInvestigacion());}
			if(!grupoInvestigacion.getParticipacionCiudadanaProyectoCTI().isEmpty()){participacionCiudadanaProyectoCTIFacade.insertarParticipacionesCiudadanasProyectosCTI(codigoGrupo, grupoInvestigacion.getParticipacionCiudadanaProyectoCTI());}
			if(!grupoInvestigacion.getPlantaPiloto().isEmpty()){plantaPilotoFacade.insertarPlantasPilotos(codigoGrupo, grupoInvestigacion.getPlantaPiloto());}
			if(!grupoInvestigacion.getPrototipo().isEmpty()){prototipoIndustrialFacade.insertarPrototiposIndustriales(codigoGrupo, grupoInvestigacion.getPrototipo());}
			//if(!grupoInvestigacion.getApoyoProgramaFormacion().isEmpty()){proyectoExtensionFacade.insertarProyectosExtensionCTI(codigoGrupo, grupoInvestigacion.getp)}
			if(!grupoInvestigacion.getProyecto().isEmpty()){proyectoFacade.insertarProyectos(codigoGrupo, grupoInvestigacion.getProyecto());}
			if(!grupoInvestigacion.getRedConocimiento().isEmpty()){redConocimientoFacade.insertarRedesConocimiento(codigoGrupo, grupoInvestigacion.getRedConocimiento());}
			if(!grupoInvestigacion.getSignoDistintivo().isEmpty()){signoDistintivoFacade.insertarSignoDistintivos(codigoGrupo, grupoInvestigacion.getSignoDistintivo());}
			if(!grupoInvestigacion.getSoftware().isEmpty()){softwareFacade.insertarSoftwares(codigoGrupo, grupoInvestigacion.getSoftware());}
			if(!grupoInvestigacion.getTrabajoDirigido().isEmpty()){trabajoGradoFacade.insertarTrabajosGrado(codigoGrupo, grupoInvestigacion.getTrabajoDirigido());}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
