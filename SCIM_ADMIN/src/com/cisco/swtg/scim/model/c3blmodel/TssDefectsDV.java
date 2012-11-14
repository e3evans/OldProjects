package com.cisco.swtg.scim.model.c3blmodel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="XXCTSBLRPTG_O.TSS_DEFECTS_D_V")
public class TssDefectsDV implements Serializable {
	private static final long serialVersionUID = 690726879266608L;

	private int blDefectKey; 
	
	private String defectNumber;
	
	@Id
	@Column(name="BL_DEFECT_KEY")
	public int getBlDefectKey() { return blDefectKey; }
	public void setBlDefectKey(int blDefectKey) { this.blDefectKey = blDefectKey; }
	
	@Column(name="DEFECT_NUMBER")
	public String getDefectNumber() { return defectNumber; }
	public void setDefectNumber(String defectNumber) { this.defectNumber = defectNumber; }

}
