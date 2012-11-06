<%@ include file="/WEB-INF/jsp/include.jsp" %>

<h3>Contact List</h3>

<table border="1" cellpadding="4">
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td><a href='<portlet:renderURL>
      		<portlet:param name="action" value="insert"/>
		</portlet:renderURL>'>Add Contact</a></td>
	</tr>

	<tr>
		<th>Contact Id</th>
		<th>First Name</th>
		<th>Last Name</th>
		<th>Email</th>
		<th>Phone</th>
	</tr>
   <c:forEach items="${contactList}" var="contact">
      <tr>
      	<td><a href='<portlet:renderURL>
      		<portlet:param name="action" value="update"/>
      		<portlet:param name="contactId">
      			<jsp:attribute name="value">
	      			<c:out value="${contact.contactId}"/>
      			</jsp:attribute>
      		</portlet:param>	
      	</portlet:renderURL>
      	'><c:out value="${contact.contactId}"/></a>
      	 </td>
      	<td><c:out value="${contact.firstName}"/></td>
      	<td><c:out value="${contact.lastName}"/></td>
      	<td><c:out value="${contact.email}"/></td>
      	<td><c:out value="${contact.phoneNumber}"/></td>
      	<td><a href='<portlet:actionURL>
      		<portlet:param name="action" value="delete"/>
      		<portlet:param name="contactId">
      			<jsp:attribute name="value">
	      			<c:out value="${contact.contactId}"/>
      			</jsp:attribute>
      		</portlet:param>	
      	</portlet:actionURL>
      	'>Remove</a>
      	 </td>
      </tr>
   </c:forEach>   
	
</table>