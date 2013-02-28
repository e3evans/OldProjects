package org.aurora.sitesapps.beans;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class AppFormBean implements Serializable {

	private static final long serialVersionUID = -6489820909702527500L;

	private List<AppCategory> appCategoryList;

	public void setAppCategoryList(List<AppCategory> appCategoryList) {
		this.appCategoryList = appCategoryList;
	}

	public List<AppCategory> getAppCategoryList() {
		return appCategoryList;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}