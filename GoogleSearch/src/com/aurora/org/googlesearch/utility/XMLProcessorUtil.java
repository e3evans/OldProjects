package com.aurora.org.googlesearch.utility;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.portlet.PortletPreferences;
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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.aurora.controllers.EditController;
import com.aurora.controllers.ViewController;
import com.aurora.webservice.client.GoogleServiceClient;

public abstract class XMLProcessorUtil {

	
	public static StringWriter getSearchResultsHTML(String xslStylesheet,String googleURL,String clusterURL,String contextPath,PortletPreferences prefs){
		StringWriter writer = new StringWriter();
		try {

			StringReader searchResults = new StringReader(GoogleServiceClient.getInstance().doGet(googleURL, ""));
			StringReader clusterResults = new StringReader(GoogleServiceClient.getInstance().doGet(clusterURL, ""));
			
			Source xsltSource = getXSLStyesheetStream(xslStylesheet);
			Result result = new StreamResult(writer);
	
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer(xsltSource);
			transformer.setParameter("contextPath", contextPath);
			transformer.setParameter("search_env", prefs.getValue(EditController.PREF_SEARCH_ENV, "NOTSET"));
			transformer.transform(getMergedResultsDoc(searchResults,clusterResults,prefs), result);
			
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
	
	private static StreamSource getXSLStyesheetStream(String feedPath){
		ClassLoader cl = XMLProcessorUtil.class.getClassLoader();
		InputStream in = cl.getResourceAsStream(feedPath);
		return new StreamSource(in);
	}
	
	private static DOMSource getMergedResultsDoc(StringReader searchSource,StringReader clusterSource,PortletPreferences prefs) {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;
			DOMSource resultsDom=null;
			Document searchDoc = null;
			Document clusterDoc = null;
			
			String [] collections = prefs.getValues(ViewController.PREF_COLLECTIONS, new String[0]);
			
			try {
				builder = factory.newDocumentBuilder();
				XPathFactory xFactory = XPathFactory.newInstance();
				XPath xpath = xFactory.newXPath();
				/*
				 * Create Search Results Document
				 */
				InputSource searchResults = new InputSource();
				searchResults.setCharacterStream(searchSource);
				searchDoc = builder.parse(searchResults);
				if (xpath.evaluate("//GSP/RES",searchDoc,XPathConstants.NODE)!=null){
					/*
					 * Create Cluster Results Document
					 */
					InputSource clusterResults = new InputSource();
					clusterResults.setCharacterStream(clusterSource);
					clusterDoc = builder.parse(clusterResults);
					/*
					 * Extract the cluster results from the original Document
					 */
					Node cluster = (Node)xpath.evaluate("//toplevel/Response/cluster", clusterDoc,XPathConstants.NODE);
//					System.out.println("HERE!!" + collections.length);
					/*
					 * Append Root and clear out XML Document from memory.
					 */
					Node searchRoot = searchDoc.getDocumentElement();
					if (cluster!=null)searchRoot.appendChild(searchDoc.importNode(cluster,true));
					/*
					 * Append the various configured collections
					 */
					
					if (collections.length>0){
						for (int i=0;i<collections.length;i++){
							String [] temp = collections[i].split(",");
							Element collectionElement = searchDoc.createElement("COLLECTION");
							collectionElement.setAttribute("displayName", temp[0]);
							collectionElement.setAttribute("collectName", temp[1]);
							searchRoot.appendChild(collectionElement);
						}
					}
				}
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
//	private static void printXML(DOMSource source){
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
