package com.cisco.swtg.scim.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cisco.swtg.scim.model.c3blmodel.TssIncidentNotesFV;
import com.cisco.swtg.scim.service.C3blService;


@Controller
@RequestMapping("/rawdataadmin.htm")
public class RawDataAdminController {

	private static final Logger logger = Logger.getLogger(RawDataAdminController.class);
	
	@Autowired
	private C3blService c3blService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView onShowForm() {
		logger.debug("Inside rawdataadmin.htm GET method");
		return new ModelAndView("rawdataadmin");
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView onSubmit(HttpServletRequest request) {
		logger.debug("Inside rawdataadmin.htm POST method");
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		logger.debug("fromDate : " + fromDate);
		logger.debug("toDate : " + toDate);
		
		Date startTime = new Date();

		logger.info("C3 Rawdata fetching started at : " + startTime);
		System.out.println("C3 Rawdata fetching started at : " + startTime);
		//Fetch records from c3bl db and save into scim db
		//List<TssIncidentsCurrentFV> tssIncidentsCurrentFVs = c3blService.findC3blCrawledRawData(fromDate, toDate);
		List<TssIncidentNotesFV> tssIncidentNotesFVs = c3blService.findC3blCrawledRawDataByDate(fromDate, toDate);
		
		logger.debug("tssIncidentNotesFVs : " + tssIncidentNotesFVs);

		Date endTime = new Date();
		
		logger.info("C3 Rawdata fetching successfully completed : at "  + endTime + "fromDate : " + fromDate + ": toDate : " + toDate);
		System.out.println("C3 Rawdata fetching successfully completed : at "  + endTime+ "fromDate : " + fromDate + ": toDate : " + toDate);
		logger.info("C3 Started at : " + startTime+ "fromDate : " + fromDate + ": toDate : " + toDate);
		logger.info("C3 completed at : " + endTime+ "fromDate : " + fromDate + ": toDate : " + toDate);
		
		//c3CrawelService.parseC3Ico(fromDate, toDate);//TODO: Delete
		
		return new ModelAndView("rawdataadmin", "message", "Successfully fetched and saved into SCIM db");
	}
}
