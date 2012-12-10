package com.aurora.quicklinksservices.rest;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.aurora.quicklinksservices.beans.App;
import com.aurora.quicklinksservices.beans.User;
import com.aurora.quicklinksservices.beans.UserAppResponseBean;
import com.aurora.quicklinksservices.services.QuickLinksService;

@Path("/qlservice")
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
		ApplicationResponse appResponse = new ApplicationResponse();
		List<App> list = quickLinksService.retrieveAvailAppListByRole("EMP");
		appResponse.setApplicationList(list);
		return appResponse;
	}

	@GET
	@Path("/userresults")
	public ApplicationResponse getUserDetails() {
		ApplicationResponse appResponse = new ApplicationResponse();
		List<User> list = quickLinksService.retrieveUserDetails("test");
		appResponse.setUserList(list);
		return appResponse;
	}

	@GET
	@Path("/alluserapplist/{userId}")
	public UserApplicationResponse getAllUserAppDetails(
			@PathParam("userId") String userId) {
		UserApplicationResponse userAppResponse = new UserApplicationResponse();
		List<UserAppResponseBean> userAppList = quickLinksService
				.findAllUserAppsByUser(userId);
	    userAppResponse.setUserAppList(userAppList);
		return userAppResponse;
	}

	@GET
	@Path("/userapplist/{userId}")
	public UserApplicationResponse getUserAppDetails(
			@PathParam("userId") String userId) {
		UserApplicationResponse userAppResponse = new UserApplicationResponse();
		List<UserAppResponseBean> userAppList = quickLinksService.findUserAppsByUser(userId);
		userAppResponse.setUserAppList(userAppList);
		return userAppResponse;
	}

	@GET
	@Path("/retrieveuserapp/{appId}/{seqNo}/{userId}")
	public UserAppResponseBean retrieveUserApp(
			@PathParam("appId") String appId, @PathParam("seqNo") String seqNo,
			@PathParam("userId") String userId) {
		UserApplicationResponse userAppResponse = new UserApplicationResponse();
		UserAppResponseBean userApp = quickLinksService.retrieveUserApp(appId,
				seqNo, userId);
		return userApp;
	}

	@GET
	@Path("/createuserapp/{appId}/{seqNo}/{userId}")
	public UserAppResponseBean createUserApp(@PathParam("appId") String appId,
			@PathParam("seqNo") String seqNo, @PathParam("userId") String userId) {
		quickLinksService.createUserApp(userId, appId, seqNo);
		UserApplicationResponse userAppResponse = new UserApplicationResponse();
		UserAppResponseBean userApp = quickLinksService.retrieveUserApp(appId,
				seqNo, userId);
		return userApp;
	}

	@GET
	@Path("/appautolist/{appId}")
	public ApplicationResponse getAppMenuAutoList(
			@PathParam("appId") String appId) {
		ApplicationResponse appResponse = new ApplicationResponse();
		List<App> list = quickLinksService.retrieveAppMenuAutoList(appId);
		appResponse.setApplicationList(list);
		return appResponse;
	}

	@GET
	@Path("/updateuserapp/{appId}/{seqNo}/{userId}/{activecd}")
	public UserAppResponseBean updateUserApp(@PathParam("appId") String appId,
			@PathParam("seqNo") String seqNo,
			@PathParam("userId") String userId,
			@PathParam("activecd") String activecd) {
		quickLinksService.updateUserApp(userId, appId, seqNo, activecd);
		UserAppResponseBean userAppResponse = new UserAppResponseBean();
		return userAppResponse;
	}

}