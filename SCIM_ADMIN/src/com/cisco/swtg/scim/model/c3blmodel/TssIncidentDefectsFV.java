package com.cisco.swtg.scim.model.c3blmodel;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="XXCTSBLRPTG_O.TSS_INCIDENT_DEFECTS_F_V")
public class TssIncidentDefectsFV implements Serializable {
	private static final long serialVersionUID = 66726879266608L;

	private int blIncidentDefectKey;
	private int incidentId;
	
	private String incidentNumber;
	
	private TssDefectsDV tssDefectsDV;
	
	@Id
	@Column(name="BL_INCIDENT_DEFECT_KEY")
	public int getBlIncidentDefectKey() { return blIncidentDefectKey; }
	public void setBlIncidentDefectKey(int blIncidentDefectKey) { this.blIncidentDefectKey = blIncidentDefectKey; }
	
	@Column(name="INCIDENT_ID")
	public int getIncidentId() { return incidentId; }
	public void setIncidentId(int incidentId) { this.incidentId = incidentId; }
	
	@Column(name="INCIDENT_NUMBER")
	public String getIncidentNumber() { return incidentNumber; }
	public void setIncidentNumber(String incidentNumber) { this.incidentNumber = incidentNumber; }
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="BL_DEFECT_KEY", referencedColumnName = "BL_DEFECT_KEY")
	public TssDefectsDV getTssDefectsDV() { return tssDefectsDV; }
	public void setTssDefectsDV(TssDefectsDV tssDefectsDV) { this.tssDefectsDV = tssDefectsDV; }

}
