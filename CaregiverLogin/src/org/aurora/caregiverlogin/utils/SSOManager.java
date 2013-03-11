package org.aurora.caregiverlogin.utils;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.servlet.http.*;

/**
 * Utility class for interacting with the SSO cookie.
 */
public class SSOManager {
  //private static Logger log = Logger.getLogger(SSOManager.class.getName());
  public static String PORTALID_ICONNECT = "ICONNECT";
  public static String PORTALID_MYAURORA = "MYAURORA";
  public static String PORTALID_CLINICAL = "CLINICAL";
  public static String COOKIE_NAME = "auroraSSO";
  public static String COOKIE_DOMAIN = ".aurora.org";
  public static String COOKIE_PATH = "/";

  public static int DEFAULT_TIMEOUT_MINUTES = 29;

  /**
   * Create SSO cookie and store it in the Http servlet response.
   * 
   * @param request
   *            The Http servlet request object.
   * @param response
   *            The Http servlet response object.
   * @param portalKey
   *            The blind key for this portal user.
   * @param userId
   *            The user id for this portal (empNo, patientId, etc.)
   * @param portalId
   *            The portal type id for this portal.
   * @param userRole
   *            The user role for this portal.
   * @param userLastName
   *            The user's last name for this portal.
   * @param userFirstName
   *            The user's first name for this portal.
   * @param portalUrl
   *            The server for this portal.
   */
  public static void createSSOCookie(HttpServletRequest request,
		  HttpServletResponse response, String portalKey, String userId,
		  String portalId, String userRole, String userLastName,
		  String userFirstName, String portalUrl) {
    SSOUtil ssoUtil = new SSOUtil();
    String clientIPAddress = request.getRemoteAddr();
    String sso = ssoUtil.create(portalKey, userId, portalId, userRole,
            userLastName, userFirstName, clientIPAddress, portalUrl);
    Cookie cookie = new Cookie(SSOManager.COOKIE_NAME, sso);
    cookie.setPath(SSOManager.COOKIE_PATH);
    cookie.setDomain(SSOManager.COOKIE_DOMAIN);
    response.addCookie(cookie);
    
    //deref
    portalKey = null; 
    userId = null;
    portalId = null;
    userRole = null;
    userLastName = null;
    userFirstName = null; 
    portalUrl = null;
    ssoUtil = null;
    sso = null;
  }

  /**
   * Create SSO cookie and store it in the Http servlet response.
   * 
   * @param request
   *            The Http servlet request object.
   * @param response
   *            The Http servlet response object.
   * @param portalKey
   *            The blind key for this portal user.
   * @param userId
   *            The user id for this portal (empNo, patientId, etc.)
   * @param portalId
   *            The portal type id for this portal.
   * @param userRole
   *            The user role for this portal.
   * @param userLastName
   *            The user's last name for this portal.
   * @param userFirstName
   *            The user's first name for this portal.
   * @param portalUrl
   *            The server for this portal.
   * @param clientIPAddress
   *            The IP address of the client.
   */
  public static void createSSOCookie(HttpServletRequest request,
		  HttpServletResponse response, String portalKey, String userId,
		  String portalId, String userRole, String userLastName,
		  String userFirstName, String portalUrl, String clientIPAddress) {
    SSOUtil ssoUtil = new SSOUtil();
    String sso = ssoUtil.create(portalKey, userId, portalId, userRole,
            userLastName, userFirstName, clientIPAddress, portalUrl);
    Cookie cookie = new Cookie(SSOManager.COOKIE_NAME, sso);
    cookie.setPath(SSOManager.COOKIE_PATH);
    cookie.setDomain(SSOManager.COOKIE_DOMAIN);
    response.addCookie(cookie);
    
    //deref
    portalKey = null; 
    userId = null;
    portalId = null;
    userRole = null;
    userLastName = null;
	userFirstName = null; 
	portalUrl = null; 
	clientIPAddress = null;
    ssoUtil = null;
    sso = null;
  }
  public static void createSSOCookie(ActionRequest request,
		  ActionResponse response, String portalKey, String userId,
		  String portalId, String userRole, String userLastName,
		  String userFirstName, String portalUrl, String clientIPAddress) {
    SSOUtil ssoUtil = new SSOUtil();
    String sso = ssoUtil.create(portalKey, userId, portalId, userRole,
            userLastName, userFirstName, clientIPAddress, portalUrl);
    Cookie cookie = new Cookie(SSOManager.COOKIE_NAME, sso);
    cookie.setPath(SSOManager.COOKIE_PATH);
    cookie.setDomain(SSOManager.COOKIE_DOMAIN);
    response.addProperty(cookie);
    
    //deref
    portalKey = null; 
    userId = null;
    portalId = null;
    userRole = null;
    userLastName = null;
	userFirstName = null; 
	portalUrl = null; 
	clientIPAddress = null;
    ssoUtil = null;
    sso = null;
  }
  

  public static HttpServletResponse createSSOCookie(HttpServletResponse response, String portalKey, String userId,
		  String portalId, String userRole, String userLastName,
		  String userFirstName, String portalUrl, String clientIPAddress) {
    SSOUtil ssoUtil = new SSOUtil();
    String sso = ssoUtil.create(portalKey, userId, portalId, userRole,
            userLastName, userFirstName, clientIPAddress, portalUrl);
    Cookie cookie = new Cookie(SSOManager.COOKIE_NAME, sso);
    cookie.setPath(SSOManager.COOKIE_PATH);
    cookie.setDomain(SSOManager.COOKIE_DOMAIN);
    response.addCookie(cookie);
    
    //deref
    portalKey = null; 
    userId = null;
    portalId = null;
    userRole = null;
    userLastName = null;
	userFirstName = null; 
	portalUrl = null; 
	clientIPAddress = null;
    ssoUtil = null;
    sso = null;
    
    return response;
  }
  
  
  /**
   * Append additional portal to SSO cookie and store it in the Http servlet
   * response.
   * 
   * @param request
   *            The Http servlet request object.
   * @param response
   *            The Http servlet response object.
   * @param portalKey
   *            The blind key for this portal user.
   * @param userId
   *            The user id for this portal (empNo, patientId, etc.)
   * @param portalId
   *            The portal type id for this portal.
   * @param userRole
   *            The user role for this portal.
   * @param userLastName
   *            The user's last name for this portal.
   * @param userFirstName
   *            The user's first name for this portal.
   * @param portalUrl
   *            The server for this portal.
   * @param clientIPAddress
   *            The IP address of the client.
   * @param timeoutMinutes
   *            The timeout value in minutes.
   */
  public static void appendSSOCookie(HttpServletRequest request,
			HttpServletResponse response, String portalKey, String userId,
			String portalId, String userRole, String userLastName,
			String userFirstName, String portalUrl, String clientIPAddress,
			String curPortalId, int timeoutMinutes) {
    if (validSSOCookie(request, response, curPortalId, timeoutMinutes)) {
      SSOUtil ssoUtil = getSSO(request, portalId);
      String sso = ssoUtil.append(portalKey, userId, portalId, userRole,
              userLastName, userFirstName, clientIPAddress, portalUrl);
      Cookie cookie = new Cookie(SSOManager.COOKIE_NAME, sso);
      cookie.setPath(SSOManager.COOKIE_PATH);
      cookie.setDomain(SSOManager.COOKIE_DOMAIN);
      response.addCookie(cookie);
      
      //dereference
      portalKey = null;
      userId = null;
      portalId = null;
      userRole = null;
      userLastName = null;
      userFirstName = null;
      portalUrl = null;
      clientIPAddress = null;
      curPortalId = null;
      ssoUtil = null;
      sso = null;
    }
  }

  /**
   * Delete SSO cookie and remove it from the Http servlet response.
   * 
   * @param request
   *            The Http servlet request object.
   * @param response
   *            The Http servlet response object.
   */
  public static void deleteSSOCookie(HttpServletRequest request,
		  HttpServletResponse response) {
	  Cookie cookie = new Cookie(SSOManager.COOKIE_NAME, "");
	  cookie.setPath(SSOManager.COOKIE_PATH);
	  cookie.setDomain(SSOManager.COOKIE_DOMAIN);
	  cookie.setMaxAge(0);
	  response.addCookie(cookie);
  }

  /**
   * Validate the SSO cookie and update/store it in the Http servlet response.
   * The global sesssion timeout and the hash are updated. The global session
   * timeout will be reset to default value minutes.
   * 
   * @param request
   *            The Http servlet request object.
   * @param response
   *            The Http servlet response object.
   */
  public static boolean validSSOCookie(HttpServletRequest request,
		  HttpServletResponse response) {
	  return validSSOCookie(request, response, DEFAULT_TIMEOUT_MINUTES);
  }

  /**
   * Validate the SSO cookie timeout and hash and IP address of the client and
   * update the cookie, then store it in the Http servlet response. The global
   * sesssion timeout and the hash are updated.
   * 
   * @param request
   *            The Http servlet request object.
   * @param response
   *            The Http servlet response object.
   * @param timeoutMinutes
   *            The number of minutes the global session timeout should be set
   *            (max is 240 minutes).
   */
  public static boolean validSSOCookie(HttpServletRequest request,
		  HttpServletResponse response, int timeoutMinutes) {
    boolean isValid = false;
    Cookie cookie = getCookie(request);
    if (cookie != null) {
      String referer = request.getHeader("REFERER");
      SSOUtil ssoUtil = new SSOUtil(cookie.getValue(), referer, null);
      String clientIPAddress = request.getRemoteAddr();
      String sso = ssoUtil.isValid(clientIPAddress, timeoutMinutes);
      if (sso != null) {
        isValid = true;
        cookie.setValue(sso);
        cookie.setPath(SSOManager.COOKIE_PATH);
        cookie.setDomain(SSOManager.COOKIE_DOMAIN);
        response.addCookie(cookie);
      }
      
      ssoUtil = null;
      clientIPAddress = null;
      sso = null;
    }
    return isValid;
  }

  /**
   * Validate the SSO cookie and update/store it in the Http servlet response.
   * The global sesssion timeout and the hash are updated. The global session
   * timeout will be reset to default value minutes.
   * 
   * @param request
   *            The Http servlet request object.
   * @param response
   *            The Http servlet response object.
   */
  public static boolean validSSOCookie(HttpServletRequest request,
		  HttpServletResponse response, String portalId) {
	  return validSSOCookie(request, response, portalId,
			  DEFAULT_TIMEOUT_MINUTES);
  }

  /**
   * Validate the SSO cookie timeout and hash and IP address of the client and
   * update the cookie, then store it in the Http servlet response. The global
   * sesssion timeout and the hash are updated.
   * 
   * @param request
   *            The Http servlet request object.
   * @param response
   *            The Http servlet response object.
   * @param timeoutMinutes
   *            The number of minutes the global session timeout should be set
   *            (max is 240 minutes).
   */
  public static boolean validSSOCookie(HttpServletRequest request,
		  HttpServletResponse response, String portalId, int timeoutMinutes) {
    boolean isValid = false;
    Cookie cookie = getCookie(request);
    if (cookie != null) {
      String referer = request.getHeader("REFERER");
      SSOUtil ssoUtil = new SSOUtil(cookie.getValue(), referer, portalId);
      String clientIPAddress = request.getRemoteAddr();
      String sso = ssoUtil.isValid(clientIPAddress, timeoutMinutes);
      if (sso != null) {
        isValid = true;
        cookie.setValue(sso);
        cookie.setPath(SSOManager.COOKIE_PATH);
        cookie.setDomain(SSOManager.COOKIE_DOMAIN);
        response.addCookie(cookie);
      }
      
      //dereference
      portalId = null;
      referer = null;
      ssoUtil = null;
      clientIPAddress = null;
      sso = null;
    }
    return isValid;
  }

  /**
   * @param request The Http servlet request object.
   * @return a very lightweight object containing the data encapsulated in the
   *         SSO cookie
   */
  public static PortalUser getPortalUser(HttpServletRequest request) {
    SSOUtil ssoUtil = getSSO(request, null);
    PortalUser portalUser = null;
    if (ssoUtil != null) {
    	portalUser = new PortalUser(ssoUtil);    	
    }
    ssoUtil = null; //deref
    return portalUser;
  }

  /**
   * @param request
   *            The Http servlet request object.
   * @return a very lightweight object containing the data encapsulated in the
   *         SSO cookie
   */
  public static PortalUser getPortalUser(HttpServletRequest request,
		  String portalId) {
	  SSOUtil ssoUtil = getSSO(request, portalId);
	  PortalUser portalUser = null;
	  if (ssoUtil != null) {
		  portalUser = new PortalUser(ssoUtil);
	  }
	  portalId = null;
	  ssoUtil = null;
	  return portalUser;
  }

  /**
   * Retreives the SSO Cookie info from the Http servlet request and puts it
   * into an instance of the SSOUtil object.
   * 
   * @param request
   *            The Http servlet request object.
   * @return SSOUtil The SSO object instance retreived.
   */
  private static SSOUtil getSSO(HttpServletRequest request, String portalId) {
	  Cookie cookie = getCookie(request);
	  SSOUtil ssoUtil = null;
	  if (cookie != null) {
		  String referer = request.getHeader("REFERER");
		  ssoUtil = new SSOUtil(cookie.getValue(), referer, portalId);
		  referer = null;
	  }

	  portalId = null;
	  cookie = null;
	  
	  return ssoUtil;
  }

  /**
   * Retreives the SSO Cookie from the Http servlet request object.
   * 
   * @param request
   *            The Http servlet request object.
   * @return Cookie The SSO Cookie.
   */
  private static Cookie getCookie(HttpServletRequest request) {
	  Cookie[] cookies = request.getCookies();
	  Cookie cookie = null;
	  if (cookies != null) {
		  for (int i = 0; i < cookies.length; i++) {
			  if (SSOManager.COOKIE_NAME.equals(cookies[i].getName())) {
				  cookie = cookies[i];
				  break;
			  }
		  }
	  }
	  return cookie;
  }

  // Use servername to determine portalid
  public static String getPortalId(HttpServletRequest request) {
    if (request.getServerName().toUpperCase().indexOf(PORTALID_MYAURORA) > -1)
      return PORTALID_MYAURORA;
    if (request.getServerName().toUpperCase().indexOf(PORTALID_CLINICAL) > -1)
      return PORTALID_CLINICAL;
    if (request.getServerName().toUpperCase().indexOf(PORTALID_ICONNECT) > -1)
      return PORTALID_ICONNECT;
    return null;
  }

  /**
   * Utility class - not to be instantiated.
   */
  private SSOManager() {
  }
}
