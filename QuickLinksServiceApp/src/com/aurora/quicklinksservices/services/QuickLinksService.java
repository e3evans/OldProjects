package com.aurora.quicklinksservices.services;

import java.util.List;

import com.aurora.quicklinksservices.beans.UserApp;
import com.aurora.quicklinksservices.beans.UserAppResponseBean;

public interface QuickLinksService {
	
	public List retrieveAvailAppListByRole(String roleCd);
	public List retrieveUserDetails(String userid);
	public List findUserAppsByUser(String userid);
	public UserAppResponseBean retrieveUserApp(String appId,String seqNo,String userId);
	public void createUserApp(String userid, String appId, String seqNo);

}
