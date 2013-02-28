package org.aurora.sitesapps.delegates;

import java.io.IOException;
import java.net.URI;

import javax.annotation.PostConstruct;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.aurora.sitesapps.utils.SitesAppsConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * This class contains web service calling function and return JSONObject
 */
@Service
public class ServiceInterfaceDelegate {

	private Logger logger = Logger.getLogger(ServiceInterfaceDelegate.class);

	@Autowired
	private RestTemplate restTemplate;

	private HttpHeaders entityHeaders;

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	/*
	 * This method is used to set HTTP Headers parameter like authentication and
	 * content-type
	 */
	private HttpHeaders createAuthenticationHeader() {
		HttpHeaders entityHeaders = new HttpHeaders();
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
	public String processGetRestRequest(String requestPath) throws Exception {
		String urlPath = System.getProperty("org.aurora.cookie.url")
				+ requestPath;
		String responseText = SitesAppsConstants.SERVICE_ERROR_MESSAGE;
		HttpEntity<String> requestEntity = new HttpEntity<String>(entityHeaders);
		URI requestURI = new URI(urlPath);
		logger.info("web service call start with request path : " + urlPath);
		ResponseEntity<String> result = restTemplate.exchange(requestURI,
				HttpMethod.GET, requestEntity, String.class);
		logger.info("web service call end with request path : " + urlPath);
		responseText = result.getBody();
		return responseText;
	}

	/**
	 * This method get called once after dependency injection happen, It
	 * configure environment and Authentication Header
	 */
	@PostConstruct
	public void initService() {
		logger.warn("*** In Init Service ***");
		entityHeaders = createAuthenticationHeader();
	}
}