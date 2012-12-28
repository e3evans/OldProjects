<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html" isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<portlet:defineObjects/>


<portlet:actionURL var="formAction">
	<portlet:param name='action' value='updateUrl' />
</portlet:actionURL>

			


    <div id="acgc_quicklinks_picker">
         <div class="acgc_overlay_header">
					<div class="acgc_content_box_header acgc_relative">
						<div class="content_box_button_wrap">
							<img src="/AuroraTheme/themes/html/assets/images/megamenu-close-icon.gif" alt="close" class="acgc_ui_overlay_close_trigger" />
						</div>
						<div class="acgc_content_box_header_icon acgc_site_icon_alt links">
							<!-- section icon -->
						</div>
						<h3 class="acgc_content_box_header_title">
							Edit My Quick Links
						</h3>
					</div>
				</div>
				<div class="acgc_overlay_body" id="acgc_quicklinks_picker_control">
					<div class="acgc_float_right" style="padding: 10px 140px 0 0;">
						<span id="acgc_quicklinks_picker_show_selected_label">Show My Selected:</span>
						&nbsp;
						<span id="acgc_quicklinks_show_sites_trigger">
							<input type="checkbox" name="acgc_show_sites" value="on" /> Sites &amp; Apps
						</span>
					</div>
					<div class="acgc_searchblock acgc_radius_5">
						<form action="" method="get" class="acgc_relative" onsubmit="javascript: acgcQuickLinksPickerClear(); return false;">
							<input type="text" name="q" value="" title="Search Quick Links..." class="acgc_searchinput" />
							<input type="submit" name="go" value="x" class="acgc_searchsubmit acgc_radius_3" />
						</form>
					</div>
					
				</div>	



<form:form  commandName="appFormBean" method="post" name="editUrlForm" id="editUrlForm" action="${formAction}">

    <div class="acgc_overlay_body" id="acgc_quicklinks_picker_canvas">
    <c:set var="Count" value="1" scope="page" /> 
	
   <c:forEach items="${appFormBean.listMenuApp}" var="appLst" varStatus="row">
 
    <c:set var="checked" value="${menuApp.alreadyRegistered}"/>
    <c:choose>
    	<c:when test="${appLst.alreadyRegistered}">
    	 	<div class="acgc_quicklinks_link_canvas acgc_relative site selected">
    	</c:when>
    	<c:otherwise>
    		<div class="acgc_quicklinks_link_canvas acgc_relative site">
    	</c:otherwise>
    </c:choose>
        <c:choose>
         <c:when test="${appLst.defaultApp}">
        <form:checkbox  path="listMenuApp[${row.index}].app.checked" id="listUrlBean[${row.index}].app.checked" checked="checked" onclick="javascript:alert('This app is added for you by admin you can not remove this app');this.checked='checked';"/>
        </c:when>
        <c:when test="${appLst.alreadyRegistered}">
        <form:checkbox  path="listMenuApp[${row.index}].app.checked" id="listUrlBean[${row.index}].app.checked" checked="checked"/>
        </c:when>
        <c:otherwise>
        <form:checkbox  path="listMenuApp[${row.index}].app.checked" id="listMenuApp[${row.index}].app.checked" />
        </c:otherwise>
        </c:choose>
        
	    <form:hidden path="listMenuApp[${row.index}].app.appName" value="${appLst.app.appName}" />
	    <form:hidden path="listMenuApp[${row.index}].app.appDesc" value="${appLst.app.appDesc}" />
		<form:hidden path="listMenuApp[${row.index}].app.seqNo" value="${appLst.app.seqNo}" />
      	<form:hidden path="listMenuApp[${row.index}].app.appId" value="${appLst.app.appId}" />
      	
	<div class="acgc_quicklinks_link_body">
	    <strong> <c:out value="${appLst.app.appName}"/> </strong>  
	     <p><c:out value="${appLst.app.appDesc}"/></p>
	</div>
 
    </div>     	
   </c:forEach>   
 
   <div class="acgc_clear"><!-- clear --></div>	
   </div>
   
    
	</form:form>
	
	
				<div class="acgc_overlay_body">
					<div class="content_box_button_wrap">
						<a href="#submit" onclick="javascript: document.editUrlForm.submit(); return false;" title="Apply Changes" id="acgc_login_submit_bttn" class="acgc_large_green_button acgc_radius_5">
							<span class="acgc_large_green_button_inner acgc_radius_3">
								Apply Changes
							</span>
						</a>
					</div>
				</div>
			</div>
