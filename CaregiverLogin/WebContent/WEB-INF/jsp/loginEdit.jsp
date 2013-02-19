<%@ page session="false" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<portlet:actionURL var="actionURL" name="doSavePrefs"/>
<form:form method="post" action="${actionURL }" commandName="editForm" id="editForm">
	<form:label path="wcm_path">WCM PATH:  <form:input path="wcm_path"/></form:label><br/>
	<form:label path="wcm_menuComponent">WCM COMPONENT NAME:  <form:input path="wcm_menuComponent"/></form:label><br/>
	<form:label path="wcm_library">WCM LIBRARY NAME:  <form:input path="wcm_library"/></form:label><br/>
	<form:label path="cookie_env">COOKIE ENV:  <form:input path="cookie_env"/></form:label><br/>
	<br/><input type="submit" value="Save Preferences"/>
</form:form>