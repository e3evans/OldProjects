package com.aurora.formemail.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/email")
public class FormEmailService {

	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sendEmail(){
		
		return "THIS IS A TEST.";
	}
}
