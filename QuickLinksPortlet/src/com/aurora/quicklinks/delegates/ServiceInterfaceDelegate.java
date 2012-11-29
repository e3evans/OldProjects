package com.aurora.quicklinks.delegates;

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

import com.aurora.quicklinks.util.BSSConstants;
import com.aurora.quicklinks.util.Base64Coder;

/**
 * This class contains web service calling function and return JSONObject
 * 
 * @author teprasad
 * 
 */
@Service
public class ServiceInterfaceDelegate {

	
	private String devProxyHostUrl = "http://localhost:10039/QuickLinksServiceApp/rest/test/results";

	
	@Autowired
	private RestTemplate restTemplate;

	

	private String stringProxyURL;

	private HttpHeaders entityHeaders;

	
	public String getDevProxyHostUrl() {
		return devProxyHostUrl;
	}

	public void setDevProxyHostUrl(String devProxyHostUrl) {
		this.devProxyHostUrl = devProxyHostUrl;
	}

	
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	


	/*
	 * This method is used to set HTTP Headers parameter like authentication and
	 * content-type
	 */
	private HttpHeaders createAuthenticationHeader() {
		//String authentication = Base64Coder.encodeString(getServiceUsername() + ":" + getServicePassword());
		HttpHeaders entityHeaders = new HttpHeaders();
		//entityHeaders.set("Authorization", "Basic " + authentication);
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

		System.out.println("!!!!!!!!!!!!!!processRequestCache Starts!!!!!1111");
		String requestUrl = stringProxyURL + requestPath;
		requestUrl = "http://localhost:10039/QuickLinksServiceApp/rest/test/userapplist";
		String responseText = BSSConstants.SERVICE_ERROR_MESSAGE;
		HttpEntity<String> requestEntity = new HttpEntity<String>(entityHeaders);
		try {
			URI requestURI = new URI(requestUrl);
			System.out.println("web service call start with request path : " + requestUrl);
			ResponseEntity<String> result = restTemplate.exchange(requestURI, HttpMethod.GET, requestEntity,
					String.class);
			System.out.println("web service call end with request path : " + requestUrl);
			responseText = result.getBody();
		} catch (Exception e) {
			//logger.error("ProcessRequestCache Exception", e);
		}
		if (cache == true) {
			if (requestResponseCache != null) {
				requestResponseCache.put(requestPath, responseText);
			}
		}
		
		System.out.println("!!!!!!!!!!!!!!processRequestCache Ends!!!!!1111");
		return responseText;
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
	public String processRequestCacheEdit(String requestPath, boolean cache,
			final Map<String, String> requestResponseCache, final Map<String, Header[]> requestHeadersCache)
			throws HttpException, IOException {

		System.out.println("!!!!!!!!!!!!!!processRequestCache Starts!!!!!1111");
		String requestUrl = stringProxyURL + requestPath;
		requestUrl = "http://localhost:10039/QuickLinksServiceApp/rest/test/results";
		String responseText = BSSConstants.SERVICE_ERROR_MESSAGE;
		HttpEntity<String> requestEntity = new HttpEntity<String>(entityHeaders);
		try {
			URI requestURI = new URI(requestUrl);
			System.out.println("web service call start with request path : " + requestUrl);
			ResponseEntity<String> result = restTemplate.exchange(requestURI, HttpMethod.GET, requestEntity,
					String.class);
			System.out.println("web service call end with request path : " + requestUrl);
			responseText = result.getBody();
		} catch (Exception e) {
			//logger.error("ProcessRequestCache Exception", e);
		}
		if (cache == true) {
			if (requestResponseCache != null) {
				requestResponseCache.put(requestPath, responseText);
			}
		}
		
		System.out.println("!!!!!!!!!!!!!!processRequestCache Ends!!!!!1111");
		return responseText;
	}
	
	

		/**
	 * This method get called once after dependency injection happen, It
	 * configure environment and Authentication Header
	 */
	@PostConstruct
	public void initService() {
		System.out.println("In Init Service !!!!!!!!!!");
		
		entityHeaders = createAuthenticationHeader();
		// requestEntity = new HttpEntity<String>(entityHeaders);
		
	}

}