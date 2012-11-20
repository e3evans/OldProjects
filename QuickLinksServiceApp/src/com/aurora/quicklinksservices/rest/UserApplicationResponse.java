package com.aurora.quicklinksservices.rest;

import java.util.Collection;

import org.apache.commons.lang.builder.ToStringBuilder;


import com.aurora.quicklinksservices.beans.*;

public class UserApplicationResponse {
	
	private static final long serialVersionUID = -4358317667109242403L;

	private Collection<UserAppResponseBean> userAppList;
	

	
	public Collection<UserAppResponseBean> getUserAppList() {
		return userAppList;
	}



	public void setUserAppList(Collection<UserAppResponseBean> userAppList) {
		this.userAppList = userAppList;
	}



	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
