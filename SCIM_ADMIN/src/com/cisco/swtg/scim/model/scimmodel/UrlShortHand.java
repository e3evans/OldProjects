package com.cisco.swtg.scim.model.scimmodel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="URL_SHORTHAND")
public class UrlShortHand implements Serializable {
	private static final long serialVersionUID = 62729266208L;
	
	private int shortHandId;
	private String shortHandServerName;
	private String qualifiedServerName;
	
	@Id
	@Column(name="SHORTHAND_ID")
	public int getShortHandId() { return shortHandId; }
	public void setShortHandId(int shortHandId) { this.shortHandId = shortHandId; }
	
	@Column(name="SHORTHAND_SERVERNAME")
	public String getShortHandServerName() { return shortHandServerName; }
	public void setShortHandServerName(String shortHandServerName) { this.shortHandServerName = shortHandServerName; }
	
	@Column(name="QUALIFIED_SERVERNAME")
	public String getQualifiedServerName() { return qualifiedServerName; }
	public void setQualifiedServerName(String qualifiedServerName) { this.qualifiedServerName = qualifiedServerName; }
	
}
