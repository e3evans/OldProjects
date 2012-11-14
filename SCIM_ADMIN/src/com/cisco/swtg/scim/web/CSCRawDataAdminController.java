package com.cisco.swtg.scim.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cisco.swtg.scim.service.CSCCrawlService;


@Controller
@RequestMapping("/cscrawdataadmin.htm")
public class CSCRawDataAdminController {

	private static final Logger logger = Logger.getLogger(CSCRawDataAdminController.class);

	@Autowired
	private CSCCrawlService cSCCrawelService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView onShowForm() throws Exception {
		logger.debug("Inside cscrawdataadmin.htm GET method");					
		return new ModelAndView("cscrawdataadmin", "message", "Please give valid Thread Creation From and To Date");
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView onSubmit(HttpServletRequest request) {
		logger.debug("Inside cscrawdataadmin.htm POST method");

		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		logger.debug("fromDate for Fetching CSC Rawdata: " + fromDate);
		logger.debug("toDate for Fetching CSC Rawdata: " + toDate);
		
		Date startTime = new Date();
		
		try {			
			cSCCrawelService.findCSCRawdatasFromSrc(fromDate, toDate);
		} catch (Exception e) {
			logger.info("*IMP: cscrawdataadmin: Exception : " + e);
		}

		Date endTime = new Date();
		
		logger.info("CSC Started at : " + startTime+ "fromDate : " + fromDate + ": toDate : " + toDate);
		logger.info("CSC completed at : " + endTime+ "fromDate : " + fromDate + ": toDate : " + toDate);
		//cSCCrawelService.parseCSCIco(fromDate, toDate); //TODO : delete

		logger.info("CSC Parsing success");
		return new ModelAndView("cscrawdataadmin", "message", "Successfully fetched CSC rawdata and saved into SCIM db");
	}
}
