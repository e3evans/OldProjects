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
String SID="";
String sitepath="";
String siteRelpath="";
String	siteName="";
LibraryComponent component;
String DesignLib		  = "CaregiverDesignLibrary";
try{ 
    
	Workspace ws = (Workspace) pageContext.getAttribute(Workspace.WCM_WORKSPACE_KEY);
    
    if (ws == null)
    {
        if (request.getUserPrincipal() != null)
            ws = WCM_API.getRepository().getWorkspace(request.getUserPrincipal());
        else
            ws = WCM_API.getRepository().getAnonymousWorkspace();
    }
    
    RenderingContext rc = (RenderingContext)pageContext.getRequest().getAttribute(ws.WCM_RENDERINGCONTEXT_KEY);
    if (rc == null)
    {
        System.out.println("null renderingContext");
        rc =ws.createRenderingContext(request,response,new HashMap());
    }
    ws.setCurrentDocumentLibrary(ws.getDocumentLibrary(DesignLib)); 
    sitepath=rc.getPath();
    
    DocumentIdIterator iterator= ws.findByPath(sitepath,Workspace.WORKFLOWSTATUS_ALL);
       	
	while(iterator.hasNext()){
		DocumentId sid=iterator.nextId();
		if(sid.getType().toString().equalsIgnoreCase(DocumentTypes.SiteArea.toString())){
			   SID=sid.getId();
			   siteName=ws.getById(ws.createDocumentId(SID)).getName();
			 }
    }
    iterator= ws.findComponentByName(siteName);
    while(iterator.hasNext()){
        DocumentId sid=iterator.nextId();
		if(sid.getType().toString().equalsIgnoreCase(DocumentTypes.LibraryImageComponent.toString())){
		  component = (LibraryComponent) ws.getById(sid);
		  String renderedContent = ws.render(rc, component);
   		  out.write(renderedContent);
   		}
		
	}
     
     
     ws.logout();
}catch(Exception ex){
 	 	System.out.println("Exception in AllNews.jsp :"+ ex.getMessage());
}

%>
