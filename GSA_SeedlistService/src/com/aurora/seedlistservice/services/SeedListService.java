package com.aurora.seedlistservice.services;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;


@Path("/seedListService")
@Produces(MediaType.TEXT_HTML)
public class SeedListService {
	
	@GET
	public String getSeedList(@Context HttpServletRequest request){
		StringBuffer sb = new StringBuffer();
		
		sb.append(request.getRequestURL());
		sb.append("<br>"+request.getRemoteHost());
		
		return "This is a test:"+sb.toString();
	}

}
