package com.aurora.quicklinksservices.services;

import java.util.List;
import java.util.Random;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.aurora.quicklinksservices.beans.App;
import com.aurora.quicklinksservices.beans.User;
import com.aurora.quicklinksservices.beans.UserApp;
import com.aurora.quicklinksservices.beans.UserAppKey;
import com.aurora.quicklinksservices.beans.UserAppResponseBean;
import com.aurora.quicklinksservices.daos.QuickLinksAPPDAO;
import com.aurora.quicklinksservices.util.QuickLinksUtility;



@Service
public class QuickLinksServiceImpl  extends SpringBeanAutowiringSupport implements QuickLinksService{

	
	@Autowired
	private QuickLinksAPPDAO quickLinksAPPDAO;
	public List<App> retrieveAvailAppListByRole(String roleCd) {
		return quickLinksAPPDAO.findAvailAppListByRole(roleCd);
	}
	
	
	@Override
	public List<User> retrieveUserDetails(String loginid) {
		return quickLinksAPPDAO.findUserDetails(loginid);
	}
	
	
	
	
	@Override
	public Long retrieveUserId(String loginid) {
		Long userid=null;
		List<User> list= quickLinksAPPDAO.findUserDetails(loginid);
		if(!(list.isEmpty())){
			userid = list.get(0).getUserID();
		}
		return userid;
	}
	
	
	public List<UserAppResponseBean> findUserAppsByUser(Long userid){
		return quickLinksAPPDAO.findUserAppsByUser(userid);
	}
	
	public List<UserAppResponseBean> findAllUserAppsByUser(Long userid){
		
		return quickLinksAPPDAO.findAllUserAppsByUser(userid);
	}
	
	
	
	@Override
	public  UserAppResponseBean retrieveUserApp(String appId, String seqNo, Long userId) {
		//Long userid = Long.parseLong(userId,10);
		return quickLinksAPPDAO.readUserApp(new UserAppKey(userId, appId,new Integer(seqNo)));
	
	}
	
	@Override
	public  void createUserApp(Long userId, String appId, String seqNo) {
		//Long userid = Long.parseLong(userId,10);
		UserApp userApp = new UserApp();
	    userApp.setUserAppKey(new UserAppKey(userId, appId, new Integer(seqNo)));
	    userApp.setCreated(QuickLinksUtility.getCurrentTime());
	    userApp.setLastAccess(userApp.getCreated());
	    userApp.setDispSeq(QuickLinksUtility.NOTDISPLAYED);
	    userApp.setActiveCd("A");
	    quickLinksAPPDAO.insertUserApp(userApp);
	    
	}
	
	
	
	
	
	@Override
	public List<App> retrieveAppMenuAutoList(String appId) {
		return quickLinksAPPDAO.findAppMenuAutoList(appId);
	}
	
	
	public void updateUserApp(Long userId, String appId, String seqNo,String activecd){
        //Long userid = Long.parseLong(userId,10);
        quickLinksAPPDAO.updateUserApp(new UserAppKey(userId, appId,new Integer(seqNo)),activecd);
       
	
}
	
}
