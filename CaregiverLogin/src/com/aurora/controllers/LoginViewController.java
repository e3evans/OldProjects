package com.aurora.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
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

	protected static final Logger log = Logger
			.getLogger(LoginViewController.class.getSimpleName());

	public static String ENV_USERPASSWORD = "com.aurora.seedlist.userpassword";
	public static String PREF_WCM_PATH = "wcm.path";
	public static String PREF_WCM_COMPONENT = "wcm.menuComponent";
	public static String PRED_WCM_LIB = "wcm.library";
	public static String PREF_COOKIE_ENV = "cookie.env";
	public static String SYS_COOKIE_ENV = "org.aurora.cookie.url";
	public static String PARAM_BAD_SESSION = "SESSIONTIMEOUT";
	public static String REQ_HEADER_FORWARD_IP = "X-Forwarded-For";
	public static String COOKIE_FORWARD = "WASReqURL";
	public boolean BAD_LOGIN = false;
	public boolean BAD_SESSION = false;
	
	public String temp="http://:10039/cgc/myportal/connect/home/media/news/weight%20watchers%20works%20--%20read%20one%20caregivers%20story%20and%20explore%20this%20option%20today/!ut/p/b1/hc9Lc4IwEAfwT1R2E17NMQiCNAkMD4VcHPpyEMG206nopy86XnrQ7m1nfrv7X9BQUxMdhtRCqEAPzU-7ab7b_dDszr121l7oycy3kiASguDC82WMitMwsCdQTwBvFMfL_BJDEaU-Jgmbn-ctJXMeUkQCK6i965I76p8QK9B_CaEXkgozKmKK1LyCOzlVtO_foJ6Ye_MUcaCACq11vj1-LE7dKdvi-CS7zVgUSpKSxDkOmSoCVGyu8s5W-FUecvwkKuhG5c9MqUT6usxKj3PZcxJDDLp97o3DS2-gQWxERojFXPvRYa47fTaDXu_E8aF796sT_wWXCYyu/dl4/d5/L2dBISEvZ0FBIS9nQSEh/";

	LoginHome loginHome;

	@PostConstruct
	public void init() {
		try {
			InitialContext ctx = new InitialContext();
			PortletServiceHome psh = (PortletServiceHome) ctx
					.lookup(LoginHome.JNDI_NAME);
			loginHome = (LoginHome) psh.getPortletService(LoginHome.class);
		} catch (NamingException e) {
			log.error("NamingException in init", e);
		} catch (PortletServiceUnavailableException e) {
			log.error("PortletServiceUnavailableException in init", e);
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping
	public ModelAndView defaultView(RenderRequest request,
			RenderResponse responses, Map model,
			@ModelAttribute("loginForm") LoginForm form)
			throws UnsupportedEncodingException {
		HttpServletRequest hsreq = com.ibm.ws.portletcontainer.portlet.PortletUtils
				.getHttpServletRequest(request);
		if (null != hsreq.getParameter(PARAM_BAD_SESSION) && !BAD_LOGIN) {
			BAD_SESSION = true;
		} else {
			BAD_SESSION = false;
		}
		if (form == null)
			form = new LoginForm();
		form.setBadLogin(BAD_LOGIN);
		form.setBadSession(BAD_SESSION);
		return new ModelAndView("loginView", "loginForm", form);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ActionMapping("doLogin")
	public void doLogin(ActionRequest request, ActionResponse response,
			@ModelAttribute("loginForm") LoginForm loginForm)
			throws IOException {
		LoginService loginService = loginHome
				.getLoginService(request, response);
		Map contextMap = new HashMap();
		contextMap.put(LoginService.DO_RESUME_SESSION_KEY, new Boolean(false));
		HttpServletRequest hsreq = com.ibm.ws.portletcontainer.portlet.PortletUtils
				.getHttpServletRequest(request);
		BAD_LOGIN = false;
		try {
			loginService.login(loginForm.getUserName(), loginForm.getPassword()
					.toCharArray(), contextMap, null);
			log.warn(loginForm.getUserName());
		} catch (Exception e) {
			BAD_LOGIN = true;
			log.error("Exception in doLogin", e);
		} finally {
			if (!BAD_LOGIN) {
				String loginId = loginForm.getUserName();
				User ssoUser = loginDAO.findUserDetails(loginId);
				if (ssoUser != null) {
					SSOManager.createSSOCookie(request, response,
							Long.toString(ssoUser.getUserID()), loginId,
							"ICONNECT", "EMP", ssoUser.getLastName(),
							ssoUser.getFirstName(),
							System.getProperty(SYS_COOKIE_ENV, "NOT SET"),
							hsreq.getHeader(REQ_HEADER_FORWARD_IP));
				}
				
				String sendRedirect = URLDecoder.decode(getCookieValue(hsreq.getCookies(), COOKIE_FORWARD, "EMPTY"),"UTF-8");
				if ("EMPTY".equals(sendRedirect)){
					response.sendRedirect("/cgc/myportal/connect/home");
				}else{
					String temp = sendRedirect.substring(sendRedirect.indexOf("/cgc"));
					temp = temp.replaceAll("%20", " ");
					temp = temp.substring(0,temp.indexOf("/!ut"));
					response.sendRedirect(temp);
				}
			}
		}
	}
	
	public static String getCookieValue(Cookie[] cookies,String cookieName,String defaultValue){
		for (int i=0;i<cookies.length;i++){
			Cookie cookie = cookies[i];
			if (cookieName.equals(cookie.getName())){
				return (cookie.getValue());
			}
		}
		return(defaultValue);
	}
}
