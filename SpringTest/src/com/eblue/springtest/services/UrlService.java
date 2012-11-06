package com.eblue.springtest.services;

import java.util.List;

import com.eblue.springtest.beans.CompleteURLBean;
import com.eblue.springtest.beans.UrlBean;

public interface UrlService {
	
	public void updateUrl(List<UrlBean> listBean);
	public List<UrlBean> listUrlBean(String userid);
	public List<CompleteURLBean> listCopmpleteUrlBean();

}
