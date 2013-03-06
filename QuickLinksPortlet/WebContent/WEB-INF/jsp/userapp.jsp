<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html" isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page import="javax.portlet.PortletPreferences"%>
<portlet:defineObjects />
<portlet:actionURL var="formAction">
	<portlet:param name='action' value='updateFeaturedApp' />
</portlet:actionURL>
<script>
	function loadQuicklinksEditOverlay() {
		// make ajax call to retrieve upcoming page
		$.get("<portlet:resourceURL id='quicklinksEditList'/>?param=resource",
				{

				}).success(function(data) {

			//load the overlay
			$().overlay({
				content : data
			});

			//do search field swaps
			acgcInputTitleSwaps();

			//add quiclinks hooks
			acgcAddQuicklinksHooks();

		}).error(function() {
			console.log("error in making ajax call");
		});
	}

	$().ready(function() {
		initQuickLinksMenu("portlet");
	});
</script>

<div class="acgc_content_box">
	<ul class="acgc_content_box_tabs">
		<li class="acgc_content_box_tabs_tab" style="width: 465px"><a
			href="/cgc/myportal/connect/home" title=""
			class="acgc_content_box_tabs_tab_link"> <span class="acgc_title">
					Aurora </span> <span class="acgc_desc"> Official news,<br />blogs
					&amp; events </span> </a>
		</li>
		<li class="acgc_content_box_tabs_tab_spacer">
			<!-- spacer -->
		</li>
		<li class="acgc_content_box_tabs_tab selected" style="width: 464px">
			<a href="/cgc/myportal/connect/home/me"
			class="acgc_content_box_tabs_tab_link"> <span class="acgc_title">
					Me </span> <span class="acgc_desc"> My quick links <br />& news </span> </a>
		</li>
		<li class="acgc_content_box_tabs_tab_spacer">
			<!-- spacer -->
		</li>

		<!--<li class="acgc_content_box_tabs_tab" style="width:465px">
			<a href="/cgc/myportal/connect/Home/Team"  class="acgc_content_box_tabs_tab_link">
				<span class="acgc_title">
					Team
				</span>
				<span class="acgc_desc">
					My team news,<br />projects &amp; events
				</span>
			</a>
		</li>-->
	</ul>
</div>

<div class="acgc_ui_overlay_mast">
	<div class="acgc_ui_overlay_base">
		<div class="acgc_ui_overlay_canvas acgc_striped_bg">
			<!-- content loads here -->
		</div>
	</div>
</div>


<div class="acgc_content_box">
	<div class="acgc_content_box_top_decal">
		<!--  top decal -->
	</div>
	<div class="acgc_content_box_body acgc_relative">
		<div class="acgc_content_box_header acgc_relative">
			<div class="content_box_button_wrap">
				<a id="acgc_quicklinks_picker_trigger"
					class="content_box_button acgc_radius_5" title="" href="#edit"
					onclick="javascript: loadQuicklinksEditOverlay(); return false;">
					<span class="content_box_button_inner acgc_radius_3"> Edit
						Quicklink </span> </a>
			</div>
			<div class="acgc_content_box_header_icon acgc_site_icon links">
				<!--  icon -->
			</div>
			<h3 class="acgc_content_box_header_title">My Quick Links</h3>
		</div>
		<div id="acgc_my_quicklinks_canvas_target">
			<!--  quicklinks html loads here -->
			<div class="acgc_loading_indicator">
				<!--  loading indicator .. loading .. loading .. robot -->
			</div>
		</div>
		<div class="acgc_clear"></div>
		<div class="acgc_add_app_callout">
			<div class="acgc_app_callout_box">Featured Site:</div>
			<div class="acgc_app_title_box">
				<%=portletPreferences.getValue("appName", "Not set")%>
				<br />
				<a title="<%=portletPreferences.getValue("appCategory", "Not set")%>" href="/cgc/myportal/connect/home/sites"><%=portletPreferences.getValue("appCategory", "Not set")%></a>
			</div>
			<div class="acgc_app_text_box"><%=portletPreferences.getValue("appDesc", "Not set")%></div>
			<div class="acgc_app_bttn_box">
				<a class="acgc_green_bttn acgc_radius_5"
					title="Add To My Quick Links" href="${formAction}"> <span
					class="acgc_green_bttn_inner acgc_radius_3"> Add To My Quick
						Links </span> </a>
			</div>
		</div>
		<div class="acgc_spacer_10"></div>
	</div>
	<div class="acgc_content_box_bottom_decal"></div>
</div>