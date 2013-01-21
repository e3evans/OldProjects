<%@page import="com.ibm.workplace.wcm.api.Repository"%>
<%@page import="com.ibm.workplace.wcm.api.WebContentService"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@page import="com.aurora.controllers.LoginViewController"%>
<%@page import="com.ibm.workplace.wcm.api.RenderingContext"%>
<%@page import="com.ibm.workplace.wcm.api.Workspace"%>
<%@ page session="false" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%String userPassword[] = System.getProperty(LoginViewController.ENV_USERPASSWORD).split(":");
  PortletPreferences prefs = renderRequest.getPreferences();
  String path = prefs.getValue(LoginViewController.PREF_WCM_PATH,"");
  String libCmpnt = prefs.getValue(LoginViewController.PREF_WCM_COMPONENT,"");
  String lib = prefs.getValue(LoginViewController.PRED_WCM_LIB,"");
%>
*<%=lib%>*
<portlet:actionURL var="loginUrl" name="doLogin"/>
<h2>WCM COMPONENT</h2><hr/>
<wcm:initworkspace username="<%=userPassword[0] %>" password="<%=userPassword[1] %>"/>
<wcm:setExplicitContext path="<%=path%>" />
<wcm:libraryComponent library="<%=lib%>" name="<%=libCmpnt %>"/>


<hr/>
<h2>END WCM COMPONENT</h2>
<form:form action="${loginUrl}" method="post" commandName="loginForm" id="loginForm">
	<form:label path="userName">User Name:  <form:input path="userName" /></form:label>
	<br/>
	<form:label path="password">Password:  <form:password path="password" /></form:label>
	<br/><input type="submit" value="Login"/>
</form:form>
<% 
/*
GET AND CLOSE THE WCM WORKSPACE WHEN WE ARE DONE WITH IT.
*/
InitialContext ctx = new InitialContext();
WebContentService wcs = (WebContentService)ctx.lookup("portal:service/wcm/WebContentService");
Repository repo = wcs.getRepository();
repo.endWorkspace();

%>