package com.cisco.swtg.bss.web.controller.requestproxy;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.cisco.swtg.bss.delegates.ServiceInterfaceDelegate;
import com.cisco.swtg.bss.util.BSSConstants;

/**
 * This Handler class takes care of invoking the POST method of the File Broker
 * service.
 * 
 * @author Infosys
 * 
 */
public class ProxyPostServiceController extends AbstractController {

	private ServiceInterfaceDelegate serviceInterfaceDelegate = null;

	public ServiceInterfaceDelegate getServiceInterfaceDelegate() {
		return serviceInterfaceDelegate;
	}

	public void setServiceInterfaceDelegate(ServiceInterfaceDelegate serviceInterfaceDelegate) {
		this.serviceInterfaceDelegate = serviceInterfaceDelegate;
	}

	public ProxyPostServiceController(ServiceInterfaceDelegate delegate) {
		super();
		setServiceInterfaceDelegate(delegate);
	}

	@SuppressWarnings("unchecked")
	public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		String userId = BSSConstants.EMPTY_STRING;
		if (request.getSession(false) != null) {
			Map<String, Object> sessionBean = (Map<String, Object>) request.getSession(false).getAttribute(
					BSSConstants.USER_SESSION_BEAN);
			userId = sessionBean.get(BSSConstants.USER).toString();
		}

		String quaryParams = BSSConstants.EMPTY_STRING;
		if (request.getQueryString() != null) {
			quaryParams = request.getQueryString().replaceAll("amp;", BSSConstants.EMPTY_STRING);
			quaryParams = URLDecoder.decode(quaryParams, "UTF-8");
		}
		StringBuilder quaryString = new StringBuilder("?").append(quaryParams).append("&un=").append(userId).append(
				BSSConstants.CI_BTK);
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String requestPath = request.getPathInfo().replaceAll("/requestproxy/post", BSSConstants.EMPTY_STRING)
				+ quaryString;
		String responseText = BSSConstants.SERVICE_ERROR_MESSAGE;
		try {
			responseText = serviceInterfaceDelegate.executePost(requestPath, false, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.print(responseText);
		out.flush();
		out.close();
		return null;
	}

	/*
	 * public ModelAndView handleRequestInternal(HttpServletRequest request,
	 * HttpServletResponse response) throws Exception {
	 * 
	 * request.setCharacterEncoding("UTF-8");
	 * 
	 * PrintWriter out = response.getWriter(); String quaryParams = ""; if
	 * (request.getQueryString() != null) { quaryParams =
	 * request.getQueryString().replaceAll("amp;", ""); quaryParams =
	 * URLDecoder.decode(quaryParams, "UTF-8"); } StringBuilder quaryString =
	 * new StringBuilder("?").append(quaryParams).append("&ci=BTK"); String
	 * requestPath = request.getPathInfo().replaceAll("/requestproxy/get", "") +
	 * quaryString; Map<String, Object> requestParameterValue = new
	 * HashMap<String, Object>(); /Enumeration<String> parameterNames =
	 * request.getParameterNames(); while (parameterNames.hasMoreElements()) {
	 * String paramName = parameterNames.nextElement(); String[] paramValues =
	 * request.getParameterValues(paramName); if (paramValues.length == 1) {
	 * requestParameterValue.put(paramName, paramValues[0]); } else {
	 * 
	 * requestParameterValue.put(paramName, paramValues); } }
	 */
	/*
	 * String responseText = BSSConstants.SERVICE_ERROR_MESSAGE; try { if
	 * (serviceInterfaceDelegate != null) { //JSONObject
	 * jsonObject=createJsonObject(requestParameterValue); JSONObject
	 * responseObject = serviceInterfaceDelegate .executeRestCall(requestPath);
	 * responseText = responseObject.toString(); logger.info("responseText:" +
	 * responseText); } } catch (Exception e) { logger.info("In catch:" +
	 * e.getMessage()); } out.print(responseText); out.flush(); out.close();
	 * 
	 * return null; }
	 */

	/*
	 * public JSONObject createJsonObject(Map<String,Object> rating) {
	 * JSONObject json=new JSONObject(); JSONObject Request=new JSONObject();
	 * JSONObject ratingRequest= new JSONObject(); try {
	 * ratingRequest.put("bugId", rating.get("bugId"));
	 * ratingRequest.put("action", rating.get("action"));
	 * ratingRequest.put("rating", rating.get("rating"));
	 * ratingRequest.put("comment", rating.get("comment")); json.put("REQUEST" ,
	 * Request);
	 * 
	 * 
	 * } catch (JSONException e) { logger.info("In catch:"+e.getMessage());
	 * 
	 * }
	 * 
	 * return json;
	 * 
	 * }
	 */

}