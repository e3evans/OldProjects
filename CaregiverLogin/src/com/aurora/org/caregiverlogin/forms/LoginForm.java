package com.aurora.org.caregiverlogin.forms;

public class LoginForm {
	
	private String userName;
	private String password;
	private boolean badLogin;
	
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
	
	

}
