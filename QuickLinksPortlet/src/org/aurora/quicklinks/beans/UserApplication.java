package org.aurora.quicklinks.beans;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class UserApplication implements Serializable {

	private static final long serialVersionUID = -4854297425895580313L;

	private String appName;
	private String userid;
	private String appDesc;
	private String appURL;
	private String appId;
	private String seqNo;
	private String activeCd;
	private String flagDefault;
	private Integer dispSeq;
	private boolean checked;

	// TODO: remove if not used
	public static final String ACTIVE = "A";
	public static final String INACTIVE = "I";
	public static final Integer NOTDISPLAYED = new Integer(0);

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
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

	public String getActiveCd() {
		return activeCd;
	}

	public void setActiveCd(String activeCd) {
		this.activeCd = activeCd;
	}

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

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isActive() {
		return this.activeCd.equalsIgnoreCase("A");
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}