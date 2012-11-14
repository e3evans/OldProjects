package com.cisco.swtg.bss.web.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.cisco.swtg.bss.delegates.ServiceInterfaceDelegate;
import com.cisco.swtg.bss.util.BSSConstants;
import com.cisco.swtg.bss.web.domain.BugDetail;

/**
 * This class is implementation of ViewBugDetailsService interface and methods
 * can be called using the interface
 * 
 * @author Infosys
 * 
 */
@Service(value = "viewService")
public class ViewBugDetailsServiceImpl implements ViewBugDetailsService {

	private static final String NAV = "nav";

	private static final String DUPLICATE_BUG_MESSAGE = "duplicateBugMessage";

	private static final int CUSTOMER = 2;

	private static final int PARTNER = 3;

	private static final int EMPLOYEE = 4;

	private static final int GUEST = 1;

	private static final String FOUND_IN_VERSIONS = "foundInVersions";

	private static final String VERSION_FIXED_IN_NAMES = "versionFixedInNames";

	private static final String SOFTWARE_COMPONENT_NAME = "softwareComponentName";

	private static final String SUB_TECHNOLOGY_NAME = "subTechnologyName";

	private static final String RELATED_BUG = "relatedBug";

	private static final String PRODUCT_NAME = "productName";

	private static final String ALL = "all";

	private static final String PRODUCT_SOFTWARE_FAMILY = "productSoftwareFamily";

	private static final String PRODUCTSERIES = "productseries";

	private static final String BUG_LAST_MODIFIED_DATE = "bugLastModifiedDate";

	private static final String SEPERATOR = " - ";

	private static final String SEVERITY_NAME = "severityName";

	private static final String SEVERITY_CODE = "severityCode";

	private static final String CLOSE_BRACES = ")";

	private static final String OPEN_BRACES = " (";

	private static final String STATUS_GROUP = "statusGroup";

	private static final String STATUS_NAME = "statusName";

	private static final String SR_NUMBERS_ASSOCIATED_TO = "srNumbersAssociatedTo";

	private static final String RELEASE_NOTE_TEXT = "releaseNoteText";

	private static final String HEAD_LINE = "headLine";

	private static final String BUG_ID = "bugId";

	private static final String BUG_DETAIL_RESPONSE = "bugDetailResponse";

	private static final String CI_BTK = "&ci=BTK";

	private static final String UN = "?un=";

	private static final String BSS_BUGDETAIL = "/bss/bugdetail/";

	private static final String REGRESSION_BUG_FLAG = "regressionBugFlag";

	private static final String S1S2_WITHOUT_WORKAROUND_FLAG = "s1s2WithoutWorkaroundFlag";

	private static final String PRODUCT_RUNS_IOS_FLAG = "productRunsIosFlag";

	private static final Log logger = LogFactory.getLog(ViewBugDetailsServiceImpl.class);

	@Autowired
	private ServiceInterfaceDelegate serviceInterfaceDelegate;

	@Autowired
	private MessageSource messgaeSource;

	public void setServiceInterfaceDelegate(ServiceInterfaceDelegate serviceInterfaceDelegate) {
		this.serviceInterfaceDelegate = serviceInterfaceDelegate;
	}

	public void setMessgaeSource(MessageSource messgaeSource) {
		this.messgaeSource = messgaeSource;
	}

	public BugDetail getBugDetailById(String bugId, String userId, int userType, Locale locale) throws HttpException,
			JSONException, IOException {
		StringBuilder quaryString = new StringBuilder(bugId).append(UN).append(userId).append(CI_BTK);
		String requestPath = BSS_BUGDETAIL + quaryString.toString();
		JSONObject bugDetailJson = new JSONObject(serviceInterfaceDelegate.processRequestCache(requestPath, false,
				null, null));
		BugDetail bugDetail = getBugDetailFromJSON(bugId, bugDetailJson, userType, locale);

		return bugDetail;
	}

	/*
	 * This method is used to set BugDetail data from JSON Object to BugDetail
	 * Object
	 */
	private BugDetail getBugDetailFromJSON(String bugId, JSONObject bugDetailJson, int userType, Locale locale)
			throws JSONException {
		BugDetail bugDetail = new BugDetail();
		if (!bugDetailJson.isNull("errorResponse")) {
			JSONObject errorResponse = (JSONObject) bugDetailJson.get("errorResponse");
			bugDetail.setErrorCode(errorResponse.get("publicErrorCode").toString());
			bugDetail.setErrorMessage(errorResponse.optString("publicErrorMessage"));
		}
		if (!bugDetailJson.isNull(BUG_DETAIL_RESPONSE) && bugDetailJson.getJSONArray(BUG_DETAIL_RESPONSE).length() > 0) {
			JSONObject json = bugDetailJson.getJSONArray(BUG_DETAIL_RESPONSE).getJSONObject(0);
			/*
			 * Duplicate Bug Scenario check for Employee and Partner or Customer
			 * and set Duplicate Bug message to bugDetail Object
			 */
			String duplicateBugMessage = BSSConstants.EMPTY_STRING;
			String duplicateBugMessageEx = BSSConstants.EMPTY_STRING;
			if (userType == EMPLOYEE && !json.optString(DUPLICATE_BUG_MESSAGE).equals(BSSConstants.EMPTY_STRING)) {
				duplicateBugMessage = json.optString(DUPLICATE_BUG_MESSAGE);
			} else if (bugDetailJson.getJSONArray(BUG_DETAIL_RESPONSE).length() > 1) {
				if (!json.optString(BUG_ID).equals(bugId)) {
					json = bugDetailJson.getJSONArray(BUG_DETAIL_RESPONSE).getJSONObject(1);
				}
				String duplicateOfBugId = json.optString("duplicateOfBugId");
				duplicateBugMessage = json.optString(DUPLICATE_BUG_MESSAGE);
				duplicateBugMessageEx = messgaeSource.getMessage("bugdetail.message.duplicatebugabsence",
						new Object[] { duplicateOfBugId }, locale);
				for (int i = 0; i < bugDetailJson.getJSONArray(BUG_DETAIL_RESPONSE).length(); i++) {
					if (bugDetailJson.getJSONArray(BUG_DETAIL_RESPONSE).getJSONObject(i).optString(BUG_ID).equals(
							duplicateOfBugId)
							&& bugDetailJson.getJSONArray(BUG_DETAIL_RESPONSE).getJSONObject(i).optString(
									"bugDetailAbsenceReason").equals(BSSConstants.EMPTY_STRING)) {
						json = bugDetailJson.getJSONArray(BUG_DETAIL_RESPONSE).getJSONObject(i);
						duplicateBugMessageEx = messgaeSource
								.getMessage("bugdetail.message.duplicatebug", null, locale);
					}
				}
			}
			bugDetail.setDuplicateBugMessage(duplicateBugMessage + duplicateBugMessageEx);

			logger.info("convesion of JSON Object to BugDetail Object start");
			bugDetail.setBugId(json.getString(BUG_ID));
			bugDetail.setHeadLine(json.optString(HEAD_LINE));
			bugDetail.setVisible(json.optBoolean("visible", true));
			JSONArray vaNamesArray = json.optJSONArray("versionAffectedNames");
			if (vaNamesArray != null) {
				String vaNames = BSSConstants.EMPTY_STRING;
				for (int i = 0; i < vaNamesArray.length(); i++) {
					vaNames = vaNames + vaNamesArray.getString(i);
				}
				bugDetail.setVersionAffectedNames(vaNames.trim());
			}

			String releaseNoteText = json.optString(RELEASE_NOTE_TEXT).replaceAll("&lt;", "<").replaceAll("&gt;", ">")
					.replaceAll("(?s)<!--.*?-->", BSSConstants.EMPTY_STRING).trim().replaceAll("(\r\n)+", "<br/><br/>")
					.replaceAll("(\n)+", "<br/>").replaceAll("\r", BSSConstants.EMPTY_STRING);
			bugDetail.setReleaseNoteText(releaseNoteText);

			bugDetail.setSrNumbersAssociatedTo(json.optString(SR_NUMBERS_ASSOCIATED_TO));
			String statusName = json.optString(STATUS_NAME);
			String statusGroup = json.optString(STATUS_GROUP);
			if (!statusGroup.equals(BSSConstants.EMPTY_STRING) && !statusName.equals(BSSConstants.EMPTY_STRING)) {
				statusGroup += OPEN_BRACES + statusName + CLOSE_BRACES;
			}
			bugDetail.setStatusName(statusGroup);
			String severity = json.optString(SEVERITY_CODE);
			String severityName = json.optString(SEVERITY_NAME);
			if (!severityName.equals(BSSConstants.EMPTY_STRING)) {
				severity += SEPERATOR + severityName;
			}
			bugDetail.setSeverityCode(severity);
			bugDetail.setBugLastModifiedDate(getValidDateFormat(json.optString(BUG_LAST_MODIFIED_DATE)));

			String product = json.optString(PRODUCTSERIES);
			if (product.equals(BSSConstants.EMPTY_STRING)) {
				product = json.optString(PRODUCT_SOFTWARE_FAMILY);
			}
			bugDetail.setProductSoftwareFamily(product);
			if (json.optString(PRODUCT_NAME).equals(ALL) || json.optString(PRODUCT_NAME).equals("unknown")) {
				bugDetail.setPlatformName("Independent");
			} else {
				bugDetail.setPlatformName("Dependent");
			}
			bugDetail.setFoundIn(json.optString(FOUND_IN_VERSIONS));
			bugDetail.setFixedInVersions(json.optString(VERSION_FIXED_IN_NAMES));
			bugDetail.setSoftwareComponentName(json.optString(SOFTWARE_COMPONENT_NAME));
			bugDetail
					.setTechnology(json.optString(SUB_TECHNOLOGY_NAME).equalsIgnoreCase(NAV) ? BSSConstants.EMPTY_STRING
							: json.optString(SUB_TECHNOLOGY_NAME));
			if (!json.isNull(RELATED_BUG)) {
				JSONArray relatedBugs = json.getJSONArray(RELATED_BUG);
				List<BugDetail> relatedBugList = new ArrayList<BugDetail>();
				for (int i = 0; i < relatedBugs.length(); i++) {
					JSONObject relBugJSON = relatedBugs.getJSONObject(i);
					BugDetail relBugDetail = new BugDetail();
					relBugDetail.setBugId(relBugJSON.optString(BUG_ID, BSSConstants.EMPTY_STRING));
					relBugDetail.setHeadLine(relBugJSON.optString(HEAD_LINE));
					String relNoteText = relBugJSON.optString(RELEASE_NOTE_TEXT).replaceAll("&lt;", "<").replaceAll(
							"&gt;", ">").replaceAll("(?s)<!--.*?-->", BSSConstants.EMPTY_STRING).trim().replaceAll(
							"(\r\n)+", "<br/><br/>").replaceAll("(\n)+", "<br/>").replaceAll("\r",
							BSSConstants.EMPTY_STRING);
					relBugDetail.setReleaseNoteText(relNoteText);

					relatedBugList.add(relBugDetail);
				}
				bugDetail.setRelatedBugList(relatedBugList);
			}
			// bugDetail.setInterpretingBugInfo(getInterpretInfo(json,
			// userType));

			logger.info("convesion of JSON Object to BugDetail Object end");
		}
		return bugDetail;
	}

	/*
	 * This method is used to convert date format from JSON data
	 */
	private String getValidDateFormat(String inputDate) {
		SimpleDateFormat inFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		SimpleDateFormat outformatter = new SimpleDateFormat("MMM dd,yyyy");
		String outDate = BSSConstants.EMPTY_STRING;
		Date out = null;
		if (inputDate != null) {
			try {
				out = inFormatter.parse(inputDate);
			} catch (ParseException e) {
				logger.info("Unable to parse date");
			}
			if (out != null) {
				outDate = outformatter.format(out);
			}
		}
		return outDate;
	}

	/*
	 * This method is used to construct list of interpreting info with the help
	 * of bug detail data and conditions
	 */
	private List<String> getInterpretInfo(JSONObject json, int userType) {

		List<String> interpretingInfo = new ArrayList<String>();
		String statusName = json.optString(STATUS_NAME);
		String statusGroup = json.optString(STATUS_GROUP);
		String severity = json.optString(SEVERITY_CODE);
		String fixedInVersions = json.optString(VERSION_FIXED_IN_NAMES);
		String regressionBugFlag = json.optString(REGRESSION_BUG_FLAG);
		String s1s2WithoutWorkaroundFlag = json.optString(S1S2_WITHOUT_WORKAROUND_FLAG);
		String productRunsIosFlag = json.optString(PRODUCT_RUNS_IOS_FLAG);
		String bugLastModifiedDate = json.optString(BUG_LAST_MODIFIED_DATE);
		int noOfDays = getDateDiff(bugLastModifiedDate);
		if (statusGroup.equals("Open") && noOfDays > 365
				&& (userType == CUSTOMER || userType == PARTNER || userType == EMPLOYEE)) {
			interpretingInfo.add("1");
		} else if (statusGroup.equals("Open") && noOfDays < 365 && (userType == CUSTOMER || userType == GUEST)) {
			interpretingInfo.add("2");
		} else if (statusGroup.equals("Open") && statusName.equals("InformationRequired") && noOfDays < 365
				&& (userType == CUSTOMER || userType == GUEST)) {
			interpretingInfo.add("3");
		} else if (statusGroup.equals("Fixed") && !fixedInVersions.equals(BSSConstants.EMPTY_STRING)
				&& (userType == CUSTOMER || userType == GUEST)) {
			interpretingInfo.add("4");
		} else if (statusGroup.equals("Fixed") && fixedInVersions.equals(BSSConstants.EMPTY_STRING)
				&& (userType == CUSTOMER || userType == GUEST)) {
			interpretingInfo.add("5");
		} else if (statusGroup.equals("Terminated") && (userType == CUSTOMER || userType == GUEST)) {
			interpretingInfo.add("6");
		} else if (statusName.equals("New") && (userType == EMPLOYEE || userType == PARTNER)) {
			interpretingInfo.add("7");
		} else if (statusName.equals("Assigned") && (userType == EMPLOYEE || userType == PARTNER)) {
			interpretingInfo.add("8");
		} else if (statusName.equals("Held") && (userType == EMPLOYEE || userType == PARTNER)) {
			interpretingInfo.add("9");
		} else if (statusName.equals("Open") && (userType == EMPLOYEE || userType == PARTNER)) {
			interpretingInfo.add("10");
		} else if (statusName.equals("More") && (userType == EMPLOYEE || userType == PARTNER)) {
			interpretingInfo.add("11");
		} else if (statusName.equals("Information Required") && (userType == EMPLOYEE || userType == PARTNER)) {
			interpretingInfo.add("12");
		} else if (statusName.equals("Waiting") && (userType == EMPLOYEE || userType == PARTNER)) {
			interpretingInfo.add("13");
		} else if (statusName.equals("Forwarded") && (userType == EMPLOYEE || userType == PARTNER)) {
			interpretingInfo.add("14");
		} else if (statusName.equals("Submitted") && (userType == EMPLOYEE || userType == PARTNER)) {
			interpretingInfo.add("15");
		} else if (statusName.equals("Postponed") && (userType == EMPLOYEE || userType == PARTNER)) {
			interpretingInfo.add("16");
		} else if (statusName.equals("Resolved") && !fixedInVersions.equals(BSSConstants.EMPTY_STRING)
				&& (userType == EMPLOYEE || userType == PARTNER)) {
			interpretingInfo.add("17");
		} else if (statusName.equals("Resolved") && fixedInVersions.equals(BSSConstants.EMPTY_STRING)
				&& (userType == EMPLOYEE || userType == PARTNER)) {
			interpretingInfo.add("18");
		} else if (statusName.equals("Verified") && !fixedInVersions.equals(BSSConstants.EMPTY_STRING)
				&& (userType == EMPLOYEE || userType == PARTNER)) {
			interpretingInfo.add("19");
		} else if (statusName.equals("Verified") && fixedInVersions.equals(BSSConstants.EMPTY_STRING)
				&& (userType == EMPLOYEE || userType == PARTNER)) {
			interpretingInfo.add("20");
		} else if (statusName.equals("Unreproducible") && (userType == EMPLOYEE || userType == PARTNER)) {
			interpretingInfo.add("21");
		} else if (statusName.equals("Closed") && (userType == EMPLOYEE || userType == PARTNER)) {
			interpretingInfo.add("22");
		} else if (statusName.equals("Junked") && (userType == EMPLOYEE || userType == PARTNER)) {
			interpretingInfo.add("23");
		}
		if (regressionBugFlag.equals("Y") && (userType == EMPLOYEE || userType == PARTNER)) {
			interpretingInfo.add("24");
		}
		if (severity.equals("1")
				&& (userType == EMPLOYEE || userType == PARTNER || userType == GUEST || userType == CUSTOMER)) {
			interpretingInfo.add("25");
		}
		if (severity.equals("2")
				&& (userType == EMPLOYEE || userType == PARTNER || userType == GUEST || userType == CUSTOMER)) {
			interpretingInfo.add("26");
		}
		if (severity.equals("3")
				&& (userType == EMPLOYEE || userType == PARTNER || userType == GUEST || userType == CUSTOMER)) {
			interpretingInfo.add("27");
		}
		if (severity.equals("4")
				&& (userType == EMPLOYEE || userType == PARTNER || userType == GUEST || userType == CUSTOMER)) {
			interpretingInfo.add("28");
		}
		if (severity.equals("5")
				&& (userType == EMPLOYEE || userType == PARTNER || userType == GUEST || userType == CUSTOMER)) {
			interpretingInfo.add("29");
		}
		if (severity.equals("6")
				&& (userType == EMPLOYEE || userType == PARTNER || userType == GUEST || userType == CUSTOMER)) {
			interpretingInfo.add("30");
		}

		if (s1s2WithoutWorkaroundFlag.equals("N") && (userType == CUSTOMER || userType == PARTNER)) {
			interpretingInfo.add("31");
		}
		if (s1s2WithoutWorkaroundFlag.equals("N") && (userType == EMPLOYEE)) {
			interpretingInfo.add("32");
		}
		if (productRunsIosFlag.equals("Y")
				&& (userType == EMPLOYEE || userType == PARTNER || userType == GUEST || userType == CUSTOMER)) {
			interpretingInfo.add("33");
		}
		if (userType == EMPLOYEE || userType == PARTNER || userType == GUEST || userType == CUSTOMER) {
			interpretingInfo.add("34");
		}
		return interpretingInfo;
	}

	/*
	 * This method is used to calculate number of days based on input date and
	 * current date.
	 */
	private int getDateDiff(String bugLastModifiedDate) {
		SimpleDateFormat inFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy MM dd");
		int noOfDays = 0;
		Calendar today = Calendar.getInstance();
		Date todaysDate = today.getTime();
		Date newDate = null;
		if (bugLastModifiedDate != null) {
			try {
				Date inputDate = inFormatter.parse(bugLastModifiedDate);
				String newDateString = newDateFormat.format(inputDate);
				newDate = newDateFormat.parse(newDateString);
			} catch (ParseException e) {
				logger.info("Unable to parse date");
			}
		}
		long difference = todaysDate.getTime() - newDate.getTime();
		noOfDays = (int) (difference / (1000 * 60 * 60 * 24));
		return noOfDays;
	}
}