package org.aurora.test;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.aurora.portalSSO.util.SSOManager;


/**
 * Servlet implementation class SSORedirectTest
 */
public class SSORedirectTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
//	private String testUrl = "https://lmsproxy.aurora.org/main_app.asp?main=app";
	public static String testUrl1 = "http://iconnect-test.aurora.org/ireq/servlet/IreqMain?command=eporthome&from=eportal&firsttime=Y";
	public static String testUrl2 = "http://iconnect-test.aurora.org/portal-extensions/callJsp.do?actionForward=lmsForward&breakFrames=Y&actionUrl=https://lmsproxy.aurora.org/dologon.asp";
	public static String testUrl3 = "https://myaurora-test.aurora.org/apb/clearPatient/admin/viewConsolidatedBillAction.do?method=removePaymentAgreementParam";                                                                            
    public static String testUrl4 = "https://iconnect-test.aurora.org/wmrmain/servlet/ReportPrinter?command=wmrhome";
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public SSORedirectTest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		Cookie cookie = new Cookie("Eric", "Test");
//		cookie.setMaxAge(60*60*24*4);
//		cookie.setDomain(".aurora.org");
//		cookie.setPath("/");
//		response.addCookie(cookie);
//		SSOManager.createSSOCookie(request, response, "398904","N26760",
//	            "ICONNECT", "XXX", "EVANS", "ERIC",
//	            "http://iconnect-test.aurora.org/portal/", "10.52.13.197");

		String testURL = testUrl1;
		if (request.getParameter("testURL").equals("2")){
			testURL = testUrl2;
		}else if (request.getParameter("testURL").equals("3")){
			testURL = testUrl3;
		}else if (request.getParameter("testURL").equals("4")){
			testURL = testUrl4;
		}
		
		System.out.println(testURL);
		
		SSOManager.createSSOCookie(request, response, "48067","000282",
	            "ICONNECT", "EMP", "ROCQUE", "TOM",
	            "http://iconnect-test.aurora.org/portal/", "10.46.9.19");
		
//		SSOManager.createSSOCookie(request, response, "398904","N26760",
//	            "ICONNECT", "EMP", "EVANS", "ERIC",
//	            "http://iconnect-test.aurora.org/portal/", "10.52.11.176");
		
		
		
		response.sendRedirect(response.encodeURL(testURL));
//		response.sendRedirect(response.encodeURL("http://localhost:10039/AuroraSSOPOC/Test.jsp"));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
