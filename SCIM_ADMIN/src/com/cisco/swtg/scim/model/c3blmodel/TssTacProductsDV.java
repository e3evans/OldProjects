package com.cisco.swtg.scim.model.c3blmodel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="XXCTSBLRPTG_O.TSS_TAC_PRODUCTS_D_V")
public class TssTacProductsDV implements Serializable {
	private static final long serialVersionUID = 690726879266608L;

	private int blTacProductKey;
	private int versionId;
	
	private String partNumber;
	private String blActiveFlag;
	
	@Id
	@Column(name="BL_TAC_PRODUCT_KEY")
	public int getBlTacProductKey() { return blTacProductKey; }
	public void setBlTacProductKey(int blTacProductKey) { this.blTacProductKey = blTacProductKey; }

	@Column(name="PART_NUMBER")
	public String getPartNumber() { return partNumber; }
	public void setPartNumber(String partNumber) { this.partNumber = partNumber; }
	
	@Column(name="BL_ACTIVE_FLAG")
	public String getBlActiveFlag() { return blActiveFlag; }
	public void setBlActiveFlag(String blActiveFlag) { this.blActiveFlag = blActiveFlag; }
	
	@Column(name="VERSION_ID")
	public int getVersionId() { return versionId; }
	public void setVersionId(int versionId) { this.versionId = versionId; }
	
}
