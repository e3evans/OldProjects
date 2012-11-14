package com.cisco.swtg.scim.model.scimmodel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="LINKED_BUGS")
public class LinkedBugs implements Serializable {
	private static final long serialVersionUID = 69072587986628L;

	private int blDefectKey;
	private String caseId;
	private String bug;
	
	@Id
	@Column(name="BL_DEFECT_KEY")
	public int getBlDefectKey() { return blDefectKey; }
	public void setBlDefectKey(int blDefectKey) { this.blDefectKey = blDefectKey; }
	
	@Column(name="CASE_ID")
	public String getCaseId() { return caseId; }
	public void setCaseId(String caseId) { this.caseId = caseId; }
	
	@Column(name="BUG")
	public String getBug() { return bug; }
	public void setBug(String bug) { this.bug = bug; }
}
