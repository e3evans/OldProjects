<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"  %>

<div id="acgc_sites_apps_alpha_jump" class="acgc_jump_to_section"
	style="display: none;">
	<strong>Jump To: </strong>&nbsp;
	<c:set var="countalphets" value="0" />
	<c:forEach var="alphabet" items="${alphabets}">
		<c:choose>
			<c:when test="${alphabet == alphabetkeys[countalphabets]}">
				<a href="#alpha-${fn:toLowerCase(alphabet)}" title="${alphabet}">${alphabet}</a> &nbsp;
            	<c:set var="countalphabets" value="${countalphabets+1}" />
			</c:when>
			<c:otherwise>
				<span title="${alphabet}">${alphabet}</span>&nbsp; 
           </c:otherwise>
		</c:choose>
	</c:forEach>
</div>

<c:forEach var="sitesappsList" items="${availAppsMap}">
    
     <div class="acgc_content_box" id="alpha-${fn:toLowerCase(sitesappsList.key)}">
     
     <c:if test="${fn:toLowerCase(sitesappsList.key)!= 'a' }">
     	<div class="acgc_content_box_top_decal"><!-- top decal --></div>
     </c:if>
     
     	<div class="acgc_content_box_body acgc_relative">
     <c:if test="${fn:toLowerCase(sitesappsList.key)== 'a' }">	
		<div class="acgc_spacer_10">
			<!-- spacer -->
		</div>
	 </c:if>	
		<div class="acgc_content_box_header acgc_relative">
		<c:if test="${fn:toLowerCase(sitesappsList.key)!= 'a' }">
		<div class="content_box_button_wrap">
				<a href="#top" title="Jump To Top" class="content_box_button acgc_radius_5" onclick="javascript: $('html, body').animate({scrollTop: 0}, 300); return false;"><span class="content_box_button_inner acgc_radius_3">Back To Top</span></a>
		</div>
		</c:if>
			<h3 class="acgc_content_box_header_title" style="left: -5px;">${sitesappsList.key}</h3>
		</div>
	</div>
	<div class="acgc_content_box_body acgc_relative">
		<div class="acgc_spacer_10">
			<!-- spacer -->
		</div>
		<div class="acgc_content_inner_box">
			<ul class="acgc_content_inner_box_list">
				<c:forEach var="sitesapps" items="${sitesappsList.value}">
					<li><a href="javascript:sitesAppUrlFormat('${sitesapps.appURL}')"; title="${sitesapps.appName}">${sitesapps.appName}
<!-- 					<img src="/AuroraTheme/themes/html/assets/images/popout-icon-no-shadow.gif" class="acgc_vertical_middle" alt="popout" /> -->
					</a></li>
				</c:forEach>
			</ul>
			<span class="acgc_legend">
<!-- 				<img src="/AuroraTheme/themes/html/assets/images/popout-icon-no-shadow.gif" class="acgc_vertical_middle" alt="popout" /> External Website -->
			</span>
		</div>
		<div class="acgc_clear"><!-- clear floats --></div>
		<div class="acgc_spacer_10">
			<!-- spacer -->
		</div>		
	</div>  
	<div class="acgc_content_box_bottom_decal">
		<!-- decal -->
	</div>
</div><!-- #alpha-"${fn:toLowerCase(sitesappsList.key)}" -->
    
</c:forEach>
	
	<div class="acgc_spacer_46">
		<!-- spacer -->
	</div>
