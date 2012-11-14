package com.cisco.swtg.scim.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cisco.swtg.scim.dao.c3bldao.C3blDAO;
import com.cisco.swtg.scim.model.c3blmodel.TssIncidentNotesFV;
import com.cisco.swtg.scim.model.c3blmodel.TssIncidentsCurrentFV;
import com.cisco.swtg.scim.util.Constants;

@Service
public class C3blServiceImpl implements C3blService {

	private static final Logger logger = Logger.getLogger(C3blServiceImpl.class);
	
	@Autowired
	private C3blDAO c3blDAO;
	
	@Autowired
	private C3CrawlService c3CrawelService;
	
	@Override
	public List<TssIncidentNotesFV> findAllC3blCrawledRawData() {		
		List<TssIncidentNotesFV> listCrawledRawData = c3blDAO.listC3blCrawledRawData();
		return listCrawledRawData;
	}
	
	@Override
	public TssIncidentsCurrentFV findC3blCrawledRawData(int incidentNumber) {
		logger.debug("Inside findC3blCrawledRawData method ...with : " + incidentNumber);
		TssIncidentsCurrentFV tssIncidentsCurrentFV = c3blDAO.findC3blCrawledRawData(incidentNumber);
		return tssIncidentsCurrentFV;
	}
	
	
	@Override
	public List<TssIncidentNotesFV>  findC3blCrawledRawDataByDate(String fromDate, String toDate) {
		logger.debug("Inside findC3blCrawledRawData method ...with  fromDate : " + fromDate + " and toDate : " + toDate);
		List<TssIncidentNotesFV> listCrawledRawDatas = new ArrayList<TssIncidentNotesFV>();
		Date dateFrom = null;
		Date dateTo = null;
		try {		
			dateFrom = Constants.SCIM_DATE_FORMATTER.parse(fromDate);
			dateTo = Constants.SCIM_DATE_FORMATTER.parse(toDate);			
		} catch(Exception e) {
			logger.debug("Date formatter exception occurred for fromDate : " + fromDate + " Or toDate : " + toDate);
			logger.info(e);
			return null;
		}
		
		logger.info("*** Total tssIncidentNotesFVs fetching  from C3BL DB started");
		List<TssIncidentNotesFV>  tssIncidentNotesFVs = c3blDAO.findC3blCrawledRawDataByCreationdate(dateFrom, dateTo);
		logger.info("*** Total tssIncidentNotesFVs fetching from C3BL DB completed");

		logger.info("*** Total Rawdatas are saving into SCIM DB started");
 		c3CrawelService.saveC3RawDatas(tssIncidentNotesFVs);
 		logger.info("*** Total Rawdatas are saving into SCIM DB completed");
		
		return listCrawledRawDatas ;
	}
	
	
	public List<List<Integer>> findListofListsCaseNoteids(List<Integer> list) {
		int subCollectionSize = 900;
		List<List<Integer>> resultList = new ArrayList<List<Integer>>();
		for (int i = 0; i < list.size() / subCollectionSize + 1; i++) {
			int maxLength;
			if (i * subCollectionSize + subCollectionSize > list.size())
				maxLength = list.size();
			else
				maxLength = i * subCollectionSize + subCollectionSize;
			List<Integer> sublist = new ArrayList<Integer>();
			for (int j = i * subCollectionSize; j < maxLength; j++) {
				sublist.add(list.get(j));
			}
			resultList.add(sublist);
		}

		return resultList;
	}
}
