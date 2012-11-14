package com.cisco.swtg.scim.model.c3blmodel;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="XXCTSBLRPTG_O.TSS_INCIDENTS_CURRENT_F_V")
public class TssIncidentsCurrentFV implements Serializable {
	private static final long serialVersionUID = 690726879266208L;

	private int incidentId;
	private int swVersionId;
	
	private String incidentnumber;
	private String summary;
	private String currentOwnerCCOID;
	private String incidentStatus;
	private String externalCaseNumber;
	private String problemCode;
	private String currentSerialNumber;
		
	private Date creationDate;
	private Date closedDate;
	
	private TssCotTechnolotiesDV tssCotTechnolotiesDV;
	private TssTacProductsDV tssTacProductsDV;
	private TssDefectsDV tssDefectsDV;
	private SrCurrContXxctsCrwsdmUV srCurrContXxctsCrwsdmUV;

	//private List<TssIncidentNotesFV> tssIncidentNotesFVs;
	private Set<TssIncidentLinksFV> tssIncidentLinksFVs;
	private Set<TssIncidentDefectsFV> tssIncidentDefectsFVs;
	private SrContactXxctsCrwsdmUV srContactXxctsCrwsdmUV;

	@Id
	@Column(name="INCIDENT_ID")
	public int getIncidentId() { return incidentId; }
	public void setIncidentId(int incidentId) { this.incidentId = incidentId; }
	
	@Column(name="INCIDENT_NUMBER")
	public String getIncidentnumber() { return incidentnumber; }
	public void setIncidentnumber(String incidentnumber) { this.incidentnumber = incidentnumber; }
	
	@Column(name="SUMMARY")
	public String getSummary() { return summary; }	
	public void setSummary(String summary) { this.summary = summary; }
	
	@Column(name="CURRENT_OWNER_CCO_ID")
	public String getCurrentOwnerCCOID() { return currentOwnerCCOID; }
	public void setCurrentOwnerCCOID(String currentOwnerCCOID) { this.currentOwnerCCOID = currentOwnerCCOID; }	
	
	@Column(name="INCIDENT_STATUS")
	public String getIncidentStatus() { return incidentStatus; }
	public void setIncidentStatus(String incidentStatus) { this.incidentStatus = incidentStatus; }
		
	@Column(name="CREATION_DATE")
	public Date getCreationDate() { return creationDate; }
	public void setCreationDate(Date creationDate) { this.creationDate = creationDate; }
	
	@Column(name="CLOSED_DATE")
	public Date getClosedDate() { return closedDate; }
	public void setClosedDate(Date closedDate) { this.closedDate = closedDate; }
	
	@Column(name="EXTERNAL_CASE_NUMBER")
	public String getExternalCaseNumber() { return externalCaseNumber; }
	public void setExternalCaseNumber(String externalCaseNumber) { this.externalCaseNumber = externalCaseNumber; }
	
	@Column(name="PROBLEM_CODE")
	public String getProblemCode() { return problemCode; }
	public void setProblemCode(String problemCode) { this.problemCode = problemCode; }
	
	@Column(name="CURRENT_SERIAL_NUMBER")
	public String getCurrentSerialNumber() { return currentSerialNumber; }
	public void setCurrentSerialNumber(String currentSerialNumber) { this.currentSerialNumber = currentSerialNumber; }
	
	@Column(name="SW_VERSION_ID")
	public int getSwVersionId() { return swVersionId; }
	public void setSwVersionId(int swVersionId) { this.swVersionId = swVersionId; }
	
/*	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="SOURCE_OBJECT_ID")
	public List<TssIncidentNotesFV> getTssIncidentNotesFVs() { return tssIncidentNotesFVs; }
	public void setTssIncidentNotesFVs(List<TssIncidentNotesFV> tssIncidentNotesFVs) { this.tssIncidentNotesFVs = tssIncidentNotesFVs;}*/

	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="BL_CONTACT_KEY", referencedColumnName = "BL_CONTACT_KEY")	
	public SrContactXxctsCrwsdmUV getSrContactXxctsCrwsdmUV() { return srContactXxctsCrwsdmUV; }
	public void setSrContactXxctsCrwsdmUV( SrContactXxctsCrwsdmUV srContactXxctsCrwsdmUV) {	this.srContactXxctsCrwsdmUV = srContactXxctsCrwsdmUV; }
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="UPDATED_COT_TECH_KEY", referencedColumnName = "BL_COT_TECHNOLOGY_KEY")
	public TssCotTechnolotiesDV getTssCotTechnolotiesDV() { return tssCotTechnolotiesDV; }
	public void setTssCotTechnolotiesDV(TssCotTechnolotiesDV tssCotTechnolotiesDV) { this.tssCotTechnolotiesDV = tssCotTechnolotiesDV; }
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="TAC_PRODUCT_HW_KEY", referencedColumnName = "BL_TAC_PRODUCT_KEY")
	public TssTacProductsDV getTssTacProductsDV() { return tssTacProductsDV; }
	public void setTssTacProductsDV(TssTacProductsDV tssTacProductsDV) { this.tssTacProductsDV = tssTacProductsDV; }
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="PROBLEM_KEY", referencedColumnName = "BL_DEFECT_KEY")
	public TssDefectsDV getTssDefectsDV() { return tssDefectsDV; }
	public void setTssDefectsDV(TssDefectsDV tssDefectsDV) { this.tssDefectsDV = tssDefectsDV; }
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="CURRENT_CONTRACT_KEY", referencedColumnName = "BL_CONTRACT_KEY")
	public SrCurrContXxctsCrwsdmUV getSrCurrContXxctsCrwsdmUV() { return srCurrContXxctsCrwsdmUV; }
	public void setSrCurrContXxctsCrwsdmUV( SrCurrContXxctsCrwsdmUV srCurrContXxctsCrwsdmUV) { this.srCurrContXxctsCrwsdmUV = srCurrContXxctsCrwsdmUV; }
		
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="FROM_INCIDENT_ID")
	public Set<TssIncidentLinksFV> getTssIncidentLinksFVs() { return tssIncidentLinksFVs; }
	public void setTssIncidentLinksFVs(Set<TssIncidentLinksFV> tssIncidentLinksFVs) { this.tssIncidentLinksFVs = tssIncidentLinksFVs; }
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="INCIDENT_ID")
	public Set<TssIncidentDefectsFV> getTssIncidentDefectsFVs() { return tssIncidentDefectsFVs; }
	public void setTssIncidentDefectsFVs( Set<TssIncidentDefectsFV> tssIncidentDefectsFVs) { this.tssIncidentDefectsFVs = tssIncidentDefectsFVs; }
	
}
