<%@page import="com.ibm.workplace.wcm.api.exceptions.DocumentSaveException"%>
<%@ page import="java.util.*,
				 java.io.*,
				 java.lang.*,
				 com.ibm.workplace.wcm.api.*"%>

<%@taglib uri="/WEB-INF/tld/wcm.tld" prefix="wcm"%>
<%@ taglib uri="http://www.ibm.com/xmlns/prod/websphere/portal/v7.0/portal-core" prefix="portal-core" %>


<%
//String selection=null;
String selValue=null;
if(null!=request.getParameter("selected")){
 selValue=request.getParameter("selected");
 request.removeAttribute("selection");
 request.setAttribute("selection",selValue);
}else{
 selValue="asc";
 request.getSession().setAttribute("selection","asc");
}
//out.write("SELECTED:"+selValue);


%>
 the Path : ${wp.selectionModel.selected.objectID.uniqueName}

<%
String SID="";
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
    out.write("<br/>PATH:"+rc.getLibraryRelativePath()+"<br/> path2: "+rc.getPath());
    
    String  sitePath=rc.getPath();
    
    DocumentIdIterator iterator= ws.findByPath(sitePath,Workspace.WORKFLOWSTATUS_ALL);
       	

    while(iterator.hasNext()){
			
			DocumentId sid=iterator.nextId();
		    
			
	        if(sid.getType().toString().equalsIgnoreCase(DocumentTypes.SiteArea.toString())){
				
				SID=sid.getId();
				out.write("</br>Site Area: "+sid.getId());	
				out.write("</br>Site Area name: "+sid.getName());			
	        
	        }
	        
			/*
			out.println("TOTAL Site Areas::"+contentiterator.getCount());
						while(contentiterator.hasNext()){
						   
							DocumentId cid=contentiterator.nextId();
							//out.println("</br>SA:"+cid.getName());
							if(cid.getType().toString().equalsIgnoreCase(DocumentTypes.SiteArea.toString())){
								SAcount++;
								
							  }
       	                   }*/
       	}
       	
       ws.logout();
	}catch(Exception ex){
 
	 	System.out.println("Exception in AllNews.jsp :"+ ex.getMessage());
	   // out.println("Exception in AllNews.jsp :"+ ex.getMessage());

	}


%>
<script>
 
  $(document).ready(function() {
    qs="&sa=<%=SID%>&un=${wp.selectionModel.selected.objectID.uniqueName}";  
   $("a.sitelanding").each(function() {
   		var _href = $(this).attr("href"); 
   		$(this).attr("href", _href + qs);
	});
   


 });

</script>