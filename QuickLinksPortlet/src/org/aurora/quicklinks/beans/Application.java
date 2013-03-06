package org.aurora.quicklinks.beans;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Application implements Serializable {

	private static final long serialVersionUID = -6210124706832456836L;

	private String appName;
	private String appDesc;
	private String seqNo;
	private String appId;
	private String appURL;
	private boolean checked;
	private String loggedInAccess;

	public String getLoggedInAccess() {
		return loggedInAccess;
	}

	public void setLoggedInAccess(String loggedInAccess) {
		this.loggedInAccess = loggedInAccess;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
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
		return ToStringBuilder.reflectionToString(this);
	}
}