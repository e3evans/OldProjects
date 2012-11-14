package com.cisco.swtg.scim.model.cscmodel;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement(name = "user")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CSCMsgUser implements Serializable {
	private static final long serialVersionUID = 6972626608L;

	int id;
	
	String name;
	String email;
	String username;

	@XmlElement(name="id")
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	
	@XmlElement(name="name")
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	@XmlElement(name="email")
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	
	@XmlElement(name="username")
	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }
	
}

