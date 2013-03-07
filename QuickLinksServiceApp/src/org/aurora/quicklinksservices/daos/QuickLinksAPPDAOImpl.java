package org.aurora.quicklinksservices.daos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;
import org.aurora.quicklinksservices.beans.App;
import org.aurora.quicklinksservices.beans.AppCategory;
import org.aurora.quicklinksservices.beans.AppKey;
import org.aurora.quicklinksservices.beans.User;
import org.aurora.quicklinksservices.beans.UserApp;
import org.aurora.quicklinksservices.beans.UserAppKey;
import org.aurora.quicklinksservices.beans.UserAppResponseBean;
import org.aurora.quicklinksservices.services.BaseQuickLinksService;
import org.aurora.quicklinksservices.utils.QuickLinksUtility;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class QuickLinksAPPDAOImpl extends BaseQuickLinksService implements
		QuickLinksAPPDAO {

	protected static final Logger logger = Logger
			.getLogger(QuickLinksAPPDAOImpl.class.getSimpleName());

	private HibernateTemplate hibernateTemplate;

	@Autowired
	public void setSessionFactory(
			@Qualifier("urlsessionFactory") SessionFactory db2SessionFactory) {
		this.hibernateTemplate = new HibernateTemplate(db2SessionFactory);
	}

	/*
	 * Helper method for logging that can easily change to log at a different
	 * level for all calls
	 */
	private void log(String msg) {
		logger.info(msg.replaceAll("\n", ""));
	}

	/* Get available quick links list based on user role */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<App> findAvailAppListByRole(String roleCd) throws Exception {
		log("Entered findAvailAppListByRole");
		final StringBuilder sql = new StringBuilder()
				.append("SELECT app.* ")
				.append("FROM TPT2B_APPLICATION app, TPT2B_APPLICATION parent, TPT2E_APP_ROLE approle ")
				.append("WHERE app.PT2B_APPID = approle.PT2E_APPID ")
				.append("AND app.PT2B_SEQ_NO = approle.PT2E_SEQ_NO ")
				.append("AND app.PT2B_APPID = parent.PT2B_APPID ")
				.append("AND parent.PT2B_SEQ_NO = 0 ")
				.append("AND approle.PT2E_ROLE_CD = '").append(roleCd)
				.append("' ")
				.append("AND app.PT2B_LOGIN_ACC NOT IN ('E','N') ")
				.append("AND app.PT2B_ACTIVE_CD = 'A' ")
				.append("AND parent.PT2B_ACTIVE_CD = 'A'");
		log(sql.toString());
		List<App> apps = (List<App>) this.hibernateTemplate
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session) {
						SQLQuery query = session.createSQLQuery(sql.toString());
						return query.addEntity(App.class).list();
					}
				});
		log("Found " + apps.size() + " apps for roleCd: " + roleCd);
		return apps;
	}

	/* Get user details */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<User> findUserDetails(String loginId) throws Exception {
		log("Entered findUserDetails");
		final StringBuilder sql = new StringBuilder(
				"SELECT user.* FROM TPT2A_USER user WHERE user.PT2A_LOGIN_ID = '")
				.append(loginId).append("'");
		log(sql.toString());
		List<User> users = (List<User>) this.hibernateTemplate
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session) {
						SQLQuery query = session.createSQLQuery(sql.toString());
						return query.addEntity("user", User.class).list();
					}
				});
		log("Found " + users.size() + " users for loginId: " + loginId);
		return users;
	}

	/* Get user saved quick links including defaults */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<UserAppResponseBean> findUserAppsByUser(Long userId)
			throws Exception {
		log("Entered findUserAppsByUser");
		List<UserAppResponseBean> userAppList = new ArrayList<UserAppResponseBean>();

		// get default apps, TODO: store some where so they are not hard coded
		final StringBuilder sql = new StringBuilder()
				.append("SELECT app.* FROM TPT2B_APPLICATION app ")
				.append("WHERE app.PT2B_APPID IN ('EBOEX', 'EB092', 'EB500', 'EBLIB', 'ahcom', 'EB122', 'EB084', ")
				.append("'EB538', 'EB480', 'EB057', 'PETR', 'EB294', 'EB110', 'EB294', 'EB416') AND app.PT2B_SEQ_NO = 0");
		log(sql.toString());
		List<App> defaultApps = (List<App>) this.hibernateTemplate
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session) {
						SQLQuery query = session.createSQLQuery(sql.toString());
						return query.addEntity(App.class).list();
					}
				});
		if (defaultApps != null) {
			log("Found " + defaultApps.size() + " default apps for userId: "
					+ userId);
			for (App app : defaultApps) {
				UserAppResponseBean bean = new UserAppResponseBean();
				AppKey key = app.getAppKey();
				bean.setAppId(key.getAppId());
				bean.setSeqNo(key.getSeqNo().toString());
				String appName = app.getAppName();
				bean.setAppName(appName);
				bean.setAppUrl(QuickLinksUtility.urlFormat(BASE_ICONNECT_URL,
						app.getAppURL()));
				bean.setActiveCd(app.getActiveCd());
				bean.setFlagDefault("true");
				bean.setUserId(userId.toString());
				userAppList.add(bean);
			}
		}

		// get user apps
		final StringBuilder sql2 = new StringBuilder()
				.append("SELECT userapp.* ")
				.append("FROM TPT2B_APPLICATION app, TPT2B_APPLICATION parent, TPT2E_APP_ROLE approle, TPT2J_USER_APP userapp ")
				.append("WHERE app.PT2B_APPID = approle.PT2E_APPID ")
				.append("AND app.PT2B_SEQ_NO = approle.PT2E_SEQ_NO ")
				.append("AND approle.PT2E_ROLE_CD = 'EMP' ")
				.append("AND app.PT2B_APPID = parent.PT2B_APPID ")
				.append("AND parent.PT2B_SEQ_NO = 0 ")
				.append("AND parent.PT2B_ACTIVE_CD = 'A' ")
				.append("AND app.PT2B_LOGIN_ACC NOT IN ('E','N') ")
				.append("AND app.PT2B_ACTIVE_CD = 'A' ")
				.append("AND userapp.PT2J_APPID = app.PT2B_APPID ")
				.append("AND userapp.PT2J_SEQ_NO = app.PT2B_SEQ_NO ")
				.append("AND userapp.PT2J_USERID = ").append(userId).append(" ")
				.append("AND userapp.PT2J_ACTIVE_CD = 'A' ")
				.append("AND (userapp.PT2J_SEQ_NO <> 0 ")
				.append("OR userapp.PT2J_APPID NOT IN ('EBOEX', 'EB092', 'EB500', 'EBLIB', 'ahcom', 'EB122', 'EB084', ")
				.append("'EB538', 'EB480', 'EB057', 'PETR', 'EB294', 'EB110', 'EB294', 'EB416'))");
		log(sql2.toString());
		List<UserApp> userApps = (List<UserApp>) this.hibernateTemplate
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session) {
						SQLQuery query = session.createSQLQuery(sql2.toString());
						return query.addEntity(UserApp.class).list();
					}
				});
		if (userApps != null) {
			log("Found " + userApps.size() + " user apps for userId: " + userId);
			for (UserApp userApp : userApps) {
				if (userApp.getApplication() != null) {
					UserAppResponseBean bean = new UserAppResponseBean();
					bean.setActiveCd(userApp.getActiveCd());
					UserAppKey key = userApp.getUserAppKey();
					bean.setAppId(key.getAppId());
					bean.setSeqNo(key.getSeqNo().toString());
					bean.setUserId(key.getUserId().toString());
					App app = userApp.getApplication();
					bean.setAppName(app.getAppName());
					bean.setAppUrl(QuickLinksUtility.urlFormat(
							BASE_ICONNECT_URL, app.getAppURL()));
					userAppList.add(bean);
				}
			}
		}

		// sort by app id and seq no and add parent app name to child app
		log("Adding parent app name to children");
		Collections.sort(userAppList, UserAppResponseBean.APP_ID_COMPARATOR);
		Map<String, String> parentNames = new HashMap<String, String>();
		for (UserAppResponseBean bean : userAppList) {
			if ("0".equals(bean.getSeqNo())) {
				parentNames.put(bean.getAppId().toLowerCase(),
						bean.getAppName());
			} else {
				bean.setAppName(parentNames.get(bean.getAppId().toLowerCase())
						+ ": " + bean.getAppName());
			}
		}

		// sort by app name
		Collections.sort(userAppList, UserAppResponseBean.APP_NAME_COMPARATOR);

		return userAppList;
	}

	/* Get user saved quick links including defaults */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<UserAppResponseBean> findAllUserAppsByUser(Long userId)
			throws Exception {
		log("Entered findAllUserAppsByUser");
		List<UserAppResponseBean> userAppList = new ArrayList<UserAppResponseBean>();

		// get user apps
		final StringBuilder sql = new StringBuilder()
				.append("SELECT userapp.* ")
				.append("FROM TPT2B_APPLICATION app, TPT2B_APPLICATION parent, TPT2E_APP_ROLE approle, TPT2J_USER_APP userapp ")
				.append("WHERE app.PT2B_APPID = approle.PT2E_APPID ")
				.append("AND app.PT2B_SEQ_NO = approle.PT2E_SEQ_NO ")
				.append("AND approle.PT2E_ROLE_CD = 'EMP' ")
				.append("AND app.PT2B_APPID = parent.PT2B_APPID ")
				.append("AND parent.PT2B_SEQ_NO = 0 ")
				.append("AND parent.PT2B_ACTIVE_CD = 'A' ")
				.append("AND app.PT2B_LOGIN_ACC NOT IN ('E','N') ")
				.append("AND app.PT2B_ACTIVE_CD = 'A' ")
				.append("AND userapp.PT2J_APPID = app.PT2B_APPID ")
				.append("AND userapp.PT2J_SEQ_NO = app.PT2B_SEQ_NO ")
				.append("AND userapp.PT2J_USERID = ").append(userId);
		log(sql.toString());
		List<UserApp> userApps = (List<UserApp>) this.hibernateTemplate
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session) {
						SQLQuery query = session.createSQLQuery(sql.toString());
						return query.addEntity(UserApp.class).list();
					}
				});
		if (userApps != null) {
			log("Found " + userApps.size() + " all user apps for userId: "
					+ userId);
			for (UserApp userApp : userApps) {
				if (userApp.getApplication() != null) {
					UserAppResponseBean bean = new UserAppResponseBean();
					bean.setActiveCd(userApp.getActiveCd());
					UserAppKey key = userApp.getUserAppKey();
					bean.setAppId(key.getAppId());
					bean.setSeqNo(key.getSeqNo().toString());
					bean.setUserId(key.getUserId().toString());
					App app = userApp.getApplication();
					bean.setAppName(app.getAppName());
					bean.setAppUrl(QuickLinksUtility.urlFormat(
							BASE_ICONNECT_URL, app.getAppURL()));
					userAppList.add(bean);
				}
			}
		}

		// sort by app name
		Collections.sort(userAppList, UserAppResponseBean.APP_NAME_COMPARATOR);

		return userAppList;
	}

	/* Get user app */
	public UserAppResponseBean readUserApp(UserAppKey userAppKey)
			throws Exception {
		log("Entered readUserApp - hibernate get UserApp with: "
				+ userAppKey.toString());
		UserApp userApp = (UserApp) this.hibernateTemplate.get(UserApp.class,
				userAppKey);
		UserAppResponseBean userAppResponseBean = new UserAppResponseBean();
		if (userApp != null) {
			log("Found user app: " + userApp.toString());
			UserAppKey key = userApp.getUserAppKey();
			userAppResponseBean.setAppId(key.getAppId());
			userAppResponseBean.setSeqNo(key.getSeqNo().toString());
			userAppResponseBean.setUserId(key.getUserId().toString());
			userAppResponseBean.setActiveCd(userApp.getActiveCd());
		}
		return userAppResponseBean;
	}

	/* Save user app */
	public void createUserApp(UserApp userApp) throws Exception {
		log("Entered createUserApp - hibernate save UserApp: "
				+ userApp.toString());
		this.hibernateTemplate.save(userApp);
	}

	/* Update user app */
	public void updateUserApp(UserAppKey userAppKey, String activeCd) {
		log("Entered updateUserApp - hibernate get UserApp with: "
				+ userAppKey.toString());
		UserApp userApp = (UserApp) this.hibernateTemplate.get(UserApp.class,
				userAppKey);
		if (userApp != null) {
			log("Found user app: " + userApp.toString());
			userApp.setActiveCd(activeCd);
			log("Hibernate update UserApp: " + userApp.toString());
			this.hibernateTemplate.update(userApp);
		} else {
			logger.error("Exception getting user app with key: " + userAppKey);
		}
	}

	/* Find sub apps */
	// TODO: not used yet
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<App> findAppMenuAutoList(String appId) throws Exception {
		log("Entered findAppMenuAutoList");
		final StringBuilder sql = new StringBuilder().append("SELECT app.* ")
				.append("FROM TPT2B_APPLICATION app ")
				.append("WHERE app.PT2B_APPID = '").append(appId).append("' ")
				.append("AND app.PT2B_AUTO_REG = 'Y' ")
				.append("AND app.PT2B_LOGIN_ACC NOT IN ('E','N') ")
				.append("AND app.PT2B_ACTIVE_CD = 'A' ")
				.append("AND app.PT2B_SEQ_NO > 0");
		log(sql.toString());
		List<App> apps = (List<App>) this.hibernateTemplate
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session) {
						SQLQuery query = session.createSQLQuery(sql.toString());
						return query.addEntity(App.class).list();
					}
				});
		log("Found " + apps.size() + " auto reg sub apps for appId: " + appId
				+ " - " + ToStringBuilder.reflectionToString(apps.toArray()));
		return apps;
	}

	/* Get all app categories */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<AppCategory> findAppCategories() throws Exception {
		log("Entered findAppCategories");
		final StringBuilder sql = new StringBuilder()
				.append("SELECT appcat.* ").append(
						"FROM TPT2Z_APP_CATEGORY appcat ");
		log(sql.toString());
		List<AppCategory> appCategories = (List<AppCategory>) this.hibernateTemplate
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session) {
						SQLQuery query = session.createSQLQuery(sql.toString());
						return query.addEntity(AppCategory.class).list();
					}
				});
		log("Found " + appCategories.size() + " app categories");
		return appCategories;
	}

	/* Get apps by category */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<App> findAvailAppListByCategory(String categoryId)
			throws Exception {
		log("Entered findAvailAppListByCategory");
		final StringBuilder sql = new StringBuilder()
				.append("SELECT app.* ")
				.append("FROM TPT2B_APPLICATION app, TPT2B_APPLICATION parent, TPT2E_APP_ROLE approle ")
				.append("WHERE app.PT2B_APPID = approle.PT2E_APPID ")
				.append("AND app.PT2B_SEQ_NO = approle.PT2E_SEQ_NO ")
				.append("AND app.PT2B_APPID = parent.PT2B_APPID ")
				.append("AND parent.PT2B_SEQ_NO = 0 ")
				.append("AND approle.PT2E_ROLE_CD = 'EMP'")
				.append("AND app.PT2B_LOGIN_ACC NOT IN ('E','N') ")
				.append("AND app.PT2B_ACTIVE_CD = 'A' ")
				.append("AND app.PT2B_PRIMARY_CATEGORY_ID = ")
				.append(categoryId).append(" AND parent.PT2B_ACTIVE_CD = 'A'");
		log(sql.toString());
		List<App> apps = (List<App>) this.hibernateTemplate
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session) {
						SQLQuery query = session.createSQLQuery(sql.toString());
						return query.addEntity(App.class).list();
					}
				});
		log("Found " + apps.size() + " apps for categoryId: " + categoryId);
		return apps;
	}

	/* Get apps by popular category */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<App> findPopularAppListByCategory(String categoryId)
			throws Exception {
		log("Entered findPopularAppListByCategory");
		final StringBuilder sql = new StringBuilder()
				.append("SELECT app.* ")
				.append("FROM TPT2B_APPLICATION app, TPT2B_APPLICATION parent, TPT2E_APP_ROLE approle ")
				.append("WHERE app.PT2B_APPID = approle.PT2E_APPID ")
				.append("AND app.PT2B_SEQ_NO = approle.PT2E_SEQ_NO ")
				.append("AND app.PT2B_APPID = parent.PT2B_APPID ")
				.append("AND parent.PT2B_SEQ_NO = 0 ")
				.append("AND approle.PT2E_ROLE_CD = 'EMP' ")
				.append("AND app.PT2B_LOGIN_ACC NOT IN ('E','N') ")
				.append("AND app.PT2B_ACTIVE_CD = 'A' ")
				.append("AND app.PT2B_SECONDARY_CATEGORY_ID = ")
				.append(categoryId).append(" AND parent.PT2B_ACTIVE_CD = 'A'");
		log(sql.toString());
		List<App> apps = (List<App>) this.hibernateTemplate
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session) {
						SQLQuery query = session.createSQLQuery(sql.toString());
						return query.addEntity(App.class).list();
					}
				});
		log("Found " + apps.size() + " popular apps for categoryId: "
				+ categoryId);
		return apps;
	}
}