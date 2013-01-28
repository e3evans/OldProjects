<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"  %>


	
<c:forEach var="sitesappsList" items="${availAppsMap}">
    
     <div class="acgc_content_box" id="alpha-${fn:toLowerCase(sitesappsList.key)}">
	<div class="acgc_content_box_body acgc_relative">
		<div class="acgc_spacer_10">
			<!-- spacer -->
		</div>
		<div class="acgc_content_box_header acgc_relative">
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
				<li><a href="${sitesapps.appURL}" title="Title of Application Here" target="_blank">${sitesapps.appName} <img src="/AuroraTheme/themes/html/assets/images/popout-icon-no-shadow.gif" class="acgc_vertical_middle" alt="popout" /></a></li>
				</c:forEach>
			</ul>
			<span class="acgc_legend">
				<img src="/assets/images/popout-icon-no-shadow.gif" class="acgc_vertical_middle" alt="popout" /> External Website
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
</div><!-- #alpha-a -->
    
</c:forEach>
	
	<div class="acgc_spacer_46">
		<!-- spacer -->
	</div>
