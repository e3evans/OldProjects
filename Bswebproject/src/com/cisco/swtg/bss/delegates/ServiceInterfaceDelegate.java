package com.cisco.swtg.bss.delegates;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cisco.swtg.bss.util.BSSConstants;
import com.cisco.swtg.bss.util.Base64Coder;

/**
 * This class contains web service calling function and return JSONObject
 * 
 * @author teprasad
 * 
 */
@Service
public class ServiceInterfaceDelegate {

	private static final String BSS_MDF_HIERARCHY_URL = "/bss/mdf/hierarchy?un=teprasad&ci=BTK&allProducts=true";

	private static final Log logger = LogFactory.getLog(ServiceInterfaceDelegate.class);

	static {
		try {
			productAndCategory = categoryAndProduct();
		} catch (HttpException e) {
			logger.info("HttpException in static block for populating product and category");
		} catch (IOException e) {
			logger.info("IOException in static block for populating product and category");
		}
	}
	@Value("#{bssProperties['SERVICE_URL_BSS_dev']}")
	private String devProxyHostUrl;

	@Value("#{bssProperties['SERVICE_URL_BSS_stage']}")
	private String stageProxyHostUrl;

	@Value("#{bssProperties['SERVICE_URL_BSS_lt']}")
	private String ltProxyHostUrl;

	@Value("#{bssProperties['SERVICE_URL_BSS_prod']}")
	private String prodProxyHostUrl;

	@Value("#{bssProperties['SERVICE_URL_BSS_local']}")
	private String localProxyHostUrl;

	@Autowired
	private RestTemplate restTemplate;

	@Value("#{bssProperties['wsg.username']}")
	private String serviceUsername;

	@Value("#{bssProperties['wsg.password']}")
	private String servicePassword;

	private String stringProxyURL;

	private HttpHeaders entityHeaders;

	private static String productAndCategory;

	public static String getProductAndCategory() {
		if (productAndCategory == null || productAndCategory.equals(BSSConstants.SERVICE_ERROR_MESSAGE)) {
			try {
				productAndCategory = categoryAndProduct();
			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return productAndCategory;
	}

	public String getDevProxyHostUrl() {
		return devProxyHostUrl;
	}

	public void setDevProxyHostUrl(String devProxyHostUrl) {
		this.devProxyHostUrl = devProxyHostUrl;
	}

	public String getStageProxyHostUrl() {
		return stageProxyHostUrl;
	}

	public void setStageProxyHostUrl(String stageProxyHostUrl) {
		this.stageProxyHostUrl = stageProxyHostUrl;
	}

	public String getLtProxyHostUrl() {
		return ltProxyHostUrl;
	}

	public void setLtProxyHostUrl(String ltProxyHostUrl) {
		this.ltProxyHostUrl = ltProxyHostUrl;
	}

	public String getProdProxyHostUrl() {
		return prodProxyHostUrl;
	}

	public void setProdProxyHostUrl(String prodProxyHostUrl) {
		this.prodProxyHostUrl = prodProxyHostUrl;
	}

	public String getLocalProxyHostUrl() {
		return localProxyHostUrl;
	}

	public void setLocalProxyHostUrl(String localProxyHostUrl) {
		this.localProxyHostUrl = localProxyHostUrl;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public String getServiceUsername() {
		return serviceUsername;
	}

	public void setServiceUsername(String serviceUsername) {
		this.serviceUsername = serviceUsername;
	}

	public String getServicePassword() {
		return servicePassword;
	}

	public void setServicePassword(String servicePassword) {
		this.servicePassword = servicePassword;
	}

	/*
	 * This method is used to set HTTP Headers parameter like authentication and
	 * content-type
	 */
	private HttpHeaders createAuthenticationHeader() {
		String authentication = Base64Coder.encodeString(getServiceUsername() + ":" + getServicePassword());
		HttpHeaders entityHeaders = new HttpHeaders();
		entityHeaders.set("Authorization", "Basic " + authentication);
		entityHeaders.set("content-type", "application/json");
		return entityHeaders;
	}

	/**
	 * This method is used to get JSONObject response for given request path.
	 * 
	 * @param requestPath
	 * @param cache
	 * @param requestResponseCache
	 * @param requestHeadersCache
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String processRequestCache(String requestPath, boolean cache,
			final Map<String, String> requestResponseCache, final Map<String, Header[]> requestHeadersCache)
			throws HttpException, IOException {

		String requestUrl = stringProxyURL + requestPath;
		String responseText = BSSConstants.SERVICE_ERROR_MESSAGE;
		HttpEntity<String> requestEntity = new HttpEntity<String>(entityHeaders);
		try {
			URI requestURI = new URI(requestUrl);
			logger.info("web service call start with request path : " + requestUrl);
			ResponseEntity<String> result = restTemplate.exchange(requestURI, HttpMethod.GET, requestEntity,
					String.class);
			logger.info("web service call end with request path : " + requestUrl);
			responseText = result.getBody();
		} catch (Exception e) {
			logger.error("ProcessRequestCache Exception", e);
		}
		if (cache == true) {
			if (requestResponseCache != null) {
				requestResponseCache.put(requestPath, responseText);
			}
		}
		return responseText;
	}

	/**
	 * This method is used to get JSONObject response for Hierarchy service path
	 * request on class initialization.
	 * 
	 * @throws HttpException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static String categoryAndProduct() throws HttpException, IOException {
		ApplicationProperties properties = ApplicationProperties.getInstance();
		HashMap propertyMap = properties.getPropertyMap();
		String stringProxyURL = null;
		String ciscoLife = System.getProperty("cisco.life");
		logger.info("Cisco Life Environment : " + ciscoLife);
		if (ciscoLife != null) {
			if (ciscoLife.equals("dev")) {
				stringProxyURL = (String) propertyMap.get("SERVICE_URL_BSS_dev");
			} else if (ciscoLife.equals("stage")) {
				stringProxyURL = (String) propertyMap.get("SERVICE_URL_BSS_stage");
			} else if (ciscoLife.equals("lt")) {
				stringProxyURL = (String) propertyMap.get("SERVICE_URL_BSS_lt");
			} else if (ciscoLife.equals("prod")) {
				stringProxyURL = (String) propertyMap.get("SERVICE_URL_BSS_prod");
			}
		} else {
			stringProxyURL = (String) propertyMap.get("SERVICE_URL_BSS_local");
		}
		String authentication = Base64Coder.encodeString((String) propertyMap.get("wsg.username") + ":"
				+ (String) propertyMap.get("wsg.password"));

		HttpHeaders entityHeaders = new HttpHeaders();
		entityHeaders.set("Authorization", "Basic " + authentication);
		entityHeaders.set("content-type", "application/json");

		HttpEntity<String> requestEntity = new HttpEntity<String>(entityHeaders);
		logger.info("Cisco Life environment and authenticationHeader configured");

		String requestUrl = stringProxyURL + BSS_MDF_HIERARCHY_URL;
		String responseText = BSSConstants.SERVICE_ERROR_MESSAGE;
		try {
			URI requestURI = new URI(requestUrl);
			logger.info("web service call start with request path : " + requestUrl);
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> result = restTemplate.exchange(requestURI, HttpMethod.GET, requestEntity,
					String.class);
			logger.info("web service call end with request path : " + requestUrl);
			responseText = result.getBody();
		} catch (Exception e) {
			logger.error("ProcessRequestCache Exception", e);
		}
		return responseText;
	}

	/**
	 * This method get called once after dependency injection happen, It
	 * configure environment and Authentication Header
	 */
	@PostConstruct
	public void initService() {
		String ciscoLife = System.getProperty("cisco.life");
		logger.info("Cisco Life Environment : " + ciscoLife);
		if (ciscoLife != null) {
			if (ciscoLife.equals("dev")) {
				stringProxyURL = getDevProxyHostUrl();
			} else if (ciscoLife.equals("stage")) {
				stringProxyURL = getStageProxyHostUrl();
			} else if (ciscoLife.equals("lt")) {
				stringProxyURL = getLtProxyHostUrl();
			} else if (ciscoLife.equals("prod")) {
				stringProxyURL = getProdProxyHostUrl();
			}
		} else {
			stringProxyURL = getLocalProxyHostUrl();
		}
		entityHeaders = createAuthenticationHeader();
		// requestEntity = new HttpEntity<String>(entityHeaders);
		logger.info("Cisco Life environment and authenticationHeader configured");
	}

	/**
	 * This method execute the POST web service for given path
	 * 
	 * @param requestPath
	 * @param cache
	 * @param requestResponseCache
	 * @param requestHeadersCache
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public String executePost(String requestPath, boolean cache, final Map<String, String> requestResponseCache,
			final Map<String, Header[]> requestHeadersCache) throws HttpException, IOException {
		String requestUrl = stringProxyURL + requestPath;
		String responseTextPost = BSSConstants.SERVICE_ERROR_MESSAGE;
		HttpEntity<String> requestEntity = new HttpEntity<String>(entityHeaders);
		try {
			logger.info("web service call start with request path : " + requestUrl);
			ResponseEntity<String> result = restTemplate.exchange(requestUrl, HttpMethod.POST, requestEntity,
					String.class);
			logger.info("web service call end with request path : " + requestUrl);
			responseTextPost = result.getBody();
		} catch (Exception e) {
			logger.error("ProcessRequestCache Exception", e);
		}
		if (cache == true) {
			if (requestResponseCache != null) {
				requestResponseCache.put(requestPath, responseTextPost);
			}
		}
		return responseTextPost;
	}

	/**
	 * This method execute the PUT web service for given path and take json data
	 * as body to call rest service.
	 * 
	 * @param srRequest
	 * @param requestPath
	 * @return responseObject
	 * @throws JSONException
	 */

	public JSONObject executePut(JSONObject jsonObject, String requestPath) throws JSONException {
		String requestUrl = stringProxyURL + requestPath;
		HttpEntity<String> requestEntity = new HttpEntity<String>(entityHeaders);
		requestEntity = new HttpEntity<String>(jsonObject.toString(), entityHeaders);
		logger.info("web service call start with request path : " + requestUrl);
		ResponseEntity<String> result = restTemplate.exchange(requestUrl, HttpMethod.PUT, requestEntity, String.class);
		logger.info("web service call end with request path : " + requestUrl);
		String response = result.getBody();
		JSONObject responseObject = new JSONObject(response);
		return responseObject;
	}
}
