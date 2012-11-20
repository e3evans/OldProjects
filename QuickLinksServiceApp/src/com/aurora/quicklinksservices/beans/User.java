package com.aurora.quicklinksservices.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "S05DTDB.TPT2A_USER")
public class User {

	@Id
	@Column(name = "PT2A_USERID")
	private Long userID;
	@Column(name = "PT2A_PORTAL_ID")
	private String portalID;
	@Column(name = "PT2A_LAST_NAME")
	private String lastName;
	@Column(name = "PT2A_FIRST_NAME")
	private String firstName;
	public Long getUserID() {
		return userID;
	}
	public void setUserID(Long userID) {
		this.userID = userID;
	}
	public String getPortalID() {
		return portalID;
	}
	public void setPortalID(String portalID) {
		this.portalID = portalID;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
}

