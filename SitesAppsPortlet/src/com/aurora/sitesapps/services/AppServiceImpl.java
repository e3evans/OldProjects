package com.aurora.sitesapps.services;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aurora.sitesapps.beans.AppCategory;
import com.aurora.sitesapps.beans.Application;
import com.aurora.sitesapps.delegates.ServiceInterfaceDelegate;
import com.aurora.sitesapps.exception.AppException;
import com.aurora.sitesapps.util.ResourceUtil;

@Service(value = "appService")
public class AppServiceImpl implements AppService {
	private Logger logger = Logger.getLogger(AppServiceImpl.class);
	@Autowired
	private ServiceInterfaceDelegate serviceInterfaceDelegate;
	
	
	
	
	
	
	
	/**
	 * 
	 * This method Get the all available Application by Category Id.
	 * 
	 */

	public List<Application> listApplication(String categoryId) throws AppException{
		List<Application> appList = null;
		Application app = null;
		try {
			appList = new ArrayList<Application>();
			
			String requestPath =  ResourceUtil.getApplistbycategory()+categoryId;
			
		    System.out.println("webservice call starts with request path "+requestPath);
			JSONObject allAvailAppJson = new JSONObject(
					serviceInterfaceDelegate.processGetRestRequest(requestPath));

			System.out.println("webservice call ends starts with request path "+requestPath);
			JSONArray jsonArray = allAvailAppJson.getJSONArray("applicationList");
			for (int i = 0; i < jsonArray.length(); i++) {
				app = new Application();
				JSONObject jsonobj = jsonArray.getJSONObject(i);
				
				String description =jsonobj.get("appDesc").toString();
				if(description.length()>50){
					description=description.substring(0, 50)+"...";
					
				}
				
				app.setSeqNo(jsonobj.getJSONObject("appKey").get("seqNo").toString());
				app.setAppId(jsonobj.getJSONObject("appKey").get("appId").toString());
				app.setAppDesc(description);
				app.setAppName(jsonobj.get("appName").toString().trim());
				app.setAppURL((jsonobj.get("appURL").toString()).trim());
				app.setLoggedInAccess((jsonobj.get("loggedInAccess").toString()).trim());
				appList.add(app);
			}

		}
		catch (JSONException je) {
			AppException ae = new AppException();
			ae.setExceptionType("Service End Point");
			ae.setExceptionCode("QLEXception 001");
			ae.setExceptionMessage("Excetion in JSON PArsing Service Implmentation !!!!"+je);
			logger.error("Exception in listUserAppByUserId !!!  "+je);
			throw ae;
			
		}
		catch (AppException ae) {
			throw ae;
			
		}catch (Exception e) {
			AppException ae = new AppException();
			ae.setExceptionType("Service End Point");
			ae.setExceptionCode("QLEXception 001");
			ae.setExceptionMessage("Excetion in Service Implmentation !!!!"+e);
			logger.error("Exception in listApplication !!!  "+e);
			throw ae;
			
		}

		return appList;

	}




	@Override
	public List<AppCategory> listAppCategory() throws AppException {
		List<AppCategory> categoryList = null;
		AppCategory appCategory = null;
		try {
			categoryList = new ArrayList<AppCategory>();
			
			String requestPath =  ResourceUtil.getCategorylisturl();
			
		    System.out.println("webservice call starts with request path "+requestPath);
			JSONObject allAvailAppJson = new JSONObject(
					serviceInterfaceDelegate.processGetRestRequest(requestPath));

			System.out.println("webservice call ends starts with request path "+requestPath);
			JSONArray jsonArray = allAvailAppJson.getJSONArray("appCategoryList");
			for (int i = 0; i < jsonArray.length(); i++) {
				appCategory = new AppCategory();
				JSONObject jsonobj = jsonArray.getJSONObject(i);
				
				
				
				appCategory.setCategoryId((jsonobj.get("appCategoryId").toString()));
				appCategory.setCategoryName(((jsonobj.get("appCategoryName").toString())));
				categoryList.add(appCategory);
			}

		}
		catch (JSONException je) {
			AppException ae = new AppException();
			ae.setExceptionType("Service End Point");
			ae.setExceptionCode("QLEXception 001");
			ae.setExceptionMessage("Excetion in JSON PArsing Service Implmentation !!!!"+je);
			logger.error("Exception in listUserAppByUserId !!!  "+je);
			throw ae;
			
		}
		catch (AppException ae) {
			throw ae;
			
		}catch (Exception e) {
			AppException ae = new AppException();
			ae.setExceptionType("Service End Point");
			ae.setExceptionCode("QLEXception 001");
			ae.setExceptionMessage("Excetion in Service Implmentation !!!!"+e);
			logger.error("Exception in listApplication !!!  "+e);
			throw ae;
			
		}

		return categoryList;

	

	}



	/**
	 * 
	 * This method Get the all available Application by Category Id.
	 * 
	 */

	public List<Application> listPopularApplication(String categoryId) throws AppException{
		List<Application> appList = null;
		Application app = null;
		try {
			appList = new ArrayList<Application>();
			
			String requestPath =  ResourceUtil.getPopularapplistbycategory()+categoryId;
			
		    System.out.println("webservice call starts with request path "+requestPath);
			JSONObject allAvailAppJson = new JSONObject(
					serviceInterfaceDelegate.processGetRestRequest(requestPath));

			System.out.println("webservice call ends starts with request path "+requestPath);
			JSONArray jsonArray = allAvailAppJson.getJSONArray("applicationList");
			for (int i = 0; i < jsonArray.length(); i++) {
				app = new Application();
				JSONObject jsonobj = jsonArray.getJSONObject(i);
				
				String description =jsonobj.get("appDesc").toString();
				if(description.length()>50){
					description=description.substring(0, 50)+"...";
					
				}
				
				app.setSeqNo(jsonobj.getJSONObject("appKey").get("seqNo").toString());
				app.setAppId(jsonobj.getJSONObject("appKey").get("appId").toString());
				app.setAppDesc(description);
				app.setAppName(jsonobj.get("appName").toString().trim());
				app.setAppURL((jsonobj.get("appURL").toString()).trim());
				app.setLoggedInAccess((jsonobj.get("loggedInAccess").toString()).trim());
				appList.add(app);
			}

		}
		catch (JSONException je) {
			AppException ae = new AppException();
			ae.setExceptionType("Service End Point");
			ae.setExceptionCode("QLEXception 001");
			ae.setExceptionMessage("Excetion in JSON PArsing Service Implmentation !!!!"+je);
			logger.error("Exception in listUserAppByUserId !!!  "+je);
			throw ae;
			
		}
		catch (AppException ae) {
			throw ae;
			
		}catch (Exception e) {
			AppException ae = new AppException();
			ae.setExceptionType("Service End Point");
			ae.setExceptionCode("QLEXception 001");
			ae.setExceptionMessage("Excetion in Service Implmentation !!!!"+e);
			logger.error("Exception in listApplication !!!  "+e);
			throw ae;
			
		}

		return appList;

	}

	
	
	public List<Application> listApplication() throws AppException{
		List<Application> appList = null;
		Application app = null;
		try {
			appList = new ArrayList<Application>();
			
			String requestPath =  ResourceUtil.getAvailableapplisturl();
			
		    System.out.println("webservice call starts with request path "+requestPath);
			JSONObject allAvailAppJson = new JSONObject(
					serviceInterfaceDelegate.processGetRestRequest(requestPath));

			System.out.println("webservice call ends starts with request path "+requestPath);
			JSONArray jsonArray = allAvailAppJson.getJSONArray("applicationList");
			for (int i = 0; i < jsonArray.length(); i++) {
				app = new Application();
				JSONObject jsonobj = jsonArray.getJSONObject(i);
				
				String description =jsonobj.get("appDesc").toString();
				if(description.length()>50){
					description=description.substring(0, 50)+"...";
					
				}
				
				app.setSeqNo(jsonobj.getJSONObject("appKey").get("seqNo").toString());
				app.setAppId(jsonobj.getJSONObject("appKey").get("appId").toString());
				app.setAppDesc(description);
				app.setAppName(jsonobj.get("appName").toString().trim());
				app.setAppURL((jsonobj.get("appURL").toString()).trim());
				app.setLoggedInAccess((jsonobj.get("loggedInAccess").toString()).trim());
				appList.add(app);
			}

		}
		catch (JSONException je) {
			AppException ae = new AppException();
			ae.setExceptionType("Service End Point");
			ae.setExceptionCode("QLEXception 001");
			ae.setExceptionMessage("Excetion in JSON PArsing Service Implmentation !!!!"+je);
			logger.error("Exception in listUserAppByUserId !!!  "+je);
			throw ae;
			
		}
		catch (AppException ae) {
			throw ae;
			
		}catch (Exception e) {
			AppException ae = new AppException();
			ae.setExceptionType("Service End Point");
			ae.setExceptionCode("QLEXception 001");
			ae.setExceptionMessage("Excetion in Service Implmentation !!!!"+e);
			logger.error("Exception in listApplication !!!  "+e);
			throw ae;
			
		}
		


		return appList;

	}


}

