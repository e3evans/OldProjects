package com.aurora.quicklinksservices.daos;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.SharedSessionContract;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;

import com.aurora.quicklinksservices.beans.App;
import com.aurora.quicklinksservices.beans.User;
import com.aurora.quicklinksservices.beans.UserApp;
import com.aurora.quicklinksservices.beans.UserAppKey;
import com.aurora.quicklinksservices.beans.UserAppResponseBean;

@Repository
public class QuickLinksAPPDAOImpl implements QuickLinksAPPDAO {

	@Autowired
	private SessionFactory urlsessionFactory;

	/*
	 * public List findAvailAppListByRole(String roleCd) { Session session =
	 * urlsessionFactory.openSession(); session.beginTransaction();
	 * 
	 * Query query =
	 * session.createQuery("from com.aurora.quicklinksservices.beans.Application"
	 * ); List list = query.list();
	 * System.out.println("TEST -- > "+list.size()); List<Application> appList =
	 * new ArrayList<Application>(); StringBuffer sb = new StringBuffer(); for
	 * (int i=0;i<list.size();i++){ Application temp = (Application)list.get(i);
	 * sb.append(temp.getAppName()+"---->"+temp.getAppDesc()+"<br/>");
	 * sb.append(temp.getAppURL()+"----><br/>"); appList.add(temp); }
	 * session.close(); System.out.println("result"+sb.toString()); return
	 * appList;
	 * 
	 * }
	 */

	/* getting Available quicklinks list based on user role */

	public List findAvailAppListByRole(String roleCd) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT {app.*} ");
		sql.append("FROM S05DTDB.tpt2b_application app, S05DTDB.tpt2b_application parent, ");
		sql.append("S05DTDB.tpt2e_app_role approle ");
		sql.append("WHERE app.pt2b_appid = approle.pt2e_appid ");
		sql.append("AND app.pt2b_seq_no = approle.pt2e_seq_no ");
		sql.append("AND app.pt2b_appid = parent.pt2b_appid ");
		sql.append("AND parent.pt2b_seq_no = 0 ");
		sql.append("AND approle.pt2e_role_cd = ? ");
		sql.append("AND app.pt2b_login_acc NOT IN ('E','N') ");
		sql.append("AND app.pt2b_active_cd = 'A' ");
		sql.append("AND parent.pt2b_active_cd = 'A' ");
		sql.append("ORDER BY parent.pt2b_app_name, app.pt2b_seq_no ");
		Session session = urlsessionFactory.openSession();
		List list = session.createSQLQuery(sql.toString())
				.addEntity("app", "com.aurora.quicklinksservices.beans.App")
				.setString(0, "EMP").list();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			App temp = (App) list.get(i);
			sb.append(temp.getAppName() + "---->" + temp.getAppDesc() + "<br/>");
			sb.append(temp.getAppURL() + "----><br/>");

		}
		session.close();
		// System.out.println("result"+sb.toString());
		return list;

	}

	/* Service for getting user details */

	@Override
	public List findUserDetails(String userid) {
		// TODO Auto-generated method stub
		Long userId=43l;
		Session session = urlsessionFactory.openSession();
		session.beginTransaction();
		Query query = session
				.createQuery("from com.aurora.quicklinksservices.beans.User where userID="
						+ userId);
		List list = query.list();
		System.out.println("TEST -- > " + list.size());
		List<User> appList = new ArrayList<User>();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			User temp = (User) list.get(i);
			sb.append(temp.getFirstName() + "---->" + temp.getLastName()
					+ "<br/>");
			sb.append(temp.getPortalID() + "----><br/>");
			appList.add(temp);
		}
		session.close();
		// System.out.println("result"+sb.toString());
		return appList;
	}

	/* Service for getting user saved quick links */

	public List findUserAppsByUser(Long userid) {
		//userid = 43l;
		Session session = urlsessionFactory.openSession();
		session.beginTransaction();
		UserAppResponseBean bean = null;
		List<UserAppResponseBean> listUserAppBean = new ArrayList<UserAppResponseBean>();
		List list = session.createCriteria(UserApp.class)
				.add(Expression.eq("userAppKey.userId", userid))
				.add(Expression.eq("activeCd", "A")).list();

		System.out.println("findUserAppsByUsers -- > " + list);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			bean = new UserAppResponseBean();
			UserApp temp = (UserApp) list.get(i);
			sb.append(temp.getCreated() + "----application---->"
					+ temp.getApplication() + "<br/>");
			bean.setAppName(temp.getApplication().getAppName().trim());
			bean.setAppUrl(temp.getApplication().getAppURL().trim());
			bean.setUserId(userid + "");
			listUserAppBean.add(bean);
			// appList.add(temp);
		}
		session.close();
		// System.out.println("result"+sb.toString());
		return listUserAppBean;

	}

	/* find userapp */

	@Override
	public UserAppResponseBean readUserApp(UserAppKey userAppKey) {
		Session session = urlsessionFactory.openSession();
		session.beginTransaction();
		UserApp userApp = (UserApp) session.createCriteria(UserApp.class)
				.add(Expression.idEq(userAppKey)).uniqueResult();
		System.out.println("userApp from query --- " + userApp);
		UserAppResponseBean userAppResponseBean = new UserAppResponseBean();
		if (null != userApp) {
			System.out.println(userApp.getUserAppKey());
			System.out.println(userApp.getApplication());
			userAppResponseBean.setAppId(userApp.getUserAppKey().getAppId());
			userAppResponseBean.setSeqNo(userApp.getUserAppKey().getSeqNo()
					.toString());
			userAppResponseBean.setUserId(userApp.getUserAppKey().getUserId()
					.toString());
		}
		System.out.println("userAppResponseBean!!!!!!  " + userAppResponseBean);
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
		
		System.out.println("Inserting User App!!!!!!" + userApp);
		
		try {  
		 session = urlsessionFactory.openSession();
		txn=session.beginTransaction();
		session.save(userApp);
		txn.commit();
		System.out.println("Inserting User App!!!!!!" + userApp);
		
		} catch (Exception e) { 
		    System.out.println(e.getMessage());
		} finally {
		    if (!txn.wasCommitted()) {
		        txn.rollback();
		    }

		    session.flush();  
		    session.close();   
		}

}
	
}
