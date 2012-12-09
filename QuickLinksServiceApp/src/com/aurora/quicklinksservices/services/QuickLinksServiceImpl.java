package com.aurora.quicklinksservices.services;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.aurora.quicklinksservices.beans.UserApp;
import com.aurora.quicklinksservices.beans.UserAppKey;
import com.aurora.quicklinksservices.beans.UserAppResponseBean;
import com.aurora.quicklinksservices.daos.QuickLinksAPPDAO;
import com.aurora.quicklinksservices.util.QuickLinksUtility;



@Service
public class QuickLinksServiceImpl  extends SpringBeanAutowiringSupport implements QuickLinksService{

	
	@Autowired
	private QuickLinksAPPDAO quickLinksAPPDAO;
	public List retrieveAvailAppListByRole(String roleCd) {
		return quickLinksAPPDAO.findAvailAppListByRole(roleCd);
	}
	@Override
	public List retrieveUserDetails(String userid) {
		return quickLinksAPPDAO.findUserDetails(userid);
	}
	
	
	public List findUserAppsByUser(String userid){
		Long userId = Long.parseLong(userid,10);
		return quickLinksAPPDAO.findUserAppsByUser(userId);
	}
	
	public List findAllUserAppsByUser(String userid){
		Long userId = Long.parseLong(userid,10);
		return quickLinksAPPDAO.findAllUserAppsByUser(userId);
	}
	
	
	
	@Override
	public  UserAppResponseBean retrieveUserApp(String appId, String seqNo, String userId) {
		Long userid = Long.parseLong(userId,10);
		return quickLinksAPPDAO.readUserApp(new UserAppKey(userid, appId,new Integer(seqNo)));
	
	}
	
	@Override
	public  void createUserApp(String userId, String appId, String seqNo) {
		Long userid = Long.parseLong(userId,10);
		UserApp userApp = new UserApp();
	    userApp.setUserAppKey(new UserAppKey(userid, appId, new Integer(seqNo)));
	    userApp.setCreated(QuickLinksUtility.getCurrentTime());
	    userApp.setLastAccess(userApp.getCreated());
	    userApp.setDispSeq(QuickLinksUtility.NOTDISPLAYED);
	    userApp.setActiveCd("A");
	    quickLinksAPPDAO.insertUserApp(userApp);
	    
	}
	@Override
	public List retrieveAppMenuAutoList(String appId) {
		return quickLinksAPPDAO.findAppMenuAutoList(appId);
	}
	
	
	public void updateUserApp(String userId, String appId, String seqNo,String activecd){
        Long userid = Long.parseLong(userId,10);
        quickLinksAPPDAO.updateUserApp(new UserAppKey(userid, appId,new Integer(seqNo)),activecd);
       
	
}
	
}
