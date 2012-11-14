package com.cisco.swtg.scim.scheduler;

import java.io.*;
import java.net.*;

public class PostToUrl {
	String cuki=new String();

	
	public String newDataPOST (String urlString) {

	    try {
	        URL url = new URL(urlString);
	        URLConnection conn;
	        conn = url.openConnection();
	        conn.setRequestProperty ("Authorization", "Basic");
	        conn.setDoOutput(true);
	        conn.setDoInput(true);
	        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	        wr.flush(); 
	        // Get the response 
	        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
	        String line; 
	        while ((line = rd.readLine()) != null) { 
	            // Process line... 
	            } 
	        wr.close(); 
	        rd.close(); 
	        return rd.toString();
	    } catch (MalformedURLException e) {

	        e.printStackTrace();
	        return e.getMessage();
	    }
	    catch (IOException e) {

	        e.printStackTrace();
	        return e.getMessage();
	    } 
	}

}
