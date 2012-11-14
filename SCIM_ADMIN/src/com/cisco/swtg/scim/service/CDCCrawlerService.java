package com.cisco.swtg.scim.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;


public class CDCCrawlerService {
	private static final Logger logger = Logger.getLogger(CDCCrawlerService.class);
	public List<String> serachCSO(String normalizedUrl)
		{
		System.out.println("Inside Search CSO method with url "+normalizedUrl);
		String concept = "";
		String name = "";
		String value = null;
		String primary = "";
		String secondary = "";
		List<String> list = new ArrayList<String>();
		try
			{
			String queryXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> " +
					"<QUERYPARAMETERS debug=\"\"> " +
					"<APP_ID>866838c15c</APP_ID>" +
			"<APP federatedSourceName=\"cdc\" /> " +
			"<QUERY><TERM value=\"INcdcurl:"+normalizedUrl+"\"/></QUERY>" +
					"<SECURITY_ID>bsolero</SECURITY_ID>" +
					"<OFFSET>0</OFFSET>" +
					"<NUM_RESULTS>10</NUM_RESULTS>" +
					"</QUERYPARAMETERS>";
	

			//String serviceUrl = "http://wsgi-stage.cisco.com/cso/srch/SearchService";
			String serviceUrl = "http://wsgi.cisco.com/cso/srch/SearchService";
	
			PostMethod method = new PostMethod (serviceUrl);
	
			method.setParameter("query", queryXML);
	
			HttpClient httpClient = new HttpClient();
			
			UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("searchcso.gen","cisco123");
	
			httpClient.getState().setCredentials(AuthScope.ANY, credentials);
	
			httpClient.getState().setCredentials(
			new AuthScope("cisco.com", AuthScope.ANY_PORT,
			AuthScope.ANY_REALM, AuthScope.ANY_SCHEME), credentials);
	
			httpClient.getParams().setAuthenticationPreemptive(true);
	
			int status = httpClient.executeMethod(method);
	
			if (status != HttpStatus.SC_OK) {
			throw new IOException("Oops .. IO Exception. HTTP " + status);
			}
	
			//Print the return XML
			System.out.println(method.getResponseBodyAsString());
			
			//
			//XML Parsing
			//
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(new ByteArrayInputStream(method.getResponseBodyAsString().getBytes()));
			Element root = document.getRootElement();
			Namespace n = Namespace.getNamespace("ims","http://schemas.microsoft.com/ceres/interaction/2011/04/ims");
			List result_tag = root.getChildren("results",n);
			System.out.println("Total no of the Document : "+result_tag.size());
			for(int result_id=0;result_id < result_tag.size(); result_id++ )
				{
				Element r_section = (Element)result_tag.get(result_id);
				List resultsection_tag = r_section.getChildren("resultsection", n);
				System.out.println("Size of the Tag : "+resultsection_tag.size());
				for( int resultsection_id = 0; resultsection_id < resultsection_tag.size(); resultsection_id++ )
					{
					Element r_results = (Element)resultsection_tag.get(resultsection_id);
					List result_tag_sub = r_results.getChildren("results",n);
					for( int result_id_sub = 0; result_id_sub < result_tag_sub.size(); result_id_sub++ )
						{
						Element r_item = (Element)result_tag_sub.get(result_id_sub);
						List item_tag = r_item.getChildren("item",n);
						for( int item_id = 0; item_id < item_tag.size(); item_id++ )
							{
							Element r_fields = (Element)item_tag.get(item_id);
							List fields_tag = r_fields.getChildren("fields",n);
							for( int fields_id = 0; fields_id < fields_tag.size(); fields_id++ )
								{
								Element item_tag_sub = (Element)fields_tag.get(fields_id);
								List item_value_tag = item_tag_sub.getChildren("item",n);
								for(int content_id = 0; content_id < item_value_tag.size();content_id++)
									{
									Element content = (Element)item_value_tag.get(content_id);
									name = content.getChildText("name", n);
									value = content.getChildText("value", n);
									System.out.println("Name : "+name+" Value : "+value);
									if(name.equals("cdcconcept"))
										list.add(value);
									if(name.equals("cdcsecondaryconcept")){
										if(value.contains(";")){
											String str[] = value.split("\\;");
											for(String s : str)
												list.add(s);
										}
										else
											list.add(value);
										}
									}						
								}
							}
						}
					}
				}
			if(secondary.equals(""))
				concept = primary;
			else
				concept = primary+"SECCONCEPT"+secondary;
			System.out.println("Concept:   *******    "+ concept);
			
			logger.debug("Concept:   *******    "+ concept);
			return list;
			}
		catch(Exception e)
			{
				logger.warn("Exception in SearchCSO"+ e);
			}
		return list;
		
		}

}
