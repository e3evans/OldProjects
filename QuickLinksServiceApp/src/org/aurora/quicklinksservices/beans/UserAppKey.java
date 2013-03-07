package org.aurora.quicklinksservices.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Type;

@Embeddable
public class UserAppKey implements Serializable {

	private static final long serialVersionUID = -1263942178955855089L;

	@Column(name = "PT2J_USERID")
	private Long userId;

	@Column(name = "PT2J_APPID")
	@Type(type = "org.aurora.spring.dao.hibernate.StringTrimUserType")
	private String appId;

	@Column(name = "PT2J_SEQ_NO")
	private Integer seqNo;

	public UserAppKey() {
	}

	public UserAppKey(Long userId, String appId, Integer seqNo) {
		this.userId = userId;
		this.appId = appId;
		this.seqNo = seqNo;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAppId() {
		return this.appId.trim();
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Integer getSeqNo() {
		return this.seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if ((o == null) || (getClass() != o.getClass()))
			return false;

		UserAppKey that = (UserAppKey) o;

		if (!this.getAppId().equals(that.getAppId()))
			return false;
		if (!this.seqNo.equals(that.seqNo))
			return false;
		if (!this.userId.equals(that.userId))
			return false;

		return true;
	}

	public int hashCode() {
		int result = this.userId.hashCode();
		result = 29 * result + this.getAppId().hashCode();
		result = 29 * result + this.seqNo.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}