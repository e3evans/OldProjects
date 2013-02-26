<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%> 
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="javax.portlet.PortletPreferences"%>
<portlet:defineObjects />
<portlet:actionURL var="actionURL" name="doSavePrefs"/>
<form:form method="post" action="${actionURL}" commandName="FeaturedAppForm" id="FeaturedAppForm">
	<form:label path="appName"> App Name: </form:label> <form:input path="appName" size="100"/><br/>
	<form:label path="appId"> App ID: </form:label> <form:input path="appId" size="100"/><br/>
	<form:label path="appDesc"> App Description:</form:label>  <form:input path="appDesc" size="100"/><br/>
	<form:label path="seqNo">SeqNo: </form:label> <form:input path="seqNo" size="100"/><br/>
	<form:label path="appCategory">AppCategory: </form:label> <form:input path="appCategory" size="100"/>
	<br/><input type="submit" value="Save Preferences"/>
</form:form>

${Success}