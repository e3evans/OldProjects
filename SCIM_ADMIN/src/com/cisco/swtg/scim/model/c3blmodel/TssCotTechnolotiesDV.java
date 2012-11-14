package com.cisco.swtg.scim.model.c3blmodel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="XXCTSBLRPTG_O.TSS_COT_TECHNOLOGIES_D_V")
public class TssCotTechnolotiesDV implements Serializable {
	private static final long serialVersionUID = 690726879266208L;


	private int blCotTechnologyKey;
	private int techId;
	private int subTechId;
	
	private String techName;
	private String subTechName;
	
	@Id	
	@Column(name="BL_COT_TECHNOLOGY_KEY")
	public int getBlCotTechnologyKey() { return blCotTechnologyKey; }
	public void setBlCotTechnologyKey(int blCotTechnologyKey) { this.blCotTechnologyKey = blCotTechnologyKey; }
	
	@Column(name="TECH_ID")
	public int getTechId() { return techId; }
	public void setTechId(int techId) { this.techId = techId; }
	
	@Column(name="SUB_TECH_ID")
	public int getSubTechId() { return subTechId; }
	public void setSubTechId(int subTechId) { this.subTechId = subTechId; }
	
	@Column(name="TECH_NAME")
	public String getTechName() { return techName; }
	public void setTechName(String techName) { this.techName = techName; }
	
	@Column(name="SUB_TECH_NAME")
	public String getSubTechName() { return subTechName; }
	public void setSubTechName(String subTechName) { this.subTechName = subTechName; }
	
}
