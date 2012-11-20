package com.aurora.org.rest.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


@Path("search")
public class QLSearch {

	@GET
	@Produces("text/html")
	public String test(){
		
		return "search";
	}
}
