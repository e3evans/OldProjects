package com.aurora.org.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;




@Controller
@RequestMapping("VIEW")
public class ViewController {
	
	public static final String JSP_VIEW = "view";
	
	private static Log log = LogFactory.getLog(ViewController.class);
	
	@RequestMapping
	public String defaultView(){
		log.info("HERE WE GO!!");
		return JSP_VIEW;
	}
	
	
}
