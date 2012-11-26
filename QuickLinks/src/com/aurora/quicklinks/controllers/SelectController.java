package com.aurora.quicklinks.controllers;

import java.util.List;
import javax.portlet.RenderRequest;


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

import com.aurora.quicklinks.beans.Application;
import com.aurora.quicklinks.beans.UrlBean;
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
	public List<Application> getUrls() {
		return urlService.listCompleteUrlBeanV();
	}
	
	
	@RenderMapping
	public String showIndex(RenderRequest request){
		System.out.println("HERE!!! showIndex111");
		System.out.println("Entering SelectController.showIndex11()");
		//request.setAttribute("urlList", urlService.listUrlBean("test"));
		System.out.println("Exiting SelectController.showIndex2222()");
		return "list";
	}
	
	
	
}
