package com.cisco.swtg.scim.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cisco.swtg.scim.service.CSCCrawlService;


@Controller
@RequestMapping("/csccrawladmin.htm")
public class CSCCrawlAdminController {

	private static final Logger logger = Logger.getLogger(RawDataAdminController.class);

	@Autowired
	private CSCCrawlService cSCCrawelService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView onShowForm() {
		logger.debug("Inside crawleradmin.htm GET method");
		return new ModelAndView("csccrawladmin");
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView onSubmit(HttpServletRequest request) {
		logger.debug("Inside crawleradmin.htm POST method");
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		logger.debug("fromDate : " + fromDate);
		logger.debug("toDate : " + toDate);
		
		cSCCrawelService.parseCSCIco(fromDate, toDate);
		
		return new ModelAndView("csccrawladmin", "message", "Successfully CSC crawled");
	}
}
