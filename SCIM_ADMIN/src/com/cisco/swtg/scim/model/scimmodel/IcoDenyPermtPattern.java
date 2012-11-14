package com.cisco.swtg.scim.model.scimmodel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ICO_DENY_PERMIT_PATTERN")
public class IcoDenyPermtPattern implements Serializable {
	private static final long serialVersionUID = 690726879666208L;
	
//	private int urlDenyPermtPatternId;
	private int icoDenyPermtPatternId;
	
	private String icoPattern;
	private String isRegex;
	private String isPermit;
	private String icoType;
	
	@Id
	@Column(name="ICO_DENY_PERMT_PATTERN_ID")
	public int getIcoDenyPermtPatternId() { return icoDenyPermtPatternId; }
	public void setIcoDenyPermtPatternId(int icoDenyPermtPatternId) { this.icoDenyPermtPatternId = icoDenyPermtPatternId; }
	
/*	@Id
	@Column(name="ICO_DENY_PERMT_PATTERN_ID")
	public int getUrlDenyPermtPatternId() { return urlDenyPermtPatternId; }
	public void setUrlDenyPermtPatternId(int urlDenyPermtPatternId) { this.urlDenyPermtPatternId = urlDenyPermtPatternId; }*/
	
	@Column(name="ICO_PATTERN")
	public String getIcoPattern() { return icoPattern; }
	public void setIcoPattern(String icoPattern) { this.icoPattern = icoPattern; }
	
	@Column(name="ISREGEX")
	public String getIsRegex() { return isRegex; }
	public void setIsRegex(String isRegex) { this.isRegex = isRegex; }
	
	@Column(name="ISPERMIT")
	public String getIsPermit() { return isPermit; }
	public void setIsPermit(String isPermit) { this.isPermit = isPermit; }
	
	@Column(name="ICO_TYPE")
	public String getIcoType() { return icoType; }
	public void setIcoType(String icoType) { this.icoType = icoType; }
}
