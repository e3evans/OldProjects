package com.aurora.quicklinksservices.services;

import java.util.List;

import com.aurora.quicklinksservices.beans.UserAppResponseBean;


public interface QuickLinksService {
	
	public List retrieveAvailAppListByRole(String roleCd);
	public List retrieveUserDetails(String loginid);
	public List findUserAppsByUser(Long userid);
	public UserAppResponseBean retrieveUserApp(String appId,String seqNo,Long userId);
	public void createUserApp(Long userid, String appId, String seqNo);
	public List retrieveAppMenuAutoList(String appId);
	public void updateUserApp(Long userid, String appId, String seqNo,String activecd);
	public List findAllUserAppsByUser(Long userid);
	public Long retrieveUserId(String loginid);


}
