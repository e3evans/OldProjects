package com.aurora.hibernate.poll.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.aurora.hibernate.poll.beans.Poll;
import com.aurora.hibernate.poll.beans.PollOption;
import com.aurora.hibernate.poll.dao.PollDAO;
import com.aurora.hibernate.util.SortByParam;

@Repository
public class PollDAOImpl implements PollDAO {

	@Autowired
	private SessionFactory sessionFactory;
	

	
	@Override
	public Poll getLatestPoll() {
		// TODO Auto-generated method stub
		return getPollByDate(new Date());
	}
	

	
	public void incrementPoll(PollOption pollOption){
		Session session = null;
		try{
			session=sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			pollOption.setCount(pollOption.getCount()+1);
			session.update(pollOption);
			tx.commit();
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			if (session!=null)session.close();
		}
		
	}
	
    public Poll getPollByDate(final Date date) {
    	DetachedCriteria maxPollDate = DetachedCriteria.forClass(Poll.class)
    			.setProjection(Property.forName("date").max())
    			.add(Restrictions.le("date", date));
    	Session session = null;
    	Poll poll = null;
    	try{
    		session = sessionFactory.openSession();
    		Criteria criteria = session.createCriteria(Poll.class)
    				.add(Property.forName("date").eq(maxPollDate));
    		criteria.addOrder(Order.desc("date"));
    		@SuppressWarnings("rawtypes")
			List tempList = criteria.list();
    		if (tempList.size()>0) poll = (Poll)tempList.get(0);
    	}catch (Exception e){
    		e.printStackTrace();
    	}finally{
    		if (session!=null)session.close();
    	}
    	return poll;
    }
    
    @SuppressWarnings("unchecked")
	public List <Poll> getLastXNumPolls(int x){
    	x+=1;
    	ArrayList<SortByParam> sort = new ArrayList<SortByParam>();
    	sort.add(new SortByParam("date", false));
    	sort.add(new SortByParam("question", true));
    	SortByParam[] sbp = sort.toArray(new SortByParam[sort.size()]);
    	Session session = null;
    	List <Poll> pollList = new ArrayList<Poll>();
    	try{
    		session = sessionFactory.openSession();
    		Criteria criteria = session.createCriteria(Poll.class)
    				.add(Restrictions.le("date",new Date()))
    				.setMaxResults(x);
    		addSortParamCriteria(criteria, sbp);
    		pollList = criteria.list();
    		System.out.println("LISTED");
    		if (!pollList.isEmpty())pollList.remove(0);
    	}catch (Exception e){
    		e.printStackTrace();
    	}finally{
    		if (session!=null)session.close();
    	}	
    	return pollList;
    }
    protected void addSortParamCriteria(Criteria cq, SortByParam[] sortParams) {
        if (sortParams != null && sortParams.length > 0) {
            for (int i = 0; i < sortParams.length; i++) {
                SortByParam sp = sortParams[i];
                if (SortByParam.SORT_ASC.equals(sp.getOrder())) {
                    cq.addOrder(Order.asc(sp.getSortPropertyName()));
                }
                if (SortByParam.SORT_DESC.equals(sp.getOrder())) {
                    cq.addOrder(Order.desc(sp.getSortPropertyName()));
                }
            }
        }
    }
    
    
	
}
