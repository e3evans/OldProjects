package com.cisco.swtg.scim.model.scimmodel;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CSC_RAWDATA")
public class CSCRawData implements Serializable {
	private static final long serialVersionUID = 68756879266208L;
	
	private int messageId;
	private int threadId;
	private int threadMessageCount;
	private int threadRootMessageId;
	private int threadCommunityId;
	private int threadParentCommunityId;
	private int messageParentId;
	
	private float threadRating;
	private float messageRating;
	
	private String threadName;
	private String threadAuthor;
	private String threadAuthorProfile;
	private String threadLatestMsgDate;
	private String threadCreationDate;
	private String threadUrl;
	private String threadHasCorrectAnswer;
	private String messageAuthor;
	private String messageAuthorProfile;
	private String messageSubject;
	private String messageBody;
	private String messageUrl;
	private String messageHasAttachment;
	private String messageAnswerIndicator;
	
	private Date messageCreationDate;

	
	@Id
	@Column(name="MESSAGE_ID")
	public int getMessageId() { return messageId; }
	public void setMessageId(int messageId) { this.messageId = messageId; }
	
	@Column(name="THREAD_ID")
	public int getThreadId() { return threadId; }
	public void setThreadId(int threadId) { this.threadId = threadId; }
	
	@Column(name="THREAD_MESSAGE_COUNT")
	public int getThreadMessageCount() { return threadMessageCount; }
	public void setThreadMessageCount(int threadMessageCount) { this.threadMessageCount = threadMessageCount; }
	
	@Column(name="THREAD_NAME")
	public String getThreadName() { return threadName; }
	public void setThreadName(String threadName) { this.threadName = threadName; }
	
	@Column(name="THREAD_AUTHOR")
	public String getThreadAuthor() { return threadAuthor; }	
	public void setThreadAuthor(String threadAuthor) { this.threadAuthor = threadAuthor; }
	
	@Column(name="THREAD_AUTHOR_PROFILE")
	public String getThreadAuthorProfile() { return threadAuthorProfile; }
	public void setThreadAuthorProfile(String threadAuthorProfile) { this.threadAuthorProfile = threadAuthorProfile; }
	
	@Column(name="THREAD_LATEST_MSG_DATE")
	public String getThreadLatestMsgDate() { return threadLatestMsgDate; }
	public void setThreadLatestMsgDate(String threadLatestMsgDate) { this.threadLatestMsgDate = threadLatestMsgDate; }
	
	@Column(name="THREAD_CREATION_DATE")
	public String getThreadCreationDate() { return threadCreationDate; }
	public void setThreadCreationDate(String threadCreationDate) { this.threadCreationDate = threadCreationDate; }
	
	@Column(name="THREAD_URL")
	public String getThreadUrl() { return threadUrl; }
	public void setThreadUrl(String threadUrl) { this.threadUrl = threadUrl; }
	
	@Column(name="THREAD_ROOT_MESSAGE_ID")
	public int getThreadRootMessageId() { return threadRootMessageId; }
	public void setThreadRootMessageId(int threadRootMessageId) { this.threadRootMessageId = threadRootMessageId; }
	
	@Column(name="THREAD_COMMUNITY_ID")
	public int getThreadCommunityId() { return threadCommunityId; }
	public void setThreadCommunityId(int threadCommunityId) { this.threadCommunityId = threadCommunityId; }
	
	@Column(name="THREAD_PARENT_COMMUNITY_ID")
	public int getThreadParentCommunityId() { return threadParentCommunityId; }
	public void setThreadParentCommunityId(int threadParentCommunityId) { this.threadParentCommunityId = threadParentCommunityId; }
	
	@Column(name="THREAD_RATING")
	public float getThreadRating() { return threadRating; }	
	public void setThreadRating(float threadRating) { this.threadRating = threadRating; }
	
	@Column(name="THREAD_HAS_CORRECT_ANSWER")
	public String getThreadHasCorrectAnswer() { return threadHasCorrectAnswer; }
	public void setThreadHasCorrectAnswer(String threadHasCorrectAnswer) { this.threadHasCorrectAnswer = threadHasCorrectAnswer; }
	
	@Column(name="MESSAGE_PARENT_ID")
	public int getMessageParentId() { return messageParentId; }
	public void setMessageParentId(int messageParentId) { this.messageParentId = messageParentId; }
	
	@Column(name="MESSAGE_AUTHOR")
	public String getMessageAuthor() { return messageAuthor; }
	public void setMessageAuthor(String messageAuthor) { this.messageAuthor = messageAuthor; }
	
	@Column(name="MESSAGE_AUTHOR_PROFILE")
	public String getMessageAuthorProfile() { return messageAuthorProfile; }
	public void setMessageAuthorProfile(String messageAuthorProfile) { this.messageAuthorProfile = messageAuthorProfile; }
	
	@Column(name="MESSAGE_CREATION_DATE")
	public Date getMessageCreationDate() { return messageCreationDate; }
	public void setMessageCreationDate(Date messageCreationDate) { this.messageCreationDate = messageCreationDate; }
	
	@Column(name="Message_RATING")
	public float getMessageRating() { return messageRating; }
	public void setMessageRating(float messageRating) { this.messageRating = messageRating; }
	
	@Column(name="MESSAGE_SUBJECT")
	public String getMessageSubject() { return messageSubject; }
	public void setMessageSubject(String messageSubject) { this.messageSubject = messageSubject; }
	
	@Column(name="MESSAGE_BODY")
	public String getMessageBody() { return messageBody; }
	public void setMessageBody(String messageBody) { this.messageBody = messageBody; }
	
	@Column(name="MESSAGE_URL")
	public String getMessageUrl() { return messageUrl; }
	public void setMessageUrl(String messageUrl) { this.messageUrl = messageUrl; }
	
	@Column(name="MESSAGE_HAS_ATTACHMENT")
	public String getMessageHasAttachment() { return messageHasAttachment; }
	public void setMessageHasAttachment(String messageHasAttachment) { this.messageHasAttachment = messageHasAttachment; }
	
	@Column(name="MESSAGE_ANSWER_INDICATOR")
	public String getMessageAnswerIndicator() { return messageAnswerIndicator; }
	public void setMessageAnswerIndicator(String messageAnswerIndicator) { this.messageAnswerIndicator = messageAnswerIndicator; }
	
}
