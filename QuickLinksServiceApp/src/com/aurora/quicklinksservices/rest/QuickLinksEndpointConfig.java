package com.aurora.quicklinksservices.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;

public class QuickLinksEndpointConfig extends Application {
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(QuickLinksServiceEndPoint.class);
		return classes;
	}

	@Override
	public Set<Object> getSingletons() {
		HashSet<Object> singletons = new HashSet<Object>();
		singletons.add(new JacksonJaxbJsonProvider());
		
		return singletons;
	}
}