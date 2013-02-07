package com.aurora.sitesapps.util;

import java.util.ResourceBundle;



public class ResourceUtil {
	
	public static String getAvailableapplisturl() {
		return availableapplisturl;
	}


	private static final String BUNDLE_NAME = "com.aurora.sitesapps.util.configuration";
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	private static String environment;
	private static String proxyhosturl;
	private static String categorylisturl;
	private static String applistbycategory;
	private static String popularapplistbycategory;
	private static String availableapplisturl;
	
	
	static{
		environment = RESOURCE_BUNDLE.getString(SitesAppsConstants.ENVIRONMENTKEY);
		proxyhosturl = RESOURCE_BUNDLE.getString(SitesAppsConstants.PROXYHOSTURLKEY+"."+environment);
		categorylisturl=RESOURCE_BUNDLE.getString(SitesAppsConstants.CATEGORYLISTKEY);
		applistbycategory=RESOURCE_BUNDLE.getString(SitesAppsConstants.APPLISTBYCATEGORYKEY);
		popularapplistbycategory=RESOURCE_BUNDLE.getString(SitesAppsConstants.POPULARAPPLISTBYCATEGORYKEY);
		availableapplisturl = RESOURCE_BUNDLE.getString(SitesAppsConstants.AVAILABLEAPPLISTKEY);
	}
	

	public static String getPopularapplistbycategory() {
		return popularapplistbycategory;
	}
	public static String getApplistbycategory() {
		return applistbycategory;
	}
	public static String getCategorylisturl() {
		return categorylisturl;
	}
	public static String getEnvironment() {
		return environment;
	}
	public static String getProxyhosturl() {
		return proxyhosturl;
	}
	
	
	// private constructor
	private ResourceUtil() {
		
	}

}
