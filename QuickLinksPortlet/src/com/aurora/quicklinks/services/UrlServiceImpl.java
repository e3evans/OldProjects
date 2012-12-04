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
import org.springframework.web.bind.annotation.ModelAttribute;

import com.aurora.quicklinks.beans.Application;

import com.aurora.quicklinks.beans.UrlFormBean;
import com.aurora.quicklinks.beans.UserApplication;
import com.aurora.quicklinks.dao.UrlDao;
import com.aurora.quicklinks.delegates.ServiceInterfaceDelegate;


@Service(value = "urlService")
public class UrlServiceImpl implements UrlService {

	/*@Autowired
	private UrlDao urlDAO;*/
	
	@Autowired
	private ServiceInterfaceDelegate serviceInterfaceDelegate;

	/**
	 * 
	 * This method Get the user saved Quicklinks.
	 * 
	 */
	
	public List<UserApplication> listCompleteUrlBeanV(String userid){
        System.out.println("getting user applist*********");
		List<UserApplication> appList = null;
      	UserApplication app = null;
		try {
			appList = new ArrayList<UserApplication>();
			JSONObject completeUrlJson = new JSONObject(serviceInterfaceDelegate.processRequestCache("test", false,
					null, null,userid));
			//System.out.println("completeUrlJson!!!!"+completeUrlJson);
		 	JSONArray jsonArray = completeUrlJson.getJSONArray("userAppList");
    		for(int i=0;i<jsonArray.length();i++){
				app = new UserApplication();
				JSONObject jsonobj = jsonArray.getJSONObject(i);
				//System.out.println(jsonArray.get(i)+jsonArray.get(i).toString());
				//JSONObject jsonobj = new JSONObject(jsonArray.get(i).toString());
				app.setUserid(jsonobj.get("userId").toString().trim());
				app.setAppName(jsonobj.get("appName").toString().trim());
				app.setAppURL((jsonobj.get("appUrl").toString()).trim());
				//System.out.println(jsonobj.get("userId"));
				//System.out.println(jsonobj.get("appName"));
				//System.out.println(jsonobj.get("appUrl"));
				appList.add(app);
			}
				
		} catch (HttpException e) {
			// TODO Auto-generated catch block
		System.out.println(e);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}catch(Exception e){
			System.out.println(e);
		}
		
		return appList;
		
	}
	
	/**
	 * 
	 * This method Get the all available Quicklinks.
	 * 
	 */		
		
	
public List<Application> listEditUrlBeanV(){
	List<Application> appList = null;
	Application app = null;
	try {
		appList = new ArrayList<Application>();
		JSONObject completeUrlJson = new JSONObject(serviceInterfaceDelegate.processRequestCacheEdit("test", false,
				null, null));
		
		//System.out.println("completeUrlJson!!!!"+completeUrlJson);
	 	JSONArray jsonArray = completeUrlJson.getJSONArray("applicationList");
		for(int i=0;i<jsonArray.length();i++){
			app = new Application();
			JSONObject jsonobj = jsonArray.getJSONObject(i);
			//System.out.println("printing appkey"+jsonobj.getJSONArray("appKey"));
			//System.out.println("printing appkey seq no"+jsonobj.getJSONObject("appKey").get("seqNo"));
			//System.out.println("printing appKey toString1111"+jsonobj.get("seqNo"));
			//System.out.println("printing appKey toString22222"+jsonobj.get("appKey.appId").toString());
			//JSONObject jsonobj = new JSONObject(jsonArray.get(i).toString());
			//app.setAppDesc(jsonobj.get("appDesc").toString().trim());
			//app.setAppDesc(jsonobj.get("appDesc").toString().trim());
			app.setSeqNo(jsonobj.getJSONObject("appKey").get("seqNo").toString());
			app.setAppId(jsonobj.getJSONObject("appKey").get("appId").toString());
			app.setAppDesc(jsonobj.get("appDesc").toString().trim());
			app.setAppName(jsonobj.get("appName").toString().trim());
			app.setAppURL((jsonobj.get("appURL").toString()).trim());
			//System.out.println(jsonobj.get("appDesc"));
			//System.out.println(jsonobj.get("appName"));
			//System.out.println(jsonobj.get("appURL"));
			appList.add(app);
		}
			
	} catch (HttpException e) {
		// TODO Auto-generated catch block
	System.out.println(e);
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		System.out.println(e);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		System.out.println(e);
	}catch(Exception e){
		System.out.println(e);
	}
	
	return appList;
	
}

/**
 * Retrieve user app(sub apps)
 */
	@Override
	public UserApplication retrieveUserApp(String userid, String appId, String seqNo) {
		
	        System.out.println("getting userapp *");
			
	      	UserApplication app = null;
			try {
				
				JSONObject jsonobj = new JSONObject(serviceInterfaceDelegate.processRequestCacheRetrieveUserApp("test", false,
						null, null,userid,appId,seqNo));
				System.out.println("retrieve user app json !!!! "+jsonobj);
			 	//JSONArray jsonArray = completeUrlJson.getJSONArray("userAppList");
	    		//for(int i=0;i<jsonArray.length();i++){
					if(jsonobj.get("appId")!=null&&!jsonobj.get("appId").toString().trim().equalsIgnoreCase("null")){
				app = new UserApplication();
					//JSONObject jsonobj = jsonArray.getJSONObject(i);
					//System.out.println(jsonArray.get(i)+jsonArray.get(i).toString());
					//JSONObject jsonobj = new JSONObject(jsonArray.get(i).toString());
					app.setUserid(jsonobj.get("userId").toString().trim());
					app.setAppName(jsonobj.get("appName").toString().trim());
					app.setAppId((jsonobj.get("appId").toString()).trim());
					System.out.println(jsonobj.get("userId"));
					System.out.println(jsonobj.get("appName"));
					System.out.println(jsonobj.get("appUrl"));
					}
					
				//}
					
			} catch (HttpException e) {
				// TODO Auto-generated catch block
			System.out.println(e);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				System.out.println(e);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println(e);
			}catch(Exception e){
				System.out.println(e);
			}
			
			return app;
			
		}
	
	/**
	 * reating and retrieving userapp
	 */

@Override
public void createUserApp(String userid, String appId, String seqNo) {
	// TODO Auto-generated method stub
	
	UserApplication app = null;
	try {
		
		JSONObject jsonobj = new JSONObject(serviceInterfaceDelegate.processRequestCacheCreateUserApp("",userid,appId,seqNo));
		System.out.println("retrieve user app json !!!! "+jsonobj);
	 	//JSONArray jsonArray = completeUrlJson.getJSONArray("userAppList");
		//for(int i=0;i<jsonArray.length();i++){
			if(jsonobj.get("appId")!=null&&!jsonobj.get("appId").toString().trim().equalsIgnoreCase("null")){
			app = new UserApplication();
			//JSONObject jsonobj = jsonArray.getJSONObject(i);
			//System.out.println(jsonArray.get(i)+jsonArray.get(i).toString());
			//JSONObject jsonobj = new JSONObject(jsonArray.get(i).toString());
			app.setUserid(jsonobj.get("userId").toString().trim());
			app.setAppName(jsonobj.get("appName").toString().trim());
			app.setAppId((jsonobj.get("appId").toString()).trim());
			System.out.println(jsonobj.get("userId"));
			System.out.println(jsonobj.get("appName"));
			System.out.println(jsonobj.get("appUrl"));
			}
				
		
			
	} catch (HttpException e) {
		// TODO Auto-generated catch block
	System.out.println(e);
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		System.out.println(e);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		System.out.println(e);
	}catch(Exception e){
		System.out.println(e);
	}
	
	
	
	    
	  

	
}
	
	
	
	
		
}
	

