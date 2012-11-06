package com.eb.service;

import java.util.ArrayList;

import com.eb.domain.Contact;

public interface ContactDAO {
	public ArrayList<Contact> getContactList();
	public Contact getContact(String contactId);
	public int insertContact(Contact contact);
	public int updateContact(Contact contact);
	public int deleteContact(String contactId);
}
