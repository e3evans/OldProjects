package com.aurora.quicklinksservices.rest;

import java.util.Collection;

import org.apache.commons.lang.builder.ToStringBuilder;


import com.aurora.quicklinksservices.beans.*;

public class ApplicationResponse {
	
	private static final long serialVersionUID = -4358317667109242403L;

	private Collection<App> applicationList;
	private Collection<User> userList;

	public Collection<App> getApplicationList() {
		return applicationList;
	}

	public void setApplicationList(Collection<App> applicationList) {
		this.applicationList = applicationList;
	}

	public Collection<User> getUserList() {
		return userList;
	}

	public void setUserList(Collection<User> userList) {
		this.userList = userList;
	}

	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
