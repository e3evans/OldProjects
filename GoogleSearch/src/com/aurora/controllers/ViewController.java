package com.aurora.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.aurora.org.googlesearch.forms.SearchForm;
import com.aurora.webservice.client.GoogleServiceClient;

@Controller
@RequestMapping("VIEW")
public class ViewController {
	
	private static String GOOGLE_SEARCH_URL = "http://google.aurora.org/search?site=default_collection&output=xml_no_dtd";
	private static String GOOGLE_CLUSTER_URL = "http://google.aurora.org/cluster?site=default_collection&client=default_collection&coutput=xml";
	private static String GOOGLE_CLICK_URL = "http://google.aurora.org/click?";
	public static String SESS_SEARCH_TERM = "google.search.term";
	public static String SEARCH_RESULTS_BOX = "searchResultsBox";

	@SuppressWarnings("unchecked")
	@RequestMapping
	public ModelAndView defaultView (RenderRequest request, RenderResponse responses, @SuppressWarnings("rawtypes") Map model,@ModelAttribute("searchForm")SearchForm form){

		HttpServletRequest hsreq= com.ibm.ws.portletcontainer.portlet.PortletUtils.getHttpServletRequest(request);
		request.getPortletSession().setAttribute(SESS_SEARCH_TERM, hsreq.getParameter("q"));
		if (form == null)form = new SearchForm();
		try {
			form.setSearchResults(getSearchResultsHTML("xsl/searchResultsMod.xsl", 
					GOOGLE_SEARCH_URL+"&q="+URLEncoder.encode(hsreq.getParameter("q"),"ISO-8859-1")+"&start=0&page=1&num=10",
					GOOGLE_CLUSTER_URL+"&q="+URLEncoder.encode(hsreq.getParameter("q"),"ISO-8859-1"),request.getContextPath()).toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.put("searchForm", form);
		return new ModelAndView("view","searchForm",form);
	}
	
	
	@ResourceMapping(value="search")
	public ModelAndView doSearch(ResourceRequest request, ResourceResponse response, @SuppressWarnings("rawtypes") Map model,@ModelAttribute("searchForm")SearchForm form) throws UnsupportedEncodingException{
		if (form == null)form = new SearchForm();
		form.setSearchResults_frag(getSearchResultsHTML("xsl/searchResultsMod.xsl", GOOGLE_SEARCH_URL+"&q="+URLEncoder.encode(request.getParameter("q"),"ISO-8859-1")+
				"&num="+request.getParameter("en")+"&start="+request.getParameter("sn")+"&page="+request.getParameter("page")
				,GOOGLE_CLUSTER_URL+"&q="+URLEncoder.encode(request.getParameter("q"),"ISO-8859-1"),request.getContextPath()).toString());
		return new ModelAndView("search_frag","searchForm",form);
	}
	
	@ResourceMapping(value="googleClick")
	public void doGoogleClick(ResourceRequest request, ResourceResponse response) throws Exception{
		Enumeration<String> e = request.getParameterNames();
		
		StringBuffer sb = new StringBuffer();
		while (e.hasMoreElements()){
			String temp = e.nextElement();
			sb.append("&"+temp+"="+URLEncoder.encode(request.getParameter(temp),"ISO-8859-1"));
//			System.out.println(temp + "--->"+request.getParameter(temp));
		}
//		System.out.println(sb.toString());
		GoogleServiceClient.getInstance().doGetNoContent(GOOGLE_CLICK_URL+sb.toString(), "");
		
	}
	
	private StreamSource getXSLStyesheetStream(String feedPath){
		ClassLoader cl = this.getClass().getClassLoader();
		InputStream in = cl.getResourceAsStream(feedPath);
		return new StreamSource(in);
	}
	
	private StringWriter getSearchResultsHTML(String xslStylesheet,String googleURL,String clusterURL,String contextPath){
		StringWriter writer = new StringWriter();
		try {

			StringReader searchResults = new StringReader(GoogleServiceClient.getInstance().doGet(googleURL, ""));
			StringReader clusterResults = new StringReader(GoogleServiceClient.getInstance().doGet(clusterURL, ""));
			
			Source xsltSource = getXSLStyesheetStream(xslStylesheet);
			Result result = new StreamResult(writer);
	
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer(xsltSource);
			transformer.setParameter("contextPath", contextPath);
			transformer.transform(getMergedResultsDoc(searchResults,clusterResults), result);
			
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
	
	private DOMSource getMergedResultsDoc(StringReader searchSource,StringReader clusterSource) {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;
			DOMSource resultsDom=null;
			Document searchDoc = null;
			Document clusterDoc = null;
			try {
				builder = factory.newDocumentBuilder();
				/*
				 * Create Search Results Document
				 */
				InputSource searchResults = new InputSource();
				searchResults.setCharacterStream(searchSource);
				searchDoc = builder.parse(searchResults);
				System.out.println("HERE++"+searchDoc.toString());
				/*
				 * Create Cluster Results Document
				 */
				InputSource clusterResults = new InputSource();
				clusterResults.setCharacterStream(clusterSource);
				clusterDoc = builder.parse(clusterResults);
				
				XPathFactory xFactory = XPathFactory.newInstance();
				XPath xpath = xFactory.newXPath();
				/*
				 * Extract the cluster results from the original Document
				 */
				Node cluster = (Node)xpath.evaluate("//toplevel/Response/cluster", clusterDoc,XPathConstants.NODE);
				
				/*
				 * Append Root and clear out XML Document from memory.
				 */
				Node searchRoot = searchDoc.getDocumentElement();
				searchRoot.appendChild(searchDoc.importNode(cluster,true));
				resultsDom = new DOMSource(searchDoc);
//				printXML(resultsDom);
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XPathExpressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				/*
				 * Clear the docs from memory.
				 */
				clusterDoc=null;
				searchDoc=null;
			}
			
			
			
		return resultsDom;
	}
	/*
	 * TEST METHOD FOR PRINTING OUT GENERATED XML DOC  **DELETE LATER**
	 */
//	private void printXML(DOMSource source){
//		StringWriter writer = new StringWriter();
//		StreamResult result = new StreamResult(writer);
//		TransformerFactory tf = TransformerFactory.newInstance();
//		Transformer transformer;
//		try {
//			transformer = tf.newTransformer();
//			transformer.transform(source, result);
//			System.out.println(writer.toString());
//		} catch (TransformerConfigurationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (TransformerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//	}
	
	
}
