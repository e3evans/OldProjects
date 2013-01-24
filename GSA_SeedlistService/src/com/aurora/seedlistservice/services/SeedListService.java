package com.aurora.seedlistservice.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.aurora.seedlistservice.remoterestclient.SeedListClient;

import config.ConfigManager;


@Path("/seedListService")
@Produces(MediaType.TEXT_HTML)
public class SeedListService {
	
	private static final String XSL_ATOMFEED = "xsl/seedlist.xsl";
	private static final String XSL_CLEANFEED = "xsl/cleanup.xsl";
	private static final String ACTION_FEEDLIST = "feedlist";
	private static final String ACTION_CLEANLIST = "cleanlist";
	private static final String CONFIG_FILELOCATION = "google.xml.location";
			
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/googlefeed")
	public String getSeedList(@Context HttpServletRequest request,@Context HttpServletResponse response){
		return getSeedListXSL(XSL_ATOMFEED).toString();
	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("/googlecleanup")
	public String getSeedListCleanup(){
		return getSeedListXSL(XSL_CLEANFEED).toString();
	}
	
	@GET
	@Path("/googlefile/{fileaction}")
	public void saveGoogleFeedtoFile(@PathParam("fileaction")String fileaction){
		System.out.println("HERE!!!");
		String xml = "";
		if (ACTION_CLEANLIST.equals(fileaction)){
			xml = getSeedListXSL(XSL_CLEANFEED).toString();
		}else if (ACTION_FEEDLIST.equals(fileaction)){
			xml = getSeedListXSL(XSL_ATOMFEED).toString();
		}
		if (!xml.equals(""))writeXMLtoFile(ConfigManager.getInstance().getString(CONFIG_FILELOCATION), xml);
		
	}
	
	private StreamSource getXSLStyesheetStream(String feedPath){
		ClassLoader cl = this.getClass().getClassLoader();
		InputStream in = cl.getResourceAsStream(feedPath);
		return new StreamSource(in);
	}
	
	private StringWriter getSeedListXSL(String xslStylesheet){
		StringWriter writer = new StringWriter();
		try {
			Source xmlSource = new StreamSource(new StringReader(SeedListClient.getInstance().getSeedListFeed()));
			Source xsltSource = getXSLStyesheetStream(xslStylesheet);
			Result result = new StreamResult(writer);
			/*
			 * BEGIN TEST CLASS FOR CONNECTOR DEV
			 * 
			 * 
			 */
			
			
			
			/*
			 * 
			 * END TEST CLASS FOR CONNECTOR DEV
			 */
			
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer(xsltSource);
			transformer.transform(xmlSource, result);
			
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return writer;
	}
	
	private void writeXMLtoFile(String fileLocation,String xml){
		Writer writer = null;
		File xmlFile = new File(fileLocation);
		try {
			writer = new BufferedWriter(new FileWriter(xmlFile));
			writer.write(xml);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if (writer!=null)
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}
	

}
