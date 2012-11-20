package com.aurora.quicklinksservices.services;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.aurora.quicklinksservices.daos.QuickLinksAPPDAO;



@Service
public class QuickLinksServiceImpl  extends SpringBeanAutowiringSupport implements QuickLinksService{

	
	@Autowired
	private QuickLinksAPPDAO quickLinksAPPDAO;
	public List retrieveAvailAppListByRole(String roleCd) {
		// TODO Auto-generated method stub
		System.out.println("retrieveAvailAppListByRole");
		return quickLinksAPPDAO.findAvailAppListByRole(roleCd);
	}
	@Override
	public List retrieveUserDetails(String userid) {
		// TODO Auto-generated method stub
		return quickLinksAPPDAO.findUserDetails(userid);
	}
	
}
