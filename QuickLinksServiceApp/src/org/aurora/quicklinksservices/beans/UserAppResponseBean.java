package org.aurora.quicklinksservices.beans;

import java.io.Serializable;
import java.util.Comparator;

import org.apache.commons.lang.builder.ToStringBuilder;

public class UserAppResponseBean implements Serializable {

	private static final long serialVersionUID = 3833409868159234106L;

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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	/**
	 * Comparator used to sort by app name
	 */
	public final static Comparator<UserAppResponseBean> APP_COMPARATOR = new AppComparator();

	public static class AppComparator implements
			Comparator<UserAppResponseBean>, Serializable {

		private static final long serialVersionUID = 4783983219368967080L;

		public int compare(UserAppResponseBean a, UserAppResponseBean b) {
			return a.getAppName().toLowerCase().compareTo(b.getAppName().toLowerCase());
		}
	}
}