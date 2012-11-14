package com.aurora.quicklinks.services;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.client.RestTemplate;

import com.aurora.quicklinks.beans.CompleteURLBean;
import com.aurora.quicklinks.beans.UrlBean;
import com.aurora.quicklinks.beans.UrlFormBean;


@Service(value = "urlService")
public class UrlServiceImpl implements UrlService {
	
	
	
	private RestTemplate restTemplate;

	
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	
/*
	public List<UrlBean> listUrlBean(String userid) {
		return urlDAO.listUrlBean(userid);

	}*/

	public void listCopmpleteUrlBean() {
		String responseText="";
		HttpHeaders entityHeaders = new HttpHeaders();
		entityHeaders.set("content-type", "application/json");

		HttpEntity<String> requestEntity = new HttpEntity<String>(entityHeaders);
		
		
		try {
		
			ResponseEntity<String> result = restTemplate.exchange("https://localhost:10032/QuickLinksServiceApp/rest/test/results", HttpMethod.GET, requestEntity,
					String.class);
			System.out.println("web service call end with request path : ");
		// responseText = result.getBody();
		System.out.println(result);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stacktrace = sw.toString();
			
			System.out.println("tracing "+stacktrace);
		}
		
		
		
		

	}

	/*public void updateUrl(List<UrlBean> listBean) {
		urlDAO.updateUrl(listBean);
	}*/

}
