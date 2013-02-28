package org.aurora.sitesapps.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.aurora.sitesapps.beans.AppCategory;
import org.aurora.sitesapps.beans.Application;
import org.aurora.sitesapps.delegates.ServiceInterfaceDelegate;
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
	protected String CATEGORY_LIST = SERVICE_URL + "appcategorylist";
	protected String APP_LIST_CATEGORY_KEY = SERVICE_URL + "applistbycategory/";
	protected String POPULAR_CATEGORY_LIST = SERVICE_URL
			+ "popularlistbycategory/";
	protected String AVAILABLE_APP_LIST = SERVICE_URL + "results";

	/**
	 * 
	 * This method gets all available Application by Category Id.
	 * 
	 */
	public List<Application> getAppsByCategory(String categoryId) {
		List<Application> appList = new ArrayList<Application>();
		try {
			appList = new ArrayList<Application>();
			String requestPath = APP_LIST_CATEGORY_KEY + categoryId;
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
			logger.error("Exception in getAppsByCategory", e);
		}
		return appList;
	}

	@Override
	public List<AppCategory> getAppCategories() {
		List<AppCategory> categoryList = new ArrayList<AppCategory>();
		try {
			String requestPath = CATEGORY_LIST;
			logger.info("webservice call starts with request path "
					+ requestPath);
			JSONObject allAvailAppJson = new JSONObject(
					serviceInterfaceDelegate.processGetRestRequest(requestPath));
			logger.info("webservice call ends starts with request path "
					+ requestPath);
			JSONArray jsonArray = allAvailAppJson
					.getJSONArray("appCategoryList");
			for (int i = 0; i < jsonArray.length(); i++) {
				AppCategory appCategory = new AppCategory();
				JSONObject jsonobj = jsonArray.getJSONObject(i);
				appCategory.setCategoryId((jsonobj.get("appCategoryId")
						.toString()));
				appCategory.setCategoryName(((jsonobj.get("appCategoryName")
						.toString())));
				categoryList.add(appCategory);
			}
		} catch (Exception e) {
			logger.error("Exception in getAppCategories", e);
		}
		return categoryList;
	}

	/**
	 * 
	 * This method Get the all available Application by Category Id.
	 * 
	 */

	public List<Application> getPopularApps(String categoryId) {
		List<Application> appList = new ArrayList<Application>();
		try {
			String requestPath = POPULAR_CATEGORY_LIST + categoryId;
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
			logger.error("Exception in getPopularApps", e);
		}
		return appList;
	}

	public List<Application> getIConnectApps() {
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
			logger.error("Exception in getIConnectApps", e);
		}
		return appList;
	}
}