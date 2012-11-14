package com.cisco.swtg.scim.model.cscmodel;

import java.io.Serializable;


public class CSCXmlMessageData implements Serializable {
	private static final long serialVersionUID = 6072687926628L;


	private int id;
	private int parentMessageID;
	
	private String body;

	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

	public int getParentMessageID() { return parentMessageID; }
	public void setParentMessageID(int parentMessageID) { this.parentMessageID = parentMessageID; }

	public String getBody() { return body; }
	public void setBody(String body) { this.body = body; }
	
}
