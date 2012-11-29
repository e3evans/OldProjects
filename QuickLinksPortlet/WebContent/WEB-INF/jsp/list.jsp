<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html" isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<portlet:defineObjects />
  
<a href='<portlet:renderURL>
      		<portlet:param name="action" value="editUrl"/>
		</portlet:renderURL>'>Edit Link</a>
<div id="acgc_my_quicklinks_slider">
<div id="acgc_my_quicklinks_slides">
<c:set var="pageCount" value="1" scope="page" /> 
<c:set var="noOfPages" value="1" scope="page" />
<c:set var="count" value="1" scope="page" /> 
<c:forEach items="${urlList}" var="urlLst">
	<c:if test="${pageCount=='1'}">
		<div class="slide">
	</c:if>
	<c:if test="${count=='1'}">
		<div class="acgc_links_inner_box">
		<ul class="acgc_links_inner_box_list acgc_no_underline">
	</c:if>
	<li>
	<a href="${urlLst.appURL}" title="Metro Wayfinding Maps">${urlLst.appName}<img
		src="/assets/images/popout-icon-no-shadow.gif"
		class="acgc_vertical_middle" alt="popout" /></a>
	</li>
    <c:if test="${count=='8'}">
    </ul></div>
    <c:set var="count" value="0" scope="page" /> 
    </c:if>
    <c:if test="${pageCount=='24'}">
   </div>
<c:set var="pageCount" value="0" scope="page" />
<c:set var="noOfPages" value="${noOfPages + 1}" scope="page" />
</c:if>
<c:set var="count" value="${count + 1}" scope="page" />
<c:set var="pageCount" value="${pageCount + 1}" scope="page" />
</c:forEach>
No of pages <c:out value="${noOfPages}"/>
<div id="acgc_my_quicklinks_controls">
<div id="acgc_my_quicklinks_control_left_arrow"></div>
<div class="icon selected"></div>
<c:forEach   begin="1" end="${noOfPages}" >

<div class="icon"></div>

</c:forEach>
<div id="acgc_my_quicklinks_control_right_arrow"></div>
</div>

