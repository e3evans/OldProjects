package com.cisco.swtg.scim.model.c3blmodel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="XXCTSBLRPTG_O.TSS_INCIDENT_LINKS_F_V")
public class TssIncidentLinksFV implements Serializable {
	private static final long serialVersionUID = 690726879266608L;

	private int blLinkIdKey;
	private int fromIncidentId;
	private int toIncidentId;
	
	private String toIncidentNumber;

	@Id
	@Column(name="BL_LINK_ID_KEY")
	public int getBlLinkIdKey() { return blLinkIdKey; }
	public void setBlLinkIdKey(int blLinkIdKey) { this.blLinkIdKey = blLinkIdKey; }
	
	@Column(name="FROM_INCIDENT_ID")
	public int getFromIncidentId() { return fromIncidentId; }
	public void setFromIncidentId(int fromIncidentId) { this.fromIncidentId = fromIncidentId; }
	
	@Column(name="TO_INCIDENT_ID")
	public int getToIncidentId() { return toIncidentId; }
	public void setToIncidentId(int toIncidentId) { this.toIncidentId = toIncidentId; }
	
	@Column(name="TO_INCIDENT_NUMBER")
	public String getToIncidentNumber() { return toIncidentNumber; }
	public void setToIncidentNumber(String toIncidentNumber) { this.toIncidentNumber = toIncidentNumber; }
	
}
