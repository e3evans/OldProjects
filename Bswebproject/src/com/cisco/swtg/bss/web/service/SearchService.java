package com.cisco.swtg.bss.web.service;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.json.JSONException;

import com.cisco.swtg.bss.web.domain.BugSearchResults;

/**
 * This class is used as interface to provide the search Service for Bugs
 * 
 * @author teprasad
 * 
 */
public interface SearchService {

	/**
	 * This method is used to get List of BugDetail based on input requestPath.
	 * 
	 * @param requestPath
	 * @return BugSearchResults
	 * @throws HttpException
	 * @throws JSONException
	 * @throws IOException
	 */
	public BugSearchResults searchResult(String requestPath) throws HttpException, JSONException, IOException;

}
