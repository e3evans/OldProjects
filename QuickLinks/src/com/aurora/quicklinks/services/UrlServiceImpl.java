package com.aurora.quicklinks.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.aurora.quicklinks.beans.CompleteURLBean;
import com.aurora.quicklinks.beans.UrlBean;
import com.aurora.quicklinks.beans.UrlFormBean;
import com.aurora.quicklinks.dao.UrlDao;

@Service(value = "urlService")
public class UrlServiceImpl implements UrlService {

	@Autowired
	private UrlDao urlDAO;

	public List<UrlBean> listUrlBean(String userid) {
		return urlDAO.listUrlBean(userid);

	}

	public List<CompleteURLBean> listCopmpleteUrlBean() {
		return urlDAO.listCopmpleteUrlBean();

	}

	public void updateUrl(List<UrlBean> listBean) {
		urlDAO.updateUrl(listBean);
	}

}
