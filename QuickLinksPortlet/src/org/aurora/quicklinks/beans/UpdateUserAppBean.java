package org.aurora.quicklinks.beans;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class UpdateUserAppBean implements Serializable {

	private static final long serialVersionUID = 1439523527169756640L;

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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}