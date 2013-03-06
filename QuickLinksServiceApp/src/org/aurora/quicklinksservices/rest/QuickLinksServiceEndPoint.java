package org.aurora.quicklinksservices.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.aurora.quicklinksservices.beans.App;
import org.aurora.quicklinksservices.beans.ApplicationResponse;
import org.aurora.quicklinksservices.beans.CategoryResponse;
import org.aurora.quicklinksservices.beans.User;
import org.aurora.quicklinksservices.beans.UserAppResponseBean;
import org.aurora.quicklinksservices.beans.UserApplicationResponse;
import org.aurora.quicklinksservices.exceptions.WriteException;
import org.aurora.quicklinksservices.services.QuickLinksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@Path("/qlservice")
@Produces(MediaType.APPLICATION_JSON)
public class QuickLinksServiceEndPoint extends SpringBeanAutowiringSupport {

	@Autowired
	private QuickLinksService quickLinksService;

	private final String ICONNECT_ROLE = "EMP";

	// TODO: @GET with params is a security issue, ajax calls to this endpoint
	// can be hacked with any params to get back information, this should change
	// if we return sensitive data

	@GET
	@Path("/results")
	public ApplicationResponse getAppList() {
		ApplicationResponse appResponse = new ApplicationResponse();
		List<App> list = quickLinksService
				.retrieveAvailAppListByRole(ICONNECT_ROLE);
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
		UserApplicationResponse userAppResponse = new UserApplicationResponse();
		userAppResponse.setUserAppList(quickLinksService
				.findAllUserAppsByUser(loginId));
		return userAppResponse;
	}

	@GET
	@Path("/userapplist/{loginId}")
	public UserApplicationResponse getUserAppDetails(
			@PathParam("loginId") String loginId) {
		UserApplicationResponse userAppResponse = new UserApplicationResponse();
		userAppResponse.setUserAppList(quickLinksService
				.findUserAppsByUser(loginId));
		return userAppResponse;
	}

	@GET
	@Path("/retrieveuserapp/{appId}/{seqNo}/{loginId}")
	public UserAppResponseBean retrieveUserApp(
			@PathParam("appId") String appId, @PathParam("seqNo") String seqNo,
			@PathParam("loginId") String loginId) {
		return quickLinksService.retrieveUserApp(appId, seqNo, loginId);
	}

	@GET
	@Path("/createuserapp/{appId}/{seqNo}/{loginId}")
	public UserAppResponseBean createUserApp(@PathParam("appId") String appId,
			@PathParam("seqNo") String seqNo,
			@PathParam("loginId") String loginId) {
		try {
			quickLinksService.createUserApp(loginId, appId, seqNo);
			return quickLinksService.retrieveUserApp(appId, seqNo, loginId);
		} catch (WriteException we) {
			return new UserAppResponseBean();
		}
	}

	@GET
	@Path("/appautolist/{appId}")
	public ApplicationResponse getAppMenuAutoList(
			@PathParam("appId") String appId) {
		ApplicationResponse appResponse = new ApplicationResponse();
		appResponse.setApplicationList(quickLinksService
				.retrieveAppMenuAutoList(appId));
		return appResponse;
	}

	@GET
	@Path("/updateuserapp/{appId}/{seqNo}/{loginId}/{activecd}")
	public UserAppResponseBean updateUserApp(@PathParam("appId") String appId,
			@PathParam("seqNo") String seqNo,
			@PathParam("loginId") String loginId,
			@PathParam("activecd") String activecd) {
		try {
			quickLinksService.updateUserApp(loginId, appId, seqNo, activecd);
		} catch (WriteException we) { /* ignore since we return new bean either way */ }
		return new UserAppResponseBean();
	}

	@GET
	@Path("/appcategorylist")
	public CategoryResponse getAppCategoryList() {
		CategoryResponse categoryResponse = new CategoryResponse();
		categoryResponse.setAppCategoryList(quickLinksService
				.findAppCategories());
		return categoryResponse;
	}

	@GET
	@Path("/applistbycategory/{categoryId}")
	public ApplicationResponse getAppListByCategory(
			@PathParam("categoryId") String categoryId) {
		ApplicationResponse appResponse = new ApplicationResponse();
		appResponse.setApplicationList(quickLinksService
				.findAvailAppListByCategory(categoryId));
		return appResponse;
	}

	@GET
	@Path("/popularlistbycategory/{categoryId}")
	public ApplicationResponse getPopularAppListByCategory(
			@PathParam("categoryId") String categoryId) {
		ApplicationResponse appResponse = new ApplicationResponse();
		appResponse.setApplicationList(quickLinksService
				.findPopularAppListByCategory(categoryId));
		return appResponse;
	}
}