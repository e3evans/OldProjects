package com.cisco.swtg.scim.dao.scimdao;

import java.util.Date;
import java.util.List;

import com.cisco.swtg.scim.model.scimmodel.C3RawData;
import com.cisco.swtg.scim.model.scimmodel.CSCExtractedIcoData;
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

public interface CrawlDAO {

	public List<C3RawData> listC3RawData();

	public void save(C3RawData c3RawData);

	public void saveC3RawDatas(List<C3RawData> c3RawDatas);

	public void saveRejectedUrlList(RejectedUrlList rejectedUrlList);

	public C3RawData findC3RawDataById(String serviceRequestId);

	public IcoRegex findRegexById(long icoRegexId);

	public List<IcoRegex> findAllRegex();

	public List<C3RawData> findC3CaseRawData(String fromDate, String toDate);

	public void saveIcoExtractedData(IcoExtractedData icoExtractedData);

	public List<UrlShortHand> findAllUrlShortHand();

	
	public List<IcoDenyPermtPattern>  findIcoDenyPermtPatternByIcoType(String icoType);
	
	public List<IcoDenyPermtPattern>  findAllIcoDenyPermtPattern();
	
	public List<IcoDenyPermtPattern>  findIcoDenyPatternByIcoType(String icoType);

	public IcoUrlServerName findIcoUrlServerNameByServerName(String serverName);

	public List<SwImageGuidMaster> findAllSwImageNamesGuidMaster();

	public List<Product> findAllProduct();

	public void save(CSCRawData cSCRawData);

	public void saveCSCRawDatas(List<CSCRawData> cSCRawDatas);

	public List<CSCRawData> findAllCSCRawDatas();

	public void saveCSCExtractedIcoData(CSCExtractedIcoData cscExtractedIcoData);

	public IcoExtractedData findC3ExtractedIcoDataByIcoValue(String icoValue);

	public CSCExtractedIcoData findCSCExtractedIcoDataByIcoValue(String icoValue);

	public CSCExtractedIcoData findCSCExtractedIcoData(String icoValue,
			int messageId);

	public IcoExtractedData findExtractedIcoData(String icoValue, String caseId);

	public List<CSCRawData> findCSCRawDatas(Date dateFrom, Date dateTo);

	public Product findProductByProductNo(String productNumber);

	public SwImageGuidMaster findSwImageByFileName(String fileName);

	public SoftwareImageNameMview findSwImageMViewByFileName(String fileName);

	public RejectedUrlList findRejectedUrlListByUrl(String url);

	public int findMaxC3IcoId();
	
	public SoftwareImageNameMview findSwImageMViewByFileNameFuzzy(String fileName);
	
	public List<LinkedServiceRequest> findLinkedServiceRequests(String caseId);
	
	public List<LinkedBugs> findLinkedBugs(String caseId);
	
	public void saveURLMDFData(UrlMDFData urlmdfdata);

	public int findMaxMDFId();
}
