package com.aurora.quicklinks.delegates;

import java.io.IOException;
import java.net.URI;

import javax.annotation.PostConstruct;

import org.apache.commons.httpclient.HttpException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.aurora.exceptions.AppException;
import com.aurora.quicklinks.util.QuickLinksConstants;

/**
 * This class contains web service calling function and return JSONObject
 * 
 * 
 * 
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
	public String processGetRestRequest(String requestPath) throws AppException {

		String requestUrl = requestPath;
		String responseText = QuickLinksConstants.SERVICE_ERROR_MESSAGE;
		HttpEntity<String> requestEntity = new HttpEntity<String>(entityHeaders);
		try {
			URI requestURI = new URI(requestUrl);
			System.out.println("web service call start with request path : "
					+ requestUrl);
			ResponseEntity<String> result = restTemplate.exchange(requestURI,
					HttpMethod.GET, requestEntity, String.class);
			System.out.println("web service call end with request path : "
					+ requestUrl);
			responseText = result.getBody();
		} catch (Exception e) {
			AppException ae = new AppException();
			ae.setExceptionType("Service Call");
			ae.setExceptionCode("QLEXception 001");
			ae.setExceptionMessage("Excetion in Rest Service Call !!!!"
					+ requestUrl);
			logger.error("Exception in processGetRestRequest !!!  " + e);
			throw ae;
			// logger.error("ProcessRequestCache Exception", e);
		}
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