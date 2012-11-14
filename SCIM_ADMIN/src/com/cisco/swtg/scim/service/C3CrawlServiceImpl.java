package com.cisco.swtg.scim.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.BindException;
import java.net.ConnectException;
import java.net.HttpRetryException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.NoRouteToHostException;
import java.net.PortUnreachableException;
import java.net.ProtocolException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import net.htmlparser.jericho.CharacterReference;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.pdmodel.PDDocumentInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cisco.swtg.scim.dao.c3bldao.C3blDAO;
import com.cisco.swtg.scim.dao.scimdao.CrawlDAO;
import com.cisco.swtg.scim.model.c3blmodel.SrContactXxctsCrwsdmUV;
import com.cisco.swtg.scim.model.c3blmodel.SrCurrContXxctsCrwsdmUV;
import com.cisco.swtg.scim.model.c3blmodel.TssCotTechnolotiesDV;
import com.cisco.swtg.scim.model.c3blmodel.TssDefectsDV;
import com.cisco.swtg.scim.model.c3blmodel.TssIncidentDefectsFV;
import com.cisco.swtg.scim.model.c3blmodel.TssIncidentLinksFV;
import com.cisco.swtg.scim.model.c3blmodel.TssIncidentNotesFV;
import com.cisco.swtg.scim.model.c3blmodel.TssIncidentsCurrentFV;
import com.cisco.swtg.scim.model.c3blmodel.TssTacProductsDV;
import com.cisco.swtg.scim.model.c3blmodel.WorkgroupsXxctsCrwsdmUV;
import com.cisco.swtg.scim.model.scimmodel.C3RawData;
import com.cisco.swtg.scim.model.scimmodel.CSCRawData;
import com.cisco.swtg.scim.model.scimmodel.IcoDenyPermtPattern;
import com.cisco.swtg.scim.model.scimmodel.IcoExtractedData;
import com.cisco.swtg.scim.model.scimmodel.IcoRegex;
import com.cisco.swtg.scim.model.scimmodel.IcoUrlServerName;
import com.cisco.swtg.scim.model.scimmodel.LinkedBugs;
import com.cisco.swtg.scim.model.scimmodel.LinkedServiceRequest;
import com.cisco.swtg.scim.model.scimmodel.Product;
import com.cisco.swtg.scim.model.scimmodel.RejectedUrlList;
import com.cisco.swtg.scim.model.scimmodel.SoftwareImageNameMview;
import com.cisco.swtg.scim.model.scimmodel.SwImageGuidMaster;
import com.cisco.swtg.scim.model.scimmodel.UrlMDFData;
import com.cisco.swtg.scim.model.scimmodel.UrlShortHand;
import com.cisco.swtg.scim.scheduler.PostToUrl;
import com.cisco.swtg.scim.util.Constants;

@Service("c3CrawelService")
public class C3CrawlServiceImpl implements C3CrawlService {

	private static final Logger logger = Logger.getLogger(C3CrawlServiceImpl.class);
	
	@Autowired
	CrawlDAO crawlDAO;
	
	@Autowired
	C3blDAO c3blDAO;
	
	@Autowired
	C3blService c3blService;
	
	@Autowired
	CSCCrawlService cSCCrawelService;
	
	@Autowired
	LoadIntializeData loadIntializeData;

 	@PostConstruct
	public void populateSwImageAndPidByJThread() {
 		// Thread used because loading pid and sw are should not affect the server start
		Thread t = new Thread(loadIntializeData);
		t.start();
	}


	@Override
    public void populateSwImageAndPid() {
    	logger.debug("inside populateSwImageAndPid method");
    	
    	// Refresh view
    	logger.debug("M view Refresh started...");
    	Constants.PID_PRODUCT_NUMBERS.clear();
    	Constants.SW_IMAGE_FILE_NAMES.clear();
    	logger.info("Before load PIDs size : " + Constants.PID_PRODUCT_NUMBERS.size());
    	logger.info("Before load SW Images size : " + Constants.SW_IMAGE_FILE_NAMES.size());
    	logger.debug("M view Refresh success...");
    	
		// For PID_NUMBERS
		List<Product> products = crawlDAO.findAllProduct();
		if (!ObjectUtils.equals(products, null)) {
			for (Product product : products) {
				if(ObjectUtils.equals(product, null)) continue;
				String productNumber = product.getProductNumber();
				String itemDesc = product.getItemDescription();
				if (StringUtils.isBlank(productNumber)) continue;
				Constants.PID_PRODUCT_NUMBERS.put(productNumber, itemDesc);
			}
			logger.info("PIDs are successfully loaded : " + Constants.PID_PRODUCT_NUMBERS.size());
		} else {
			logger.debug("There is no PID in Product table of SCIM DB");
		}
		
    	// FOR SW Image Names
		List<SwImageGuidMaster> swImageGuidMasters = crawlDAO.findAllSwImageNamesGuidMaster();
		logger.debug("SwImageGuidMaster successfully fetched : Size : " + swImageGuidMasters.size());
		if (!ObjectUtils.equals(swImageGuidMasters, null))  {
			for (SwImageGuidMaster swImageGuidMaster : swImageGuidMasters) {
				if(ObjectUtils.equals(swImageGuidMaster, null)) continue;
				Constants.SW_IMAGE_FILE_NAMES.add(swImageGuidMaster.getFileName());
			}
			logger.info("SWs are successfully loaded size : " + Constants.SW_IMAGE_FILE_NAMES.size());
		} else {
			logger.debug("There is no 'Sw Image Name' in SW_Image_view of SCIM DB");
		}
    }

    /*@Override
    public void constructPIDRegexs(Map<String, String> productNumbersMap) {
    	logger.debug("Inside constructPIDRegexs method with productNumbersMap");

    	String pidRegex = "";
    	int count = 1;
    	int pentingCount = productNumbersMap.size();
    	for (Map.Entry<String, String> entry : productNumbersMap.entrySet()) {
    		String productNumber = entry.getKey();
    		productNumber = Pattern.quote(productNumber);
    		if (StringUtils.isBlank(pidRegex))
    			pidRegex = "("+productNumber+"\\s)|(\\s"+productNumber+"\\s)|(\\s"+productNumber+"\n)";
    		else pidRegex = pidRegex + "|("+productNumber+"\\s)|(\\s"+productNumber+"\\s)|(\\s"+productNumber+"\n)";
    		
    		if ((count >= Constants.REGEX_ELEMENTS_MAX_COUNT) || (pentingCount <= count)) {    			
    			Constants.PID_PRODUCT_NUMBER_REGEXs.add(pidRegex);
    			count = 1;
    			pidRegex = "";
    		}    			
       		pentingCount--;
       		count++;
    	}    	
    	logger.debug("Pid regex are constructed success : " + Constants.PID_PRODUCT_NUMBER_REGEXs.size());
    	System.out.println("Pid regex are constructed success : " + Constants.PID_PRODUCT_NUMBER_REGEXs.size());
    }*/
    
	@Override
	public List<C3RawData> listC3RawData() {
		logger.debug("Inside listC3RawData method ...");
		List<C3RawData> listC3RawDatas = crawlDAO.listC3RawData();
		return listC3RawDatas;
	}
	
	@Override
	public C3RawData  findC3RawDataById(String serviceRequestId) {
		logger.debug("Inside findC3RawDataById method ...");
		C3RawData c3RawData = crawlDAO.findC3RawDataById(serviceRequestId);
		return c3RawData;
	}
	
	@Override
	public void save(C3RawData c3RawData) {
		logger.debug("Inside save method with : " + c3RawData);
		crawlDAO.save(c3RawData);
	}
	
	@Override
	public void saveC3RawData(List<C3RawData> c3RawDatas) {
		logger.debug("Inside saveC3RawData method ..." + c3RawDatas);
		crawlDAO.saveC3RawDatas(c3RawDatas);
	}

	@Override
	public void saveC3RawData(TssIncidentNotesFV tssIncidentNotesFV) {
		logger.debug("Inside saveC3RawData method with saveC3RawData : " +  tssIncidentNotesFV);
		List<C3RawData> c3RawDatas = generateC3RawDatas(tssIncidentNotesFV);
		if (!ObjectUtils.equals(c3RawDatas, null)) {
			saveC3RawData(c3RawDatas);
			logger.debug("Successfully saved c3RawDatas size : " + c3RawDatas.size());
		}
	}
	
	@Override
	public void saveC3RawDatas(List<TssIncidentNotesFV> tssIncidentNotesFVs) {
		logger.debug("Inside saveC3RawData method with saveC3RawData : " +  tssIncidentNotesFVs);
		
		if (ObjectUtils.equals(tssIncidentNotesFVs, null)) return;
		
		for (TssIncidentNotesFV tssIncidentNotesFV : tssIncidentNotesFVs) {
			List<C3RawData> c3RawDatas = generateC3RawDatas(tssIncidentNotesFV);
			if (!ObjectUtils.equals(c3RawDatas, null)) {
				saveC3RawData(c3RawDatas);
				logger.debug("Successfully saved c3RawDatas size : " + c3RawDatas.size());
			}
		}
	}
	
	public List<C3RawData> generateC3RawDatas(TssIncidentNotesFV tssIncidentNotesFV) {
		logger.debug("Inside generateC3RawDatas method ...with tssIncidentNotesFV : " + tssIncidentNotesFV);
		if ((ObjectUtils.equals(tssIncidentNotesFV, null)) || ((ObjectUtils.equals(tssIncidentNotesFV.getTssIncidentsCurrentFV(), null)))) {
			logger.debug("'tssIncidentsCurrentFV is null]' or  'tssIncidentsCurrentFV.getTssIncidentNotesFVs() is null or empty'");
			return null;
		}

		TssIncidentsCurrentFV tssIncidentsCurrentFV = tssIncidentNotesFV.getTssIncidentsCurrentFV();
		List<C3RawData> c3RawDatas = new ArrayList<C3RawData>();
			C3RawData c3RawData = new C3RawData();
			c3RawData.setCaseId(tssIncidentsCurrentFV.getIncidentnumber());
			c3RawData.setTitle(tssIncidentsCurrentFV.getSummary());
			c3RawData.setCreationDate(tssIncidentsCurrentFV.getCreationDate());
			c3RawData.setCsOneId(tssIncidentsCurrentFV.getExternalCaseNumber());
			c3RawData.setProblemCode(tssIncidentsCurrentFV.getProblemCode());
			c3RawData.setSerialNumber(tssIncidentsCurrentFV.getCurrentSerialNumber());
			
			c3RawData.setCaseNoteId(tssIncidentNotesFV.getJtfNoteId());//Primary Key
			c3RawData.setSrCaseNoteType(tssIncidentNotesFV.getNoteType());
			c3RawData.setSrCaseNoteTimestamp(tssIncidentNotesFV.getCreationDate());
			c3RawData.setSrCaseNoteAuthor(tssIncidentNotesFV.getCreatedAuthor());
			c3RawData.setSrCaseNoteWorkgroup(tssIncidentNotesFV.getCreatorWorkgroup());
			
	/*		// Workgroup ID
			WorkgroupsXxctsCrwsdmUV workgroupsXxctsCrwsdmUV = tssIncidentNotesFV.getWorkgroupsXxctsCrwsdmUV();
			if (!ObjectUtils.equals(workgroupsXxctsCrwsdmUV, null)) {
				c3RawData.setWorkgroupId(workgroupsXxctsCrwsdmUV.getWorkgroupId());
			}*/
			List<WorkgroupsXxctsCrwsdmUV> workgroupsXxctsCrwsdmUVs = tssIncidentNotesFV.getWorkgroupsXxctsCrwsdmUV();
			if (!ObjectUtils.equals(workgroupsXxctsCrwsdmUVs, null) && (workgroupsXxctsCrwsdmUVs.size() > 0)) {
				c3RawData.setWorkgroupId(workgroupsXxctsCrwsdmUVs.get(0).getWorkgroupId());
			}
			
			//If Notes Details is null, have to set Notes for Case Notes Text
			if (StringUtils.isBlank(tssIncidentNotesFV.getNotesDetail()))
				c3RawData.setSrCaseNotesText(tssIncidentNotesFV.getNotes());
			else
				c3RawData.setSrCaseNotesText(tssIncidentNotesFV.getNotesDetail());
			
			TssCotTechnolotiesDV tssCotTechnolotiesDV = tssIncidentsCurrentFV.getTssCotTechnolotiesDV();
			if (!ObjectUtils.equals(tssCotTechnolotiesDV, null)) {
				c3RawData.setTechId(tssCotTechnolotiesDV.getTechId());
				c3RawData.setSubTechId(tssCotTechnolotiesDV.getSubTechId());
				c3RawData.setTechName(tssCotTechnolotiesDV.getTechName());
				c3RawData.setSubTechName(tssCotTechnolotiesDV.getSubTechName());			
			}
			
			//contract
			SrCurrContXxctsCrwsdmUV srCurrContXxctsCrwsdmUV = tssIncidentsCurrentFV.getSrCurrContXxctsCrwsdmUV();
			if (!ObjectUtils.equals(srCurrContXxctsCrwsdmUV, null)) {
				c3RawData.setContractId(srCurrContXxctsCrwsdmUV.getContractNumber());
			}
			
			//contact
			SrContactXxctsCrwsdmUV srContactXxctsCrwsdmUV = tssIncidentsCurrentFV.getSrContactXxctsCrwsdmUV();
			if (!ObjectUtils.equals(srContactXxctsCrwsdmUV, null)) {
				c3RawData.setContact(srContactXxctsCrwsdmUV.getCcoId());
			}
			
			// hardware detail
			TssTacProductsDV tssTacProductsDV = tssIncidentsCurrentFV.getTssTacProductsDV();
			if (!ObjectUtils.equals(tssTacProductsDV, null))
				c3RawData.setHardwareDetails(tssTacProductsDV.getPartNumber());
			
			// software details
			tssTacProductsDV = c3blDAO.findTssTacProductsDV(tssIncidentsCurrentFV.getSwVersionId());
			if (!ObjectUtils.equals(tssTacProductsDV, null))
				c3RawData.setSwDetails(tssTacProductsDV.getPartNumber());
				
			// bugs column setting
			if ((!ObjectUtils.equals(tssIncidentsCurrentFV.getTssIncidentDefectsFVs(), null))) {
				//Set<LinkedBugs> linkedBugs = new HashSet<LinkedBugs>();
				List<LinkedBugs> linkedBugs = new ArrayList<LinkedBugs>();
				for (TssIncidentDefectsFV tssIncidentDefectsFV : tssIncidentsCurrentFV.getTssIncidentDefectsFVs()) {
					if (!ObjectUtils.equals(tssIncidentDefectsFV, null)) {
						TssDefectsDV tssDefectsDV = tssIncidentDefectsFV.getTssDefectsDV();
						if (!ObjectUtils.equals(tssDefectsDV, null)) {
							//c3RawData.setBugs(tssDefectsDV.getDefectNumber());// moved to separate table
							LinkedBugs linkedBug = new LinkedBugs();
							linkedBug.setBlDefectKey(tssDefectsDV.getBlDefectKey());
							linkedBug.setBug(tssDefectsDV.getDefectNumber());
							linkedBug.setCaseId(c3RawData.getCaseId());
							linkedBugs.add(linkedBug);
							//break;
						} else {
							logger.debug("There is no tssDefectsDV for BL_DEFECT_KEY : " + tssDefectsDV.getBlDefectKey());
						}
					} else {
						logger.debug("There is no tssIncidentDefectsFV for incidentID : " + tssIncidentsCurrentFV.getIncidentId());
					}
			}
			c3RawData.setLinkedBugs(linkedBugs);
			}

			// Linked Case id column			
			Set<TssIncidentLinksFV> tssIncidentLinksFVs = tssIncidentsCurrentFV.getTssIncidentLinksFVs();
			if ((!ObjectUtils.equals(tssIncidentLinksFVs, null)) && (tssIncidentLinksFVs.size() > 0)) {
				Set<LinkedServiceRequest> linkedServiceRequests = new HashSet<LinkedServiceRequest>();
				for (TssIncidentLinksFV tssIncidentLinksFV : tssIncidentLinksFVs) {
					//c3RawData.setLinkedServiceRequest(tssIncidentLinksFV.getToIncidentId()); // Moved to separate table
					LinkedServiceRequest linkedServiceRequest = new LinkedServiceRequest();
					linkedServiceRequest.setCaseId(c3RawData.getCaseId());
					linkedServiceRequest.setToCaseId(tssIncidentLinksFV.getToIncidentNumber());
					linkedServiceRequest.setLinkId(tssIncidentLinksFV.getBlLinkIdKey());
					if (!StringUtils.isBlank(linkedServiceRequest.getToCaseId()))
						linkedServiceRequests.add(linkedServiceRequest);					
				}
				c3RawData.setLinkedServiceRequests(linkedServiceRequests);
			}
			
			// save into db
			if ((!ObjectUtils.equals(c3RawData, null)) && (!StringUtils.isBlank(c3RawData.getCaseId()))) {				
				crawlDAO.save(c3RawData);
				//count++;
				logger.debug("c3RawData Saved successfully in SCIM DB Case Note ID : " + c3RawData.getCaseNoteId() + " Case Id : " + c3RawData.getCaseId());
			} else {
				logger.debug("c3RawData's caseId is blank. So can't save");
			}

		return c3RawDatas;		
	}

	@Override
	public IcoRegex findIcoRegex(long icoRegexId) {
		logger.debug("Inside findIcoRegex method ...with : " + icoRegexId);
		return crawlDAO.findRegexById(icoRegexId);
	}
	
	
	@Override
	public Map<String, Integer> findRegexMatchedResult(String notes, String regex, boolean isCaseSensitive) {
		//logger.debug("Inside findRegexMatchedResult method ...with regex : " + regex + " isCaseSensitive : " + isCaseSensitive);

		Map<String, Integer> regexMatchedResults = new HashMap<String, Integer>();
		if ((StringUtils.isBlank(notes)) || (StringUtils.isBlank(regex))) return regexMatchedResults;
		
		Pattern p = Pattern.compile(regex);
		if (!isCaseSensitive) p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE|Pattern.MULTILINE);
		Matcher m = p.matcher(notes);
		while (m.find()) {
			String regexMatchedString = m.group();
			if (StringUtils.isBlank(regexMatchedString)) continue;
			// For no.of times occurred
			if ((!regexMatchedResults.containsKey(regexMatchedString))) 
				regexMatchedResults.put(regexMatchedString, 1);
			else
				regexMatchedResults.put(regexMatchedString, regexMatchedResults.get(regexMatchedString) + 1);
		}
		return regexMatchedResults;
	}
	

	public void saveIcoExtractedData(IcoExtractedData icoExtractedData, C3RawData crawledRawData ) {
		logger.debug("Inside saveIcoExtractedData method");
		// if already available same ico, then not need to update it
		if (!ObjectUtils.equals(crawlDAO.findExtractedIcoData(icoExtractedData.getIcoValue(), crawledRawData.getCaseId()), null)) {
			logger.debug("Already this ico available in the ico_extraced table for same caseId : " + icoExtractedData.getIcoValue() + " : case Id : " + crawledRawData.getCaseId());
			return;
		} 
		
		// if the ico is Denied, not need to save into ico extracted table
		if (!StringUtils.equals(icoExtractedData.getIcoType(), Constants.ICO_TYPE_URL)) { //For URL ico we had separate method to deny
			if (findIsDeniedIco(icoExtractedData.getIcoValue(), icoExtractedData.getIcoType())) {
				logger.debug("This ico is denied  : " + icoExtractedData.getIcoValue() + " : icoType : " + icoExtractedData.getIcoType());
				return;
			}
		}
		
		// unique Id value setting
		int c3IcoId = crawlDAO.findMaxC3IcoId();
		icoExtractedData.setC3IcoId(c3IcoId+1);

		logger.debug("Storing the ico because of this is new ICO. Not exist in the DB for caseId : " + icoExtractedData.getIcoValue() + " : case Id : " + crawledRawData.getCaseId());
		crawlDAO.saveIcoExtractedData(icoExtractedData);		
	}
	
	
	public void generateAndSaveSerialNoIco(C3RawData crawledRawData) {
		logger.debug("Inside generateAndSaveSerialNoIco method...");
		
		String serialNo = crawledRawData.getSerialNumber();

		// For serialNoIco extraction should resolved case only.That is case note should contain "Resolution Summary" 
		if ((StringUtils.isBlank(serialNo)) || (!StringUtils.containsIgnoreCase(crawledRawData.getSrCaseNotesText(), Constants.RESOLUTION_SUMMARY)))
				return;
		
			IcoExtractedData icoExtractedData = new IcoExtractedData();
			icoExtractedData.setIcoValue(serialNo);
			icoExtractedData.setCaseNoteType(Constants.RESOLUTION_SUMMARY_CASE_NOTE_TYPE);
			icoExtractedData = setIcoExtractedDataValues(icoExtractedData, crawledRawData, Constants.ICO_TYPE_SERIALNO);
			icoExtractedData.setIcoTitle(serialNo);

			saveIcoExtractedData(icoExtractedData, crawledRawData);
			logger.debug("SerialNo Ico saved successfully for resolved caseNoteId : " + crawledRawData.getCaseNoteId() +  " :  c3_ico_id : " + icoExtractedData.getC3IcoId());;
				
	}
	
	public void generateAndSaveLinkedCaseIdIco(C3RawData crawledRawData) {
		logger.debug("Inside generateAndSaveLinkedCaseIdIco method..." +  crawledRawData.getLinkedServiceRequests());
		
//		Set<TssIncidentLinksFV> tssIncidentLinksFVs = tssIncidentsCurrentFV.getTssIncidentLinksFVs();
		Set<LinkedServiceRequest> linkedServiceRequests = crawledRawData.getLinkedServiceRequests();

		// For Linked caseId extraction should resolved case only.That is case note should contain "Resolution Summary" 
		if ((ObjectUtils.equals(linkedServiceRequests, null)) || (!StringUtils.containsIgnoreCase(crawledRawData.getSrCaseNotesText(), Constants.RESOLUTION_SUMMARY)))
				return;
		
		for (LinkedServiceRequest linkedServiceRequest : linkedServiceRequests) {
			IcoExtractedData icoExtractedData = new IcoExtractedData();
			icoExtractedData.setIcoValue(linkedServiceRequest.getToCaseId());
			icoExtractedData.setIcoTitle(crawledRawData.getTitle());	
			icoExtractedData.setCaseNoteType(Constants.RESOLUTION_SUMMARY_CASE_NOTE_TYPE);
			icoExtractedData = setIcoExtractedDataValues(icoExtractedData, crawledRawData, Constants.ICO_TYPE_C3_CASE);
			saveIcoExtractedData(icoExtractedData, crawledRawData);
			logger.debug("LinkedCaseIdIco Ico saved successfully for resolved caseNoteId : " + crawledRawData.getCaseNoteId() +  " :  c3_ico_id : " + icoExtractedData.getC3IcoId());;
		}		
	}
	
	public void generateAndSaveLinkedCDETSIco(C3RawData crawledRawData) {
		logger.debug("Inside generateAndSaveLinkedCDETSIco method..." + crawledRawData.getLinkedBugs());
		
//		Set<TssIncidentLinksFV> tssIncidentLinksFVs = tssIncidentsCurrentFV.getTssIncidentLinksFVs();
		List<LinkedBugs> linkedBugs = crawledRawData.getLinkedBugs();

		// For CDETS extraction should resolved case only.That is case note should contain "Resolution Summary" 
		if ((ObjectUtils.equals(linkedBugs, null)) || (!StringUtils.containsIgnoreCase(crawledRawData.getSrCaseNotesText(), Constants.RESOLUTION_SUMMARY)))
				return;
		
		for (LinkedBugs linkedBug : linkedBugs) {
			IcoExtractedData icoExtractedData = new IcoExtractedData();
			icoExtractedData.setIcoValue(linkedBug.getBug());
			icoExtractedData.setCaseNoteType(Constants.RESOLUTION_SUMMARY_CASE_NOTE_TYPE);
			icoExtractedData = setIcoExtractedDataValues(icoExtractedData, crawledRawData, Constants.ICO_TYPE_CDETS);

			String cdetsTitle = findTitleFromHtmlUrl(Constants.CDETS_URL + icoExtractedData.getIcoValue(), Constants.CISCO_USERID, Constants.CISCO_PASSWORD);
			cdetsTitle = cleanCdetsTitle(cdetsTitle, icoExtractedData.getIcoValue());
			icoExtractedData.setIcoTitle(cdetsTitle);

			saveIcoExtractedData(icoExtractedData, crawledRawData);
			logger.debug("LinkedCDETSIco Ico saved successfully for resolved caseNoteId : " + crawledRawData.getCaseNoteId() +  " :  c3_ico_id : " + icoExtractedData.getC3IcoId());;
		}		
	}
	
	
	public void findLinkedCaseIdAndLinkedCDETS(C3RawData crawledRawData) {
		logger.debug("Inside findLinkedCaseIdAndLinkedCDETS method...");
		
		String caseId = crawledRawData.getCaseId();
		List<LinkedServiceRequest> linkedServiceRequests = crawlDAO.findLinkedServiceRequests(caseId);
		List<LinkedBugs> linkedBugs = crawlDAO.findLinkedBugs(caseId);
		
		crawledRawData.setLinkedServiceRequests(new HashSet<LinkedServiceRequest>(linkedServiceRequests));
		crawledRawData.setLinkedBugs(new ArrayList<LinkedBugs>(linkedBugs));
	}
	
	public void generateAndSaveHWDetailsIco(C3RawData crawledRawData) {
		logger.debug("Inside generateAndSaveHWDetailsIco method...");
		
		// For HWDetailsIco extraction should resolved case only.That is case note should contain "Resolution Summary" 
		if ((!StringUtils.containsIgnoreCase(crawledRawData.getSrCaseNotesText(), Constants.RESOLUTION_SUMMARY)))
				return;
		// verifying hardware details is available in Scim DB
		String hwDetails = crawledRawData.getHardwareDetails();
		Product product = crawlDAO.findProductByProductNo(hwDetails);		
		if (ObjectUtils.equals(product, null)) {
			logger.debug("productNumber : " + hwDetails + " not available in the product table") ;
			return;
		}
		
		IcoExtractedData icoExtractedData = new IcoExtractedData();
		icoExtractedData.setIcoValue(hwDetails);
		icoExtractedData.setIcoTitle(crawledRawData.getHardwareDetails());			
		icoExtractedData.setCaseNoteType(Constants.RESOLUTION_SUMMARY_CASE_NOTE_TYPE);
		icoExtractedData = setIcoExtractedDataValues(icoExtractedData, crawledRawData, Constants.ICO_TYPE_PID);
		saveIcoExtractedData(icoExtractedData, crawledRawData);
		logger.debug("HWDetails Ico saved successfully for resolved caseNoteId : " + crawledRawData.getCaseNoteId() +  " :  c3_ico_id : " + icoExtractedData.getC3IcoId());;
	}
	
	
	public void generateAndSaveSWDetailsIco(C3RawData crawledRawData) {
		logger.debug("Inside generateAndSaveSWDetailsIco method...");
		
		// For HWDetailsIco extraction should resolved case only.That is case note should contain "Resolution Summary" 
		if ((!StringUtils.containsIgnoreCase(crawledRawData.getSrCaseNotesText(), Constants.RESOLUTION_SUMMARY)))
				return;
		// verifying is sw details available in Scim DB
		String swDetails = crawledRawData.getSwDetails();
		SoftwareImageNameMview softwareImageName = crawlDAO.findSwImageMViewByFileName(swDetails);
		if (ObjectUtils.equals(softwareImageName, null)) softwareImageName = crawlDAO.findSwImageMViewByFileNameFuzzy(swDetails+".");
		if ((ObjectUtils.equals(softwareImageName, null)) || (StringUtils.isBlank(swDetails)) || (swDetails.length() < 6)) {
			logger.debug("swDetails : " + swDetails + " not available in the SoftwareImageNameMview DB or its length should greater than 6") ;
			return;
		}
		
		IcoExtractedData icoExtractedData = new IcoExtractedData();
		icoExtractedData.setIcoValue(swDetails);
		icoExtractedData.setIcoTitle(swDetails);			
		icoExtractedData.setCaseNoteType(Constants.RESOLUTION_SUMMARY_CASE_NOTE_TYPE);
		icoExtractedData = setIcoExtractedDataValues(icoExtractedData, crawledRawData, Constants.ICO_TYPE_SW_IMAGE);
		saveIcoExtractedData(icoExtractedData, crawledRawData);
		logger.debug("SWDetails Ico saved successfully for resolved caseNoteId : " + crawledRawData.getCaseNoteId() +  " :  c3_ico_id : " + icoExtractedData.getC3IcoId());;
	}
	
	@Override
	public String findIcoTitle(String icoType, String icoValue) {
		
			String title = findIcoFieldValueFromNeo4j(icoValue, "ico_title");
			
			if (!StringUtils.isBlank(title))
				return title;
			
			// C3CASE
			if (StringUtils.equals(icoType, Constants.ICO_TYPE_C3_CASE)) {
				TssIncidentsCurrentFV tssIncidentsCurrentFV = c3blDAO.findC3blCrawledRawDataIncidentNo(icoValue);
				if (!ObjectUtils.equals(tssIncidentsCurrentFV, null)) {
					title = tssIncidentsCurrentFV.getSummary();
				}
			}
			
			// CDETS
			if (StringUtils.equals(icoType, Constants.ICO_TYPE_CDETS)) {
				String cdetsTitle = findTitleFromHtmlUrl(Constants.CDETS_URL + icoValue, Constants.CISCO_USERID, Constants.CISCO_PASSWORD);
				cdetsTitle = cleanCdetsTitle(cdetsTitle, icoValue);
				title = cdetsTitle;
			}
			
			// RFC
			if (StringUtils.equals(icoType, Constants.ICO_TYPE_RFC)) {
				String rfcContent = cSCCrawelService.readUrl(Constants.RFC_HOST, Constants.RFC_REQUEST + icoValue + ".html");
				title = findTitleFromHtmlContent(rfcContent);
			}
			
			// URL
			if (StringUtils.equals(icoType, Constants.ICO_TYPE_RFC)) {				
				if (StringUtils.endsWithIgnoreCase(icoValue, ".pdf")) {
					title = findPDFTtitleByURL(icoValue);
				} else {
					title = findTitleFromHtmlUrl(icoValue, Constants.CISCO_USERID, Constants.CISCO_PASSWORD);
				}
			}
			
			// MIB, ERRMSG
			if ((StringUtils.equals(icoType, Constants.ICO_TYPE_MIB)) || (StringUtils.equals(icoType, Constants.ICO_TYPE_ERRMSG))) title = icoValue;


			return title;		
	}

	public String findIcoFieldValueFromNeo4j(String icoValue, String field) {
		logger.debug("Inside findIcoFieldValueFromNeo4j method ...with icoValue : " + icoValue + " field : " + field);
		String fieldValue = null;
		try {
			
			String encodedIcoValue = URLEncoder.encode(icoValue,"UTF-8");
			String neo4jIcoNodeUrl = Constants.NEO4J_ICO_NODE_URL + encodedIcoValue;
			
			String content = cSCCrawelService.readNeo4jUrlAsJson(neo4jIcoNodeUrl);
			if (StringUtils.isBlank(content)) return fieldValue;
			
			content = StringUtils.removeStart(content, "[");
			content = StringUtils.removeEnd(content, "]");

			if (StringUtils.isBlank(content)) return fieldValue;
			JSONObject neo4jIcoNodeJson = new JSONObject(content);
			JSONObject neo4jIcoDataJson = (JSONObject) neo4jIcoNodeJson.get("data");

			fieldValue = (String) neo4jIcoDataJson.get(field);			
			logger.info("Found  " + field + " in neo4j : " + fieldValue);
			
        } catch (Exception e) {
        	logger.warn("Exception catched : " + e);
            e.printStackTrace();
        }
		return fieldValue;
	}
		
	
	@Override
	public void parseC3Ico(String fromDate, String toDate) {
		logger.debug("Inside parseC3Ico method ...with fromDate : " + fromDate + " toDate : " + toDate);

		List<IcoRegex>  icoRegexs = crawlDAO.findAllRegex();
		logger.info("***Loading All rawdata from SCIM DB to memory for ICO extraction started");
		List<C3RawData> c3RawDatas = crawlDAO.findC3CaseRawData(fromDate, toDate);		
		logger.info("***Loading All rawdata from SCIM DB to memory for ICO extraction completed");

		if ((ObjectUtils.equals(c3RawDatas, null)) || (c3RawDatas.size() < 1)) {
			logger.info("*Imp : Empty c3Rawdatas for fromDate : " + fromDate + " to date : " + toDate);
			return;
		}
		
		for (C3RawData crawledRawData : c3RawDatas) {
			logger.debug("Ico parscing is in process for C3RawData : " + crawledRawData.getCaseId());
			
			// Step 1 : Process for Regexs
			if (!ObjectUtils.equals(icoRegexs, null))
			for (IcoRegex icoRegex : icoRegexs) {
				logger.debug("icoRegex : " + icoRegex.getIcoRegexId());
				logger.info("*** ICO extraction started for : " + icoRegex.getIcoType());
				IcoExtractedData icoExtractedData = new IcoExtractedData();
				if ((ObjectUtils.equals(crawledRawData, null)) || (ObjectUtils.equals(icoRegex, null))) continue;

				boolean isCaseSensitiveRegex = true;
				if (StringUtils.equals(icoRegex.getCaseSensitive(),Constants.NO_OPTION)) isCaseSensitiveRegex = false;
				
				Map<String, Integer> c3CaseIdIcosForRegex = findRegexMatchedResult(crawledRawData.getSrCaseNotesText(), icoRegex.getIcoRegex(), isCaseSensitiveRegex);
				
				for (Map.Entry<String, Integer> c3CaseIdIcoEntry : c3CaseIdIcosForRegex.entrySet()) {
															
					String icoValue = c3CaseIdIcoEntry.getKey().trim();
					icoExtractedData.setIcoValue(icoValue);
					
					// url
					if (StringUtils.equals(icoRegex.getIcoType(), Constants.ICO_TYPE_URL)) {
						logger.debug("Ico type is URL. So normalization is in process...");
						
						// if ico occurred more than 5 times no need to process any more
						//if (c3CaseIdIcoEntry.getValue() >= Constants.ICO_MAX_IN_SINGLE_TEXT) continue;
						if (urlIsSpamCheck(c3CaseIdIcosForRegex)) continue; 
						
						String parsedUrl = icoValue;

						parsedUrl = findRemovedNewlineCharUrl(parsedUrl, crawledRawData.getSrCaseNotesText(), icoRegex.getIcoRegex());//TODO: have to finish
						if (StringUtils.isBlank(parsedUrl)) continue;
						logger.debug("parsedUrl : " + parsedUrl);
						String normalizedUlr = findNormalizedUrl(parsedUrl, crawledRawData);
						logger.debug("Normalized URL : " + normalizedUlr);
						if (StringUtils.isBlank(normalizedUlr)) {
							logger.debug("Denied. Because normalizedUrl is blank");
							continue;
						}
						if (!isValidURL(normalizedUlr)) {
							logger.debug("normalizedUlr url is not valid one : " + parsedUrl);
							continue;
						}
												
						icoExtractedData.setIcoValue(normalizedUlr);
						findCSOConcept(normalizedUlr);						
						icoExtractedData = findUrlProperties(normalizedUlr, icoExtractedData);
					} 
					
					// For only 'Resolution Summary' contain in the case_notes text, then only we have to set bugs, linked case id and hardware details
					icoExtractedData.setCaseNoteType(crawledRawData.getSrCaseNoteType());
					// CDETS
					if (StringUtils.equals(icoRegex.getIcoType(), Constants.ICO_TYPE_CDETS)) {
						// if icos occurred more than 5 times no need to process any more
						if (c3CaseIdIcosForRegex.size() >= Constants.ICO_MAX_IN_SINGLE_TEXT) continue;
						 // No need to store bug ico for case notes text not contain the word 'Resolution Summary
						if (!StringUtils.containsIgnoreCase(crawledRawData.getSrCaseNotesText(), Constants.RESOLUTION_SUMMARY)) continue;						
						icoExtractedData.setCaseNoteType(Constants.RESOLUTION_SUMMARY_CASE_NOTE_TYPE);
					}

					// RFC
					if (StringUtils.equals(icoRegex.getIcoType(), Constants.ICO_TYPE_RFC)) {
						String rfcIcoValue = cleanRfcIcoValue(icoExtractedData.getIcoValue());
						icoExtractedData.setIcoValue(rfcIcoValue);
					}
					
					// Title setting for all Icos except pid, sw and serial no
					icoExtractedData.setIcoTitle(findIcoTitle(icoRegex.getIcoType(), icoExtractedData.getIcoValue()));
					
					// C3CASE
					if (StringUtils.equals(icoRegex.getIcoType(), Constants.ICO_TYPE_C3_CASE)) {
						// CaseId and IcoValue should not be same
						if (StringUtils.equals(crawledRawData.getCaseId(), icoExtractedData.getIcoValue())) continue;
						// If title is blank, then that c3 case id is not valid
						if (StringUtils.isBlank(icoExtractedData.getIcoTitle())) continue;
					}
					
					icoExtractedData = setIcoExtractedDataValues(icoExtractedData, crawledRawData, icoRegex.getIcoType());
					// Store into scim db 'Table : c3_extracted_ico'
					saveIcoExtractedData(icoExtractedData, crawledRawData );
					logger.info("Regex icoExtractedData : " + icoExtractedData.getIcoValue() + " Saved successfully");
				}
				
				logger.info("***ICO extraction completed for : " + icoRegex.getIcoType());
			}

			// No need to store PID and SW ico for case notes text not contain the word 'Resolution Summary'
			if (StringUtils.containsIgnoreCase(crawledRawData.getSrCaseNotesText(), Constants.RESOLUTION_SUMMARY)) {
				logger.info("*** Linked Case ID, Linked Bugs, SW and PID ICO extraction started for rawdata record");
				//TssIncidentsCurrentFV tssIncidentsCurrentFV = c3blDAO.findC3blCrawledRawDataIncidentNo(crawledRawData.getCaseId());
				//if (!ObjectUtils.equals(tssIncidentsCurrentFV, null)) {
					findLinkedCaseIdAndLinkedCDETS(crawledRawData);
					generateAndSaveHWDetailsIco(crawledRawData);
					generateAndSaveSWDetailsIco(crawledRawData);
					generateAndSaveLinkedCaseIdIco(crawledRawData);	
					generateAndSaveLinkedCDETSIco(crawledRawData);
					generateAndSaveSerialNoIco(crawledRawData);
				//}
					// Update neo4j node 
					try {
						//String NEO4J_UPDATE_CASENOTE_ID_ICO_URL = "http://scim:7474/rest/casenote/casenoteid/351854527";
						String url = Constants.NEO4J_UPDATE_CASENOTE_ID_ICO_URL+crawledRawData.getCaseNoteId();
						PostToUrl postTo = new PostToUrl();
						logger.info("Neo4j updating the node url  : " + url);
						postTo.newDataPOST(url);
						
					} catch (Exception e) {
						logger.warn("Exception catched when update neo4j node : " + e);
					}
				logger.info("*** Linked Case ID's Sw and PID ICO extraction completed for rawdata record");
			} else {
				logger.info("*** Linked Case ID's Sw and PID ICO extraction shoud process for Resolved case for rawdata records. this is not resolved case");
			}

			logger.info("*** Case note's SW ICO parsing started");
			Set<String> c3CaseIdIcosForSW = findIcosForSW(crawledRawData.getSrCaseNotesText());
			if ((!ObjectUtils.equals(c3CaseIdIcosForSW, null)) && (c3CaseIdIcosForSW.size() > 0)) {
			for (String swIco : c3CaseIdIcosForSW) {
				IcoExtractedData icoExtractedData = new IcoExtractedData();
				icoExtractedData.setIcoValue(swIco);
				logger.info("swIco icoExtractedData values are setting for : " + swIco);
				icoExtractedData.setIcoTitle(swIco);
				icoExtractedData.setCaseNoteType(Constants.RESOLUTION_SUMMARY_CASE_NOTE_TYPE);
				icoExtractedData = setIcoExtractedDataValues(icoExtractedData, crawledRawData, Constants.ICO_TYPE_SW_IMAGE);
				// Store into scim db 'Table : c3_extracted_ico' for SW
				saveIcoExtractedData(icoExtractedData, crawledRawData );
				logger.info("swIco icoExtractedData : " + icoExtractedData.getIcoValue() + " Saved successfully");
			}
			} else {
				logger.info("There is no 'SW-IMAGE-NAMES' ico for caseNoteId : " + crawledRawData.getCaseNoteId());
			}
			logger.info("*** Case note's SW ICO parsing completed");
			
			//Step3 : For PID
			logger.info("*** Case note's PID ICO parsing started");
			//Step2 : For PID : Not required this process for 'Resolution Summary' text not contained case notes text
			Set<String> c3CaseIdIcosForPID = findIcosForPID(crawledRawData.getSrCaseNotesText());
			if ((!ObjectUtils.equals(c3CaseIdIcosForPID, null)) && (c3CaseIdIcosForPID.size() > 0)) {
			for (String pidIco : c3CaseIdIcosForPID) {
				IcoExtractedData icoExtractedData = new IcoExtractedData();
				logger.info("PID Ico icoExtractedData values are setting for : " + pidIco);
				icoExtractedData.setIcoValue(pidIco);
				icoExtractedData.setIcoTitle(Constants.PID_PRODUCT_NUMBERS.get(pidIco));
				icoExtractedData.setCaseNoteType(Constants.RESOLUTION_SUMMARY_CASE_NOTE_TYPE);
				icoExtractedData = setIcoExtractedDataValues(icoExtractedData, crawledRawData, Constants.ICO_TYPE_PID);
				// Store into scim db 'Table : c3_extracted_ico' for PID
				saveIcoExtractedData(icoExtractedData, crawledRawData );
				logger.info("pidIco icoExtractedData : " + icoExtractedData.getIcoValue() + " Saved successfully");
			}
			} else {
				logger.info("Case note's haven't 'PID' ico for caseNoteId : " + crawledRawData.getCaseNoteId());
			}
			logger.info("*** Case note's PID ICO parsing completed");
		}
	}
	
	private String findRemovedNewlineCharUrl(String parsedUrl, String notes, String urlRegex) {
		logger.debug("Inside findRemovedInlineCharUrl method with url : " + parsedUrl);
		
		String url = parsedUrl;		
		
		for (String urlExtn : Constants.URL_EXTENSIONS) {
			if (StringUtils.containsIgnoreCase(parsedUrl, urlExtn)) {
				logger.debug("Fetched url from case note text is good.");
				return parsedUrl;
			}
		}
		
		logger.debug("Fetched url from case note text is not fully.");
		
		int startIndex = StringUtils.indexOf(notes, parsedUrl);
		String subString = null;
		if (startIndex > -1) {
			if (startIndex < notes.length())
			subString = StringUtils.substringAfterLast(notes, parsedUrl);
			if (!StringUtils.isBlank(subString)) subString = StringUtils.trim(subString);
			int subStringEndIndex = StringUtils.indexOf(subString, "\n");
			if (subStringEndIndex > 0);
			subString = StringUtils.substring(subString, 0, subStringEndIndex);
		}
		int endIndex = -1;
		for (String urlExtn : Constants.URL_EXTENSIONS) {
			endIndex = StringUtils.indexOf(subString, urlExtn);
			if (endIndex > -1) {
				String remainingUrl = StringUtils.substring(subString, 0,endIndex) + urlExtn;
				logger.debug("Remaining url : " + remainingUrl);
				url = url + remainingUrl;
				if (url.length() > 355) return url = parsedUrl;
			}
		}
		
		Map<String, Integer> urlsMap = findRegexMatchedResult(url, urlRegex, false);
		for (Map.Entry<String, Integer> urlEntry : urlsMap.entrySet()) {
			if (!StringUtils.isBlank(urlEntry.getKey())) {
				url = urlEntry.getKey();
				break;
			}
		}
		
		return url;		
	}
	
	
	private IcoExtractedData setIcoExtractedDataValues(IcoExtractedData icoExtractedData, C3RawData crawledRawData, String icoType) {
		logger.debug("Inside setIcoExtractedDataValues method ...with crawledRawData : " + crawledRawData.getCaseId() + " icoType : " + icoType);
		
		icoExtractedData.setSource(Constants.SOURCE_C3);
		icoExtractedData.setC3CaseId(crawledRawData.getCaseId());
		icoExtractedData.setIcoType(icoType);		
		icoExtractedData.setCaseNoteDate(crawledRawData.getSrCaseNoteTimestamp());
		icoExtractedData.setC3CaseTitle(crawledRawData.getTitle());
		icoExtractedData.setCsoneCaseId(crawledRawData.getCsOneId());
		icoExtractedData.setTechId(crawledRawData.getTechId());
		icoExtractedData.setSubTechId(crawledRawData.getSubTechId());
		icoExtractedData.setTechIdName(crawledRawData.getTechName());
		icoExtractedData.setSubTechIdName(crawledRawData.getSubTechName());
		icoExtractedData.setContact(crawledRawData.getContact());
		icoExtractedData.setContractId(crawledRawData.getContractId());
		icoExtractedData.setCaseNoteId(crawledRawData.getCaseNoteId());
		icoExtractedData.setTacEngineer(crawledRawData.getSrCaseNoteAuthor());
		icoExtractedData.setC3WorkgroupTacEngineer(crawledRawData.getSrCaseNoteWorkgroup());		
		icoExtractedData.setWorkgroupId(crawledRawData.getWorkgroupId());
		
		if (StringUtils.containsIgnoreCase(crawledRawData.getSrCaseNotesText(), Constants.RESOLUTION_SUMMARY))
			icoExtractedData.setSerialNumber(crawledRawData.getSerialNumber());
		
		return icoExtractedData;
	}
	
	@Override
	public Set<String> findIcosForSW(String notes) {
		logger.debug("Inside findIcosForSW method ...with notes");
		
		logger.debug("size of swImages from constants : " +Constants.SW_IMAGE_FILE_NAMES.size());
		if (StringUtils.isBlank(notes)) return null;
		
		Set<String> icosForSW = new HashSet<String>();
		try{
		for (String swImageFileName : Constants.SW_IMAGE_FILE_NAMES) {
			if (StringUtils.isBlank(swImageFileName)) continue;
			if (StringUtils.contains(notes, swImageFileName)) {
				logger.debug("Found SWI name : " + swImageFileName);
				if ((StringUtils.isBlank(swImageFileName)) || (swImageFileName.length() < 6)) {
					logger.debug("sw Ico's length should be greater than 6 : " + swImageFileName);
					continue;
				}
				icosForSW.add(swImageFileName);
			}
		}
		}catch(Exception e){
			logger.info("Exception when finding ICO for SW Image : " + e);
		}

		return icosForSW;		
	}
	
	/*
	 * Using single as  one regex
	 */
	/*public Set<String> findIcosForPID(String notes) {
		logger.debug("Inside findIcosForPID method ...with notes");
		
		logger.debug("size of Pid from constants : " + Constants.PID_PRODUCT_NUMBERS.size());
		if (StringUtils.isBlank(notes)) return null;
		
		Set<String> icosForPID = new HashSet<String>();
		try{
		for (String productNumber : Constants.PID_PRODUCT_NUMBERS.keySet()) {
			if (StringUtils.isBlank(productNumber)) continue;
			
			productNumber = Pattern.quote(productNumber);
			
			String pidRegex = "("+productNumber+"\\s)|(\\s"+productNumber+"\\s)|(\\s"+productNumber+"\n)";
			Map<String, Integer> pidMap = findRegexMatchedResult(notes, pidRegex, true);
			
			if (StringUtils.contains(notes, productNumber)) {
				logger.debug("Found PID : " + productNumber);				
				for (Map.Entry<String, Integer> pidsEntry : pidMap.entrySet()) {					
					if (pidsEntry.getValue() >= Constants.ICO_MAX_IN_SINGLE_TEXT) continue;
					icosForPID.add(productNumber);
					break;
				}
			}
		}
		}catch(Exception e){
			logger.info("Exception when finding ICO for PID : " + e);
		}
		
		return icosForPID;
	}*/
	
	/* 
	 * Using Set of regex
	 * @see com.cisco.swtg.scim.service.C3CrawelService#findIcosForPID(java.lang.String)
	 */
	/*@Override
	public Set<String> findIcosForPID(String notes) {
		logger.debug("Inside findIcosForPID method ...with notes");

		logger.debug("size of Pid from constants : " + Constants.PID_PRODUCT_NUMBERS.size());
		if (StringUtils.isBlank(notes))
			return null;

		Set<String> icosForPID = new HashSet<String>();
		try {
			for (String pidRegex : Constants.PID_PRODUCT_NUMBER_REGEXs) {
				if (StringUtils.isBlank(pidRegex)) continue;
				// productNumber = Pattern.quote(productNumber);
				// String pidRegex =
				// "("+productNumber+"\\s)|(\\s"+productNumber+"\\s)|(\\s"+productNumber+"\n)";
				Map<String, Integer> pidMap = findRegexMatchedResult(notes,
						pidRegex, true);
				for (Map.Entry<String, Integer> pidsEntry : pidMap.entrySet()) {
					icosForPID.add(pidsEntry.getKey());
				}
			}

		} catch (Exception e) {
			logger.info("Exception when finding ICO for PID : " + e);
		}

		return icosForPID;
	}*/
	
	
	/*
	 * Using StringTokenizer
	 */
	@Override
	public Set<String> findIcosForPID(String notes) {
		logger.debug("Inside findIcosForPID method ...with notes");

		logger.debug("size of Pid from constants : " + Constants.PID_PRODUCT_NUMBERS.size());
		if (StringUtils.isBlank(notes))
			return null;

		Set<String> icosForPID = new HashSet<String>();
		StringTokenizer stringTokenizer = new StringTokenizer(notes);		
		while(stringTokenizer.hasMoreTokens()) { 
			String pid = stringTokenizer.nextToken(); 			
			String pidDesc = Constants.PID_PRODUCT_NUMBERS.get(pid);
			if (!StringUtils.isBlank(pidDesc)) {
				icosForPID.add(pid);
				logger.debug("PID found : " + pid + " : desc : " + pidDesc);
			}
		} 		
		logger.debug("Inside findIcosForPID method completed");
		return icosForPID;
	}
	
	@Override
	public void saveRejectedUrlList(RejectedUrlList rejectedUrlList, Object rawDataObject) {
		if (rawDataObject instanceof C3RawData) { 
			C3RawData c3RawData = (C3RawData)rawDataObject;
			saveC3RejectedUrlList(rejectedUrlList, c3RawData);
		} else if (rawDataObject instanceof CSCRawData) { 
			CSCRawData cSCRawData = (CSCRawData)rawDataObject;
			saveCSCRejectedUrlList(rejectedUrlList, cSCRawData);
		}
	}
	
	@Override
	public void saveC3RejectedUrlList(RejectedUrlList rejectedUrlList, C3RawData c3RawData) {
		rejectedUrlList.setSource(Constants.SOURCE_C3);
		rejectedUrlList.setC3CaseNoteId(c3RawData.getCaseNoteId());
		saveRejectedUrlList(rejectedUrlList);
		
	}

	@Override
	public void saveCSCRejectedUrlList(RejectedUrlList rejectedUrlList, CSCRawData cSCRawData) {
		rejectedUrlList.setSource(Constants.SOURCE_CSC);
		rejectedUrlList.setCscMessageId(cSCRawData.getMessageId());
		saveRejectedUrlList(rejectedUrlList);
	}
	
	@Override
	public void saveRejectedUrlList(RejectedUrlList rejectedUrlList) {
		rejectedUrlList.setRejectedCount(1);
		RejectedUrlList rejectedUrlFromScim = crawlDAO.findRejectedUrlListByUrl(rejectedUrlList.getIcoValue());
		if (!ObjectUtils.equals(rejectedUrlFromScim, null)) {			
			rejectedUrlFromScim.setRejectedCount(rejectedUrlFromScim.getRejectedCount() + 1);
			rejectedUrlList = rejectedUrlFromScim;
		}
		rejectedUrlList.setLastRejectedDate(new Date());
		crawlDAO.saveRejectedUrlList(rejectedUrlList);
	}
	
	@Override
	public String findNormalizedUrl(String url, Object rawDataObject) {
		logger.debug("Inside findNormalizedUrl method ...with url : " + url);
		
		String normalizedUrl = url;
				
		// Rule 1.Redirected url
		logger.debug("Rule 1 : Finding the Destination URL ");
		normalizedUrl  = findFinalDestinationUrl(normalizedUrl, 0);

		// Rule 2.Converting tiny url into fully qualified url
		logger.debug("Rule 2 : Convert tiny url to fully qualified url");
		normalizedUrl = convertTinyToFullUrl(normalizedUrl);
		
		// Rule 3.Truncate named anchors from the url
		logger.debug("Rule 3.Truncate named anchors from the url");
		normalizedUrl = trunkcateNamedAnchorsAndQueryStr(normalizedUrl);
		
		// Rule 4.Remove entitlement from the url 
		logger.debug("Rule 4.Remove entitlement from the url");
		normalizedUrl = removeEntitlementFromUrl(normalizedUrl);
		
		// Rule 5.Convert ShortHand URL into Fully Qualified URL 
		logger.debug("Rule 5.Convert ShortHand URl into Fully Qualified URL");
		normalizedUrl = convertShortHandUrlToFullUrl(normalizedUrl);
		
		// Rule 6. If url ends with '/', have to add 'index.html'. Then verify its active or inactive status
		normalizedUrl = cleanEndsWithSlashUrl(normalizedUrl);		
		
		//Rule 6.1 Add http if not starts with
		normalizedUrl = addHttpInStart(normalizedUrl);	
		
		// Rule 7.Finding is denied or permitted url
		logger.debug("Rule 6.Finding is denied or permitted url");
		if ((findIsDeniedUrl(normalizedUrl)) || isUrlNotEndsWithProperly(normalizedUrl)) {
			logger.debug("Url is rejected because denied pattern or not ends with properly : " + url);
			RejectedUrlList rejectedUrlList = new RejectedUrlList();
			rejectedUrlList.setErrorCode("Denied");
			rejectedUrlList.setIcoValue(normalizedUrl);			
			saveRejectedUrlList(rejectedUrlList, rawDataObject);
			return null;
		}
		
		// Rule 8. Finding the is url is active or inactive. If inactive save rejected url with error code
		int code = findErrorCodeForURL(normalizedUrl, Constants.CISCO_USERID, Constants.CISCO_PASSWORD);
		if ((StringUtils.startsWith(String.valueOf(code), "2")) || (StringUtils.startsWith(String.valueOf(code), "3")))
			return normalizedUrl;
		else {
			logger.debug("Url is rejected because errorcode : " + code + " : Description : " + Constants.URLEXCEPTIONS.get(code));
			RejectedUrlList rejectedUrlList = new RejectedUrlList();
			rejectedUrlList.setIcoValue(normalizedUrl);
			rejectedUrlList.setErrorCode(code + "--" + Constants.URLEXCEPTIONS.get(code));
			saveRejectedUrlList(rejectedUrlList, rawDataObject);
			return null;
		}
	}
	
	/*
	 * Url should ends with '/' or ''htm' or 'shtml' or 'html' or 'pdf' 
	 */
	@Override
	public boolean isUrlNotEndsWithProperly(String url) {
		logger.debug("Inside isUrlNotEndsWithProperly method ...with tiny url : " + url);
		
		if (StringUtils.isBlank(url)) return true;
		if (StringUtils.endsWith(url, "/")) return false;
		for (String exten : Constants.URL_EXTENSIONS) {
			if (StringUtils.endsWith(url, exten)) return false;
		}
		return true;		
	}
	
	@Override
	public int findErrorCodeForURL(String urlStr, String userId, String password) {
		logger.debug("Inside findErrorCodeForURL method with urlStr : " + urlStr); 
		
		int code=0;

		try {
	    	URL url = new URL(urlStr);
	        HttpURLConnection uc=(HttpURLConnection)url.openConnection();
	        if ((!StringUtils.isBlank(userId)) && (!StringUtils.isBlank(password)))
	        uc.setRequestProperty( "Authorization", "Basic " + new sun.misc.BASE64Encoder().encode((userId + ":" + password).getBytes()));
	        code=uc.getResponseCode();	        
	    }
	    catch(BindException e) {
	    	code=4;
	    	logger.debug("BindException. So code : " + code);	        
	    }
	    catch(ConnectException e) {
	        code=5;
	        logger.debug("ConnectException. So code : " + code);
	    }
	    catch(HttpRetryException e) {
	        code=6;
	        logger.debug("HttpRetryException. So code : " + code);
	    }
	    catch(MalformedURLException e) {
	        code=7;
	        logger.debug("MalformedURLException. So code : " + code);
	    }
	    catch(NoRouteToHostException e) {
	        code=8;
	        logger.debug("NoRouteToHostException. So code : " + code);
	    }
	    catch(PortUnreachableException e) {
	        code=9;
	        logger.debug("PortUnreachableException. So code : " + code);
	    }
	    catch(ProtocolException e) {
	        code=10;
	        logger.debug("ProtocolException. So code : " + code);
	    }
	    catch(SocketException e) {
	        code=8;
	        logger.debug("SocketException. So code : " + code);
	    }
	    catch(SocketTimeoutException e) {
	        code=11;
	        logger.debug("SocketTimeoutException. So code : " + code);
	    }
	    catch(UnknownHostException e) {
	        code=12;
	        logger.debug("UnknownHostException. So code : " + code);
	    }
	    catch(UnknownServiceException e) {
	        code=13;
	        logger.debug("UnknownServiceException. So code : " + code);
	    }
	    catch(IOException e) {
	        code=14;
	        logger.debug("IOException. So code : " + code);
	    }
	    
		return code;
	}
	
	
	/**
	 * Checks whether the given URL is valid.
	 * @param url represents the website address. 
	 * @return true if the url is valid,false otherwise.
	 */
	public boolean isValidURL(String url) {
		if (url == null) {
			return false;
		} 
		String[] schemes = { "http", "https" };
		UrlValidator urlValidator = new UrlValidator(schemes);
		if (urlValidator.isValid(url)) return true;
		else return false;
	}
	
	@Override
	public String findFinalDestinationUrl(String url, int count) {
		logger.debug("Inside findFinalDestinationUrl method ...with url : " + url);		
		String metaRefreshRegex = Constants.META_REFRESH_REGEX;
		
		String urlContent = cSCCrawelService.readUrl(url, null, null);
		
		Map<String, Integer> refreshUrls = findRegexMatchedResult(urlContent, metaRefreshRegex, false);
		if ((!ObjectUtils.equals(refreshUrls, null)) || (refreshUrls.size() > 0)) {
			for (Map.Entry<String, Integer> entry : refreshUrls.entrySet()) {
				logger.info(url + " is redirecting to : " );
				String metaTagUrl = entry.getKey();
				
				// find the url from meta tag starts here 
				Map<String, Integer> metaTagUrls = findRegexMatchedResult(urlContent, "\\(?\\b(http://|www[.])[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]", false);//TODO: Remove hard code
				for (Map.Entry<String, Integer> entryMetaTag : metaTagUrls.entrySet()) {
					metaTagUrl = entryMetaTag.getKey();
				}
				if ((StringUtils.equals(url, metaTagUrl)) || (count > 5)) return url;
				url = metaTagUrl;
				// find the url from meta tag ends here 
				logger.info(url);
				findFinalDestinationUrl(url, count++);
				break;
			}
		} else {
			logger.info("Final Destination url is : " + url);
			return url;
		}
		
		return url;
	}
	
	@SuppressWarnings("null")
	@Override
	public boolean findIsActiveUrl(String url) {
		logger.debug("Inside findIsActiveUrl method ...with url : " + url);
		boolean isActiveUrl = false;

		HttpURLConnection httpURLConnection = null;

		try {
			httpURLConnection.setConnectTimeout(5000);
			httpURLConnection.setReadTimeout(5000);
			httpURLConnection.connect();
			if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				isActiveUrl = true;
				logger.debug("Active url because able to Connected with the url : " + url);
			}
		} catch (Exception e) {
			logger.debug("Url is not Active because Exception when connect the url : " + url + " : " + e);
			isActiveUrl = false;
		}

		return isActiveUrl;
	}
	
	@SuppressWarnings("unused")
	@Override
	public String convertTinyToFullUrl(String tinyUrl) {
		logger.debug("Inside convertTinyToFullUrl method ...with tiny url : " + tinyUrl);
		
		if (StringUtils.isBlank(tinyUrl)) {
			logger.debug("tinyUrl should not be blank : " + tinyUrl);
			return tinyUrl;
		}
		
		String fullUrl = tinyUrl;
		try {
			HttpURLConnection.setFollowRedirects(false);
			URL url = new URL(tinyUrl);
			URLConnection connection = url.openConnection();
			//fullUrl = connection.getHeaderField("Location");
			//logger.debug("Successfully converted tiny url : " +  tinyUrl + " into full url : " + fullUrl);
		} catch (Exception e) {
			logger.debug("Exception catch when convert tiny to full url : " + tinyUrl + " : " + e);
		}
		return fullUrl;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public String trunkcateNamedAnchorsAndQueryStr(String url) {
		logger.debug("Inside trunkcateNamedAnchorsAndQueryStr method ...with url : " + url);
		
		if (StringUtils.isBlank(url)) {
			logger.debug("url should not be blank : " + url);
			return url;
		}
			
		// 1. Remove query String
		//1.1 Remove after extension
		for (String extn : Constants.URL_EXTENSIONS) {			
			int index = url.indexOf(extn);
			if (index > -1) {
				url = url.substring(0, index);
				url = url + extn;
				break;
			}
		}
		// 1.2 After question(?) mark
		int index = url.indexOf('?');
		if (index > -1) url = url.substring(0, index);
		
		// 2. Remove anchors
		index = url.indexOf('#');
		if (index > -1) url = url.substring(0, index);
		
		// 3. decode
		try {
			url = URLDecoder.decode(url);
		} catch (Exception e) {
			logger.info("Exception occurred when decode the url : " + e + " : url  : " + url);
		}
		
		logger.debug("trunkcateNamedAnchorsAndQueryStr url : " + url);
		
		return url;
	}

	
	
	@Override
	public String addHttpInStart(String url) {
		logger.debug("Inside addHttpInStart method ...with url : " + url);
		
		if (StringUtils.isBlank(url)) return url;

		if (StringUtils.startsWith(url, "http")) return url;
		else url = "http://"+url;
		
		logger.debug("addHttpInStart with url : " + url);
		
		return url;
	}
	
	
	@Override
	public String cleanEndsWithSlashUrl(String url) {
		logger.debug("Inside cleanEndsWithSlashUrl method ...with url : " + url);
		
		if (StringUtils.isBlank(url)) return url;

		if (StringUtils.endsWith(url, "/")) { 
			String urlWithIndex = url + "index.html"; //TODO: Remove hardcode
			
			int code = findErrorCodeForURL(urlWithIndex, Constants.CISCO_USERID, Constants.CISCO_PASSWORD);
			if ((StringUtils.startsWith(String.valueOf(code), "2")) || (StringUtils.startsWith(String.valueOf(code), "3")))
				return urlWithIndex;
			else return url;
			
		}
		
		return url;
	}
	
	@Override
	public String removeEntitlementFromUrl(String url) {
		logger.debug("Inside removeEntitlementFromUrl method ...with url : " + url);
		
		if (StringUtils.isBlank(url)) {
			logger.debug("url should not be blank : " + url);
			return url;
		}

		for (String entitlement : Constants.ENTITLEMENTS) {
			//url = StringUtils.replace(url, "/"+entitlement+"/", "/");
			url = StringUtils.replace(url, "/en/US/"+entitlement+"/", "/en/US/");
		}
		
		logger.debug("Entitlement removed url is : " + url);
		
		return url;
	}
	
	
	public String cleanRfcIcoValue(String rfcIcoValue) {		
		logger.debug("Inside cleanRfcIcoValue method with : " + rfcIcoValue);
		
		String icoValue = rfcIcoValue;
		
		if (StringUtils.isBlank(icoValue)) return icoValue;
		
		icoValue = StringUtils.trim(icoValue);
		icoValue = StringUtils.lowerCase(icoValue);
		icoValue = StringUtils.replace(icoValue, " ", "");
		
		logger.debug("Cleaned rfcIcoValue is : " + icoValue);
		
		return icoValue;
	}
	
	@Override
	public String findTitleFromHtmlUrl(String urlStr, String userId,
			String password) {		
		logger.debug("Inside findTitleFromHtmlUrl method with url : " + urlStr + " : userid " + userId + " :  pwd : " + password);

		String title = Constants.NO_TITLE_MSG;
		
		try {
			URL url = new URL(urlStr);
			URLConnection uc = url.openConnection();
			if ((!StringUtils.isBlank(userId))
					&& (!StringUtils.isBlank(password)))
				uc.setRequestProperty(
						"Authorization",
						"Basic "
								+ new sun.misc.BASE64Encoder().encode((userId
										+ ":" + password).getBytes()));

			Source source = new Source(uc);

			Element titleElement = source
					.getFirstElement(HTMLElementName.TITLE);

			if (titleElement == null)
				return title; 
			
			logger.debug("TitleElement : " + titleElement +  " : for url " + urlStr);

			title =  CharacterReference.decodeCollapseWhiteSpace(titleElement
					.getContent());
		} catch (Exception e) {
			logger.info("*Imp : Exception when try to find the title for : " + urlStr);
			logger.info("Exception : " + e);
		}

		return title;
	}


	@Override
	public String findTitleFromHtmlContent(String content) {		
		logger.debug("Inside findTitleFromHtmlContent method with content");

		String title = Constants.NO_TITLE_MSG;
		if (StringUtils.isBlank(content)) return title;
		
		try {
			InputStream is = new ByteArrayInputStream(content.getBytes());
			Source source = new Source(is);
			Element titleElement = source.getFirstElement(HTMLElementName.TITLE);

			if (titleElement == null) return title;
			
			logger.debug("TitleElement : " + titleElement);
			title =  CharacterReference.decodeCollapseWhiteSpace(titleElement
					.getContent());
			
		} catch (Exception e) {
			logger.info("Exception findTitleFromHtmlContent method : " + e);
		}
		
		return title;
	}
	
	
	@Override
	public String convertShortHandUrlToFullUrl(String url) {
		logger.debug("Inside convertShortHandUrlToFullUrl method ...with url : " + url);
		
		if (StringUtils.isBlank(url)) {
			logger.debug("tinyUrl should not be blank : " + url);
			return url;
		}
		
		String regexForUptoShortHand = "\\(?\\b(http://|www[.])";//TODO: Remove hardcode
		String fullUrl = url;

		List<UrlShortHand> shortHandUrls = crawlDAO.findAllUrlShortHand();
		if ((ObjectUtils.equals(shortHandUrls, null)) || (shortHandUrls.size() < 1)) {
			logger.debug("There is no shorthand url available in the scimdb");
			return url;
		}
		
		for (UrlShortHand shortHandUrl : shortHandUrls) {
			logger.debug("Processing for Shorthand server : " + shortHandUrl.getShortHandServerName());
			regexForUptoShortHand = regexForUptoShortHand + "\\Q" + shortHandUrl.getShortHandServerName() + "\\E";
			Map<String, Integer> matchedShortHandUrls = findRegexMatchedResult(url, regexForUptoShortHand, false);
			if ((ObjectUtils.equals(matchedShortHandUrls, null)) || (matchedShortHandUrls.size() < 1)) {
				logger.debug("There is no shorhand server name in url : " + url );
				continue;
			}
			for (Map.Entry<String, Integer> entry : matchedShortHandUrls.entrySet()) {				
				String shortHandUrlFromUrl = entry.getKey();
				String shortHandUrlToFullQUrl = StringUtils.replace(shortHandUrlFromUrl, shortHandUrl.getShortHandServerName(), shortHandUrl.getQualifiedServerName());
				logger.debug("Converting shor hand server name : " + shortHandUrlFromUrl + " into qualified server name : " + shortHandUrlToFullQUrl);
				fullUrl = StringUtils.replace(fullUrl, shortHandUrlFromUrl, shortHandUrlToFullQUrl);
				logger.debug("Successfully Converted shorthand server name url into full qualified server name url: " + fullUrl);
				return fullUrl;
			}
		}
				
		return fullUrl;
	}

	@Override
	public boolean findIsDeniedUrl(String url) {
		logger.debug("Inside findIsDeniedUrl method ...with url : " + url);
		
		boolean isDenied = false;
		
		if (StringUtils.isBlank(url)) {
			logger.debug("url should not be blank : " + url +  ". So this url denied");
			isDenied = true;
		}
		
		List<IcoDenyPermtPattern> urlDenyPermtPatterns = crawlDAO.findIcoDenyPermtPatternByIcoType(Constants.ICO_TYPE_URL);
		
		if ((ObjectUtils.equals(urlDenyPermtPatterns, null)) || (urlDenyPermtPatterns.size() < 1)) {
			logger.debug("There is no urlDenyPermtPatterns url available in the scimdb. So all url patterns are not denied");
			isDenied = false;
		}
		
		for (IcoDenyPermtPattern urlDenyPermtPattern : urlDenyPermtPatterns) {
			if (StringUtils.equals(urlDenyPermtPattern.getIsPermit(), "Y")) continue;
			
			if (StringUtils.equals(urlDenyPermtPattern.getIsRegex(), "Y")) {
				if (StringUtils.containsIgnoreCase(url, urlDenyPermtPattern.getIcoPattern())) isDenied = true;				
			} else if (StringUtils.equals(urlDenyPermtPattern.getIsRegex(), "N")) {
				if (StringUtils.equalsIgnoreCase(url, urlDenyPermtPattern.getIcoPattern())) isDenied = true;
				if (StringUtils.equalsIgnoreCase(url, urlDenyPermtPattern.getIcoPattern())) isDenied = true;
				if (StringUtils.equalsIgnoreCase(url, "http://"+urlDenyPermtPattern.getIcoPattern())) isDenied = true;
				if (StringUtils.equalsIgnoreCase(url, "https://"+urlDenyPermtPattern.getIcoPattern())) isDenied = true;
			}
		}

		boolean isPermit = false;
		if (!isDenied) {
		for (IcoDenyPermtPattern urlDenyPermtPattern : urlDenyPermtPatterns) {
			if (StringUtils.equals(urlDenyPermtPattern.getIsPermit(), "Y")) {
				if (StringUtils.containsIgnoreCase(url, urlDenyPermtPattern.getIcoPattern())) isPermit = true;
			}
		}
		return !isPermit;
		}
		 
		return isDenied;
	}
	
	
	
	@Override
	public boolean findIsDeniedIco(String icoValue, String icoType) {
		logger.debug("Inside findIsDeniedIco method ...with ico : " + icoValue + " :  icoType : " + icoType);

		boolean isDenied = false;
		
		if ((StringUtils.isBlank(icoValue)) || (StringUtils.isBlank(icoType))) {
			logger.debug("icoValue and icoType should not be blank. So this ico denied : " + icoValue);
			isDenied = true;
		}
		
		List<IcoDenyPermtPattern> icoDenyPatterns = crawlDAO.findIcoDenyPatternByIcoType(icoType);
		
		if ((ObjectUtils.equals(icoDenyPatterns, null)) || (icoDenyPatterns.size() < 1)) {
			logger.debug("There is no icoDenyPatterns available in the scimdb for icoType : " + icoType);
			isDenied = false;
		}
		
		for (IcoDenyPermtPattern icoDenyPattern : icoDenyPatterns) {
			if (StringUtils.equals(icoDenyPattern.getIsRegex(), "Y")) {
				if (StringUtils.containsIgnoreCase(icoValue, icoDenyPattern.getIcoPattern())) isDenied = true;				
			} else if (StringUtils.equals(icoDenyPattern.getIsRegex(), "N")) {
				if (StringUtils.equalsIgnoreCase(icoValue, icoDenyPattern.getIcoPattern())) isDenied = true;
			}
		}
	 
		return isDenied;
	}
	
	@Override
	public IcoExtractedData findUrlProperties(String normalizedUlr, IcoExtractedData icoExtractedData) {
		logger.debug("Inside findUrlProperties method ...with url : " + normalizedUlr);
		
		String hostName = null;
		IcoUrlServerName icoUrlServerName = null;
		try {
			URL url = new URL(normalizedUlr);
			hostName = url.getHost();
			logger.debug("Founded host name : " + hostName);
		} catch(Exception e) {
			logger.info("Exception when finding the host name : " + normalizedUlr + " : " + e);
		}
		
		if (!StringUtils.isBlank(hostName))
			icoUrlServerName = crawlDAO.findIcoUrlServerNameByServerName(hostName);
		if (ObjectUtils.equals(icoUrlServerName, null)) {
			logger.debug("Cant find the ico url server in the DB for  : " + hostName);
			return icoExtractedData;
		}
		
		icoExtractedData.setIcoUrlServerName(icoUrlServerName.getServerName());
		icoExtractedData.setIcoUrlDomain(icoUrlServerName.getIcoUrlDomain());
		icoExtractedData.setIcoUrlAccessibility(icoUrlServerName.getIcoUrlAccessibility());
		
		return icoExtractedData;	
	}
	
	@Override
	public String findPDFTtitleByURL(String urlStr) {
		logger.debug("Inside findPDFTtitleByURL method ...with url : " + urlStr);
		String title = Constants.NO_TITLE_MSG;
		PDDocument pdfDocument = null;
		try {
			pdfDocument = new PDDocument();	
			URL url = new URL(urlStr);
			pdfDocument = PDDocument.load(url);
			PDDocumentInformation pdfInfo = pdfDocument.getDocumentInformation();
			title = pdfInfo.getTitle();
			if (StringUtils.isBlank(title)) title = Constants.NO_TITLE_MSG;
			pdfDocument.close();
		} catch (Exception e) {
			logger.info("Exception when try to fetch tile from pdf url : " + urlStr + " : " + e);			
		}

		return title;
	}
	
	@Override
	public String cleanCdetsTitle(String cdetsTitle, String icoValue) {
		logger.debug("Inside cleanCdetsTitle method with : " + cdetsTitle);
		
		String cdetsTitleStartRegex = "CSC[a-z]{2}[0-9]{5}\\s[-]\\s";//TODO:Remove hard code
		
		Map<String, Integer> regexes = findRegexMatchedResult(cdetsTitle, cdetsTitleStartRegex, false);
		for (Map.Entry<String, Integer> entry : regexes.entrySet()) {
			String cdetsStartsStr =  entry.getKey().trim();
			cdetsTitle = StringUtils.replace(cdetsTitle, cdetsStartsStr, "");
			break;
		}
		
		return cdetsTitle;
	}
	
	@Override
	public String cleanRfcTitle(String rfcTitle) {
		logger.debug("Inside cleanRfcTitle method with : " + rfcTitle);
		
		String cdetsTitleStartRegex = "(rfc[0-9]{3,4})|(rfc\\s[0-9]{3,4})\\s[-]\\s";//TODO:Remove hard code
		
		Map<String, Integer> regexes = findRegexMatchedResult(rfcTitle, cdetsTitleStartRegex, false);
		for (Map.Entry<String, Integer> entry : regexes.entrySet()) {
			String cdetsStartsStr =  entry.getKey().trim();
			rfcTitle = StringUtils.replace(rfcTitle, cdetsStartsStr, "");
			break;
		}
		
		return rfcTitle;
	}
	
	@Override
	public boolean urlIsSpamCheck(Map<String, Integer> urlsMap) {
		logger.debug("Inside urlSpamCheck method with urlsMap : " + urlsMap);
		
		boolean isSpam = false;
		if (ObjectUtils.equals(urlsMap, null)) return isSpam;
		int count = urlsMap.size();
		
		for (Map.Entry<String, Integer> entry : urlsMap.entrySet()) {
			String url = entry.getKey();
			if (StringUtils.isBlank(url)) continue;
			if ((findIsDeniedUrl(url)))	count--;
		}
		
		//if (urlsMap.size() >= Constants.ICO_MAX_IN_SINGLE_TEXT) isSpam = true;
		if (count >= Constants.ICO_MAX_IN_SINGLE_TEXT) isSpam = true;
		
		logger.debug("isSpam : " + isSpam);
		return isSpam;
		
	}
	
	/**
	 * Search CSO code
	 * 
	 */
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
			String content = cSCCrawelService.readNeo4jUrlAsJson(neo4jIcoNodeUrl);
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
