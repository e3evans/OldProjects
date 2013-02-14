<%@ page
	import="java.util.*,
				 java.io.*,
				 java.lang.*,
				 com.ibm.workplace.wcm.api.*"%>

<%@taglib uri="/WEB-INF/tld/wcm.tld" prefix="wcm"%>
<%
	String selValue = null;
	if (null != request.getParameter("selected")) {
		selValue = request.getParameter("selected");
		request.removeAttribute("selection");
		request.setAttribute("selection", selValue);
	} else {
		selValue = "desc";
		request.getSession().setAttribute("selection", selValue);
	}
	//out.write("SELECTED:"+selValue);
%>
<form id="faqChangeForm" method="GET" name="sortChangeForm">
	<p class="acgc_chooser">
		View Questions for: <select class="u188" id="u188">
	<%
	try {

		String contentLibrary = "CaregiverContentLibrary_en";
		String DesignLib = "CaregiverDesignLibrary";

		String faqmenu = "AllEvents_ASC_MNU";
		String componentName = "";
		String faqSitePath = "/Caregiver/FAQ";
		DocumentId docId;
		LibraryComponent component;
				
		//Creating a workspace 
		Workspace ws = (Workspace) pageContext
				.getAttribute(Workspace.WCM_WORKSPACE_KEY);

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
			if (sid.getType().toString().equalsIgnoreCase(DocumentTypes.SiteArea.toString())) {
				SiteArea sitearea = (SiteArea) ws.getById(sid);

				DocumentIdIterator childSites = sitearea.getAllDirectChildren();

				while (childSites.hasNext()) {
					DocumentId childSiteArea = iterator.nextId();
					if (DocumentTypes.SiteArea.toString().equalsIgnoreCase(childSiteArea.getType().toString())) {
						SiteArea childSA = (SiteArea) childSiteArea;
						out.write("<option onchange='document.faqChangeForm.submit()' value='" + contentLibrary+ faqSitePath + childSA.getName()+"'>"+ childSA.getTitle()+ "</option>");

					}
				}

			}
		}
		ws.logout();
	} catch (Exception ex) {
		System.out.println("Exception in siteLanding.jsp :"+ ex.getMessage());
	}
%>
<!--
<option value="Category 1">Category 1</option>
<option value="Category 2" selected="">Category 2</option>
<option value="Category 3">Category 3</option>
<option value="Category 4">Category 4</option>
<option value="Category 3">Category 5</option>
-->
		</select>
	</p>
</form>