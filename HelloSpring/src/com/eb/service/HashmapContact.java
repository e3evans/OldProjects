package com.eb.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

import com.eb.domain.Contact;

public class HashmapContact {
	private static TreeMap contactDB = new TreeMap();

	static{
		Contact contact = new Contact();
		contact.setContactId("1");
		contact.setFirstName("FirstName");
		contact.setLastName("LastName");
		contact.setEmail("firstName@ibm.com");
		contact.setPhoneNumber("1234567890");
		contactDB.put("1",contact);

		Contact contact1 = new Contact();
		contact1.setContactId("2");
		contact1.setFirstName("FirstName2");
		contact1.setLastName("LastName2");
		contact1.setEmail("secondName@ibm.com");
		contact1.setPhoneNumber("0987654321");
		contactDB.put("2",contact1);	
	}
	/* (non-Javadoc)
	 * @see com.sample.IContact#addContact(com.sample.ContactForm)
	 */
	public int saveContact(Contact contact) {
		System.out.println("Inside saveContact " + contact);
		contactDB.put(contact.getContactId(),contact);	
		System.out.println("Inside saveContact " + contactDB);
		return 1;
	}
	/* (non-Javadoc)
	 * @see com.sample.IContact#deleteContact(int)
	 */
	public int deleteContact(String contactId) {
		System.out.println("Inside deleteContact" + contactId);
		contactDB.remove(contactId);
		System.out.println("Inside deleteContact " + contactDB);

		return 0;
	}
	/* (non-Javadoc)
	 * @see com.sample.IContact#getContact(int)
	 */
	public Contact getContact(String contactId) {
		return (Contact)contactDB.get(contactId);
	}
	/* (non-Javadoc)
	 * @see com.sample.IContact#getContacts()
	 */
	public ArrayList getContacts() {
		ArrayList contactList = new ArrayList();
		Iterator contactIt = contactDB.values().iterator();
		while(contactIt.hasNext()){
			Contact contact =(Contact)contactIt.next();
			contactList.add(contact);
		}
		return contactList;
	}

}
