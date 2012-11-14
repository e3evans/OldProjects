package com.cisco.swtg.scim.model.c3blmodel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WORKGROUPS_XXCTS_CRWSDM_U_V")
public class WorkgroupsXxctsCrwsdmUV implements Serializable {
	private static final long serialVersionUID = 690726879266688L;

	private int workgroupId;
	private String workgroupName;
	
	@Column(name = "WORKGROUP_ID")
	public int getWorkgroupId() { return workgroupId; }
	public void setWorkgroupId(int workgroupId) { this.workgroupId = workgroupId; }

	@Id
	@Column(name = "WORKGROUP_NAME")
	public String getWorkgroupName() { return workgroupName; }
	public void setWorkgroupName(String workgroupName) { this.workgroupName = workgroupName; }
}
