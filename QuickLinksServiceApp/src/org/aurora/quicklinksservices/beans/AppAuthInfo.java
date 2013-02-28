package org.aurora.quicklinksservices.beans;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * A lightweight transfer representation of the AppAuth object. This object is
 * primarily used in web service calls.
 */
public class AppAuthInfo implements Serializable {

	private static final long serialVersionUID = 6923287113651357853L;

	private Integer appAuthId;
	private String appAuthName;
	private String appId;
	private Integer seqNo;

	public AppAuthInfo() {
	}

	public AppAuthInfo(Integer appAuthId, String appAuthName, String appId,
			Integer seqNo) {
		this.appAuthId = appAuthId;
		this.appAuthName = appAuthName;
		this.appId = appId;
		this.seqNo = seqNo;
	}

	public Integer getAppAuthId() {
		return appAuthId;
	}

	public void setAppAuthId(Integer appAuthId) {
		this.appAuthId = appAuthId;
	}

	public String getAppAuthName() {
		return appAuthName;
	}

	public void setAppAuthName(String appAuthName) {
		this.appAuthName = appAuthName;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Integer getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}