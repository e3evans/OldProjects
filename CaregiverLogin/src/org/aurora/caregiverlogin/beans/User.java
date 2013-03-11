package org.aurora.caregiverlogin.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "TPT2A_USER")
public class User implements Serializable {

	private static final long serialVersionUID = 4216143729310635946L;

	@Id
	@Column(name = "PT2A_USERID", insertable = false, updatable = false)
	private Long userID;

	@Column(name = "PT2A_PORTAL_ID")
	@Type(type = "org.aurora.spring.dao.hibernate.StringTrimUserType")
	private String portalID;

	@Column(name = "PT2A_LAST_NAME")
	@Type(type = "org.aurora.spring.dao.hibernate.StringTrimUserType")
	private String lastName;

	@Column(name = "PT2A_FIRST_NAME")
	@Type(type = "org.aurora.spring.dao.hibernate.StringTrimUserType")
	private String firstName;

	@Column(name = "PT2A_EMP_NO")
	@Type(type = "org.aurora.spring.dao.hibernate.StringTrimUserType")
	private String empNO;

	@Column(name = "PT2A_LOGIN_ID")
	@Type(type = "org.aurora.spring.dao.hibernate.StringTrimUserType")
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

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}