package com.aurora.quicklinksservices.rest;


import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import org.jboss.logging.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.aurora.quicklinksservices.beans.App;
import com.aurora.quicklinksservices.beans.User;
import com.aurora.quicklinksservices.beans.UserApp;
import com.aurora.quicklinksservices.beans.UserAppResponseBean;
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
	@Path("/userapplist/{userId}")
	public UserApplicationResponse getUserAppDetails(@PathParam("userId")String userId) {
	
		//Long i = 1111l;
		System.out.println("getUserAppDetails *****************");
		UserApplicationResponse userAppResponse = new UserApplicationResponse();
		List<UserAppResponseBean> userAppList = quickLinksService.findUserAppsByUser(userId);
		userAppResponse.setUserAppList(userAppList);
		return userAppResponse;
	}
	
	
	
	@GET
	@Path("/retrieveuserapp/{appId}/{seqNo}/{userId}")
	
	public UserAppResponseBean retrieveUserApp(@PathParam("appId")String appId,@PathParam("seqNo")String seqNo,@PathParam("userId")String userId) {
		Long i = 1111l;
		System.out.println("EndPoint getUserAppDetails *****************"+appId+"---"+seqNo+"---"+userId);
		UserApplicationResponse userAppResponse = new UserApplicationResponse();
		UserAppResponseBean userApp = quickLinksService.retrieveUserApp(appId,seqNo,userId);
		//userAppResponse.setUserAppList(userAppList);
		return userApp;
	}
	
	
	@GET
	@Path("/createuserapp/{appId}/{seqNo}/{userId}")
	
	public UserAppResponseBean createUserApp(@PathParam("appId")String appId,@PathParam("seqNo")String seqNo,@PathParam("userId")String userId) {
		Long i = 1111l;
		System.out.println("EndPoint createUserApp *****************"+appId+"---"+seqNo+"---"+userId);
		quickLinksService.createUserApp(userId, appId, seqNo);
		UserApplicationResponse userAppResponse = new UserApplicationResponse();
		UserAppResponseBean userApp = quickLinksService.retrieveUserApp(appId,seqNo,userId);
		System.out.println("printing userApp in createUserApp QuicklinksServiceEndpoint.java"+userApp);
		//userAppResponse.setUserAppList(userAppList);
		return userApp;
	}
	
	@GET
	@Path("/appautolist/{appId}")
	
	public ApplicationResponse getAppMenuAutoList(@PathParam("appId")String appId) {
		
		System.out.println("entering getAppMenuAutoList");
		ApplicationResponse appResponse = new ApplicationResponse();
		List<App> list = quickLinksService.retrieveAppMenuAutoList(appId);
		appResponse.setApplicationList(list);
		return appResponse;
	}
	
	/*need to delete this method
	/*@POST
	@Path("/updateuserapp/{appId}/{seqNo}/{userId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public UserAppResponseBean updateUserApp(@PathParam("appId")String appId,@PathParam("seqNo")String seqNo,@PathParam("userId")String userId) {
		Long i = 1111l;
		System.out.println("EndPoint createUserApp *****************"+appId+"---"+seqNo+"---"+userId);
		quickLinksService.createUserApp(userId, appId, seqNo);
		UserApplicationResponse userAppResponse = new UserApplicationResponse();
		UserAppResponseBean userApp = quickLinksService.retrieveUserApp(appId,seqNo,userId);
		//userAppResponse.setUserAppList(userAppList);
		return userApp;
	}*/

	
	
	
	
	@GET
    @Path("/updateuserapp/{appId}/{seqNo}/{userId}/{activecd}")
    public UserAppResponseBean updateUserApp(@PathParam("appId")String appId,@PathParam("seqNo")String seqNo,@PathParam("userId")String userId,@PathParam("activecd")String activecd) {
        Long i = 1111l;
        System.out.println("EndPoint updateUserApp *****************"+appId+"---"+seqNo+"---"+userId);
        quickLinksService.updateUserApp(userId, appId, seqNo,activecd);
        UserAppResponseBean userAppResponse = new UserAppResponseBean();
   
        //userAppResponse.setUserAppList(userAppList);
        return userAppResponse;
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}