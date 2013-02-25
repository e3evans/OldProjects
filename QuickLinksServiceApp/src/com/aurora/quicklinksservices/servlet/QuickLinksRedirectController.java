package com.aurora.quicklinksservices.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Servlet implementation class SSORedirectTest
 */
@Controller
@RequestMapping("/redirect")
public class QuickLinksRedirectController {

	protected static final Logger log = Logger
			.getLogger(QuickLinksRedirectController.class.getSimpleName());

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@RequestMapping(method = RequestMethod.GET)
	protected void redirectURL(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// get url from request
		String url = request.getParameter("url");
//		log.warn("Entered QuickLinksRedirectController redirectURL with url: "
//				+ url);

		// move cookie from request to response
		Cookie[] cookies = request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if ("auroraSSO".equals(cookie.getName())) {
//				log.warn("Found auroraSSO cookie");
				response.addCookie(cookie);
			}
		}

		// redirect to given app url
		response.sendRedirect(response.encodeURL(url));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}
}