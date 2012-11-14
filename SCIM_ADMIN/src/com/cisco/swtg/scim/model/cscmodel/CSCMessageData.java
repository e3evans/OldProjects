package com.cisco.swtg.scim.model.cscmodel;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CSCMessageData implements Serializable {
	private static final long serialVersionUID = 69076687926608L;
	

	private int id;
	private int threadId;
	private int parentId;
	
	private String author;
	
	private String authorProfile;
	private String creationDate;
	private String subject;
	private String body;
	private String shortBody;
	private String url;
	private List<String> attachments;
	private boolean hasAttachment;
	private boolean isCorrectAnswer;
	

	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

	public int getThreadId() { return threadId; }
	public void setThreadId(int threadId) { this.threadId = threadId; }
	
	public int getParentId() { return parentId; }
	public void setParentId(int parentId) { this.parentId = parentId; }
	
	public String getAuthor() { return author; }
	public void setAuthor(String author) { this.author = author; }
	
	public String getAuthorProfile() { return authorProfile; }
	public void setAuthorProfile(String authorProfile) { this.authorProfile = authorProfile; }
	
	public String getCreationDate() { return creationDate; }
	public void setCreationDate(String creationDate) { this.creationDate = creationDate; }
	
	public String getSubject() { return subject; }
	public void setSubject(String subject) { this.subject = subject; }
	
	public String getBody() { return body; }
	public void setBody(String body) { this.body = body; }
	
	public String getShortBody() { return shortBody; }
	public void setShortBody(String shortBody) { this.shortBody = shortBody; }
	
	public String getUrl() { return url; }
	public void setUrl(String url) { this.url = url; }
	
	public List<String> getAttachments() { return attachments; }
	public void setAttachments(List<String> attachments) { this.attachments = attachments; }
	
	public boolean isHasAttachment() { return hasAttachment; }
	public void setHasAttachment(boolean hasAttachment) { this.hasAttachment = hasAttachment; }
	
	public boolean isCorrectAnswer() { return isCorrectAnswer; }
	public void setCorrectAnswer(boolean isCorrectAnswer) { this.isCorrectAnswer = isCorrectAnswer; }
		
}
