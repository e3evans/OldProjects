package com.aurora.quicklinks.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
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

import com.aurora.exceptions.AppException;
import com.aurora.quicklinks.beans.AppFormBean;
import com.aurora.quicklinks.beans.Application;
import com.aurora.quicklinks.beans.MenuApp;
import com.aurora.quicklinks.beans.UserApplication;
import com.aurora.quicklinks.services.AppService;

@Controller(value = "editUserAppController")
@RequestMapping(value = "VIEW")
@SessionAttributes(types = UserApplication.class)
public class EditUserAppController {

	@Autowired
	@Qualifier("appService")
	private AppService appService;

	public void setAppService(AppService urlService) {
		this.appService = appService;
	}

	public AppService getAppService() {
		return appService;
	}

	@ModelAttribute("appFormBean")
	public AppFormBean getCommandObject(PortletRequest request) {
		PortletSession session = request.getPortletSession();
		String userid= (String) session.getAttribute("userId");
		AppFormBean appFormBean = new AppFormBean();
		try {
			System.out.println("printing userid in EditUserAppController"+userid);
			appFormBean.setListMenuApp(retrieveAvailMenuApps("EMP", userid));
		} catch (AppException ae) {
			System.out.println(ae.getExceptionDesc());
			System.out.println(ae.getExceptionCode());
			System.out.println(ae.getExceptionType());
			System.out.println(ae.getExceptionMessage());
		}

		return appFormBean;
	}

	@ResourceMapping(value = "quicklinksEditList")
	public ModelAndView showQuickLinkForm(ResourceResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("appedit");
		return mav;
	}

	@ActionMapping(params = "action=updateUrl")
	public void updateApp(@ModelAttribute AppFormBean appFormBean,
			BindingResult bindingResult, ActionRequest request ,ActionResponse response,
			SessionStatus sessionStatus) {
		PortletSession session =request.getPortletSession();
		String userid = (String) session.getAttribute("userId");
		boolean update = false;
		UserApplication userApp = null;
		List<Application> updateduserapp = new ArrayList<Application>();
		System.out.println("Inside updateUrl action method user="+userid);
		try {
			List<UserApplication> listUserApp = appService
					.listAllUserAppByUserId(userid);
			for (UserApplication app : listUserApp) {
				System.out.println("App Info : " + app);
			}
			for (MenuApp menuapp : appFormBean.getListMenuApp()) {

				Application bean = menuapp.getApp();
				String appId = bean.getAppId().trim();
				String seqNo = bean.getSeqNo().trim();
				System.out.println("printing appId in edituserappcontroller"+appId);
				System.out.println("printing seqno in edituserappcontroller"+seqNo);

				if (appId != null && seqNo != null) {

					if (bean.isChecked()) {

						updateduserapp.add(bean);

					}

				}
			}

			for (UserApplication userApp2 : listUserApp) {
				boolean isInActive = true;
				boolean toggleActive = false;
				for (Application app2 : updateduserapp) {
					if ((userApp2.getAppId() + userApp2.getSeqNo()).equals(app2
							.getAppId() + app2.getSeqNo())) {
						isInActive = false;
						if (!userApp2.isActive()) {
							toggleActive = true;
						}

					}
				}

				if (isInActive) {
					userApp2.setActiveCd("I");

					appService.updateUserApp(userApp2, userid);
				}
				if (toggleActive) {
					userApp2.setActiveCd("A");

					appService.updateUserApp(userApp2, userid);
				}
			}
			List<Application> createList = new ArrayList<Application>();
			for (Application app2 : updateduserapp) {
				boolean isCreate = true;
				;
				for (UserApplication userApp2 : listUserApp) {
					if ((userApp2.getAppId() + userApp2.getSeqNo()).equals(app2
							.getAppId() + app2.getSeqNo())) {
						isCreate = false;

					}
				}

				if (isCreate) {
					String appId = app2.getAppId().trim();
					String seqNo = app2.getSeqNo().trim();
					appService.createUserApp(userid, appId, seqNo);
				}
			}

		} catch (AppException ae) {
			System.out.println(ae.getExceptionDesc());
			System.out.println(ae.getExceptionCode());
			System.out.println(ae.getExceptionType());
			System.out.println(ae.getExceptionMessage());
			response.setRenderParameter("errorMsg", ae.getExceptionMessage());
		} catch (Exception ex) {
			System.out.println("Exception in updateApp");
			response.setRenderParameter("error", "");
		}

		// urlService.updateUrl(listUrlBean);
		response.setRenderParameter("action", "list");
		sessionStatus.setComplete();

	}

	public List<MenuApp> retrieveAvailMenuApps(String roleCd, String userid)
			throws AppException {
		List<MenuApp> menuAppsList = new ArrayList<MenuApp>();

		List availAppsList = appService.listApplication();
		List userAppsList;
		Iterator i$;
		if ((availAppsList != null) && (!availAppsList.isEmpty())) {
			userAppsList = appService.listUserAppByUserId(userid);

			for (i$ = availAppsList.iterator(); i$.hasNext();) {
				Object anAvailAppsList = i$.next();
				Application app = (Application) anAvailAppsList;
				MenuApp menuApp = new MenuApp();
				menuApp.setApp(app);
				if (userAppsList != null)
					for (Iterator i = userAppsList.iterator(); i.hasNext();) {
						Object anUserAppsList = i.next();
						UserApplication userApp = (UserApplication) anUserAppsList;
						if ((userApp.getAppId().equals(app.getAppId()))
								&& (userApp.getSeqNo().equals(app.getSeqNo()))) {
							menuApp.setUserApp(userApp);
							break;
						}
					}

				if ((!app.getLoggedInAccess().equals("R"))
						|| (menuApp.getUserApp() != null)) {

					menuAppsList.add(menuApp);

				}
			}
		}

		return menuAppsList;
	}

	public void enrollAutoRegSubApps(String userid, String appId)
			throws AppException {

		List appList = appService.retrieveAppMenuAutoList(appId);
		Iterator i$;
		if ((appList != null) && (!appList.isEmpty()))
			for (i$ = appList.iterator(); i$.hasNext();) {
				Object anAppList = i$.next();
				Application app = (Application) anAppList;
				UserApplication userApp = appService.retrieveUserApp(userid,
						app.getAppId(), app.getSeqNo());

				if (userApp == null) {
					appService.createUserApp(userid, app.getAppId(),
							app.getSeqNo());

				}
				if (!userApp.isActive()) {
					userApp.setActiveCd("A");
					userApp.setDispSeq((Integer.parseInt(userApp.getSeqNo())));
					// userAppService.updateUserApp(userApp);
				}
			}
	}

}