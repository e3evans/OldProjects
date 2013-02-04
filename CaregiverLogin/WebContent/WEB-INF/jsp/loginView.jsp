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
				<a title="Forgot your username?" href="">Forgot your username?</a>
			</li>
			<li>
				<a title="Forgot your password?" href="">Forgot your password?</a>
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
			<p><strong>NOTE:</strong> Please login to read full articles and gain access to all material</p>
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
$("#acgc_login_form input").keypress(function(event) {
    if (event.which == 13) {
        event.preventDefault();
        $("#acgc_login_form").submit();
    }
});
</script>

<% 
/*
GET AND CLOSE THE WCM WORKSPACE WHEN WE ARE DONE WITH IT.
*/
InitialContext ctx = new InitialContext();
WebContentService wcs = (WebContentService)ctx.lookup("portal:service/wcm/WebContentService");
Repository repo = wcs.getRepository();
repo.endWorkspace();

%>
