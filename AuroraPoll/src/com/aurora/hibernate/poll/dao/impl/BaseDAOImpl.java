package com.aurora.hibernate.poll.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseDAOImpl {
	@Autowired
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory(){
		return this.sessionFactory;
	}
}
