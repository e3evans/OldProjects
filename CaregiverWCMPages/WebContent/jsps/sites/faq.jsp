<%@ page
	import="java.util.*,
				 java.io.*,
				 java.lang.*,
				 com.ibm.workplace.wcm.api.*"%>

<%@taglib uri="/WEB-INF/tld/wcm.tld" prefix="wcm"%>
<%
String selValue=null;
String optionStr ="";
boolean fetch=true;
try {
	if (null != request.getParameter("u188")) {
		selValue = request.getParameter("u188");
		optionStr =(String)request.getSession().getAttribute("optionStr");
		System.out.println("selValue:"+selValue);
		fetch=false;
	 } else {// TODO
	
	 }
%>
<form id="faqChangeForm" method="GET" name="sortChangeForm">
	<p class="acgc_chooser">
		View Questions for: <select class="u188" name="u188" id="u188" onchange="this.form.submit()">
	
	<%
	if(fetch){
		
	
			String contentLibrary = "CaregiverContentLibrary_en";
			String DesignLib = "CaregiverDesignLibrary";
	
			String faqmenu = "AllEvents_ASC_MNU";
			String componentName = "";
			String faqSitePath = "/Caregiver/FAQ/";
			DocumentId docId;
			LibraryComponent component;
					
			//Creating a workspace 
			Workspace ws = (Workspace) pageContext.getAttribute(Workspace.WCM_WORKSPACE_KEY);
	
			if (ws == null) {
				if (request.getUserPrincipal() != null)
					ws = WCM_API.getRepository().getWorkspace(
							request.getUserPrincipal());
				else
					ws = WCM_API.getRepository().getAnonymousWorkspace();
			}
			ws.useUserAccess(true);
	
			ws.setCurrentDocumentLibrary(ws.getDocumentLibrary(contentLibrary));
	
			DocumentIdIterator iterator = ws.findByPath((contentLibrary+faqSitePath),Workspace.WORKFLOWSTATUS_ALL);
			while (iterator.hasNext()) {
				DocumentId sid = iterator.nextId();
				if ((DocumentTypes.SiteArea.toString()).equalsIgnoreCase(sid.getType().toString())) {
					SiteArea sitearea = (SiteArea) ws.getById(sid);
	                System.out.println("..:"+sitearea.getName());
									
					DocumentIdIterator siteiterator=sitearea.getAllDirectChildren();
	                System.out.println("CHILD SITES Count::"+ siteiterator.getCount());
	               
					while (siteiterator.hasNext()) {
						DocumentId chid = siteiterator.nextId();
					 
						 if ((DocumentTypes.SiteArea.toString()).equalsIgnoreCase(chid.getType().toString())) {
					 		SiteArea chsitearea = (SiteArea) ws.getById(chid);
					 		String tempStr="<option value='"+contentLibrary+faqSitePath+chid.getName()+"'>"+chid.getName()+"</option>";
					 		optionStr+=tempStr;
					 	}
					}
					out.write(optionStr);
					session.setAttribute("optionStr",optionStr);// setting options string in session to avoid recursive/redundant searches.
				}
			}
		ws.logout();
		
	} 
	else{
	 System.out.println("Getting from Session");
	 out.write(optionStr);
	}
%>
</select></p></form>
    	
<%if(!fetch&& (null!=selValue) ){ // setting the focus on selected item%>
  <script type="text/javascript">
  $(document).ready(function() {
   	$("#u188").val('<%=selValue%>').attr('selected',true);
   
   });
  </script> 
<%}


}catch (Exception ex) {// the below block to handle fatal errors%>
			<form id="faqChangeForm" method="GET" name="sortChangeForm">
	         <p class="acgc_chooser">
		       View Questions for: <select class="u188" name="u188" id="u188" onchange="this.form.submit()">
               <option value='#'>No Results</option>
  
<%}%>