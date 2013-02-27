<%@page import="com.ibm.workplace.wcm.api.exceptions.DocumentSaveException"%>
<%@ page import="java.util.*,
				 java.io.*,
				 java.lang.*,
				 com.ibm.workplace.wcm.api.*"%>

<%@taglib uri="/WEB-INF/tld/wcm.tld" prefix="wcm"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.ibm.portal.navigation.NavigationSelectionModel" %>
<%@ taglib uri="http://www.ibm.com/xmlns/prod/websphere/portal/v7.0/portal-fmt" prefix="portal-fmt" %>

<%
    
	String logo="false";
	String mediaRedirect="";
	String siteName,pageUniqueName ="";
	String pth=null;
	if(null!=request.getParameter("sa"))
		siteName=request.getParameter("sa");
	if(null!=request.getParameter("un"))
		pageUniqueName=request.getParameter("un");
	
	if(null!=request.getParameter("pth"))
	   pth=request.getParameter("pth");
         
	if((null!=siteName)&& (null!=pageUniqueName)){
	  logo="true";
	  pageContext.setAttribute("pageUniqueName",pageUniqueName);
	   
	}
	pageContext.setAttribute("logo",logo);
	String siteTitle="";
	
	
	
	if(("true".equalsIgnoreCase(logo)||(null==pth)){ //execute this block only when branding is required
		try{ 
		  //Creating a workspace 
			Workspace ws = (Workspace) pageContext.getAttribute(Workspace.WCM_WORKSPACE_KEY); //this is cached call, so improves performance
		    if (null == ws  ){
		        if ( null !=request.getUserPrincipal() )
		            ws = WCM_API.getRepository().getWorkspace(request.getUserPrincipal());
		        else
		            ws = WCM_API.getRepository().getAnonymousWorkspace();
		    }
		   
		    RenderingContext rc = (RenderingContext)pageContext.getRequest().getAttribute(ws.WCM_RENDERINGCONTEXT_KEY);
		     if (rc == null)
   			 {
	      		 rc =ws.createRenderingContext(request,response,new HashMap());
    		 }
		    
		     //the below links NEVER change, hence they need not be maintained in props files.
		    
		    if(rc.getPath().contains("/News/"))
		      mediaRedirect="/CaregiverContentLibrary_en/Caregiver/News/"+siteName+"/";
		    
		    else if (rc.getPath().contains("/Videos/"))
		      mediaRedirect="CaregiverContentLibrary_en/Caregiver/Videos/"+siteName+"/";
		    
		    else if (rc.getPath().contains("/Events/"))
		      mediaRedirect="CaregiverContentLibrary_en/Caregiver/Events/"+siteName+"/";
		      
		    else if (rc.getPath().contains("/Documents/"))
		      mediaRedirect="CaregiverContentLibrary_en/Caregiver/Documents/"+siteName+"/";
		    
		    else
		      mediaRedirect=null;
	
		    pth=mediaRedirect;
		    ws.logout();
			}catch(Exception ex){
		 	 	System.out.println("Exception in SiteBranding.jsp :"+ ex.getMessage());
			}
	
	}
	

%>
<c:if test="${logo eq 'true'}" >
<div class="acgc_top_content_wrap">
	<div class="acgc_top_content_box">
		<h1><%=siteName%></h1>
		<div class="acgc_searchblock acgc_radius_5">
			
			
			<form class="acgc_relative" method="get" action="/aurora/htmlsite//">
				<input type="hidden" value="searchresults" name="v">
				<input type="text" class="acgc_searchinput" title="Search ..." value="" name="q">
				<input type="submit" class="acgc_searchsubmit acgc_radius_3" value="go" name="go">
			</form>
		</div>
	</div>
</div>
					
<div id="breadCrumb_inner" class="breadCrumb_inner" style="display:none">			
     <c:set var="node" value="${wp.navigationModel['ahc.caregiver.home']}"/>
      <c:set var="nodeID" value="${wp.identification[node]}"/>
	  <a href="?uri=nm:oid:${nodeID}" class="acgc_breadcrumb_link"><portal-fmt:out>${node.title}</portal-fmt:out></a>
      
      <span class="acgc_breadcrumb_spacer">&gt;</span>
       
     <c:set var="node" value="${wp.navigationModel[pageUniqueName]}"/>
     <c:set var="nodep" value="${wp.navigationModel.parent[node]}"/>
      
    
	 <c:set var="nodeParent" value="${wp.identification[nodep]}"/>
     <a href="?uri=nm:oid:${nodeParent}" class="acgc_breadcrumb_link"><portal-fmt:out>${nodep.title}</portal-fmt:out></a>
     
     <span class="acgc_breadcrumb_spacer">&gt;</span>
      
      <c:set var="nodeID" value="${wp.identification[node]}"/>
      <a href="?uri=nm:oid:${nodeID}" class="acgc_breadcrumb_link"><portal-fmt:out>${node.title}</portal-fmt:out></a>
</div>
	 
	  
 <script>
		 var insertElementsStr=null;
		  $(document).ready(function() {
			    qs="?&sa=<%=siteName%>&un=<%=pageUniqueName%>";  
			    $("a.sitelanding").each(function() {
			   		var _href = $(this).attr("href"); 
			   		$(this).attr("href", _href + qs);
				});
			
			   if ($(".acgc_breadcrumb").length > 0){
	                $('.acgc_breadcrumb').html($("#breadCrumb_inner").html()); //replacing default breadcrumb(BC) with reffering site's BC.
	            }
			
			   if ($("#sortChangeForm").length > 0){
	             	 insertElementsStr= '<input type="hidden" id="sa" 	name="sa" 	 value="<%=siteName%>" />'+
	             						'<input type="hidden" id="un" 	name="un" 	 value="<%=pageUniqueName%>" />'+
	             						'<input type="hidden" id="pth" 	name="pth" 	 value="<%=pth%>" />';
	             	$('#sortChangeForm').append(insertElementsStr); // for better performance- doing a bulk insert instead of each one.
	           }
	           
	           $("a.allmedia, .acgc_pagination_block a").each(function() {
			   		var _href = $(this).attr("href"); 
			   		var _JSmediaredirect ="<%=pth%>";
			   		if(_JSmediaredirect.length >0){
			   			$(this).attr("href", _href + '&pth='+ _JSmediaredirect);
			   		}
			   		
				});
				
				
			  $("a.sitehome").each(function() {
			   		var _href = $(this).attr("href"); 
			   		var _hrefNew='?uri=nm:oid:${nodeID}';
			   		$(this).attr("href", _hrefNew);
			   	});
				
		});
		
</script>

</c:if>
