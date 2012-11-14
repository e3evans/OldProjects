package com.cisco.swtg.scim.model.scimmodel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="software_image_name_mview")
public class SoftwareImageNameMview implements Serializable {
	private static final long serialVersionUID = 68726879266208L;

	private String imageName;
	private String downloadable;
	
	@Id
	@Column(name="IMAGE_NAME")
	public String getImageName() { return imageName; }
	public void setImageName(String imageName) { this.imageName = imageName; }
	
	@Column(name="DOWNLOADABLE")
	public String getDownloadable() { return downloadable; }
	public void setDownloadable(String downloadable) { this.downloadable = downloadable; }
	
}
