package com.aurora.seedlistservice.services;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.aurora.seedlistservice.remoterestclient.SeedListClient;






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
