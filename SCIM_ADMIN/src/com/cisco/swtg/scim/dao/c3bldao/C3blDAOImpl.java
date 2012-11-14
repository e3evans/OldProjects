package com.cisco.swtg.scim.dao.c3bldao;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.cisco.swtg.scim.model.c3blmodel.TssIncidentNotesFV;
import com.cisco.swtg.scim.model.c3blmodel.TssIncidentsCurrentFV;
import com.cisco.swtg.scim.model.c3blmodel.TssTacProductsDV;

@Repository
public class C3blDAOImpl implements C3blDAO {

	private static final Logger logger = Logger.getLogger(C3blDAOImpl.class);
	
	private HibernateTemplate c3hibernateTemplate;

	public void setC3hibernateTemplate(HibernateTemplate c3hibernateTemplate) {
		this.c3hibernateTemplate = c3hibernateTemplate;
	}

	@SuppressWarnings("unchecked")
	@Override	
	public List<TssIncidentNotesFV> listC3blCrawledRawData() {
		logger.debug("Inside listCrawledRawData method");
		return c3hibernateTemplate.find("from TssIncidentNotesFV inc");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public TssIncidentsCurrentFV findC3blCrawledRawData(int incidentId) {
		logger.debug("inside findC3blCrawledRawData method with ... " + incidentId);
		String hql_query = "from TssIncidentsCurrentFV inc where inc.incidentId = " + incidentId;
		List<TssIncidentsCurrentFV> tssIncidentsCurrentFV  = c3hibernateTemplate.find(hql_query);
		if ((ObjectUtils.equals(tssIncidentsCurrentFV, null)) || (tssIncidentsCurrentFV.size() < 1)) return null;
		else return tssIncidentsCurrentFV.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TssIncidentsCurrentFV> findC3blCrawledRawData(Date fromDate, Date toDate) {
		logger.debug("inside findC3blCrawledRawData method with ...fromDate " + fromDate + " toDate : " + toDate);
		String hql_query = "from TssIncidentsCurrentFV inc where inc.creationDate between :fromDate and :toDate";

		List<TssIncidentsCurrentFV>  tssIncidentsCurrentFVs = c3hibernateTemplate.findByNamedParam(hql_query, new String[]{"fromDate","toDate"}, new Object[]{fromDate, toDate});
		
		if ((!ObjectUtils.equals(tssIncidentsCurrentFVs, null)) && (tssIncidentsCurrentFVs.size() > 0)) {
			logger.debug("Totally fetched records : " + tssIncidentsCurrentFVs.size());
		}
		
		return tssIncidentsCurrentFVs;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> findC3blCrawledRawDataIds(Date fromDate, Date toDate) {
		logger.debug("inside findC3blCrawledRawDataIds method with ...fromDate " + fromDate + " toDate : " + toDate);

		String hqlQuery = "select n.jtfNoteId from TssIncidentNotesFV n where n.creationDate between :fromDate and :toDate";
		List<Integer>  tssIncidentNotesFVIds = c3hibernateTemplate.findByNamedParam(hqlQuery, new String[]{"fromDate","toDate"}, new Object[]{fromDate, toDate});

		//String hql_queryTest = "select n.jtfNoteId from TssIncidentNotesFV n where n.jtfNoteId = 333560394";
		//List<Integer>  tssIncidentNotesFVIds = c3hibernateTemplate.find(hql_queryTest);
		
		if ((!ObjectUtils.equals(tssIncidentNotesFVIds, null)) && (tssIncidentNotesFVIds.size() > 0)) {
			logger.debug("Totally c3bl raw data fetched records : " + tssIncidentNotesFVIds.size());
		}
		
		return tssIncidentNotesFVIds;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TssIncidentNotesFV> findC3blCrawledRawDataByCreationdate(Date fromDate, Date toDate) {
		logger.info("inside findC3blCrawledRawDataByCreationdate method with ...fromDate " + fromDate + " toDate : " + toDate);

		String hqlQuery = "from TssIncidentNotesFV n where n.creationDate between :fromDate and :toDate";
		List<TssIncidentNotesFV> tssIncidentNotesFVs = c3hibernateTemplate.findByNamedParam(hqlQuery, new String[]{"fromDate","toDate"}, new Object[]{fromDate, toDate});

		//String hql_queryTest = "from TssIncidentNotesFV n where n.jtfNoteId = 311813926";
		//List<TssIncidentNotesFV>  tssIncidentNotesFVs = c3hibernateTemplate.find(hql_queryTest);
		
		if ((!ObjectUtils.equals(tssIncidentNotesFVs, null)) && (tssIncidentNotesFVs.size() > 0)) {
			logger.debug("Totally c3bl raw data fetched records : " + tssIncidentNotesFVs.size());
		}
		System.out.println("inside findC3blCrawledRawDataByCreationdate method with ...fromDate " + fromDate + " toDate : " + toDate + " : completed at : " + new Date());
		logger.info("inside findC3blCrawledRawDataByCreationdate method with ...fromDate " + fromDate + " toDate : " + toDate + " : completed at : " + new Date());
		return tssIncidentNotesFVs;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public TssIncidentsCurrentFV findC3blCrawledRawDataByCaseNoteId(int jtfNoteId) {
		logger.debug("inside findC3blCrawledRawData method with ... and from and to date :  " + jtfNoteId);
		String hql_query = "select inc from TssIncidentsCurrentFV inc join inc.tssIncidentNotesFVs n where n.jtfNoteId =:jtfNoteId";
		List<TssIncidentsCurrentFV> tssIncidentsCurrentFV  = c3hibernateTemplate.findByNamedParam(hql_query, new String[]{"jtfNoteId"}, new Object[]{jtfNoteId});
		if ((ObjectUtils.equals(tssIncidentsCurrentFV, null)) || (tssIncidentsCurrentFV.size() < 1)) return null;
		else return tssIncidentsCurrentFV.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public TssIncidentNotesFV findTssIncidentNotesFVByCaseNoteId(int jtfNoteId) {
		logger.debug("inside findTssIncidentNotesFVByCaseNoteId method with ... ajtfNoteId :  " + jtfNoteId);
		String hql_query = "from TssIncidentNotesFV n where n.jtfNoteId =:jtfNoteId";
		List<TssIncidentNotesFV> tssIncidentNotesFV  = c3hibernateTemplate.findByNamedParam(hql_query, new String[]{"jtfNoteId"}, new Object[]{jtfNoteId});
		if ((ObjectUtils.equals(tssIncidentNotesFV, null)) || (tssIncidentNotesFV.size() < 1)) return null;
		else return tssIncidentNotesFV.get(0);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TssIncidentNotesFV> findTssIncidentNotesFVByCaseNoteId(List<Integer> jtfNoteIds) {
		logger.debug("inside findTssIncidentNotesFVByCaseNoteId method with ... jtfNoteIds :  " + jtfNoteIds);
		String hqlQuery = "from TssIncidentNotesFV n where n.jtfNoteId in (:jtfNoteIds)";
		return  c3hibernateTemplate.findByNamedParam(hqlQuery, new String[]{"jtfNoteIds"}, new Object[]{jtfNoteIds});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public TssIncidentsCurrentFV findC3blCrawledRawDataIncidentNo(String incidentnumber) {
		logger.debug("inside TssIncidentsCurrentFV method with ...incidentnumber " + incidentnumber);
		String hqlQuery = "from TssIncidentsCurrentFV inc where inc.incidentnumber =:incidentnumber";

		List<TssIncidentsCurrentFV> tssIncidentsCurrentFVs = c3hibernateTemplate.findByNamedParam(hqlQuery, new String[]{"incidentnumber"}, new Object[]{incidentnumber});
		
		if ((!ObjectUtils.equals(tssIncidentsCurrentFVs, null)) && (tssIncidentsCurrentFVs.size() > 0)) {
			logger.debug("Totally fetched records : " + tssIncidentsCurrentFVs.size());
			return tssIncidentsCurrentFVs.get(0);
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public TssTacProductsDV findTssTacProductsDV(int versionId) {
		logger.debug("Inside findTssTacProductsDV method with versionId : " + versionId);
		List<TssTacProductsDV> tssTacProductsDVs = c3hibernateTemplate.find("from TssTacProductsDV pd where pd.blActiveFlag = 'Y' and pd.versionId = " + versionId );
		if((ObjectUtils.equals(tssTacProductsDVs, null)) || (tssTacProductsDVs.size() < 1)) return null;
		return tssTacProductsDVs.get(0);
	}
	
}
