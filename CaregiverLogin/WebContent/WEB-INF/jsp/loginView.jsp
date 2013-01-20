<%@ include file="/WEB-INF/jsp/include.jsp" %>
<portlet:actionURL var="loginUrl" name="doLogin"/>
<form:form action="${loginUrl}" method="post" commandName="loginForm" id="loginForm">
	<form:label path="userName">User Name:  <form:input path="userName" /></form:label>
	<br/>
	<form:label path="password">Password:  <form:password path="password" /></form:label>
	<br/><input type="submit" value="Login"/>
</form:form>