package org.aurora.quicklinks.beans;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class FeaturedAppForm implements Serializable {

	private static final long serialVersionUID = -2700249616676280536L;
	
	private String appName;
	private String appDesc;
	private String seqNo;
	private String appId;
	private String appCategory;

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

	public String getAppCategory() {
		return appCategory;
	}

	public void setAppCategory(String appCategory) {
		this.appCategory = appCategory;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}