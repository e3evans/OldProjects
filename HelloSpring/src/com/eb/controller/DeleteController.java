package com.eb.controller;


import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.portlet.mvc.SimpleFormController;

import com.eb.service.ContactDAO;

public class DeleteController extends SimpleFormController{
	private static Log log = LogFactory.getLog(DeleteController.class);
	ContactDAO contactDAO;
	public ContactDAO getContactDAO() {
		return contactDAO;
	}
	public void setContactDAO(ContactDAO contactDAO) {
		this.contactDAO = contactDAO;
	}
	protected void processFormSubmission(ActionRequest request, ActionResponse response, Object command, BindException errors) throws Exception {
		log.debug("Entering DeleteController.processFormSubmission()");
		contactDAO.deleteContact(request.getParameter("contactId"));
		response.setRenderParameter("action", "list");
		log.debug("Entering DeleteController.processFormSubmission()");
	}

}
