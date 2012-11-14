package com.cisco.swtg.scim.model.scimmodel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/*@Entity
@Table(name="SW_IMAGE_GUID_MASTER")
public class SwImageGuidMaster implements Serializable {
	private static final long serialVersionUID = 66826879266208L;

	private String imageGuid;
	private String mdfId;
	private String fileName;
	private String mdfConceptName;
	private String fileId;
	
	@Id	
	@Column(name="IMAGE_GUID")
	public String getImageGuid() { return imageGuid; }
	public void setImageGuid(String imageGuid) { this.imageGuid = imageGuid; }
	
	@Column(name="MDF_ID")
	public String getMdfId() { return mdfId; }
	public void setMdfId(String mdfId) { this.mdfId = mdfId; }
	
	@Column(name="FILE_NAME")
	public String getFileName() { return fileName; }
	public void setFileName(String fileName) { this.fileName = fileName; }
	
	@Column(name="MDF_CONCEPT_NAME")
	public String getMdfConceptName() { return mdfConceptName; }
	public void setMdfConceptName(String mdfConceptName) { this.mdfConceptName = mdfConceptName; }
	
	@Column(name="FILE_ID")
	public String getFileId() { return fileId; }
	public void setFileId(String fileId) { this.fileId = fileId; }

}
*/

@Entity
@Table(name="software_image_name_mview")
public class SwImageGuidMaster implements Serializable {
	private static final long serialVersionUID = 66826879266208L;

	private String imageGuid;
	private String mdfId;
	private String fileName;
	private String mdfConceptName;
	private String fileId;
	
	@Id	
	@Column(name="IMAGE_NAME")
	public String getFileName() { return fileName; }
	public void setFileName(String fileName) { this.fileName = fileName; }
	
	@Transient
	public String getImageGuid() { return imageGuid; }
	public void setImageGuid(String imageGuid) { this.imageGuid = imageGuid; }
	
	@Transient
	public String getMdfId() { return mdfId; }
	public void setMdfId(String mdfId) { this.mdfId = mdfId; }
	
	@Transient
	public String getMdfConceptName() { return mdfConceptName; }
	public void setMdfConceptName(String mdfConceptName) { this.mdfConceptName = mdfConceptName; }
	
	@Transient
	public String getFileId() { return fileId; }
	public void setFileId(String fileId) { this.fileId = fileId; }

}
