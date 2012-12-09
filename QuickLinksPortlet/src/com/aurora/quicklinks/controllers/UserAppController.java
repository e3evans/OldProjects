package com.aurora.quicklinks.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.aurora.exceptions.AppException;
import com.aurora.quicklinks.beans.UserApplication;

import com.aurora.quicklinks.services.AppService;

@Controller
@RequestMapping("VIEW")
public class UserAppController {
	private static Log log = LogFactory.getLog(UserAppController.class);

	@Autowired
	@Qualifier("appService")
	private AppService appService;

	public void setAppService(AppService urlService) {
		this.appService = urlService;
	}

	public AppService getUrlService() {
		return appService;
	}

	@ModelAttribute(value = "urlList")
	public List<UserApplication> getUserApplication(RenderRequest request,
			RenderResponse response) {
		Principal user=request.getUserPrincipal();
		PortletSession session= request.getPortletSession();
		session.setAttribute("userId", user.toString());
		String userid = "28355";
		
		List<UserApplication> listUserApp = new ArrayList<UserApplication>();
		try {
			listUserApp = appService.listUserAppByUserId(userid);
		} catch (AppException ae) {
			System.out.println(ae.getExceptionDesc());
			System.out.println(ae.getExceptionCode());
			System.out.println(ae.getExceptionType());
			System.out.println(ae.getExceptionMessage());
			System.out.println("Exception in getUserApplication");
		}
		
		return listUserApp;
	}

	@RenderMapping
	public String showUserApplication(RenderRequest request) {
		String errorMsg = request.getParameter("errorMsg");
		if(null!=errorMsg){
			request.setAttribute("errorMsg", errorMsg);
		}
		
		// request.setAttribute("urlList", urlService.listUrlBean("test"));
		System.out.println("Exiting SelectController.showUserApplication()");
		return "userapp";
	}

}
