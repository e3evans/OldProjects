package com.cisco.swtg.scim.service;

import java.util.List;

import com.cisco.swtg.scim.model.c3blmodel.TssIncidentNotesFV;
import com.cisco.swtg.scim.model.c3blmodel.TssIncidentsCurrentFV;

public interface C3blService {

	public List<TssIncidentNotesFV> findAllC3blCrawledRawData();

	public TssIncidentsCurrentFV findC3blCrawledRawData(int incidentNumber);

	/*public List<TssIncidentsCurrentFV> findC3blCrawledRawData(String fromDate,
			String endDate);*/
	
	public List<TssIncidentNotesFV>  findC3blCrawledRawDataByDate(String fromDate, String toDate);
}
