package com.cisco.swtg.scim.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cisco.swtg.scim.model.c3blmodel.TssIncidentNotesFV;
import com.cisco.swtg.scim.model.scimmodel.C3RawData;
import com.cisco.swtg.scim.model.scimmodel.CSCRawData;
import com.cisco.swtg.scim.model.scimmodel.IcoExtractedData;
import com.cisco.swtg.scim.model.scimmodel.IcoRegex;
import com.cisco.swtg.scim.model.scimmodel.RejectedUrlList;

public interface C3CrawlService {

	public List<C3RawData> listC3RawData();

	public C3RawData findC3RawDataById(String serviceRequestId);

	public void save(C3RawData c3RawData);

	public void saveC3RawData(List<C3RawData> c3RawDatas);

	public void saveC3RawDatas(List<TssIncidentNotesFV> tssIncidentNotesFVs);

	public IcoRegex findIcoRegex(long icoRegexId);

	public Map<String, Integer> findRegexMatchedResult(String notes,
			String regex, boolean isCaseSensitive);

	public void parseC3Ico(String fromDate, String toDate);

	public String findNormalizedUrl(String url, Object rawDataObject);

	public String findFinalDestinationUrl(String url, int count);

	public boolean findIsActiveUrl(String url);

	public String convertTinyToFullUrl(String tinyUrl);

	public String trunkcateNamedAnchorsAndQueryStr(String url);

	public String removeEntitlementFromUrl(String url);

	public String convertShortHandUrlToFullUrl(String url);

	public boolean findIsDeniedUrl(String url);

	public IcoExtractedData findUrlProperties(String normalizedUlr,
			IcoExtractedData icoExtractedData);

	public String findTitleFromHtmlUrl(String url, String userId,
			String password);

	public void populateSwImageAndPid();

	public Set<String> findIcosForSW(String notes);

	public Set<String> findIcosForPID(String notes);

	public boolean isValidURL(String url);

	public int findErrorCodeForURL(String urlStr, String userId, String password);

	public String cleanRfcIcoValue(String rfcIcoValue);

	public boolean isUrlNotEndsWithProperly(String url);

	public String cleanCdetsTitle(String cdetsTitle, String icoValue);

	public String cleanEndsWithSlashUrl(String url);

	public String addHttpInStart(String url);

	public void saveRejectedUrlList(RejectedUrlList rejectedUrlList,
			Object rawDataObject);

	public void saveC3RejectedUrlList(RejectedUrlList rejectedUrlList,
			C3RawData c3RawData);

	public void saveCSCRejectedUrlList(RejectedUrlList rejectedUrlList,
			CSCRawData cSCRawData);

	public void saveRejectedUrlList(RejectedUrlList rejectedUrlList);

	public String findTitleFromHtmlContent(String content);

	public String findPDFTtitleByURL(String urlStr);
	
	public String cleanRfcTitle(String rfcTitle);
	
	public boolean urlIsSpamCheck(Map<String, Integer> urlsMap);
	
	//public void constructPIDRegexs(Map<String, String> productNumbersMap);
	
	public void saveC3RawData(TssIncidentNotesFV tssIncidentNotesFV);
	
	public String findIcoTitle(String icoType, String icoValue);
	
	public boolean findIsDeniedIco(String icoValue, String icoType);

}
