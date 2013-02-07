package com.aurora.quicklinks.util;

import java.util.ResourceBundle;

public class ResourceUtil {
	
	private static final String BUNDLE_NAME = "com.aurora.quicklinks.util.configuration";
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	private static String environment;
	private static String proxyhosturl;
	private static String userapplisturl;
	private static String alluserapplisturl;
	private static String retrieveuserappurl;
	private static String createuserappurl;
	private static String updateuserappurl;
	private static String appautolisturl;
	private static String availableapplisturl;
	
	
	
	static{
		environment = RESOURCE_BUNDLE.getString(QuickLinksConstants.ENVIRONMENTKEY);
		proxyhosturl = RESOURCE_BUNDLE.getString(QuickLinksConstants.PROXYHOSTURLKEY+"."+environment);
		userapplisturl = RESOURCE_BUNDLE.getString(QuickLinksConstants.USERAPPLISTKEY);
		retrieveuserappurl = RESOURCE_BUNDLE.getString(QuickLinksConstants.RETRIEVEUSERAPPKEY);
		createuserappurl = RESOURCE_BUNDLE.getString(QuickLinksConstants.CREATEUSERAPPKEY);
		updateuserappurl = RESOURCE_BUNDLE.getString(QuickLinksConstants.UPDATERAPPKEY);
		appautolisturl = RESOURCE_BUNDLE.getString(QuickLinksConstants.APPAUTOLISTKEY);
		availableapplisturl = RESOURCE_BUNDLE.getString(QuickLinksConstants.AVAILABLEAPPLISTKEY);
		alluserapplisturl=RESOURCE_BUNDLE.getString(QuickLinksConstants.ALLUSERAPPLISTKEY);
		
	}
	

	public static String getEnvironment() {
		return environment;
	}
	public static String getProxyhosturl() {
		return proxyhosturl;
	}
	public static String getUserapplisturl() {
		return userapplisturl;
	}
	public static String getRetrieveuserappurl() {
		return retrieveuserappurl;
	}
	public static String getCreateuserappurl() {
		return createuserappurl;
	}
	public static String getUpdateuserappurl() {
		return updateuserappurl;
	}
	public static String getAppautolisturl() {
		return appautolisturl;
	}
	public static String getAvailableapplisturl() {
		return availableapplisturl;
	}
	public static String getAlluserapplisturl() {
		return alluserapplisturl;
	}
	public static void setAlluserapplisturl(String alluserapplisturl) {
		ResourceUtil.alluserapplisturl = alluserapplisturl;
	}
	
	// private constructor
	private ResourceUtil() {
		
	}

}
