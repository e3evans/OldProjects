package com.aurora.quicklinksservices.util;

import java.sql.Timestamp;
import java.util.Date;

public class QuickLinksUtility {

public static final Integer NOTDISPLAYED = new Integer(0);


public static Timestamp getCurrentTime()
{
  return new Timestamp(new Date().getTime());
}


public static String urlFormat(String url){
	/* DEV */
	//String baseUrl = "http://iconnect-test.aurora.org";
	/* PROD */
	
	
	String baseUrl = "http://iconnect.aurora.org";
	String firstsubStringurl = null ;
	if(url.indexOf("actionUrl")!=-1){
	 firstsubStringurl = url.substring(0, url.indexOf("actionUrl"));
	}
	if (url.indexOf(".org") == -1 && url.indexOf(".com") == -1
			&& url.indexOf("http") == -1
			&& url.indexOf("https") == -1) {
		url = baseUrl + url;
	} 
	else if (url.indexOf("https") == -1 && url.indexOf("http") == -1) {

		url = "http://" + url;
		url = url.replaceAll("////", "//");
	}
	else if (firstsubStringurl != null && firstsubStringurl.indexOf(".org") == -1) {
		url = baseUrl + url;
	}
	
   
 return url;
	
	
	
}


}

