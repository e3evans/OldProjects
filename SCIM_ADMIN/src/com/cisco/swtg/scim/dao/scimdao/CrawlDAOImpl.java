package com.cisco.swtg.scim.dao.scimdao;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
import com.cisco.swtg.scim.util.Constants;

@Repository
public class CrawlDAOImpl implements CrawlDAO {

	private static final Logger logger = Logger.getLogger(CrawlDAOImpl.class);
	
	@Autowired
	private HibernateTemplate scimhibernateTemplate;

	public void setScimhibernateTemplate(@Qualifier("scimhibernateTemplate")HibernateTemplate scimhibernateTemplate) {
		this.scimhibernateTemplate = scimhibernateTemplate;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<C3RawData> listC3RawData() {
		logger.debug("Inside listC3RawData method");
		return scimhibernateTemplate.find("from C3RawData crd");
	}
		
	@Override
	@SuppressWarnings("unchecked")
	public C3RawData findC3RawDataById(String serviceRequestId) {
		logger.debug("Inside findC3RawDataById method");
		List<C3RawData> c3RawDatas = scimhibernateTemplate.find("from C3RawData crd where crd.serviceRequestId = " + serviceRequestId);
		
		if((ObjectUtils.equals(c3RawDatas, null)) || (c3RawDatas.size() < 1)) return null;
		return c3RawDatas.get(0);
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public IcoExtractedData findC3ExtractedIcoDataByIcoValue(String icoValue) {
		logger.debug("Inside findC3ExtractedIcoDataByIcoValue method");
		List<IcoExtractedData> c3IcoExtractedDatas = scimhibernateTemplate.find("from IcoExtractedData ied where ied.icoValue = '" + icoValue + "'");
		
		if((ObjectUtils.equals(c3IcoExtractedDatas, null)) || (c3IcoExtractedDatas.size() < 1)) return null;
		return c3IcoExtractedDatas.get(0);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public CSCExtractedIcoData findCSCExtractedIcoDataByIcoValue(String icoValue) {
		logger.debug("Inside findCSCExtractedIcoDataByIcoValue method");
		List<CSCExtractedIcoData> cscIcoExtractedDatas = scimhibernateTemplate.find("from CSCExtractedIcoData ied where ied.icoValue = '" + icoValue + "'");
		
		if((ObjectUtils.equals(cscIcoExtractedDatas, null)) || (cscIcoExtractedDatas.size() < 1)) return null;
		return cscIcoExtractedDatas.get(0);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public IcoUrlServerName  findIcoUrlServerNameByServerName(String serverName) {
		logger.debug("Inside findIcoUrlServerNameByServerName method with serverName : " + serverName);
		List<IcoUrlServerName> icoUrlServerNames = scimhibernateTemplate.find("from IcoUrlServerName usn where usn.serverName = '" + serverName + "'");
		
		if((ObjectUtils.equals(icoUrlServerNames, null)) || (icoUrlServerNames.size() < 1)) return null;
		return icoUrlServerNames.get(0);
	}
	
	@Override
	@Transactional
	public void  save(C3RawData c3RawData) {
		logger.debug("inside saveC3RawData with ... " + c3RawData);
		scimhibernateTemplate.merge(c3RawData);
		//scimhibernateTemplate.saveOrUpdate(c3RawData);
	}
	
	@Override
	@Transactional
	public void saveC3RawDatas(List<C3RawData> c3RawDatas) {
		logger.debug("inside saveC3RawData with ... " + c3RawDatas);
		scimhibernateTemplate.saveOrUpdateAll(c3RawDatas);
	}
	
	@Override
	@Transactional
	public void saveCSCRawDatas(List<CSCRawData> cSCRawDatas) {
		logger.debug("inside saveCSCRawDatas with ... " + cSCRawDatas);
		scimhibernateTemplate.saveOrUpdateAll(cSCRawDatas);
	}
	
	@Override
	@Transactional
	public void  save(CSCRawData cSCRawData) {
		logger.debug("inside saveCSCRawData with ... " + cSCRawData);
		scimhibernateTemplate.saveOrUpdate(cSCRawData);
	}
		
	@Override
	@Transactional
	public void  saveCSCExtractedIcoData(CSCExtractedIcoData cscExtractedIcoData) {
		logger.debug("inside saveCSCExtractedIcoData with ... " + cscExtractedIcoData);
		scimhibernateTemplate.saveOrUpdate(cscExtractedIcoData);
	}
		
	@Override
	@SuppressWarnings("unchecked")
	public IcoRegex findRegexById(long icoRegexId) {
		logger.debug("Inside listC3RawData method");
		List<IcoRegex> IcoRegexs =  scimhibernateTemplate.find("from IcoRegex ireg where ireg.icoRegexId = " + icoRegexId);
		if((ObjectUtils.equals(IcoRegexs, null)) || (IcoRegexs.size() < 1)) return null;
		return IcoRegexs.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<C3RawData> findC3CaseRawData(String fromDate, String toDate) {
		logger.debug("inside findC3blC3RawData method with ...fromDate " + fromDate + " toDate : " + toDate);
		String hql_query = "from C3RawData c where c.srCaseNoteTimestamp between :fromDate and :toDate";
		try {	
			Date dateFrom = Constants.SCIM_DATE_FORMATTER.parse(fromDate);
			Date dateTo = Constants.SCIM_DATE_FORMATTER.parse(toDate);
			return scimhibernateTemplate.findByNamedParam(hql_query, new String[]{"fromDate","toDate"}, new Object[]{dateFrom, dateTo});
			//return scimhibernateTemplate.find("from C3RawData c where c.caseId = '612269261'");
			//return scimhibernateTemplate.find("from C3RawData c where c.caseNoteId = 358324408");//340752266");//340356861");
		} catch (ParseException e) {
			logger.info(e);
		}
		logger.info("SQL Not executed properly : " + hql_query);		
		return null;
	}
	
	
	@Override
	@Transactional
	public void  saveIcoExtractedData(IcoExtractedData icoExtractedData) {
		logger.debug("inside saveC3IcoExtractedData with ... " + icoExtractedData);
		scimhibernateTemplate.saveOrUpdate(icoExtractedData);		
	}
	
	@Override
	@Transactional
	public void saveRejectedUrlList(RejectedUrlList rejectedUrlList) {
		logger.debug("inside saveRejectedUrlList with ... " + rejectedUrlList);
		scimhibernateTemplate.saveOrUpdate(rejectedUrlList);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<IcoRegex> findAllRegex() {
		logger.debug("Inside findAllRegex method");
		return scimhibernateTemplate.find("from IcoRegex ir");
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<SwImageGuidMaster> findAllSwImageNamesGuidMaster() {
		logger.debug("Inside findAllSwImageNamesGuidMaster method");
		return scimhibernateTemplate.find("from SwImageGuidMaster sw");
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Product> findAllProduct() {
		logger.debug("Inside findAllProduct method");
		System.out.println("Inside findAllProduct method");
		return scimhibernateTemplate.find("from Product ir");
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<UrlShortHand> findAllUrlShortHand() {
		logger.debug("Inside findAllUrlShortHand method");
		return scimhibernateTemplate.find("from UrlShortHand ush");
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<IcoDenyPermtPattern>  findAllIcoDenyPermtPattern() {
		logger.debug("Inside findAllIcoDenyPermtPattern method");
		return scimhibernateTemplate.find("from IcoDenyPermtPattern idp");
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<IcoDenyPermtPattern>  findIcoDenyPermtPatternByIcoType(String icoType) {
		logger.debug("Inside findIcoDenyPermtPatternByIcoType method with : " + icoType);
		String hqlQuery = "from IcoDenyPermtPattern idp where idp.icoType =:icoType";
		return scimhibernateTemplate.findByNamedParam(hqlQuery, new String[]{"icoType"}, new Object[]{icoType});
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<IcoDenyPermtPattern>  findIcoDenyPatternByIcoType(String icoType) {
		logger.debug("Inside findIcoDenyPatternByIcoType method with : " + icoType);
		String hqlQuery = "from IcoDenyPermtPattern idp where idp.icoType =:icoType and idp.isPermit = 'N'";
		return scimhibernateTemplate.findByNamedParam(hqlQuery, new String[]{"icoType"}, new Object[]{icoType});
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<CSCRawData> findAllCSCRawDatas() {
		logger.debug("Inside findAllCSCRawDatas method");
		return scimhibernateTemplate.find("from CSCRawData cscr");
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<CSCRawData> findCSCRawDatas(Date dateFrom, Date dateTo) {
		logger.debug("Inside findCSCRawDatas method with fromDate : " + dateFrom + " and toDate : " + dateTo);
		String hql_query = "from CSCRawData cscr where cscr.messageCreationDate between :fromDate and :toDate";
		return scimhibernateTemplate.findByNamedParam(hql_query, new String[]{"fromDate","toDate"}, new Object[]{dateFrom, dateTo});
		//return scimhibernateTemplate.find("from CSCRawData cscr where cscr.messageId = 3581962");
	}
	

	@Override
	@SuppressWarnings("unchecked")
	public CSCExtractedIcoData findCSCExtractedIcoData(String icoValue, int threadId) {
		logger.debug("Inside findCSCExtractedIcoDataByIcoValue method with ");
		String hqlQuery = "from CSCExtractedIcoData ied where ied.icoValue =:icoValue and ied.threadId =:threadId";
		List<CSCExtractedIcoData> cscIcoExtractedDatas = scimhibernateTemplate.findByNamedParam(hqlQuery, new String[]{"icoValue","threadId"}, new Object[]{icoValue,threadId});
		
		if((ObjectUtils.equals(cscIcoExtractedDatas, null)) || (cscIcoExtractedDatas.size() < 1)) return null;
		return cscIcoExtractedDatas.get(0);
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public IcoExtractedData findExtractedIcoData(String icoValue, String caseId) {
		logger.debug("Inside findExtractedIcoData method with icoValue : " + icoValue + " : " + caseId);
		String hqlQuery = "from IcoExtractedData ied where ied.icoValue =:icoValue and ied.c3CaseId =:caseId";
		List<IcoExtractedData> c3IcoExtractedDatas = scimhibernateTemplate.findByNamedParam(hqlQuery, new String[]{"icoValue","caseId"}, new Object[]{icoValue,caseId});
		
		if((ObjectUtils.equals(c3IcoExtractedDatas, null)) || (c3IcoExtractedDatas.size() < 1)) return null;
		return c3IcoExtractedDatas.get(0);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Product findProductByProductNo(String productNumber) {
		logger.debug("Inside findProductByProductNo method with productNumber : " + productNumber);
		List<Product> products = scimhibernateTemplate.find("from Product prd where prd.productNumber = '" + productNumber + "'");
		
		if((ObjectUtils.equals(products, null)) || (products.size() < 1)) return null;
		return products.get(0);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public SwImageGuidMaster findSwImageByFileName(String fileName) {
		logger.debug("Inside findSwImageByFileName method with fileName : " + fileName);
		List<SwImageGuidMaster> swImageGuidMasters = scimhibernateTemplate.find("from SwImageGuidMaster sw where sw.fileName = '" + fileName + "'");
		if((ObjectUtils.equals(swImageGuidMasters, null)) || (swImageGuidMasters.size() < 1)) return null;
		return swImageGuidMasters.get(0);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public SoftwareImageNameMview findSwImageMViewByFileName(String fileName) {
		logger.debug("Inside findSwImageMViewByFileName method with fileName : " + fileName);
		//List<SoftwareImageNameMview> softwareImageNames = scimhibernateTemplate.find("from SoftwareImageNameMview sw where sw.imageName = '" + fileName + "'");
		String hqlQuery = "from SoftwareImageNameMview sw where sw.imageName =:fileName";
		List<SoftwareImageNameMview> softwareImageNames =  scimhibernateTemplate.findByNamedParam(hqlQuery, new String[]{"fileName"}, new Object[]{fileName});
		if((ObjectUtils.equals(softwareImageNames, null)) || (softwareImageNames.size() < 1)) return null;
		return softwareImageNames.get(0);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public SoftwareImageNameMview findSwImageMViewByFileNameFuzzy(String fileName) {
		logger.debug("Inside findSwImageMViewByFileNameFuzzy method with fileName : " + fileName);
		String hqlQuery = "from SoftwareImageNameMview sw where sw.imageName like :fileName";
		List<SoftwareImageNameMview> softwareImageNames =  scimhibernateTemplate.findByNamedParam(hqlQuery, "fileName", '%' + fileName + '%'); 

		if((ObjectUtils.equals(softwareImageNames, null)) || (softwareImageNames.size() < 1)) return null;
		return softwareImageNames.get(0);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public RejectedUrlList findRejectedUrlListByUrl(String url) {
		logger.debug("Inside findRejectedUrlListByUrl method with url : " + url);
		//List<RejectedUrlList> rejectedUrlLists = scimhibernateTemplate.find("from RejectedUrlList rj where rj.icoValue = '" + url + "'");
		String hqlQuery = "from RejectedUrlList rj where rj.icoValue =:url";
		List<RejectedUrlList> rejectedUrlLists =  scimhibernateTemplate.findByNamedParam(hqlQuery, new String[]{"url"}, new Object[]{url});
		
		if((ObjectUtils.equals(rejectedUrlLists, null)) || (rejectedUrlLists.size() < 1)) return null;
		return rejectedUrlLists.get(0);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<LinkedServiceRequest> findLinkedServiceRequests(String caseId) {
		logger.debug("Inside findLinkedServiceRequests method with : " + caseId);
		String hqlQuery = "from LinkedServiceRequest lc where lc.caseId =:caseId";
		return scimhibernateTemplate.findByNamedParam(hqlQuery, new String[]{"caseId"}, new Object[]{caseId});
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<LinkedBugs> findLinkedBugs(String caseId) {
		logger.debug("Inside findLinkedBugs method with : " + caseId);
		String hqlQuery = "from LinkedBugs lb where lb.caseId =:caseId";
		return scimhibernateTemplate.findByNamedParam(hqlQuery, new String[]{"caseId"}, new Object[]{caseId});
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public int findMaxC3IcoId() {
		logger.debug("Inside findMaxC3IcoId method...");
		String hqlQuery = "select max(ied.c3IcoId)from IcoExtractedData ied";
		try {
			List<Integer> maxValues = scimhibernateTemplate.find(hqlQuery);		
			if((!ObjectUtils.equals(maxValues, null)) && (maxValues.size() > 0)) return maxValues.get(0);;
		} catch (Exception e) {
			return 0; 
		}
		return 0; 		

	}
	
	@Override
	@SuppressWarnings("unchecked")
	public int findMaxMDFId() {
		logger.debug("Inside findMaxMDFId method...");
		String hqlQuery = "select max(mdf.mdfId)from UrlMDFData mdf";
		try {
			List<Integer> maxValues = scimhibernateTemplate.find(hqlQuery);		
			if((!ObjectUtils.equals(maxValues, null)) && (maxValues.size() > 0)) return maxValues.get(0);;
		} catch (Exception e) {
			return 0; 
		}
		return 0; 		

	}
	
	@Override
	@Transactional
	public void  saveURLMDFData(UrlMDFData urlmdfdata) {
		logger.debug("inside saveURLMDFData with ... " + urlmdfdata.getUrl() + urlmdfdata.getMdf());
		System.out.println("inside saveURLMDFData with ... " + urlmdfdata.getUrl() + urlmdfdata.getMdf());
		scimhibernateTemplate.saveOrUpdate(urlmdfdata);		
	}
}
