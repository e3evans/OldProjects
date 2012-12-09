package com.aurora.quicklinksservices.daos;

import java.util.List;

import com.aurora.quicklinksservices.beans.UserApp;
import com.aurora.quicklinksservices.beans.UserAppKey;
import com.aurora.quicklinksservices.beans.UserAppResponseBean;

public interface QuickLinksAPPDAO {

	public List findAvailAppListByRole(String roleCd);
	public List findUserDetails(String userid);
	public List findUserAppsByUser(Long userid);
	public UserAppResponseBean readUserApp( UserAppKey userAppKey);
	public void insertUserApp(UserApp paramUserApp);
	public List findAppMenuAutoList(String paramString);
	public void updateUserApp( UserAppKey userAppKey , String activecd);
	public List findAllUserAppsByUser(Long userid);
}
