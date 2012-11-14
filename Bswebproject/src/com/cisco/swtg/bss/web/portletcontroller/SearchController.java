package com.cisco.swtg.bss.web.portletcontroller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.httpclient.HttpException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.cisco.swtg.bss.util.BSSConstants;
import com.cisco.swtg.bss.web.domain.BugDetail;
import com.cisco.swtg.bss.web.domain.BugSearchResults;
import com.cisco.swtg.bss.web.domain.Search;
import com.cisco.swtg.bss.web.service.SearchService;
import com.cisco.swtg.bss.web.service.ViewBugDetailsService;
import com.tivoli.pd.jasn1.boolean32;

/**
 * This class is used as controller for search Service from Basic Search,
 * Advanced Search and Filter
 * 
 * @author teprasad
 */
@Controller
@SessionAttributes(value = { "search" })
@RequestMapping("VIEW")
public class SearchController extends BasePortlet {

	/*
	 * bssSearchType for keyword search-kw, Advanced Search-adv, and for
	 * multiple Bug search-bugId
	 */

	private static final String ALL_PRODUCTS = "allProducts";
	private static final String RF_TAG = "rf";
	private static final String PID_SEARCH = "pidSearch";
	private static final String SOFTWARE_VERSION = "softVer";
	private static final String PID = "pid";
	private static final String SEARCH_RESULT = "searchResult";
	private static final String PID_SEARCH_ERROR_PAGE = "pidSearchError";
	private static final String SEARCH_TYPE = "searchType";
	private static final String UN = "?un=";
	private static final String BACK_SLASH = "/";
	private static final String BSS_BUGSUMMARY = "/bss/bugsummary/";
	private static final String BSS_BUGSUMMARY_PROXY = "/service/requestproxy/get/bss/bugsummary/";
    @Autowired
    @Qualifier("bugdetailcontroller")
    private BugDetailController bugdetailcontroller;
    
	@Autowired
	@Qualifier("searchService")
	private SearchService searchService;

	@Autowired
	@Qualifier("viewService")
	private ViewBugDetailsService viewBugDetailsService;

	private BugSearchResults bugSearchResults;

	private BugDetail bugDetail;

	public void setViewBugDetailsService(ViewBugDetailsService viewBugDetaisService) {
		this.viewBugDetailsService = viewBugDetaisService;
	}

	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}
	/*@RequestMapping(value="" ,params="action=action1")
	public void searchForm2(RenderRequest request, RenderResponse response){
		PrintWriter pw =null;
		try {
			pw = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pw.println("IN request mapping method with paramters action=action1 ");
		
		
	}*/
		
	
	/**
	 * This method get called when the user landed to home page and render the
	 * Basic Search page and initialize the domain object, authentication
	 * 
	 * @param request
	 * @param model
	 * @return String
	 */
	
	@RequestMapping()
	public String showSearchForm(RenderRequest request, RenderResponse response, Model model,
			@ModelAttribute("Search") Search search) {
		System.out.println("In show search form****************************");
		System.err.println("In show search form****************************");
		/*
		 * PID Search variables
		 */
		String searchType = null;
		String pid = null;
		String softVer = null;
		String softVerType = null;
		String bugIds = null;
		String keyword = null;
		String bugId = null;
		String filterData = null;
		String categoryId = null;
		String productId = null;
		String BugDetail = null;
		boolean isResultPage = false;
		PortletSession session = request.getPortletSession();
		

		/*
		 * This code is used to take Smart enabler parameter from url
		 */
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		if (httpServletRequest instanceof HttpServletRequestWrapper) {
			HttpServletRequestWrapper httpServletRequestWrapper = (HttpServletRequestWrapper) httpServletRequest;
			httpServletRequest = (HttpServletRequest) httpServletRequestWrapper.getRequest();
			searchType = httpServletRequest.getParameter(SEARCH_TYPE);
			pid = httpServletRequest.getParameter(PID);
			softVer = httpServletRequest.getParameter(SOFTWARE_VERSION);
			softVerType = httpServletRequest.getParameter("softVerType");
			keyword = httpServletRequest.getParameter("keyword");
			bugId = httpServletRequest.getParameter(BUG_ID);
			BugDetail =httpServletRequest.getParameter(PAGE_DETAIL);
			filterData = httpServletRequest.getParameter(FILTER_DATA);
			bugIds = httpServletRequest.getParameter("bugIds");
			categoryId = httpServletRequest.getParameter("categoryId");
			productId = httpServletRequest.getParameter("productId");
			
			PrintWriter pw =null;
			/*try {
				pw = response.getWriter();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pw.println(" tirumala");
			pw.println(filterData);
			pw.println(bugId);
			pw.println(searchType);
			*/
		}
		// process authentication
		authenticateUser(request);
		Integer userType = Integer.parseInt(getUserSessionBean(request).get(ACCESS_LEVEL).toString());

		String error = BSSConstants.EMPTY_STRING;

		if (searchType != null && searchType.equalsIgnoreCase(PID_SEARCH) && userType != 1) {
			setPIDSearchResults(request, model, pid, softVer, softVerType, session, error);
		} else if (searchType != null && searchType.equalsIgnoreCase("bugIdSearch")) {
			setBugIdSearchResult(bugId, request, model);
		}
		else if (searchType != null && searchType.equalsIgnoreCase("keywordSearch") && userType != 1) {
			setKeywordSearch(keyword, request, model);
		} else if (searchType != null && searchType.equalsIgnoreCase("multipleBugIdSearch") && userType != 1) {
			setMultipleBugIdsSearch(bugIds, request, model);
		} else if (searchType != null && searchType.equalsIgnoreCase("advanceSearch") && userType != 1) {
			setAdvanceSearch(categoryId, productId, softVer, softVerType, request, model);
		}else if (searchType != null && searchType.equalsIgnoreCase("bugIdfilterData")&& userType != 1) {
		System.out.println("In bugId after object creation filter*********************************************************************");
		
		bugdetailcontroller.setBugIdfilterSearchResult(bugId,filterData, request, model);
		System.out.println("In bugId filter*********************************************************************");
	}else if (searchType != null && searchType.equalsIgnoreCase("BugIdSearchURL")&&BugDetail.equalsIgnoreCase("BugDetail")) {
		String userId = getUserSessionBean(request).get(BSSConstants.USER).toString();
		Integer userType1 = Integer.parseInt(getUserSessionBean(request).get(ACCESS_LEVEL).toString());
		
		System.out.println("In Bugby ID parameterized search link******************************************");
		bugdetailcontroller.searchBug(search, isResultPage, request, response, model);
		
	}
		
		else if (searchType != null && pid != null && !searchType.equalsIgnoreCase(PID_SEARCH) && userType != 1) {
			error = messgaeSource.getMessage("pid.invalid.link", null, request.getLocale());
			model.addAttribute(MESSAGE, error);
			request.setAttribute(BSSConstants.PORTLET_VIEW, PID_SEARCH_ERROR_PAGE);
		} else {
			request.setAttribute(BSSConstants.PORTLET_VIEW, SEARCH);
			model.addAttribute(SEARCH, search);
		}
		return BSSConstants.PORTLET_PAGE;
	}
		
	/*
	 * PID search will work if request parameter contains
	 * "searchType=pidSearch". If searchType is not null and value other than
	 * pidSearch it treat as invalid url
	 */
	private void setPIDSearchResults(RenderRequest request, Model model, String pid, String softVer,
			String softVerType, PortletSession session, String error) {
		error = messgaeSource.getMessage("pid.parameter.missing", null, request.getLocale());
		model.addAttribute(PID, pid);
		if (session != null) {
			session.setAttribute(PID, pid, PortletSession.PORTLET_SCOPE);
		}
		String userId = getUserSessionBean(request).get(BSSConstants.USER).toString();
		if (pid != null && softVer != null && !pid.equals(BSSConstants.EMPTY_STRING)
				&& !softVer.equals(BSSConstants.EMPTY_STRING)) {
			StringBuilder queryString = new StringBuilder("?st=pid").append("&pid=").append(pid).append(":").append(
					softVer);
			if (softVerType != null) {
				queryString.append("&vt=").append(softVerType);
			}
			/*
			 * requestPathProxy is used for lazy loading on jsp page, and this
			 * url get called as ajax way. requestPath is used for validate the
			 * url and results
			 */
			String requestPathProxy = BSS_BUGSUMMARY_PROXY + queryString.toString();
			String requestPath = BSS_BUGSUMMARY + queryString.append("&un=").append(userId).append("&ci=BTK&rpp=25");
			try {
				bugSearchResults = searchService.searchResult(requestPath);
			} catch (HttpException e) {
				logger.error("HttpException in PID Search Action");
			} catch (JSONException e) {
				logger.error("Exception in JSON data conversion in PID Search Action");
			} catch (IOException e) {
				logger.error("IOException in PID Search Action");
			}
			if (session != null) {
				session.setAttribute(REQUEST_PATH_PROXY, requestPathProxy, PortletSession.PORTLET_SCOPE);
				session.setAttribute(PID, pid, PortletSession.PORTLET_SCOPE);
				session.setAttribute("isFilter", false, PortletSession.PORTLET_SCOPE);
				session.setAttribute(BSS_SEARCH_TYPE, "pid", PortletSession.PORTLET_SCOPE);
			}
			if (bugSearchResults != null && bugSearchResults.getErrorCode() != null) {
				model.addAttribute(MESSAGE, error);
				request.setAttribute(BSSConstants.PORTLET_VIEW, PID_SEARCH_ERROR_PAGE);
			} else if (bugSearchResults != null && bugSearchResults.getTotalNumberOfResults() != null
					&& bugSearchResults.getTotalNumberOfResults().equals(ZERO)) {
				error = messgaeSource.getMessage("pid.noresult.message", new Object[] { pid, softVer }, request
						.getLocale());
				model.addAttribute(MESSAGE, error);
				request.setAttribute(BSSConstants.PORTLET_VIEW, PID_SEARCH_ERROR_PAGE);
			} else if (bugSearchResults != null && bugSearchResults.getBugDetailJSON() != null) {
				model.addAttribute(REQUEST_PATH_PROXY, requestPathProxy);
				request.setAttribute(BSSConstants.PORTLET_VIEW, SEARCH_RESULT);
			} else {
				error = messgaeSource.getMessage("pid.noresult.message", new Object[] { pid, softVer }, request
						.getLocale());
				model.addAttribute(MESSAGE, error);
				request.setAttribute(BSSConstants.PORTLET_VIEW, PID_SEARCH_ERROR_PAGE);
			}
		} else {
			error = messgaeSource.getMessage("pid.parameter.missing", null, request.getLocale());
			model.addAttribute(MESSAGE, error);
			request.setAttribute(BSSConstants.PORTLET_VIEW, PID_SEARCH_ERROR_PAGE);
		}
	}

	/**
	 * This method is used for search bugs by keyword and process the result to
	 * Search Screen page
	 * 
	 * @param search
	 * @param request
	 * @param response
	 * @param model
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(params = "action=keywordsSearchAction")
	public void search(@ModelAttribute(SEARCH) Search search, @RequestParam("isResultPage") boolean isResultPage,
			ActionRequest request, ActionResponse response, Model model) {
		String keywords = search.getKeywords().trim();
		String userId = getUserSessionBean(request).get(BSSConstants.USER).toString();
		String error = messgaeSource.getMessage("default.exception", null, request.getLocale());
		logger.info("Basic keyword search Action start");
		String keyword = URLEncoder.encode(keywords);
		StringBuilder queryString = new StringBuilder(keyword).append(UN).append(userId).append("&ci=BTK&st=kw&rpp=25");
		StringBuilder queryStringProxy = new StringBuilder(keyword).append("?st=kw");
		String requestPathProxy = BSS_BUGSUMMARY_PROXY + queryStringProxy.toString();
		String requestPath = BSS_BUGSUMMARY + queryString.toString();
		try {
			bugSearchResults = searchService.searchResult(requestPath);
		} catch (HttpException e) {
			logger.error("HttpException in Keyword Search Action");
		} catch (JSONException e) {
			logger.error("Exception in JSON data conversion in Keyword Search Action");
		} catch (IOException e) {
			logger.error("IOException in Keyword Search Action");
		}
		PortletSession session = request.getPortletSession();
		if (session != null) {
			session.setAttribute(REQUEST_PATH_PROXY, requestPathProxy, PortletSession.PORTLET_SCOPE);
			session.setAttribute("searchCriteria", keywords, PortletSession.PORTLET_SCOPE);
			session.setAttribute(IS_ADV_SEARCH_PAGE, false, PortletSession.PORTLET_SCOPE);
			session.setAttribute("bss_keyword", keywords, PortletSession.PORTLET_SCOPE);
			session.setAttribute("isFilter", false, PortletSession.PORTLET_SCOPE);
			session.setAttribute(BSS_SEARCH_TYPE, "kw", PortletSession.PORTLET_SCOPE);
		}
		if (isResultPage) {
			response.setRenderParameter(VIEW, SEARCH_RESULT_VIEW);
			model.addAttribute(REQUEST_PATH_PROXY, requestPathProxy);
		} else {
			response.setRenderParameter(VIEW, SEARCH_FORM);
		}
		if (bugSearchResults != null && bugSearchResults.getErrorCode() != null) {
			error = bugSearchResults.getErrorMessage();
			model.addAttribute(MESSAGE, error);
		} else if (bugSearchResults != null && bugSearchResults.getTotalNumberOfResults() != null
				&& bugSearchResults.getTotalNumberOfResults().equals(ZERO)) {
			error = messgaeSource.getMessage("search.noresult.message", null, request.getLocale());
			model.addAttribute(MESSAGE, error);
		} else if ((bugSearchResults != null && bugSearchResults.getBugDetailJSON() != null) || isResultPage) {
			model.addAttribute(REQUEST_PATH_PROXY, requestPathProxy);
			response.setRenderParameter(VIEW, SEARCH_RESULT_VIEW);
		} else {
			model.addAttribute(MESSAGE, error);
		}
		logger.info("Basic keyword search Action finished");
	}

	/**
	 * This method is used to search multiple bug ids and show on search Result
	 * page.
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(params = "action=multipleBugSearchAction")
	public void multipalBugsearch(@ModelAttribute(SEARCH) Search search,
			@RequestParam("isResultPage") boolean isResultPage, ActionRequest request, ActionResponse response,
			Model model) {
		String keywords = search.getKeywords().replaceAll(";", ",").trim();
		Locale request_locale = request.getLocale();
		String userId = getUserSessionBean(request).get(BSSConstants.USER).toString();
		String error = messgaeSource.getMessage("default.exception", null, request.getLocale());
		logger.info("Multiple Bug search Action start");
		String keyword = URLEncoder.encode(keywords);
		StringBuilder queryString = new StringBuilder(UN).append(userId).append("&bugId=").append(keyword).append(
				"&ci=BTK&rpp=25");
		StringBuilder queryStringProxy = new StringBuilder("?bugId=").append(keyword);
		String requestPathProxy = BSS_BUGSUMMARY_PROXY + queryStringProxy.toString();
		String requestPath = BSS_BUGSUMMARY + queryString.toString();
		try {
			bugSearchResults = searchService.searchResult(requestPath);
		} catch (HttpException e) {
			logger.error("HttpException in Multiple Bug Search Action");
		} catch (JSONException e) {
			logger.error("Exception in JSON data conversion in Multiple Bug Search Action");
		} catch (IOException e) {
			logger.error("IOException in Multiple Bug Search Action");
		}
		List<String> errorList = new java.util.ArrayList<String>();
		if (bugSearchResults.getErrorSearchResults() != null) {
			errorList = getErrorListForMultipleBugIdSearch(bugSearchResults, request_locale, errorList);
			model.addAttribute("errorList", errorList);
		}

		PortletSession session = request.getPortletSession();
		if (session != null) {
			session.setAttribute(REQUEST_PATH_PROXY, requestPathProxy, PortletSession.PORTLET_SCOPE);
			session.setAttribute("searchCriteria", keywords, PortletSession.PORTLET_SCOPE);
			session.setAttribute(IS_ADV_SEARCH_PAGE, false, PortletSession.PORTLET_SCOPE);
			session.setAttribute("bss_keyword", keywords, PortletSession.PORTLET_SCOPE);
			session.setAttribute("isFilter", false, PortletSession.PORTLET_SCOPE);
			session.setAttribute(BSS_SEARCH_TYPE, "bugId", PortletSession.PORTLET_SCOPE);
		}
		if (isResultPage) {
			response.setRenderParameter(VIEW, SEARCH_RESULT_VIEW);
			model.addAttribute(REQUEST_PATH_PROXY, requestPathProxy);
		} else {
			response.setRenderParameter(VIEW, SEARCH_FORM);
		}

		if (bugSearchResults != null && bugSearchResults.getErrorCode() != null) {
			error = bugSearchResults.getErrorMessage();
			model.addAttribute(MESSAGE, error);
		} else if (bugSearchResults != null && bugSearchResults.getTotalNumberOfResults() != null
				&& bugSearchResults.getTotalNumberOfResults().equals(ZERO)) {
			error = messgaeSource.getMessage("multiplebugsearch.noresult.message", null, request_locale);
			model.addAttribute(MESSAGE, error);
		} else if (bugSearchResults != null && bugSearchResults.getBugDetailJSON() != null) {
			model.addAttribute(REQUEST_PATH_PROXY, requestPathProxy);
			response.setRenderParameter(VIEW, SEARCH_RESULT_VIEW);
		} else {
			model.addAttribute(MESSAGE, error);
		}
		logger.info("Multiple Bug search Action finished");
	}

	/**
	 * This method is used to search bugs with advanced search options like
	 * Product Category, product/series, software version, version type
	 * 
	 * @param search
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(params = "action=advancedSearchAction")
	public void advancedSearch(@ModelAttribute(SEARCH) Search search, ActionRequest request, ActionResponse response,
			Model model) {
		String userId = getUserSessionBean(request).get(BSSConstants.USER).toString();
		String error = messgaeSource.getMessage("default.exception", null, request.getLocale());
		logger.info("Advanced search Action start");
		StringBuilder query = new StringBuilder(BSSConstants.EMPTY_STRING);
		String versionType = BSSConstants.EMPTY_STRING;
		search.setKeywords(BSSConstants.EMPTY_STRING);
		StringBuilder searchCriteria = new StringBuilder(BSSConstants.EMPTY_STRING);
		if (search.getProductCategory() != null && !search.getProductCategory().equals(BSSConstants.EMPTY_STRING)
				&& !search.getProductCategory().equals(ALL_PRODUCTS)) {
			query.append(search.getProductCategory());
			query.append(BACK_SLASH);
		}
		if (search.getProduct() != null && !search.getProduct().equals(BSSConstants.EMPTY_STRING)) {
			query.append(search.getProduct());
			query.append("/NA");
			searchCriteria.append(search.getProductName());
		}
		if (search.getSoftwareVersion() != null && !search.getSoftwareVersion().equals(BSSConstants.EMPTY_STRING)) {
			query.append(BACK_SLASH);
			query.append(search.getSoftwareVersion());
			searchCriteria.append(", software version - ").append(search.getSoftwareVersion());
		}
		String version = search.getVersionType();
		if (version != null && !version.equals(BSSConstants.EMPTY_STRING)) {
			versionType = "&vt=" + version;
			String versionTypeText = BSSConstants.EMPTY_STRING;
			if (version.equals("foundIn")) {
				versionTypeText = messgaeSource.getMessage("advsearchpage.softversion.foundin", null, request
						.getLocale());
			} else if (version.equals("fixedIn")) {
				versionTypeText = messgaeSource.getMessage("advsearchpage.softversion.fixedin", null, request
						.getLocale());
			} else if (version.equals("affected")) {
				versionTypeText = messgaeSource.getMessage("advsearchpage.softversion.knownaffectedversion", null,
						request.getLocale());
			}
			searchCriteria.append(", version type - ").append(versionTypeText);
		}
		StringBuilder proxyQuery = new StringBuilder(query).append("?st=adv").append(versionType);

		StringBuilder reqQuery = new StringBuilder(query).append(UN).append(userId).append("&ci=BTK&st=adv").append(
				"&rpp=25&sort=severityCode:asc,bugId:desc").append(versionType);

		String requestPathProxy = BSS_BUGSUMMARY_PROXY + proxyQuery.toString();
		String requestPath = BSS_BUGSUMMARY + reqQuery.toString();
		try {
			bugSearchResults = searchService.searchResult(requestPath);
		} catch (HttpException e) {
			logger.error("HttpException in Advanced Search Action");
		} catch (JSONException e) {
			logger.error("JSON data conversion in Advanced Search Action");
		} catch (IOException e) {
			logger.error("IOException in Advanced Search Action");
		}
		PortletSession session = request.getPortletSession();
		if (session != null) {
			session.setAttribute(REQUEST_PATH_PROXY, requestPathProxy, PortletSession.PORTLET_SCOPE);
			session.setAttribute("searchCriteria", searchCriteria.toString(), PortletSession.PORTLET_SCOPE);
			session.setAttribute(IS_ADV_SEARCH_PAGE, true, PortletSession.PORTLET_SCOPE);
			session.setAttribute("searchObject", search, PortletSession.PORTLET_SCOPE);
			session.setAttribute("isFilter", false, PortletSession.PORTLET_SCOPE);
			session.setAttribute(BSS_SEARCH_TYPE, "adv", PortletSession.PORTLET_SCOPE);
		}
		response.setRenderParameter(VIEW, SEARCH_FORM);
		if (bugSearchResults != null && bugSearchResults.getErrorCode() != null) {
			model.addAttribute(MESSAGE, error);
		} else if (bugSearchResults != null && bugSearchResults.getTotalNumberOfResults().equals(ZERO)) {
			error = messgaeSource.getMessage("search.noresult.message", null, request.getLocale());
			model.addAttribute(MESSAGE, error);
		} else if (bugSearchResults != null && bugSearchResults.getBugDetailJSON() != null) {
			model.addAttribute(REQUEST_PATH_PROXY, requestPathProxy);
			response.setRenderParameter("view", SEARCH_RESULT_VIEW);
		} else {
			model.addAttribute(MESSAGE, error);
		}
		logger.info("Advanced search Action Finished");
	}

	/**
	 * This method is used to show the Result page with result data
	 * 
	 * @param request
	 * @param model
	 * @return String
	 */
	@RenderMapping(params = "view=searchResultView")
	public String showResultPage(RenderRequest request, Model model) {
		request.setAttribute(BSSConstants.PORTLET_VIEW, SEARCH_RESULT);
		return BSSConstants.PORTLET_PAGE;
	}

	/**
	 * This method is used to navigate pages using Breadcrumb
	 * 
	 * @param page
	 * @param request
	 * @param model
	 * @return string
	 */
	@RequestMapping(params = "view=breadcrum")
	public String showPageByBreadcrum(@RequestParam("page") String page, @RequestParam("rfTag") String rfTag,
			RenderRequest request, Model model, @ModelAttribute(SEARCH) Search search) {
		model.addAttribute(RF_TAG, rfTag);
		model.addAttribute(SEARCH, search);
		if (page.equals("resultPage")) {
			String requestPathProxy = request.getPortletSession().getAttribute(REQUEST_PATH_PROXY).toString();
			logger.info("Breadcrum Result Page start");
			model.addAttribute(REQUEST_PATH_PROXY, requestPathProxy);
			logger.info("Breadcrum Result Page finished");
			request.setAttribute(BSSConstants.PORTLET_VIEW, SEARCH_RESULT);
		} else {
			search.setProductCategory(BSSConstants.EMPTY_STRING);
			search.setSoftwareVersion(BSSConstants.EMPTY_STRING);
			search.setVersionType(BSSConstants.EMPTY_STRING);
			request.setAttribute(BSSConstants.PORTLET_VIEW, SEARCH);
			model.addAttribute(SEARCH, search);
		}
		return BSSConstants.PORTLET_PAGE;
	}

	/*
	 * This method is used to show search result page by url which includes
	 * searchType as keywordSearch and keyword
	 * 
	 * ex- contexturl?searchType=keywordSearch&keyword=test
	 */
	@SuppressWarnings("deprecation")
	private void setKeywordSearch(String keywords, RenderRequest request, Model model) {
		Search search = createSearch();
		search.setKeywords(keywords);
		String userId = getUserSessionBean(request).get(BSSConstants.USER).toString();
		String error = messgaeSource.getMessage("default.exception", null, request.getLocale());
		logger.info("Basic keyword search Action start");
		String keyword = URLEncoder.encode(keywords.trim());
		StringBuilder queryString = new StringBuilder(keyword).append(UN).append(userId).append("&ci=BTK&st=kw&rpp=25");
		StringBuilder queryStringProxy = new StringBuilder(keyword).append("?st=kw");
		String requestPathProxy = BSS_BUGSUMMARY_PROXY + queryStringProxy.toString();
		String requestPath = BSS_BUGSUMMARY + queryString.toString();
		try {
			bugSearchResults = searchService.searchResult(requestPath);
		} catch (HttpException e) {
			logger.error("HttpException in Keyword Search Action");
		} catch (JSONException e) {
			logger.error("Exception in JSON data conversion in Keyword Search Action");
		} catch (IOException e) {
			logger.error("IOException in Keyword Search Action");
		}
		PortletSession session = request.getPortletSession();
		if (session != null) {
			session.setAttribute(REQUEST_PATH_PROXY, requestPathProxy, PortletSession.PORTLET_SCOPE);
			session.setAttribute("searchCriteria", keywords.trim(), PortletSession.PORTLET_SCOPE);
			session.setAttribute(IS_ADV_SEARCH_PAGE, false, PortletSession.PORTLET_SCOPE);
			session.setAttribute("bss_keyword", keywords, PortletSession.PORTLET_SCOPE);
			session.setAttribute("isFilter", false, PortletSession.PORTLET_SCOPE);
			session.setAttribute(BSS_SEARCH_TYPE, "kw", PortletSession.PORTLET_SCOPE);
		}
		request.setAttribute(BSSConstants.PORTLET_VIEW, SEARCH);
		if (bugSearchResults != null && bugSearchResults.getErrorCode() != null) {
			error = bugSearchResults.getErrorMessage();
			model.addAttribute(MESSAGE, error);
		} else if (bugSearchResults != null && bugSearchResults.getTotalNumberOfResults() != null
				&& bugSearchResults.getTotalNumberOfResults().equals(ZERO)) {
			error = messgaeSource.getMessage("search.noresult.message", null, request.getLocale());
			model.addAttribute(MESSAGE, error);
		} else if (bugSearchResults != null && bugSearchResults.getBugDetailJSON() != null) {
			model.addAttribute(REQUEST_PATH_PROXY, requestPathProxy);
			request.setAttribute(BSSConstants.PORTLET_VIEW, SEARCH_RESULT);
		} else {
			model.addAttribute(MESSAGE, error);
		}
		model.addAttribute(SEARCH, search);
		logger.info("Basic keyword search Action finished");
	}

	/*
	 * This method is used to handle url based request for multiple Bug Id
	 * Search and set the result page as Search Result page with All available
	 * bugs
	 */
	@SuppressWarnings("deprecation")
	private void setMultipleBugIdsSearch(String bugIds, RenderRequest request, Model model) {
		Search search = createSearch();
		bugIds = bugIds.replaceAll(";", ",");
		search.setKeywords(bugIds);
		String userId = getUserSessionBean(request).get(BSSConstants.USER).toString();
		Locale request_locale = request.getLocale();
		String error = messgaeSource.getMessage("default.exception", null, request_locale);
		logger.info("Multiple Bug search Action start");
		String keyword = URLEncoder.encode(bugIds.trim());
		StringBuilder queryString = new StringBuilder(UN).append(userId).append("&bugId=").append(keyword).append(
				"&ci=BTK&rpp=25");
		StringBuilder queryStringProxy = new StringBuilder("?bugId=").append(keyword);
		String requestPathProxy = BSS_BUGSUMMARY_PROXY + queryStringProxy.toString();
		String requestPath = BSS_BUGSUMMARY + queryString.toString();
		try {
			bugSearchResults = searchService.searchResult(requestPath);
		} catch (HttpException e) {
			logger.error("HttpException in Multiple Bug Search Action");
		} catch (JSONException e) {
			logger.error("Exception in JSON data conversion in Multiple Bug Search Action");
		} catch (IOException e) {
			logger.error("IOException in Multiple Bug Search Action");
		}
		List<String> errorList = new java.util.ArrayList<String>();
		if (bugSearchResults.getErrorSearchResults() != null) {
			errorList = getErrorListForMultipleBugIdSearch(bugSearchResults, request_locale, errorList);
			model.addAttribute("errorList", errorList);
		}

		PortletSession session = request.getPortletSession();
		if (session != null) {
			session.setAttribute(REQUEST_PATH_PROXY, requestPathProxy, PortletSession.PORTLET_SCOPE);
			session.setAttribute("searchCriteria", bugIds.trim(), PortletSession.PORTLET_SCOPE);
			session.setAttribute(IS_ADV_SEARCH_PAGE, false, PortletSession.PORTLET_SCOPE);
			session.setAttribute("bss_keyword", bugIds, PortletSession.PORTLET_SCOPE);
			session.setAttribute("isFilter", false, PortletSession.PORTLET_SCOPE);
			session.setAttribute(BSS_SEARCH_TYPE, "bugId", PortletSession.PORTLET_SCOPE);
		}
		request.setAttribute(BSSConstants.PORTLET_VIEW, SEARCH);

		if (bugSearchResults != null && bugSearchResults.getErrorCode() != null) {
			error = bugSearchResults.getErrorMessage();
			model.addAttribute(MESSAGE, error);
		} else if (bugSearchResults != null && bugSearchResults.getTotalNumberOfResults() != null
				&& bugSearchResults.getTotalNumberOfResults().equals(ZERO)) {
			error = messgaeSource.getMessage("multiplebugsearch.noresult.message", null, request_locale);
			model.addAttribute(MESSAGE, error);
		} else if (bugSearchResults != null && bugSearchResults.getBugDetailJSON() != null) {
			model.addAttribute(REQUEST_PATH_PROXY, requestPathProxy);
			request.setAttribute(BSSConstants.PORTLET_VIEW, SEARCH_RESULT);
		} else {
			model.addAttribute(MESSAGE, error);
		}
		model.addAttribute(SEARCH, search);
		logger.info("Multiple Bug search Action finished");
	}

	/*
	 * This method is used to handle url based request for single Bug Id Search
	 * and set the result page bugDetail page
	 */
	private void setBugIdSearchResult(String bugId, RenderRequest request, Model model) {
		Search search = createSearch();
		search.setKeywords(bugId);
		String userId = getUserSessionBean(request).get(BSSConstants.USER).toString();
		Integer userType = Integer.parseInt(getUserSessionBean(request).get(ACCESS_LEVEL).toString());
		String error = messgaeSource.getMessage("default.exception", null, request.getLocale());
		logger.info("Bug By ID search Action start");
		try {
			bugDetail = viewBugDetailsService.getBugDetailById(bugId.trim(), userId, userType, request.getLocale());
		} catch (HttpException e) {
			logger.error("HttpException in Bug By ID Search Action");
		} catch (JSONException e) {
			logger.error("JSON data conversion in Bug By ID Search Action");
		} catch (IOException e) {
			logger.error("IOException in Bug By ID Search Action");
		}
		request.setAttribute(BSSConstants.PORTLET_VIEW, SEARCH);
		if (bugDetail != null && bugDetail.getErrorCode() != null) {
			if (bugDetail.getErrorCode().equals("405")) {
				error = messgaeSource.getMessage("errorcode.405", new Object[] { bugId.trim() }, request.getLocale());
			} else if (bugDetail.getErrorCode().equals("408")) {
				error = messgaeSource.getMessage("errorcode.408", new Object[] { bugId.trim() }, request.getLocale());
			} else {
				error = bugDetail.getErrorMessage();
			}
			model.addAttribute(MESSAGE, error);
		} else if (bugDetail != null && bugDetail.getBugId() != null) {
			model.addAttribute("isSearchResultNav", false);
			model.addAttribute("bugDetail", bugDetail);
			request.setAttribute(BSSConstants.PORTLET_VIEW, "viewBugDetails");
			return;
		} else {
			model.addAttribute(MESSAGE, error);
		}
		model.addAttribute(SEARCH, search);
		logger.info("Bug By ID search Action finished");
	}

	/*
	 * This method is used to handle url based request for Advanced Search
	 * feature like Category id, product id Search and set the result page as
	 * Search Result page with All available bugs
	 */
	private void setAdvanceSearch(String categoryId, String productId, String softVer, String softVerType,
			RenderRequest request, Model model) {
		Search search = createSearch();
		search.setKeywords(BSSConstants.EMPTY_STRING);
		search.setProduct(productId);
		search.setProductCategory(categoryId);
		search.setSoftwareVersion(softVer);
		search.setVersionType(softVerType);
		String userId = getUserSessionBean(request).get(BSSConstants.USER).toString();
		String error = messgaeSource.getMessage("default.exception", null, request.getLocale());
		logger.info("Advanced search Action start");
		StringBuilder query = new StringBuilder(BSSConstants.EMPTY_STRING);
		String versionType = BSSConstants.EMPTY_STRING;
		StringBuilder searchCriteria = new StringBuilder(BSSConstants.EMPTY_STRING);
		if (categoryId != null && !categoryId.equals(BSSConstants.EMPTY_STRING) && !categoryId.equals(ALL_PRODUCTS)) {
			query.append(categoryId);
			query.append(BACK_SLASH);
		}
		if (productId != null && !productId.equals(BSSConstants.EMPTY_STRING)) {
			query.append(productId);
			query.append("/NA");
			searchCriteria.append(productId);
		}
		if (softVer != null && !softVer.equals(BSSConstants.EMPTY_STRING)) {
			query.append(BACK_SLASH);
			query.append(softVer);
			searchCriteria.append(", software version - ").append(softVer);
		}
		if (softVerType != null && !softVerType.equals(BSSConstants.EMPTY_STRING)) {
			versionType = "&vt=" + softVerType;
			String versionTypeText = BSSConstants.EMPTY_STRING;
			if (softVerType.equals("foundIn")) {
				versionTypeText = messgaeSource.getMessage("advsearchpage.softversion.foundin", null, request
						.getLocale());
			} else if (softVerType.equals("fixedIn")) {
				versionTypeText = messgaeSource.getMessage("advsearchpage.softversion.fixedin", null, request
						.getLocale());
			} else if (softVerType.equals("affected")) {
				versionTypeText = messgaeSource.getMessage("advsearchpage.softversion.knownaffectedversion", null,
						request.getLocale());
			}
			searchCriteria.append(", version type - ").append(versionTypeText);
		}
		StringBuilder proxyQuery = new StringBuilder(query).append("?st=adv").append(versionType);

		StringBuilder reqQuery = new StringBuilder(query).append(UN).append(userId).append("&ci=BTK&st=adv").append(
				"&rpp=25&sort=severityCode:asc,bugId:desc").append(versionType);

		String requestPathProxy = BSS_BUGSUMMARY_PROXY + proxyQuery.toString();
		String requestPath = BSS_BUGSUMMARY + reqQuery.toString();
		try {
			bugSearchResults = searchService.searchResult(requestPath);
		} catch (HttpException e) {
			logger.error("HttpException in Advanced Search Action");
		} catch (JSONException e) {
			logger.error("JSON data conversion in Advanced Search Action");
		} catch (IOException e) {
			logger.error("IOException in Advanced Search Action");
		}
		PortletSession session = request.getPortletSession();
		if (session != null) {
			session.setAttribute(REQUEST_PATH_PROXY, requestPathProxy, PortletSession.PORTLET_SCOPE);
			session.setAttribute("searchCriteria", searchCriteria.toString(), PortletSession.PORTLET_SCOPE);
			session.setAttribute(IS_ADV_SEARCH_PAGE, true, PortletSession.PORTLET_SCOPE);
			session.setAttribute("searchObject", search, PortletSession.PORTLET_SCOPE);
			session.setAttribute("isFilter", false, PortletSession.PORTLET_SCOPE);
			session.setAttribute(BSS_SEARCH_TYPE, "adv", PortletSession.PORTLET_SCOPE);
		}
		request.setAttribute(BSSConstants.PORTLET_VIEW, SEARCH);
		if (bugSearchResults != null && bugSearchResults.getErrorCode() != null) {
			model.addAttribute(MESSAGE, error);
		} else if (bugSearchResults != null && bugSearchResults.getTotalNumberOfResults().equals(ZERO)) {
			error = messgaeSource.getMessage("search.noresult.message", null, request.getLocale());
			model.addAttribute(MESSAGE, error);
		} else if (bugSearchResults != null && bugSearchResults.getBugDetailJSON() != null) {
			model.addAttribute(REQUEST_PATH_PROXY, requestPathProxy);
			request.setAttribute(BSSConstants.PORTLET_VIEW, "searchResult");
		} else {
			model.addAttribute(MESSAGE, error);
		}
		model.addAttribute(SEARCH, search);
		logger.info("Advanced search Action Finished");
	}
}