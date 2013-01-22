<%@page import="com.ibm.workplace.wcm.api.exceptions.DocumentSaveException"%>
<%@ page import="java.util.*,
				 java.io.*,
				 java.lang.*,
				 com.ibm.workplace.wcm.api.*"%>

<%@taglib uri="/WEB-INF/tld/wcm.tld" prefix="wcm"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.ibm.com/xmlns/prod/websphere/portal/v7.0/portal-core" prefix="portal-core" %>
<%@ page import="com.ibm.portal.navigation.NavigationSelectionModel" %>

<%
    
	String logo="false";
	String siteareaId=request.getParameter("sa");
	String pageUniqueName=request.getParameter("un");
	String sitepath=request.getParameter("pth");
	//NavigationSelectionModelHome home = (NavigationSelectionModelHome)pageUniqueName;
	if((null!=siteareaId)&& (null!=pageUniqueName)){
	  logo="true";
	  pageContext.setAttribute("pageUniqueName",pageUniqueName);
	   
	}
	pageContext.setAttribute("logo",logo);
	String siteTitle="";
	String siteName ="";
	if("true".equalsIgnoreCase(logo)){
		/*try{ 
		
		   //Creating a workspace 
			Workspace ws = (Workspace) pageContext.getAttribute(Workspace.WCM_WORKSPACE_KEY);
		                                       
		    if (ws == null){
		        if (request.getUserPrincipal() != null)
		            ws = WCM_API.getRepository().getWorkspace(request.getUserPrincipal());
		        else
		            ws = WCM_API.getRepository().getAnonymousWorkspace();
		    }
		    
		    siteTitle = ((SiteArea) ws.getById(ws.createDocumentId(siteareaId))).getTitle();
		    siteName  =ws.getById(ws.createDocumentId(siteareaId)).getName();
		    RenderingContext rc = (RenderingContext)pageContext.getRequest().getAttribute(ws.WCM_RENDERINGCONTEXT_KEY);
		     if (rc == null)
   			 {
	      		 System.out.println("null renderingContext");
	       		 rc =ws.createRenderingContext(request,response,new HashMap());
    		 }
		    System.out.println("RC in branding:"+rc.getPath());
		    //siteTitle=sarea.getTitle();
		    ws.logout();
			
			}catch(Exception ex){
		 
			 	System.out.println("Exception in AllNews.jsp :"+ ex.getMessage());
			   // out.println("Exception in AllNews.jsp :"+ ex.getMessage());
		
			}*/
	
	}
	
System.out.println("AM in here "+logo);
%>
<c:if test="${logo eq 'true'}" >
<div class="acgc_top_content_wrap">
	<div class="acgc_top_content_box">
		<h1><%=siteareaId%></h1>
		<div class="acgc_searchblock acgc_radius_5">
			
			
			<form class="acgc_relative" method="get" action="/aurora/htmlsite//">
				<input type="hidden" value="searchresults" name="v">
				<input type="text" class="acgc_searchinput" title="Search ..." value="" name="q">
				<input type="submit" class="acgc_searchsubmit acgc_radius_3" value="go" name="go">
			</form>
		</div>
	</div>
</div>
					
				
	
 <!--<c:set var="nodeID" value="${wp.identification[pageUniqueName]}"/>
 <a href="?uri=nm:oid:${nodeID}"><portal-fmt:out>${node.title}</portal-fmt:out></a>&nbsp;&nbsp;|&nbsp;&nbsp;-->
	
          

     <c:set var="nodeh" value="${wp.navigationModel['org.ahc.caregiver.home']}"/>
      <c:set var="nodeIDh" value="${wp.identification[nodeh]}"/>
	 <a href="?uri=nm:oid:${nodeIDh}"><portal-fmt:out>${nodeh.title}</portal-fmt:out></a>&nbsp;&nbsp;&gt; &nbsp;&nbsp;
       
       
     <c:set var="node" value="${wp.navigationModel[pageUniqueName]}"/>
     <c:set var="nodep" value="${wp.navigationModel.parent[node]}"/>
     
    
	 <c:set var="nodeParent" value="${wp.identification[nodep]}"/>
     <a href="?uri=nm:oid:${nodeParent}"><portal-fmt:out>${nodep.title}</portal-fmt:out></a>&nbsp;&nbsp;&gt;&nbsp;&nbsp;


       <c:set var="nodeID" value="${wp.identification[node]}"/>
    <a href="?uri=nm:oid:${nodeID}"><portal-fmt:out>${node.title}</portal-fmt:out></a>
	
	 <!--<c:forEach var="step" begin="1" items="${pageUniqueName}" varStatus="status">
        <c:set var="nodeID" value="${wp.identification[wp.navigationModel[step]]}"/>
        <portal-fmt:out>${wp.navigationModel[step].title}</portal-fmt:out>
	  </c:forEach>-->
	  
 <script>
		 var insertElementsStr=null;
		  $(document).ready(function() {
		    qs="&sa=<%=siteareaId%>&un=<%=pageUniqueName%>";  
		    $("a.sitelanding").each(function() {
		   		var _href = $(this).attr("href"); 
		   		$(this).attr("href", _href + qs);
			});
		   
		   if ($("#sortChangeForm").length > 0){
             		
             	
             	 insertElementsStr= '<input type="hidden" id="sa" 	name="sa" 	 value="<%=siteareaId%>" />'+
             						'<input type="hidden" id="un" 	name="un" 	 value="<%=pageUniqueName%>" />'+
             						'<input type="hidden" id="pth" 	name="pth" 	 value="<%=sitepath%>" />'+
					             	'<input type="hidden" id="extra" name="extra" value="" />';
             
             	
             		$('#sortChangeForm').append(insertElementsStr); // it saves performance to do a bulk insert instead of each one.
             		
             	console.log("Yes the form is there");
		   
		   
		  	 }else{
		  	  	console.log("No the form isn't there");
		  	 
		  	 }
		  	 
		   
		});
		
</script>

</c:if>
