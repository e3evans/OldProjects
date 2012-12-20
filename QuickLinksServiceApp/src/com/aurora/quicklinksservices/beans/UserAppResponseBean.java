package com.aurora.quicklinksservices.beans;

public class UserAppResponseBean {
	
	private String userId;
	private String appName;
	private String appUrl;
	private String appId;
	private String activeCd;
	private Integer dispSeq;
	private String seqNo;
	private String flagDefault;
	
	public String getFlagDefault() {
		return flagDefault;
	}
	public void setFlagDefault(String flagDefault) {
		this.flagDefault = flagDefault;
	}
	public Integer getDispSeq() {
		return dispSeq;
	}
	public void setDispSeq(Integer dispSeq) {
		this.dispSeq = dispSeq;
	}
	public String getActiveCd() {
		return activeCd;
	}
	public void setActiveCd(String activeCd) {
		this.activeCd = activeCd;
	}
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
