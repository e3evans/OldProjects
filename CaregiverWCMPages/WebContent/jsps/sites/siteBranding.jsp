<%@page import="com.ibm.workplace.wcm.api.exceptions.DocumentSaveException"%>
<%@ page import="java.util.*,
				 java.io.*,
				 java.lang.*,
				 com.ibm.workplace.wcm.api.*"%>

<%@taglib uri="/WEB-INF/tld/wcm.tld" prefix="wcm"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.ibm.com/xmlns/prod/websphere/portal/v7.0/portal-core" prefix="portal-core" %>


<%

	String logo="false";
	String siteareaId=request.getParameter("sa");
	String pageUniqueName=request.getParameter("un");
	if((null!=siteareaId)&& (null!=pageUniqueName)){
	  logo="true";
	  pageContext.setAttribute("pageUniqueName",pageUniqueName);
	}
	pageContext.setAttribute("logo",logo);
	String siteTitle="";
	if("true".equalsIgnoreCase(logo)){
		try{ 
		
		 
			//Creating a workspace 
			Workspace ws = (Workspace) pageContext.getAttribute(Workspace.WCM_WORKSPACE_KEY);
		                                       
		    if (ws == null){
		        if (request.getUserPrincipal() != null)
		            ws = WCM_API.getRepository().getWorkspace(request.getUserPrincipal());
		        else
		            ws = WCM_API.getRepository().getAnonymousWorkspace();
		    }
		    
		    siteTitle = ((SiteArea) ws.getById(ws.createDocumentId(siteareaId))).getTitle();
		    //siteTitle=sarea.getTitle();
		    ws.logout();
			
			}catch(Exception ex){
		 
			 	System.out.println("Exception in AllNews.jsp :"+ ex.getMessage());
			   // out.println("Exception in AllNews.jsp :"+ ex.getMessage());
		
			}
	
	}
	
%>
<c:if test="${logo eq 'true'}">
	<div class="acgc_content_box">
		<div class="acgc_content_box_body acgc_relative">
			<div class="acgc_spacer_10 acgc_bg_white"><!-- spacer --></div>
			<div class="acgc_content_box_header acgc_relative">
		
				<h3 class="acgc_content_box_header_title">
					<%=siteTitle%>
				</h3>
			</div>
			
		</div>
		
	</div>
	
  <c:set var="node" value="${wp.navigationModel[pageUniqueName]}"/>
1 ${wp.selectionModel[node]} /

2 ${wp.navigationModel.parent[node]} /

\
				    <c:set var="nodeID" value="${wp.identification[node]}"/>
					  <a href="?uri=nm:oid:${nodeID}"><portal-fmt:out>${node.title}</portal-fmt:out></a>&nbsp;&nbsp;|&nbsp;&nbsp;
	
	
	<!-- <script>
		 
		  $(document).ready(function() {
		    qs="&sa=&un=${wp.selectionModel.selected.objectID.uniqueName}";  
		   $("a.sitelanding").each(function() {
		   		var _href = $(this).attr("href"); 
		   		$(this).attr("href", _href + qs);
			});
		   
		
		
		 });
		
	</script>-->

</c:if>