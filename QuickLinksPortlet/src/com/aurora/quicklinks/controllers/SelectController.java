package com.aurora.quicklinks.controllers;

import java.util.List;
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

import com.aurora.quicklinks.beans.UserApplication;

import com.aurora.quicklinks.services.UrlService;

@Controller
@RequestMapping("VIEW")

public class SelectController {
	private static Log log = LogFactory.getLog(SelectController.class);
	
	@Autowired
	@Qualifier("urlService")
	private UrlService urlService;
	
	

	public void setUrlService(UrlService urlService) {
		this.urlService = urlService;
	}

	public UrlService getUrlService() {
		return urlService;
	}
	
	@ModelAttribute(value="urlList")
	public List<UserApplication> getUrls(RenderRequest request, RenderResponse response) {
		String userid="28355";
		System.out.println("HERE!!");
		System.out.println("*********************************************************************************");
		System.out.println("printing context path"+request.getContextPath());
		System.out.println("printing server port"+request.getServerPort());
		System.out.println("printing etag "+request.getETag());
		System.out.println("printing "+request.getServerName());
		System.out.println("printing "+request.getPortalContext());
		System.out.println("*********************************************************************************");
		return urlService.listCompleteUrlBeanV(userid);
	}
	
	
	@RenderMapping
	public String showIndex(RenderRequest request){
		System.out.println("printing context path"+request.getContextPath());
		System.out.println("printing server port"+request.getServerPort());
		System.out.println("printing etag "+request.getETag());
		System.out.println("printing "+request.getServerName());
		System.out.println("printing "+request.getPortalContext());
		System.out.println("HERE!!! showIndex111");
		System.out.println("Entering SelectController.showIndex11()");
		//request.setAttribute("urlList", urlService.listUrlBean("test"));
		System.out.println("Exiting SelectController.showIndex2222()");
		return "list";
	}
	
	
	
}
