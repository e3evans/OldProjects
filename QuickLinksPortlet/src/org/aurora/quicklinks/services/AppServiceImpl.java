package org.aurora.quicklinks.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.aurora.quicklinks.beans.Application;
import org.aurora.quicklinks.beans.UserApplication;
import org.aurora.quicklinks.delegates.ServiceInterfaceDelegate;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "appService")
public class AppServiceImpl implements AppService {
	private Logger logger = Logger.getLogger(AppServiceImpl.class);

	@Autowired
	private ServiceInterfaceDelegate serviceInterfaceDelegate;

	protected String SERVICE_URL = "/QuickLinksServiceApp/rest/qlservice/";
	protected String USER_APP_LIST = SERVICE_URL + "userapplist/";
	protected String ALL_USER_APP_LIST = SERVICE_URL + "alluserapplist/";
	protected String RETRIEVE_USER_APP = SERVICE_URL + "retrieveuserapp/";
	protected String CREATE_USER_APP = SERVICE_URL + "createuserapp/";
	protected String UPDATE_USER_APP = SERVICE_URL + "updateuserapp/";
	protected String APP_AUTO_LIST = SERVICE_URL + "appautolist/";
	protected String AVAILABLE_APP_LIST = SERVICE_URL + "results";

	public List<UserApplication> listUserAppByUserId(String userid) {
		List<UserApplication> appList = new ArrayList<UserApplication>();
		try {
			String requestPath = USER_APP_LIST + userid;
			logger.info("webservice call starts with request path "
					+ requestPath);
			JSONObject userAppJSON = new JSONObject(
					serviceInterfaceDelegate.processGetRestRequest(requestPath));
			logger.info("webservice call ends with request path " + requestPath);
			JSONArray jsonArray = userAppJSON.getJSONArray("userAppList");
			for (int i = 0; i < jsonArray.length(); i++) {
				UserApplication app = new UserApplication();
				JSONObject jsonobj = jsonArray.getJSONObject(i);
				app.setUserid(jsonobj.get("userId").toString().trim());
				app.setAppName(jsonobj.get("appName").toString().trim());
				app.setAppURL((jsonobj.get("appUrl").toString()).trim());
				app.setSeqNo(jsonobj.get("seqNo").toString());
				app.setAppId(jsonobj.get("appId").toString());
				app.setActiveCd((jsonobj.get("activeCd").toString()).trim());
				if (jsonobj.get("flagDefault") != null) {
					app.setFlagDefault((jsonobj.get("flagDefault").toString())
							.trim());
				}
				appList.add(app);
			}
		} catch (Exception e) {
			logger.error("Exception in listUserAppByUserId", e);
		}
		return appList;
	}

	/**
	 * This method gets all active and inactive user saved quick links.
	 */
	public List<UserApplication> listAllUserAppByUserId(String userid) {
		List<UserApplication> appList = new ArrayList<UserApplication>();
		try {
			String requestPath = USER_APP_LIST + userid;
			logger.info("webservice call starts with request path "
					+ requestPath);
			JSONObject userAppJSON = new JSONObject(
					serviceInterfaceDelegate.processGetRestRequest(requestPath));
			logger.info("webservice call ends with request path " + requestPath);
			JSONArray jsonArray = userAppJSON.getJSONArray("userAppList");
			for (int i = 0; i < jsonArray.length(); i++) {
				UserApplication app = new UserApplication();
				JSONObject jsonobj = jsonArray.getJSONObject(i);
				app.setUserid(jsonobj.get("userId").toString().trim());
				app.setAppName(jsonobj.get("appName").toString().trim());
				app.setAppURL((jsonobj.get("appUrl").toString()).trim());
				app.setSeqNo(jsonobj.get("seqNo").toString());
				app.setAppId(jsonobj.get("appId").toString());
				app.setActiveCd((jsonobj.get("activeCd").toString()).trim());
				if (jsonobj.get("flagDefault") != null) {
					app.setFlagDefault((jsonobj.get("flagDefault").toString())
							.trim());
				}
				appList.add(app);
			}
		} catch (Exception e) {
			logger.error("Exception in listUserAppByUserId", e);
		}
		return appList;
	}

	/**
	 * This method gets all available quick links.
	 */
	public List<Application> listApplication() {
		List<Application> appList = new ArrayList<Application>();
		try {
			String requestPath = AVAILABLE_APP_LIST;
			logger.info("webservice call starts with request path "
					+ requestPath);
			JSONObject allAvailAppJson = new JSONObject(
					serviceInterfaceDelegate.processGetRestRequest(requestPath));
			logger.info("webservice call ends starts with request path "
					+ requestPath);
			JSONArray jsonArray = allAvailAppJson
					.getJSONArray("applicationList");
			for (int i = 0; i < jsonArray.length(); i++) {
				Application app = new Application();
				JSONObject jsonobj = jsonArray.getJSONObject(i);

				String description = jsonobj.get("appDesc").toString();
				if (description.length() > 50) {
					description = description.substring(0, 50) + "...";
				}
				app.setSeqNo(jsonobj.getJSONObject("appKey").get("seqNo")
						.toString());
				app.setAppId(jsonobj.getJSONObject("appKey").get("appId")
						.toString());
				app.setAppDesc(description);
				app.setAppName(jsonobj.get("appName").toString().trim());
				app.setAppURL((jsonobj.get("appURL").toString()).trim());
				app.setLoggedInAccess((jsonobj.get("loggedInAccess").toString())
						.trim());
				appList.add(app);
			}
		} catch (Exception e) {
			logger.error("Exception in listApplication", e);
		}
		return appList;
	}

	/**
	 * Retrieve user app (sub apps)
	 */
	// public UserApplication retrieveUserApp(String userid, String appId,
	// String seqNo) {
	// try {
	// String requestPath = RETRIEVE_USER_APP + appId + "/" + seqNo + "/"
	// + userid;
	// logger.info("webservice call starts with request path "
	// + requestPath);
	// JSONObject jsonobj = new JSONObject(
	// serviceInterfaceDelegate.processGetRestRequest(requestPath));
	// logger.info("webservice call ends with request path " + requestPath);
	// if (jsonobj.get("appId") != null
	// && !jsonobj.get("appId").toString().trim()
	// .equalsIgnoreCase("null")) {
	// UserApplication app = new UserApplication();
	// app.setUserid(jsonobj.get("userId").toString().trim());
	// app.setAppName(jsonobj.get("appName").toString().trim());
	// app.setAppId((jsonobj.get("appId").toString()).trim());
	// if (jsonobj.get("activeCd") != null) {
	// app.setActiveCd(jsonobj.get("activeCd").toString());
	// app.setSeqNo(jsonobj.get("seqNo").toString());
	// }
	// }
	// return app;
	// } catch (Exception e) {
	// logger.error("Exception in retrieveUserApp", e);
	// }
	// return null;
	// }

	/**
	 * Retrieving userapp
	 */

	public UserApplication createUserApp(String userid, String appId,
			String seqNo) {
		try {
			String requestPath = CREATE_USER_APP + appId + "/" + seqNo + "/"
					+ userid;
			logger.info("webservice call starts with request path "
					+ requestPath);
			JSONObject jsonobj = new JSONObject(
					serviceInterfaceDelegate.processGetRestRequest(requestPath));
			logger.info("webservice call ends with request path " + requestPath);
			UserApplication app = new UserApplication();
			if (jsonobj.get("appId") != null
					&& !jsonobj.get("appId").toString().trim()
							.equalsIgnoreCase("null")) {
				app.setUserid(jsonobj.get("userId").toString().trim());
				app.setAppName(jsonobj.get("appName").toString().trim());
				app.setAppId((jsonobj.get("appId").toString()).trim());
				app.setActiveCd(jsonobj.get("activeCd").toString());
				app.setSeqNo(jsonobj.get("seqNo").toString());
			}
			return app;
		} catch (Exception e) {
			logger.error("Exception in createUserApp", e);
		}
		return null;
	}

	// public List<Application> retrieveAppMenuAutoList(String appId) {
	// List<Application> appList = null;
	// Application app = null;
	// try {
	// appList = new ArrayList<Application>();
	// String requestPath = APP_AUTO_LIST + appId;
	// logger.info("webservice call starts with request path "
	// + requestPath);
	// JSONObject appAutoListJson = new JSONObject(
	// serviceInterfaceDelegate.processGetRestRequest(requestPath));
	// logger.info("webservice call starts with request path "
	// + requestPath);
	// JSONArray jsonArray = appAutoListJson
	// .getJSONArray("applicationList");
	// for (int i = 0; i < jsonArray.length(); i++) {
	// app = new Application();
	// JSONObject jsonobj = jsonArray.getJSONObject(i);
	// app.setSeqNo(jsonobj.getJSONObject("appKey").get("seqNo")
	// .toString());
	// app.setAppId(jsonobj.getJSONObject("appKey").get("appId")
	// .toString());
	// app.setAppDesc(jsonobj.get("appDesc").toString().trim());
	// app.setAppName(jsonobj.get("appName").toString().trim());
	// app.setAppURL((jsonobj.get("appURL").toString()).trim());
	// app.setLoggedInAccess((jsonobj.get("loggedInAccess").toString())
	// .trim());
	// appList.add(app);
	// }
	// } catch (Exception e) {
	// logger.error("Exception in retrieveAppMenuAutoList", e);
	// }
	// return appList;
	//
	// }

	// For user app update
	public void updateUserApp(UserApplication userApp, String userId) {
		try {
			String requestPath = UPDATE_USER_APP + userApp.getAppId().trim()
					+ "/" + userApp.getSeqNo().trim() + "/" + userId + "/"
					+ userApp.getActiveCd().trim();
			logger.info("webservice call starts with request path "
					+ requestPath);
			serviceInterfaceDelegate.processGetRestRequest(requestPath);
			logger.info("webservice call starts with request path "
					+ requestPath);
		} catch (Exception e) {
			logger.error("Exception in updateUserApp", e);
		}
	}
}