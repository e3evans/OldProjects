package com.aurora.seedlistservice.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;


@Path("/seedListService")
public class SeedListService {
	
	@GET
	public String getSeedList(){
		
		return "This is a test.";
	}

}
