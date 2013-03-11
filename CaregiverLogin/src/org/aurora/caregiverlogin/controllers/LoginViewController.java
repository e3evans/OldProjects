package org.aurora.caregiverlogin.controllers;

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
import org.aurora.caregiverlogin.beans.User;
import org.aurora.caregiverlogin.daos.LoginDAO;
import org.aurora.caregiverlogin.forms.LoginForm;
import org.aurora.caregiverlogin.utils.SSOManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;

import com.ibm.portal.portlet.service.PortletServiceHome;
import com.ibm.portal.portlet.service.PortletServiceUnavailableException;
import com.ibm.portal.portlet.service.login.LoginHome;
import com.ibm.portal.portlet.service.login.LoginService;

@Controller
@RequestMapping("VIEW")
public class LoginViewController {

	@Autowired
	private LoginDAO loginDAO;

	protected static final Logger logger = Logger
			.getLogger(LoginViewController.class.getSimpleName());

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

	LoginHome loginHome;

	@PostConstruct
	public void init() {
		try {
			InitialContext ctx = new InitialContext();
			PortletServiceHome psh = (PortletServiceHome) ctx
					.lookup(LoginHome.JNDI_NAME);
			loginHome = (LoginHome) psh.getPortletService(LoginHome.class);
		} catch (NamingException e) {
			logger.error("NamingException in init", e);
		} catch (PortletServiceUnavailableException e) {
			logger.error("PortletServiceUnavailableException in init", e);
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
			logger.warn(loginForm.getUserName());
		} catch (Exception e) {
			BAD_LOGIN = true;
			logger.error("Exception in doLogin", e);
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

				String sendRedirect = URLDecoder.decode(
						getCookieValue(hsreq.getCookies(), COOKIE_FORWARD,
								"EMPTY"), "UTF-8");
				if ("EMPTY".equals(sendRedirect)) {
					response.sendRedirect("/cgc/myportal/connect/home");
				} else {
					String temp = sendRedirect.substring(sendRedirect
							.indexOf("/cgc"));
					temp = temp.replaceAll("%20", " ");
					temp = temp.substring(0, temp.indexOf("/!ut"));
					response.sendRedirect(temp);
				}
			}
		}
	}

	public static String getCookieValue(Cookie[] cookies, String cookieName,
			String defaultValue) {
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookieName.equals(cookie.getName())) {
				return (cookie.getValue());
			}
		}
		return (defaultValue);
	}
}