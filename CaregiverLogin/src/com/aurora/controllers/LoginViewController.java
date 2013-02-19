package com.aurora.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import org.aurora.portalSSO.util.SSOManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;

import com.aurora.hibernate.login.beans.User;
import com.aurora.hibernate.login.dao.LoginDAO;
import com.aurora.org.caregiverlogin.forms.LoginForm;
import com.ibm.portal.portlet.service.PortletServiceHome;
import com.ibm.portal.portlet.service.PortletServiceUnavailableException;
import com.ibm.portal.portlet.service.login.LoginHome;
import com.ibm.portal.portlet.service.login.LoginService;




@Controller
@RequestMapping("VIEW")
public class LoginViewController {
	@Autowired
	private LoginDAO loginDAO;
	
	public static String ENV_USERPASSWORD = "com.aurora.seedlist.userpassword";
	public static String PREF_WCM_PATH = "wcm.path";
	public static String PREF_WCM_COMPONENT ="wcm.menuComponent";
	public static String PRED_WCM_LIB = "wcm.library";
	public static String PREF_COOKIE_ENV = "cookie.env";
	public static String PARAM_BAD_SESSION = "SESSIONTIMEOUT";
	public boolean BAD_LOGIN = false;
	public boolean BAD_SESSION = false;
	
	LoginHome loginHome;
	@PostConstruct
	public void init(){
		try {
			InitialContext ctx = new InitialContext();
			PortletServiceHome psh = (PortletServiceHome)ctx.lookup(LoginHome.JNDI_NAME);
			loginHome = (LoginHome)psh.getPortletService(LoginHome.class);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PortletServiceUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	@RequestMapping
	public ModelAndView defaultView (RenderRequest request, RenderResponse responses, @SuppressWarnings("rawtypes") Map model,@ModelAttribute("loginForm")LoginForm form) throws UnsupportedEncodingException{
		
		HttpServletRequest hsreq= com.ibm.ws.portletcontainer.portlet.PortletUtils.getHttpServletRequest(request);
		if (null!=hsreq.getParameter(PARAM_BAD_SESSION) && !BAD_LOGIN){
			BAD_SESSION=true;
		}else{
			BAD_SESSION=false;
		}
		if (form==null)form = new LoginForm();
		form.setBadLogin(BAD_LOGIN);
		form.setBadSession(BAD_SESSION);
		return new ModelAndView("loginView","loginForm",form);
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ActionMapping("doLogin")
	public void doLogin(ActionRequest request, ActionResponse response,@ModelAttribute("loginForm")LoginForm loginForm) throws IOException{
		LoginService loginService = loginHome.getLoginService(request, response);
		Map contextMap = new HashMap();
        contextMap.put(LoginService.DO_RESUME_SESSION_KEY, new Boolean(false));
        
		BAD_LOGIN=false;
		try {
			loginService.login(loginForm.getUserName(), loginForm.getPassword().toCharArray(), contextMap, null);
		} catch (Exception e){
			BAD_LOGIN=true;
		}finally{
			if (!BAD_LOGIN){
				String loginId = loginForm.getUserName();
				loginId="000282";
				User ssoUser = loginDAO.findUserDetails(loginId);
				PortletPreferences prefs = request.getPreferences();
				if (ssoUser != null){
					SSOManager.createSSOCookie(request, response, Long.toString(ssoUser.getUserID()) ,loginId,
				            "ICONNECT", "EMP", ssoUser.getLastName(), ssoUser.getFirstName(),
				            prefs.getValue(PREF_COOKIE_ENV, "NOT SET"), InetAddress.getLocalHost().getHostAddress());
				}
				response.sendRedirect("/cgc/myportal/connect/home");
			}
		}
		
	}

}
