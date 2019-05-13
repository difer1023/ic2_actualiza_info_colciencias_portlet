package co.com.ic2.actualizaInfo.portlet;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import co.com.ic2.colciencias.gruplac.ClasificacionGrupo;
import co.com.ic2.colciencias.utilidades.usuario.UsuarioUtil;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

public class PreferenciasRecomendacionPortlet extends GenericPortlet {

    public void init() {
        viewTemplate = getInitParameter("view-template");
    }

    public void processAction(
            ActionRequest actionRequest, ActionResponse actionResponse)
        throws IOException, PortletException {
    	ThemeDisplay themeDisplay=(ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
    	Boolean redirect=themeDisplay.getUser().getExpandoBridge().getAttribute("clasificacionObjetivo")==null || themeDisplay.getUser().getExpandoBridge().getAttribute("clasificacionObjetivo").equals("");
    	themeDisplay.getUser().getExpandoBridge().setAttribute("clasificacionObjetivo", ParamUtil.getString(actionRequest,"selectorCategoria"));
    	themeDisplay.getUser().getExpandoBridge().setAttribute("recomendacion","");
		try {
			UsuarioUtil.INSTANCE.asignarRol(PortalUtil.getUser(actionRequest).getCompanyId(),PortalUtil.getUser(actionRequest).getUserId(),"Recomendacion");
		} catch (PortalException | SystemException e) {
			LOG.error("Error asignando rol en preferenciasRecomendacion");
			e.printStackTrace();
		}
		if(redirect){
			actionResponse.sendRedirect("/group/user/recomendacion");
		}
    }

    public void doView(
            RenderRequest renderRequest, RenderResponse renderResponse)
        throws IOException, PortletException {
    	
    	HttpServletRequest request=PortalUtil.getHttpServletRequest(renderRequest);
    	String vista=(String)request.getAttribute("view");
    	
    	PortletSession portletSession = renderRequest.getPortletSession();
    	ClasificacionGrupo clasificacionGrupo=(ClasificacionGrupo) portletSession.getAttribute("clasificacionGrupoInvestigacion",PortletSession.APPLICATION_SCOPE);
    	
    	renderRequest.setAttribute("clasificacion", clasificacionGrupo.getClasificacionGrupo());
    	
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
            LOG.error(path + " is not a valid include");
        }
        else {
            portletRequestDispatcher.include(renderRequest, renderResponse);
        }
    }
 
    protected String viewTemplate;

    private static Log LOG = LogFactoryUtil.getLog(PreferenciasRecomendacionPortlet.class);

}
