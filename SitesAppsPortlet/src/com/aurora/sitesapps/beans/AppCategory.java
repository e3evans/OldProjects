package com.aurora.sitesapps.beans;

import java.util.List;

public class AppCategory {
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
	

}
;
