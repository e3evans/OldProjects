package org.aurora.quicklinksservices.utils;

import java.sql.Timestamp;
import java.util.Date;

public class QuickLinksUtility {

	public static final Integer NOTDISPLAYED = new Integer(0);

	public static Timestamp getCurrentTime() {
		return new Timestamp(new Date().getTime());
	}

	public static String urlFormat(String baseUrl, String appUrl) {
		if (appUrl.startsWith("//")) {
			appUrl = "http:" + appUrl;
		}
		if (!appUrl.startsWith("http")) {
			appUrl = baseUrl + appUrl;
		}
		if (baseUrl.startsWith("https")) {
			appUrl.replaceAll("http", "https");
		}
		return appUrl;
	}
}