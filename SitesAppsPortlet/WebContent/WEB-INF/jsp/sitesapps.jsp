
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html" isELIgnored="false"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"  %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page import="javax.portlet.PortletRequest"%>
<portlet:defineObjects/>


<c:forEach items="${appFormBean.listAppCategory}" var="appCategory">

<c:choose>
<c:when test="${fn:containsIgnoreCase(appCategory.categoryName,'Most Popular') }">
<c:set var="mostpopular" value="${appCategory}"/>
</c:when>
<c:when test="${fn:containsIgnoreCase(appCategory.categoryName,'CLINICAL') }">
<c:set var="clinical" value="${appCategory}"/>
</c:when>
<c:when test="${fn:containsIgnoreCase(appCategory.categoryName,'BUSSINESS') }">
<c:set var="Bussiness" value="${appCategory}"/>
</c:when>
<c:when test="${fn:containsIgnoreCase(appCategory.categoryName,'LOCATIONS')}">
<c:set var="locations" value="${appCategory}"/>
</c:when>
<c:when test="${fn:containsIgnoreCase(appCategory.categoryName,'Strategic Plan')}">
<c:set var="strategicplan" value="${appCategory}"/>
</c:when>
<c:otherwise>
</c:otherwise>
</c:choose>

</c:forEach>
<%

PortletRequest pResquest = (PortletRequest)request;

 %>


<script type="text/javascript">

var userid='<%=pResquest.getPortletSession().getAttribute("userId")%>';

	
	function urlFormat(url){
	    document.getElementById("sitesapps_form_url").value=url;
		document.getElementById("sitesapps_form_userid").value=userid;
		document.sitesapps_form.submit();	
	}


function sitesAppsAlphabeticalSort(){

	//	$("#acgc_sites_apps_alpha_list").html('<div id="searchResultsBox"><div class="loading"><img src="assets/images/ajax-loader.gif" alt="loader" /></div></div>');

		// make ajax call to retrieve upcoming page
        $.get("<portlet:resourceURL id='sitesappsalphabeticalsort'/>?param=resource", {

        }).success(function(data) {
   
		  $("#acgc_sites_apps_alpha_list").html(data);
 		$('#acgc_sites_apps_alpha_list .acgc_content_inner_box_list').easyListSplitter({ 
			colNumber: 4
		});		
 
        }).error(function() {
			console.log("error in making ajax call");
    	});
    }



	$().ready(function(){
	
		// Set Up Page Variables for use on this page
		$jumps        = $('.acgc_jump_to_section');
		$lists        = $('.acgc_sites_apps_list');
		$categoryList = $('#acgc_sites_apps_category_list');
		$alphaList    = $('#acgc_sites_apps_alpha_list');
		$controls     = $('.acgc_sort_by_holder a');

		// Now set up the page by hiding the alpha lists
		$alphaList.hide();

		$('.acgc_content_inner_box_list').easyListSplitter({ 
			colNumber: 4
		});

		// When you click one of the controls, show/hide the appropriate section
		$controls.click(function(e) {
		    sitesAppsAlphabeticalSort();
			e.preventDefault();
			list = $(this).attr('href');
			jump = list.replace('list', 'jump');

			// Also change the text label
			$('.acgc_sort_by_focus .text').html($(this).find('label').html());
			
			

			// Show/hide the jump links
			$jumps.hide();
			$(jump).fadeIn();

			// Show/hide the lists at the bottom of the page
			$lists.hide();
			$(list).fadeIn();
		});
	});

</script>

<form name="sitesapps_form" action="/QuickLinksServiceApp/QuickLinksRedirect/redirect" method="get" target="_blank">
	<input id="sitesapps_form_url" type="hidden" name="url"   value="">
	<input id="sitesapps_form_userid" type="hidden" name="userId" value="" >

</form> 


<div class="acgc_theme_wrapper acgc_relative acgc_striped_bg">
		<div class="acgc_top_content_wrap">
	<div class="acgc_top_content_box" class="acgc_relative">
		<h1>Sites &amp; Apps</h1>
		<div class="acgc_searchblock acgc_radius_5">
			<form action="" method="get" class="acgc_relative">
				<input type="text" name="q" value="" title="Search Sites &amp; Apps..." class="acgc_searchinput" />
				<input type="submit" name="go" value="go" class="acgc_searchsubmit acgc_radius_3" />
			</form>
		</div>
	</div>
</div><div id="acgc_recordsorter" class="acgc_relative">
	<div class="acgc_sort_by">
		Sort By: <span class="acgc_sort_by_focus"><span class="text">Category</span> <img src="/AuroraTheme/themes/html/assets/images/arrow-down-small-header.png" alt="arrow" class="acgc_inline_icon" /></span>
		<div class="acgc_relative">
			<div class="acgc_sort_by_holder">
				<ul>
					<li>
						<a href="#acgc_sites_apps_category_list" title="Sort By Category" onclick="javascript: $(this).children('input').prop('checked', 'checked');">
							<input name="selected" id="selected_1" value="category" checked="checked" type="radio">&nbsp; <label for="selected_1">Category</label>
						</a>
					</li>
					<li>
						<a href="#acgc_sites_apps_alpha_list" title="Sort By Alphabetical" onclick="javascript: $(this).children('input').prop('checked', 'checked');">
							<input name="selected" id="selected_2" value="alphabetical" type="radio">&nbsp; <label for="selected_2">Alphabetical</label>
						</a>
					</li>
				</ul>
			</div>
		</div>
		<div id="acgc_sites_apps_category_jump" class="acgc_jump_to_section">
			<strong>Jump To: </strong>&nbsp; 
			&nbsp;
			<a href="#clinical" title="Clinical">Clinical</a>
			&nbsp; | &nbsp; 
			<a href="#business" title="Business">Business</a>
			&nbsp; | &nbsp; 
			<a href="#locations" title="Locations">Locations</a>
			&nbsp; | &nbsp; 
			<a href="#strategic-plan" title="Strategic Plan">Strategic Plan</a>
		</div>

		 <div id="acgc_sites_apps_alpha_jump" class="acgc_jump_to_section" style="display: none;">
			<strong>Jump To: </strong>&nbsp; 
			<a href="#alpha-a" title="A">A</a>&nbsp;&nbsp; 
			<a href="#alpha-b" title="B">B</a>&nbsp;&nbsp; 
			<a href="#alpha-c" title="C">C</a>&nbsp;&nbsp; 
			<a href="#alpha-d" title="D">D</a>&nbsp;&nbsp; 
			<a href="#alpha-e" title="E">E</a>&nbsp;&nbsp; 
			<a href="#alpha-f" title="F">F</a>&nbsp;&nbsp; 
			<a href="#alpha-g" title="G">G</a>&nbsp;&nbsp; 
			<a href="#alpha-h" title="H">H</a>&nbsp;&nbsp; 
			<a href="#alpha-i" title="I">I</a>&nbsp;&nbsp; 
			<a href="#alpha-j" title="J">J</a>&nbsp;&nbsp; 
			<a href="#alpha-k" title="J">K</a>&nbsp;&nbsp; 
			<a href="#alpha-l" title="L">L</a>&nbsp;&nbsp; 
			<a href="#alpha-m" title="M">M</a>&nbsp;&nbsp; 
			<a href="#alpha-n" title="N">N</a>&nbsp;&nbsp; 
			<a href="#alpha-o" title="O">O</a>&nbsp;&nbsp; 
			<a href="#alpha-p" title="P">P</a>&nbsp;&nbsp; 
			<span title="Q">Q</span>&nbsp;&nbsp; 
			<a href="#alpha-r" title="R">R</a>&nbsp;&nbsp; 
			<a href="#alpha-s" title="S">S</a>&nbsp;&nbsp; 
			<a href="#alpha-t" title="S">T</a>&nbsp;&nbsp;
			<a href="#alpha-u" title="U">U</a>&nbsp;&nbsp; 
			<a href="#alpha-v" title="V">V</a>&nbsp;&nbsp; 
			<a href="#alpha-w" title="W">W</a>&nbsp;&nbsp; 
			<span title="X">X</span>&nbsp;&nbsp; 
			<span title="Y">Y</span>&nbsp;&nbsp; 
			<span title="Z">Z</span>&nbsp;&nbsp; 
		</div> 

	</div>
</div>
		<div id="acgc_sites_apps_category_list" class="acgc_sites_apps_list">
		<div class="acgc_content_box">
	<div class="acgc_spacer_10 acgc_bg_white">
		<!-- spacer -->
	</div>
	<div class="acgc_content_box_body acgc_relative">
		<div class="acgc_content_box_header acgc_relative">
			<h3 class="acgc_content_box_header_title" style="left: -5px;">Most Popular</h3>
		</div>
	</div>
	<div class="acgc_content_box_body acgc_relative">
		<div class="acgc_spacer_10"><!-- spacer --></div>
			<div class="acgc_content_inner_box">
				<ul class="acgc_content_inner_box_list">
				<c:forEach items="${mostpopular.popularapplist}" var="application" varStatus="row">
				 <li>
					<a href="javascript:urlFormat('${application.appURL}')"; title="${application.appName}">
						${application.appName} <img src="/AuroraTheme/themes/html/assets/images/popout-icon-no-shadow.gif" class="acgc_vertical_middle" alt="popout">
					</a>
				</li>
				</c:forEach>
				</ul>
			</div>

		<span class="acgc_legend">
			<img src="/assets/images/popout-icon-no-shadow.gif" class="acgc_vertical_middle" alt="popout" /> External Website
		</span>
		<div class="acgc_spacer_10"><!-- spacer --></div>
	</div>
	<div class="acgc_content_box_bottom_decal"><!-- decal --></div>
</div>
<div class="acgc_content_box" id="clinical">
	<div class="acgc_content_box_top_decal"><!-- top decal --></div>
	<div class="acgc_content_box_body acgc_relative">
		<div class="acgc_content_box_header acgc_relative">
			<div class="content_box_button_wrap">
				<a href="#top" title="Jump To Top" class="content_box_button acgc_radius_5" onclick="javascript: $('html, body').animate({scrollTop: 0}, 300); return false;">
					<span class="content_box_button_inner acgc_radius_3">Back To Top</span>
				</a>
			</div>
			<h3 class="acgc_content_box_header_title" style="left: -5px;">Clinical</h3>
		</div>
	</div>
	<div class="acgc_content_box_body acgc_relative">
		<div class="acgc_spacer_10">
			<!-- spacer -->
		</div>
		<div class="acgc_content_inner_box">
			<ul class="acgc_content_inner_box_list">
				
				<c:forEach items="${clinical.appList}" var="application" varStatus="row">
				 <li>
					<a href="javascript:urlFormat('${application.appURL}')";  title="${application.appName}">
						${application.appName} <img src="/AuroraTheme/themes/html/assets/images/popout-icon-no-shadow.gif" class="acgc_vertical_middle" alt="popout">
					</a>
				</li>
				</c:forEach>
			</ul>
		</div>
		<span class="acgc_legend">
			<img src="/assets/images/popout-icon-no-shadow.gif" class="acgc_vertical_middle" alt="popout" /> External Website
		</span>
		<div class="acgc_spacer_10"><!-- spacer --></div>

	</div>
	<div class="acgc_content_box_bottom_decal"><!-- decal --></div>
</div><div class="acgc_content_box" id="business">
	<div class="acgc_content_box_top_decal">
		<!-- top decal -->
	</div>
	<div class="acgc_content_box_body acgc_relative">
		<div class="acgc_content_box_header acgc_relative">
			<div class="content_box_button_wrap">
				<a href="#top" title="Jump To Top" class="content_box_button acgc_radius_5" onclick="javascript: $('html, body').animate({scrollTop: 0}, 300); return false;">
					<span class="content_box_button_inner acgc_radius_3">Back To Top</span>
				</a>
			</div>
			<h3 class="acgc_content_box_header_title" style="left: -5px;">Business</h3>
		</div>
	</div>
	<div class="acgc_content_box_body acgc_relative">
		<div class="acgc_spacer_10"><!-- spacer --></div>
		<div class="acgc_content_inner_box">
			<ul class="acgc_content_inner_box_list">
				<c:forEach items="${Bussiness.appList}" var="application" varStatus="row">
				 <li>
					<a href="javascript:urlFormat('${application.appURL}')"; title="${application.appName}" >
						${application.appName} <img src="/AuroraTheme/themes/html/assets/images/popout-icon-no-shadow.gif" class="acgc_vertical_middle" alt="popout">
					</a>
				</li>
				</c:forEach>
			</ul>
		</div>
		<span class="acgc_legend">
			<img src="/assets/images/popout-icon-no-shadow.gif" class="acgc_vertical_middle" alt="popout" /> External Website
		</span>
		<div class="acgc_spacer_10"><!-- spacer --></div>
	</div>
	<div class="acgc_content_box_bottom_decal"><!-- decal --></div>
</div><div class="acgc_content_box" id="locations">
	<div class="acgc_content_box_top_decal">
		<!-- top decal -->
	</div>
	<div class="acgc_content_box_body acgc_relative">
		<div class="acgc_content_box_header acgc_relative">
			<div class="content_box_button_wrap">
				<a href="#top" title="Jump To Top" class="content_box_button acgc_radius_5" onclick="javascript: $('html, body').animate({scrollTop: 0}, 300); return false;">
					<span class="content_box_button_inner acgc_radius_3">Back To Top</span>
				</a>
			</div>
			<h3 class="acgc_content_box_header_title" style="left: -5px;">Locations</h3>
		</div>
	</div>
	<div class="acgc_content_box_body acgc_relative">
		<div class="acgc_spacer_10"><!-- spacer --></div>
		<div class="acgc_content_inner_box">
			<ul class="acgc_content_inner_box_list">
				<c:forEach items="${locations.appList}" var="application" varStatus="row">
				 <li>
					<a href="javascript:urlFormat('${application.appURL}')"; title="${application.appName}">
						${application.appName} <img src="/AuroraTheme/themes/html/assets/images/popout-icon-no-shadow.gif" class="acgc_vertical_middle" alt="popout">
					</a>
				</li>
				</c:forEach>
			</ul>
		</div>
		<span class="acgc_legend">
			<img src="/assets/images/popout-icon-no-shadow.gif" class="acgc_vertical_middle" alt="popout" /> External Website
		</span>
		<div class="acgc_spacer_10"><!-- spacer --></div>
	</div>
	<div class="acgc_content_box_bottom_decal"><!-- decal --></div>
</div><div class="acgc_content_box" id="strategic-plan">
	<div class="acgc_content_box_top_decal">
		<!-- top decal -->
	</div>
	<div class="acgc_content_box_body acgc_relative">
		<div class="acgc_content_box_header acgc_relative">
			<div class="content_box_button_wrap">
				<a href="#top" title="Jump To Top" class="content_box_button acgc_radius_5" onclick="javascript: $('html, body').animate({scrollTop: 0}, 300); return false;">
					<span class="content_box_button_inner acgc_radius_3">Back To Top</span>
				</a>
			</div>
			<h3 class="acgc_content_box_header_title" style="left: -5px;">Strategic Plan</h3>
		</div>
	</div>
	<div class="acgc_content_box_body acgc_relative">
		<div class="acgc_spacer_10"><!-- spacer --></div>
		<div class="acgc_content_inner_box">
			<ul class="acgc_content_inner_box_list">
				<c:forEach items="${strategicplan.appList}" var="application" varStatus="row">
				 <li>
					<a href="javascript:urlFormat('${application.appURL}')"; title="${application.appName}">
						${application.appName} <img src="/AuroraTheme/themes/html/assets/images/popout-icon-no-shadow.gif" class="acgc_vertical_middle" alt="popout">
					</a>
				</li>
				</c:forEach>
			</ul>
		</div>															
		<span class="acgc_legend">
			<img src="/assets/images/popout-icon-no-shadow.gif" class="acgc_vertical_middle" alt="popout" /> External Website
		</span>
		<div class="acgc_spacer_10"><!-- spacer --></div>
	</div>
	<div class="acgc_content_box_bottom_decal"><!-- decal --></div>
</div>		</div>
		
		<div id="acgc_sites_apps_alpha_list" class="acgc_sites_apps_list"></div>