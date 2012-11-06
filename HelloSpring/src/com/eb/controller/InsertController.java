package com.eb.controller;


import java.util.Enumeration;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.validation.BindException;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.mvc.SimpleFormController;

import com.eb.domain.Contact;
import com.eb.service.ContactDAO;



public class InsertController extends SimpleFormController{
	private static Log log = LogFactory.getLog(InsertController.class);
	private ContactDAO contactDAO;
	public ContactDAO getContactDAO() {
		return contactDAO;
	}
	public void setContactDAO(ContactDAO contactDAO) {
		this.contactDAO = contactDAO;
	}
	
	protected void onSubmitAction(ActionRequest request, ActionResponse response, Object command, BindException errors) throws Exception {
		log.debug("Entering InsertController.doSubmitAction() " + command);
		Contact contact =(Contact)command;
		contactDAO.insertContact((Contact)contact);
		response.setRenderParameter("action", "list");
		log.debug("Entering InsertController.doSubmitAction() " + command);
	}
	
}
