<%@page import="java.util.regex.Matcher"%>
<%@page import="java.util.regex.Pattern"%>
<%@page import="com.ibm.portal.navigation.SelectionModel"%>
<%@page import="com.ibm.workplace.wcm.api.exceptions.DocumentSaveException"%>
<%@ page import="java.util.*,
				 java.io.*,
				 java.lang.*,
				 com.ibm.workplace.wcm.api.* 
				 "%>
<%@ page import="com.ibm.portal.model.NavigationSelectionModelHome" %>
<%@ page import="com.ibm.portal.model.NavigationSelectionModelProvider" %>
<%@ page import="com.ibm.portal.navigation.NavigationSelectionModel" %>
<%@ page import="com.ibm.portal.navigation.NavigationNode" %>
<%@ page import="com.ibm.portal.ModelException" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.NamingException," %>

<%@taglib uri="/WEB-INF/tld/wcm.tld" prefix="wcm"%>


<%
String contentLibrary     = "CaregiverContentLibrary_en";
String SID="";
String sitepath="";
String siteRelpath="";
String siteName="";
try{ 
    //Creating a workspace 
	Workspace ws = (Workspace) pageContext.getAttribute(Workspace.WCM_WORKSPACE_KEY); //this is cached call so improves performance
    
    if (null == ws  ){
        if ( null !=request.getUserPrincipal() )
            ws = WCM_API.getRepository().getWorkspace(request.getUserPrincipal());
        else
            ws = WCM_API.getRepository().getAnonymousWorkspace();
    }
    
    RenderingContext rc = (RenderingContext)pageContext.getRequest().getAttribute(ws.WCM_RENDERINGCONTEXT_KEY);
    if (rc == null){
        System.out.println("null renderingContext");
        rc =ws.createRenderingContext(request,response,new HashMap());
    }
    
    String regexString= "CaregiverContentLibrary_en/Caregiver/SITES/(.*?)(?=/[^/])";
    Pattern p= Pattern.compile(regexString,Pattern.DOTALL);
    sitepath =rc.getPath();
    try{
	    Matcher matcher = p.matcher(rc.getPath());
		matcher.find();
		
		if(matcher.groupCount()>=0){
	      sitepath=matcher.group(0);
	    }
    }catch(Exception ex){
      System.out.println("Excption in matcher:"+ex.getMessage());
    }
    
    ws.setCurrentDocumentLibrary(ws.getDocumentLibrary(contentLibrary));
    DocumentIdIterator iterator= ws.findByPath(sitepath,Workspace.WORKFLOWSTATUS_ALL);
       	
	while(iterator.hasNext()){
		DocumentId sid=iterator.nextId();
		if(sid.getType().toString().equalsIgnoreCase(DocumentTypes.SiteArea.toString())){
		
			   SID=sid.getId();
			   siteName=ws.getById(ws.createDocumentId(SID)).getTitle();
			  }
    }
    ws.logout();
}catch(Exception ex){
 	 	System.out.println("Exception in siteLanding.jsp :"+ ex.getMessage());
}

%>
<script>
 
  $(document).ready(function() {
    qs="?&sa=<%=siteName%>&un=${wp.selectionModel.selected.objectID.uniqueName}";  
    var _Jsite ="<%=siteName%>";
    
   $("a.sitelanding").each(function() {
   		var _href = $(this).attr("href");
   		 
   		$(this).attr("href", _href + qs);
	});
	
	 $("a.allmedianews").each(function() {
		   		var _href = $(this).attr("href"); 
		   		if(_Jsite.length >0){
		   			$(this).attr("href", _href + '&pth=/CaregiverContentLibrary_en/Caregiver/News/'+ _Jsite);
     	   		}
		   		
		});
	$("a.allmediavideos").each(function() {
		   		var _href = $(this).attr("href"); 
		   		if(_Jsite.length >0){
		   			$(this).attr("href", _href + '&pth=/CaregiverContentLibrary_en/Caregiver/Videos/'+ _Jsite);
     	   		}
		   		
		});
		
	$("a.allmediaevents").each(function() {
		   		var _href = $(this).attr("href"); 
		   		if(_Jsite.length >0){
		   			$(this).attr("href", _href + '&pth=/CaregiverContentLibrary_en/Caregiver/Events/'+ _Jsite);
     	   		}
		   		
		});
	$("a.allmediadocuments").each(function() {
		   		var _href = $(this).attr("href"); 
		   		if(_Jsite.length >0){
		   			$(this).attr("href", _href + '&pth=/CaregiverContentLibrary_en/Caregiver/Documents/'+ _Jsite);
     	   		}
		   		
		});
	
	
	// provision to add any customizations later to individual media links, hence they are different from above links.
	
	 $("a.sitelandingnews").each(function() {
		   		var _href = $(this).attr("href"); 
		   		if(_Jsite.length >0){
		   			$(this).attr("href", _href + '&pth=/CaregiverContentLibrary_en/Caregiver/News/'+ _Jsite);
     	   		}
		   		
		});
	$("a.sitelandingvideos").each(function() {
		   		var _href = $(this).attr("href"); 
		   		if(_Jsite.length >0){
		   			$(this).attr("href", _href + '&pth=/CaregiverContentLibrary_en/Caregiver/Videos/'+ _Jsite);
     	   		}
		   		
		});
		
	$("a.sitelandingevents").each(function() {
		   		var _href = $(this).attr("href"); 
		   		if(_Jsite.length >0){
		   			$(this).attr("href",_href + '&pth=/CaregiverContentLibrary_en/Caregiver/Events/'+ _Jsite);
     	   		}
		   		
		});
	$("a.sitelandingdocuments").each(function() {
		   		var _href = $(this).attr("href"); 
		   		if(_Jsite.length >0){
		   			$(this).attr("href", _href + '&pth=/CaregiverContentLibrary_en/Caregiver/Documents/'+ _Jsite);
     	   		}
		   		
		});
   
});

</script>
