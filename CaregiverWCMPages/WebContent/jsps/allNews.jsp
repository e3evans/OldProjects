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
 selValue="desc";
 request.getSession().setAttribute("selection",selValue);
}
//out.write("SELECTED:"+selValue);



%>
<form id="sortChangeForm"  method="GET" class="sortChangeForm" name="sortChangeForm" >
<div id="acgc_recordsorter" class="acgc_relative">
	<div class="acgc_sort_by">
		Sort By: <span class="acgc_sort_by_focus"> <% if("desc".equalsIgnoreCase(selValue)){ %>Newest First<%}else{%>Oldest First<%}%> &nbsp;<img src="/AuroraTheme/themes/html/assets/images/arrow-down-small-header.png" alt="arrow" class="acgc_inline_icon"/></span>
		<div class="acgc_relative">
			<div style="display: none;" class="acgc_sort_by_holder">
				<ul>
				   <% if ((null!=selValue) && ("asc".equalsIgnoreCase(selValue))) { %>
					<li>
					<a href="#newest" title="Oldest First" onclick="javascript: $(this).children('input').prop('checked', 'checked'); document.sortChangeForm.submit(); return false;">
						<input name="selected" onclick="document.sortChangeForm.submit()" value="asc"  type="radio" checked class="acgc_hidden"><strong>Oldest First</strong>
					</a>
					</li>
					<% }else{%>
					<li>
						<a href="#newest" title="Oldest First" onclick="javascript: $(this).children('input').prop('checked', 'checked'); document.sortChangeForm.submit(); return false;">
							<input name="selected" onclick="document.sortChangeForm.submit()" value="asc"  type="radio" class="acgc_hidden">Oldest First
						</a>
					</li>
					<%} 
					if ((null!=selValue)&& ("desc".equalsIgnoreCase(selValue))) { %>
					<li>
						
						<a href="#oldest" title="Newest First" onclick="javascript: $(this).children('input').prop('checked', 'checked'); document.sortChangeForm.submit(); return false;">
							<input name="selected" onclick="document.sortChangeForm.submit()" value="desc" type="radio" checked class="acgc_hidden"><strong>Newest First</strong>
						</a>
					</li>
					<% }else{%>
					<li>
						<a href="#oldest" title="Newest First" onclick="javascript: $(this).children('input').prop('checked', 'checked'); document.sortChangeForm.submit(); return false;">
							<input name="selected" onclick="document.sortChangeForm.submit()" value="desc" type="radio" class="acgc_hidden">Newest First
						</a>
					</li>
					<%} %>
				</ul>
			</div>
		</div>
	</div>
</div>
</form>



<%

try{ 

    String ContentLibrary     = "CaregiverContentLibrary_en";
	String DesignLib		  = "CaregiverDesignLibrary";
	
    String ascMenu			  = "AllNews_ASC_MNU";
	String descMenu			  = "AllNews_DESC_MNU";   
 
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
    
 	//Workspace ws = (Workspace) pageContext.getAttribute(Workspace.WCM_WORKSPACE_KEY);
    
    // Set library 
    ws.setCurrentDocumentLibrary(ws.getDocumentLibrary(DesignLib)); 
    
    // rc=ws.createRenderingContext(pageContext.getRequest(),pageContext.getResponse(), new java.util.HashMap(), "http://porporit1.ahc.root.loc:10039/cgc/wcm", "/connect");
    
    DocumentIdIterator docIdIter;
    
    if("asc".equalsIgnoreCase(selValue)){
     	
 	   docIdIter= ws.findComponentByName(ascMenu);
    }
    else if("desc".equalsIgnoreCase(selValue)){
    
       docIdIter= ws.findComponentByName(descMenu);
    }else{
       
       docIdIter= ws.findComponentByName(descMenu);
    }
    RenderingContext rc = (RenderingContext)pageContext.getRequest().getAttribute(ws.WCM_RENDERINGCONTEXT_KEY);
    if (rc == null)
    {
        System.out.println("null renderingContext");
        rc =ws.createRenderingContext(request,response,new HashMap());
    }
    
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
