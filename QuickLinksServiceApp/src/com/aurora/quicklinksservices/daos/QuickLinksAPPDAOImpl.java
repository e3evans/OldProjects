package com.aurora.quicklinksservices.daos;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.aurora.quicklinksservices.beans.App;
import com.aurora.quicklinksservices.beans.User;
import com.aurora.quicklinksservices.beans.UserApp;
import com.aurora.quicklinksservices.beans.UserAppKey;
import com.aurora.quicklinksservices.beans.UserAppResponseBean;
import com.aurora.quicklinksservices.util.QuickLinksUtility;

@Repository
public class QuickLinksAPPDAOImpl implements QuickLinksAPPDAO {
	private Logger logger = Logger.getLogger(QuickLinksAPPDAOImpl.class);
	@Autowired
	private SessionFactory urlsessionFactory;

	

	/* getting Available quicklinks list based on user role */

	@SuppressWarnings("unchecked")
	public List<App> findAvailAppListByRole(String roleCd) {
		String rolecd="EMP";
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT {app.*} ");
		sql.append("FROM S05DTDB.tpt2b_application app, S05DTDB.tpt2b_application parent, ");
		sql.append("S05DTDB.tpt2e_app_role approle ");
		sql.append("WHERE app.pt2b_appid = approle.pt2e_appid ");
		sql.append("AND app.pt2b_seq_no = approle.pt2e_seq_no ");
		sql.append("AND app.pt2b_appid = parent.pt2b_appid ");
		sql.append("AND parent.pt2b_seq_no = 0 ");
		sql.append("AND approle.pt2e_role_cd ='"+rolecd+"'");
		sql.append("AND app.pt2b_login_acc NOT IN ('E','N') ");
		sql.append("AND app.pt2b_active_cd = 'A' ");
		sql.append("AND parent.pt2b_active_cd = 'A' ");
		sql.append("ORDER BY parent.pt2b_app_name, app.pt2b_seq_no ");
		Session session = urlsessionFactory.openSession();
		List<App> list = session.createSQLQuery(sql.toString())
				.addEntity("app", "com.aurora.quicklinksservices.beans.App").list();
		session.close();
		return list;

	}

	/* Service for getting user details */

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findUserDetails(String loginid) {
		// TODO Auto-generated method stub
		//Long userId=43l;
		
	
		Session session = urlsessionFactory.openSession();
		session.beginTransaction();
		Query query = session
				.createQuery("from com.aurora.quicklinksservices.beans.User where loginId='"+loginid+"'");
		List<User> list = query.list();
		List<User> appList = new ArrayList<User>();
		//StringBuffer sb = new StringBuffer();
		if(null!=list){
		for (int i = 0; i < list.size(); i++) {
			User temp = (User) list.get(i);
			appList.add(temp);
		}
		}
		session.close();
		// System.out.println("result"+sb.toString());
		return appList;
	}

	/* Service for getting user saved quick links */

	@SuppressWarnings("unchecked")
	public List<UserAppResponseBean> findUserAppsByUser(Long userid) {
		//userid = 43l;
		//String appid="ICONNECT";
		String formatedurl="";
		Session session = urlsessionFactory.openSession();
		session.beginTransaction();
		UserAppResponseBean bean = null;
		List<UserAppResponseBean> listUserAppBean = new ArrayList<UserAppResponseBean>();
		StringBuffer sql = new StringBuffer();
	    sql.append("SELECT {app.*} ");
	    sql.append("FROM S05DTDB.tpt2b_application app ");
	    sql.append("WHERE (app.pt2b_appid = 'EBINO' OR  app.pt2b_appid='EB092' OR  app.pt2b_appid='EB500'  OR  app.pt2b_appid='EBLIB'  OR  app.pt2b_appid='ahcom' ");
	    sql.append("OR  app.pt2b_appid='EB122' OR  app.pt2b_appid='EB084'  OR  app.pt2b_appid='EB538'  OR  app.pt2b_appid='EB480'  OR  app.pt2b_appid='EB057'");  
	    sql.append("OR app.pt2b_appid='PETR' OR  app.pt2b_appid='EB294' OR app.pt2b_appid='EB110 ' OR app.pt2b_appid='EB294' OR app.pt2b_appid='EB416')");
        sql.append("AND app.pt2b_seq_no = 0");
       	    
	    List<App> defaultapplist =  session.createSQLQuery(sql.toString()).addEntity("app", App.class).list();

		if (null != defaultapplist && !(defaultapplist.isEmpty())) {
			for (App app : defaultapplist) {
				if (app.getAppURL() != null) {
					String url = app.getAppURL().trim();
					QuickLinksUtility qlutility = new QuickLinksUtility();
					formatedurl = qlutility.urlFormat(url);

				}
	    	 
		 bean = new UserAppResponseBean();
		 bean.setAppId(app.getAppKey().getAppId());
		 bean.setAppName(app.getAppName().trim());
		 bean.setAppUrl(formatedurl);
	     bean.setActiveCd(app.getActiveCd());
		 bean.setSeqNo(app.getAppKey().getSeqNo().toString());
		 bean.setFlagDefault("true");
		 bean.setUserId(userid + "");
	     listUserAppBean.add(bean);
	}
}

		 
		List<UserApp> list = session.createCriteria(UserApp.class)
				.add(Restrictions.eq("userAppKey.userId", userid))
				.add(Restrictions.eq("activeCd", "A")).list();
        
		for (int i = 0; i < list.size(); i++) {
			
			bean = new UserAppResponseBean();
			UserApp temp = (UserApp) list.get(i);
			
			if (temp.getApplication().getAppURL() != null) {
				String url = temp.getApplication().getAppURL().trim();
				QuickLinksUtility qlutility = new QuickLinksUtility();
				formatedurl = qlutility.urlFormat(url);

			}
		    bean.setAppName(temp.getApplication().getAppName().trim());
			bean.setAppUrl(formatedurl);
			bean.setAppId((temp.getApplication().getAppKey().getAppId()));
			bean.setActiveCd(temp.getApplication().getActiveCd());
			bean.setSeqNo(temp.getApplication().getAppKey().getSeqNo().toString());
			bean.setUserId(userid + "");
			listUserAppBean.add(bean);
			// appList.add(temp);
		}
	
			
		session.close();
		
		return listUserAppBean;

	}

	
	@SuppressWarnings("unchecked")
	public List<UserAppResponseBean> findAllUserAppsByUser(Long userid) {
		Session session = urlsessionFactory.openSession();
		session.beginTransaction();
		UserAppResponseBean bean = null;
		List<UserAppResponseBean> listUserAppBean = new ArrayList<UserAppResponseBean>();
		
		StringBuffer sql = new StringBuffer();
	    sql.append("SELECT {app.*} ");
	    sql.append("FROM S05DTDB.tpt2b_application app ");
	    sql.append("WHERE (app.pt2b_appid = 'EBINO' OR  app.pt2b_appid='EB092' OR  app.pt2b_appid='EB500'  OR  app.pt2b_appid='EBLIB'  OR  app.pt2b_appid='ahcom' ");
	    sql.append("OR  app.pt2b_appid='EB122' OR  app.pt2b_appid='EB084'  OR  app.pt2b_appid='EB538'  OR  app.pt2b_appid='EB480'  OR  app.pt2b_appid='EB057'");  
	    sql.append("OR app.pt2b_appid='PETR' OR  app.pt2b_appid='EB294' OR app.pt2b_appid='EB110 ' OR app.pt2b_appid='EB294' OR app.pt2b_appid='EB416')");
        sql.append("AND app.pt2b_seq_no = 0");
       	    
	    List<App> defaultapplist =  session.createSQLQuery(sql.toString()).addEntity("app", App.class).list();

         if(null!=defaultapplist&&!(defaultapplist.isEmpty())){
	     for(App app : defaultapplist){
		 bean = new UserAppResponseBean();
		 bean.setAppId(app.getAppKey().getAppId());
		 bean.setAppName(app.getAppName().trim());
		 bean.setAppUrl(app.getAppURL().trim());
	     bean.setActiveCd(app.getActiveCd());
		 bean.setSeqNo(app.getAppKey().getSeqNo().toString());
		 bean.setFlagDefault("true");
		 bean.setUserId(userid + "");
	     listUserAppBean.add(bean);
	}
}

	
		
		List<UserApp> list =  session.createCriteria(UserApp.class).add(Restrictions.eq("userAppKey.userId", userid)).list();
        //StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			
			bean = new UserAppResponseBean();
			UserApp temp = (UserApp) list.get(i);
			bean.setAppName(temp.getApplication().getAppName().trim());
			bean.setAppUrl(temp.getApplication().getAppURL().trim());
			bean.setAppId((temp.getApplication().getAppKey().getAppId()));
			bean.setActiveCd(temp.getActiveCd());
			bean.setSeqNo(temp.getApplication().getAppKey().getSeqNo().toString());
			bean.setUserId(userid + "");
			listUserAppBean.add(bean);
			// appList.add(temp);
		}
		session.close();
		return listUserAppBean;

	}
	
	
	
	
	/* find userapp */

	@Override
	public UserAppResponseBean readUserApp(UserAppKey userAppKey) {
		Session session = urlsessionFactory.openSession();
		session.beginTransaction();
		UserApp userApp = (UserApp) session.createCriteria(UserApp.class)
				.add(Restrictions.idEq(userAppKey)).uniqueResult();
		UserAppResponseBean userAppResponseBean = new UserAppResponseBean();
		if (null != userApp) {
			userAppResponseBean.setAppId(userApp.getUserAppKey().getAppId().toString());
			userAppResponseBean.setSeqNo(userApp.getUserAppKey().getSeqNo().toString());
			
			userAppResponseBean.setUserId(userApp.getUserAppKey().getUserId()
					.toString());
			userAppResponseBean.setActiveCd(userApp.getActiveCd().toString());
		}
	    session.close();
		return userAppResponseBean;

	}

	/**
	 * need to write utility class for session
	 * 
	 */

	public void insertUserApp(UserApp userApp) {
		Transaction txn = null;
		Session session=null;
		try {  
		session = urlsessionFactory.openSession();
		txn=session.beginTransaction();
		session.save(userApp);
		txn.commit();
		} catch (Exception e) { 
			logger.error(e.getMessage());
		} finally {
		    if (!txn.wasCommitted()) {
		        txn.rollback();
		    }

		    session.flush();  
		    session.close();   
		}

}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<App> findAppMenuAutoList(String appId)
	  {
	    
	     StringBuffer sql = new StringBuffer();
         sql.append("SELECT {app.*} ");
	     sql.append("FROM S05DTDB.tpt2b_application app ");
	     sql.append("WHERE pt2b_appid ='"+appId+"'");
	     sql.append("AND pt2b_auto_reg = 'Y' ");
	     sql.append("AND pt2b_login_acc NOT IN ('E','N') ");
	     sql.append("AND pt2b_active_cd = 'A' ");
	     sql.append("AND pt2b_seq_no > 0 ");
	     sql.append("ORDER BY pt2b_seq_no ");
	     Session session = urlsessionFactory.openSession();
	     List<App> list = session.createSQLQuery(sql.toString()).addEntity("app", App.class).list();
	     session.close();
	     return list;
	   }
	
	
	public void updateUserApp( UserAppKey userAppKey , String activecd){
        Transaction txn = null;
        Session session = null;
        try { 
        session = urlsessionFactory.openSession();
        txn = session.beginTransaction();
        UserApp   userApp = (UserApp) session.createCriteria(UserApp.class)
                .add(Restrictions.idEq(userAppKey)).uniqueResult();
       if(null!=userApp){
         userApp.setActiveCd(activecd);
        session.update(userApp);
        txn.commit();
        }} catch (Exception e) {
        	logger.error(e.getMessage());
        } finally {
            if (!txn.wasCommitted()) {
                txn.rollback();
            }

            session.flush(); 
            session.close();  
        }
       
	}
	
	
}
