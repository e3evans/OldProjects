package com.aurora.seedlistservice;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.aurora.seedlistservice.services.SeedListService;

public class RestServiceDispatcher extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		// TODO Auto-generated method stub
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(SeedListService.class);
		return classes;
	}

	@Override
	public Set<Object> getSingletons() {
		// TODO Auto-generated method stub
		return super.getSingletons();
	}

}
