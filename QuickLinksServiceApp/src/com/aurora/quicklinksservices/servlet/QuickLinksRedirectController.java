package com.aurora.quicklinksservices.servlet;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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
	
	private Logger logger = Logger.getLogger(QuickLinksRedirectController.class);
	@Autowired
	private QuickLinksService quickLinksService;

	
	
	//	private String testUrl = "https://lmsproxy.aurora.org/main_app.asp?main=app";
	public static String testUrl1 = "http://iconnect-test.aurora.org/ireq/servlet/IreqMain?command=eporthome&from=eportal&firsttime=Y";
	public static String testUrl2 = "http://iconnect-test.aurora.org/portal-extensions/callJsp.do?actionForward=lmsForward&breakFrames=Y&actionUrl=https://lmsproxy.aurora.org/dologon.asp";
	                                                                              
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @RequestMapping(method=RequestMethod.GET)
	protected void redirectURL(HttpServletRequest request, HttpServletResponse response)throws IOException  {
    
		// TODO Auto-generated method stub
//		Cookie cookie = new Cookie("Eric", "Test");
//		cookie.setMaxAge(60*60*24*4);
//		cookie.setDomain(".aurora.org");
//		cookie.setPath("/");
//		response.addCookie(cookie);
//		SSOManager.createSSOCookie(request, response, "398904","N26760",
//	            "ICONNECT", "XXX", "EVANS", "ERIC",
//	            "http://iconnect-test.aurora.org/portal/", "10.52.13.197");
    	InetAddress ip1;
    	String ipaddress="";
    	String firstName="";
    	String lastName="";
    	String LoginId=request.getParameter("userId");
    	String url=request.getParameter("url");
    	String empNo="";
    	Long portalKey;
    	String portalkeys="";
    	
    	
    	
		try {
 
			ip1 = InetAddress.getLocalHost();
		    ipaddress = ip1.getHostAddress();
			logger.debug("Current IP address : " + ipaddress);
			} catch (UnknownHostException e) {
            
			logger.error(e.getMessage());
 
		}
    	
		
    	List<User> list = quickLinksService.retrieveUserDetails(LoginId);
    	for(User user : list){
    		
    		firstName=user.getFirstName();
    		lastName=user.getLastName();
    		empNo="000282";
    		portalKey=user.getUserID();
    		portalkeys=Long.toString(portalKey);
    		
    		
    	}
    	
		SSOManager.createSSOCookie(request, response, portalkeys ,empNo,
	            "ICONNECT", "EMP", firstName, lastName,
	            "http://iconnect-test.aurora.org/portal/", ipaddress);
		
		
		response.sendRedirect(response.encodeURL(url));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
