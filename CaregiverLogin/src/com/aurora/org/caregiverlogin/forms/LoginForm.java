package com.aurora.org.caregiverlogin.forms;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class LoginForm implements Serializable {
	private static final long serialVersionUID = -7093168867019252158L;

	private String userName;
	private String password;
	private boolean badLogin;
	private boolean badSession;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isBadLogin() {
		return badLogin;
	}

	public void setBadLogin(boolean badLogin) {
		this.badLogin = badLogin;
	}

	public boolean isBadSession() {
		return badSession;
	}

	public void setBadSession(boolean badSession) {
		this.badSession = badSession;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (badLogin ? 1231 : 1237);
		result = prime * result + (badSession ? 1231 : 1237);
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoginForm other = (LoginForm) obj;
		if (badLogin != other.badLogin)
			return false;
		if (badSession != other.badSession)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}