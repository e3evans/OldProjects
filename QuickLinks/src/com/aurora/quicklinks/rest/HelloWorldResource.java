package com.aurora.quicklinks.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aurora.quicklinks.beans.UrlBean;
import com.aurora.quicklinks.services.UrlService;


@Controller
@RequestMapping("/listurl")
public class HelloWorldResource {

@Autowired
@Qualifier("urlService")
private UrlService urlService;	

public void setUrlService(UrlService urlService) {
	this.urlService = urlService;
}

public UrlService getUrlService() {
	return urlService;
}

	
@RequestMapping(value = "/{id}", method = RequestMethod.GET)
@ResponseBody
 public String sayHello(@PathVariable("id") String urlid) {
  System.out.println();
   System.out.println("testlist --->"+urlService.listUrlBean("test"));
   StringBuffer strResponse = new StringBuffer();
   List<UrlBean> listUrlBean = urlService.listUrlBean("test");
   
   
   strResponse.append("<table><tr><td><B> id </B></td> <td><B> url </B> </td> <td><B>  userid </B></td> </tr>");
   for(UrlBean urlBean : listUrlBean){
	   strResponse.append("<tr><td>"+urlBean.getId()+"</td> <td>"+urlBean.getUrl()+"</td> <td>"+urlBean.getUserid()+"</td> </tr>");
   }
   strResponse.append("</table>");
   
   return strResponse.toString();
 }

/*
@Path("/helloworld")
public class HelloWorldResource {

 @GET
 @Produces("text/plain")
 public String sayHello(@QueryParam("world") String world) {
  System.out.println();
	 return "Hello " + world;
 }*/

}