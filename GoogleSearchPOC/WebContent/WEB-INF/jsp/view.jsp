<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script language="JavaScript" src='<%= renderResponse.encodeURL(renderRequest.getContextPath() + "/js/jquery.min.js") %>'></script>
<portlet:actionURL var="actionURL">
	<portlet:param name="action" value="doSearch"/>
</portlet:actionURL>


<form:form method="post" action="${actionURL }" commandName="searchForm" id="searchForm">
	<form:label path="searchString" cssClass="input"><span>INPUT SEARCH STRING:  </span><form:input path="searchString"/></form:label>
	<hr/>
	<input type="submit" value="SEARCH"/>
</form:form>


