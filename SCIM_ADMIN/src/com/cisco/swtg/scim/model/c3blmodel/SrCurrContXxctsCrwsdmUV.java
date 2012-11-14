package com.cisco.swtg.scim.model.c3blmodel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="XXCTSBLBRS_O.SR_CURR_CONT_XXCTS_CRWSDM_U_V")
public class SrCurrContXxctsCrwsdmUV implements Serializable {
	private static final long serialVersionUID = 690626879266608L;

	private int blContractKey;
	
	private String contractNumber;
	
	@Id
	@Column(name="BL_CONTRACT_KEY")
	public int getBlContractKey() { return blContractKey; }
	public void setBlContractKey(int blContractKey) { this.blContractKey = blContractKey; }
	
	@Column(name="CONTRACT_NUMBER")
	public String getContractNumber() { return contractNumber; }
	public void setContractNumber(String contractNumber) { this.contractNumber = contractNumber; }

}
