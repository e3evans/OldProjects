<%@page import="java.util.HashMap"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@page import="com.aurora.controllers.LoginViewController"%>
<%@page import="com.ibm.workplace.wcm.api.*" %>
<%@ page session="false" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%
  PortletPreferences prefs = renderRequest.getPreferences();
  String path = prefs.getValue(LoginViewController.PREF_WCM_PATH,"");
  String libCmpnt = prefs.getValue(LoginViewController.PREF_WCM_COMPONENT,"");
  String lib = prefs.getValue(LoginViewController.PRED_WCM_LIB,""); 
%>

<portlet:actionURL var="loginUrl" name="doLogin"/>
<wcm:initworkspace />
<wcm:setExplicitContext path="<%=path%>" />
<%
	Workspace ws = (Workspace)pageContext.getAttribute(Workspace.WCM_WORKSPACE_KEY);
	ws.useUserAccess(false);
 %>


<div class="acgc_theme_wrapper acgc_relative acgc_striped_bg">
<div class="acgc_content_box">
	<div class="acgc_spacer_10 acgc_bg_white"><!-- spacer --></div>
	<div class="acgc_content_box_body">
		<h1 class="acgc_welcometo_header">Welcome to <strong>Caregiver Connect</strong></h1>
		<div class="acgc_clear"><!-- clear --></div>
	</div>
	<div class="acgc_spacer_10 acgc_bg_white"><!-- spacer --></div>
	<div class="acgc_relative" id="acgc_login">
		<div class="acgc_intro_text">
			<h3>Log in to your account for full access</h3>
			<p>At Aurora Health Care, we help people live well. Caregiver Connect is where Aurora caregivers and partners find information, transact business and collaborate to fulfill this purpose.</p>
		</div>


<form:form action="${loginUrl}" method="post" commandName="loginForm" id="acgc_login_form">
	<c:if test="${loginForm.badLogin}">
		<div class="acgc_error">
			<p>Invalid Username or Password. Please try again.</p>
		</div>
	</c:if>
	<c:if test="${loginForm.badSession}">
		<div class="acgc_error">
			<p>For security reasons, your Caregiver Connect session has timed out. Please log back in to restore your access.</p>
		</div>
	</c:if>
	<div class="acgc_large_form_field">
		<label>Username</label>
		<form:input path="userName" cssClass="acgc_title_swap" title="Enter username"/>
	</div>
	<div class="acgc_large_form_field">
		<label>Password</label>
		<form:password path="password" cssClass="acgc_title_swap acgc_this_was_password" title="Enter password"/>
	</div>
	<div class="acgc_login_utils">
		<ul>
			<li>
				<a id="inline" title="Forgot your username or password?" href="#data">Forgot your username or password?</a>
			</li>
		</ul>
	</div>
	<div class="acgc_large_form_submit">
		<a class="acgc_large_green_button acgc_radius_5" id="acgc_login_submit_bttn" title="" href="javascript:document.getElementById('acgc_login_form').submit()">
			<span class="acgc_large_green_button_inner acgc_radius_3">Log in</span>
		</a>
	</div>
</form:form>


<!-- START WCM -->
	<wcm:libraryComponent library="<%=lib%>" name="<%=libCmpnt %>"/>
<!-- END WCM -->
		<div class="acgc_content_login_links">
			<p><strong>NOTE:</strong> Please log in to read full articles and gain access to all material</p>
			<div class="acgc_content_login_quicklinks">
				<h3>Quick Links:</h3>
				<ul>
					<li><a href="https://iconnect.aurora.org/cafeteriamenu/home.do" target="_blank">Cafeteria Menus</a></li>
					<li><a href="https://mail.ahcmessaging.org/owa/auth/logon.aspx?replaceCurrent=1&url=https%3a%2f%2fmail.ahcmessaging.org%2fowa%2f" target="blank">Email</a></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="acgc_spacer_10 acgc_bg_white"><!-- spacer --></div>	
	<div class="acgc_spacer_10 acgc_bg_white"><!-- spacer --></div>
	<div class="acgc_content_box_body">
		<p>&nbsp;</p>
	</div>
	<div class="acgc_spacer_10 acgc_bg_white"><!-- spacer --></div>	
	<div class="acgc_content_box_bottom_decal">
		<!-- decal -->
	</div>
</div>
</div>
<script type="text/javascript">

/*
 In-Field Label jQuery Plugin
 http://fuelyourcoding.com/scripts/infield.html

 Copyright (c) 2009 Doug Neiner
 Dual licensed under the MIT and GPL licenses.
 Uses the same license as jQuery, see:
 http://docs.jquery.com/License

*/
(function(d){d.InFieldLabels=function(e,b,f){var a=this;a.$label=d(e);a.label=e;a.$field=d(b);a.field=b;a.$label.data("InFieldLabels",a);a.showing=true;a.init=function(){a.options=d.extend({},d.InFieldLabels.defaultOptions,f);if(a.$field.val()!==""){a.$label.hide();a.showing=false}a.$field.focus(function(){a.fadeOnFocus()}).blur(function(){a.checkForEmpty(true)}).bind("keydown.infieldlabel",function(c){a.hideOnChange(c)}).bind("paste",function(){a.setOpacity(0)}).change(function(){a.checkForEmpty()}).bind("onPropertyChange",
function(){a.checkForEmpty()})};a.fadeOnFocus=function(){a.showing&&a.setOpacity(a.options.fadeOpacity)};a.setOpacity=function(c){a.$label.stop().animate({opacity:c},a.options.fadeDuration);a.showing=c>0};a.checkForEmpty=function(c){if(a.$field.val()===""){a.prepForShow();a.setOpacity(c?1:a.options.fadeOpacity)}else a.setOpacity(0)};a.prepForShow=function(){if(!a.showing){a.$label.css({opacity:0}).show();a.$field.bind("keydown.infieldlabel",function(c){a.hideOnChange(c)})}};a.hideOnChange=function(c){if(!(c.keyCode===
16||c.keyCode===9)){if(a.showing){a.$label.hide();a.showing=false}a.$field.unbind("keydown.infieldlabel")}};a.init()};d.InFieldLabels.defaultOptions={fadeOpacity:0.5,fadeDuration:300};d.fn.inFieldLabels=function(e){return this.each(function(){var b=d(this).attr("for");if(b){b=d("input#"+b+"[type='text'],input#"+b+"[type='search'],input#"+b+"[type='tel'],input#"+b+"[type='url'],input#"+b+"[type='email'],input#"+b+"[type='password'],textarea#"+b);b.length!==0&&new d.InFieldLabels(this,b[0],e)}})}})(jQuery);


$("label.infield").inFieldLabels({
	fadeOpacity:  0, 
	fadeDuration: 0
});


$("#acgc_login_form input").keypress(function(event) {
    if (event.which == 13) {
        event.preventDefault();
        $("#acgc_login_form").submit();
    }
});

$(document).ready(function(){
	$("a#inline").fancybox({
		'hideOnContentClick':true
	});

});

</script>

<div style="display:none;">
	<div id="data">To log into the beta Caregiver Connect release, be sure to use your Windows log in credentials (also used to log into your computer).
	<br/><br/>If you do not remember your username or password, please contact the IS Service Desk at 414-647-3520 or 800-889-9677 outside of Milwaukee.
	</div>
</div>

<% 
/*
GET AND CLOSE THE WCM WORKSPACE WHEN WE ARE DONE WITH IT.
*/
InitialContext ctx = new InitialContext();
WebContentService wcs = (WebContentService)ctx.lookup("portal:service/wcm/WebContentService");
Repository repo = wcs.getRepository();
repo.endWorkspace();

%>
