package com.aurora.hibernate.login.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.aurora.hibernate.login.beans.User;
import com.aurora.hibernate.login.dao.LoginDAO;


@Repository
public class LoginDAOImpl extends BaseDAOImpl implements LoginDAO{

	/* Service for getting user details */

	@SuppressWarnings("unchecked")
	public User findUserDetails(String loginid) {
		User systemUser=null;
		Session session = null;
		session=getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(User.class).add(Restrictions.eq("loginId", loginid));
		List<User>temp = criteria.list();
		if (temp!=null && temp.size()>0)systemUser=temp.get(0);
		return systemUser;
	}
	
	
}
