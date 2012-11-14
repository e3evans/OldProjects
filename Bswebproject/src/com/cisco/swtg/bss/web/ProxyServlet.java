package com.cisco.swtg.bss.web;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cisco.swtg.bss.util.BSSConstants;
import com.cisco.swtg.bss.util.Base64Coder;

public class ProxyServlet extends HttpServlet {

	public ProxyServlet() throws IOException {
		super();
	}

	/**
	 * Serialization UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Key for redirect location header.
	 */
	private static final String STRING_LOCATION_HEADER = "Location";
	/**
	 * Key for content type header.
	 */
	private static final String STRING_CONTENT_TYPE_HEADER_NAME = "Content-Type";

	/**
	 * Key for content length header.
	 */
	private static final String STRING_CONTENT_LENGTH_HEADER_NAME = "Content-Length";
	/**
	 * Key for host header
	 */
	private static final String STRING_HOST_HEADER_NAME = "Host";

	// Proxy host params
	/**
	 * The host to which we are proxying requests
	 */
	private String stringProxyHost;

	private String serviceUsername;

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

	private String servicePassword;

	/**
	 * The port on the proxy host to which we are proxying requests. Default
	 * value is 80.
	 */
	private int intProxyPort = 80;
	/**
	 * The (optional) path on the proxy host to wihch we are proxying requests.
	 * Default value is "".
	 */
	private String stringProxyPath = "";

	/**
	 * Initialize the <code>ProxyServlet</code>
	 * 
	 * @param servletConfig
	 *            The Servlet configuration passed in by the servlet conatiner
	 */
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		Resource propertyFileResource = context.getResource("classpath:bssapp.properties");
		Properties properties;
		try {
			properties = PropertiesLoaderUtils.loadProperties(propertyFileResource);
			// properties.getProperty(key)
			// Get the proxy host
			String ciscoLife = "SERVICE_URL_BSS_local";
			if (System.getProperty("cisco.life") != null) {
				ciscoLife = "SERVICE_URL_BSS_" + System.getProperty("cisco.life");
			}
			String stringProxyHostNew = properties.getProperty(ciscoLife);
			if (stringProxyHostNew == null || stringProxyHostNew.length() == 0) {
				throw new IllegalArgumentException("Proxy host not set, please set init-param 'proxyHost' in web.xml");
			}
			this.setProxyHostUrl(stringProxyHostNew);

			// Get the proxy path if specified
			String stringServiceUsernameNew = properties.getProperty("wsg.username");
			if (stringServiceUsernameNew != null && stringServiceUsernameNew.length() > 0) {
				this.setServiceUsername(stringServiceUsernameNew);
			}

			String stringServicePasswordNew = properties.getProperty("wsg.password");
			if (stringServicePasswordNew != null && stringServicePasswordNew.length() > 0) {
				this.setServicePassword(stringServicePasswordNew);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Performs an HTTP GET request
	 * 
	 * @param httpServletRequest
	 *            The {@link HttpServletRequest} object passed in by the servlet
	 *            engine representing the client request to be proxied
	 * @param httpServletResponse
	 *            The {@link HttpServletResponse} object by which we can send a
	 *            proxied response to the client
	 */
	public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
			throws IOException, ServletException {
		// Create a GET request
		GetMethod getMethodProxyRequest = new GetMethod(this.getProxyURL(httpServletRequest));
		// Forward the request headers
		setProxyRequestHeaders(httpServletRequest, getMethodProxyRequest);
		// Execute the proxy request
		this.executeProxyRequest(getMethodProxyRequest, httpServletRequest, httpServletResponse);
	}

	/**
	 * Performs an HTTP POST request
	 * 
	 * @param httpServletRequest
	 *            The {@link HttpServletRequest} object passed in by the servlet
	 *            engine representing the client request to be proxied
	 * @param httpServletResponse
	 *            The {@link HttpServletResponse} object by which we can send a
	 *            proxied response to the client
	 */
	public void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
			throws IOException, ServletException {
		// Create a standard POST request
		PostMethod postMethodProxyRequest = new PostMethod(this.getProxyURL(httpServletRequest));
		// Forward the request headers
		setProxyRequestHeaders(httpServletRequest, postMethodProxyRequest);
		// Execute the proxy request
		this.executeProxyRequest(postMethodProxyRequest, httpServletRequest, httpServletResponse);
	}

	/**
	 * Executes the {@link HttpMethod} passed in and sends the proxy response
	 * back to the client via the given {@link HttpServletResponse}
	 * 
	 * @param httpMethodProxyRequest
	 *            An object representing the proxy request to be made
	 * @param httpServletResponse
	 *            An object by which we can send the proxied response back to
	 *            the client
	 * @throws IOException
	 *             Can be thrown by the {@link HttpClient}.executeMethod
	 * @throws ServletException
	 *             Can be thrown to indicate that another error has occurred
	 */
	private void executeProxyRequest(HttpMethod httpMethodProxyRequest, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws IOException, ServletException {
		try {
			// Create a default HttpClient
			HttpClient httpClient = new HttpClient();

			httpClient.getParams().setAuthenticationPreemptive(true);

			String authentication = Base64Coder.encodeString(getServiceUsername() + ":" + getServicePassword());
			httpMethodProxyRequest.setRequestHeader("Authorization", "Basic " + authentication);

			httpMethodProxyRequest.setFollowRedirects(false);
			// Execute the request
			int intProxyResponseCode = httpClient.executeMethod(httpMethodProxyRequest);

			// Check if the proxy response is a redirect
			// The following code is adapted from
			// org.tigris.noodle.filters.CheckForRedirect
			// Hooray for open source software
			if (intProxyResponseCode >= HttpServletResponse.SC_MULTIPLE_CHOICES /* 300 */
					&& intProxyResponseCode < HttpServletResponse.SC_NOT_MODIFIED /* 304 */) {
				String stringStatusCode = Integer.toString(intProxyResponseCode);
				String stringLocation = httpMethodProxyRequest.getResponseHeader(STRING_LOCATION_HEADER).getValue();
				if (stringLocation == null) {
					throw new ServletException("Recieved status code: " + stringStatusCode + " but no "
							+ STRING_LOCATION_HEADER + " header was found in the response");
				}
				// Modify the redirect to go to this proxy servlet rather that
				// the proxied host
				String stringMyHostName = httpServletRequest.getServerName();
				if (httpServletRequest.getServerPort() != 80) {
					stringMyHostName += ":" + httpServletRequest.getServerPort();
				}
				stringMyHostName += httpServletRequest.getContextPath();
				httpServletResponse.sendRedirect(stringLocation.replace(getProxyHostUrl(), stringMyHostName));
				return;
			} else if (intProxyResponseCode == HttpServletResponse.SC_NOT_MODIFIED) {
				// 304 needs special handling. See:
				// http://www.ics.uci.edu/pub/ietf/http/rfc1945.html#Code304
				// We get a 304 whenever passed an 'If-Modified-Since'
				// header and the data on disk has not changed; server
				// responds w/ a 304 saying I'm not going to send the
				// body because the file has not changed.
				httpServletResponse.setIntHeader(STRING_CONTENT_LENGTH_HEADER_NAME, 0);
				httpServletResponse.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				return;
			}

			// Pass the response code back to the client
			httpServletResponse.setStatus(intProxyResponseCode);

			// Pass response headers back to the client
			Header[] headerArrayResponse = httpMethodProxyRequest.getResponseHeaders();
			for (Header header : headerArrayResponse) {
				httpServletResponse.setHeader(header.getName(), header.getValue());
			}

			// Send the content to the client
			InputStream inputStreamProxyResponse = httpMethodProxyRequest.getResponseBodyAsStream();
			BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStreamProxyResponse);
			OutputStream outputStreamClientResponse = httpServletResponse.getOutputStream();
			int intNextByte;
			while ((intNextByte = bufferedInputStream.read()) != -1) {
				outputStreamClientResponse.write(intNextByte);
			}
			outputStreamClientResponse.flush();
			outputStreamClientResponse.close();
			bufferedInputStream.close();
		} catch (Exception e) {
			PrintWriter out = httpServletResponse.getWriter();
			out.println(BSSConstants.SERVICE_ERROR_MESSAGE);
			out.flush();
			out.close();
		}

	}

	public String getServletInfo() {
		return "Jason's Proxy Servlet";
	}

	/**
	 * Retreives all of the headers from the servlet request and sets them on
	 * the proxy request
	 * 
	 * @param httpServletRequest
	 *            The request object representing the client's request to the
	 *            servlet engine
	 * @param httpMethodProxyRequest
	 *            The request that we are about to send to the proxy host
	 */
	@SuppressWarnings("unchecked")
	private void setProxyRequestHeaders(HttpServletRequest httpServletRequest, HttpMethod httpMethodProxyRequest) {
		// Get an Enumeration of all of the header names sent by the client
		Enumeration enumerationOfHeaderNames = httpServletRequest.getHeaderNames();
		while (enumerationOfHeaderNames.hasMoreElements()) {
			String stringHeaderName = (String) enumerationOfHeaderNames.nextElement();
			if (stringHeaderName.equalsIgnoreCase(STRING_CONTENT_LENGTH_HEADER_NAME))
				continue;
			// As per the Java Servlet API 2.5 documentation:
			// Some headers, such as Accept-Language can be sent by clients
			// as several headers each with a different value rather than
			// sending the header as a comma separated list.
			// Thus, we get an Enumeration of the header values sent by the
			// client
			Enumeration enumerationOfHeaderValues = httpServletRequest.getHeaders(stringHeaderName);
			while (enumerationOfHeaderValues.hasMoreElements()) {
				String stringHeaderValue = (String) enumerationOfHeaderValues.nextElement();
				// In case the proxy host is running multiple virtual servers,
				// rewrite the Host header to ensure that we get content from
				// the correct virtual server
				if (stringHeaderName.equalsIgnoreCase(STRING_HOST_HEADER_NAME)) {
					stringHeaderValue = getProxyHostUrl();
				}
				Header header = new Header(stringHeaderName, stringHeaderValue);
				// Set the same header on the proxy request
				httpMethodProxyRequest.setRequestHeader(header);
			}
		}
	}

	private String getProxyURL(HttpServletRequest httpServletRequest) {
		// Set the protocol to HTTP
		String stringProxyURL = getProxyHostUrl();

		// Handle the path given to the servlet
		stringProxyURL += httpServletRequest.getPathInfo();

		stringProxyURL = stringProxyURL.replaceAll("/requestproxy", "");
		// Handle the query string
		if (httpServletRequest.getQueryString() != null) {
			stringProxyURL += "?" + httpServletRequest.getQueryString();
		}
		return stringProxyURL;
	}

	private String proxyHostUrl;

	public String getProxyHostUrl() {
		return proxyHostUrl;
	}

	public void setProxyHostUrl(String proxyHostUrl) {
		this.proxyHostUrl = proxyHostUrl;
	}
}