package com.cisco.swtg.scim.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;




@Controller
public class QueryBuilderController {
	
	
	
	
	private static final String BASE_URL="http://scim-stage:7474/rest/";
	

	private static final Logger logger = Logger.getLogger( QueryBuilderController.class);

	
	@RequestMapping(value = "/Popularityquerybuilder.htm")
	
	public ModelAndView onShowPopularityForm() {
		logger.debug("Inside  GET method");
		return new ModelAndView("querybuilder");
	}

	/* method responsible for creating url for popularity service*/
	
    @RequestMapping(value = "/popularityurldisplay.htm", method = RequestMethod.POST)
	
	public ModelAndView displayPopularityURl(HttpServletRequest request) {
	
		logger.debug("Inside  GET method");
		
		String format=request.getParameter("format");
		String domain=request.getParameter("domain");
		String accessibility=request.getParameter("accessibility");
		String source=request.getParameter("source");
		String pattern=request.getParameter("pattern");
		String servername=request.getParameter("servername");
		String daterange=request.getParameter("daterange");
		String fieldname=request.getParameter("fieldname");
		String fieldvalue=request.getParameter("fieldvalue");
		String popularitythreshold=request.getParameter("popularitythreshold");
		String popularitythresholdsource=request.getParameter("popularitythresholdsource");
		String qualitythreshold=request.getParameter("qualitythreshold");
		String qualitythresholdsource=request.getParameter("qualitythresholdsource");
		String sortsource=request.getParameter("sortsource");
		String returncount=request.getParameter("returncount");
		String begincount=request.getParameter("begincount");
		
		
		StringBuilder url = new StringBuilder(BASE_URL).append("popularity").append("/domain/").append(domain).append("/accessibility/").append(accessibility).append("/source/").append(source).append("/pattern/").append(pattern).append("/servername/").append(servername).
				append("/daterange/").append(daterange).append("/format/").append(format).append("?fieldname=").append(fieldname).append("&fieldvalue=").append(fieldvalue).append("&popularitythreshold=").append(popularitythreshold).append("&popularitythresholdsource=").append(popularitythresholdsource).append("&qualitythreshold=").append(qualitythreshold).append("&qualitythresholdsource=").append(qualitythresholdsource).append("&sortsource=").append(sortsource)
				.append("&returncount=").append(returncount).append("&begincount=").append(begincount);
		
		
		return new ModelAndView("querybuilderoutput","url" ,url);
	}



   @RequestMapping(value = "/Relatedyquerybuilder.htm")
	
	public ModelAndView onShowRelatedForm() {
		logger.debug("Inside  GET method");
		return new ModelAndView("relatedquerybuilder");
	}

	
/* method responsible for creating url for Related service*/
   
    @RequestMapping(value = "/relatedurldisplay.htm", method = RequestMethod.POST)
	
	public ModelAndView displayRelatedURl(HttpServletRequest request) {
	
		logger.debug("Inside  GET method");
		
		String format=request.getParameter("format");
		String domain=request.getParameter("domain");
		String accessibility=request.getParameter("accessibility");
		String source=request.getParameter("source");
		String pattern=request.getParameter("pattern");
		String servername=request.getParameter("servername");
		String daterange=request.getParameter("daterange");
		String fieldname=request.getParameter("fieldname");
		String fieldvalue=request.getParameter("fieldvalue");
		String relatedto =request.getParameter("relatedto");
		String threshold=request.getParameter("threshold");
		String returncount=request.getParameter("returncount");
		String begincount=request.getParameter("begincount");
		
		
		StringBuilder url = new StringBuilder(BASE_URL).append("related").append("/domain/").append(domain).append("/accessibility/").append(accessibility).append("/source/").append(source).append("/pattern/").append(pattern).append("/servername/").append(servername).
				append("/daterange/").append(daterange).append("/format/").append(format).append("?fieldname=").append(fieldname).append("&fieldvalue=").append(fieldvalue).append("&relatedto=").append(relatedto).append("&threshold=").append(threshold).append("&returncount=").append(returncount).append("&begincount=").append(begincount);
		
            return new ModelAndView("querybuilderoutput","url" ,url);
	}



  @RequestMapping(value = "/Streamquerybuilder.htm")
	
	public ModelAndView onShowStreamForm() {
		logger.debug("Inside  GET method");
		return new ModelAndView("streamquerybuilder");
	}

  /* method responsible for creating url for Stream service*/
	
    @RequestMapping(value = "/streamurldisplay.htm", method = RequestMethod.POST)
	
	public ModelAndView displayStreamURl(HttpServletRequest request) {
	
		logger.debug("Inside  GET method");
		
		String format=request.getParameter("format");
		String domain=request.getParameter("domain");
		String accessibility=request.getParameter("accessibility");
		String source=request.getParameter("source");
		String pattern=request.getParameter("pattern");
		String servername=request.getParameter("servername");
		String daterange=request.getParameter("daterange");
		String type=request.getParameter("type");
		String fieldname=request.getParameter("fieldname");
		String fieldvalue=request.getParameter("fieldvalue");
		String relatedto =request.getParameter("relatedto");
		String qualitysource=request.getParameter("qualitysource");
		String threshold=request.getParameter("threshold");
		String returncount=request.getParameter("returncount");
		String begincount=request.getParameter("begincount");
		
		
		StringBuilder url = new StringBuilder(BASE_URL).append("stream").append("/domain/").append(domain).append("/accessibility/").append(accessibility).append("/source/").append(source).append("/pattern/").append(pattern).append("/servername/").append(servername).
				append("/daterange/").append(daterange).append("/format/").append(format).append("?type=").append(type).append("&fieldname=").append(fieldname).append("&fieldvalue=").append(fieldvalue).append("&relatedto=").append(relatedto).append("&qualitysource=").append(qualitysource).append("&threshold=").append(threshold).append("&returncount=").append(returncount).append("&begincount=").append(begincount);
		
            return new ModelAndView("querybuilderoutput","url" ,url);
	}


















}
