package org.aurora.quicklinksservices.services;

import java.util.List;

import org.aurora.quicklinksservices.beans.App;
import org.aurora.quicklinksservices.beans.AppCategory;
import org.aurora.quicklinksservices.beans.User;
import org.aurora.quicklinksservices.beans.UserAppResponseBean;
import org.aurora.quicklinksservices.exceptions.WriteException;

public interface QuickLinksService {

	public List<App> retrieveAvailAppListByRole(String roleCd);

	public List<User> retrieveUserDetails(String loginid);

	public List<UserAppResponseBean> findUserAppsByUser(String loginId);

	public UserAppResponseBean retrieveUserApp(String appId, String seqNo,
			String loginId);

	public void createUserApp(String loginId, String appId, String seqNo)
			throws WriteException;

	public List<App> retrieveAppMenuAutoList(String appId);

	public void updateUserApp(String loginId, String appId, String seqNo,
			String activecd) throws WriteException;

	public List<UserAppResponseBean> findAllUserAppsByUser(String loginId);

	public Long retrieveUserId(String loginid);

	public List<AppCategory> findAppCategories();

	public List<App> findAvailAppListByCategory(String categoryId);

	List<App> findPopularAppListByCategory(String categoryId);
}