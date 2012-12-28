package com.aurora.contorllers;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.aurora.org.googlesearchpoc.forms.SearchForm;
import com.aurora.webservice.client.GoogleServiceClient;

@Controller
@RequestMapping("VIEW")
public class ViewController {
	
	private static String TEST_URL = "http://google.aurora.org/search?site=default_collection&output=xml_no_dtd";

	@SuppressWarnings("unchecked")
	@RequestMapping
	public ModelAndView defaultView (RenderRequest request, RenderResponse responses, @SuppressWarnings("rawtypes") Map model,@ModelAttribute("searchForm")SearchForm form){
		HttpServletRequest hsreq= com.ibm.ws.portletcontainer.portlet.PortletUtils.getHttpServletRequest(request);
        System.out.println(hsreq.getParameter("q"));
        
        System.out.println(getSearchResultsHTML("xsl/searchResultsMod.xsl", TEST_URL+"&q="+hsreq.getParameter("q")));
		if (form == null)form = new SearchForm();
		form.setSearchResults(getSearchResultsHTML("xsl/searchResultsMod.xsl", TEST_URL+"&q="+hsreq.getParameter("q")).toString());
		model.put("searchForm", form);
		return new ModelAndView("view","searchForm",form);
	}
	
	@ActionMapping(params = "action=doSearch")
	public void doSearch(ActionRequest request, ActionResponse response,@ModelAttribute("searchForm")SearchForm form){
		response.setRenderParameter("searchString", form.getSearchString());
		System.out.println(form.getSearchString());
		System.out.println("ACTION");
	}
	
	@ResourceMapping(value="search")
	public ModelAndView doSearch(){
		return null;
	}
	
	private StreamSource getXSLStyesheetStream(String feedPath){
		ClassLoader cl = this.getClass().getClassLoader();
		InputStream in = cl.getResourceAsStream(feedPath);
		return new StreamSource(in);
	}
	
	private StringWriter getSearchResultsHTML(String xslStylesheet,String googleURL){
		StringWriter writer = new StringWriter();
		try {
			Source xmlSource = new StreamSource(new StringReader(GoogleServiceClient.getInstance().doGet(googleURL, "")));
			Source xsltSource = getXSLStyesheetStream(xslStylesheet);
			Result result = new StreamResult(writer);
			
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer(xsltSource);
			transformer.transform(xmlSource, result);
			
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return writer;
	}
	
	
}
