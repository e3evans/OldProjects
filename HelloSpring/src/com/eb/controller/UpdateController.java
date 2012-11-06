package com.eb.controller;


import java.util.Enumeration;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.portlet.mvc.SimpleFormController;

import com.eb.domain.Contact;
import com.eb.service.ContactDAO;

public class UpdateController extends SimpleFormController{
	private static Log log = LogFactory.getLog(UpdateController.class);

	ContactDAO contactDAO;
	public ContactDAO getContactDAO() {
		return contactDAO;
	}
	public void setContactDAO(ContactDAO contactDAO) {
		this.contactDAO = contactDAO;
	}
	
	protected Object formBackingObject(PortletRequest request) throws Exception {
		log.debug("Entering UpdateController.formBackingObject() " );
		Enumeration paramEnum  =request.getParameterNames();
		while(paramEnum.hasMoreElements()){
			String paramName = (String)paramEnum.nextElement();
			String paramValue = request.getParameter(paramName);
			log.debug( paramName + " = " + paramValue);
		}
		Contact updateContact =(Contact)super.formBackingObject(request);
		if(updateContact.getContactId() == null){
			String contactId = request.getParameter("contactId");
			updateContact = contactDAO.getContact(contactId);
		}
		log.debug("Exiting UpdateController.formBackingObject() " + updateContact);
		return updateContact;
	}

	
	
	protected void onSubmitAction(ActionRequest request, ActionResponse response, Object command, BindException errors) throws Exception {
		log.debug("Entering InsertController.doSubmitAction() " + command);
		contactDAO.updateContact((Contact)command);
		response.setRenderParameter("action", "list");
		log.debug("Entering InsertController.doSubmitAction() " + command);
	}
}
