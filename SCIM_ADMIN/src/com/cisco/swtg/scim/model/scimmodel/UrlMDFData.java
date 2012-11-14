package com.cisco.swtg.scim.model.scimmodel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="URL_MDF")
public class UrlMDFData {
	
		private static final long serialVersionUID = 69072587986628L;
		
		private int mdfId;
		private String url;
		private String mdf;
		
		@Id
		@Column(name="MDF_ID")
		public int getMdfId() {	return mdfId;	}
		public void setMdfId(int mdfId) {	this.mdfId = mdfId;	}
		
		
		@Column(name="URL")
		public String getUrl() {	return url;	}
		public void setUrl(String url) {	this.url = url;	}
		
		@Column(name="MDF_CONCEPT_NAME")
		public String getMdf() {	return mdf;	}
		public void setMdf(String mdf) {	this.mdf = mdf;	}
}
