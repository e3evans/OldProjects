package com.cisco.swtg.scim.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("loadIntializeData")
public class LoadIntializeData implements Runnable {
	private static final Logger logger = Logger.getLogger(LoadIntializeData.class);

	@Autowired
	C3CrawlService c3CrawelService;
	
	public void run() {
		   logger.debug("'SW Image File Name' and 'PID' loding process started...");
		   c3CrawelService.populateSwImageAndPid();
		}

}

	
