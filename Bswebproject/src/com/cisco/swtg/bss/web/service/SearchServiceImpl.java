package com.cisco.swtg.bss.web.service;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cisco.swtg.bss.delegates.ServiceInterfaceDelegate;
import com.cisco.swtg.bss.web.domain.BugSearchResults;

/**
 * This class is Implementation of SearchService interface and methods can be
 * called using the interface
 * 
 * @author teprasad
 * 
 */
@Service(value = "searchService")
public class SearchServiceImpl implements SearchService {

	private static final String ERROR_RESPONSE = "errorResponse";

	private static final String ERROR_SEARCH_RESULTS = "errorSearchResults";

	private static final String BUG_SEARCH_RESULTS = "bugSearchResults";

	private static final Log logger = LogFactory.getLog(SearchServiceImpl.class);

	@Autowired
	private ServiceInterfaceDelegate serviceInterfaceDelegate;

	public void setServiceInterfaceDelegate(ServiceInterfaceDelegate serviceInterfaceDelegate) {
		this.serviceInterfaceDelegate = serviceInterfaceDelegate;
	}

	public BugSearchResults searchResult(String requestPath) throws HttpException, JSONException, IOException {

		logger.info("Calling searchResult method start");
		JSONObject bugSummaryJson = new JSONObject(serviceInterfaceDelegate.processRequestCache(requestPath, false,
				null, null));
		logger.info("Calling searchResult method end");
		BugSearchResults bugSearchResults = new BugSearchResults();
		logger.info("Setting BugSearchResults data start");
		setBugSearchResultsData(bugSummaryJson, bugSearchResults);
		if (!bugSummaryJson.isNull(BUG_SEARCH_RESULTS)) {
			bugSearchResults.setBugDetailJSON(bugSummaryJson.getJSONArray(BUG_SEARCH_RESULTS));
		}
		if (!bugSummaryJson.isNull(ERROR_SEARCH_RESULTS)) {
			bugSearchResults.setErrorSearchResults(bugSummaryJson.getJSONArray(ERROR_SEARCH_RESULTS));
		}
		if (!bugSummaryJson.isNull(ERROR_RESPONSE)) {
			JSONObject errorResponse = (JSONObject) bugSummaryJson.get(ERROR_RESPONSE);
			bugSearchResults.setErrorCode(errorResponse.get("publicErrorCode").toString());
			bugSearchResults.setErrorMessage(errorResponse.optString("publicErrorMessage"));
		}
		logger.info("Setting BugSearchResults data end");
		return bugSearchResults;
	}

	/*
	 * This method is used to set pagination details from JSON data to
	 * BugSearchResults object
	 */
	private void setBugSearchResultsData(JSONObject bugSearchResultsJson, BugSearchResults bugSearchResults)
			throws JSONException {
		if (!bugSearchResultsJson.isNull("nextPageNumber")) {
			bugSearchResults.setNextPageNumber(bugSearchResultsJson.getString("nextPageNumber"));
			bugSearchResults.setNextPageURL(bugSearchResultsJson.getString("nextPageURL"));
			bugSearchResults.setNoOfItemsPerPage(bugSearchResultsJson.getString("noOfItemsPerPage"));
			bugSearchResults.setPreviousPageNumber(bugSearchResultsJson.getString("previousPageNumber"));
			bugSearchResults.setPreviousPageURL(bugSearchResultsJson.getString("previousPageURL"));
			bugSearchResults.setTotalNumberOfResults(bugSearchResultsJson.getString("totalNumberOfResults"));
		}
	}
}
