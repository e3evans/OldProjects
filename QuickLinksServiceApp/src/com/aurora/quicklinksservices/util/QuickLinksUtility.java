package com.aurora.quicklinksservices.util;

import java.sql.Timestamp;
import java.util.Date;

import com.ibm.wcp.runtime.feedback.lm.util.Logger;

public class QuickLinksUtility {

	protected static final Logger log = Logger
			.getLogger(QuickLinksUtility.class.getSimpleName());

	public static final Integer NOTDISPLAYED = new Integer(0);

	public static Timestamp getCurrentTime() {
		return new Timestamp(new Date().getTime());
	}

	public static String urlFormat(String baseUrl, String appUrl) {
		String firstsubStringurl = null;
		if (appUrl.indexOf("actionUrl") != -1) {
			firstsubStringurl = appUrl
					.substring(0, appUrl.indexOf("actionUrl"));
		}
		if (appUrl.indexOf(".org") == -1 && appUrl.indexOf(".com") == -1
				&& appUrl.indexOf("http") == -1
				&& appUrl.indexOf("https") == -1) {
			appUrl = baseUrl + appUrl;
		} else if (appUrl.indexOf("https") == -1
				&& appUrl.indexOf("http") == -1) {
			appUrl = "http://" + appUrl;
			appUrl = appUrl.replaceAll("////", "//");
		} else if (firstsubStringurl != null
				&& firstsubStringurl.indexOf(".org") == -1) {
			appUrl = baseUrl + appUrl;
		}
		return appUrl;
	}
}