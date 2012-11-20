package com.cisco.swtg.bss.web.controller.requestproxy;

import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.cisco.swtg.bss.delegates.ServiceInterfaceDelegate;
import com.cisco.swtg.bss.util.BSSConstants;

/*
 This Handler class takes care of invoking the POST method of the File Broker
 service.

 @author Infosys

 */
public class ProxyPutServiceController extends AbstractController {

	private ServiceInterfaceDelegate serviceInterfaceDelegate;

	public ServiceInterfaceDelegate getServiceInterfaceDelegate() {
		return serviceInterfaceDelegate;
	}

	public void setServiceInterfaceDelegate(ServiceInterfaceDelegate serviceInterfaceDelegate) {
		this.serviceInterfaceDelegate = serviceInterfaceDelegate;
	}

	public ProxyPutServiceController(ServiceInterfaceDelegate delegate) {
		super();
		setServiceInterfaceDelegate(delegate);
	}

	@SuppressWarnings("unchecked")
	public ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String userId = BSSConstants.EMPTY_STRING;
		if (request.getSession(false) != null) {
			Map<String, Object> sessionBean = (Map<String, Object>) request.getSession(false).getAttribute(
					BSSConstants.USER_SESSION_BEAN);
			userId = sessionBean.get(BSSConstants.USER).toString();
		}

		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String requestPath = request.getPathInfo().replaceAll("/requestproxy/put", BSSConstants.EMPTY_STRING);

		Map<String, Object> requestParameterValue = new HashMap<String, Object>();
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			String[] paramValues = request.getParameterValues(paramName);
			if (paramValues.length == 1) {
				requestParameterValue.put(paramName, paramValues[0]);
			} else {

				requestParameterValue.put(paramName, paramValues);
			}
		}
		String responseText = BSSConstants.SERVICE_ERROR_MESSAGE;
		try {
			if (serviceInterfaceDelegate != null) {
				JSONObject jsonObject = createJsonObject(requestParameterValue, userId);
				JSONObject responseObject = serviceInterfaceDelegate.executePut(jsonObject, requestPath);
				responseText = responseObject.toString();
			}
		} catch (Exception e) {
			logger.info("In catch:" + e.getMessage());
		}
		out.print(responseText);
		out.flush();
		out.close();

		return null;
	}

	/*
	 * This function is used to create Json object based on user input for
	 * service call B=SaveBug, S=SaveSearch, R=Rating Submit
	 */
	public JSONObject createJsonObject(Map<String, Object> jsonData, String userId) {
		JSONObject json = new JSONObject();
		JSONObject searchJSON = new JSONObject();
		try {
			json.put("clientId", BSSConstants.BTK);
			if (jsonData.get("notifeq") != null && !jsonData.get("notifeq").equals(BSSConstants.EMPTY_STRING)) {
				json.put("notifeq", jsonData.get("notifeq"));
			}
			if (jsonData.get("email") != null && !jsonData.get("email").equals(BSSConstants.EMPTY_STRING)) {
				json.put("email", jsonData.get("email"));
			}
			if (jsonData.get("requestType") != null && jsonData.get("requestType").equals("B")) {
				json.put("userId", userId); // May have to be changed to userName... Services might have made a mistake with a wrong tag
				json.put("requestType", "B");
				json.put("groupName", jsonData.get("groupName"));
				JSONArray jsonArray = new JSONArray();
				jsonArray.put(jsonData.get("bugId"));
				searchJSON.put("bugId", jsonArray);
				json.put("saveBug", searchJSON);

				// API Change for save search
				JSONObject bwrJson = new JSONObject();
				bwrJson.put("bugWatchAction", "S");
				bwrJson.put("saveBugWatchRequest", json);
				return bwrJson;
			} else if (jsonData.get("requestType") != null && jsonData.get("requestType").equals("S")) {
				json.put("userId", userId); // May have to be changed to userName... Services might have made a mistake with a wrong tag
				json.put("requestType", "S");
				json.put("groupName", jsonData.get("groupName"));
				searchJSON.put("searchName", jsonData.get("searchName"));
				searchJSON.put("searchType", jsonData.get("searchType"));
				if (jsonData.get("searchType").equals("kw") || jsonData.get("searchType").equals("bugId")) {
					searchJSON.put("keywordSearch", jsonData.get("keywordSearch"));
				} else if (jsonData.get("searchType").equals("adv")) {
					if (jsonData.get("mdfCategoryId") != null
							&& !jsonData.get("mdfCategoryId").equals(BSSConstants.EMPTY_STRING)
							&& !jsonData.get("mdfCategoryId").equals("allProducts")) {
						searchJSON.put("mdfCategoryId", jsonData.get("mdfCategoryId"));
						searchJSON.put("mdfProductConceptId", jsonData.get("mdfProductConceptId"));
					} else {
						String category_productId = (String) jsonData.get("mdfProductConceptId");
						searchJSON.put("mdfCategoryId", category_productId.split("/")[0]);
						searchJSON.put("mdfProductConceptId", category_productId.split("/")[1]);
					}
					searchJSON.put("mdfProductName", jsonData.get("mdfProductName"));
					// searchJSON.put("mdfCategoryName",
					// jsonData.get("mdfCategoryName"));
					if (jsonData.get("ngrpVersionName") != null
							&& !jsonData.get("ngrpVersionName").equals(BSSConstants.EMPTY_STRING)) {
						searchJSON.put("ngrpVersionName", jsonData.get("ngrpVersionName"));
					}
					if (jsonData.get("versionType") != null
							&& !jsonData.get("versionType").equals(BSSConstants.EMPTY_STRING)) {
						searchJSON.put("versionType", jsonData.get("versionType"));
					}
				}
				if (jsonData.get("kw") != null && !jsonData.get("kw").equals(BSSConstants.EMPTY_STRING)) {
					searchJSON.put("filterKeyword", jsonData.get("kw"));
				}
				if (jsonData.get("sg") != null && !jsonData.get("sg").equals(BSSConstants.EMPTY_STRING)) {
					searchJSON.put("resolutionStatusCodes", jsonData.get("sg"));
				}
				if (jsonData.get("as") != null && !jsonData.get("as").equals(BSSConstants.EMPTY_STRING)) {
					searchJSON.put("lifecycleStateCodes", jsonData.get("as"));
				}
				if (jsonData.get("se") != null && !jsonData.get("se").equals(BSSConstants.EMPTY_STRING)) {
					searchJSON.put("severityCodes", jsonData.get("se"));
				}
				if (jsonData.get("tn") != null && !jsonData.get("tn").equals(BSSConstants.EMPTY_STRING)) {
					searchJSON.put("technologyNames", jsonData.get("tn"));
				}
				if (jsonData.get("acsl") != null && !jsonData.get("acsl").equals(BSSConstants.EMPTY_STRING)) {
					searchJSON.put("accessLevel", jsonData.get("acsl"));
				}
				if (jsonData.get("blmd") != null && !jsonData.get("blmd").equals(BSSConstants.EMPTY_STRING)) {
					searchJSON.put("lastModifiedPeriodId", jsonData.get("blmd"));
				}
				json.put("saveSearch", searchJSON);
				
				// API Change for save search
				JSONObject bwrJson = new JSONObject();
				bwrJson.put("bugWatchAction", "S");
				bwrJson.put("saveBugWatchRequest", json);
				return bwrJson;

			} else if (jsonData.get("requestType") != null && jsonData.get("requestType").equals("R")) {
				json.put("userName", userId);
				json.put("comment", jsonData.get("comment"));
				json.put("rating", jsonData.get("rating"));
				json.put("bugId", jsonData.get("bugId"));
			}
		} catch (JSONException e) {
			logger.info("In catch:" + e.getMessage());
		}
		return json;
	}
}