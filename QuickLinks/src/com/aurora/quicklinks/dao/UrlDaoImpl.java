package com.aurora.quicklinks.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.aurora.quicklinks.beans.CompleteURLBean;
import com.aurora.quicklinks.beans.UrlBean;

@Repository
public class UrlDaoImpl implements UrlDao{
	
	@Autowired
	private HibernateTemplate urlTemplate;
	
	public void setUrlTemplate(HibernateTemplate urlTemplate) {
		this.urlTemplate = urlTemplate;
	}
	
	public void updateUrl(List<UrlBean> listBean){
			urlTemplate.saveOrUpdateAll(listBean);
		
		
	}
	public List<UrlBean> listUrlBean(String userid){
		//List<UrlBean> listBean = null;
		/*String hquery = "SELECT u.id,u.url,u.userid "+
        "FROM UrlBean u "+
        "WHERE u.userid = '"+userid+"'";*/
		//System.out.println(urlTemplate.find(hquery));
		List<UrlBean> listBean = urlTemplate.find("from UrlBean u where u.userid ='"+userid+"'");
		
		System.out.println("listBean---->"+listBean);
		for(UrlBean bean : listBean){
			System.out.println("id--->"+bean.getId());
			System.out.println("url--->"+bean.getUrl());
			System.out.println("userid--->"+bean.getUserid());
		}
		
		//listBean = new ArrayList<UrlBean>();
		//UrlBean urlBean= new UrlBean();
		//urlBean.setId("1");
		//urlBean.setUrl("testtt");
		//urlBean.setUserid("testuserid");
		//listBean.add(urlBean);
		return listBean;
		
	}
	
	
	
	public List<CompleteURLBean> listCopmpleteUrlBean(){
		//List<UrlBean> listBean = null;
		/*String hquery = "SELECT u.id,u.url,u.userid "+
        "FROM UrlBean u "+
        "WHERE u.userid = '"+userid+"'";*/
		//System.out.println(urlTemplate.find(hquery));
		List<CompleteURLBean> listBean = urlTemplate.find("from CompleteURLBean");
		
		System.out.println("listBean---->"+listBean);
		for(CompleteURLBean bean : listBean){
			System.out.println("id--->"+bean.getId());
			System.out.println("url--->"+bean.getUrl());
			System.out.println("userid--->"+bean.getDescription());
		}
		
		//listBean = new ArrayList<UrlBean>();
		//UrlBean urlBean= new UrlBean();
		//urlBean.setId("1");
		//urlBean.setUrl("testtt");
		//urlBean.setUserid("testuserid");
		//listBean.add(urlBean);
		return listBean;
		
	}
	
	


}
