package com.aurora.quicklinks.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.portlet.ActionResponse;
import javax.portlet.RenderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import com.aurora.quicklinks.beans.CompleteURLBean;
import com.aurora.quicklinks.beans.UrlBean;
import com.aurora.quicklinks.beans.UrlFormBean;
import com.aurora.quicklinks.services.UrlService;

@Controller(value = "urlController")
@RequestMapping(value = "VIEW")
@SessionAttributes(types = UrlBean.class)
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
		urlFormBean.setListUrlBean(urlService.listCopmpleteUrlBean());
		return urlFormBean;
	}

	@RenderMapping(params = "action=editUrl")
	public String showQuickLinkForm(RenderResponse response) {
		System.out.println("Creating urlFormBean command object");
		return "urledit";
	}

	@ActionMapping(params = "action=updateUrl")
	public void updateUrl(@ModelAttribute UrlFormBean urlFormBean,
			BindingResult bindingResult, ActionResponse response,
			SessionStatus sessionStatus) {
		String userid = "test";
		UrlBean urlBean = null;
		System.out.println("Inside updateUrl action method");
		List<UrlBean> listUrlBean = new ArrayList<UrlBean>();
		for (CompleteURLBean bean : urlFormBean.getListUrlBean()) {
			if (bean.getId() != null) {
				urlBean = new UrlBean();
				urlBean.setId(bean.getId());
				urlBean.setUrl(bean.getUrl());
				urlBean.setUserid(userid);
				System.out.println("id--->" + bean.getId());
				System.out.println("url--->" + bean.getUrl());
				System.out.println("description--->" + bean.getDescription());
				listUrlBean.add(urlBean);

			}
		}
		urlService.updateUrl(listUrlBean);
		response.setRenderParameter("action", "list");
		sessionStatus.setComplete();

	}

}
