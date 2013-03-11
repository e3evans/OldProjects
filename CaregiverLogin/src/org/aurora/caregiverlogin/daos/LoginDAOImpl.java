package org.aurora.caregiverlogin.daos;

import java.util.List;

import org.aurora.caregiverlogin.beans.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LoginDAOImpl implements LoginDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}

	/* Service for getting user details */
	@SuppressWarnings("unchecked")
	public User findUserDetails(String loginid) {
		User systemUser = null;
		Session session = null;
		session = getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(User.class).add(
				Restrictions.eq("loginId", loginid));
		List<User> temp = criteria.list();
		if (temp != null && temp.size() > 0)
			systemUser = temp.get(0);
		return systemUser;
	}
}