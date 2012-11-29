package com.aurora.quicklinks.services;

import java.util.List;

import org.json.JSONObject;

import com.aurora.quicklinks.beans.Application;
import com.aurora.quicklinks.beans.UserApplication;

public interface UrlService {
	
	/*public void updateUrl(List<UrlBean> listBean);
	public List<UrlBean> listUrlBean(String userid);
	public List<CompleteURLBean> listCopmpleteUrlBean();
	*/public List<UserApplication> listCompleteUrlBeanV();
	public List<Application> listEditUrlBeanV();
	

}
