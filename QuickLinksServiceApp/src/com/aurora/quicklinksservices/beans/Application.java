package com.aurora.quicklinksservices.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "S05DTDB.TPT2B_APPLICATION")
public class Application {

	@Id
	private AppKey appKey;
	@Column(name = "PT2B_APP_NAME")
	private String appName;
	@Column(name = "PT2B_APP_DESC")
	private String appDesc;
	@Column(name = "PT2B_APP_URL")
	private String appURL;

	public AppKey getAppKey() {
		return appKey;
	}

	public void setAppKey(AppKey appKey) {
		this.appKey = appKey;
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

}
