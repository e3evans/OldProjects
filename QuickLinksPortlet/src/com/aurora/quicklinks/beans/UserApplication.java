package com.aurora.quicklinks.beans;

public class UserApplication {
	private String appName;
	
	private String userid;

	private String appDesc;

	private String appURL;
	
	private String appId;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	private boolean checked;
	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppDesc() {
		return appDesc;
	}

	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
	}

	public String getAppURL() {
		return appURL;
	}

	public void setAppURL(String appURL) {
		this.appURL = appURL;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "user app: " + "appName :" +appName+ "app Description :" + appDesc;
	}

}
