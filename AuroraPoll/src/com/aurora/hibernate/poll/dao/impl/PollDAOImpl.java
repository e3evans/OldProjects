package com.aurora.hibernate.poll.dao.impl;

import java.util.Date;
import java.util.List;

import com.aurora.hibernate.poll.beans.Poll;
import com.aurora.hibernate.poll.dao.PollDAO;
import org.apache.commons.lang.StringUtils;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PollDAOImpl implements PollDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public List<Poll> getLastTenPollResults() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Poll getLatestPoll() {
		// TODO Auto-generated method stub
		return getPollByDate(new Date());
	}

    public Poll getPollByDate(final Date date) {
    	DetachedCriteria maxPollDate = DetachedCriteria.forClass(Poll.class)
    			.setProjection(Property.forName("data").max())
    			.add(Restrictions.le("date", date));
    	Session session = null;
    	try{
    		session = sessionFactory.openSession();
    		Criteria criteria = session.createCriteria(Poll.class)
    				.add(Property.forName("date").eq(maxPollDate));
    		criteria.addOrder(Order.desc("date"));
    		List tempList = criteria.list();
    		System.out.println("LIST LENGTH:  "+tempList.size());
    	}catch (Exception e){
    		e.printStackTrace();
    	}finally{
    		
    		if (session!=null)session.close();
    	}
    	
    	
    	
//        return (Poll) this.getHibernateTemplate().execute(new HibernateCallback() {
//            public Object doInHibernate(Session session) {
//                // Sub select Max date prior or equal to today for this type
//                DetachedCriteria maxPollDate = DetachedCriteria.forClass(Poll.class)
//                        .setProjection(Property.forName("date").max())
//                        .add(Expression.le("date", date));
//                // Select record equal to Max date for this type
//                Criteria cri = session.createCriteria(Poll.class)
//                        .add(Property.forName("date").eq(maxPollDate));
//                addSortParamCriteria(cri, null);
//                List newsList = cri.list();
//                Iterator i = newsList.iterator();
//                if (i != null && i.hasNext()) {
//                    Poll poll = (Poll) i.next();
//                    // Lazy initialize poll options
//                    List poList = poll.getPollOptions();
//                    for (Iterator o = poList.iterator(); o.hasNext(); ) {
//                        PollOption po = (PollOption) o.next();
//                    }
//                    return poll;
//                } else
//                    return null;
//            }
//        });
    	return null;
    }
	
}
