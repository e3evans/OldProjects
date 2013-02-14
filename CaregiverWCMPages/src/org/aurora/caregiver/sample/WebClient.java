package org.aurora.caregiver.sample;




public class WebClient {
	
	private static WebClient instance;
	
	static{
		instance = new WebClient();
	}

	private WebClient(){};
	
	public static WebClient getInstance(){
		return instance;
	}
	
	public String doGet(String serviceURL) throws Exception{
		
	    return serviceURL ;
	}
	
	
}
