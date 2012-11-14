package com.cisco.swtg.scim.util;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class Constants {

	public final static String YES_OPTION = "y";
	public final static String NO_OPTION = "n";
	
	public final static int ICO_MAX_IN_SINGLE_TEXT = 6;
	
	public final static String SOURCE_C3 = "C3CASE";
	public final static String SOURCE_CSC = "CSC";
	public final static String ICO_TYPE_C3_CASE = "C3CASEID";
	public final static String ICO_TYPE_CDETS = "CDETS";
	public final static String ICO_TYPE_RFC = "RFC";
	public final static String ICO_TYPE_MIB = "MIB";
	public final static String ICO_TYPE_ERRMSG = "ERRMSG";
	public final static String ICO_TYPE_URL = "URL";
	public final static String ICO_TYPE_SW_IMAGE = "SW-IMAGE-NAMES";
	public final static String ICO_TYPE_PID = "PID";
	public final static String ICO_TYPE_SERIALNO = "SERIAL_NO";
	
	public final static String DENIED_URL_ERROR_CODE = "Denied";
	public final static String NO_TITLE_MSG = "No Title";	
	public final static String RESOLUTION_SUMMARY = "Resolution Summary";
	public final static String RESOLUTION_SUMMARY_CASE_NOTE_TYPE = "SCIMNote_for_StructuredData";//"CISCO_EMAIL_IN";
	
	public final static String CSC_MESSAGE_DATE_FORMATTER = "yyyy-MM-dd'T'HH:mm:sss.SSS";
	public final static SimpleDateFormat SCIM_DATE_FORMATTER = new SimpleDateFormat("dd-MM-yyyy");
	public final static SimpleDateFormat CSC_THREAD_CAM_DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");	
	public final static SimpleDateFormat formatter = new SimpleDateFormat(CSC_MESSAGE_DATE_FORMATTER);
	
	public final static String META_REFRESH_REGEX = "(<meta http-equiv=\"refresh.*>)|(<meta http-equiv=\'refresh.*>)";
	public final static String TITLE_REGEX = "\\<title>(.*)\\</title>";
	
	public final static String CSC_THREAD_URL = "https://supportforums.cisco.com/externalcommunitydataservice-v2-data.jspa?format=JSON&numThreads=10&q=&channel=test&questionStatus=All&startDate=&endDate=&resultStart="; //0";
	public final static String CSC_THREAD_CAM_URL_WITH_DATE = "https://supportforums.cisco.com/externalcommunitydataservice-v2-data.jspa?format=JSON&numThreads=10&q=&channel=test&questionStatus=All&startDate=<fromDate>&endDate=<toDate>&resultStart=";//0";
	public final static String CSC_THREAD_CAM_HOST = "supportforums.cisco.com";
	public final static String CSC_THREAD_CAM_REQUEST = "/externalcommunitydataservice-v2-data.jspa?format=JSON&numThreads=10&q=&channel=test&questionStatus=All&startDate=<fromDate>&endDate=<toDate>&resultStart=";//0";
	public final static String CSC_THREAD_CAM_DEFAULT_REQUEST = "/externalcommunitydataservice-v2-data.jspa?format=JSON&numThreads=10&q=&channel=test&questionStatus=All&startDate=&endDate=&resultStart="; //0";	
	public final static String PORTKEY_MESSAGES_BY_THREAD_URL = "https://tools.cisco.com/swtg/pkyin1/caas/sc/discussion/collaborationService/v1/getMessagesByThreadID/";
	public final static String CDETS_URL = "http://cdetsweb-prd.cisco.com/apps/dumpcr?identifier=";	
	public final static String RFC_URL = "http://www.faqs.org/rfcs/";
	public final static String RFC_HOST = "www.faqs.org";
	public final static String RFC_REQUEST = "/rfcs/";	
	public final static String CASEID_URL = "http://wwwin-tools.cisco.com/casekwery/getServiceRequest.do?id=";
	
	public final static String CISCO_USERID = "scim.gen";
	public final static String CISCO_PASSWORD = "5C1MProd";

	public final static String CISCO_STAGE_USERID = "scim.gen";
	public final static String CISCO_STAGE_PASSWORD = "5C1MDev";
	
	public final static String ENTITLEMENTS[] = {"partner", "customer", "guest"};	                                              
	public final static String URL_EXTENSIONS[] = {".shtml", ".html", ".htm", ".pdf"};
	
	/*// STAGE
	public final static String NEO4J_PUBLISH_BASE_URL = "http://scim-stage:7474/rest/data/import/";
	public final static String NEO4J_ICO_NODE_URL = "http://scim-stage:7474/db/data/index/node/ICO/node_id/";
	public final static String NEO4J_UPDATE_CASENOTE_ID_ICO_URL = "http://scim-stage:7474/rest/casenote/casenoteid/";*/
	// PROD
	/*public final static String NEO4J_PUBLISH_BASE_URL = "http://crowdwisdom:7474/rest/data/import/";
	public final static String NEO4J_ICO_NODE_URL = "http://scim:7474/db/data/index/node/ICO/node_id/";
	public final static String NEO4J_UPDATE_CASENOTE_ID_ICO_URL = "http://scim:7474/rest/casenote/casenoteid/";*/
	
	// DEV
	public final static String NEO4J_PUBLISH_BASE_URL = "http://scim-dev:7474/rest/data/import/";
	public final static String NEO4J_ICO_NODE_URL = "http://scim-dev:7474/db/data/index/node/ICO/node_id/";
	public final static String NEO4J_UPDATE_CASENOTE_ID_ICO_URL = "http://scim-dev:7474/rest/casenote/casenoteid/";
	
	public final static String PROXY_HOST_NAME = "proxy.esl.cisco.com";
	
	public static Set<String> SW_IMAGE_FILE_NAMES = new HashSet<String>();
	public static Map<String, String> PID_PRODUCT_NUMBERS = new HashMap<String, String>();
	public static Map<Integer, String> URLEXCEPTIONS = new HashMap<Integer, String>();
	
	public static Set<String> PID_PRODUCT_NUMBER_REGEXs = new HashSet<String>();
	public static Set<String> SW_IMAGE_FILE_NAME_REGEXs = new HashSet<String>();
	public static int REGEX_ELEMENTS_MAX_COUNT = 50000;
	
	
	
	public Constants() {
		URLEXCEPTIONS.put(4,"BindException");
		URLEXCEPTIONS.put(5,"ConnectException");
		URLEXCEPTIONS.put(6,"HttpRetryException");
		URLEXCEPTIONS.put(7,"MalformedURLException");
		URLEXCEPTIONS.put(8,"NoRouteToHostException");
		URLEXCEPTIONS.put(9,"PortUnreachableException");
		URLEXCEPTIONS.put(10,"ProtocolException");
		URLEXCEPTIONS.put(11,"SocketException");
		URLEXCEPTIONS.put(12,"SocketTimeoutException");
		URLEXCEPTIONS.put(13,"UnknownHostException");
		URLEXCEPTIONS.put(14,"UnknownServiceException");
		URLEXCEPTIONS.put(15,"IOException");
		
		URLEXCEPTIONS.put(200, "OK");
		URLEXCEPTIONS.put(201, "CREATED");
		URLEXCEPTIONS.put(202, "Accepted");
		URLEXCEPTIONS.put(203, "Partial Information");
		URLEXCEPTIONS.put(204, "No Response");
		URLEXCEPTIONS.put(400, "Bad request");
		URLEXCEPTIONS.put(401, "Unauthorized");
		URLEXCEPTIONS.put(402, "PaymentRequired");
		URLEXCEPTIONS.put(403, "Forbidden");
		URLEXCEPTIONS.put(404, "Not found");
		URLEXCEPTIONS.put(500, "Internal Error");
		URLEXCEPTIONS.put(501, "Not implemented");
		URLEXCEPTIONS.put(502, "Service temporarily overloaded");
		URLEXCEPTIONS.put(503, "Gateway timeout");
		URLEXCEPTIONS.put(301, "Moved");
		URLEXCEPTIONS.put(302, "Found");
		URLEXCEPTIONS.put(303, "Method");
		URLEXCEPTIONS.put(304, "Not Modified");
				
	}
	
}
