package com.aurora.seedlistservice.utility;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.dom.DOMSource;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class MyResolver implements URIResolver{

	@Override
	public Source resolve(String href, String base) throws TransformerException {
		ClassLoader cl = this.getClass().getClassLoader();
		InputStream in = cl.getResourceAsStream("xsl/"+href);
		InputSource xslInputSource = new InputSource(in);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			System.out.println("TRYING THIS!!");
			dBuilder = factory.newDocumentBuilder();
			Document xslDoc = dBuilder.parse(xslInputSource);
			DOMSource xslDomSource = new DOMSource(xslDoc);
			xslDomSource.setSystemId("xsl/"+href);
			System.out.println("GOT HERE");
			return xslDomSource;
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("RETURNING NULL!!");
		return null;
	}
	
}