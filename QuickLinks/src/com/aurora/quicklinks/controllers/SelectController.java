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
	
	/*@ModelAttribute(value="urlList")
	public List<UrlBean> getUrls() {
		return urlService.listUrlBean("test");
	}*/
	
	

	
	
	@RenderMapping
	public String showIndex(RenderRequest request){
		System.out.println("HERE!!! showIndex111");
		System.out.println("Entering SelectController.showIndex11()");
		//request.setAttribute("urlList", urlService.listUrlBean("test"));
		System.out.println("Exiting SelectController.showIndex2222()");
		request.getUserPrincipal();
		return "list";
	}
	
	/*@RequestMapping(params="action=update")
	public String updateContact(@RequestParam("contactId")String contactId,Model model){
		if (!model.containsAttribute("contact")){
			model.addAttribute("contact",contactDAO.getContact(contactId));
		}	
		return"update";
	}
	
	@RequestMapping(params="action=insert")
	public String insertContact(Model model){
		System.out.println("in insertcontactt----");
		model.addAttribute("contact",new Contact());
		return"insert";
	}
	
	@ActionMapping(params="action=delete")
	public void deleteContact(ActionRequest request,ActionResponse response,@RequestParam("contactId")String contactId){
		log.debug("Entering Delete");
		contactDAO.deleteContact(contactId);
		response.setRenderParameter("action", "list");
		log.debug("Exiting Delete");
	}
	
	
	@ActionMapping(params="action=insert")
	public void handleInsertRequest(ActionRequest request, ActionResponse response,@ModelAttribute("contact")Contact contact){
		log.debug("Entering SelectController.handleActionRequest()");
		System.out.println("Entering SelectController.handleActionRequest()----");
		contactDAO.insertContact(contact);
		response.setRenderParameter("action", "list");
		log.debug("Exiting SelectController.handleActionRequest()");
	}
	
	@ActionMapping(params="action=update")
	public void handleUpdateRequest(ActionRequest request, ActionResponse response,@ModelAttribute("contact")Contact contact){
		log.debug("Entering SelectController.handleActionRequest()");
		System.out.println("Entering SelectController.handleUpdateRequest()----");
		contactDAO.updateContact(contact);
		response.setRenderParameter("action", "list");
		log.debug("Exiting SelectController.handleActionRequest()");
	}
	
	@ResourceMapping
	public String testResource(){
		System.out.println("HERE!!!");
		return"blank";
	}*/
	
	
	
}
