package com.cisco.swtg.scim.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.CommonsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cisco.swtg.scim.dao.c3bldao.C3blDAO;
import com.cisco.swtg.scim.dao.scimdao.CrawlDAO;
import com.cisco.swtg.scim.model.cscmodel.CSCJsonCamData;
import com.cisco.swtg.scim.model.cscmodel.CSCMessageData;
import com.cisco.swtg.scim.model.cscmodel.CSCMessagesByThreadId;
import com.cisco.swtg.scim.model.cscmodel.CSCThreadData;
import com.cisco.swtg.scim.model.cscmodel.Message;
import com.cisco.swtg.scim.model.scimmodel.CSCExtractedIcoData;
import com.cisco.swtg.scim.model.scimmodel.CSCRawData;
import com.cisco.swtg.scim.model.scimmodel.IcoRegex;
import com.cisco.swtg.scim.model.scimmodel.IcoUrlServerName;
import com.cisco.swtg.scim.model.scimmodel.UrlMDFData;
import com.cisco.swtg.scim.util.Constants;
import com.cisco.swtg.scim.util.MakeRestCalls;


@Service
public class CSCCrawlServiceImpl implements CSCCrawlService {

	private static final Logger logger = Logger.getLogger(CSCCrawlServiceImpl.class);
	
	@Autowired
	CrawlDAO crawlDAO;
	
	@Autowired
	C3blDAO c3blDAO;
	
	@Autowired
	C3CrawlService c3CrawelService;
	
	@Autowired
	RestTemplate restTemplate; 
	
	@Autowired
    Credentials credentials; 

	
	@Override
	public String readUrl(String hostStr, String reqStr) {
		logger.debug("Inside readUrl method...with hostStr " + hostStr);
		logger.debug("reqStr " + reqStr);
		String content = "";
		
        //HttpHost proxy = new HttpHost("proxy.esl.cisco.com", 8080, "http"); //TODO : move to property file
		HttpHost proxy = new HttpHost(Constants.PROXY_HOST_NAME, 8080, "http");
		
        DefaultHttpClient httpclient = new DefaultHttpClient();
         
        try {
            httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);

            HttpHost target = new HttpHost(hostStr);
            HttpGet req = new HttpGet(reqStr);

            logger.debug("executing request to " + target + " via " + proxy);
            org.apache.http.HttpResponse rsp = httpclient.execute(target, req);
            HttpEntity entity = rsp.getEntity();

            logger.debug("Status : " + rsp.getStatusLine());

            if (entity != null) {
                content = new String(EntityUtils.toString(entity).getBytes(), "UTF-8");
                logger.debug("Content read success...");
            } else {
            	logger.debug("Readed empty content...");
            }
        } catch (Exception e) {
        	logger.info("Exception readUrl : " + hostStr + " : " + reqStr);
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        return content;
    }
	
	@Override
	public String readNeo4jUrlAsJson(String urlStr) {
		logger.debug("Inside readUrl method ...with : " + urlStr);
		
		String content = "";
		if (StringUtils.isBlank(urlStr)) {		
			logger.debug("Can't read blank url : " + urlStr);
			return content;
		}

		
		try {
			URL url = new URL(urlStr);
			HttpURLConnection uc = (HttpURLConnection)url.openConnection();
			//URLConnection uc = url.openConnection();

			uc.addRequestProperty("accept", "application/json");
			uc.setRequestMethod("GET");
			
			BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream(), "UTF-8")); 
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				content += inputLine.trim();
			}
			in.close();
			logger.debug("Successfully content read from the url : " + urlStr);

		} catch (Exception e) {
			logger.info("Failed content read from the url : " + urlStr + " : " + e);
			return null;
		}

		return content;
	}
	
	@Override
	public String readUrl(String urlStr, String userId, String password) {
		logger.debug("Inside readUrl method ...with : " + urlStr);
		
		String content = "";
		if ((StringUtils.isBlank(urlStr))
			/*	|| (StringUtils.endsWithIgnoreCase(urlStr, ".pdf"))
				|| (StringUtils.endsWithIgnoreCase(urlStr, ".jpg"))
				|| (StringUtils.endsWithIgnoreCase(urlStr, ".jpeg"))
				|| (StringUtils.endsWithIgnoreCase(urlStr, ".png"))
				|| (StringUtils.endsWithIgnoreCase(urlStr, ".jar"))
				|| (StringUtils.endsWithIgnoreCase(urlStr, ".war"))
				|| (StringUtils.endsWithIgnoreCase(urlStr, ".rar"))
				|| (StringUtils.endsWithIgnoreCase(urlStr, ".pkg"))
				|| (StringUtils.endsWithIgnoreCase(urlStr, ".gz"))
				|| (StringUtils.endsWithIgnoreCase(urlStr, ".tar"))
				|| (StringUtils.endsWithIgnoreCase(urlStr, ".js"))
				|| (StringUtils.endsWithIgnoreCase(urlStr, ".css"))
				|| (StringUtils.endsWithIgnoreCase(urlStr, ".zip"))*/) {
			logger.debug("Cant read blank url : " + urlStr);
			return content;
		}
		
		if ((StringUtils.endsWithIgnoreCase(urlStr, ")"))) return content;//TODO:delete
		
		try {
			URL url = new URL(urlStr);
			URLConnection uc = url.openConnection();
			//uc.setConnectTimeout(1800000);//30 Mins
			
			if ((!StringUtils.isBlank(userId)) && (!StringUtils.isBlank(password)))
			  uc.setRequestProperty( "Authorization", "Basic " + new sun.misc.BASE64Encoder().encode((userId + ":" + password).getBytes()));

			String contentType = uc.getContentType(); // We want to read only html file
			if (!StringUtils.equalsIgnoreCase(contentType, "text/html")) {
				logger.debug("Content type is not text/html so can't read : " + urlStr);
				return content;
			}
			
			BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream(), "UTF-8")); 
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				content += inputLine.trim();
			}
			in.close();
			logger.debug("Successfully content read from the url : " + urlStr);

		} catch (Exception e) {
			logger.info("Failed content read from the url : " + urlStr + " : " + e);
			return null;
		}

		return content;
	}
	
	@Override
	public String cleanJsonContent(String content) {
		logger.debug("Inside  cleanJsonContent method with content...");
		if (StringUtils.isBlank(content)) return content;
				
		content = StringUtils.removeStart(content, "(");
		content = StringUtils.removeEnd(content, ")");
		return content;
	}
	
	
	@Override
	public CSCJsonCamData convertJsonToCSCJsonCamData(String content) {
		logger.debug("Inside  convertJsonToCSCRawData method with : " + content);
		
		try {
			content = new String(content.getBytes(),"UTF-8");
		} catch (Exception e) {
			logger.debug(e);
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		CSCJsonCamData cSCJsonCamData = null;
		try {
			cSCJsonCamData = mapper.readValue(content.getBytes(), CSCJsonCamData.class);
		} catch (Exception e) {
			logger.info("Exception occurred when converting Json string into CSCJsonCamData Object : " + e);
			e.printStackTrace();
		}
		
		return cSCJsonCamData;		
	}
	
		
	@Override
	public CSCMessagesByThreadId convertXmlToCSCXmlMsgsByThreadData(CSCThreadData cSCThreadData)  throws Exception {
		logger.info("Inside  convertXmlToCSCXmlMsgsByThreadData method with threadId: " + cSCThreadData.getId());

		String xmlContent = null;
		CSCMessagesByThreadId cSCMessagesByThreadId =  null;
		String[][] _headers = new String[][]{
				{"Authorization", "Basic "+new sun.misc.BASE64Encoder().encode((Constants.CISCO_USERID+":"+Constants.CISCO_PASSWORD).getBytes())}
		};
		String requestUrl = Constants.PORTKEY_MESSAGES_BY_THREAD_URL+cSCThreadData.getId();
 	    xmlContent = MakeRestCalls.makeGetCall(requestUrl,_headers, null);

		JAXBContext jaxbContext = JAXBContext.newInstance(CSCMessagesByThreadId.class); Unmarshaller unmarshaller = jaxbContext.createUnmarshaller(); 
		cSCMessagesByThreadId = (CSCMessagesByThreadId) unmarshaller.unmarshal(new StringReader(xmlContent));
		
		CommonsClientHttpRequestFactory factory = (CommonsClientHttpRequestFactory) restTemplate.getRequestFactory();
		HttpClient client = factory.getHttpClient();
		client.getState().setCredentials(AuthScope.ANY, credentials);
				
		if (ObjectUtils.equals(cSCMessagesByThreadId, null)) return null;
		List<Message> returns = cSCMessagesByThreadId.getReturns();
		if ((ObjectUtils.equals(returns, null)) || (returns.size() < 1)) return null;
		
		for (Message cscMsgData : returns) {
			CSCRawData cscRawData = new CSCRawData();
			
			cscRawData.setThreadId(cSCThreadData.getId());
			cscRawData.setThreadName(cSCThreadData.getName());
			cscRawData.setThreadMessageCount(cSCThreadData.getMessageCount());
			cscRawData.setThreadRootMessageId(cSCThreadData.getRootMessageId());
			cscRawData.setThreadCommunityId(cSCThreadData.getCurrentCommunityId());
			cscRawData.setThreadParentCommunityId(cSCThreadData.getParentCommunityId());
			cscRawData.setThreadRating(cSCThreadData.getRating());
			cscRawData.setThreadAuthor(cSCThreadData.getAuthor());
			cscRawData.setThreadAuthorProfile(cSCThreadData.getAuthorProfile());
			cscRawData.setThreadLatestMsgDate(cSCThreadData.getLatestMessageDate());
			cscRawData.setThreadCreationDate(cSCThreadData.getCreationDate());
			cscRawData.setThreadUrl(cSCThreadData.getUrl());
			
			cscRawData.setMessageId(cscMsgData.getId());
			cscRawData.setMessageBody(cscMsgData.getBody());
			cscRawData.setMessageParentId(cscMsgData.getParentMessageID());
			try {
				cscRawData.setMessageCreationDate(Constants.formatter.parse(cscMsgData.getCreationDate()));
			} catch (Exception e) {
				logger.debug("Exception : " + e);
				cscRawData.setMessageCreationDate(parseDateFromMsgCreationDateStr(cscMsgData.getCreationDate()));
			}
			cscRawData.setMessageSubject(cscMsgData.getSubject());			
			
			if (!ObjectUtils.equals(cscMsgData.getUser(), null)) {
				cscRawData.setMessageAuthor(cscMsgData.getUser().getUsername());
			}
			crawlDAO.save(cscRawData);
			logger.info(cscRawData.getMessageId() + " Saved successfully");
		}

		return cSCMessagesByThreadId;
	}
	
	
	@Override
	public void findCSCRawdatasFromSrc(String fromdate, String toDate) {
		logger.debug("Inside  findCSCRawdatasFromSrc method");
		String cscCAMRequestStr = Constants.CSC_THREAD_CAM_REQUEST;
		
		try {
			Date dateFrom = Constants.SCIM_DATE_FORMATTER.parse(fromdate);
			Date dateTo = Constants.SCIM_DATE_FORMATTER.parse(toDate);
			cscCAMRequestStr = StringUtils.replace(cscCAMRequestStr, "<fromDate>", Constants.CSC_THREAD_CAM_DATE_FORMATTER.format(dateFrom));
			cscCAMRequestStr = StringUtils.replace(cscCAMRequestStr, "<toDate>", Constants.CSC_THREAD_CAM_DATE_FORMATTER.format(dateTo));
			logger.debug("CSC_THREAD CAM URL : " + cscCAMRequestStr);
		} catch (Exception e) {
			logger.info("Exception : When converting date inside findCSCRawdatasFromSrc method. So csc rawdata url set with default date");
			cscCAMRequestStr = Constants.CSC_THREAD_CAM_DEFAULT_REQUEST;
			logger.info("Took Default CSC_THREAD CAM URL Request : " + cscCAMRequestStr);
		}
		
		logger.debug("CSC_THREAD CAM URL : " + cscCAMRequestStr);
		
		int i = 0;
		while(i > -1) { //loop will execute up to get empty thread
			String jsonContent = readUrl(Constants.CSC_THREAD_CAM_HOST, cscCAMRequestStr + (i++));
			String cleanedJsonContent = cleanJsonContent(jsonContent);
			logger.debug("Json file read success : " + cscCAMRequestStr + (i++));
			CSCJsonCamData cSCJsonCamData = convertJsonToCSCJsonCamData(cleanedJsonContent);
			logger.debug("Json to java object convertion success");
		
		if ((ObjectUtils.equals(cSCJsonCamData, null)) || (ObjectUtils.equals(cSCJsonCamData.getThreads(), null))) {
			logger.debug("There is no thread for this request : " + Constants.CSC_THREAD_URL);
			return;
		}

		if ((ObjectUtils.equals(cSCJsonCamData.getThreads(), null)) || (cSCJsonCamData.getThreads().size() < 1)) {
				System.out.println("Break at : resultStart : " + i);
				break;
		}
		
		for (CSCThreadData cSCThreadData : cSCJsonCamData.getThreads()) {
			if (ObjectUtils.equals(cSCThreadData, null)) {
				logger.debug("CAM THREAD is NULL");
				continue;
			}
			List<CSCMessageData> messages = cSCJsonCamData.getMessages();
			if ((ObjectUtils.equals(messages, null)) || (messages.size() < 0)) {
				logger.debug("CAM messages is NULL");
				continue;
			}
			try {
				convertXmlToCSCXmlMsgsByThreadData(cSCThreadData);
			} catch (Exception e) {
				logger.info("convertXmlToCSCXmlMsgsByThreadData : " + e);
			}
		}
			
		}
	}
	
	
	@Override
	public void parseCSCIco(String fromDate, String toDate) {
		logger.debug("Inside parseCSCIco method ...with fromDate : " + fromDate + " toDate : " + toDate);

		Date dateFrom = new Date();
		Date dateTo = new Date();
		
		try {
			dateFrom = Constants.SCIM_DATE_FORMATTER.parse(fromDate);
			dateTo = Constants.SCIM_DATE_FORMATTER.parse(toDate);
		} catch (Exception e) {
			logger.debug("Exception when converting String date to Date : " + e);
		}

		List<IcoRegex>  icoRegexs = crawlDAO.findAllRegex();
		List<CSCRawData> cscRawDatas = crawlDAO.findCSCRawDatas(dateFrom, dateTo);
		
		if ((ObjectUtils.equals(cscRawDatas, null)) || (cscRawDatas.size() < 1)) return;

		logger.debug("Total cscrawdata size : " + cscRawDatas.size());
		
		for (CSCRawData cscRawData : cscRawDatas) {
			logger.debug("Ico parscing is in process for cscRawData : " + cscRawData.getMessageId());
			CSCExtractedIcoData cscExtractedIcoData = new CSCExtractedIcoData();
			
			// Step 1: for regex patterns
			if (!ObjectUtils.equals(icoRegexs, null))
			for (IcoRegex icoRegex : icoRegexs) {
				logger.debug("icoRegex : " + icoRegex);
				if ((ObjectUtils.equals(cscRawData, null)) || (ObjectUtils.equals(icoRegex, null))) continue;

				boolean isCaseSensitiveRegex = true;
				if (StringUtils.equals(icoRegex.getCaseSensitive(), "n")) isCaseSensitiveRegex = false;
				
				Map<String, Integer> cscCaseIdIcosForRegex = c3CrawelService.findRegexMatchedResult(cscRawData.getMessageBody(), icoRegex.getIcoRegex(), isCaseSensitiveRegex);
				
				for (Map.Entry<String, Integer> cscCaseIdIcoEntry : cscCaseIdIcosForRegex.entrySet()) {					
					cscExtractedIcoData.setIcoValue(cscCaseIdIcoEntry.getKey());
					
					// url
					if (StringUtils.equals(icoRegex.getIcoType(), Constants.ICO_TYPE_URL)) {
						logger.debug("Ico type is URL. So normalization is in process...");
						
						// if url occurred more than 5 times no need to process any more
						if (c3CrawelService.urlIsSpamCheck(cscCaseIdIcosForRegex)) continue;
						
						String parsedUrl = cscCaseIdIcoEntry.getKey();
						if (!c3CrawelService.isValidURL(parsedUrl)) {
							logger.debug("Parsed url is not valid one : " + parsedUrl);
							continue;
						}
						
						String normalizedUlr = c3CrawelService.findNormalizedUrl(cscCaseIdIcoEntry.getKey(),cscRawData);
						if (StringUtils.isBlank(normalizedUlr)) {
							logger.debug("Denied url.");
							continue;
						}
						cscExtractedIcoData.setIcoValue(normalizedUlr);
						findCSOConcept(normalizedUlr);	
						cscExtractedIcoData = findUrlProperties(normalizedUlr, cscExtractedIcoData);
					} 
					
					// CDETS
					if (StringUtils.equals(icoRegex.getIcoType(), Constants.ICO_TYPE_CDETS)) {
						// if ico occurred more than 5 times no need to process any more
						if (cscCaseIdIcosForRegex.size() >= Constants.ICO_MAX_IN_SINGLE_TEXT) continue;
					}
					
					// RFC
					if (StringUtils.equals(icoRegex.getIcoType(), Constants.ICO_TYPE_RFC)) {
						String rfcIcoValue = c3CrawelService.cleanRfcIcoValue(cscExtractedIcoData.getIcoValue());
						cscExtractedIcoData.setIcoValue(rfcIcoValue);
					}
					
					// Title setting for all Icos except pid, sw and serial no
					cscExtractedIcoData.setIcoTitle(c3CrawelService.findIcoTitle(icoRegex.getIcoType(), cscExtractedIcoData.getIcoValue()));
					
					// C3CASE
					if (StringUtils.equals(icoRegex.getIcoType(), Constants.ICO_TYPE_C3_CASE)) {
						// If title is blank, then that c3 case id is not valid
						if (StringUtils.isBlank(cscExtractedIcoData.getIcoTitle())) continue;
					}
					
					cscExtractedIcoData = setCSCExtractedIcoDataValues(cscExtractedIcoData, cscRawData, icoRegex.getIcoType());
					
					saveCSCExtractedIcoData(cscExtractedIcoData, cscRawData);
					
					logger.info("Regex cscExtractedIcoData : " + cscExtractedIcoData.getIcoValue() + " Saved successfully");
				}
			}
			
			//Step2 : For Software Images : Not required this process for 'Resolution Summary' text not contained case notes text
			logger.debug("Parsing ICO started for SW...");
			Set<String> c3CaseIdIcosForSW = c3CrawelService.findIcosForSW(cscRawData.getMessageBody());
			if ((!ObjectUtils.equals(c3CaseIdIcosForSW, null)) && (c3CaseIdIcosForSW.size() > 0)) {
			for (String swIco : c3CaseIdIcosForSW) {
				cscExtractedIcoData.setIcoValue(swIco);
				cscExtractedIcoData.setIcoTitle(swIco);
				logger.info("swIco cscExtractedIcoData values are setting for : " + swIco);
				cscExtractedIcoData = setCSCExtractedIcoDataValues(cscExtractedIcoData, cscRawData, Constants.ICO_TYPE_SW_IMAGE);
				// Store into scim db 'Table : c3_extracted_ico' for SW
				saveCSCExtractedIcoData(cscExtractedIcoData, cscRawData);
				logger.info("swIco cscExtractedIcoData : " + cscExtractedIcoData.getIcoValue() + " Saved successfully");
			}
			} else {
				logger.info("There is no 'SW-IMAGE-NAMES' ico for caseId : " + cscRawData.getMessageId());
			}
			
			//Step3 : For PID
			logger.debug("Parsing ICO started for PID...");
			Set<String> c3CaseIdIcosForPID = c3CrawelService.findIcosForPID(cscRawData.getMessageBody());
			if ((!ObjectUtils.equals(c3CaseIdIcosForPID, null)) && (c3CaseIdIcosForPID.size() > 0)) {
			for (String pidIco : c3CaseIdIcosForPID) {
				logger.info("PID Ico cscExtractedIcoData values are setting for : " + pidIco);
				cscExtractedIcoData.setIcoValue(pidIco);
				cscExtractedIcoData.setIcoTitle(Constants.PID_PRODUCT_NUMBERS.get(pidIco));
				cscExtractedIcoData = setCSCExtractedIcoDataValues(cscExtractedIcoData, cscRawData, Constants.ICO_TYPE_PID);
				// Store into scim db 'Table : c3_extracted_ico' for PID
				saveCSCExtractedIcoData(cscExtractedIcoData, cscRawData);
				logger.info("pidIco cscExtractedIcoData : " + cscExtractedIcoData.getIcoValue() + " Saved successfully");
			}
			} else {
				logger.info("There is no 'PID' ico for caseId : " + cscRawData.getMessageId());
			}
		}
	}
	
	
	public void saveCSCExtractedIcoData( CSCExtractedIcoData cscExtractedIcoData, CSCRawData cscRawData) {
		logger.debug("Inside saveCSCExtractedIcoData method...");
		
		CSCExtractedIcoData cSCExtractedIcoDataForIsExist = crawlDAO.findCSCExtractedIcoData(cscExtractedIcoData.getIcoValue(), cscRawData.getThreadId());
		if (!ObjectUtils.equals(cSCExtractedIcoDataForIsExist, null)) {// && (cSCExtractedIcoDataForIsExist.getMessageId() == cscRawData.getMessageId())) {
			logger.debug("Already this ico available in the ico_extraced table : " + cscExtractedIcoData.getIcoValue()+ " : for threadId : " + cscRawData.getThreadId());
			return;
		}		
		logger.debug("This is new ICO. Not exist in the DB : " + cscExtractedIcoData.getIcoValue() + " : for threadId : " + cscRawData.getThreadId());
		crawlDAO.saveCSCExtractedIcoData(cscExtractedIcoData);
	}
	
	private CSCExtractedIcoData setCSCExtractedIcoDataValues(CSCExtractedIcoData cscExtractedIcoData, CSCRawData cscRawData, String icoType) {
		logger.debug("Inside parseC3Ico method ...with crawledRawData : " + cscRawData.getMessageId() + " icoType : " + icoType);
		
		cscExtractedIcoData.setSource(Constants.SOURCE_CSC);
		cscExtractedIcoData.setIcoType(icoType);
		cscExtractedIcoData.setThreadId(cscRawData.getThreadId());
		cscExtractedIcoData.setThreadCommunityId(cscRawData.getThreadCommunityId());
		cscExtractedIcoData.setThreadParentCommunityId(cscRawData.getThreadParentCommunityId());
		cscExtractedIcoData.setMessageId(cscRawData.getMessageId());
		cscExtractedIcoData.setThreadName(cscRawData.getThreadName());
		cscExtractedIcoData.setThreadAuthor(cscRawData.getThreadAuthor());
		cscExtractedIcoData.setMessageCreationDate(cscRawData.getMessageCreationDate());
		cscExtractedIcoData.setMessageAuthor(cscRawData.getMessageAuthor());
		cscExtractedIcoData.setMessageAnswerIndicator(cscRawData.getMessageAnswerIndicator());
	
		return cscExtractedIcoData;
	}
	
	
	public CSCExtractedIcoData findUrlProperties(String normalizedUlr, CSCExtractedIcoData icoExtractedData) {
		logger.debug("Inside findUrlProperties method ...with url : " + normalizedUlr);
		
		String hostName = null;
		IcoUrlServerName icoUrlServerName = null;
		try {
			URL url = new URL(normalizedUlr);
			hostName = url.getHost();
			logger.debug("Founded host name : " + hostName);
		} catch(Exception e) {
			logger.debug("Exception when finding the host name : " + normalizedUlr);
		}
		
		if (!StringUtils.isBlank(hostName))
			icoUrlServerName = crawlDAO.findIcoUrlServerNameByServerName(hostName);
		if (ObjectUtils.equals(icoUrlServerName, null)) {
			logger.debug("Cant find the ico url server in the DB for  : " + hostName);
			return icoExtractedData;
		}
		
		icoExtractedData.setIcoUrlDomain(icoUrlServerName.getIcoUrlDomain());
		icoExtractedData.setIcoUrlAccessibility(icoUrlServerName.getIcoUrlAccessibility());
		icoExtractedData.setIcoUrlServerName(icoUrlServerName.getServerName());
		
		return icoExtractedData;	
	}
	
	private Date parseDateFromMsgCreationDateStr(String messageCreationDateStr) throws Exception {
		logger.debug("Inside parseDateFromMsgCreationDateStr method with : " + messageCreationDateStr);
		//String messageCreationDateStr = "2012-03-09T14:49:33.326-07:00";
		String truncFromChar = "T";
		if ((!StringUtils.isBlank(messageCreationDateStr)) && (messageCreationDateStr.indexOf(truncFromChar) > -1))
			messageCreationDateStr = messageCreationDateStr.substring(0, messageCreationDateStr.indexOf(truncFromChar));
		
		logger.debug("After truncated messageCreationDateStr : " + messageCreationDateStr);
		
		SimpleDateFormat MESG_DATE_FORMATTER = new SimpleDateFormat("yy-MM-dd");
		Date messageCreationDate = MESG_DATE_FORMATTER.parse(messageCreationDateStr);
		
		logger.debug("Formatted messageCreationDate : " + messageCreationDate);
		
		return messageCreationDate;		
	}
	void findCSOConcept(String normalizedUlr)
	{
	logger.debug("Inside findCSOConcept method ...with icoValue : " + normalizedUlr);
	UrlMDFData urlmdfdata = new UrlMDFData();
	String fieldValue = null;	
	
	CDCCrawlerService cdcCrawler = new CDCCrawlerService();
	List<String> list = new ArrayList<String>();
	try {
		
		String encodedIcoValue = URLEncoder.encode(normalizedUlr,"UTF-8");
		String neo4jIcoNodeUrl = Constants.NEO4J_ICO_NODE_URL + encodedIcoValue;
		String content = readNeo4jUrlAsJson(neo4jIcoNodeUrl);
			
		content = StringUtils.removeStart(content, "[ ");
		content = StringUtils.removeEnd(content, "]");
		logger.debug("content "+content);
		if (!StringUtils.isBlank(content)) {
	
			JSONObject neo4jIcoNodeJson = new JSONObject(content);
			JSONObject neo4jIcoDataJson = (JSONObject) neo4jIcoNodeJson.get("data");

			fieldValue = (String) neo4jIcoDataJson.get("node_id");
			list = cdcCrawler.serachCSO(normalizedUlr);
				if(!list.equals("") || list != null){						
					for(String concept : list){
						if(!concept.equals(null)){
							int mdfId = crawlDAO.findMaxMDFId();
							urlmdfdata.setMdfId(mdfId+ 1);
							urlmdfdata.setUrl(normalizedUlr);
							urlmdfdata.setMdf(concept);
							logger.debug("Url_MDF " + urlmdfdata.getUrl() + urlmdfdata.getMdf() );
							crawlDAO.saveURLMDFData(urlmdfdata);
						}
					}
				}
			}			
    } catch (Exception e) {
    	logger.warn("Exception catched : " + e);
        e.printStackTrace();
    }
}
	
}
