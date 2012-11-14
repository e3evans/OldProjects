package com.cisco.swtg.scim.dao.c3bldao;

import java.util.Date;
import java.util.List;

import com.cisco.swtg.scim.model.c3blmodel.TssIncidentNotesFV;
import com.cisco.swtg.scim.model.c3blmodel.TssIncidentsCurrentFV;
import com.cisco.swtg.scim.model.c3blmodel.TssTacProductsDV;

public interface C3blDAO {

	public List<TssIncidentNotesFV> listC3blCrawledRawData();

	public TssIncidentsCurrentFV findC3blCrawledRawData(int incidentNumber);

	public List<TssIncidentsCurrentFV> findC3blCrawledRawData(Date fromDate,
			Date endDate);

	public List<Integer> findC3blCrawledRawDataIds(Date fromDate, Date toDate);

	public TssIncidentsCurrentFV findC3blCrawledRawDataIncidentNo(
			String incidentnumber);

	public TssTacProductsDV findTssTacProductsDV(int versionId);
	
	//public TssIncidentsCurrentFV findC3blCrawledRawData(int incidentId, Date fromDate, Date toDate);
	
	public TssIncidentsCurrentFV findC3blCrawledRawDataByCaseNoteId(int jtfNoteId);
	
	public TssIncidentNotesFV findTssIncidentNotesFVByCaseNoteId(int jtfNoteId);
	
	public List<TssIncidentNotesFV> findTssIncidentNotesFVByCaseNoteId(List<Integer> jtfNoteIds);
	
	public List<TssIncidentNotesFV> findC3blCrawledRawDataByCreationdate(Date fromDate, Date toDate);
}
