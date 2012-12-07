package com.aurora.quicklinks.beans;

import java.util.List;

public class UrlFormBean {
	/*private  List<Application> listUrlBean;

	public List<Application> getListUrlBean() {
		return listUrlBean;
	}

	public void setListUrlBean(List<Application> listUrlBean) {
		this.listUrlBean = listUrlBean;
	}*/
	
	private  List<MenuApp> listUrlBean;

	public void setListUrlBean(List<MenuApp> listUrlBean) {
		this.listUrlBean = listUrlBean;
	}

	public List<MenuApp> getListUrlBean() {
		return listUrlBean;
	}

}
