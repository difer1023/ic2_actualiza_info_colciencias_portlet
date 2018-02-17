package co.com.ic2.actualizaInfo.portlet;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.ProcessAction;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.util.PortalUtil;

public class ActualizaInfoPortlet extends GenericPortlet {

    public void init() {
        viewTemplate = getInitParameter("view-template");
    }

    @ProcessAction(name="seleccionMetodo")
    public void seleccionMetodo(
            ActionRequest actionRequest, ActionResponse actionResponse)
        throws IOException, PortletException {
    	System.out.println("processAction");
//        super.processAction(actionRequest, actionResponse);
    	HttpServletRequest request=PortalUtil.getHttpServletRequest(actionRequest);
    	System.out.println(request.getParameter("tipoExtraccion"));
    	
    	String tipoExtraccion=request.getParameter("tipoExtraccion");
    	if(tipoExtraccion.equalsIgnoreCase("automatico")){
    		request.setAttribute("view", "/html/actualiza_info/privado/informacion_gruplac.jsp");
    	}
    	if(tipoExtraccion.equalsIgnoreCase("manual")){
    		request.setAttribute("view", "/html/actualiza_info/publico/informacion_gruplac.jsp");
    	}
//    	request.setAttribute("view", "/html/actualiza_info/privado/informacion_gruplac.jsp");
    }
    
    @ProcessAction(name="obtenerInformacionPrivada")
    public void obtenerInformacionPrivada(
            ActionRequest actionRequest, ActionResponse actionResponse)
        throws IOException, PortletException {
    	
    	HttpServletRequest request=PortalUtil.getHttpServletRequest(actionRequest);
    	request.setAttribute("view", "/html/actualiza_info/privado/confirmacion.jsp");
    	
    }
    
    @ProcessAction(name="obtenerInformacionPublica")
    public void obtenerInformacionPublica(
            ActionRequest actionRequest, ActionResponse actionResponse)
        throws IOException, PortletException {
    	
    	HttpServletRequest request=PortalUtil.getHttpServletRequest(actionRequest);
    	request.setAttribute("view", "/html/actualiza_info/publico/confirmacion.jsp");
    	
    }

    public void doView(
            RenderRequest renderRequest, RenderResponse renderResponse)
        throws IOException, PortletException {
    	
    	HttpServletRequest request=PortalUtil.getHttpServletRequest(renderRequest);
    	String vista=(String)request.getAttribute("view");
    	
    	if (vista!=null){
    		include(vista, renderRequest, renderResponse);
    	}else{
    		include(viewTemplate, renderRequest, renderResponse);
    	}
    }

    protected void include(
            String path, RenderRequest renderRequest,
            RenderResponse renderResponse)
        throws IOException, PortletException {

        PortletRequestDispatcher portletRequestDispatcher =
            getPortletContext().getRequestDispatcher(path);

        if (portletRequestDispatcher == null) {
            _log.error(path + " is not a valid include");
        }
        else {
            portletRequestDispatcher.include(renderRequest, renderResponse);
        }
    }
 
    protected String viewTemplate;

    private static Log _log = LogFactoryUtil.getLog(ActualizaInfoPortlet.class);

}
