package com.aurora.controllers;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.security.auth.login.LoginException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;

import com.aurora.org.caregiverlogin.forms.LoginForm;
import com.ibm.portal.auth.exceptions.AuthenticationException;
import com.ibm.portal.auth.exceptions.AuthenticationFailedException;
import com.ibm.portal.auth.exceptions.PasswordInvalidException;
import com.ibm.portal.auth.exceptions.PortletLoginDisabledException;
import com.ibm.portal.auth.exceptions.SessionTimeOutException;
import com.ibm.portal.auth.exceptions.SystemLoginException;
import com.ibm.portal.auth.exceptions.UserAlreadyLoggedInException;
import com.ibm.portal.auth.exceptions.UserIDInvalidException;
import com.ibm.portal.portlet.service.PortletServiceHome;
import com.ibm.portal.portlet.service.PortletServiceUnavailableException;
import com.ibm.portal.portlet.service.login.LoginHome;
import com.ibm.portal.portlet.service.login.LoginService;
import com.ibm.websphere.security.WSSecurityException;



@Controller
@RequestMapping("VIEW")
public class LoginViewController {
	
	public static String ENV_USERPASSWORD = "com.aurora.seedlist.userpassword";
	public static String PREF_WCM_PATH = "wcm.path";
	public static String PREF_WCM_COMPONENT ="wcm.menuComponent";
	public static String PRED_WCM_LIB = "wcm.library";
	
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
	   
		if (form==null)form = new LoginForm();
		
		return new ModelAndView("loginView","loginForm",form);
	}
	
	@SuppressWarnings("rawtypes")
	@ActionMapping("doLogin")
	public void doLogin(ActionRequest request, ActionResponse response,@ModelAttribute("loginForm")LoginForm loginForm){
		LoginService loginService = loginHome.getLoginService(request, response);
		Map contextMap = new HashMap();
		try {
			loginService.login(loginForm.getUserName(), loginForm.getPassword().toCharArray(), contextMap, null);
		} catch (PasswordInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UserIDInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AuthenticationFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SessionTimeOutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PortletLoginDisabledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UserAlreadyLoggedInException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemLoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WSSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (com.ibm.portal.auth.exceptions.LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
