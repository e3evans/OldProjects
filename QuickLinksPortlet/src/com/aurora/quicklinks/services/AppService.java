package com.aurora.quicklinks.services;

import java.util.List;

import org.json.JSONObject;

import com.aurora.exceptions.AppException;
import com.aurora.quicklinks.beans.Application;
import com.aurora.quicklinks.beans.UserApplication;
/**
 * 
 * @author 
 *
 */
public interface AppService {
	
	
	public List<UserApplication> listUserAppByUserId(String userid) throws AppException;
	public List<Application> listApplication() throws AppException;
	public UserApplication retrieveUserApp(String userid,String appId,String seqNo) throws AppException;
	public UserApplication  createUserApp(String userid,String  appId, String seqNo) throws AppException;
	public  List<Application> retrieveAppMenuAutoList(String paramString) throws AppException;
	public void updateUserApp(UserApplication userApp,String userId) throws AppException;
	public List<UserApplication> listAllUserAppByUserId(String userid) throws AppException;

}
