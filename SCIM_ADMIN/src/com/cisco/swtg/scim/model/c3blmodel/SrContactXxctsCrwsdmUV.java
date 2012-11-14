package com.cisco.swtg.scim.model.c3blmodel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SR_CONTACT_XXCTS_CRWSDM_U_V")
public class SrContactXxctsCrwsdmUV implements Serializable {
	private static final long serialVersionUID = 6626879266608L;

	private int blContactKey;
	
	private String contactPartyName;
	private String ccoId;
	
	@Id
	@Column(name="BL_CONTACT_KEY")
	public int getBlContactKey() { return blContactKey; }
	public void setBlContactKey(int blContactKey) { this.blContactKey = blContactKey; }
	
	@Column(name="CONTACT_PARTY_NAME")
	public String getContactPartyName() { return contactPartyName; }
	public void setContactPartyName(String contactPartyName) { this.contactPartyName = contactPartyName; }
	
	@Column(name="CCO_ID")
	public String getCcoId() { return ccoId; }
	public void setCcoId(String ccoId) { this.ccoId = ccoId; }

}
