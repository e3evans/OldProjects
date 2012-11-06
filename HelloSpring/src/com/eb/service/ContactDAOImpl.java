package com.eb.service;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eb.domain.Contact;


public class ContactDAOImpl implements ContactDAO{
	private static Log log = LogFactory.getLog(ContactDAOImpl.class);

	HashmapContact hashMapContact;
	
	
	public HashmapContact getHashMapContact() {
		return hashMapContact;
	}

	public void setHashMapContact(HashmapContact hashMapContact) {
		this.hashMapContact = hashMapContact;
	}

	public Contact getContact(String contactId) {
		log.debug("Entering ContactDAOImpl.getContact() " + contactId);
		Contact updateContact = hashMapContact.getContact(contactId);
		log.debug("Entering ContactDAOImpl.getContact() " +updateContact);
		return updateContact;
	}

	public ArrayList getContactList() {
		log.debug("Entering ContactDAOImpl.getContactList()");
		ArrayList contactList = hashMapContact.getContacts();
		log.debug("Exiting ContactDAOImpl.getContactList() " + contactList);
		return contactList;
	}

	public int insertContact(Contact contact) {
		log.debug("Entering ContactDAOImpl.insertContact() " + contact);
		hashMapContact.saveContact(contact);
		log.debug("Entering ContactDAOImpl.insertContact() " );
		return 0;
	}

	public int updateContact(Contact contact) {
		log.debug("Entering ContactDAOImpl.updateContact() " + contact);
		hashMapContact.saveContact(contact);
		log.debug("Entering ContactDAOImpl.updateContact() " );
		return 0;
	}
	public int deleteContact(String contactId) {
		log.debug("Entering ContactDAOImpl.deleteContact() " + contactId);
		hashMapContact.deleteContact(contactId);
		log.debug("Entering ContactDAOImpl.deleteContact() " );
		return 0;
	}

}
