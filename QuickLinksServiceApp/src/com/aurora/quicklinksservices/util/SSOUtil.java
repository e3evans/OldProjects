package com.aurora.quicklinksservices.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

/**
 * A utility that interacts with the SSO cookie value.
 */
public class SSOUtil {
  private static Logger log = Logger.getLogger(SSOUtil.class.getName());
  /*
  * Since constructing a 'mac' is an expensive operation - statically
  * initialize it, and reuse for all instances of this util.
  */
  private static final Mac mac = buildMac();
  private StringBuffer xml;
  //XML elements found in the SSO cookie
  private static final String ROOT = "root";
  private static final String PORTAL = "Portal";
  public static final String PORTAL_KEY = "PortalKey";
  public static final String USER_ID = "UserId";
  public static final String PORTAL_ID = "PortalId";
  public static final String USER_ROLE = "UserRole";
  public static final String USER_LAST_NM = "UserLastName";
  public static final String USER_FIRST_NM = "UserFirstName";
  public static final String PORTAL_URL = "PortalUrl";
  public static final String CLIENT_IP_ADDR = "ClientIPAddr";
  public static final String SESSION_TIMESTAMP = "SessionTimestamp";
  public static final String HASH = "HASH";

  // Applications use referer to find portal match
  private String referer;
  // Portals use server to find portal match
  private String portalId;

  public static final int MAX_TIMEOUT = 240;

  /**
   * Default constructor.
   */
  public SSOUtil() {
  }

  /**
   * Constructor.
   *
   * @param xml The SSO xml string.
   * @param referer The html header referer value.
   * @param portalId The exact portal id or null to use referer.
   */
  public SSOUtil(String xml, String referer, String portalId) {
    this.xml = new StringBuffer(xml);
    this.referer = referer;
    this.portalId = portalId;
    
    //dereference
    xml = null;
    referer = null;
    portalId = null;
  }

  /**
   * Creates a signed SSO xml string and returns it.
   * 
   * @param portalKey
   *            The blind key for this portal user.
   * @param userId
   *            The user id for this portal (empNo, patientId, etc.)
   * @param portalId
   *            The portal id for this portal.
   * @param userRole
   *            The user role for this portal.
   * @param userLastName
   *            The user's last name for this portal.
   * @param userFirstName
   *            The user's first name for this portal.
   * @param clientIPAddress
   *            The user's client IP Address from the browser.
   * @param portalUrl
   *            The server for this portal.
   * @return String The signed SSO xml string.
   */
  public String create(String portalKey, String userId, String portalId,
		  String userRole, String userLastName, String userFirstName,
		  String clientIPAddress, String portalUrl) {

	  userLastName = removeCookieDelimiters(userLastName);
	  userFirstName = removeCookieDelimiters(userFirstName);

	  xml = new StringBuffer()
		  .append(buildTag(ROOT, true))
		  .append(buildTag(PORTAL, true))
		  .append(buildElement(PORTAL_KEY, portalKey))
		  .append(buildElement(USER_ID, userId))
		  .append(buildElement(PORTAL_ID, portalId))
		  .append(buildElement(USER_ROLE, userRole))
		  .append(buildElement(USER_LAST_NM, userLastName))
		  .append(buildElement(USER_FIRST_NM, userFirstName))
		  .append(buildElement(PORTAL_URL, portalUrl))
		  .append(buildElement(CLIENT_IP_ADDR, clientIPAddress))
		  .append(buildTag(PORTAL, false))
		  .append(buildElement(SESSION_TIMESTAMP, String.valueOf(System.currentTimeMillis())))
		  .append(buildTag(ROOT, false));

	  //dereference
	  portalKey = null;
	  userId = null; 
	  portalId = null;
	  userRole = null;
	  userFirstName = null;
	  userLastName = null;
	  clientIPAddress = null;
	  portalUrl = null;

	  return signDocument();
  }

  /**
   * Append a portal section to a signed SSO xml string and returns it.
   * 
   * @param portalKey
   *            The blind key for this portal user.
   * @param userId
   *            The user id for this portal (empNo, patientId, etc.)
   * @param portalId
   *            The portal id for this portal.
   * @param userRole
   *            The user role for this portal.
   * @param userLastName
   *            The user's last name for this portal.
   * @param userFirstName
   *            The user's first name for this portal.
   * @param clientIPAddress
   *            The user's client IP Address from the browser.
   * @param portalUrl
   *            The server for this portal.
   * @return String The signed SSO xml string.
   */
  public String append(String portalKey, String userId, String portalId,
		  String userRole, String userLastName, String userFirstName,
		  String clientIPAddress, String portalUrl) {

    userLastName = removeCookieDelimiters(userLastName);
    userFirstName = removeCookieDelimiters(userFirstName);

    ArrayList portals = getPortals();
    xml = new StringBuffer().append(buildTag(ROOT, true));
    Iterator i = portals.iterator();
    while (i.hasNext()) {
      String portal = (String)i.next();
      if (!getElementValue(portal, PORTAL_ID).equals(portalId)) {
    	  xml.append(buildTag(PORTAL, true))
    	  	.append(portal)
    	  	.append(buildTag(PORTAL, false));
      }
    }
    xml.append(buildTag(PORTAL, true))
    	.append(buildElement(PORTAL_KEY, portalKey))
    	.append(buildElement(USER_ID, userId))
    	.append(buildElement(PORTAL_ID, portalId))
    	.append(buildElement(USER_ROLE, userRole))
    	.append(buildElement(USER_LAST_NM, userLastName))
    	.append(buildElement(USER_FIRST_NM, userFirstName))
    	.append(buildElement(PORTAL_URL, portalUrl))
    	.append(buildElement(CLIENT_IP_ADDR, clientIPAddress))
    	.append(buildTag(PORTAL, false))
    	.append(buildElement(SESSION_TIMESTAMP, String.valueOf(System.currentTimeMillis())))
    	.append(buildTag(ROOT, false));
    
    //dereference
    portalKey = null; 
    userId = null; 
    portalId = null;
    userRole = null; 
    userLastName = null; 
    userFirstName = null;
	clientIPAddress = null; 
	portalUrl = null;
	
    return signDocument();
  }

  /**
   * Validates a signed SSO xml string. Verifies that the hash value can be
   * re-caculated. Verifies that the global session timeout has not bee hit.
   * Verifies that client IP Address from the browser matched the signed SSO
   * value. Updates the global timeout value. Re-signs (re-hashes) the SSO xml
   * string.
   * 
   * @param cip
   *            The user's client IP Address from the browser.
   * @param sessionMinutes
   *            The number of minutes that the global timeout should occur.
   * @return String The signed SSO xml string.
   */
  public String isValid(String cip, int sessionMinutes) {

	  if (sessionMinutes > MAX_TIMEOUT) {
		  sessionMinutes = MAX_TIMEOUT;
	  }

	  // verify hash with computed hash
	  String hash = getElementValue(HASH);
	  removeElement(HASH);
	  String computedHash = getMacHashValue();
	  boolean isValid = hash.equals(computedHash);

	  // check to see that the cookie hasn't 'expired'
	  if (isValid) {
		  long timeOutValue = sessionMinutes * 60000;
		  long lastAccess = Long
		  .parseLong(getElementValue(SESSION_TIMESTAMP));
		  long currentAccess = new Date().getTime();
		  long diff = currentAccess - lastAccess;
		  if (diff > timeOutValue) {
			  log.debug("sso cookie has timed out!");
			  isValid = false;
		  }
	  }

	  // ensure the request came from the right IP
	  if (isValid) {
		  String portal = getPortal();
		  if (StringUtils.isBlank(portal)) {
			  log.debug("Portal missing from sso cookie");	
			  isValid = false;
		  } else {
			  String cookIp = getElementValue(portal, CLIENT_IP_ADDR);
			  if (cookIp.length() > 0 && !cip.equals(cookIp)) {
				  if (log.isDebugEnabled())
					  log.debug(new StringBuffer("Client IP (")
					  	.append(cip).append(") doesn't match sso value of (")
					  	.append(cookIp).append(")!"));
				  isValid = false;
			  }
			  cookIp = null;
		  }
		  portal = null;
	  }

	  // update the SSO cookie with a new timestamp and hash
	  String sig = null; // null means document is not valid
	  if (isValid) {
		  removeElement(SESSION_TIMESTAMP);
		  addElement(SESSION_TIMESTAMP, String.valueOf(System
				  .currentTimeMillis()));
		  sig = signDocument();
	  }
	  
	  //deference
	  cip = null;
	  hash = null;
	  computedHash = null;
	  return sig;
  }

  /**
   * @return the portal value based logic
   */
  public String getPortal() {
	  String ret = "";
	  String startTag = buildTag(PORTAL, true);
	  int start = xml.indexOf(startTag);
	  while (start > -1) {
		  int end = xml.indexOf(buildTag(PORTAL, false), start);
		  if (start != -1 && end != -1) {
			  String portal = xml.substring((start + startTag.length()), end);
			  if (portalId == null) {
				  // Find portal by referer (the application method)
				  String portalURL = getElementValue(portal, PORTAL_URL);
				  if (referer != null && portalURL != null 
						  && referer.startsWith(portalURL)) {
					  //return immediately (this method takes priority)
					  return portal; 					  
				  }
				  portalURL= null;
			  } else {
				  // Find portal by portalId (the portal method)
				  String portalId = getElementValue(portal, PORTAL_ID);
				  if (portalId != null && this.portalId != null 
						  && portalId.equals(this.portalId)) {
					  ret = portal;		
					  portalId = null;
				  }
				  portalId = null;
			  }
			  portal = null;
		  }
		  start = xml.indexOf(startTag, end);
	  }
	  startTag = null;
	  return ret;
  }

  /**
   * Retries a specific field value from the SSO xml string.
   *
   * @return String The value of the specific field.
   */
  public ArrayList getPortals() {
    ArrayList portals = new ArrayList();
    String startTag = buildTag(PORTAL, true);
    int start = xml.indexOf(startTag);
    while (start > -1) {
      int end = xml.indexOf(buildTag(PORTAL, false), start);
      if (start != -1 && end != -1) {
    	  portals.add(xml.substring((start + startTag.length()), end));    	  
      }
      start = xml.indexOf(startTag, end);
    }
    startTag = null; //dereference
    return portals;
  }

  /**
   * Retries a specific field value from the SSO xml string.
   *
   * @param tag The name of the xml tag to return.
   * @return String The value of the specific field.
   */
  public String getElementValue(String tag) {
    String ret = null;
    String startTag = buildTag(tag, true);
    int start = xml.indexOf(startTag);
    int end = xml.indexOf(buildTag(tag, false));
    if (start != -1 && end != -1) {
      ret = xml.substring((start + startTag.length()), end);
    }
    tag = null;
    return ret;
  }

  /**
   * Retrieves a specific field value from the SSO xml string.
   *
   * @param xml The xml fragment to search.
   *        tag The name of the xml tag to return.
   * @return String The value of the specific field.
   */
  public static String getElementValue(String xml, String tag) {
    String ret = null;
    String startTag = buildTag(tag, true);
    int start = xml.indexOf(startTag);
    int end = xml.indexOf(buildTag(tag, false));
    if (start != -1 && end != -1) {
      ret = xml.substring((start + startTag.length()), end);
    }
    
    startTag = null; //dereference
    xml = null;
    tag = null;
    return ret;
  }

  /**
   * Add an element to the end of the XML doc.
   *
   * @param tag   tag to add
   * @param value value of the tag
   */
  private void addElement(String tag, String value) {
    String el = buildElement(tag, value);
    try {
      xml.insert(xml.indexOf(buildTag(ROOT, false)), el);
    } catch (RuntimeException e) {
      log.warn("Could not insert element: " + el + " to xml: " + xml);
    }
    tag = null;
    value = null;
  }

  /**
   * Remove an element from the underlying XML string.
   *
   * @param tag   the tag to remove (and its value)
   */
  private void removeElement(String tag) {
    if (xml != null) {
      String endTag = buildTag(tag, false);
      int start = xml.indexOf(buildTag(tag, true));
      int end = xml.indexOf(endTag);
      if (start != -1 && end != -1) {
        xml.replace(start, (end + endTag.length()), "");
      }
      endTag = null; //dereference
    }
    tag = null;
  }

  /**
   * @param tag
   *            the tag name (not containing) '<'
   * @param value
   *            the value of the XML element
   * @return an XML element using the provided tag and the XML value
   */
  private static String buildElement(String tag, String value) {
	  String ret = new StringBuffer(buildTag(tag, true)).append(value).append(
			  buildTag(tag, false)).toString(); 
	  tag = null;
	  value = null;
	  return ret;

  }

  /**
   * @param tag
   *            the tag
   * @param start
   *            is it a start tag?
   * @return an XML element with the provided tag name
   */
  private static String buildTag(String tag, boolean start) {
    StringBuffer sb = new StringBuffer("<");
    if (!start) {
      sb.append("/");
    }
    String ret = sb.append(tag).append(">").toString();
    tag = null;
    return ret;
  }

  /**
   * Signs the SSO xml string and returns it.
   *
   * @return String The signed SSO xml string.
   */
  private String signDocument() {
    addElement(HASH, getMacHashValue());
    return xml.toString();
  }

  /**
   * Retries a base64 encoded hash using the Shared Secret.
   *
   * @return String The base64 enocded hash.
   */
  private String getMacHashValue() {
    if (log.isDebugEnabled()) {
      log.debug(new StringBuffer("Generating hash from: ").append(xml));
    }
    byte[] result = null;
    synchronized (mac) { //force multiple threads to block while mac works
      result = mac.doFinal(xml.toString().getBytes());
    }
    return new String(Base64.encodeBase64(result));
  }

  /**
   * A utility that builds the MAC used by this utility class. NOTE: this
   * method should NEVER be called besides by the static initializer.
   *
   * @return a MAC with the configured sercret key
   */
  private static synchronized Mac buildMac() {
    if (mac != null) {
      throw new IllegalStateException("Mac is already initialized.");
    }
    Mac ret = null;
    try {
      ret = Mac.getInstance("HmacSHA1");
      ret.init(new SecretKeySpec("7f3mss9".getBytes(), "HmacSHA1"));
    } catch (Exception e) {
      log.fatal("Could not intialize key / mac!!!", e);
    }
    return ret;
  }

  private static String removeCookieDelimiters(String string) {
    if (string != null) {
      //Remove any semicolons and any commas in the string
      string = string.replaceAll(";", "");
      string = string.replaceAll(",", "");
    }
    return string;  
  }
}