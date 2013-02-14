package com.aurora.quicklinksservices.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.aurora.quicklinksservices.beans.App;
import com.aurora.quicklinksservices.beans.AppCategory;
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
	@Path("/userresults/{loginId}")
	public ApplicationResponse getUserDetails(
			@PathParam("loginId") String loginId) {

		ApplicationResponse appResponse = new ApplicationResponse();
		List<User> list = quickLinksService.retrieveUserDetails(loginId);
		appResponse.setUserList(list);
		return appResponse;
	}

	@GET
	@Path("/alluserapplist/{loginId}")
	public UserApplicationResponse getAllUserAppDetails(
			@PathParam("loginId") String loginId) {
		Long userId = null;
		userId = quickLinksService.retrieveUserId(loginId);
		UserApplicationResponse userAppResponse = new UserApplicationResponse();
		List<UserAppResponseBean> userAppList = quickLinksService
				.findAllUserAppsByUser(userId);
		userAppResponse.setUserAppList(userAppList);
		return userAppResponse;
	}

	@GET
	@Path("/userapplist/{loginId}")
	public UserApplicationResponse getUserAppDetails(
			@PathParam("loginId") String loginId) {
		Long userId = null;
		userId = quickLinksService.retrieveUserId(loginId);
		UserApplicationResponse userAppResponse = new UserApplicationResponse();
		List<UserAppResponseBean> userAppList = quickLinksService
				.findUserAppsByUser(userId);
		userAppResponse.setUserAppList(userAppList);
		return userAppResponse;
	}

	@GET
	@Path("/retrieveuserapp/{appId}/{seqNo}/{loginId}")
	public UserAppResponseBean retrieveUserApp(
			@PathParam("appId") String appId, @PathParam("seqNo") String seqNo,
			@PathParam("loginId") String loginId) {
		Long userId = null;
		userId = quickLinksService.retrieveUserId(loginId);
		// UserApplicationResponse userAppResponse = new
		// UserApplicationResponse();
		UserAppResponseBean userApp = quickLinksService.retrieveUserApp(appId,
				seqNo, userId);
		return userApp;
	}

	@GET
	@Path("/createuserapp/{appId}/{seqNo}/{loginId}")
	public UserAppResponseBean createUserApp(@PathParam("appId") String appId,
			@PathParam("seqNo") String seqNo,
			@PathParam("loginId") String loginId) {
		Long userId = null;
		userId = quickLinksService.retrieveUserId(loginId);
		quickLinksService.createUserApp(userId, appId, seqNo);
		// UserApplicationResponse userAppResponse = new
		// UserApplicationResponse();
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
	@Path("/updateuserapp/{appId}/{seqNo}/{loginId}/{activecd}")
	public UserAppResponseBean updateUserApp(@PathParam("appId") String appId,
			@PathParam("seqNo") String seqNo,
			@PathParam("loginId") String loginId,
			@PathParam("activecd") String activecd) {
		Long userId = null;
		userId = quickLinksService.retrieveUserId(loginId);
		quickLinksService.updateUserApp(userId, appId, seqNo, activecd);
		UserAppResponseBean userAppResponse = new UserAppResponseBean();
		return userAppResponse;
	}

	@GET
	@Path("/appcategorylist")
	public CategoryResponse getAppCategoryList() {
		CategoryResponse categoryResponse = new CategoryResponse();
		List<AppCategory> list = quickLinksService.findAppCategories();
		categoryResponse.setAppCategoryList(list);
		return categoryResponse;
	}
	
	@GET
	@Path("/applistbycategory/{categoryId}")
	public ApplicationResponse getAppListByCategory(@PathParam("categoryId") String categoryId) {
		ApplicationResponse appResponse = new ApplicationResponse();
		List<App> list = quickLinksService.findAvailAppListByCategory(categoryId);
		appResponse.setApplicationList(list);
		return appResponse;
	}
	
	@GET
	@Path("/popularlistbycategory/{categoryId}")
	public ApplicationResponse getPopularAppListByCategory(@PathParam("categoryId") String categoryId) {
		ApplicationResponse appResponse = new ApplicationResponse();
		List<App> list = quickLinksService.findPopularAppListByCategory(categoryId);
		appResponse.setApplicationList(list);
		return appResponse;
	}
	

}