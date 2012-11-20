package org.test;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aurora.hibernate.HibernateUtil;
import org.aurora.hibernate.beans.Application;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.context.internal.ThreadLocalSessionContext;

/**
 * Servlet implementation class HibernateTester
 */
public class HibernateTester extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HibernateTester() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("from org.aurora.hibernate.beans.Application");
		List list = query.list();
		System.out.println("TEST -- > "+list.size());
		StringBuffer sb = new StringBuffer();
		for (int i=0;i<list.size();i++){
			Application temp = (Application)list.get(i);
			sb.append(temp.getAppName()+"---->"+temp.getAppDesc()+"<br/>");
		}
		session.close();
		
		response.getWriter().println(sb.toString());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
