package com.cisco.swtg.scim.service;

import com.cisco.swtg.scim.model.cscmodel.CSCJsonCamData;
import com.cisco.swtg.scim.model.cscmodel.CSCMessagesByThreadId;
import com.cisco.swtg.scim.model.cscmodel.CSCThreadData;

public interface CSCCrawlService {

	public String readUrl(String urlStr, String userId, String password);

	public String cleanJsonContent(String content);

	public CSCJsonCamData convertJsonToCSCJsonCamData(String content);

	public CSCMessagesByThreadId convertXmlToCSCXmlMsgsByThreadData(
			CSCThreadData cSCThreadData) throws Exception;

	public void findCSCRawdatasFromSrc(String fromDate, String toDate);

	public void parseCSCIco(String fromDate, String toDate);

	public String readUrl(String hostStr, String reqStr);
	
	public String readNeo4jUrlAsJson(String urlStr);
	

}
