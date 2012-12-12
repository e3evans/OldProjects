<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html" isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<portlet:defineObjects />
<script>

	function loadQuicklinksEditOverlay(){

		// make ajax call to retrieve upcoming page
        $.get("<portlet:resourceURL id='quicklinksEditList'/>", {

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
			alert('no buenoo - did not reach server?');
    	});
    }
                        
	$().ready(function(){
		initQuickLinksMenu('test', 'test',"portlet");
	});
	                        
</script> 


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
				<a id="acgc_quicklinks_picker_trigger" class="content_box_button acgc_radius_5" title="" href="#edit" onclick="javascript: loadQuicklinksEditOverlay(); return false;">
					<span class="content_box_button_inner acgc_radius_3"> Edit Quicklink </span>
				</a>
			</div>
			<div class="acgc_content_box_header_icon acgc_site_icon links"> 
				<!--  icon -->
			</div>
			<h3 class="acgc_content_box_header_title"> My Quick Links </h3>
		</div>
		<div id="acgc_my_quicklinks_canvas_target">
			<!--  quicklinks html loads here -->
			<div class="acgc_loading_indicator">
				<!--  loading indicator .. loading .. loading .. robot -->
			</div>
		</div>
		<div class="acgc_clear"></div>
		<div class="acgc_add_app_callout">
			<div class="acgc_app_callout_box"> Featured App: </div>
			<div class="acgc_app_title_box">
				Title Of App Goes Here
				<br />
				<a title="App Category" href="">App Category</a>
			</div>
			<div class="acgc_app_text_box"> Description lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus molestie dolor ut est placerat ornare... </div>
			<div class="acgc_app_bttn_box">
				<a class="acgc_green_bttn acgc_radius_5" title="Browse Apps" href="">
					<span class="acgc_green_bttn_inner acgc_radius_3"> Add To My Apps </span>
				</a>
			</div>
		</div>
		<div class="acgc_spacer_10"> </div>
	</div>
	<div class="acgc_content_box_bottom_decal"> </div>
</div>