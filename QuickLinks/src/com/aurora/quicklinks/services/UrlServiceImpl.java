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
import com.aurora.quicklinks.beans.CompleteURLBean;
import com.aurora.quicklinks.beans.UrlBean;
import com.aurora.quicklinks.beans.UrlFormBean;
import com.aurora.quicklinks.dao.UrlDao;
import com.aurora.quicklinks.delegates.ServiceInterfaceDelegate;


@Service(value = "urlService")
public class UrlServiceImpl implements UrlService {

	@Autowired
	private UrlDao urlDAO;
	
	@Autowired
	private ServiceInterfaceDelegate serviceInterfaceDelegate;

	public List<UrlBean> listUrlBean(String userid) {
		return urlDAO.listUrlBean(userid);

	}

	public List<CompleteURLBean> listCopmpleteUrlBean() {
		return urlDAO.listCopmpleteUrlBean();

	}

	public void updateUrl(List<UrlBean> listBean) {
		urlDAO.updateUrl(listBean);
	}
	
	public List<Application> listCompleteUrlBeanV(){
		
		List<Application> appList = null;
		Application app = null;
		try {
			appList = new ArrayList<Application>();
			JSONObject completeUrlJson = new JSONObject(serviceInterfaceDelegate.processRequestCache("test", false,
					null, null));
			System.out.println("completeUrlJson!!!!"+completeUrlJson);
		 	JSONArray jsonArray = completeUrlJson.getJSONArray("applicationList");
			for(int i=0;i<jsonArray.length();i++){
				app = new Application();
				JSONObject jsonobj = jsonArray.getJSONObject(i);
				System.out.println(jsonArray.get(i)+jsonArray.get(i).toString());
				//JSONObject jsonobj = new JSONObject(jsonArray.get(i).toString());
				app.setAppDesc(jsonobj.get("appDesc").toString().trim());
				app.setAppName(jsonobj.get("appName").toString().trim());
				app.setAppURL((jsonobj.get("appURL").toString()).trim());
				System.out.println(jsonobj.get("appDesc"));
				System.out.println(jsonobj.get("appName"));
				System.out.println(jsonobj.get("appURL"));
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
}
