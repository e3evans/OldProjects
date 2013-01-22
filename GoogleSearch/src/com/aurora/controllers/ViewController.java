package com.aurora.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.aurora.org.googlesearch.forms.SearchForm;
import com.aurora.org.googlesearch.utility.XMLProcessorUtil;
import com.aurora.webservice.client.GoogleServiceClient;

@Controller
@RequestMapping("VIEW")
public class ViewController {
	
	private static String GOOGLE_SEARCH_URL = "http://google.aurora.org/search?site=default_collection&output=xml_no_dtd";
	private static String GOOGLE_CLUSTER_URL = "http://google.aurora.org/cluster?site=default_collection&client=default_collection&coutput=xml";
	private static String GOOGLE_CLICK_URL = "http://google.aurora.org/click?";
	private static String XSL_STYLSHEET = "xsl/searchResultsMod.xsl";
	public static String SESS_SEARCH_TERM = "google.search.term";
	public static String SEARCH_RESULTS_BOX = "searchResultsBox";
	public static String PREF_COLLECTIONS = "com.aurora.org.collections";


	
	@SuppressWarnings("unchecked")
	@RequestMapping
	public ModelAndView defaultView (RenderRequest request, RenderResponse responses, @SuppressWarnings("rawtypes") Map model,@ModelAttribute("searchForm")SearchForm form) throws UnsupportedEncodingException{
		HttpServletRequest hsreq= com.ibm.ws.portletcontainer.portlet.PortletUtils.getHttpServletRequest(request);
		request.getPortletSession().setAttribute(SESS_SEARCH_TERM, hsreq.getParameter("q"));
		if (form == null)form = new SearchForm();
		form.setSearchResults(XMLProcessorUtil.getSearchResultsHTML(XSL_STYLSHEET, createURLwithParams(GOOGLE_SEARCH_URL, hsreq)+"&start=0&page=1&num=10", 
				createURLwithParams(GOOGLE_CLUSTER_URL, hsreq), request.getContextPath(),request.getPreferences()).toString());	
		model.put("searchForm", form);
		
		
		return new ModelAndView("view","searchForm",form);
	}
	
	
	@ResourceMapping(value="search")
	public ModelAndView doSearch(ResourceRequest request, ResourceResponse response, @SuppressWarnings("rawtypes") Map model,@ModelAttribute("searchForm")SearchForm form) throws UnsupportedEncodingException{
		if (form == null)form = new SearchForm();
		form.setSearchResults_frag(XMLProcessorUtil.getSearchResultsHTML(XSL_STYLSHEET, createURLwithParams(GOOGLE_SEARCH_URL, request)
				,createURLwithParams(GOOGLE_CLUSTER_URL, request),request.getContextPath(),request.getPreferences()).toString());		
		return new ModelAndView("search_frag","searchForm",form);
	}
	
	@ResourceMapping(value="googleClick")
	public void doGoogleClick(ResourceRequest request, ResourceResponse response) throws Exception{
		GoogleServiceClient.getInstance().doGetNoContent(createURLwithParams(GOOGLE_CLICK_URL,request), "");
	}
	
	private String createURLwithParams(String urlIn,HttpServletRequest request) throws UnsupportedEncodingException{
		@SuppressWarnings("unchecked")
		Enumeration<String> e = request.getParameterNames();
		StringBuffer sb = new StringBuffer();
		sb.append(urlIn);
		while (e.hasMoreElements()){
			String temp = e.nextElement();
			sb.append("&"+temp+"="+URLEncoder.encode(request.getParameter(temp),"ISO-8859-1"));
		}
		return sb.toString();
	}
	private String createURLwithParams(String urlIn, ResourceRequest request) throws UnsupportedEncodingException{
		Enumeration<String> e = request.getParameterNames();
		StringBuffer sb = new StringBuffer();
		sb.append(urlIn);
		while (e.hasMoreElements()){
			String temp = e.nextElement();
			sb.append("&"+temp+"="+URLEncoder.encode(request.getParameter(temp),"ISO-8859-1"));
		}
		return sb.toString();
	}
	
}
