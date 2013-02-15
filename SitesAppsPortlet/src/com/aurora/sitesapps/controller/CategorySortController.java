package com.aurora.sitesapps.controller;

import java.security.Principal;
import java.util.ArrayList;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.aurora.sitesapps.beans.AppCategory;
import com.aurora.sitesapps.beans.AppFormBean;
import com.aurora.sitesapps.beans.Application;
import com.aurora.sitesapps.exception.AppException;
import com.aurora.sitesapps.services.AppService;
import com.aurora.sitesapps.util.AppComparator;

@Controller(value = "sitesAppsController")
@RequestMapping(value = "VIEW")
@SessionAttributes(types = AppFormBean.class)
public class CategorySortController {
	private Logger logger = Logger.getLogger(CategorySortController.class);

	@Autowired
	@Qualifier("appService")
	private AppService appService;

	public void setAppService(AppService appService) {
		this.appService = appService;
	}

	public AppService getAppService() {
		return appService;
	}

	@ModelAttribute("appFormBean")
	public AppFormBean getCommandObject(PortletRequest request) {

		Principal user = request.getUserPrincipal();
		PortletSession session = request.getPortletSession();
		session.setAttribute("userId", user.toString());
		AppFormBean appFormBean = new AppFormBean();
		List<Application> listApplication = null;
		List<Application> listPopularApplication = null;
		List<AppCategory> listAppCategory;
		List<AppCategory> listApplicationCategory;
		List<List<Application>> listAllApplication = new LinkedList<List<Application>>();
		AppCategory applicationCategory = null;
		AppComparator appComparator = new AppComparator();
		try {
			listApplicationCategory = new ArrayList<AppCategory>();
			listAppCategory = appService.listAppCategory();
			for (AppCategory appCategory : listAppCategory) {
				applicationCategory = new AppCategory();
				if (appCategory.getCategoryName().equalsIgnoreCase(
						"Most Popular")) {
					listPopularApplication = appService
							.listPopularApplication(appCategory.getCategoryId());
					if (null != listPopularApplication
							&& !listPopularApplication.isEmpty()) {
						Collections.sort(listPopularApplication, appComparator);
						applicationCategory
								.setPopularapplist(listPopularApplication);
						applicationCategory.setCategoryName(appCategory
								.getCategoryName());
						listApplicationCategory.add(applicationCategory);
					}

				} else {
					listApplication = appService.listApplication(appCategory
							.getCategoryId());
					listAllApplication.add(listApplication);
					session.setAttribute("listAllApplication",
							listAllApplication);
					if (null != listApplication && !listApplication.isEmpty()) {
						Collections.sort(listApplication, appComparator);
						applicationCategory.setAppList(listApplication);
						applicationCategory.setCategoryName(appCategory
								.getCategoryName());
						listApplicationCategory.add(applicationCategory);
					}

				}
				listApplication = null;
				listPopularApplication = null;
			}
			appFormBean.setListAppCategory(listApplicationCategory);

		} catch (AppException ae) {
			logger.error(ae.getExceptionDesc());
			logger.error(ae.getExceptionCode());
			logger.error(ae.getExceptionType());
			logger.error(ae.getExceptionMessage());
		}

		return appFormBean;
	}

	@RenderMapping
	public String showUserApplication(RenderRequest request) {
		String errorMsg = request.getParameter("errorMsg");
		if (null != errorMsg) {
			request.setAttribute("errorMsg", errorMsg);
		}
		return "sitesapps";
	}

	@SuppressWarnings("unchecked")
	@ResourceMapping(value = "sitesappsalphabeticalsort")
	public ModelAndView showQuickLinkForm(ResourceRequest request,
			ResourceResponse response) {
		PortletSession session = request.getPortletSession();
		ModelAndView mav = new ModelAndView();
		int i = 0;
		int j = 0;
		try {
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
			Collections.sort(availAppsList, new AppComparator());

			Map<String, List<Application>> map = new HashMap<String, List<Application>>();

			for (Application app : availAppsList) {

				if (app.getAppName().trim().toUpperCase()
						.indexOf(alphabates[i]) == 0) {
					listApplication.add(app);
					j = 1;

				} else if (j == 0) {
					for (int k = 0; k < alphabates.length; k++) {

						if (app.getAppName().trim().toUpperCase()
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
						if (app.getAppName().trim().toUpperCase()
								.indexOf(alphabates[l]) == 0) {
							listApplication.add(app);
							i = l;
							break;
						}
					}

				}

			}

			mav.addObject("availAppsMap", map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mav.setViewName("sitesappsalphabeticalsort");
		return mav;
	}

}