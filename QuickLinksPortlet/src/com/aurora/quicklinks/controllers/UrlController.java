package com.aurora.quicklinks.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.portlet.ActionResponse;
import javax.portlet.ResourceResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.aurora.quicklinks.beans.Application;
import com.aurora.quicklinks.beans.MenuApp;
import com.aurora.quicklinks.beans.UrlFormBean;
import com.aurora.quicklinks.beans.UserApplication;
import com.aurora.quicklinks.services.UrlService;

@Controller(value = "urlController")
@RequestMapping(value = "VIEW")
@SessionAttributes(types = UserApplication.class)
public class UrlController {

	@Autowired
	@Qualifier("urlService")
	private UrlService urlService;

	public void setUrlService(UrlService urlService) {
		this.urlService = urlService;
	}

	public UrlService getUrlService() {
		return urlService;
	}

	@ModelAttribute("urlFormBean")
	public UrlFormBean getCommandObject() {
		System.out.println("Creating urlFormBean command object");
		UrlFormBean urlFormBean = new UrlFormBean();
		String userid= "28355";
		urlFormBean.setListUrlBean(retrieveAvailMenuApps("EMP",userid));

		return urlFormBean;
	}

	@ResourceMapping(value = "quicklinksEditList")
	public ModelAndView showQuickLinkForm(ResourceResponse response) {
		System.out.println("Creating urlFormBean command object");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("urledit");
		return mav;
	}

	/*
	 * @RenderMapping(params = "action=editUrl") public String
	 * showQuickLinkForm(RenderResponse response) {
	 * System.out.println("Creating urlFormBean command object"); return
	 * "urledit"; }
	 */

	@ActionMapping(params = "action=updateUrl")
	public void updateUrl(@ModelAttribute UrlFormBean urlFormBean,
			BindingResult bindingResult, ActionResponse response,
			SessionStatus sessionStatus) {
		
		
		
		boolean update = false;
		String userid = "28355";
		UserApplication urlBean = null;
		UserApplication userApp = null;
		String activecd = "";
		System.out.println("Inside updateUrl action method");
		List<UserApplication> listUrlBean = new ArrayList<UserApplication>();
		for (MenuApp menuapp : urlFormBean.getListUrlBean()) {
			
			
			Application bean = menuapp.getApp();
			String appId = bean.getAppId().trim();
			System.out.println("url controller appid from userinterface"
					+ appId);
			String seqNo = bean.getSeqNo().trim();
			System.out.println("printing appName"+bean.getAppName());
			System.out.println("printing appId in updateUrl"+appId);
			System.out.println("printing seqno in updateUrl"+seqNo);
		if( appId!=null &&  seqNo!=null ) {
			
			
			userApp = urlService.retrieveUserApp(userid,appId, seqNo);
			
			System.out.println("bean is checked condition checking"+bean.isChecked()+"userapp!!! "+userApp);
			                           
			if (bean.isChecked()) {
				
				//userApp = urlService.retrieveUserApp(userid,
					//	appId, seqNo);
				System.out.println("Retrieving UserApp in  Urlcontroller#############" + userApp);
				if (null == userApp) {
					
					System.out.println("Creating  UserApp in  Urlcontroller#############" + userApp);
					userApp = urlService.createUserApp(userid, appId, seqNo);
				} 
				System.out.println("printing dat from userApp"+userApp.getActiveCd()+userApp.getAppId()+userApp.getSeqNo());
				System.out.println("chcking active cd if it is true then it wont enter below condition"+userApp.isActive());
				      
				if (!userApp.isActive()) {
		            userApp.setActiveCd("A");
		            if (Integer.parseInt(seqNo) == 0) {
		              //enrollAutoRegSubApps(userid, appId);
		             // callAppRegistrationURL(form, userid, appId, Integer.valueOf(0));
		            }
		            update = true;
		          }
		         /* if (Integer.parseInt(seqNo) == 0) {
		            if (userApp.getDispSeq().intValue() != appCount) {
		              userApp.setDispSeq(Integer.valueOf(appCount));
		              update = true;
		            }
		            appCount--;
		          }*/
		         // else if (userApp.getDispSeq().intValue() != 0);
		        }
		        else if ((userApp != null) && (userApp.isActive())) {
		          userApp.setActiveCd("I");

		          update = true;
		        }

		        if (update) {
		        	System.out.println("entering update method********");
		          urlService.updateUserApp(userApp,userid);
		        }
				
				
		
			}
		}
		
		// urlService.updateUrl(listUrlBean);
		response.setRenderParameter("action", "list");
		sessionStatus.setComplete();

	}

	public List<MenuApp> retrieveAvailMenuApps(String roleCd, String userid) {
		List<MenuApp> menuAppsList = new ArrayList<MenuApp>();

		List availAppsList = urlService.listEditUrlBeanV();
		List userAppsList;
		Iterator i$;
		if ((availAppsList != null) && (!availAppsList.isEmpty())) {
			userAppsList = urlService.listCompleteUrlBeanV(userid);

			for (i$ = availAppsList.iterator(); i$.hasNext();) {
				Object anAvailAppsList = i$.next();
				Application app = (Application) anAvailAppsList;
				MenuApp menuApp = new MenuApp();
				menuApp.setApp(app);

				for (Iterator i = userAppsList.iterator(); i.hasNext();) {
					Object anUserAppsList = i.next();
					UserApplication userApp = (UserApplication) anUserAppsList;
					if ((userApp.getAppId().equals(app.getAppId()))
							&& (userApp.getSeqNo().equals(app.getSeqNo()))) {
						menuApp.setUserApp(userApp);
						break;
					}
				}

				/*if ((menuApp.getUserApp() != null)) {
					menuAppsList.add(menuApp);
				}*/

				/*
				 * if ((!app.getLoggedInAccess().equals("R")) ||
				 * (menuApp.getUserApp() != null)) { menuAppsList.add(menuApp);
				 * }
				 */
				//System.out.println("printing logged in access"+app.getLoggedInAccess().equals("R"));
				if ((!app.getLoggedInAccess().equals("R")) || (menuApp.getUserApp() != null)){
					
				menuAppsList.add(menuApp);
				
				}
			}
		}
		System.out.println("MEnuAppsList!!!!!!!"+menuAppsList);
		return menuAppsList;
	}
	
	
	
	
	public void enrollAutoRegSubApps(String userid, String appId) {
		
		List appList = urlService.retrieveAppMenuAutoList(appId);
		Iterator i$;
		if ((appList != null) && (!appList.isEmpty()))
			for (i$ = appList.iterator(); i$.hasNext();) {
				Object anAppList = i$.next();
				Application app = (Application) anAppList;
				UserApplication userApp = urlService.retrieveUserApp(userid,
						app.getAppId(),app.getSeqNo());
				
				if (userApp == null) {
					urlService.createUserApp(userid,app.getAppId() ,app.getSeqNo());
					
				}
				if (!userApp.isActive()) {
					userApp.setActiveCd("A");
					userApp.setDispSeq((Integer.parseInt(userApp.getSeqNo())));
					//userAppService.updateUserApp(userApp);
				}
			}
	   }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}