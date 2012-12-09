package com.aurora.quicklinks.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.httpclient.HttpException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aurora.exceptions.AppException;
import com.aurora.quicklinks.beans.Application;
import com.aurora.quicklinks.beans.UpdateUserAppBean;
import com.aurora.quicklinks.beans.UserApplication;
import com.aurora.quicklinks.delegates.ServiceInterfaceDelegate;
import com.aurora.quicklinks.util.ResourceUtil;

@Service(value = "appService")
public class AppServiceImpl implements AppService {

	@Autowired
	private ServiceInterfaceDelegate serviceInterfaceDelegate;

	/**
	 * 
	 * 
	 * 
	 */
	public List<UserApplication> listUserAppByUserId(String userid) throws AppException {
		System.out.println("getting user applist*********");
		List<UserApplication> appList = null;
		UserApplication app = null;
		try {
			appList = new ArrayList<UserApplication>();
			String requestPath = ResourceUtil.getUserapplisturl()+userid;
			 System.out.println("webservice call starts with request path "+requestPath);
			JSONObject userAppJSON = new JSONObject(serviceInterfaceDelegate.processGetRestRequest(requestPath));
			 System.out.println("webservice call ends with request path "+requestPath);
			JSONArray jsonArray = userAppJSON.getJSONArray("userAppList");
			for (int i = 0; i < jsonArray.length(); i++) {
				app = new UserApplication();
				JSONObject jsonobj = jsonArray.getJSONObject(i);
				app.setUserid(jsonobj.get("userId").toString().trim());
				app.setAppName(jsonobj.get("appName").toString().trim());
				app.setAppURL((jsonobj.get("appUrl").toString()).trim());
				app.setSeqNo(jsonobj.get("seqNo").toString());
				app.setAppId(jsonobj.get("appId").toString());
				app.setActiveCd((jsonobj.get("activeCd").toString()).trim());
				appList.add(app);
			}

		} catch (JSONException je) {
			AppException ae = new AppException();
			ae.setExceptionType("Service End Point");
			ae.setExceptionCode("QLEXception 001");
			ae.setExceptionMessage("Excetion in JSON PArsing Service Implmentation !!!!"+je);
			System.out.println("Exception in listUserAppByUserId !!!  "+je);
			throw ae;
			
		}
		catch (AppException ae) {
			throw ae;
			
		}catch (Exception e) {
			AppException ae = new AppException();
			ae.setExceptionType("Service End Point");
			ae.setExceptionCode("QLEXception 001");
			ae.setExceptionMessage("Excetion in Service Implmentation !!!!"+e);
			System.out.println("Exception in listUserAppByUserId !!!  "+e);
			throw ae;
			
		}
		

		return appList;

	}

	
	/**
	 * 
	 * This method Get the all active and inactive user saved Quicklinks.
	 * 
	 */
	public List<UserApplication> listAllUserAppByUserId(String userid) throws AppException {
		System.out.println("getting user applist*********");
		List<UserApplication> appList = null;
		UserApplication app = null;
		try {
			appList = new ArrayList<UserApplication>();
			String requestPath = ResourceUtil.getAlluserapplisturl()+userid;
			 System.out.println("webservice call starts with request path "+requestPath);
			JSONObject userAppJSON = new JSONObject(
					serviceInterfaceDelegate.processGetRestRequest(requestPath));
			 System.out.println("webservice call ends with request path "+requestPath);
			JSONArray jsonArray = userAppJSON.getJSONArray("userAppList");
			for (int i = 0; i < jsonArray.length(); i++) {
				app = new UserApplication();
				JSONObject jsonobj = jsonArray.getJSONObject(i);
				app.setUserid(jsonobj.get("userId").toString().trim());
				app.setAppName(jsonobj.get("appName").toString().trim());
				app.setAppURL((jsonobj.get("appUrl").toString()).trim());
				app.setSeqNo(jsonobj.get("seqNo").toString());
				app.setAppId(jsonobj.get("appId").toString());
				app.setActiveCd((jsonobj.get("activeCd").toString()).trim());
				appList.add(app);
			}

		} catch (JSONException je) {
			AppException ae = new AppException();
			ae.setExceptionType("Service End Point");
			ae.setExceptionCode("QLEXception 001");
			ae.setExceptionMessage("Excetion in JSON PArsing Service Implmentation !!!!"+je);
			System.out.println("Exception in listUserAppByUserId !!!  "+je);
			throw ae;
			
		}
		catch (AppException ae) {
			throw ae;
			
		}catch (Exception e) {
			AppException ae = new AppException();
			ae.setExceptionType("Service End Point");
			ae.setExceptionCode("QLEXception 001");
			ae.setExceptionMessage("Excetion in Service Implmentation !!!!"+e);
			System.out.println("Exception in listUserAppByUserId !!!  "+e);
			throw ae;
			
		}
		

		return appList;

	}	
	
	/**
	 * 
	 * This method Get the all available Quicklinks.
	 * 
	 */

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
			System.out.println("Exception in listUserAppByUserId !!!  "+je);
			throw ae;
			
		}
		catch (AppException ae) {
			throw ae;
			
		}catch (Exception e) {
			AppException ae = new AppException();
			ae.setExceptionType("Service End Point");
			ae.setExceptionCode("QLEXception 001");
			ae.setExceptionMessage("Excetion in Service Implmentation !!!!"+e);
			System.out.println("Exception in listApplication !!!  "+e);
			throw ae;
			
		}
		


		return appList;

	}

	/**
	 * Retrieve user app(sub apps)
	 */
	public UserApplication retrieveUserApp(String userid, String appId,
			String seqNo) throws AppException{

		System.out.println("getting userapp *");

		UserApplication app = null;
		try {
            String requestPath = ResourceUtil.getRetrieveuserappurl()+appId+"/"+seqNo+"/"+userid;
            System.out.println("webservice call starts with request path "+requestPath);
			JSONObject jsonobj = new JSONObject(serviceInterfaceDelegate.processGetRestRequest(requestPath));
			 System.out.println("webservice call ends with request path "+requestPath);
			if (jsonobj.get("appId") != null
					&& !jsonobj.get("appId").toString().trim()
							.equalsIgnoreCase("null")) {
				app = new UserApplication();
				app.setUserid(jsonobj.get("userId").toString().trim());
				app.setAppName(jsonobj.get("appName").toString().trim());
				app.setAppId((jsonobj.get("appId").toString()).trim());
				if (jsonobj.get("activeCd") != null) {
					app.setActiveCd(jsonobj.get("activeCd").toString());
					app.setSeqNo(jsonobj.get("seqNo").toString());
				}
				// app.setDispSeq( jsonobj.get("dispSeq"));
				System.out.println(jsonobj.get("userId"));
				System.out.println(jsonobj.get("appName"));
				System.out.println(jsonobj.get("appUrl"));
				System.out.println(jsonobj.get("activeCd"));
				System.out.println(jsonobj.get("dispSeq"));
			}

			// }

		}catch (JSONException je) {
			AppException ae = new AppException();
			ae.setExceptionType("Service End Point");
			ae.setExceptionCode("QLEXception 001");
			ae.setExceptionMessage("Excetion in JSON PArsing Service Implmentation !!!!"+je);
			System.out.println("Exception in listUserAppByUserId !!!  "+je);
			throw ae;
			
		} catch (AppException ae) {
			throw ae;
			
		}catch (Exception e) {
			AppException ae = new AppException();
			ae.setExceptionType("Service End Point");
			ae.setExceptionCode("QLEXception 001");
			ae.setExceptionMessage("Excetion in Service Implmentation !!!!"+e);
			System.out.println("Exception in retrieveUserApp !!!  "+e);
			throw ae;
			
		}
		


		return app;

	}

	/**
	 * reating and retrieving userapp
	 */

	public UserApplication createUserApp(String userid, String appId,
			String seqNo) throws AppException{
		// TODO Auto-generated method stub

		UserApplication app = null;
		try {
            String requestPath = ResourceUtil.getCreateuserappurl()+appId+"/"+seqNo+"/"+userid;
			
            System.out.println("webservice call starts with request path "+requestPath);
			JSONObject jsonobj = new JSONObject(serviceInterfaceDelegate.processGetRestRequest(requestPath));
			System.out.println("webservice call ends with request path "+requestPath);
			
			if (jsonobj.get("appId") != null
					&& !jsonobj.get("appId").toString().trim()
							.equalsIgnoreCase("null")) {
				app = new UserApplication();
				app.setUserid(jsonobj.get("userId").toString().trim());
				app.setAppName(jsonobj.get("appName").toString().trim());
				app.setAppId((jsonobj.get("appId").toString()).trim());
				app.setActiveCd(jsonobj.get("activeCd").toString());
				app.setSeqNo(jsonobj.get("seqNo").toString());
				
			}

		} catch (AppException ae) {
			throw ae;
			
		}catch (Exception e) {
			AppException ae = new AppException();
			ae.setExceptionType("Service End Point");
			ae.setExceptionCode("QLEXception 001");
			ae.setExceptionMessage("Excetion in Service Implmentation !!!!"+e);
			System.out.println("Exception in createUserApp !!!  "+e);
			throw ae;
			
		}
		


		return app;

	}

	public List retrieveAppMenuAutoList(String appId) throws AppException{
		List<Application> appList = null;
		Application app = null;
		try {
			appList = new ArrayList<Application>();
			String requestPath = ResourceUtil.getAppautolisturl()+appId;
			 System.out.println("webservice call starts with request path "+requestPath);
			JSONObject appAutoListJson = new JSONObject(
					serviceInterfaceDelegate
							.processGetRestRequest(requestPath));
             System.out.println("webservice call starts with request path "+requestPath);
			JSONArray jsonArray = appAutoListJson
					.getJSONArray("applicationList");
			for (int i = 0; i < jsonArray.length(); i++) {
				app = new Application();
				JSONObject jsonobj = jsonArray.getJSONObject(i);
				app.setSeqNo(jsonobj.getJSONObject("appKey").get("seqNo").toString());
				app.setAppId(jsonobj.getJSONObject("appKey").get("appId").toString());
				app.setAppDesc(jsonobj.get("appDesc").toString().trim());
				app.setAppName(jsonobj.get("appName").toString().trim());
				app.setAppURL((jsonobj.get("appURL").toString()).trim());
				app.setLoggedInAccess((jsonobj.get("loggedInAccess")
								.toString()).trim());
				appList.add(app);
			}

		}catch (JSONException je) {
			AppException ae = new AppException();
			ae.setExceptionType("Service End Point");
			ae.setExceptionCode("QLEXception 001");
			ae.setExceptionMessage("Excetion in JSON PArsing Service Implmentation !!!!"+je);
			System.out.println("Exception in listUserAppByUserId !!!  "+je);
			throw ae;
			
		} catch (AppException ae) {
			throw ae;
			
		}catch (Exception e) {
			AppException ae = new AppException();
			ae.setExceptionType("Service End Point");
			ae.setExceptionCode("QLEXception 001");
			ae.setExceptionMessage("Excetion in Service Implmentation !!!!"+e);
			System.out.println("Exception in createUserApp !!!  "+e);
			throw ae;
			
		}
		

		return appList;

	}

	// / For user app update

	public void updateUserApp(UserApplication userApp, String userId) throws AppException{
		try {

			String requestPath = ResourceUtil.getUpdateuserappurl()+userApp.getAppId().trim()+"/"+userApp.getSeqNo().trim()+"/"+userId+"/"+userApp.getActiveCd().trim();
			 System.out.println("webservice call starts with request path "+requestPath);
			serviceInterfaceDelegate.processGetRestRequest(requestPath);
			 System.out.println("webservice call starts with request path "+requestPath);
		} catch (AppException ae) {
			throw ae;
			
		}catch (Exception e) {
			AppException ae = new AppException();
			ae.setExceptionType("Service End Point");
			ae.setExceptionCode("QLEXception 001");
			ae.setExceptionMessage("Excetion in Service Implmentation !!!!"+e);
			System.out.println("Exception in createUserApp !!!  "+e);
			throw ae;
			
		}
		

	}
	
	
	

}
