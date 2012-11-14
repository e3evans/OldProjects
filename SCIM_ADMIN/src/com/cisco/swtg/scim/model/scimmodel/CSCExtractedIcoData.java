package com.cisco.swtg.scim.model.scimmodel;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="CSC_EXTRACTED_ICO")
public class CSCExtractedIcoData  implements Serializable {
	private static final long serialVersionUID = 68756879266208L;
	
	private int cscIcoId;
	private int threadCommunityId;
	private int threadParentCommunityId;
	private int messageId;
	private int threadId;
	
	private String source;
	private String threadName;
	private String threadAuthor;
	private String messageAuthor;
	private int messageRating;
	private String messageAnswerIndicator;
	private String icoType;
	private String icoValue;
	private String icoTitle;
	private String icoUrlServerName;
	private String icoUrlDomain;
	private String icoUrlAccessibility;
	private String neoNodeId;
	
	private Date messageCreationDate;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_entity_seq_gen") 
	@SequenceGenerator(name = "my_entity_seq_gen", sequenceName="auto_inc") 
	@Column(name="CSC_ICO_ID")
	public int getCscIcoId() { return cscIcoId; }
	public void setCscIcoId(int cscIcoId) { this.cscIcoId = cscIcoId; }

	@Column(name="ICO_VALUE")
	public String getIcoValue() { return icoValue; }
	public void setIcoValue(String icoValue) { this.icoValue = icoValue; }
	
	@Column(name="SOURCE")
	public String getSource() { return source; }
	public void setSource(String source) { this.source = source; }
	
	@Column(name="THREAD_ID")
	public int getThreadId() { return threadId; }
	public void setThreadId(int threadId) { this.threadId = threadId; }
	
	@Column(name="THREAD_NAME")
	public String getThreadName() { return threadName; }
	public void setThreadName(String threadName) { this.threadName = threadName; }
	
	@Column(name="THREAD_AUTHOR")
	public String getThreadAuthor() { return threadAuthor; }
	public void setThreadAuthor(String threadAuthor) { this.threadAuthor = threadAuthor; }
	
	@Column(name="THREAD_COMMUNITY_ID")
	public int getThreadCommunityId() { return threadCommunityId; }
	public void setThreadCommunityId(int threadCommunityId) { this.threadCommunityId = threadCommunityId; }
	
	@Column(name="THREAD_PARENT_COMMUNITY_ID")
	public int getThreadParentCommunityId() { return threadParentCommunityId; }
	public void setThreadParentCommunityId(int threadParentCommunityId) { this.threadParentCommunityId = threadParentCommunityId; }
	
	@Column(name="MESSAGE_ID")
	public int getMessageId() { return messageId; }
	public void setMessageId(int messageId) { this.messageId = messageId; }
	
	@Column(name="MESSAGE_CREATION_DATE")
	public Date getMessageCreationDate() { return messageCreationDate; }
	public void setMessageCreationDate(Date messageCreationDate) { this.messageCreationDate = messageCreationDate; }
	
	@Column(name="MESSAGE_AUTHOR")
	public String getMessageAuthor() { return messageAuthor; }
	public void setMessageAuthor(String messageAuthor) { this.messageAuthor = messageAuthor; }
	
	@Column(name="MESSAGE_RATING")
	public int getMessageRating() { return messageRating; }
	public void setMessageRating(int messageRating) { this.messageRating = messageRating; }
	
	@Column(name="MESSAGE_ANSWER_INDICATOR")
	public String getMessageAnswerIndicator() { return messageAnswerIndicator; }
	public void setMessageAnswerIndicator(String messageAnswerIndicator) { this.messageAnswerIndicator = messageAnswerIndicator; }
	
	@Column(name="ICO_TYPE")
	public String getIcoType() { return icoType; }
	public void setIcoType(String icoType) { this.icoType = icoType; }
	
	@Column(name="ICO_TITLE")
	public String getIcoTitle() { return icoTitle; }
	public void setIcoTitle(String icoTitle) { this.icoTitle = icoTitle; }
	
	@Column(name="ICO_URL_SERVER_NAME")
	public String getIcoUrlServerName() { return icoUrlServerName; }
	public void setIcoUrlServerName(String icoUrlServerName) { this.icoUrlServerName = icoUrlServerName; }
	
	@Column(name="ICO_URL_DOMAIN")
	public String getIcoUrlDomain() { return icoUrlDomain; }
	public void setIcoUrlDomain(String icoUrlDomain) { this.icoUrlDomain = icoUrlDomain; }
	
	@Column(name="ICO_URL_ACCESSIBILITY")
	public String getIcoUrlAccessibility() { return icoUrlAccessibility; }
	public void setIcoUrlAccessibility(String icoUrlAccessibility) { this.icoUrlAccessibility = icoUrlAccessibility; }
	
	@Column(name="NEO_NODE_ID")
	public String getNeoNodeId() { return neoNodeId; }
	public void setNeoNodeId(String neoNodeId) { this.neoNodeId = neoNodeId; }
	
}
