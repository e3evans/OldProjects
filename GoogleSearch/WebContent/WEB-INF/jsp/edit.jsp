<%@page import="com.aurora.controllers.ViewController"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<portlet:actionURL var="actionURL" name="doSavePrefs"/>

<form:form method="post" action="${actionURL }" commandName="editForm" id="editForm">
	<form:label path="search_environment">SEARCH ENVIRONMENT:  <form:input path="search_environment" size="100"/></form:label>
	<br/><input type="submit" value="Save Preferences"/>
</form:form>

