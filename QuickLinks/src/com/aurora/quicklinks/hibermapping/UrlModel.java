package com.aurora.quicklinks.hibermapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="urldetail")
public class UrlModel {
	@Id
	@Column(name="id")
	private String id;
	
	
	@Column(name="url")
	private String url;
	
	@Column(name="userid")
	private String userid;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUserid() {
		return userid;
	}

	

}
