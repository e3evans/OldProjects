package com.cisco.swtg.scim.model.cscmodel;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CSCXmlMsgReturnData implements Serializable {
	private static final long serialVersionUID = 69072687926628L;
	
	private String id;
	private String body;

	@JsonProperty("ID")
	public String getId() { return id; }
	public void setId(String id) { this.id = id; }
	
	@JsonProperty("ID")
	public String getBody() { return body; }
	public void setBody(String body) { this.body = body; }
			
}


