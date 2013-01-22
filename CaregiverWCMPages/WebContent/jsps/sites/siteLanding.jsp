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
try{ 
    //Creating a workspace 
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
    
    sitepath=rc.getPath();
    
    DocumentIdIterator iterator= ws.findByPath(sitepath,Workspace.WORKFLOWSTATUS_ALL);
       	ws.findComponentByName()
	while(iterator.hasNext()){
		DocumentId sid=iterator.nextId();
		if(sid.getType().toString().equalsIgnoreCase(DocumentTypes.SiteArea.toString())){
			   SID=sid.getId();
			   siteName=ws.getById(ws.createDocumentId(SID)).getTitle();
			    System.out.println("pageTitle:"+siteName);
			   
			  // request.getSession().setAttribute("siteName",siteName);
			   
		 	 }
    }
     /*Context ctx = new InitialContext();
     NavigationSelectionModelHome home = (NavigationSelectionModelHome) ctx.lookup("portal:service/model/NavigationSelectionModel");
     if (home != null) {
        NavigationSelectionModelProvider provider =  home.getNavigationSelectionModelProvider();
        NavigationSelectionModel model =  provider.getNavigationSelectionModel(request,response);
        System.out.println("lOC:"+model.getSelectedNode());
       
       
        NavigationNode selectedNode = (NavigationNode) model.getSelectedNode();
		String pageTitle = selectedNode.getTitle(Locale.ENGLISH); 
        System.out.println("pageTitle:"+pageTitle);
       
       
        
        for (java.util.Iterator i = model.iterator(); i.hasNext(); ) 
        {
            NavigationNode node = (NavigationNode) i.next();
            if (i.hasNext()) {
             System.out.println("node :"+ node.getTitle(Locale.ENGLISH));
            }
            
         }
         
          qs="&sa=<//siteName //>&un=${wp.selectionModel.selected.objectID.uniqueName}";  
         
         
      }*/
     ws.logout();
}catch(Exception ex){
 	 	System.out.println("Exception in AllNews.jsp :"+ ex.getMessage());
}

%>
<script>
 
  $(document).ready(function() {
    qs="&sa=<%=siteName%>&un=${wp.selectionModel.selected.objectID.uniqueName}";  
   
    
   $("a.sitelanding").each(function() {
   		var _href = $(this).attr("href"); 
   		$(this).attr("href", _href + qs);
	});
   
});

</script>