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
		// TODO Auto-generated method stub
		System.out.println("retrieveAvailAppListByRole");
		return quickLinksAPPDAO.findAvailAppListByRole(roleCd);
	}
	@Override
	public List retrieveUserDetails(String userid) {
		// TODO Auto-generated method stub
		return quickLinksAPPDAO.findUserDetails(userid);
	}
	
	
	public List findUserAppsByUser(String userid){
		Long userId = Long.parseLong(userid,10);
		return quickLinksAPPDAO.findUserAppsByUser(userId);
	}
	@Override
	public  UserAppResponseBean retrieveUserApp(String appId, String seqNo, String userId) {
		// TODO Auto-generated method stub
		Long userid = Long.parseLong(userId,10);
		return quickLinksAPPDAO.readUserApp(new UserAppKey(userid, appId,new Integer(seqNo)));
	
	}
	
	@Override
	public  void createUserApp(String userId, String appId, String seqNo) {
		// TODO Auto-generated method stub
		Long userid = Long.parseLong(userId,10);
		//Long userid = 43l;
		UserApp userApp = new UserApp();
	    userApp.setUserAppKey(new UserAppKey(userid, appId, new Integer(seqNo)));
	    userApp.setCreated(QuickLinksUtility.getCurrentTime());
	    userApp.setLastAccess(userApp.getCreated());
	    userApp.setDispSeq(QuickLinksUtility.NOTDISPLAYED);
	    userApp.setActiveCd("I");
	    System.out.println(" retrieving details from userAPP**********************************************************");
	    System.out.println(userApp.getUserAppKey());
	    System.out.println(userApp.getCreated());
	    System.out.println(userApp.getLastAccess());
	    System.out.println(userApp.getDispSeq());
	    System.out.println(userApp.getActiveCd());
	    quickLinksAPPDAO.insertUserApp(userApp);
	    System.out.println(" retrieving details from userAPP**********************************************************");
		
	
	}
	
	
	
	
}
