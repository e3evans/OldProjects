package com.cisco.swtg.scim.model.cscmodel;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CSCThreadData implements Serializable {
	private static final long serialVersionUID = 69076879266208L;

	private int id;
	private int messageCount;
	private int rootMessageId;
	private int currentCommunityId;
	private int parentCommunityId;
	private int rating;
	
	private boolean hasCorrectAnswer;
	
	private String name;
	private String author;
	private String authorProfile;
	private String latestMessageDate;
	private String creationDate;
	private String url;
	private String locale;
	
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	
	public int getMessageCount() { return messageCount; }
	public void setMessageCount(int messageCount) { this.messageCount = messageCount; }
	
	public int getRootMessageId() { return rootMessageId; }
	public void setRootMessageId(int rootMessageId) { this.rootMessageId = rootMessageId; }
	
	public int getCurrentCommunityId() { return currentCommunityId; }
	public void setCurrentCommunityId(int currentCommunityId) { this.currentCommunityId = currentCommunityId; }
	
	public int getParentCommunityId() { return parentCommunityId; }
	public void setParentCommunityId(int parentCommunityId) { this.parentCommunityId = parentCommunityId; }
	
	public int getRating() { return rating; }
	public void setRating(int rating) { this.rating = rating; }
	
	public boolean isHasCorrectAnswer() { return hasCorrectAnswer; }
	public void setHasCorrectAnswer(boolean hasCorrectAnswer) { this.hasCorrectAnswer = hasCorrectAnswer; }
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public String getAuthor() { return author; }
	public void setAuthor(String author) { this.author = author; }
	
	public String getAuthorProfile() { return authorProfile; }
	public void setAuthorProfile(String authorProfile) { this.authorProfile = authorProfile; }
	
	public String getLatestMessageDate() { return latestMessageDate; }
	public void setLatestMessageDate(String latestMessageDate) { this.latestMessageDate = latestMessageDate; }
	
	public String getCreationDate() { return creationDate; }
	public void setCreationDate(String creationDate) { this.creationDate = creationDate; }
	
	public String getUrl() { return url; }
	public void setUrl(String url) { this.url = url; }
	
	public String getLocale() { return locale; }
	public void setLocale(String locale) { this.locale = locale; }
		
}
