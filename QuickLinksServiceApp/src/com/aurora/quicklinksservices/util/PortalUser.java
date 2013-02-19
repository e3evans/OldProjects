/*
 * Class created Sep 12, 2006
 */
package com.aurora.quicklinksservices.util;

//import org.aurora.portal_services.beans.AppAuthInfo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

/**
 * A simple value object encapsulating the data that is in the SSO cookie.  The
 * constructor is set to package protected so that only classes in the SSO
 * utility class can create instances of this class.
 *
 * @author Brock Heinz (nVISIA)
 */
public class PortalUser implements Serializable {
  private static final long serialVersionUID = 1956083486378325330L;
  /**
   * The blind key of the portal user. The value should NOT be used when
   * storing data in client applications. Client application that need to
   * store an ID should use the 'userId' instead.
   */
  private Long portalKey;
  /**
   * The id as it pertains to the portal type Id. If the portal type id is
   * 'MYAURORA', this is the patient id; if the type id is 'CLINICAL' this is
   * the clinician id, etc.
   */
  private String userId;
  /**
   * The portal type: MYAURORA, CLINICIAN, EMPLOYEE ICONNECT, etc
   */
  private String portalId;
  private String userRole;
  private String lastName;
  private String firstName;
  private String portalUrl;
  private String clientIP;

  private Collection<AppAuthInfo> permissions;
  private boolean permissionsExceptionThrown;

  /**
   * Build a user object with the SSO utility.
   *
   * @param util utility used when parsing SSO cookie
   */
  PortalUser(SSOUtil util) {
    if (util != null) {
      String portal = util.getPortal();
      String strPortalKey = SSOUtil.getElementValue(portal, SSOUtil.PORTAL_KEY);
      if (!empty(strPortalKey)) {
        portalKey = new Long(strPortalKey);
      }
      userId = SSOUtil.getElementValue(portal, SSOUtil.USER_ID);
      portalId = SSOUtil.getElementValue(portal, SSOUtil.PORTAL_ID);
      userRole = SSOUtil.getElementValue(portal, SSOUtil.USER_ROLE);
      lastName = SSOUtil.getElementValue(portal, SSOUtil.USER_LAST_NM);
      firstName = SSOUtil.getElementValue(portal, SSOUtil.USER_FIRST_NM);
      portalUrl = SSOUtil.getElementValue(portal, SSOUtil.PORTAL_URL);
      clientIP = SSOUtil.getElementValue(portal, SSOUtil.CLIENT_IP_ADDR);
      
      strPortalKey = null;
      portal = null;
    } else {
      throw new IllegalArgumentException("Util cannot be null");
    }
  }

  /**
   * IDE generated 'get' method
   *
   * @return Returns firstName.
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * IDE generated 'get' method
   *
   * @return Returns lastName.
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * IDE generated 'get' method
   * @deprecated use getPortalKey
   * @return Returns portalKey.
   */
  public Long getPortalUserId() {
    return portalKey;
  }

  /**
   * IDE generated 'get' method
   *
   * @return Returns portalKey.
   */
  public Long getPortalKey() {
    return portalKey;
  }

  /**
   * IDE generated 'get' method
   *
   * @return Returns userId.
   */
  public String getUserId() {
    return userId;
  }

  /**
   * IDE generated 'get' method
   *
   * @return Returns userRole.
   */
  public String getUserRole() {
    return userRole;
  }

  /**
   * IDE generated 'get' method
   * @deprecated use getPortalId
   * @return Returns portalId.
   */
  public String getPortalTypeId() {
    return portalId;
  }
  /**
   * IDE generated 'get' method
   *
   * @return Returns portalId.
   */
  public String getPortalId() {
    return portalId;
  }

  /**
   * IDE generated 'get' method
   *
   * @return Returns portalUrl.
   */
  public String getPortalUrl() {
    return portalUrl;
  }

  /**
   * IDE generated 'get' method
   *
   * @return Returns clientIP.
   */
  public String getClientIP() {
    return clientIP;
  }

  /**
   * @return the full name in the form of First Last.
   */
  public String getFullName() {
    return new StringBuffer()
            .append(getFirstName())
            .append(" ")
            .append(getLastName()).toString();
  }

  /**
   * Checks if a permission exists. Throws an exception if the permissions are unknown (which could happen if there
   * was a portal-services problem that resulted in the PortalSSOFilter being unable to load the data, or if the
   * filter was not configured with load-permissions=true). This method checks for the existence of the permission
   * (aka: AppAuth) across all sub-applications. The sequence numbers are disregarded.
   * @param appAuthName The AppAuth name.
   * @return True if the permission exists.
   * @throws PermissionsUnavailableException Thrown if the permissions are not available.
   */
  public boolean hasPermission(final String appAuthName) throws PermissionsUnavailableException {
    return hasPermission(appAuthName, null, null);
  }

  /**
   * Checks if the permission exists. This method signature is provided because, in general, only the AppAuths
   * (the permissions) of the current application exist in the PortalUser object because the appId is supplied as a
   * configuration parameter to PortalSSOFilter. Hence, the appId parameter is not required.
   * @param appAuthName The AppAuth name.
   * @param seqNo Optional parameter. If non-null, only AppAuths with a matching Sequence Number are considered.
   * @return True if the permission exists.
   * @throws PermissionsUnavailableException Thrown if the permissions are not available.
   */
  public boolean hasPermission(final String appAuthName, final Integer seqNo) throws PermissionsUnavailableException {
      return hasPermission(appAuthName, null, seqNo);
  }

  /**
   * Checks if the permission exists.
   * @param appAuthName The AppAuth name.
   * @param appId Optional parameter. If non-null, only AppAuths with a matching AppId are considered.
   * @param seqNo Optional parameter. If non-null, only AppAuths with a matching Sequence Number are considered.
   * @return True if the permission exists.
   * @throws PermissionsUnavailableException Thrown if the permissions are not available.
   */
  public boolean hasPermission(final String appAuthName, final String appId, final Integer seqNo)
        throws PermissionsUnavailableException {
      if (this.permissions != null) {
          final Iterator<AppAuthInfo> iter = this.permissions.iterator();
          while (iter.hasNext()) {
              AppAuthInfo appAuth = (AppAuthInfo) iter.next();
              boolean hasPermission = appAuthName.equalsIgnoreCase(appAuth.getAppAuthName());
              hasPermission &= appId == null || appId.equals(appAuth.getAppId());
              hasPermission &= seqNo == null || seqNo.equals(appAuth.getSeqNo());
              if (hasPermission) {
                  return true;
              }
          }
          return false;
      } else {
          this.permissionsExceptionThrown = true;
          throw new PermissionsUnavailableException("Permissions are not currently available. Please check that " +
                  "portal-services UserService is available and that the PortalSSOFilter is configured with " +
                  "load-permissions=true.");
      }
  }

  /**
   * Returns true if PermissionsUnavailableException was thrown at least once since the last time
   * {@link #setPermissions(Collection)} was called.
   * @return True if PermissionsUnavailableException was thrown.
   */
  public boolean wasPermissionsExceptionThrown() {
      return permissionsExceptionThrown;
  }

  /**
   * Sets the permissions of this PortalUser. The corresponding getter method is intentionally missing because the
   * {@link #hasPermission(String)} method should be used instead.
   * @param appAuthInfos The permissions.
   */
  public void setPermissions(final Collection<AppAuthInfo> appAuthInfos) {
    this.permissions = appAuthInfos;
    this.permissionsExceptionThrown = false;
  }

  /**
   * Performs a shallow copy of the other PortalUser object's permissions. This was needed because the PortalSSOFilter
   * creates a new PortalUser instance for each request, but we want to avoid loading the permissions from the
   * {@link org.aurora.portal_services.services.UserService} each time.
   * @param anotherUser The other PortalUser object.
   */
  public void copyPermissions(final PortalUser anotherUser) {
      this.permissions = anotherUser.permissions;
  }

  /**
   * @param s a string
   * @return true if the string is null or only whitespace
   */
  private static boolean empty(String s) {
    return s == null || s.trim().length() == 0;
  }
}
