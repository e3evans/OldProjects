package com.cisco.swtg.bss.web.controller.requestproxy;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.cisco.swtg.bss.delegates.ServiceInterfaceDelegate;
import com.cisco.swtg.bss.util.BSSConstants;

/**
 * 
 * @author Infosys
 * 
 *         This Controller is used to handle GET Request which coming from UI
 *         with calling path "/requestproxy/get"
 */
public class ProxyGetServiceController extends AbstractController {

	private ServiceInterfaceDelegate serviceInterfaceDelegate;

	public ServiceInterfaceDelegate getServiceInterfaceDelegate() {
		return serviceInterfaceDelegate;
	}

	public void setServiceInterfaceDelegate(ServiceInterfaceDelegate serviceInterfaceDelegate) {
		this.serviceInterfaceDelegate = serviceInterfaceDelegate;
	}

	public ProxyGetServiceController(ServiceInterfaceDelegate delegate) {
		super();
		setServiceInterfaceDelegate(delegate);
	}

	@SuppressWarnings( { "deprecation", "unchecked" })
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
			quaryParams = request.getQueryString().replaceAll("&amp;", "%26").replaceAll("\\|", "%7C");
		}
		String pathInfo = URLEncoder.encode(
				request.getPathInfo().replaceAll("/requestproxy/get", BSSConstants.EMPTY_STRING))
				.replaceAll("%2F", "/").replaceAll("#", "%23");
		StringBuilder quaryString = new StringBuilder("?").append(quaryParams).append("&un=").append(userId).append(
				BSSConstants.CI_BTK);
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String requestPath = pathInfo + quaryString;
		String responseText = BSSConstants.SERVICE_ERROR_MESSAGE;
		try {
			responseText = serviceInterfaceDelegate.processRequestCache(requestPath, false, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.print(responseText);
		out.flush();
		out.close();
		return null;
	}
}