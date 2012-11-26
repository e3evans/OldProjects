package com.aurora.quicklinksservices.services;


import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.aurora.quicklinksservices.beans.App;
import com.aurora.quicklinksservices.beans.User;
import com.aurora.quicklinksservices.beans.UserApp;
import com.aurora.quicklinksservices.beans.UserAppResponseBean;
import com.aurora.quicklinksservices.rest.ApplicationResponse;
import com.aurora.quicklinksservices.rest.UserApplicationResponse;
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
		List<App> list = quickLinksService.retrieveAvailAppListByRole("test");
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
	
	@GET
	@Path("/userapplist")
	public UserApplicationResponse getUserAppDetails() {
		Long i = 1111l;
		System.out.println("getUserAppDetails *****************");
		UserApplicationResponse userAppResponse = new UserApplicationResponse();
		List<UserAppResponseBean> userAppList = quickLinksService.findUserAppsByUser(i);
		userAppResponse.setUserAppList(userAppList);
		return userAppResponse;
	}

	
}