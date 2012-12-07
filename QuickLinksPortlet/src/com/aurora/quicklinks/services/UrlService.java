package com.aurora.quicklinks.services;

import java.util.List;

import org.json.JSONObject;

import com.aurora.quicklinks.beans.Application;
import com.aurora.quicklinks.beans.UserApplication;

public interface UrlService {
	
	
	public List<UserApplication> listCompleteUrlBeanV(String userid);
	public List<Application> listEditUrlBeanV();
	public UserApplication retrieveUserApp(String userid,String appId,String seqNo);
	public UserApplication  createUserApp(String userid,String  appId, String seqNo);
	public  List<Application> retrieveAppMenuAutoList(String paramString);
	
	public void updateUserApp(UserApplication userApp,String userId);

}
