package com.aurora.quicklinksservices.rest;


import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.aurora.quicklinksservices.beans.Application;
import com.aurora.quicklinksservices.beans.User;
import com.aurora.quicklinksservices.services.QuickLinksService;

@Path("/test")
@Produces(MediaType.APPLICATION_JSON)
public class QuickLinksServiceEndPoint extends SpringBeanAutowiringSupport {

	@Autowired
	private QuickLinksService quickLinksService;

	@GET
	public String getTest() {
		return "Success";
	}

	@GET
	@Path("/results")
	public ApplicationResponse getAppList() {
		System.out.println("getAppList---->");
		
		System.out.println("list--->");
		ApplicationResponse appResponse = new ApplicationResponse();
		List<Application> list = quickLinksService.retrieveAvailAppListByRole("test");
		appResponse.setApplicationList(list);
		return appResponse;
	}
	
	
	
	@GET
	@Path("/userresults")
	public ApplicationResponse getUserDetails() {
		
		System.out.println("printing user name *****************");
		ApplicationResponse appResponse = new ApplicationResponse();
		List<User> list = quickLinksService.retrieveUserDetails("test");
		appResponse.setUserList(list);
		return appResponse;
	}

	
}