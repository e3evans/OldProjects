package com.cisco.swtg.scim.model.scimmodel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="LINKED_SERVICE_REQUEST")
public class LinkedServiceRequest implements Serializable {
	private static final long serialVersionUID = 690725879266208L;

	private int linkId;
	private String caseId;
	private String toCaseId;

	@Id
	@Column(name="LINK_ID")
	public int getLinkId() { return linkId; }
	public void setLinkId(int linkId) { this.linkId = linkId; }
	
	@Column(name="CASE_ID")
	public String getCaseId() { return caseId; }
	public void setCaseId(String caseId) { this.caseId = caseId; }
	
	@Column(name="TO_CASE_ID")
	public String getToCaseId() { return toCaseId; }
	public void setToCaseId(String toCaseId) { this.toCaseId = toCaseId; }
	
}
