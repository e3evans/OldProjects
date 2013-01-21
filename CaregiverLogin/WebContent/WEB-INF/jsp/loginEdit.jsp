<%@ page session="false" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<portlet:actionURL var="actionURL" name="doSavePrefs"/>
<form:form method="post" action="${actionURL }" commandName="editForm" id="editForm">
	<form:label path="wcm_path">WCM PATH:  <form:input path="wcm_path"/></form:label><br/>
	<form:label path="wcm_WebAppPath">WCM WEB APP PATH:  <form:input path="wcm_WebAppPath"/></form:label><br/>
	<form:label path="wcm_servletPath">WCM SERVLET PATH:  <form:input path="wcm_servletPath"/></form:label><br/>
	<form:label path="wcm_menuComponent">WCM COMPONENT NAME:  <form:input path="wcm_menuComponent"/></form:label><br/>

	<br/><input type="submit" value="Save Preferences"/>
</form:form>