<%@ page import="java.util.*,
				 java.io.*,
				 java.lang.*,
				 com.ibm.workplace.wcm.api.*"%>

<%@taglib uri="/WEB-INF/tld/wcm.tld" prefix="wcm"%>
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
out.write("SELECTED:"+selValue);



%>
<form id="sortChangeForm"  method="GET" name="sortChangeForm" >
<div id="acgc_recordsorter" class="acgc_relative">
	<div class="acgc_sort_by">
		Sort By: <span class="acgc_sort_by_focus"> <% if("asc".equalsIgnoreCase(selValue)){ %>Newest First<%}else{%>Oldest First<%}%><img src="/AuroraTheme/themes/html/assets/images/arrow-down-small-header.png" alt="arrow" class="acgc_inline_icon"/></span>
		<div class="acgc_relative">
			<div style="display: none;" class="acgc_sort_by_holder">
				<ul>
					<!--  <li><a href="#"  onclick="javascript:changeSort('asc');"   title="Newest First">Newest First</a></li>
					<li><a href="#"  onclick="javascript:changeSort('desc');"  title="Oldest First">Oldest First</a></li>-->
					  <% if (selValue!=null && ("asc".equalsIgnoreCase(selValue))) { %>
					&nbsp&nbsp<li><input type="radio" name="selected"  onclick="this.form.submit()" value="asc" checked>&nbsp Newest First</input></li>
					<% }else{%>
					&nbsp&nbsp<li><input type="radio" name="selected"  onclick="this.form.submit()" value="asc" >&nbsp Newest First</input></li>
					<%} 
					if (selValue!=null && ("desc".equalsIgnoreCase(selValue))) { %>
					&nbsp&nbsp<li><input type="radio" name="selected"  onclick="this.form.submit()" value="desc" checked>&nbsp Oldest First</input></li>
					<% }else{%>
					&nbsp&nbsp<li><input type="radio" name="selected"  onclick="this.form.submit()" value="desc" >&nbsp Oldest First</input></li>
					<%} %>
				</ul>
			</div>
		</div>
	</div>
</div>
</form>

<%

try{ 

    String ContentLibraryPath = "CaregiverContentLibrary_en";
	String DesignLib		  = "CaregiverDesignLibrary";
	String sitePath			  = "/Caregiver/News";
   
    String componentName = "";
    DocumentId docId;
 	LibraryComponent component;
 
	//Creating a workspace 
	Workspace ws = (Workspace) pageContext.getAttribute(Workspace.WCM_WORKSPACE_KEY);
                                       
    if (ws == null)
    {
        if (request.getUserPrincipal() != null)
            ws = WCM_API.getRepository().getWorkspace(request.getUserPrincipal());
        else
            ws = WCM_API.getRepository().getAnonymousWorkspace();
    }
    ws.useUserAccess(true);
    RenderingContext renderingContextTasks = (RenderingContext)pageContext.getRequest().getAttribute(ws.WCM_RENDERINGCONTEXT_KEY);
    if (renderingContextTasks == null)
    {
        System.out.println("null renderingContext");
    }
 	//Workspace ws = (Workspace) pageContext.getAttribute(Workspace.WCM_WORKSPACE_KEY);
    
    // Set library 
    ws.setCurrentDocumentLibrary(ws.getDocumentLibrary(DesignLib)); 
    
    RenderingContext rc=ws.createRenderingContext(pageContext.getRequest(),pageContext.getResponse(), new java.util.HashMap(), "http://porporit1.ahc.root.loc:10039/cgc/wcm", "/connect");
    
    DocumentIdIterator docIdIter;
    
    if("asc".equalsIgnoreCase(selValue)){
     	
 	   docIdIter= ws.findComponentByName("AllNews_ASC_MNU");
    }
    else if("desc".equalsIgnoreCase(selValue)){
    
       docIdIter= ws.findComponentByName("AllNews_DESC_MNU");
    }else{
       
       docIdIter= ws.findComponentByName("AllNews_ASC_MNU");
    }
    
    // Set the path (Context) to the for the site area where the component will be rendered
    rc.setRenderedContent(ContentLibraryPath + sitePath);
 	
 	while(docIdIter.hasNext()){
	   
	   	docId = (DocumentId)docIdIter.next();
		component = (LibraryComponent) ws.getById(docId);
		componentName= (String)component.getName();
		//out.println(component.getName());
		String renderedContent = ws.render(rc, component);
   		out.write(renderedContent);
	 
	  }
  
       	
	}catch(Exception ex){
 
 	System.out.println("Exception in AllNews.jsp :"+ ex.getMessage());
   // out.println("Exception in AllNews.jsp :"+ ex.getMessage());

}



 
%>


<script type="text/javascript">
function changeSort(order){
 document.getElementById("selected").value=order;
 document.forms["sortChangeForm"].submit();
}

</script>