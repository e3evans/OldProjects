package com.cisco.swtg.scim.model.scimmodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="C3_RAWDATA")
public class C3RawData implements Serializable {
	private static final long serialVersionUID = 690726879286208L;

	private String caseId;
	
	private int caseNoteId;
	private int techId;
	private int subTechId;
	private int linkedServiceRequest;
	private int workgroupId;
	
	private String srCaseNotesText;
	private String title;
	private String contact;
	private String srCaseNoteType;
	private String srCaseNoteAuthor;
	private String srCaseNoteWorkgroup;
	private String techName;
	private String subTechName;
	private String problemCode;
	private String hardwareDetails;
	private String bugs;
	private String csOneId;
	private String contractId;
	private String swDetails;
	private String serialNumber;
	
	private Date srCaseNoteTimestamp;
	private Date creationDate;
	
	private Set<LinkedServiceRequest> linkedServiceRequests;
	private List<LinkedBugs> linkedBugs;
	
	@Id
	@Column(name="CASE_NOTE_ID")
	public int getCaseNoteId() { return caseNoteId; }	
	public void setCaseNoteId(int caseNoteId) { this.caseNoteId = caseNoteId; }

	@Column(name="CASE_ID")
	public String getCaseId() { return caseId; }
	public void setCaseId(String caseId) { this.caseId = caseId; }
	
	@Column(name="SR_CASE_NOTE_TEXT")
	public String getSrCaseNotesText() { return srCaseNotesText; }
	public void setSrCaseNotesText(String srCaseNotesText) { this.srCaseNotesText = srCaseNotesText; }
	
	@Column(name="TITLE")
	public String getTitle() { return title; }	
	public void setTitle(String title) { this.title = title; }
	
	@Column(name="CONTACT")
	public String getContact() { return contact; }
	public void setContact(String contact) { this.contact = contact; }
	
	@Column(name="CREATION_DATE")
	public Date getCreationDate() { return creationDate; }
	public void setCreationDate(Date creationDate) { this.creationDate = creationDate; }
		
	@Column(name="TECH_ID")
	public int getTechId() { return techId; }
	public void setTechId(int techId) { this.techId = techId; }
	
	@Column(name="SUB_TECH_ID")
	public int getSubTechId() { return subTechId; }
	public void setSubTechId(int subTechId) { this.subTechId = subTechId; }
	
	@Column(name="SR_CASE_NOTE_TYPE")
	public String getSrCaseNoteType() { return srCaseNoteType; }	
	public void setSrCaseNoteType(String srCaseNoteType) { this.srCaseNoteType = srCaseNoteType; }
	
	@Column(name="SR_CASE_NOTE_TIMESTAMP")
	public Date getSrCaseNoteTimestamp() { return srCaseNoteTimestamp; }
	public void setSrCaseNoteTimestamp(Date srCaseNoteTimestamp) { this.srCaseNoteTimestamp = srCaseNoteTimestamp; }	
		
	@Column(name="SR_CASE_NOTE_AUTHOR")
	public String getSrCaseNoteAuthor() { return srCaseNoteAuthor; }
	public void setSrCaseNoteAuthor(String srCaseNoteAuthor) { this.srCaseNoteAuthor = srCaseNoteAuthor; }
	
	@Column(name="SR_CASE_NOTE_WORKGROUP")
	public String getSrCaseNoteWorkgroup() { return srCaseNoteWorkgroup; }
	public void setSrCaseNoteWorkgroup(String srCaseNoteWorkgroup) { this.srCaseNoteWorkgroup = srCaseNoteWorkgroup; }
	
	@Column(name="WORKGROUP_ID")
	public int getWorkgroupId() { return workgroupId; }
	public void setWorkgroupId(int workgroupId) { this.workgroupId = workgroupId; }
	
	@Column(name="TECH_NAME")
	public String getTechName() { return techName; }
	public void setTechName(String techName) { this.techName = techName; }
	
	@Column(name="SUB_TECH_NAME")
	public String getSubTechName() { return subTechName; }
	public void setSubTechName(String subTechName) { this.subTechName = subTechName; }
	
	@Column(name="PROBLEM_CODE")
	public String getProblemCode() { return problemCode; }
	public void setProblemCode(String problemCode) { this.problemCode = problemCode; }
	
	@Column(name="HARDWARE_DETAILS")
	public String getHardwareDetails() { return hardwareDetails; }
	public void setHardwareDetails(String hardwareDetails) { this.hardwareDetails = hardwareDetails; }
	
	@Column(name="SW_DETAILS")
	public String getSwDetails() { return swDetails; }
	public void setSwDetails(String swDetails) { this.swDetails = swDetails; }
	
	@Column(name="SERIAL_NUMBER")
	public String getSerialNumber() { return serialNumber; }
	public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }
	
	@Column(name="BUGS")
	public String getBugs() { return bugs; }
	public void setBugs(String bugs) { this.bugs = bugs; }
	
	@Column(name="LINKED_SERVICE_REQUEST")
	public int getLinkedServiceRequest() { return linkedServiceRequest; }
	public void setLinkedServiceRequest(int linkedServiceRequest) { this.linkedServiceRequest = linkedServiceRequest; }
	
	@Column(name="CONTRACT_ID")
	public String getContractId() { return contractId; }
	public void setContractId(String contractId) { this.contractId = contractId; }
	
	@Column(name="CSONEID")
	public String getCsOneId() { return csOneId; }	
	public void setCsOneId(String csOneId) { this.csOneId = csOneId; }
	
	@OneToMany(targetEntity = LinkedServiceRequest.class , fetch=FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@JoinColumn(name="CASE_ID", referencedColumnName = "CASE_ID")
	public Set<LinkedServiceRequest> getLinkedServiceRequests() { return linkedServiceRequests; }
	public void setLinkedServiceRequests( Set<LinkedServiceRequest> linkedServiceRequests) { this.linkedServiceRequests = linkedServiceRequests; }

	@OneToMany(targetEntity = LinkedBugs.class , fetch=FetchType.EAGER)
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.EXTRA)
	@JoinColumn(name="CASE_ID", referencedColumnName = "CASE_ID")
	public List<LinkedBugs> getLinkedBugs() { return linkedBugs; }
	public void setLinkedBugs(List<LinkedBugs> linkedBugs) { this.linkedBugs = linkedBugs; }
	
}
