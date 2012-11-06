package com.eb.controller;

import java.util.HashMap;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.mvc.Controller;

import com.eb.service.ContactDAO;



public class SelectController implements Controller{
	private static Log log = LogFactory.getLog(SelectController.class);
	private ContactDAO contactDAO;
	

	public void handleActionRequest(ActionRequest request, ActionResponse response) throws Exception {
		log.debug("Entering SelectController.handleActionRequest()");
		log.debug("Exiting SelectController.handleActionRequest()");
	}

	public ModelAndView handleRenderRequest(RenderRequest renderRequest, RenderResponse response) throws Exception {
		log.debug("Entering SelectController.handleActionRequest()");
		ModelAndView modelAndView = new ModelAndView("list");
		modelAndView.addObject("contactList", contactDAO.getContactList());
		log.debug("Exiting SelectController.handleActionRequest()");
		return modelAndView;
	}

	public ContactDAO getContactDAO() {
		return contactDAO;
	}

	public void setContactDAO(ContactDAO contactDAO) {
		this.contactDAO = contactDAO;
	}

}
