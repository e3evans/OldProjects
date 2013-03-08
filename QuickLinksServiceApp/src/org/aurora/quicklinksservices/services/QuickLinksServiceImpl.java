package org.aurora.quicklinksservices.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.aurora.quicklinksservices.beans.App;
import org.aurora.quicklinksservices.beans.AppCategory;
import org.aurora.quicklinksservices.beans.User;
import org.aurora.quicklinksservices.beans.UserApp;
import org.aurora.quicklinksservices.beans.UserAppKey;
import org.aurora.quicklinksservices.beans.UserAppResponseBean;
import org.aurora.quicklinksservices.daos.QuickLinksAPPDAO;
import org.aurora.quicklinksservices.exceptions.WriteException;
import org.aurora.quicklinksservices.utils.QuickLinksUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { WriteException.class })
public class QuickLinksServiceImpl extends BaseQuickLinksService implements
		QuickLinksService {

	protected final Logger logger = Logger
			.getLogger(QuickLinksServiceImpl.class.getSimpleName());

	@Autowired
	private QuickLinksAPPDAO quickLinksAPPDAO;

	public List<App> retrieveAvailAppListByRole(String roleCd) {
		try {
			List<App> appList = new ArrayList<App>();
			List<App> apps = quickLinksAPPDAO.findAvailAppListByRole(roleCd);
			if (null != apps && !apps.isEmpty()) {
				for (App app : apps) {
					app.setAppURL(QuickLinksUtility.urlFormat(
							BASE_ICONNECT_URL, app.getAppURL()));
					appList.add(app);
				}
			}
			return appList;
		} catch (Exception e) {
			logger.error("Exception in retrieveAvailAppListByRole", e);
		}
		return new ArrayList<App>();
	}

	public List<User> retrieveUserDetails(String loginId) {
		try {
			return quickLinksAPPDAO.findUserDetails(loginId);
		} catch (Exception e) {
			logger.error("Exception in retrieveUserDetails", e);
		}
		return new ArrayList<User>();
	}

	@Override
	public Long retrieveUserId(String loginId) {
		logger.info("Getting userId for loginId: " + loginId);
		try {
			Long userId = null;
			List<User> list = quickLinksAPPDAO.findUserDetails(loginId);
			if (!list.isEmpty()) {
				userId = list.iterator().next().getUserID();
				return userId;
			}
		} catch (Exception e) {
			logger.error("Exception in retrieveUserId", e);
		}
		logger.info("Did NOT find userId for loginId: " + loginId);
		return null;
	}

	public List<UserAppResponseBean> findUserAppsByUser(String loginId) {
		try {
			Long userId = this.retrieveUserId(loginId);
			if (userId != null) {
				logger.info("Getting apps for userId: " + userId);
			} else {
				logger.error("findUserAppsByUser - no userId found for loginId: "
						+ loginId);
			}
			List<UserAppResponseBean> list = quickLinksAPPDAO
					.findUserAppsByUser(userId);
			return list;
		} catch (Exception e) {
			logger.error("Exception in findUserAppsByUser", e);
		}
		return new ArrayList<UserAppResponseBean>();
	}

	public List<UserAppResponseBean> findAllUserAppsByUser(String loginId) {
		try {
			Long userId = this.retrieveUserId(loginId);
			if (userId != null) {
				List<UserAppResponseBean> list = quickLinksAPPDAO
						.findAllUserAppsByUser(userId);
				return list;
			} else {
				logger.error("findAllUserAppsByUser - no userId found for loginId: "
						+ loginId);
			}
		} catch (Exception e) {
			logger.error("Exception in findAllUserAppsByUser", e);
		}
		return new ArrayList<UserAppResponseBean>();
	}

	public UserAppResponseBean retrieveUserApp(String appId, String seqNo,
			String loginId) {
		try {
			Long userId = this.retrieveUserId(loginId);
			if (userId != null) {
				return quickLinksAPPDAO.readUserApp(new UserAppKey(userId,
						appId, new Integer(seqNo)));
			} else {
				logger.error("retrieveUserApp - no userId found for loginId: "
						+ loginId);
			}
		} catch (Exception e) {
			logger.error("Exception in retrieveUserApp", e);
		}
		return new UserAppResponseBean();
	}

	public void createUserApp(String loginId, String appId, String seqNo)
			throws WriteException {
		try {
			Long userId = this.retrieveUserId(loginId);
			if (userId != null) {
				UserApp userApp = new UserApp();
				userApp.setUserAppKey(new UserAppKey(userId, appId,
						new Integer(seqNo)));
				userApp.setCreated(QuickLinksUtility.getCurrentTime());
				userApp.setLastAccess(userApp.getCreated());
				userApp.setDispSeq(QuickLinksUtility.NOTDISPLAYED);
				userApp.setActiveCd("A");
				quickLinksAPPDAO.createUserApp(userApp);
			} else {
				logger.error("createUserApp - no userId found for loginId: "
						+ loginId);
			}
		} catch (Exception e) {
			logger.error("Exception in createUserApp", e);
			throw new WriteException();
		}
	}

	public void updateUserApp(String loginId, String appId, String seqNo,
			String activecd) throws WriteException {
		try {
			Long userId = this.retrieveUserId(loginId);
			if (userId != null) {
				quickLinksAPPDAO.updateUserApp(new UserAppKey(userId, appId,
						new Integer(seqNo)), activecd);
			} else {
				logger.error("updateUserApp - no userId found for loginId: "
						+ loginId);
			}
		} catch (Exception e) {
			logger.error("Exception in updateUserApp", e);
			throw new WriteException();
		}
	}

	public List<App> retrieveAppMenuAutoList(String appId) {
		try {
			return quickLinksAPPDAO.findAppMenuAutoList(appId);
		} catch (Exception e) {
			logger.error("Exception in retrieveAppMenuAutoList", e);
		}
		return new ArrayList<App>();
	}

	public List<AppCategory> findAppCategories() {
		try {
			logger.info("Getting all apps categories");
			return quickLinksAPPDAO.findAppCategories();
		} catch (Exception e) {
			logger.error("Exception in findAppCategories", e);
		}
		return new ArrayList<AppCategory>();
	}

	public List<App> findAvailAppListByCategory(String categoryId) {
		try {
			logger.info("Getting apps for categoryId: " + categoryId);
			List<App> appListFormatURL = new ArrayList<App>();
			List<App> appList = quickLinksAPPDAO
					.findAvailAppListByCategory(categoryId);
			if (null != appList && !appList.isEmpty()) {
				for (App app : appList) {
					app.setAppURL(QuickLinksUtility.urlFormat(
							BASE_ICONNECT_URL, app.getAppURL()));
					appListFormatURL.add(app);
				}

			}
			return appListFormatURL;
		} catch (Exception e) {
			logger.error("Exception in findAvailAppListByCategory", e);
		}
		return new ArrayList<App>();
	}

	public List<App> findPopularAppListByCategory(String categoryId) {
		try {
			logger.info("Getting popular apps for categoryId: " + categoryId);
			List<App> appListFormatURL = new ArrayList<App>();
			List<App> appList = quickLinksAPPDAO
					.findPopularAppListByCategory(categoryId);
			if (null != appList && !appList.isEmpty()) {
				for (App app : appList) {
					app.setAppURL(QuickLinksUtility.urlFormat(
							BASE_ICONNECT_URL, app.getAppURL()));
					appListFormatURL.add(app);
				}
			}
			return appListFormatURL;
		} catch (Exception e) {
			logger.error("Exception in findPopularAppListByCategory", e);
		}
		return new ArrayList<App>();
	}
}