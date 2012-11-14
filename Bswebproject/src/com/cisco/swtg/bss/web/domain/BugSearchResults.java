package com.cisco.swtg.bss.web.domain;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * This class is used as domain object for BugSearchResults and contains only
 * setter/getter
 * 
 * @author teprasad
 * 
 * 
 */
@Configurable
public class BugSearchResults {

	private String previousPageURL;
	private String nextPageURL;
	private String noOfItemsPerPage;
	private String previousPageNumber;
	private String nextPageNumber;
	private String totalNumberOfResults;
	private JSONArray bugDetailJSON;
	private String errorCode;
	private String errorMessage;
	private JSONArray errorSearchResults;

	public String getPreviousPageURL() {
		return previousPageURL;
	}

	public void setPreviousPageURL(String previousPageURL) {
		this.previousPageURL = previousPageURL;
	}

	public String getNextPageURL() {
		return nextPageURL;
	}

	public void setNextPageURL(String nextPageURL) {
		this.nextPageURL = nextPageURL;
	}

	public String getNoOfItemsPerPage() {
		return noOfItemsPerPage;
	}

	public void setNoOfItemsPerPage(String noOfItemsPerPage) {
		this.noOfItemsPerPage = noOfItemsPerPage;
	}

	public String getPreviousPageNumber() {
		return previousPageNumber;
	}

	public void setPreviousPageNumber(String previousPageNumber) {
		this.previousPageNumber = previousPageNumber;
	}

	public String getNextPageNumber() {
		return nextPageNumber;
	}

	public void setNextPageNumber(String nextPageNumber) {
		this.nextPageNumber = nextPageNumber;
	}

	public String getTotalNumberOfResults() {
		return totalNumberOfResults;
	}

	public void setTotalNumberOfResults(String totalNumberOfResults) {
		this.totalNumberOfResults = totalNumberOfResults;
	}

	public JSONArray getBugDetailJSON() {
		return bugDetailJSON;
	}

	public void setBugDetailJSON(JSONArray bugDetailJSON) {
		this.bugDetailJSON = bugDetailJSON;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public JSONArray getErrorSearchResults() {
		return errorSearchResults;
	}

	public void setErrorSearchResults(JSONArray errorSearchResults) {
		this.errorSearchResults = errorSearchResults;
	}
}
