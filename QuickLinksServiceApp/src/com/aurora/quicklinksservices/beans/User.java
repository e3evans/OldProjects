package com.aurora.quicklinksservices.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TPT2A_USER")
public class User {

	@Id
	@Column(name = "PT2A_USERID",insertable = false, updatable = false)
	
	private Long userID;
	@Column(name = "PT2A_PORTAL_ID")
	private String portalID;
	@Column(name = "PT2A_LAST_NAME")
	private String lastName;
	@Column(name = "PT2A_EMP_NO")
	private String empNO;
	@Column(name="PT2A_LOGIN_ID")
	private String loginId;
	
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getEmpNO() {
		return empNO;
	}
	public void setEmpNO(String empNO) {
		this.empNO = empNO;
	}
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
	@Column(name = "PT2A_FIRST_NAME")
	private String firstName;
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

