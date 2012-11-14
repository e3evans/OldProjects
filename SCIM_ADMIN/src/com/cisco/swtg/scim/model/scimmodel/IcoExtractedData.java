package com.cisco.swtg.scim.model.scimmodel;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="C3_EXTRACTED_ICO")
@SequenceGenerator(name="SEQ_STORE", sequenceName="auto_inc")
public class IcoExtractedData  implements Serializable {
	private static final long serialVersionUID = 6875679266208L;
	
	private int c3IcoId;
	private int techId;  
	private int subTechId; 
	private int caseNoteId;
	private Integer workgroupId;
	
	private String source;
	private String c3CaseId;
	private String c3CaseTitle;
	private String csoneCaseId;
	private String techIdName;
	private String subTechIdName;
	private String contact;
	private String contractId;
	private String caseNoteType;
	private String tacEngineer;
	private String c3WorkgroupTacEngineer;
	private String icoType;
	private String icoValue;
	private String icoTitle;
	private String icoUrlServerName;
	private String icoUrlDomain;
	private String icoUrlAccessibility;
	private String neoNodeId;
	private String serialNumber;
	
	private Date caseNoteDate;
	
	
	@Id 
//	@GeneratedValue(strategy=GenerationType.TABLE, generator="SEQ_STORE")
	@Column(name="C3_ICO_ID")
	public int getC3IcoId() { return c3IcoId; }
	public void setC3IcoId(int c3IcoId) { this.c3IcoId = c3IcoId; }

	@Column(name="ICO_VALUE")
	public String getIcoValue() { return icoValue; }
	public void setIcoValue(String icoValue) { this.icoValue = icoValue; }
	
	@Column(name="TECHID")
	public int getTechId() { return techId; }
	public void setTechId(int techId) { this.techId = techId; }

	@Column(name="SUBTECHID")
	public int getSubTechId() { return subTechId; }
	public void setSubTechId(int subTechId) { this.subTechId = subTechId; }

	@Column(name="CASE_NOTE_ID")
	public int getCaseNoteId() { return caseNoteId; }
	public void setCaseNoteId(int caseNoteId) { this.caseNoteId = caseNoteId; }

	@Column(name="SOURCE")
	public String getSource() { return source;}
	public void setSource(String source) { this.source = source; }
	
	@Column(name="C3_CASE_ID")
	public String getC3CaseId() { return c3CaseId; }
	public void setC3CaseId(String c3CaseId) { this.c3CaseId = c3CaseId; }

	@Column(name="C3_CASE_TITLE")
	public String getC3CaseTitle() { return c3CaseTitle; }
	public void setC3CaseTitle(String c3CaseTitle) { this.c3CaseTitle = c3CaseTitle; }

	@Column(name="CSONE_CaseID")
	public String getCsoneCaseId() { return csoneCaseId; }
	public void setCsoneCaseId(String csoneCaseId) { this.csoneCaseId = csoneCaseId; }

	@Column(name="TECHID_NAME")
	public String getTechIdName() { return techIdName; }
	public void setTechIdName(String techIdName) { this.techIdName = techIdName; }

	@Column(name="SUBTECHID_NAME")
	public String getSubTechIdName() { return subTechIdName; }
	public void setSubTechIdName(String subTechIdName) { this.subTechIdName = subTechIdName; }

	@Column(name="CONTACT")
	public String getContact() { return contact; }
	public void setContact(String contact) { this.contact = contact; }

	@Column(name="CONTRACT_ID")
	public String getContractId() { return contractId; }
	public void setContractId(String contractId) { this.contractId = contractId; }

	@Column(name="CASE_NOTE_TYPE")
	public String getCaseNoteType() { return caseNoteType; }
	public void setCaseNoteType(String caseNoteType) { this.caseNoteType = caseNoteType; }

	@Column(name="TAC_ENGINEER")
	public String getTacEngineer() { return tacEngineer; }
	public void setTacEngineer(String tacEngineer) { this.tacEngineer = tacEngineer; }

	@Column(name="C3_WORKGROUP_TAC_ENGINEER")
	public String getC3WorkgroupTacEngineer() { return c3WorkgroupTacEngineer; }
	public void setC3WorkgroupTacEngineer(String c3WorkgroupTacEngineer) { this.c3WorkgroupTacEngineer = c3WorkgroupTacEngineer; }

	@Column(name="ICO_TYPE")
	public String getIcoType() { return icoType; }
	public void setIcoType(String icoType) { this.icoType = icoType; }

	@Column(name="ICO_TITLE")
	public String getIcoTitle() { return icoTitle; }
	public void setIcoTitle(String icoTitle) { this.icoTitle = icoTitle; }

	@Column(name="ICO_URL_SERVER_NAME")
	public String getIcoUrlServerName() { return icoUrlServerName; }
	public void setIcoUrlServerName(String icoUrlServerName) { this.icoUrlServerName = icoUrlServerName; }
	
	@Column(name="ICO_URL_DOMAIN")
	public String getIcoUrlDomain() { return icoUrlDomain; }
	public void setIcoUrlDomain(String icoUrlDomain) { this.icoUrlDomain = icoUrlDomain; }

	@Column(name="ICO_URL_ACCESSIBILITY")
	public String getIcoUrlAccessibility() { return icoUrlAccessibility; }
	public void setIcoUrlAccessibility(String icoUrlAccessibility) { this.icoUrlAccessibility = icoUrlAccessibility; }

	@Column(name="NEO_NODE_ID")
	public String getNeoNodeId() { return neoNodeId; }
	public void setNeoNodeId(String neoNodeId) { this.neoNodeId = neoNodeId; }

	@Column(name="CASE_NOTE_DATE")
	public Date getCaseNoteDate() { return caseNoteDate; }
	public void setCaseNoteDate(Date caseNoteDate) { this.caseNoteDate = caseNoteDate; }
	
	@Column(name="SERIAL_NUMBER")
	public String getSerialNumber() { return serialNumber; }
	public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }
	
	@Column(name="WORKGROUP_ID")
	public Integer getWorkgroupId() { return workgroupId; }
	public void setWorkgroupId(Integer workgroupId) { this.workgroupId = workgroupId; }
	
}
