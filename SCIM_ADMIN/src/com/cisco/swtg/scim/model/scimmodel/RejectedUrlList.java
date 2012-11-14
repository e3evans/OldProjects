package com.cisco.swtg.scim.model.scimmodel;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="REJECTED_URL_LIST")
public class RejectedUrlList implements Serializable {	
	private static final long serialVersionUID = 690726879266208L;

	private int cscMessageId;
	private int c3CaseNoteId;	
	private int rejectedCount;
	
	private String icoValue;
	private String source;
	private String icoTitle;
	private String icoUrlDomain;
	private String icoUrlAccessiblity;
	private String errorCode;

	private Date lastRejectedDate;
	
	
	@Id
	@Column(name="ICO_VALUE")
	public String getIcoValue() {	return icoValue;	}
	public void setIcoValue(String icoValue) {	this.icoValue = icoValue;	}
	
	@Column(name="SOURCE")
	public String getSource() {	return source;	}
	public void setSource(String source) {	this.source = source;	}
	
	@Column(name="ICO_TYPE")
	public String getIcoTitle() {	return icoTitle;	}
	public void setIcoTitle(String icoTitle) {	this.icoTitle = icoTitle;	}
	
	@Column(name="ICO_URL_DOMAIN")
	public String getIcoUrlDomain() {	return icoUrlDomain;	}
	public void setIcoUrlDomain(String icoUrlDomain) {	this.icoUrlDomain = icoUrlDomain;	}
	
	@Column(name="ICO_URL_ACCESSIBILITY")
	public String getIcoUrlAccessiblity() {	return icoUrlAccessiblity;	}
	public void setIcoUrlAccessiblity(String icoUrlAccessiblity) {	this.icoUrlAccessiblity = icoUrlAccessiblity;	}
	
	@Column(name="ERROR_CODE")
	public String getErrorCode() {	return errorCode;	}
	public void setErrorCode(String errorCode) {	this.errorCode = errorCode;	}
	
	@Column(name="C3_CASE_NOTE_ID")
	public int getC3CaseNoteId() { return c3CaseNoteId; }
	public void setC3CaseNoteId(int c3CaseNoteId) { this.c3CaseNoteId = c3CaseNoteId; }
	
	@Column(name="CSC_MSG_ID")
	public int getCscMessageId() { return cscMessageId; }
	public void setCscMessageId(int cscMessageId) { this.cscMessageId = cscMessageId; }
	
	@Column(name="REJECTED_COUNT")
	public int getRejectedCount() { return rejectedCount; }
	public void setRejectedCount(int rejectedCount) { this.rejectedCount = rejectedCount; }
	
	@Column(name="LAST_REJECTED_DATE")
	public Date getLastRejectedDate() { return lastRejectedDate; }
	public void setLastRejectedDate(Date lastRejectedDate) { this.lastRejectedDate = lastRejectedDate; }

}
