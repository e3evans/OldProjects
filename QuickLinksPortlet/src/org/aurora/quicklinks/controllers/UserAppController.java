package org.aurora.quicklinks.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.ResourceResponse;

import org.apache.log4j.Logger;
import org.aurora.quicklinks.beans.AppFormBean;
import org.aurora.quicklinks.beans.Application;
import org.aurora.quicklinks.beans.MenuApp;
import org.aurora.quicklinks.beans.UserApplication;
import org.aurora.quicklinks.services.AppService;
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
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

@Controller(value = "editUserAppController")
@RequestMapping(value = "VIEW")
@SessionAttributes(types = AppFormBean.class)
public class UserAppController {

	protected final Logger logger = Logger.getLogger(UserAppController.class);

	@Autowired
	@Qualifier("appService")
	private AppService appService;

	@ModelAttribute("appFormBean")
	public AppFormBean getCommandObject(PortletRequest request) {
		AppFormBean appFormBean = new AppFormBean();
		try {
			Principal user = request.getUserPrincipal();
			PortletSession session = request.getPortletSession();
			session.setAttribute("userId", user.toString());
			String userid = user.toString();
			appFormBean.setListMenuApp(retrieveAvailMenuApps(userid));
		} catch (Exception e) {
			logger.error("Exception in getCommandObject", e);
		}
		return appFormBean;
	}

	@RenderMapping
	public String showUserApplication(RenderRequest request) {
		try {
			String errorMsg = request.getParameter("errorMsg");
			if (null != errorMsg) {
				request.setAttribute("errorMsg", errorMsg);
			}
		} catch (Exception e) {
			logger.error("Exception in showUserApplication", e);
		}
		return "userapp";
	}

	@ResourceMapping(value = "quicklinksEditList")
	public ModelAndView showQuickLinkForm(ResourceResponse response) {
		return new ModelAndView("appedit");
	}

	@ActionMapping(params = "action=updateUrl")
	public void updateApp(@ModelAttribute AppFormBean appFormBean,
			BindingResult bindingResult, ActionRequest request,
			ActionResponse response, SessionStatus sessionStatus) {
		try {
			PortletSession session = request.getPortletSession();
			String userid = (String) session.getAttribute("userId");
			request.getPortletSession().setAttribute("flag", "true");

			// collect checked apps
			List<Application> checkedApps = new ArrayList<Application>();
			for (MenuApp menuapp : appFormBean.getListMenuApp()) {
				Application bean = menuapp.getApp();
				String appId = bean.getAppId().trim();
				String seqNo = bean.getSeqNo().trim();
				if (appId != null && seqNo != null && bean.isChecked()) {
					checkedApps.add(bean);
				}
			}

			// get all user apps plus inactives
			List<UserApplication> listAllUserApps = appService
					.listAllUserAppByUserId(userid);

			// create link for checked apps not found for user
			for (Application app2 : checkedApps) {
				boolean create = true;
				for (UserApplication userApp2 : listAllUserApps) {
					if (((userApp2.getAppId().trim() + userApp2.getSeqNo()
							.trim()).equalsIgnoreCase(app2.getAppId().trim()
							+ app2.getSeqNo().trim()))) {
						// found checked app in user list
						create = false;
						break;
					}
				}

				// create link for user
				if (create) {
					appService.createUserApp(userid, app2.getAppId().trim(),
							app2.getSeqNo().trim());
				}
			}

			// iterate user apps to see if they contain checked apps
			for (UserApplication userApp : listAllUserApps) {
				boolean found = false;
				boolean activate = false;
				for (Application app : checkedApps) {
					if (((userApp.getAppId().trim() + userApp.getSeqNo().trim())
							.equalsIgnoreCase(app.getAppId().trim()
									+ app.getSeqNo().trim()))) {
						found = true;
						if (!userApp.isActive()) {
							activate = true;
						}
						break;
					}
				}

				// inactivate links not found to be checked
				if (!found) {
					userApp.setActiveCd("I");
					appService.updateUserApp(userApp, userid);
				}

				// activate inactive links
				if (activate) {
					userApp.setActiveCd("A");
					appService.updateUserApp(userApp, userid);
				}
			}
		} catch (Exception e) {
			logger.error("Exception in updateApp", e);
			response.setRenderParameter("error", "");
		}
		response.setRenderParameter("action", "list");
		sessionStatus.setComplete();
	}

	@ActionMapping(params = "action=updateFeaturedApp")
	public void updateFeaturedApp(ActionRequest request,
			ActionResponse response, SessionStatus sessionStatus) {
		try {
			PortletSession session = request.getPortletSession();
			String userid = (String) session.getAttribute("userId");
			request.getPortletSession().setAttribute("flag", "true");
			List<UserApplication> listUserApp = appService
					.listAllUserAppByUserId(userid);
			String appId = null;
			String seqNo = null;
			PortletPreferences pf = request.getPreferences();
			appId = pf.getValue("appID", "Not Set").trim();
			seqNo = pf.getValue("seqNO", "Not Set").trim();
			boolean toggleActive = false;
			boolean isCreate = true;
			if (appId == "Not Set" && seqNo == "Not Set") {
				isCreate = false;
			}
			for (UserApplication userApp2 : listUserApp) {
				if ((userApp2.getAppId().trim() + userApp2.getSeqNo().trim())
						.equalsIgnoreCase(appId.trim() + seqNo.trim())) {
					isCreate = false;
					if (!userApp2.isActive()) {
						toggleActive = true;
					}
				}
				if (toggleActive) {
					toggleActive = false;
					userApp2.setActiveCd("A");
					appService.updateUserApp(userApp2, userid);
				}
			}
			if (isCreate) {
				appService.createUserApp(userid, appId, seqNo);
			}
		} catch (Exception e) {
			logger.error("Exception in updateApp", e);
			response.setRenderParameter("error", "");
		}
		response.setRenderParameter("action", "list");
		sessionStatus.setComplete();
	}

	public List<MenuApp> retrieveAvailMenuApps(String userid) throws Exception {
		List<MenuApp> menuAppsList = new ArrayList<MenuApp>();
		// get all available iconnect apps
		List<Application> availAppsList = appService.listApplication();
		if ((availAppsList != null) && (!availAppsList.isEmpty())) {
			// get all user apps plus inactives
			List<UserApplication> userAppsList = appService
					.listAllUserAppByUserId(userid);
			for (Application app : availAppsList) {
				MenuApp menuApp = new MenuApp(app);
				if (userAppsList != null) {
					for (UserApplication userApp : userAppsList) {
						if ((userApp.getAppId().trim() + userApp.getSeqNo()
								.trim()).equalsIgnoreCase(app.getAppId().trim()
								+ app.getSeqNo().trim())) {
							menuApp.setUserApp(userApp);
							break;
						}
					}
				}

				// only add if app allows adding to menu
				// or granted access
				if ((!app.getLoggedInAccess().equals("R"))
						|| (menuApp.getUserApp() != null)) {
					menuAppsList.add(menuApp);
				}
			}
		}
		Collections.sort(menuAppsList, MenuApp.APP_COMPARATOR);
		return menuAppsList;
	}

	// TODO: is this method used???
	// public void enrollAutoRegSubApps(PortletRequest request, String userid,
	// String appId) throws Exception {
	//
	// List<Application> appList = appService.retrieveAppMenuAutoList(appId);
	// Iterator<Application> i$;
	// if ((appList != null) && (!appList.isEmpty()))
	// for (i$ = appList.iterator(); i$.hasNext();) {
	// Object anAppList = i$.next();
	// Application app = (Application) anAppList;
	// UserApplication userApp = appService.retrieveUserApp(userid,
	// app.getAppId(),
	// app.getSeqNo());
	//
	// if (userApp == null) {
	// appService.createUserApp(getBaseUrl(request), userid,
	// app.getAppId(), app.getSeqNo());
	//
	// }
	// if (!userApp.isActive()) {
	// userApp.setActiveCd("A");
	// userApp.setDispSeq((Integer.parseInt(userApp.getSeqNo())));
	// // userAppService.updateUserApp(userApp);
	// }
	// }
	// }
}