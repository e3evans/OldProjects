package org.aurora.quicklinksservices.beans;

import java.io.Serializable;
import java.util.Collection;

import org.apache.commons.lang.builder.ToStringBuilder;

public class CategoryResponse implements Serializable {

	private static final long serialVersionUID = -2518120599593831884L;

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