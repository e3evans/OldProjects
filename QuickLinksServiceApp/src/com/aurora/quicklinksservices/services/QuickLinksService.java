package com.aurora.quicklinksservices.services;

import java.util.List;

import com.aurora.quicklinksservices.beans.App;
import com.aurora.quicklinksservices.beans.User;
import com.aurora.quicklinksservices.beans.UserAppResponseBean;


public interface QuickLinksService {
	
	public List<App> retrieveAvailAppListByRole(String roleCd);
	public List<User> retrieveUserDetails(String loginid);
	public List<UserAppResponseBean> findUserAppsByUser(Long userid);
	public UserAppResponseBean retrieveUserApp(String appId,String seqNo,Long userId);
	public void createUserApp(Long userid, String appId, String seqNo);
	public List<App> retrieveAppMenuAutoList(String appId);
	public void updateUserApp(Long userid, String appId, String seqNo,String activecd);
	public List<UserAppResponseBean> findAllUserAppsByUser(Long userid);
	public Long retrieveUserId(String loginid);


}
