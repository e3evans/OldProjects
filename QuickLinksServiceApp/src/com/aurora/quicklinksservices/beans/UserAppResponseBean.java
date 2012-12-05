package com.aurora.quicklinksservices.beans;

public class UserAppResponseBean {
	
	private String userId;
	private String appName;
	private String appUrl;
	private String appId;
	public String getAppId() {
	    return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	private String seqNo;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppUrl() {
		return appUrl;
	}
	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

}
