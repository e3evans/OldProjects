<%@page import="javax.portlet.PortletPreferences"%>
<%@page import="com.aurora.controllers.LoginViewController"%>
<%@page import="com.ibm.workplace.wcm.api.RenderingContext"%>
<%@page import="com.ibm.workplace.wcm.api.Workspace"%>
<%@ page session="false" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%String userPassword[] = System.getProperty(LoginViewController.ENV_USERPASSWORD).split(":");
  PortletPreferences prefs = renderRequest.getPreferences();
  String webAppPath = prefs.getValue(LoginViewController.PREF_WCM_WEBAPP,"");
  String path = prefs.getValue(LoginViewController.PREF_WCM_PATH,"");
  String servletPath = prefs.getValue(LoginViewController.PREF_WCM_SERVLET,"");
  String libCmpnt = prefs.getValue(LoginViewController.PREF_WCM_COMPONENT,"");
%>
<%=path%>
<portlet:actionURL var="loginUrl" name="doLogin"/>
<h2>WCM COMPONENT</h2><hr/>
<wcm:initworkspace username="waslocal" password="waslocal"/>
<wcm:setExplicitContext path="<%=path%>" wcmWebAppPath="<%=webAppPath%>" wcmServletPath="/myconnect"/>
<wcm:libraryComponent name="Articles List"/>
<hr/>
<h2>END WCM COMPONENT</h2>
<form:form action="${loginUrl}" method="post" commandName="loginForm" id="loginForm">
	<form:label path="userName">User Name:  <form:input path="userName" /></form:label>
	<br/>
	<form:label path="password">Password:  <form:password path="password" /></form:label>
	<br/><input type="submit" value="Login"/>
</form:form>
<% 
Workspace ws = (Workspace) pageContext.getAttribute(Workspace.WCM_WORKSPACE_KEY);
ws.logout();%>