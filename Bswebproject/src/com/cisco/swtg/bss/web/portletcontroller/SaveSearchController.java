package com.cisco.swtg.bss.web.portletcontroller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletSession;

import org.apache.commons.httpclient.HttpException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.cisco.swtg.bss.util.BSSConstants;
import com.cisco.swtg.bss.web.domain.BugDetail;
import com.cisco.swtg.bss.web.domain.BugSearchResults;
import com.cisco.swtg.bss.web.domain.Search;
import com.cisco.swtg.bss.web.service.SearchService;
import com.cisco.swtg.bss.web.service.ViewBugDetailsService;

/**
 * This class is used to show savedBug details and saved search in result page
 * from myBug watch Controller
 * 
 * @author teprasad
 * 
 */
@Controller
@RequestMapping("VIEW")
@SessionAttributes(value = { "search", "bugDetail" })
public class SaveSearchController extends BasePortlet {

	private static final String NA = "NA";

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

	/**
	 * This method is execute when request is come form myBugWatch portlet and
	 * display the page for savedBug
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(params = "action=bugIdWatchAction")
	public void showSaveBugId(ActionRequest request, ActionResponse response, Model model) {
		logger.info("bugId" + request.getParameter("bugId"));
		logger.info("type" + request.getParameter("type"));
		authenticateUser(request);
		Map<String, Object> bugWatchMap = new HashMap<String, Object>();
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			String[] paramValues = request.getParameterValues(paramName);
			if (paramValues.length == 1) {
				bugWatchMap.put(paramName, paramValues[0]);
			} else {
				bugWatchMap.put(paramName, paramValues);
			}
		}
		Search search = createSearch();
		String bugId = (String) bugWatchMap.get("bugId");
		search.setKeywords(bugId);
		String userId = getUserSessionBean(request).get(BSSConstants.USER).toString();
		Integer userType = Integer.parseInt(getUserSessionBean(request).get(ACCESS_LEVEL).toString());
		logger.info("View bug Detail web service start");
		try {
			bugDetail = viewBugDetailsService.getBugDetailById(bugId, userId, userType, request.getLocale());
		} catch (HttpException e) {
			logger.error("HttpException in View Bug Detail Action");
		} catch (JSONException e) {
			logger.error("JSONException in View Bug Detail Action");
		} catch (IOException e) {
			logger.error("IOException View Bug Detail Action");
		}
		logger.info("View bug Detail web service end");
		if (bugDetail != null && bugDetail.getBugId() != null) {
			model.addAttribute("isSearchResultNav", false);
			model.addAttribute("bugDetail", bugDetail);
			response.setRenderParameter(VIEW, "viewBugDetailPage");
		}
		model.addAttribute(SEARCH, search);

	}

	/**
	 * This method is execute when request is come form myBugWatch portlet and
	 * display the page for SavedSearch.
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(params = "action=searchWatchAction")
	public void showSaveSearch(ActionRequest request, ActionResponse response, Model model) {
		authenticateUser(request);
		Map<String, Object> bugWatchMap = new HashMap<String, Object>();
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			String[] paramValues = request.getParameterValues(paramName);
			if (paramValues.length == 1) {
				bugWatchMap.put(paramName, paramValues[0]);
			} else {
				bugWatchMap.put(paramName, paramValues);
			}
		}
		Search search = createSearch();
		JSONObject filterJSON = null;
		Boolean isFilter = false;
		String savedSearchType = (String) bugWatchMap.get("savedSearchType");
		String userId = getUserSessionBean(request).get(BSSConstants.USER).toString();
		String error = messgaeSource.getMessage("default.exception", null, request.getLocale());
		logger.info("Basic keyword search Action start");
		StringBuilder queryString = createSaveSearchURL(bugWatchMap, userId, savedSearchType, search);
		String filterData = getFilterData(bugWatchMap);
		if (!filterData.equals("EMPTY")) {
			Locale locale = request.getLocale();
			filterJSON = getFilterJSON(filterData, locale);
			isFilter = true;
		}
		StringBuilder queryStringProxy = queryString;
		queryString = queryString.append("&un=").append(userId).append("&ci=BTK");
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
		List<String> errorList = new java.util.ArrayList<String>();
		if (bugSearchResults.getErrorSearchResults() != null) {
			errorList = getErrorListForMultipleBugIdSearch(bugSearchResults, request.getLocale(), errorList);
			model.addAttribute("errorList", errorList);
		}
		PortletSession session = request.getPortletSession();
		if (session != null) {
			session.setAttribute(REQUEST_PATH_PROXY, requestPathProxy, PortletSession.PORTLET_SCOPE);
			session.setAttribute("bss_keyword", search.getKeywords(), PortletSession.PORTLET_SCOPE);
			session.setAttribute("filterJSON", filterJSON, PortletSession.PORTLET_SCOPE);
			session.setAttribute("isFilter", isFilter, PortletSession.PORTLET_SCOPE);
			session.setAttribute(BSS_SEARCH_TYPE, savedSearchType, PortletSession.PORTLET_SCOPE);
			if (savedSearchType.equals("adv")) {
				session.setAttribute(IS_ADV_SEARCH_PAGE, true, PortletSession.PORTLET_SCOPE);
				String searchCriteria = getSearchCriteria(search, request.getLocale());
				session.setAttribute("searchCriteria", searchCriteria, PortletSession.PORTLET_SCOPE);
				session.setAttribute("searchObject", search, PortletSession.PORTLET_SCOPE);
			} else {
				session.setAttribute(IS_ADV_SEARCH_PAGE, false, PortletSession.PORTLET_SCOPE);
				session.setAttribute("searchCriteria", search.getKeywords(), PortletSession.PORTLET_SCOPE);
			}
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
			response.setRenderParameter(VIEW, "searchResultView");
		} else {
			model.addAttribute(MESSAGE, error);
		}
		model.addAttribute(SEARCH, search);
		logger.info("Basic keyword search Action finished");
	}

	/*
	 * This method is used to create urls when user click on saved search of
	 * keyword or advanced search or multiple bug id search
	 */
	@SuppressWarnings("deprecation")
	private StringBuilder createSaveSearchURL(Map<String, Object> bugWatchMap, String userId, String searchType,
			Search search) {
		StringBuilder queryStringSavedSearch = null;
		boolean isMdfCategoryId = false;
		// StringBuilder queryStringForAdvancedSearch = new StringBuilder();
		if (searchType.equals("adv")) {
			queryStringSavedSearch = new StringBuilder();

			if (bugWatchMap.containsKey("mdfCategoryId")) {
				String mdfCategoryId = (String) bugWatchMap.get("mdfCategoryId");
				if (!mdfCategoryId.equals(BSSConstants.EMPTY_STRING) && !mdfCategoryId.equals(NA)) {
					queryStringSavedSearch.append(mdfCategoryId).append("/");
					search.setProductCategory(mdfCategoryId);
					isMdfCategoryId = true;
				}
			}
			if (bugWatchMap.containsKey("mdfProductConceptId")) {
				String mdfProductConceptId = (String) bugWatchMap.get("mdfProductConceptId");
				if (!mdfProductConceptId.equals(BSSConstants.EMPTY_STRING) && !mdfProductConceptId.equals(NA)) {
					if (!mdfProductConceptId.contains("/") && !isMdfCategoryId) {
						queryStringSavedSearch.append("NA/");
					}
					queryStringSavedSearch.append(mdfProductConceptId);
					search.setProduct(mdfProductConceptId);
					search.setProductName((String) bugWatchMap.get("mdfProductName"));
				}
			}
			// For Model Appending NA
			queryStringSavedSearch.append("/NA");
			if (bugWatchMap.containsKey("ngrpVersionName")) {
				String ngrpVersionName = (String) bugWatchMap.get("ngrpVersionName");
				if (!ngrpVersionName.equals(BSSConstants.EMPTY_STRING) && !ngrpVersionName.equals(NA)) {
					queryStringSavedSearch.append("/").append(ngrpVersionName);
					search.setSoftwareVersion(ngrpVersionName);
				}
			}
			queryStringSavedSearch.append("?st=adv&rpp=25&sort=severityCode:asc");
			// For Version Type As URL parameter rather than rest parameter.
			if (bugWatchMap.containsKey("versionType")) {
				String versionType = (String) bugWatchMap.get("versionType");
				if (!versionType.equals(BSSConstants.EMPTY_STRING) && !versionType.equals(NA)) {
					queryStringSavedSearch.append("&vt=").append(versionType);
					search.setVersionType(versionType);
				}
			}
		} else if (searchType.equals("kw")) {
			String keywords = (String) bugWatchMap.get("keywordSearch");
			String keyword = URLEncoder.encode(keywords.trim());
			search.setKeywords(keywords);
			queryStringSavedSearch = new StringBuilder(keyword).append("?st=kw&rpp=25");

		} else if (searchType.equals("bugId")) {
			String keywords = (String) bugWatchMap.get("keywordSearch");
			String keyword = URLEncoder.encode(keywords.trim());
			search.setKeywords(keywords);
			queryStringSavedSearch = new StringBuilder("?bugId=").append(keyword);
		}
		return queryStringSavedSearch;
	}

	/*
	 * This method is used to set filter data as JSONFormat from saved filter
	 * options
	 */
	private String getFilterData(Map<String, Object> bugWatchMap) {
		boolean filterFlag = false;
		StringBuilder filterData = new StringBuilder(BSSConstants.EMPTY_STRING);
		if (bugWatchMap.containsKey("severityCodes")) {
			String severityCodes = (String) bugWatchMap.get("severityCodes");
			if (!severityCodes.equals(BSSConstants.EMPTY_STRING) && !severityCodes.equals(NA)) {
				severityCodes = severityCodes.replaceAll(",", "|");
				if (filterFlag) {
					filterData.append(",se:" + severityCodes);
				} else {
					filterFlag = true;
					filterData.append("se:" + severityCodes);
				}
			}
		}
		if (bugWatchMap.containsKey("lifecycleStateCodes")) {
			String lifecycleStateCodes = (String) bugWatchMap.get("lifecycleStateCodes");
			if (!lifecycleStateCodes.equals(BSSConstants.EMPTY_STRING) && !lifecycleStateCodes.equals(NA)) {
				lifecycleStateCodes = lifecycleStateCodes.replaceAll(",", "|");
				if (filterFlag) {
					filterData.append(",as:" + lifecycleStateCodes);
				} else {
					filterFlag = true;
					filterData.append("as:" + lifecycleStateCodes);
				}
			}
		}
		if (bugWatchMap.containsKey("resolutionStatusCodes")) {
			String resolutionStatusCodes = (String) bugWatchMap.get("resolutionStatusCodes");
			if (!resolutionStatusCodes.equals(BSSConstants.EMPTY_STRING) && !resolutionStatusCodes.equals(NA)) {
				resolutionStatusCodes = resolutionStatusCodes.replaceAll(",", "|");
				if (filterFlag) {
					filterData.append(",sg:" + resolutionStatusCodes);
				} else {
					filterFlag = true;
					filterData.append("sg:" + resolutionStatusCodes);
				}
			}
		}
		if (bugWatchMap.containsKey("technologyNames")) {
			String technologyNames = (String) bugWatchMap.get("technologyNames");
			if (!technologyNames.equals(BSSConstants.EMPTY_STRING) && !technologyNames.equals(NA)) {
				technologyNames = technologyNames.replaceAll(",", "|");
				if (filterFlag) {
					filterData.append(",tn:" + technologyNames);
				} else {
					filterFlag = true;
					filterData.append("tn:" + technologyNames);
				}
			}
		}
		if (bugWatchMap.containsKey("filterKeyword")) {
			String filterKeyword = (String) bugWatchMap.get("filterKeyword");
			if (!filterKeyword.equals(BSSConstants.EMPTY_STRING) && !filterKeyword.equals(NA)) {
				filterKeyword = filterKeyword.replaceAll(",", "|");
				if (filterFlag) {
					filterData.append(",kw:" + filterKeyword);
				} else {
					filterFlag = true;
					filterData.append("kw:" + filterKeyword);
				}
			}
		}
		if (bugWatchMap.containsKey("includeAllUnknown")) {
			String includeAllUnknown = (String) bugWatchMap.get("includeAllUnknown");
			if (includeAllUnknown.equals("Y")) {
				if (filterFlag) {
					filterData.append(",ib:true");
				} else {
					filterFlag = true;
					filterData.append("ib:true");
				}
			}
		}
		if (bugWatchMap.containsKey("accessLevel")) {
			String accessLevel = (String) bugWatchMap.get("accessLevel");
			if (!accessLevel.equals(NA)) {
				if (filterFlag) {
					filterData.append(",ib:" + accessLevel);
				} else {
					filterFlag = true;
					filterData.append("ib:" + accessLevel);
				}
			}
		}
		if (bugWatchMap.containsKey("lastModifiedPeriodId")) {
			String modifiedPeriod = (String) bugWatchMap.get("lastModifiedPeriodId");
			if (!modifiedPeriod.equals("1") && !modifiedPeriod.equals(NA)) {
				String dateLabel = BSSConstants.EMPTY_STRING;
				if (!modifiedPeriod.equals(BSSConstants.EMPTY_STRING)) {// TODO
					if (modifiedPeriod.equals("3")) {
						dateLabel = "Last week";
					} else if (modifiedPeriod.equals("7")) {
						dateLabel = "Last 3 months";
					} else if (modifiedPeriod.equals("8")) {
						dateLabel = "Last 6 months";
					} else if (modifiedPeriod.equals("6")) {
						dateLabel = "Last year";
					} else if (modifiedPeriod.equals("9")) {
						dateLabel = "Before 1 year";
					}
					if (filterFlag) {
						filterData.append(",filterBlmd:" + dateLabel);
					} else {
						filterFlag = true;
						filterData.append("filterBlmd:" + dateLabel);
					}
				}

			}
		}
		return filterData.toString().equals(BSSConstants.EMPTY_STRING) ? "EMPTY" : filterData.toString();
	}

	/*
	 * This method is used to get searchCriteria for Advanced search input
	 */
	private String getSearchCriteria(Search search, Locale locale) {
		StringBuilder searchCriteria = new StringBuilder(BSSConstants.EMPTY_STRING);
		String productName = search.getProductName();
		if (productName != null && !productName.equals(BSSConstants.EMPTY_STRING) && !productName.equals(NA)) {
			searchCriteria.append(productName);
		}
		String softVer = search.getSoftwareVersion();
		if (softVer != null && !softVer.equals(BSSConstants.EMPTY_STRING) && !softVer.equals(NA)) {
			searchCriteria.append(", software version - ").append(softVer);
		}
		String softVerType = search.getVersionType();
		if (softVerType != null && !softVerType.equals(BSSConstants.EMPTY_STRING) && !softVerType.equals(NA)) {
			String versionTypeText = BSSConstants.EMPTY_STRING;
			if (softVerType.equals("foundIn")) {
				versionTypeText = messgaeSource.getMessage("advsearchpage.softversion.foundin", null, locale);
			} else if (softVerType.equals("fixedIn")) {
				versionTypeText = messgaeSource.getMessage("advsearchpage.softversion.fixedin", null, locale);
			} else if (softVerType.equals("affected")) {
				versionTypeText = messgaeSource.getMessage("advsearchpage.softversion.knownaffectedversion", null,
						locale);
			}
			searchCriteria.append(", version type - ").append(versionTypeText);

		}
		return searchCriteria.toString();
	}

}
