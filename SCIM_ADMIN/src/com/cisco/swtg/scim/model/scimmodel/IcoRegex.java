package com.cisco.swtg.scim.model.scimmodel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ICO_REGEX")
public class IcoRegex implements Serializable {
	private static final long serialVersionUID = 62726879266208L;
		
	private int icoRegexId;
	
	private String icoRegex;
	private String icoType;
	private String isRegex;
	private String caseSensitive;
	
	@Id
	@Column(name="ICO_REGEX_ID")
	public int getIcoRegexId() { return icoRegexId; }
	public void setIcoRegexId(int icoRegexId) { this.icoRegexId = icoRegexId; }

	@Column(name="ICO_REGEX")
	public String getIcoRegex() { return icoRegex; }
	public void setIcoRegex(String icoRegex) { this.icoRegex = icoRegex; }
	
	@Column(name="ICO_TYPE")
	public String getIcoType() { return icoType; }
	public void setIcoType(String icoType) { this.icoType = icoType; }
	
	@Column(name="IS_REGEX")
	public String getIsRegex() { return isRegex; }
	public void setIsRegex(String isRegex) { this.isRegex = isRegex; }
	
	@Column(name="CASE_SENSITIVE")
	public String getCaseSensitive() { return caseSensitive; }	
	public void setCaseSensitive(String caseSensitive) { this.caseSensitive = caseSensitive; }
	
}
