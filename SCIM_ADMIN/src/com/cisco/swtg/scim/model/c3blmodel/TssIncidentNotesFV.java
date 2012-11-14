package com.cisco.swtg.scim.model.c3blmodel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="XXCTSBLRPTG_O.TSS_INCIDENT_NOTES_F_V")
public class TssIncidentNotesFV implements Serializable {
	private static final long serialVersionUID = 69075879266208L;
	
	private int jtfNoteId;
	private int sourceObjectId;
	
	private String noteType;
	private String notes;
	private String notesDetail;
	private String createdAuthor;
	private String creatorWorkgroup;
	
	private Date creationDate;
	
	private TssIncidentsCurrentFV tssIncidentsCurrentFV;
	private List<WorkgroupsXxctsCrwsdmUV> workgroupsXxctsCrwsdmUV;
	
	
	@Id
	@Column(name="JTF_NOTE_ID")
	public int getJtfNoteId() { return jtfNoteId; }
	public void setJtfNoteId(int jtfNoteId) { this.jtfNoteId = jtfNoteId; }

	@Column( name = "SOURCE_OBJECT_ID", insertable = false, updatable = false )
	public int getSourceObjectId() { return sourceObjectId; }
	public void setSourceObjectId(int sourceObjectId) { this.sourceObjectId = sourceObjectId; }
	
	@Column(name="NOTE_TYPE")
	public String getNoteType() { return noteType; }
	public void setNoteType(String noteType) { this.noteType = noteType; }
	
	@Column(name="NOTES")
	public String getNotes() { return notes; }
	public void setNotes(String notes) { this.notes = notes; }
	
	@Column(name="NOTES_DETAIL")
	public String getNotesDetail() { return notesDetail; }
	public void setNotesDetail(String notesDetail) { this.notesDetail = notesDetail;}
	
	@Column(name="CREATED_AUTHOR")
	public String getCreatedAuthor() { return createdAuthor; }
	public void setCreatedAuthor(String createdAuthor) { this.createdAuthor = createdAuthor; }
	
	@Column(name="CREATOR_WORKGROUP")
	public String getCreatorWorkgroup() { return creatorWorkgroup; }
	public void setCreatorWorkgroup(String creatorWorkgroup) { this.creatorWorkgroup = creatorWorkgroup; }
	
	@Column(name="CREATION_DATE")
	public Date getCreationDate() { return creationDate; }
	public void setCreationDate(Date creationDate) { this.creationDate = creationDate; }
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="SOURCE_OBJECT_ID", referencedColumnName = "INCIDENT_ID")
	public TssIncidentsCurrentFV getTssIncidentsCurrentFV() { return tssIncidentsCurrentFV; }
	public void setTssIncidentsCurrentFV(TssIncidentsCurrentFV tssIncidentsCurrentFV) { this.tssIncidentsCurrentFV = tssIncidentsCurrentFV; }
	
	@ManyToMany(cascade = { CascadeType.ALL }, fetch=FetchType.EAGER)
	@JoinTable(name = "XXCTSBLRPTG_O.TSS_INCIDENT_NOTES_F_V", joinColumns = { @JoinColumn(name = "JTF_NOTE_ID") }, inverseJoinColumns = { @JoinColumn(name = "CREATOR_WORKGROUP") })
	public List<WorkgroupsXxctsCrwsdmUV> getWorkgroupsXxctsCrwsdmUV() { return workgroupsXxctsCrwsdmUV; }
	public void setWorkgroupsXxctsCrwsdmUV( List<WorkgroupsXxctsCrwsdmUV> workgroupsXxctsCrwsdmUV) { this.workgroupsXxctsCrwsdmUV = workgroupsXxctsCrwsdmUV; }
	
}
