package org.aurora.sitesapps.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.log4j.Logger;
import org.aurora.sitesapps.beans.AppCategory;
import org.aurora.sitesapps.beans.AppFormBean;
import org.aurora.sitesapps.beans.Application;
import org.aurora.sitesapps.services.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

@Controller(value = "sitesAppsController")
@RequestMapping(value = "VIEW")
@SessionAttributes(types = AppFormBean.class)
public class CategorySortController {
	private Logger logger = Logger.getLogger(CategorySortController.class);

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
			List<Application> appsByCategory = null;
			List<Application> popularApps = null;
			List<AppCategory> appCategories;
			List<AppCategory> appCategoryList;
			List<List<Application>> listAllApplication = new LinkedList<List<Application>>();
			AppCategory applicationCategory = null;
			appCategoryList = new ArrayList<AppCategory>();
			appCategories = appService.getAppCategories();
			for (AppCategory appCategory : appCategories) {
				applicationCategory = new AppCategory();
				if (appCategory.getCategoryName().equalsIgnoreCase(
						"Most Popular")) {
					popularApps = appService.getPopularApps(appCategory
							.getCategoryId());
					if (null != popularApps && !popularApps.isEmpty()) {
						Collections.sort(popularApps,
								Application.APP_COMPARATOR);
						applicationCategory.setPopularapplist(popularApps);
						applicationCategory.setCategoryName(appCategory
								.getCategoryName());
						appCategoryList.add(applicationCategory);
					}
				} else {
					appsByCategory = appService.getAppsByCategory(appCategory
							.getCategoryId());
					listAllApplication.add(appsByCategory);
					session.setAttribute("listAllApplication",
							listAllApplication);
					if (null != appsByCategory && !appsByCategory.isEmpty()) {
						Collections.sort(appsByCategory,
								Application.APP_COMPARATOR);
						applicationCategory.setAppList(appsByCategory);
						applicationCategory.setCategoryName(appCategory
								.getCategoryName());
						appCategoryList.add(applicationCategory);
					}
				}
				appsByCategory = null;
				popularApps = null;
			}
			appFormBean.setAppCategoryList(appCategoryList);
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
		return "sitesapps";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ResourceMapping(value = "sitesappsalphabeticalsort")
	public ModelAndView showQuickLinkForm(ResourceRequest request,
			ResourceResponse response) {
		ModelAndView mav = new ModelAndView("sitesappsalphabeticalsort");
		try {
			PortletSession session = request.getPortletSession();
			int i = 0;
			int j = 0;
			String[] alphabates = new String[] { "A", "B", "C", "D", "E", "F",
					"G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
					"S", "T", "U", "V", "W", "X", "Y", "Z" };
			List<Application> listApplication = new ArrayList<Application>();
			List<Application> availAppsList = new ArrayList<Application>();

			List<List<Application>> availAppsList1 = (List<List<Application>>) session
					.getAttribute("listAllApplication");
			for (List<Application> availAppsList2 : availAppsList1) {
				availAppsList.addAll(availAppsList2);
			}
			Collections.sort(availAppsList, Application.APP_COMPARATOR);

			Map<String, List<Application>> map = new HashMap<String, List<Application>>();
			for (Application app : availAppsList) {
				if (app.getAppName().toUpperCase().indexOf(alphabates[i]) == 0) {
					listApplication.add(app);
					j = 1;
				} else if (j == 0) {
					for (int k = 0; k < alphabates.length; k++) {
						if (app.getAppName().toUpperCase()
								.indexOf(alphabates[k]) == 0) {
							i = k;
							j = 1;
							break;
						}
					}
					listApplication.add(app);

				} else {
					if (listApplication.size() > 0) {
						map.put(alphabates[i], listApplication);
					}

					// i++;
					listApplication = new ArrayList<Application>();
					for (int l = 0; l < alphabates.length; l++) {
						if (app.getAppName().toUpperCase()
								.indexOf(alphabates[l]) == 0) {
							listApplication.add(app);
							i = l;
							break;
						}
					}
				}
			}
			mav.addObject("alphabateskeys", new ArrayList(map.keySet()));
			mav.addObject("alphabates", Arrays.asList(alphabates));
			mav.addObject("availAppsMap", map);
		} catch (Exception e) {
			logger.error("Exception in showQuickLinkForm", e);
		}
		return mav;
	}
}