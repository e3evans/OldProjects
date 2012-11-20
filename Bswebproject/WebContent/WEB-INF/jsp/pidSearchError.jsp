<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<div id="pidBreadcrumbContainer">
	<div id="breadcrumb" nested="false" dojoType="xwt.widget.layout.Breadcrumb" class="xwtBreadcrumb"
		breadcrumbItems="{items:[{label:'<spring:message code="breadcrumb.pid" />',
		htmlText:'<spring:message code="breadcrumb.pid" />${pid}'}]}" spacerString="&nbsp;&nbsp;">
	</div>
</div>

<!-- Error -->
<div id="pidMessageContainer" class="pidMessageContainer">
	<div class="pidError">${message}</div>
</div>

