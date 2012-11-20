package com.aurora.quicklinks.dao;

import java.util.List;

import com.aurora.quicklinks.beans.CompleteURLBean;
import com.aurora.quicklinks.beans.UrlBean;

public interface UrlDao {
	
	public void updateUrl(List<UrlBean> listBean);
	public List<UrlBean> listUrlBean(String userid);
	public List<CompleteURLBean> listCopmpleteUrlBean();

}
