<%@ include file="/WEB-INF/jsp/include.jsp" %>
<portlet:actionURL var="formAction">
	<portlet:param name="action" value="insert"/>
</portlet:actionURL>
<h3>Add new Contact</h3>
<form:form commandName="contact" method="post" action="${formAction}">
	<table cellpadding="4">
		<tr>
			<td>Contact Id</td>
			<td><form:input path="contactId" size="30" maxlength="80"/></td>
		</tr>
		<tr>
			<td>First Name</td>
			<td><form:input path="firstName" size="30" maxlength="80"/></td>
		</tr>
		<tr>
			<td>Last Name</td>
			<td><form:input path="lastName" size="30" maxlength="80"/></td>
		</tr>
		<tr>
			<td>Email</td>
			<td><form:input path="email" size="30" maxlength="80"/></td>
		</tr>
		<tr>
			<td>Phone Number</td>
			<td><form:input path="phoneNumber" size="30" maxlength="80"/></td>
		</tr>
		<tr>
			<td><input type="submit" name="_finish" value="Save"/></td>
			<td><input type="submit" name="_cancel" value="Cancel"/></td>
		</tr>
	</table>
</form:form>