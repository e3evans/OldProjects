
<%@ page import="com.ibm.wps.services.ServiceManager"%>
<%@ page import="java.util.*,java.security.Principal"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.ibm.workplace.wcm.api.*"%>
<%@ page import="com.ibm.workplace.wcm.api.exceptions.*"%>
<%@ page import="java.util.*,javax.servlet.jsp.JspWriter,java.io.*"%>
<%@ page import="java.awt.image.*"%>
<%@ page import="javax.imageio.*"%>
<style type="text/css">
	table.myTable { border-collapse:collapse; }
	table.myTable td, table.myTable th,table.myTable tr { border:1px solid black;padding:5px; }
</style>
<div align="center"><h3>WCM Report Tool</h3>  <h6 align="right"> &copy Lokesh Yenneti</h6></div>
<table class="myTable" align="center">
<tr ><td colspan="1">Content Name</td><td>SiteArea </td><td>Site URL(from the content)</td></tr>
<%

try {
        String sitePath			  = "/Caregiver/";//Top Most SITE path
        String ContentLibrary 	  = "CaregiverContentLibrary_en"; //Library Name
        String siteareaname		  = "Caregiver";  //Top most SITE
        //String compoName	      = "Site URL";   //COMPONENT TO MATCH in CONTENT
        String compoName	      = "Short_Description"; 
		
		int matcher=0;
		int totalContent=0;
		int SAcount=0;
		DocumentId docId=null;
		DocumentIdIterator itemsIterator =null;
		Workspace myworkspace            = WCM_API.getRepository().getSystemWorkspace();
		DocumentLibrary MyLibrary 		 = myworkspace.getDocumentLibrary(ContentLibrary);
		
		//RenderingContext rc = myworkspace.createRenderingContext(request,response, new java.util.HashMap(), "http://ipof your server:portnum/wps/wcm", "/myconnect");
		RenderingContext rc =myworkspace.createRenderingContext(request,response,new HashMap());
		
		rc.setRenderedContent(ContentLibrary + sitePath);
		
			
		if (MyLibrary == null){
			out.println("Library is null");
		}
		else
		{
		
			Content con=null;
			myworkspace.setCurrentDocumentLibrary(MyLibrary);
			DocumentIdIterator siteareaiterator= myworkspace.findByName(DocumentTypes.Site,siteareaname);
			
			while(siteareaiterator.hasNext()){
			
			
			DocumentId sid=siteareaiterator.nextId();
			SiteArea sarea=(SiteArea)myworkspace.getById(sid);
			DocumentIdIterator contentiterator=sarea.getAllDirectChildren();
			//out.println("TOTAL Site Areas::"+contentiterator.getCount());
						while(contentiterator.hasNext()){
						   
							DocumentId cid=contentiterator.nextId();
							//out.println("</br>SA:"+cid.getName());
							if(cid.getType().toString().equalsIgnoreCase(DocumentTypes.SiteArea.toString())){
								SAcount++;
							}	
							if(cid.getType().toString().equalsIgnoreCase(DocumentTypes.Content.toString())){
							  totalContent++;
							
								con=(Content)myworkspace.getById(cid);
								
								
								DocumentId cont=con.getDirectParent();
								SiteArea ct=(SiteArea)myworkspace.getById(cont);
								
					
								ContentComponentIterator compIterator=con.componentIterator();
								
								TextComponent tc=null;
								
									while(compIterator.hasNext()){
										
										
										ContentComponent contCompo=(ContentComponent)compIterator.next();
										
										if(compoName.equalsIgnoreCase(contCompo.getName() )){
											 if(contCompo instanceof TextComponent){
											   tc=(TextComponent)contCompo;
											   String siteURL=tc.getText();
											   matcher ++;
											   //Printing
											   out.println("<tr>");
											   out.println("<td>"+con.getTitle()+"</td>");
											   out.println("<td>"+ct.getTitle()+"</td>");
											   out.println("<td>"+siteURL+"</td>");
											   out.println("</tr>");
											  }
											}
									
									}
							
							}
					
						}
			
			}
		}
		
 out.println("<tr><td colspan='3'> <font color='red' >"+matcher+" </font> matches found  from : <font color='blue'>"+totalContent+" </font> total contents from a total of <font color='green'>"+SAcount+" </font> SiteAreas in the library</td></tr>");
 myworkspace.logout();
} catch (Exception e) {
	out.println("Exception " + e.getMessage());
	e.printStackTrace();
}

%>
</table>