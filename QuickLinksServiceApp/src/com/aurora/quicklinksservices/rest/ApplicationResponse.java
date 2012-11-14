package com.aurora.quicklinksservices.rest;

import java.util.Collection;

import org.apache.commons.lang.builder.ToStringBuilder;


import com.aurora.quicklinksservices.beans.*;

public class ApplicationResponse {
	
	private static final long serialVersionUID = -4358317667109242403L;

	private Collection<Application> applicationList;

	public Collection<Application> getApplicationList() {
		return applicationList;
	}

	public void setApplicationList(Collection<Application> applicationList) {
		this.applicationList = applicationList;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}