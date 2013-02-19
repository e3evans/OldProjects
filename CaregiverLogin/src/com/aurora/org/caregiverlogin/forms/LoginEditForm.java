package com.aurora.org.caregiverlogin.forms;

import java.util.HashMap;
import java.util.Map;

import com.aurora.controllers.LoginViewController;

public class LoginEditForm {
	private String wcm_path;
	private String wcm_library;
	private String wcm_menuComponent;
	private String cookie_env;
	
	public String getWcm_path() {
		return wcm_path;
	}
	public void setWcm_path(String wcm_path) {
		this.wcm_path = wcm_path;
	}

	public String getWcm_menuComponent() {
		return wcm_menuComponent;
	}
	public void setWcm_menuComponent(String wcm_menuComponent) {
		this.wcm_menuComponent = wcm_menuComponent;
	}

	public String getWcm_library() {
		return wcm_library;
	}
	public void setWcm_library(String wcm_library) {
		this.wcm_library = wcm_library;
	}
	public Map<String,String> getPreferences(){
		Map<String,String> prefs = new HashMap<String, String>();
		prefs.put(LoginViewController.PREF_WCM_COMPONENT, getWcm_menuComponent());
		prefs.put(LoginViewController.PREF_WCM_PATH, getWcm_path());
		prefs.put(LoginViewController.PRED_WCM_LIB, getWcm_library());
		prefs.put(LoginViewController.PREF_COOKIE_ENV, getCookie_env());
		return prefs;
	}
	public String getCookie_env() {
		return cookie_env;
	}
	public void setCookie_env(String cookie_env) {
		this.cookie_env = cookie_env;
	}
	
}
