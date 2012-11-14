package com.cisco.swtg.scim.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cisco.swtg.scim.service.C3CrawlService;


@Controller
@RequestMapping("/crawladmin.htm")
public class CrawlAdminController {

	private static final Logger logger = Logger.getLogger(RawDataAdminController.class);

	@Autowired
	private C3CrawlService c3CrawelService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView onShowForm() {
		logger.debug("Inside crawleradmin.htm GET method");
		return new ModelAndView("crawladmin");
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView onSubmit(HttpServletRequest request) {
		logger.debug("Inside crawleradmin.htm POST method");
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		logger.debug("fromDate : " + fromDate);
		logger.debug("toDate : " + toDate);
		
		Date startTime = new Date();

		logger.info("C3 Extraction fetching started at : " + startTime);
		System.out.println("C3 Extraction fetching started at : " + startTime);
		
		c3CrawelService.parseC3Ico(fromDate, toDate);
		
		Date endTime = new Date();
		
		logger.info("C3 Extraction fetching successfully completed : at "  + endTime + "fromDate : " + fromDate + ": toDate : " + toDate);
		System.out.println("C3 extration fetching successfully completed : at "  + endTime+ "fromDate : " + fromDate + ": toDate : " + toDate);
		logger.info("Extraction Started at : " + startTime+ "fromDate : " + fromDate + ": toDate : " + toDate);
		logger.info("Extraction completed at : " + endTime+ "fromDate : " + fromDate + ": toDate : " + toDate);
		
		return new ModelAndView("crawladmin", "message", "Successfully crawled");
	}
}
