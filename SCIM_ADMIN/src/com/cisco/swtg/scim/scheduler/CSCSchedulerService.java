package com.cisco.swtg.scim.scheduler;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cisco.swtg.scim.service.CSCCrawlService;
import com.cisco.swtg.scim.util.Constants;

/**
 * Scheduler for handling jobs
 */
@Service
public class CSCSchedulerService {

	protected static Logger logger = Logger.getLogger("CSCSchedulerService");

	@Autowired
	private CSCCrawlService cSCCrawelService;
	
	//@Scheduled(fixedDelay=5000)
	//@Scheduled(fixedRate=500)
	//@Scheduled(cron="*/5 * * * * ?")
	@Scheduled(cron="0 1 17 * * ?")
	public void doSchedule() {
		logger.info("CSC Start schedule");
		
		int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;

		/*Date toDate = new Date();
		Date fromDate = new Date(toDate.getTime() - MILLIS_IN_DAY);*/
		Date toDate = new Date();
		toDate = new Date(toDate.getTime() - MILLIS_IN_DAY);
		Date fromDate = new Date(toDate.getTime() - MILLIS_IN_DAY);
		
		String fromDateStr = Constants.SCIM_DATE_FORMATTER.format(fromDate);
		String toDateStr = Constants.SCIM_DATE_FORMATTER.format(toDate);
		
		logger.info("CSC Scheduler :  fromDateStr : " + fromDateStr);
		logger.info("CSC Scheduler :  toDateStr : " + toDateStr);
		
		
		
		// CSC Rawdata
		Date startRawdataTime = new Date();
		logger.info("CSC Scheduler : Rawdata fetching started at : " +  startRawdataTime);		
		// Rawdata fetching
		cSCCrawelService.findCSCRawdatasFromSrc(fromDateStr, toDateStr);	
		Date endRawdataTime = new Date();
		logger.info("CSC Scheduler :  Rawdata Started at : " + startRawdataTime+ " : fromDate : " + fromDate + " : toDate : " + toDate);
		logger.info("CSC Scheduler :  Rawdata completed at : " + endRawdataTime+ " : fromDate : " + fromDate + " : toDate : " + toDate);
		
		
		// Extraction
		Date startC3ExtractionStratTime = new Date();
		logger.info("CSC Scheduler : Extraction started at : " +  startC3ExtractionStratTime);		
		// Parsing ICOs
		cSCCrawelService.parseCSCIco(fromDateStr, toDateStr);
		Date startC3ExtractionEndTime = new Date();		
		logger.info("CSC Scheduler :  Extraction Started at : " + startC3ExtractionStratTime+ " : fromDate : " + fromDate + " : toDate : " + toDate);
		logger.info("CSC Scheduler :  Extraction completed at : " + startC3ExtractionEndTime+ " : fromDate : " + fromDate + " : toDate : " + toDate);
		
		
		// NEO4J Publishing
		String urlBase = Constants.NEO4J_PUBLISH_BASE_URL;//"http://scim-stage:7474/rest/data/import/";
		SimpleDateFormat SCIM_NEO4J_DATE_FORMATTER = new SimpleDateFormat("yyyy/MM/dd");
		String fromDateNeo4j = SCIM_NEO4J_DATE_FORMATTER.format(fromDate);
		String toDateNeo4j = SCIM_NEO4J_DATE_FORMATTER.format(toDate);
		PostToUrl postTo = new PostToUrl();
		String url = urlBase + fromDateNeo4j;
		logger.info("Posting First url  : " + url);
		postTo.newDataPOST(url);
		url = urlBase + toDateNeo4j;
		logger.info("Posting Second url : " + url);
		postTo.newDataPOST(url);
		logger.info("End schedule");
	}
	

}
