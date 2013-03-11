<%@page import="java.util.HashMap"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@page import="org.aurora.caregiverlogin.controllers.LoginViewController"%>
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
		<label for="userName" class="infield">Enter Username:</label>
		<form:input path="userName" cssClass="infield" title="Enter username"/>
	</div>
	<div class="acgc_large_form_field">
		<label>Password</label>
		<label for="password" class="infield">Enter Password:</label>
		<form:password path="password" cssClass="infield" title="Enter password"/>
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
(function($){$.InFieldLabels=function(label,field,options){var base=this;base.$label=$(label);base.label=label;base.$field=$(field);base.field=field;base.$label.data("InFieldLabels",base);base.showing=true;base.init=function(){base.options=$.extend({},$.InFieldLabels.defaultOptions,options);setTimeout(function(){if(base.$field.val()!==""){base.$label.hide();base.showing=false;}},200);base.$field.focus(function(){base.fadeOnFocus();}).blur(function(){base.checkForEmpty(true);}).bind('keydown.infieldlabel',function(e){base.hideOnChange(e);}).bind('paste',function(e){base.setOpacity(0.0);}).change(function(e){base.checkForEmpty();}).bind('onPropertyChange',function(){base.checkForEmpty();}).bind('keyup.infieldlabel',function(){base.checkForEmpty()});setInterval(function(){if(base.$field.val()!=""){base.$label.hide();base.showing=false;};},100);};base.fadeOnFocus=function(){if(base.showing){base.setOpacity(base.options.fadeOpacity);}};base.setOpacity=function(opacity){base.$label.stop().animate({opacity:opacity},base.options.fadeDuration);base.showing=(opacity>0.0);};base.checkForEmpty=function(blur){if(base.$field.val()===""){base.prepForShow();base.setOpacity(blur?1.0:base.options.fadeOpacity);}else{base.setOpacity(0.0);}};base.prepForShow=function(e){if(!base.showing){base.$label.css({opacity:0.0}).show();base.$field.bind('keydown.infieldlabel',function(e){base.hideOnChange(e);});}};base.hideOnChange=function(e){if((e.keyCode===16)||(e.keyCode===9)){return;}
if(base.showing){base.$label.hide();base.showing=false;}
base.$field.unbind('keydown.infieldlabel');};base.init();};$.InFieldLabels.defaultOptions={fadeOpacity:0.5,fadeDuration:300};$.fn.inFieldLabels=function(options){return this.each(function(){var for_attr=$(this).attr('for'),$field;if(!for_attr){return;}
$field=$("input#"+for_attr+"[type='text'],"+"input#"+for_attr+"[type='search'],"+"input#"+for_attr+"[type='tel'],"+"input#"+for_attr+"[type='url'],"+"input#"+for_attr+"[type='email'],"+"input#"+for_attr+"[type='password'],"+"textarea#"+for_attr);if($field.length===0){return;}
(new $.InFieldLabels(this,$field[0],options));});};}(jQuery));

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
