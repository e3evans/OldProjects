package com.aurora.org.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.aurora.org.rest.services.QLModify;
import com.aurora.org.rest.services.QLSearch;

public class QLServices extends Application {
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(QLSearch.class);
		classes.add(QLModify.class);

		return classes;
	}
}
