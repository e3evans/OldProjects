package com.aurora.seedlistservice.remoterestclient;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;



public class SeedListClient {
	
	private static SeedListClient instance;
	private static String ENV_USERPASSWORD="com.aurora.seedlist.userpassword";
	
	static{
		instance = new SeedListClient();
	}

	private SeedListClient(){};
	
	public static SeedListClient getInstance(){
		return instance;
	}
	
	
	public String getSeedListFeed(){
		String[] userPassword = System.getProperty(ENV_USERPASSWORD).split(":");
		BasicAuthClient bac = new BasicAuthClient();
		bac.setUsernamePassword(userPassword[0], userPassword[1]);
		StringBuffer sb = new StringBuffer();
		sb.append(bac.getFeedResult());
		bac.close();
		return sb.toString();
		
	}

	public String getContentItem(String contentURL){
		String[] userPassword = System.getProperty(ENV_USERPASSWORD).split(":");
		BasicAuthClient bac = new BasicAuthClient();
		bac.setUsernamePassword(userPassword[0], userPassword[1]);
		StringBuffer sb = new StringBuffer();
		sb.append(bac.getTestResult());
		return sb.toString();
	}
	
	
	static class BasicAuthClient{
		private WebResource webResource;
		private Client client;
		private static final String seedListURI = "http://porporit1.ahc.root.loc:10039/seedlist/myserver?SeedlistId=CaregiverContentLibrary_en/Caregiver&Source=com.ibm.workplace.wcm.plugins.seedlist.retriever.WCMRetrieverFactory&Action=GetDocuments";		
		private static final String testingURL = "http://porporit1.ahc.root.loc:10039/cgc/wcm/connect/CaregiverContentLibrary_en/Caregiver/Default_Contents/New_Default_content?CACHE=NONE";
		
		
		public BasicAuthClient(){
			ClientConfig config = new DefaultClientConfig();
			client = Client.create(config);
//			client.addFilter(new LoggingFilter());
			webResource = client.resource(seedListURI);
		}
		
		public void setUsernamePassword(String username,String password){
			client.addFilter(new HTTPBasicAuthFilter(username, password));
		}
		public String getFeedResult(){
			WebResource resource = webResource;
			return resource.accept(MediaType.APPLICATION_ATOM_XML).get(String.class);
		}
		
		public String getTestResult(){
			WebResource resource = client.resource(testingURL);
			client.setFollowRedirects(false);
			return resource.accept(MediaType.TEXT_HTML).get(String.class);
		}
		
		
		public void close(){
			client.destroy();
		}
	}

}
