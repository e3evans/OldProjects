package com.cisco.swtg.bss.web.portletcontroller;

import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.cisco.swtg.bss.delegates.ServiceInterfaceDelegate;
import com.cisco.swtg.bss.util.BSSConstants;
import com.cisco.swtg.bss.web.domain.BugSearchResults;

/**
 * This Controller class is used to proceed action for Export data to excel
 * file. This controller class is configured with
 * ControllerClassNameHandlerMapping and can be called with controller name
 * 
 * @author teprasad
 * 
 */
public class ExportToExcelController extends AbstractController {

	private static final Log logger = LogFactory.getLog(ExportToExcelController.class);

	private static final String BUG_SEARCH_RESULTS = "bugSearchResults";
	private ServiceInterfaceDelegate serviceInterfaceDelegate;

	public void setServiceInterfaceDelegate(ServiceInterfaceDelegate serviceInterfaceDelegate) {
		this.serviceInterfaceDelegate = serviceInterfaceDelegate;
	}

	public ExportToExcelController(ServiceInterfaceDelegate delegate) {
		super();
		setServiceInterfaceDelegate(delegate);
	}

	/**
	 * This method is used to handle request which come with name of this
	 * controller class and pass to Excel view resolver
	 */
	@SuppressWarnings( { "deprecation", "unchecked" })
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String userId = BSSConstants.EMPTY_STRING;
		if (request.getSession(false) != null) {
			Map<String, Object> sessionBean = (Map<String, Object>) request.getSession(false).getAttribute(
					BSSConstants.USER_SESSION_BEAN);
			userId = sessionBean.get(BSSConstants.USER).toString();
		}

		String requestPath = ServletRequestUtils.getStringParameter(request, "queryUrl");
		requestPath = URLEncoder.encode(requestPath).replaceAll("%3F", "?").replaceAll("%2F", "/").replaceAll("%3D",
				"=").replaceAll("%26", "&").replaceAll("&amp%3B", "%26").replaceAll("\\|", "%7C")
				+ "&un=" + userId + BSSConstants.CI_BTK;

		logger.info("Calling searchResult for Export Data start");
		JSONObject bugSummaryJson = new JSONObject(serviceInterfaceDelegate.processRequestCache(requestPath, false,
				null, null));
		logger.info("Calling searchResult for Export Data end");
		BugSearchResults bugSearchResults = new BugSearchResults();
		if (!bugSummaryJson.isNull(BUG_SEARCH_RESULTS)) {
			bugSearchResults.setBugDetailJSON(bugSummaryJson.getJSONArray(BUG_SEARCH_RESULTS));
		}
		return new ModelAndView("ExcelBugSummary", "bugSearchResults", bugSearchResults);

	}
}