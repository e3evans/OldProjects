package com.cisco.swtg.scim.model.cscmodel;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement(name = "result")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message implements Serializable {
	private static final long serialVersionUID = 69672626608L;
	
	int id;
	int parentMessageID;
	
	String subject;
	String body;	
	String creationDate;
	
	CSCMsgUser user;
	
	@XmlElement(name="ID")
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
		
	@XmlElement(name="subject")
	public String getSubject() { return subject; }
	public void setSubject(String subject) { this.subject = subject; }
		
	@XmlElement(name="body")
	public String getBody() { return body; }
	public void setBody(String body) { this.body = body; }
	
	@XmlElement(name="creationDate")
	public String getCreationDate() { return creationDate; }
	public void setCreationDate(String creationDate) { this.creationDate = creationDate; }
	
	@XmlElement(name="parentMessageID")
	public int getParentMessageID() { return parentMessageID; }
	public void setParentMessageID(int parentMessageID) { this.parentMessageID = parentMessageID; }	
	
	@XmlElement(name="user")
	public CSCMsgUser getUser() { return user; }
	public void setUser(CSCMsgUser user) { this.user = user; }
	
}

