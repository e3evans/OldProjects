package com.aurora.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
	private static String GOOGLE_CLUSTER_URL = "http://google.aurora.org/cluster?site=default_collection&client=default_collection&coutput=xml"; //&q=
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
					GOOGLE_CLUSTER_URL+"&q="+URLEncoder.encode(hsreq.getParameter("q"),"ISO-8859-1")).toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.put("searchForm", form);
		return new ModelAndView("view","searchForm",form);
	}
	
	
	@ResourceMapping(value="search")
	public ModelAndView doSearch(ResourceRequest request, ResourceResponse responses, @SuppressWarnings("rawtypes") Map model,@ModelAttribute("searchForm")SearchForm form) throws UnsupportedEncodingException{
		if (form == null)form = new SearchForm();
//		String q = request.getParameter("q");
//		System.out.println(request.getParameter("q"));
//		System.out.println(request.getParameter("en"));
//		System.out.println(request.getParameter("sn"));
//		form.setSearchResults_frag(getSearchResultsHTML("xsl/searchResults_frag.xsl", TEST_URL+"&q=news"+
//				"&num="+request.getParameter("en")+"&start="+request.getParameter("sn")).toString());
		form.setSearchResults_frag(getSearchResultsHTML("xsl/searchResultsMod.xsl", GOOGLE_SEARCH_URL+"&q="+URLEncoder.encode(request.getParameter("q"),"ISO-8859-1")+
				"&num="+request.getParameter("en")+"&start="+request.getParameter("sn")+"&page="+request.getParameter("page")
				,GOOGLE_CLUSTER_URL+"&q="+URLEncoder.encode(request.getParameter("q"),"ISO-8859-1")).toString());
		return new ModelAndView("search_frag","searchForm",form);
	}
	
	private StreamSource getXSLStyesheetStream(String feedPath){
		ClassLoader cl = this.getClass().getClassLoader();
		InputStream in = cl.getResourceAsStream(feedPath);
		return new StreamSource(in);
	}
	
	private StringWriter getSearchResultsHTML(String xslStylesheet,String googleURL,String clusterURL){
		StringWriter writer = new StringWriter();
		try {
//			System.out.println(googleURL);
//			System.out.println(clusterURL);
//			Source xmlSource = new StreamSource(new StringReader(GoogleServiceClient.getInstance().doGet(googleURL, "")));
			System.out.println(GoogleServiceClient.getInstance().doGet(googleURL, ""));
			StringReader searchResults = new StringReader(GoogleServiceClient.getInstance().doGet(googleURL, ""));
			StringReader clusterResults = new StringReader(GoogleServiceClient.getInstance().doGet(clusterURL, ""));
			
			Source xsltSource = getXSLStyesheetStream(xslStylesheet);
			Result result = new StreamResult(writer);
			
	
			
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer(xsltSource);

			transformer.transform(getMergedResultsDoc(searchResults,clusterResults), result);
			System.out.println(result.toString());
			
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
//				searchDoc = builder.newDocument();
//				clusterDoc = builder.newDocument();
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
				clusterDoc=null;
				searchDoc=null;
			}
			
			
			
		return resultsDom;
	}
	
	
}
