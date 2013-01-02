package com.aurora.quicklinks.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import com.aurora.exceptions.AppException;
import com.aurora.quicklinks.beans.UserApplication;

import com.aurora.quicklinks.services.AppService;

@Controller
@RequestMapping("VIEW")
public class UserAppController {

	private static Log log = LogFactory.getLog(UserAppController.class);

	
	@RenderMapping
	public String showUserApplication(RenderRequest request) {
		Principal user = request.getUserPrincipal();
		PortletSession session = request.getPortletSession();
		session.setAttribute("userId", user.toString());
		String errorMsg = request.getParameter("errorMsg");
		if (null != errorMsg) {
			request.setAttribute("errorMsg", errorMsg);
		}
		return "userapp";
	}

}
