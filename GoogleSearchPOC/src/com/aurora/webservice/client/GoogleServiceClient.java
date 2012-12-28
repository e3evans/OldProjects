package com.aurora.webservice.client;


import javax.ws.rs.core.MediaType;



import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;


public class GoogleServiceClient {
    
	private static GoogleServiceClient instance ;
    static {
    	instance = new GoogleServiceClient() ;
    }
	    
    private GoogleServiceClient () {
    }
    
    public static GoogleServiceClient getInstance () {
    	return instance ;
    }
    
    public String doGet(String path, String queryParams) throws Exception {
  
		WebResource service = getWebResource (path) ;
	    ClientResponse response = service.accept(MediaType.TEXT_XML).get(ClientResponse.class);
		if (response.getStatus() != 200)
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
	    String output = response.getEntity(String.class);
	    return output ;
	}
	
	public void doPost(String path, String input, String queryParams) throws Exception {
		WebResource service = getWebResource (path) ;
		ClientResponse response = service.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, input);
		if ( (response.getStatus() != 201) && (response.getStatus() != 204) )
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
	}
	
	public void doPut(String path, String input, String queryParams) throws Exception {
		WebResource service = getWebResource (path) ;
		ClientResponse response = service.type(MediaType.APPLICATION_JSON).put(ClientResponse.class, input);
		if ( (response.getStatus() != 201) && (response.getStatus() != 204) )
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
	}
	
	public void doDelete(String path, String input, String queryParams) throws Exception {
		WebResource service = getWebResource (path) ;
		ClientResponse response = service.type(MediaType.APPLICATION_JSON).delete(ClientResponse.class, input);
		if ( (response.getStatus() != 201) && (response.getStatus() != 204) )
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
	}
    
    

    private WebResource getWebResource (String url) throws Exception {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		//String url = appendSuffix(SERVICE_NAME, path, queryParams) ; 
	    WebResource service = client.resource(url);
	    return service ;
	}
	
	
}
