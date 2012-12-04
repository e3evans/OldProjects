package com.aurora.quicklinks.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.portlet.ActionResponse;
import javax.portlet.RenderResponse;
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
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;
import com.aurora.quicklinks.beans.Application;
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
		urlFormBean.setListUrlBean(urlService.listEditUrlBeanV());
		return urlFormBean;
	}

	@ResourceMapping(value="quicklinksEditList")
	public ModelAndView showQuickLinkForm(ResourceResponse response) {
		System.out.println("Creating urlFormBean command object");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("urledit");
		return mav;
	}
	
	
/*	@RenderMapping(params = "action=editUrl")
	public String showQuickLinkForm(RenderResponse response) {
		System.out.println("Creating urlFormBean command object");
		return "urledit";
	}*/

	

	@ActionMapping(params = "action=updateUrl")
	public void updateUrl(@ModelAttribute UrlFormBean urlFormBean,
			BindingResult bindingResult, ActionResponse response,
			SessionStatus sessionStatus) {
		String userid = "43";
		UserApplication urlBean = null;
		System.out.println("Inside updateUrl action method");
		List<UserApplication> listUrlBean = new ArrayList<UserApplication>();
		for (Application bean : urlFormBean.getListUrlBean()) {
			if (bean.isChecked()) {
				String appId= bean.getAppId().trim();
				System.out.println("url controller appid from userinterface"+appId);
				String seqNo= bean.getSeqNo().trim();
				UserApplication userApp = urlService.retrieveUserApp(userid, appId, seqNo);
				System.out.println("userapplist****"+userApp);
				if(null==userApp){
					urlService.createUserApp(userid, appId,seqNo);
				}else{
					System.out.println("printing appid from data base"+userApp.getAppId());
				}

			}
		}
	//	urlService.updateUrl(listUrlBean);
		response.setRenderParameter("action", "list");
		sessionStatus.setComplete();

	}
	
}