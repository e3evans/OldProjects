package com.cisco.swtg.bss.web.portletcontroller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.StateAwareResponse;

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

import com.cisco.swtg.bss.util.BSSConstants;
import com.cisco.swtg.bss.web.domain.BugDetail;
import com.cisco.swtg.bss.web.domain.Search;
import com.cisco.swtg.bss.web.service.ViewBugDetailsService;

/**
 * This class is used as controller for BugDetail action
 * 
 * @author teprasad
 */
@Controller(value="bugdetailcontroller")
@SessionAttributes(value = { "search", "bugDetail" })
@RequestMapping("VIEW")
public class BugDetailController extends BasePortlet {

	@Autowired
	@Qualifier("viewService")
	private ViewBugDetailsService viewBugDetailsService;

	private BugDetail bugDetail;

	public void setViewBugDetailsService(ViewBugDetailsService viewBugDetaisService) {
		this.viewBugDetailsService = viewBugDetaisService;
	}

	/**
	 * This method is used for bugDetail action and get the bugDetail from Bug
	 * Detail Service and call view page for Render
	 * 
	 * @param bugId
	 * @param request
	 * @param response
	 * @param model
	 */
	/*@RequestMapping(params = "action=viewBugDetailAction")
	public void viewBugDetail(@RequestParam(BUG_ID) String bugId, @RequestParam("filterData") String filterData,
			ActionRequest request, ActionResponse response, Model model) {

		JSONObject filterJSON = null;
		Boolean isFilter = false;
		Search search = createSearch();
		if (request.getPortletSession().getAttribute("bss_keyword") != null) {
			search.setKeywords(request.getPortletSession().getAttribute("bss_keyword").toString());
		}
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
		if (!filterData.equals("EMPTY")) {
			Locale locale = request.getLocale();
			filterJSON = getFilterJSON(filterData, locale);
			isFilter = true;
		}
		PortletSession session = request.getPortletSession();
		if (session != null) {
			session.setAttribute("filterJSON", filterJSON, PortletSession.PORTLET_SCOPE);
			session.setAttribute("isFilter", isFilter, PortletSession.PORTLET_SCOPE);
		}
		logger.info("View bug Detail web service end");
		if (bugDetail != null) {
			model.addAttribute("bugDetail", bugDetail);
			response.setRenderParameter(VIEW, "viewBugDetailPage");
		}
		model.addAttribute(SEARCH, search);
	}
	*/
	
	/**
	 * This method is used for bugDetail action when user clicks on bugid links in search results page and get the bugdetails from
	 * Detail Service and call view page for Render
	 * 
	 * @param bugId
	 * @param request
	 * @param filterdata
	 * @param model
	 */
	
	public void setBugIdfilterSearchResult(String bugId,String filterData, RenderRequest request,Model model) {

		JSONObject filterJSON = null;
		Boolean isFilter = false;
		Search search = createSearch();
		if (request.getPortletSession().getAttribute("bss_keyword") != null) {
			search.setKeywords(request.getPortletSession().getAttribute("bss_keyword").toString());
		}
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
		String date2=bugDetail.getBugLastModifiedDate();
		System.out.println("***************************::::::::::::::::::::::::{{{{{{{{{{{{{{{{{{{{{{{");
		System.out.println(date2);
		
		if (!filterData.equals("EMPTY")) {
			Locale locale = request.getLocale();
			filterJSON = getFilterJSON(filterData, locale);
			isFilter = true;
		}
		PortletSession session = request.getPortletSession();
		if (session != null) {
			session.setAttribute("filterJSON", filterJSON, PortletSession.PORTLET_SCOPE);
			session.setAttribute("isFilter", isFilter, PortletSession.PORTLET_SCOPE);
		}
		logger.info("View bug Detail web service end");
		if (bugDetail != null) {
			model.addAttribute("bugDetail", bugDetail);

			request.setAttribute(BSSConstants.PORTLET_VIEW, "viewBugDetails");
			//response.setRenderParameter(VIEW, "viewBugDetailPage");
		}
		model.addAttribute(SEARCH, search);
	}


	/**
	 * This method is used to search a bug with bugId and process the result to
	 * search result screen
	 * 
	 * @param search
	 * @param request
	 * @param response
	 * @param model
	 */
	/*@SuppressWarnings("deprecation")
	@RequestMapping(params = "action=bugByIdAction")*/
	public void searchBug(@ModelAttribute(SEARCH) Search search, boolean isResultPage,
	 RenderRequest request, RenderResponse response, Model model) {
		String userId = getUserSessionBean(request).get(BSSConstants.USER).toString();
		Integer userType = Integer.parseInt(getUserSessionBean(request).get(ACCESS_LEVEL).toString());
		String bugId = URLEncoder.encode(search.getKeywords().replaceAll(";", BSSConstants.EMPTY_STRING).replaceAll(
				",", BSSConstants.EMPTY_STRING).trim());
		String error = messgaeSource.getMessage("default.exception", null, request.getLocale());
		logger.info("Bug By ID search Action start");
		try {
			bugDetail = viewBugDetailsService.getBugDetailById(bugId, userId, userType, request.getLocale());
		} catch (HttpException e) {
			logger.error("HttpException in Bug By ID Search Action");
		} catch (JSONException e) {
			logger.error("JSON data conversion in Bug By ID Search Action");
		} catch (IOException e) {
			logger.error("IOException in Bug By ID Search Action");
		}
		if (isResultPage) {
			((StateAwareResponse) response).setRenderParameter(VIEW, SEARCH_RESULT_VIEW);
			model.addAttribute(REQUEST_PATH_PROXY, request.getPortletSession().getAttribute(REQUEST_PATH_PROXY)
					.toString());
		} else {
			((StateAwareResponse) response).setRenderParameter(VIEW, SEARCH_FORM);
		}
		model.addAttribute(SEARCH, search);
		if (bugDetail != null && bugDetail.getErrorCode() != null) {
			if (bugDetail.getErrorCode().equals("405")) {
				error = messgaeSource.getMessage("errorcode.405", new Object[] { bugId }, request.getLocale());
			} else if (bugDetail.getErrorCode().equals("408")) {
				error = messgaeSource.getMessage("errorcode.408", new Object[] { bugId }, request.getLocale());
			} else {
				error = bugDetail.getErrorMessage();
			}
			model.addAttribute(MESSAGE, error);
		} else if (bugDetail != null && bugDetail.getBugId() != null) {
			model.addAttribute("isSearchResultNav", false);
			model.addAttribute("bugDetail", bugDetail);
			((StateAwareResponse) response).setRenderParameter(VIEW, "viewBugDetailPage");
			return;
		} else {
			model.addAttribute(MESSAGE, error);
		}
		logger.info("Bug By ID search Action finished");
	}
}