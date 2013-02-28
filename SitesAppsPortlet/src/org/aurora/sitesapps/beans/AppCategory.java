package org.aurora.sitesapps.beans;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class AppCategory implements Serializable {

	private static final long serialVersionUID = 6796113893564735545L;

	private String categoryId;
	private String categoryName;
	private List<Application> appList;
	private List<Application> popularapplist;

	public List<Application> getPopularapplist() {
		return popularapplist;
	}

	public void setPopularapplist(List<Application> popularapplist) {
		this.popularapplist = popularapplist;
	}

	public List<Application> getAppList() {
		return appList;
	}

	public void setAppList(List<Application> appList) {
		this.appList = appList;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}