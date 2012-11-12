package com.aurora.quicklinksservices.daos;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;

import com.aurora.quicklinksservices.beans.Application;

@Repository
public class QuickLinksAPPDAOImpl  implements
		QuickLinksAPPDAO {
	
	@Autowired
	private SessionFactory urlsessionFactory;
	
	
	public List findAvailAppListByRole(String roleCd) {
		Session session = urlsessionFactory.openSession();
		session.beginTransaction();
		
		Query query = session.createQuery("from com.aurora.quicklinksservices.beans.Application");
		List list = query.list();
		System.out.println("TEST -- > "+list.size());
		List<Application> appList = new ArrayList<Application>();
		StringBuffer sb = new StringBuffer();
		for (int i=0;i<list.size();i++){
			Application temp = (Application)list.get(i);
			sb.append(temp.getAppName()+"---->"+temp.getAppDesc()+"<br/>");
			sb.append(temp.getAppURL()+"----><br/>");
			appList.add(temp);
		}
		session.close();
		System.out.println("result"+sb.toString());
		return appList;
	
	}


	/*public List findAvailAppListByRole(String roleCd) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT {app.*} ");
		sql.append("FROM tpt2b_application app, tpt2b_application parent, ");
		sql.append("tpt2e_app_role approle ");
		sql.append("WHERE app.pt2b_appid = approle.pt2e_appid ");
		sql.append("AND app.pt2b_seq_no = approle.pt2e_seq_no ");
		sql.append("AND app.pt2b_appid = parent.pt2b_appid ");
		sql.append("AND parent.pt2b_seq_no = 0 ");
		sql.append("AND approle.pt2e_role_cd = ? ");
		sql.append("AND app.pt2b_login_acc NOT IN ('E','N') ");
		sql.append("AND app.pt2b_active_cd = 'A' ");
		sql.append("AND parent.pt2b_active_cd = 'A' ");
		sql.append("ORDER BY parent.pt2b_app_name, app.pt2b_seq_no ");
		return urlsessionFactory.openSession().createSQLQuery(sql.toString()).addEntity("app","org/aurora/quicklinksservices/beans/App").setString(0, roleCd)
				.list();
	}
*/
}
