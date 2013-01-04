package com.aurora.quicklinksservices.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aurora.quicklinksservices.beans.User;
import com.aurora.quicklinksservices.services.QuickLinksService;
import com.aurora.quicklinksservices.util.SSOManager;



/**
 * Servlet implementation class SSORedirectTest
 */
@Controller
@RequestMapping("/redirect")
public class QuickLinksRedirectController  {
	@Autowired
	private QuickLinksService quickLinksService;

	
	
	private static final long serialVersionUID = 1L;
//	private String testUrl = "https://lmsproxy.aurora.org/main_app.asp?main=app";
	public static String testUrl1 = "http://iconnect-test.aurora.org/ireq/servlet/IreqMain?command=eporthome&from=eportal&firsttime=Y";
	public static String testUrl2 = "http://iconnect-test.aurora.org/portal-extensions/callJsp.do?actionForward=lmsForward&breakFrames=Y&actionUrl=https://lmsproxy.aurora.org/dologon.asp";
	                                                                              
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @RequestMapping(method=RequestMethod.GET)
	protected void redirectURL(HttpServletRequest request, HttpServletResponse response)throws IOException  {
    	System.out.println("Enter in QuickLinksRedirectController");
		// TODO Auto-generated method stub
//		Cookie cookie = new Cookie("Eric", "Test");
//		cookie.setMaxAge(60*60*24*4);
//		cookie.setDomain(".aurora.org");
//		cookie.setPath("/");
//		response.addCookie(cookie);
//		SSOManager.createSSOCookie(request, response, "398904","N26760",
//	            "ICONNECT", "XXX", "EVANS", "ERIC",
//	            "http://iconnect-test.aurora.org/portal/", "10.52.13.197");
    	
    	String firstName="";
    	String lastName="";
    	String portalID="";
    	String LoginId=request.getParameter("userId");
    	String url=request.getParameter("url");
    	String empNo="";
    	Long portalKey;
    	String portalkeys="";
    	String ip=request.getRemoteAddr();
    	System.out.println("url printing"+url);
    	System.out.println("userid"+LoginId);
    	
		
    	List<User> list = quickLinksService.retrieveUserDetails("000282");
    	for(User user : list){
    		
    		firstName=user.getFirstName();
    		lastName=user.getLastName();
    		portalID=user.getPortalID();
    		empNo="000282";
    		portalKey=user.getUserID();
    		portalkeys=Long.toString(portalKey);
    		System.out.println("printing ip"+ip);
    		System.out.println("printing empno"+empNo);
    		System.out.println("printing portal keys"+portalkeys);
            System.out.println("firstname"+firstName);
    		
    		System.out.println("last"+lastName);
    		
    	}
    	
		SSOManager.createSSOCookie(request, response, portalkeys ,empNo,
	            "ICONNECT", "EMP", firstName, lastName,
	            "http://iconnect-test.aurora.org/portal/", "10.46.21.57");
		
		response.sendRedirect(response.encodeURL(url));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
