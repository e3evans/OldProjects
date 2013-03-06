package org.aurora.quicklinksservices.beans;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "TPT2Z_APP_CATEGORY")
public class AppCategory implements Serializable {

	private static final long serialVersionUID = -109597943809595762L;

	@Id
	@Column(name = "PT2Z_APP_CATEGORY_ID")
	private Integer appCategoryId;

	@Column(name = "PT2Z_APP_CATEGORY_NAME")
	@Type(type = "org.aurora.spring.dao.hibernate.StringTrimUserType")
	private String appCategoryName;

	@Column(name = "PT2Z_ACTIVE_CD")
	@Type(type = "org.aurora.spring.dao.hibernate.StringTrimUserType")
	private String activeCD;

	@Column(name = "PT2Z_UPDATED_TMSP")
	private Timestamp updatedTimeStamp;

	public Integer getAppCategoryId() {
		return appCategoryId;
	}

	public void setAppCategoryId(Integer appCategoryId) {
		this.appCategoryId = appCategoryId;
	}

	public String getAppCategoryName() {
		return appCategoryName;
	}

	public void setAppCategoryName(String appCategoryName) {
		this.appCategoryName = appCategoryName;
	}

	public String getActiveCD() {
		return activeCD;
	}

	public void setActiveCD(String activeCD) {
		this.activeCD = activeCD;
	}

	public Timestamp getUpdatedTimeStamp() {
		return updatedTimeStamp;
	}

	public void setUpdatedTimeStamp(Timestamp updatedTimeStamp) {
		this.updatedTimeStamp = updatedTimeStamp;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}