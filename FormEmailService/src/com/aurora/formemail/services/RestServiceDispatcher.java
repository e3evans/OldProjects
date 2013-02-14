package com.aurora.formemail.services;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;


public class RestServiceDispatcher extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		// TODO Auto-generated method stub
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(FormEmailService.class);
		return classes;
	}

	@Override
	public Set<Object> getSingletons() {
		// TODO Auto-generated method stub
		return super.getSingletons();
	}

}
