package com.aurora.seedlistservice.remoterestclient;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.aurora.xml.GSAFeed;

public class SeedListObjectsService {
	
	private static SeedListObjectsService instance;
	
	static {
		instance = new SeedListObjectsService();
	}
	public static SeedListObjectsService getInstance(){
		return instance;
	}
	
	public GSAFeed getSeedListXSL(String xslStylesheet){
		StringWriter writer = new StringWriter();
		GSAFeed gsaFeed = new GSAFeed();
		try {
			Source xmlSource = new StreamSource(new StringReader(SeedListClient.getInstance().getSeedListFeed()));
			Source xsltSource = getXSLStyesheetStream(xslStylesheet);
			Result result = new StreamResult(writer);

			
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer(xsltSource);
			transformer.transform(xmlSource, result);
			
			JAXBContext jaxbContext = JAXBContext.newInstance(GSAFeed.class);
			Unmarshaller jaxbUnmarshller = jaxbContext.createUnmarshaller();
			gsaFeed = (GSAFeed)jaxbUnmarshller.unmarshal(new StreamSource(new StringReader(writer.toString())));
			
			
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return gsaFeed;
	}
	
	
	
	private StreamSource getXSLStyesheetStream(String feedPath){
		ClassLoader cl = this.getClass().getClassLoader();
		InputStream in = cl.getResourceAsStream(feedPath);
		return new StreamSource(in);
	}
}
