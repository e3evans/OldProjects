package com.aurora.quicklinksservices.daos;

import java.util.List;


import com.aurora.quicklinksservices.beans.App;
import com.aurora.quicklinksservices.beans.User;
import com.aurora.quicklinksservices.beans.UserApp;
import com.aurora.quicklinksservices.beans.UserAppKey;
import com.aurora.quicklinksservices.beans.UserAppResponseBean;

public interface QuickLinksAPPDAO {

	public List<App> findAvailAppListByRole(String roleCd);
	public List<User> findUserDetails(String loginid);
	public List<UserAppResponseBean> findUserAppsByUser(Long userid);
	public UserAppResponseBean readUserApp( UserAppKey userAppKey);
	public void insertUserApp(UserApp paramUserApp);
	public List<App> findAppMenuAutoList(String paramString);
	public void updateUserApp( UserAppKey userAppKey , String activecd);
	public List<UserAppResponseBean> findAllUserAppsByUser(Long userid);

}
