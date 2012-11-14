package com.cisco.swtg.bss.web.service;

import java.io.IOException;
import java.util.Locale;

import org.apache.commons.httpclient.HttpException;
import org.json.JSONException;

import com.cisco.swtg.bss.web.domain.BugDetail;

/**
 * This class is used as interface for View BugDetail Service based on BugId
 * 
 * @author teprasad
 * 
 */
public interface ViewBugDetailsService {

	/**
	 * This method is used to get BugSummary details based on input bugId.
	 * 
	 * @param bugId
	 * @param userId
	 * @param userType
	 * @param locale
	 * @return BugSummary
	 * @throws HttpException
	 * @throws JSONException
	 * @throws IOException
	 */
	public BugDetail getBugDetailById(String bugId, String userId, int userType, Locale locale) throws HttpException,
			JSONException, IOException;

}
