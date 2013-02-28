package org.aurora.quicklinksservices.beans;

import java.io.Serializable;
import java.util.Collection;

import org.apache.commons.lang.builder.ToStringBuilder;

public class ApplicationResponse implements Serializable {

	private static final long serialVersionUID = 2459294450978033262L;

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