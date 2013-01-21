package com.aurora.org.caregiverlogin.forms;

import java.util.HashMap;
import java.util.Map;

import com.aurora.controllers.LoginViewController;

public class LoginEditForm {
	private String wcm_path;
	private String wcm_WebAppPath;
	private String wcm_servletPath;
	private String wcm_menuComponent;
	
	public String getWcm_path() {
		return wcm_path;
	}
	public void setWcm_path(String wcm_path) {
		this.wcm_path = wcm_path;
	}
	public String getWcm_WebAppPath() {
		return wcm_WebAppPath;
	}
	public void setWcm_WebAppPath(String wcm_WebAppPath) {
		this.wcm_WebAppPath = wcm_WebAppPath;
	}
	public String getWcm_servletPath() {
		return wcm_servletPath;
	}
	public void setWcm_servletPath(String wcm_servletPath) {
		this.wcm_servletPath = wcm_servletPath;
	}
	public String getWcm_menuComponent() {
		return wcm_menuComponent;
	}
	public void setWcm_menuComponent(String wcm_menuComponent) {
		this.wcm_menuComponent = wcm_menuComponent;
	}

	public Map<String,String> getPreferences(){
		Map<String,String> prefs = new HashMap<String, String>();
		prefs.put(LoginViewController.PREF_WCM_COMPONENT, getWcm_menuComponent());
		prefs.put(LoginViewController.PREF_WCM_PATH, getWcm_path());
		prefs.put(LoginViewController.PREF_WCM_SERVLET, getWcm_servletPath());
		prefs.put(LoginViewController.PREF_WCM_WEBAPP, getWcm_WebAppPath());
		return prefs;
	}
	
}
