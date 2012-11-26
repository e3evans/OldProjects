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

<script type="text/javascript">
function submitURLForm(formName){
	document.getElementById(formName).submit();
}

</script>
</head>

<h3>URL</h3>

<table border="1" cellpadding="4">
	<tr>
	
	
		<td><a href='<portlet:renderURL>
      		<portlet:param name="action" value="editUrl"/>
		</portlet:renderURL>'>Edit Link</a></td>
		<div class="u193" id="u193">
<div id="u193_rtf"><p style="text-align:left;"><span style="font-family:Arial;font-size:16px;font-weight:normal;font-style:normal;text-decoration:none;color:#333333;">MY QUICK LINKS</span></p></div>
</div> 
	</tr> 

<portlet:actionURL var="formAction">
	<portlet:param name='action' value='updateUrl' />
</portlet:actionURL>

<form:form  commandName="urlFormBean" method="post" name="editUrlForm" id="editUrlForm" action="${formAction}">


	<tr>
		<th>Id</th>
		<th>Url</th>
		<th>Description</th>
		
	</tr>
	
   <c:forEach items="${urlFormBean.listUrlBean}" var="urlLst" varStatus="row">
      <tr>
      
      	<td><form:checkbox path="listUrlBean[${row.index}].id" checked="" value="${urlLst.id}" />
										 <c:out value="${urlLst.id}"/></td>
      	<td><form:hidden path="listUrlBean[${row.index}].url" value="${urlLst.url}" />
      	<c:out value="${urlLst.url}"/></td>
      	<td><form:hidden path="listUrlBean[${row.index}].description" value="${urlLst.description}" /><c:out value="${urlLst.description}"/></td>
      	
				
      </tr>
   </c:forEach>   
   
    <a href="javascript:void(0);" onclick="submitURLForm('editUrlForm');"><span>Save</span></a>
	</form:form>
</table>
