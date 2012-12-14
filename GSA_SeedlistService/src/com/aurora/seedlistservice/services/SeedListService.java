package com.aurora.seedlistservice.services;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.BasicScheme;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.aurora.seedlistservice.remoterestclient.SeedListClient;

import sun.misc.BASE64Decoder;






@Path("/seedListService")
@Produces(MediaType.TEXT_HTML)
public class SeedListService {
	
	@GET
	@Produces(MediaType.APPLICATION_ATOM_XML)
	public String getSeedList(@Context HttpServletRequest request){
//		String location = "http://porporit1.ahc.root.loc:10039/seedlist/myserver?SeedlistId=CaregiverContentLibrary_en/Caregiver&Source=com.ibm.workplace.wcm.plugins.seedlist.retriever.WCMRetrieverFactory&Action=GetDocuments";		
		return SeedListClient.getInstance().getSeedListFeed();
	}

	
}
