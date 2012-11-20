package com.eblue.springtest.dao;

import java.util.List;

import com.eblue.springtest.beans.CompleteURLBean;
import com.eblue.springtest.beans.UrlBean;

public interface UrlDao {
	
	public void updateUrl(List<UrlBean> listBean);
	public List<UrlBean> listUrlBean(String userid);
	public List<CompleteURLBean> listCopmpleteUrlBean();

}
