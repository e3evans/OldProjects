<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html" isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<portlet:defineObjects />
 <head>
 <style type="text/css">
.u193 {
    font-family: Arial;
    height: 18px;
    left: 100px;
    position: absolute;
    text-align: left;
    top: 627px;
    width: 290px;
    word-wrap: break-word;
}
div {
    background-repeat: no-repeat;
}

.u201 {
    font-family: Arial;
    height: 18px;
    left: 2px;
    position: absolute;
    text-align: left;
    top: 6px;
    width: 111px;
    word-wrap: break-word;
}
div {
    background-repeat: no-repeat;
}
</style>
</head>

<h3>URL</h3>
Now is the time for all good men to come to the aid of their country.

<table border="1" cellpadding="4">
	<tr>

		
		<td><a href='<portlet:renderURL>
      		<portlet:param name="action" value="editUrl"/>
		</portlet:renderURL>'>Edit Link</a></td>
		
	</tr>


	<tr>
		<th>Id</th>
		<th>Url</th>
		<th>UserId</th>
		
	</tr>
	
   <c:forEach items="${urlList}" var="urlLst">
      <tr>
      
      	<td><c:out value="${urlLst.id}"/></td>
      	<td><c:out value="${urlLst.url}"/></td>
      	<td><c:out value="${urlLst.userid}"/></td>
      	
      	
      </tr>
   </c:forEach>   
	
</table>
