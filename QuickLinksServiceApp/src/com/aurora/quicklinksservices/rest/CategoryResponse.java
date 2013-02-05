package com.aurora.quicklinksservices.rest;

import java.util.Collection;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.aurora.quicklinksservices.beans.App;
import com.aurora.quicklinksservices.beans.AppCategory;

public class CategoryResponse {
	
	private Collection<AppCategory> appCategoryList;
	
	public Collection<AppCategory> getAppCategoryList() {
		return appCategoryList;
	}

	public void setAppCategoryList(Collection<AppCategory> appCategoryList) {
		this.appCategoryList = appCategoryList;
	}

	

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
