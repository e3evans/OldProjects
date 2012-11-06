package com.eblue.springtest.controllers;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.eblue.springtest.beans.UrlBean;
import com.eblue.springtest.beans.UrlFormBean;

import com.eblue.springtest.services.UrlService;



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
	public List<UrlBean> getUrls() {
		return urlService.listUrlBean("test");
	}
	
	
	@RenderMapping
	public String showIndex(RenderRequest request){
		System.out.println("HERE!!! showIndex");
		System.out.println("Entering SelectController.showIndex()");
		//request.setAttribute("urlList", urlService.listUrlBean("test"));
		System.out.println("Exiting SelectController.showIndex()");
		return "list";
	}
	
}
