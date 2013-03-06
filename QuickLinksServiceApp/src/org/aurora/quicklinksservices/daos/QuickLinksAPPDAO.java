package org.aurora.quicklinksservices.daos;

import java.util.List;
import java.util.Map;

import org.aurora.quicklinksservices.beans.App;
import org.aurora.quicklinksservices.beans.AppCategory;
import org.aurora.quicklinksservices.beans.User;
import org.aurora.quicklinksservices.beans.UserApp;
import org.aurora.quicklinksservices.beans.UserAppKey;
import org.aurora.quicklinksservices.beans.UserAppResponseBean;

public interface QuickLinksAPPDAO {

	public List<App> findAvailAppListByRole(String roleCd) throws Exception;

	public List<User> findUserDetails(String loginid) throws Exception;

	public List<UserAppResponseBean> findUserAppsByUser(
			boolean includeInactives, Long userid) throws Exception;

	public UserAppResponseBean readUserApp(UserAppKey userAppKey)
			throws Exception;

	public void createUserApp(UserApp paramUserApp) throws Exception;

	public List<App> findAppMenuAutoList(String paramString) throws Exception;

	public void updateUserApp(UserAppKey userAppKey, String activecd)
			throws Exception;

	public List<AppCategory> findAppCategories() throws Exception;

	public List<App> findAvailAppListByCategory(String categoryId)
			throws Exception;

	public List<App> findPopularAppListByCategory(String categoryId)
			throws Exception;
}