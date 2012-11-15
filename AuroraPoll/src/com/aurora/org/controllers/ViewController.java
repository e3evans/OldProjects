package com.aurora.org.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aurora.hibernate.poll.dao.PollDAO;




@Controller
@RequestMapping("VIEW")
public class ViewController {
	
	@Autowired
	private PollDAO pollDAO;
	
	public static final String JSP_VIEW = "view";
	
	private static Log log = LogFactory.getLog(ViewController.class);
	
	@RequestMapping
	public String defaultView(){
		log.info("HERE WE GO!!");
		
		pollDAO.getLatestPoll();

		
		return JSP_VIEW;
	}

	public PollDAO getPollDAO() {
		return pollDAO;
	}

	public void setPollDAO(PollDAO pollDAO) {
		this.pollDAO = pollDAO;
	}
	
	
}
