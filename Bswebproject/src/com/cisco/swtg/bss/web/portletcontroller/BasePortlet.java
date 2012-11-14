package com.cisco.swtg.bss.web.portletcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.cisco.swtg.bss.util.BSSConstants;
import com.cisco.swtg.bss.util.Entitlement;
import com.cisco.swtg.bss.web.domain.BugSearchResults;
import com.cisco.swtg.bss.web.domain.Search;

/**
 * This class is used as super class of SearchController and Bug Detail
 * Controller
 * 
 * @author teprasad
 * 
 */
public class BasePortlet {

	protected static final String SEARCH = "search";
	protected static final String MESSAGE = "message";
	protected static final String SEARCH_FORM = "searchForm";
	protected static final String VIEW = "view";
	protected static final String SEARCH_RESULT_VIEW = "searchResultView";
	protected static final String REQUEST_PATH_PROXY = "REQUEST_PATH_PROXY";
	protected static final String BUG_ID = "bugId";
	protected static final String ISRESULTPAGE = "isResultPage";
	protected static final String FILTER_DATA = "filterData";
	protected static final String PAGE_DETAIL = "page";
	protected static final String ACCESS_LEVEL = "ACCESS_LEVEL";
	private static final String STRING_COMMA = ", ";
	private static final String ERROR_MESSAGE = "errorMessage";
	private static final String ERROR_CODE = "errorCode";
	protected static final String BSS_BUGSUMMARY = "/bss/bugsummary/";
	protected static final String BSS_BUGSUMMARY_PROXY = "/service/requestproxy/get/bss/bugsummary/";
	protected static final String BSS_SEARCH_TYPE = "bssSearchType";
	protected static final String IS_ADV_SEARCH_PAGE = "isAdvSearchPage";
	protected static final String ZERO = "0";

	protected Log logger = LogFactory.getLog(this.getClass());

	@Value("#{bssProperties['env.localenvironment']}")
	private boolean localEnvironment = false;

	@Value("#{bssProperties['env.testuserid']}")
	private String testUserId = null;

	@Value("#{bssProperties['env.usertype']}")
	private Integer userType = 1;

	@Autowired
	protected MessageSource messgaeSource;

	private String firstName;

	private String lastName;

	private String mailId;

	public void setMessgaeSource(MessageSource messgaeSource) {
		this.messgaeSource = messgaeSource;
	}

	@ModelAttribute("search")
	public Search createSearch() {
		return new Search();
	}

	public boolean isLocalEnvironment() {
		return localEnvironment;
	}

	public void setLocalEnvironment(boolean localEnvironment) {
		this.localEnvironment = localEnvironment;
	}

	public String getTestUserId() {
		return testUserId;
	}

	public void setTestUserId(String testUserId) {
		this.testUserId = testUserId;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	/**
	 * This method is get called when the user come to Search page when any
	 * error and exception happen in result processing
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(params = "view=searchForm")
	public String searchForm(RenderRequest request, Model model, @ModelAttribute(SEARCH) Search search) {
		search.setProductCategory(BSSConstants.EMPTY_STRING);
		search.setSoftwareVersion(BSSConstants.EMPTY_STRING);
		request.setAttribute(BSSConstants.PORTLET_VIEW, SEARCH);
		model.addAttribute(SEARCH, search);
		return BSSConstants.PORTLET_PAGE;
	}

	/**
	 * This method is used to render viewBugDetails page
	 * 
	 * @param request
	 * @return String
	 */
	@RenderMapping(params = "view=viewBugDetailPage")
	public String showBugDetailPage(RenderRequest request) {
		request.setAttribute(BSSConstants.PORTLET_VIEW, "viewBugDetails");
		return BSSConstants.PORTLET_PAGE;
	}

	/**
	 * This method is used to get the current user sessionBean
	 * 
	 * @param inRequest
	 * @return sessionObject
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getUserSessionBean(PortletRequest inRequest) {
		PortletSession portletSession = inRequest.getPortletSession(false);
		Map<String, Object> sessionObject = null;
		if (portletSession != null) {
			sessionObject = (Map<String, Object>) portletSession.getAttribute(BSSConstants.USER_SESSION_BEAN,
					PortletSession.APPLICATION_SCOPE);
		}
		logger.info("User Session Bean created");
		return sessionObject;
	}

	/**
	 * This method is used to get Login user or Local environment user which is
	 * available in properties file
	 * 
	 * @param request
	 * @return String
	 */
	public String getRemoteUser(PortletRequest request) {
		String remoteUser = request.getRemoteUser();
		if (isLocalEnvironment() && getTestUserId() != null) {
			remoteUser = getTestUserId();
		}
		return remoteUser;
	}

	/*
	 * This function is used to get JSONObject from FilterData in some specific
	 * format to show Filter UI when search result page get loaded
	 */
	public JSONObject getFilterJSON(String filterData, Locale locale) {

		JSONObject filter = new JSONObject();
		JSONArray filterArray = new JSONArray();
		StringTokenizer st = new StringTokenizer(filterData, ",");
		try {
			filter.put("name", "prevFilter");
			filter.put("conjunction", "and");
			filter.put("expressions", filterArray);
		} catch (JSONException e) {
			logger.error("Exception while craeting json object");
		}
		while (st.hasMoreTokens()) {
			String[] filterRow = st.nextToken().split(":");
			StringTokenizer filterValues = new StringTokenizer(filterRow[1], "|");
			while (filterValues.hasMoreTokens()) {
				JSONObject rowObject = new JSONObject();
				try {
					if (filterRow[0].equals("se")) {
						rowObject.put("attr", "severityCode");
					} else if (filterRow[0].equals("tn")) {
						rowObject.put("attr", "technology");
					} else if (filterRow[0].equals("kw")) {
						rowObject.put("attr", "keyword");
					} else if (filterRow[0].equals("sg") || filterRow[0].equals("as")) {
						rowObject.put("attr", "statusGroup");
						rowObject.put("value", getStatusGroup(filterValues.nextToken(), locale));
					} else if (filterRow[0].equals("blmd")) {
						break;
					} else if (filterRow[0].equals("filterBlmd")) {
						rowObject.put("attr", "modifiedDate");
					} else if (filterRow[0].equals("ib") || filterRow[0].equals("acsl")) {
						rowObject.put("attr", "includeBugs");
					}
					rowObject.put("op", "contains");
					if (!filterRow[0].equals("as") && !filterRow[0].equals("sg")) {
						rowObject.put("value", filterValues.nextToken().replaceAll("%26", "&"));
					}

				} catch (JSONException e) {
					logger.error("Exception while craeting json object");
				}
				filterArray.put(rowObject);
			}
		}
		return filter;
	}

	/*
	 * This method is used to authenticate loged-in used and set the entitlement
	 * rule based on userType
	 */
	public final Map<String, Object> authenticateUser(PortletRequest request) {
		Map<String, Object> sessionBean = getUserSessionBean(request);
		Entitlement entitlement = new Entitlement();
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		try {
			/*
			 * To test Header details from CISCO environment Enumeration names =
			 * httpRequest.getHeaderNames(); while (names.hasMoreElements()) {
			 * String name = (String) names.nextElement(); Enumeration values =
			 * httpRequest.getHeaders(name); if (values != null) { while
			 * (values.hasMoreElements()) { String value = (String)
			 * values.nextElement(); System.out.println(name + ": " + value);
			 * logger.info(name + ": " + value); } } }
			 */
			logger.info("Access Level from Header :" + httpRequest.getHeader(ACCESS_LEVEL));
			int userType = Integer.parseInt(httpRequest.getHeader(ACCESS_LEVEL));
			setUserType(userType);
			logger.info("First Name from Header :" + httpRequest.getHeader("firstname"));
			setFirstName(httpRequest.getHeader("firstname"));
			logger.info("Last Name from Header :" + httpRequest.getHeader("lastName"));
			setLastName(httpRequest.getHeader("lastname"));
			logger.info("Mail Id from Header :" + httpRequest.getHeader("mail"));
			setMailId(httpRequest.getHeader("mail"));
		} catch (NumberFormatException e) {
			logger.error("Number format exception in Access Level conversion");
		}
		logger.info("in authenticateUser logger information");
		if (sessionBean == null) {
			PortletSession portletSession = request.getPortletSession(true);
			sessionBean = new HashMap<String, Object>();
			try {
				entitlement.setUserEntitlement(getUserType(), sessionBean);
				String user = getRemoteUser(request);
				sessionBean.put(BSSConstants.USER, user);
				sessionBean.put(ACCESS_LEVEL, getUserType());
				sessionBean.put("firstName", getFirstName());
				sessionBean.put("lastName", getLastName());
				sessionBean.put("emailId", getMailId());
				logger.info("Logged in user is : " + user);
			} catch (Exception e) {
				logger.error("Exception in AuthenticateUser", e);
			}
			portletSession.setAttribute(BSSConstants.USER_SESSION_BEAN, sessionBean, PortletSession.APPLICATION_SCOPE);
		}
		return sessionBean;
	}

	/*
	 * This method is used to create errorList for MultipleBug Ids Search It
	 * give you exact error for errorCode 403,404,405 only other than this it
	 * will give you error which comes from web service
	 */
	public List<String> getErrorListForMultipleBugIdSearch(BugSearchResults bugSearchResults, Locale request_locale,
			List<String> errorList) {
		JSONArray mulBugIdError = bugSearchResults.getErrorSearchResults();
		StringBuilder errorCode_403_message = new StringBuilder(BSSConstants.EMPTY_STRING);
		StringBuilder errorCode_404_message = new StringBuilder(BSSConstants.EMPTY_STRING);
		StringBuilder errorCode_405_message = new StringBuilder(BSSConstants.EMPTY_STRING);
		boolean flag_403 = false;
		boolean flag_404 = false;
		boolean flag_405 = false;
		for (int i = 0; i < mulBugIdError.length(); i++) {
			JSONObject errorJson = mulBugIdError.optJSONObject(i);
			if (errorJson.optString(ERROR_CODE).equals("403")) {
				if (flag_403) {
					errorCode_403_message.append(STRING_COMMA);
				} else {
					flag_403 = true;
				}
				errorCode_403_message.append(errorJson.optString(BUG_ID));

			} else if (errorJson.optString(ERROR_CODE).equals("404")) {
				if (flag_404) {
					errorCode_404_message.append(STRING_COMMA);
				} else {
					flag_404 = true;
				}
				errorCode_404_message.append(errorJson.optString(BUG_ID));
			} else if (errorJson.optString(ERROR_CODE).equals("405")) {
				if (flag_405) {
					errorCode_405_message.append(STRING_COMMA);
				} else {
					flag_405 = true;
				}
				errorCode_405_message.append(errorJson.optString(BUG_ID));
			} else {
				errorList.add(errorJson.optString(ERROR_MESSAGE));
			}
		}
		if (!errorCode_403_message.toString().equals(BSSConstants.EMPTY_STRING)) {
			errorList.add(errorCode_403_message.append(
					messgaeSource.getMessage("multiplebugsearch.errorcode.403", null, request_locale).toString())
					.toString());
		}
		if (!errorCode_404_message.toString().equals(BSSConstants.EMPTY_STRING)) {
			errorList.add(errorCode_404_message.append(
					messgaeSource.getMessage("multiplebugsearch.errorcode.404", null, request_locale).toString())
					.toString());
		}
		if (!errorCode_405_message.toString().equals(BSSConstants.EMPTY_STRING)) {
			errorList.add(errorCode_405_message.append(
					messgaeSource.getMessage("multiplebugsearch.errorcode.405", null, request_locale).toString())
					.toString());
		}
		return errorList;
	}

	/*
	 * This method is used to get Status Detail based on status Code
	 */
	private String getStatusGroup(String statusCode, Locale locale) {
		String statusLabel = statusCode;
		if (statusCode.equals("A")) {
			statusLabel = messgaeSource.getMessage("filter.label.status.open.assigned", null, locale);
		} else if (statusCode.equals("F")) {
			statusLabel = messgaeSource.getMessage("filter.label.status.open.forwarded", null, locale);
		} else if (statusCode.equals("N")) {
			statusLabel = messgaeSource.getMessage("filter.label.status.open.new", null, locale);
		} else if (statusCode.equals("R")) {
			statusLabel = messgaeSource.getMessage("filter.label.status.fixed.resolved", null, locale);
		} else if (statusCode.equals("V")) {
			statusLabel = messgaeSource.getMessage("filter.label.status.fixed.verified", null, locale);
		} else if (statusCode.equals("C")) {
			statusLabel = messgaeSource.getMessage("filter.label.status.terminated.closed", null, locale);
		} else if (statusCode.equals("D")) {
			statusLabel = messgaeSource.getMessage("filter.label.status.other.duplicate", null, locale);
		} else if (statusCode.equals("H")) {
			statusLabel = messgaeSource.getMessage("filter.label.status.open.held", null, locale);
		} else if (statusCode.equals("I")) {
			statusLabel = messgaeSource.getMessage("filter.label.status.open.inforequired", null, locale);
		} else if (statusCode.equals("M")) {
			statusLabel = messgaeSource.getMessage("filter.label.status.open.more", null, locale);
		} else if (statusCode.equals("O")) {
			statusLabel = messgaeSource.getMessage("filter.label.status.open.open", null, locale);
		} else if (statusCode.equals("P")) {
			statusLabel = messgaeSource.getMessage("filter.label.status.open.postponed", null, locale);
		} else if (statusCode.equals("S")) {
			statusLabel = messgaeSource.getMessage("filter.label.status.open.submitted", null, locale);
		} else if (statusCode.equals("W")) {
			statusLabel = messgaeSource.getMessage("filter.label.status.open.waiting", null, locale);
		} else if (statusCode.equals("J")) {
			statusLabel = messgaeSource.getMessage("filter.label.status.terminated.junked", null, locale);
		} else if (statusCode.equals("U")) {
			statusLabel = messgaeSource.getMessage("filter.label.status.terminated.unreproducible", null, locale);
		}
		return statusLabel;

	}
}
