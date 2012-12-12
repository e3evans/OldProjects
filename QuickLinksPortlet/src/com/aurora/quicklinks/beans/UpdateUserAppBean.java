package com.aurora.quicklinks.beans;

public class UpdateUserAppBean {
	private String appId;
	private String userid;
	private String activeCd;
	public String getAppId() {
		return appId;
	}
	public String getActiveCd() {
		return activeCd;
	}
	public void setActiveCd(String activeCd) {
		this.activeCd = activeCd;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}

}
