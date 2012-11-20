<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page import="com.cisco.swtg.bss.util.BSSConstants"%>

<%
	String ciscoLife = System.getProperty("cisco.life");
	String url="";
	if(ciscoLife == null){
		url = BSSConstants.DEV_FEEDBACK_URL;// TODO
	}else if(ciscoLife.equalsIgnoreCase("dev")){
		url = BSSConstants.DEV_FEEDBACK_URL;
	}else if(ciscoLife.equalsIgnoreCase("stage")){
		url = BSSConstants.STAGE_FEEDBACK_URL;
	}else if(ciscoLife.equalsIgnoreCase("lt")){
		url = BSSConstants.STAGE_FEEDBACK_URL;
	}else if(ciscoLife.equalsIgnoreCase("prod")){
		url = BSSConstants.PROD_FEEDBACK_URL;
	}
%>
<style type="text/css">
.buttonPane{
	display:none !important;
}
#feedbackMessageContainer .xwtErrorMessage  {
    margin-left: 0 !important;
}
</style>

<script type="text/javascript">
	/*
	function openReplyWindow(form){
		var url=form.appContext.value+"/search/getFeedbackdetails.do?method=fetchDefaultFeedbackDetails&search=default";
		window.open(url);
	}
	*/
    function openWindow() {
		window.name = "cco_main";
	    //var thankYou_url = "/web/applicat/cbsshelp/thankyou.html";
	    var thankYou_url = "http://www.cisco.com/web/applicat/cbsshelp/thankyou.html";
        var thankYou_win = "thanks";
	    var thankYou_params = 'scrollbars=yes,width=300,height=170,left=200,top=200';
	    window.open(thankYou_url,thankYou_win,thankYou_params);
	}
	function setChkContact(){
	  if(document.feedback.choice_7477.checked) { 
 		document.feedback.choice_7477.value='16242';
  	  }	else {
		document.feedback.choice_7477.value='16241';
	  }
	}
	
	function emailValidation(email){
	   var REG_EMAIL = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
	   if(email.search(REG_EMAIL) == -1) {
	      return true;
	   }else{
		   return false;
		}
	}
	
	function enableSubmit(){
		if(!dijit.byId("scale_711_1").getValue(null) || !dijit.byId("scale_711_2").getValue(null) 
			|| !dijit.byId("scale_711_3").getValue(null) || !dijit.byId("scale_711_4").getValue(null) 
			|| !dijit.byId("scale_711_5").getValue(null)){
			submitFeedback.attr("disabled",false);
		}
	}
   
    function resetSubmitForm(){
  		dijit.byId("scale_711_1").setValue(null);
  		dijit.byId("scale_711_2").setValue(null);
  		dijit.byId("scale_711_3").setValue(null);
  		dijit.byId("scale_711_4").setValue(null);
  		dijit.byId("scale_711_5").setValue(null);
  		document.getElementById("text_713_1").value = "";
  		dijit.byId("choice_7477_1").setValue(true);
  		dijit.byId("name_1").setValue("${sessionScope.USER_SESSION_BEAN.firstName} ${sessionScope.USER_SESSION_BEAN.lastName}");
  		dijit.byId("email_1").setValue("${sessionScope.USER_SESSION_BEAN.emailId}");
  		submitFeedback.attr("disabled",true);
  		dojo.style("feedbackMessageContainer","display","none");
    }
    
    function showFeedbackError(errorMessage){
		showErrorFlag = true;
		dojo.query ( ".xwtErrorMessageText", xwtErrorMessage_feedbackDialog.domNode )[0].innerHTML = errorMessage;
       	//show error
	    dojo.style("feedbackMessageContainer","display","inline-block");	
	    
	}
	
    function submitForm(){
    	if(dojo.string.trim(dijit.byId("email_1").attr('value')) != "" && emailValidation(dojo.string.trim(dijit.byId("email_1").attr('value')))){			
			showFeedbackError('<spring:message code="savedialog.invalidemail.message"/>');
		}else{
    		document.getElementById("feedbackId").submit();
    		openWindow();
    		dijit.byId("feedbackDialog").hide();
    	}
    }
   
</script>

<div id="feedbackDialog" style="padding:10px; display:none;" dojoType="xwt.widget.layout.Dialog" title="<spring:message code="feedback.dashlet.title.feedback" />" class="masterbrand">
	<div id="feedbackMessageContainer" style="margin:10px;display:none">
		<div dojoType="xwt.widget.notification.ErrorMessage" jsId="xwtErrorMessage_feedbackDialog"></div>
	</div>
	<form id="feedbackId" class="feedbackForm" action="<%= url%>" name="feedback" target="thanks" method="post">
		<input name="feedback_type"	value="1" dojoType="dijit.form.TextBox" type="hidden" />
		<input name="survey_id" value="220" dojoType="dijit.form.TextBox" type="hidden" />
		<input name="ioContentSource" value="BTK" dojoType="dijit.form.TextBox" type="hidden" />
		<input name="version" value="custom" dojoType="dijit.form.TextBox" type="hidden" />
		<input name="BugID" value="${bugDetail.bugId}" dojoType="dijit.form.TextBox" type="hidden" />
		<input name="doc_url" value="BUG SEARCH SERVICE" dojoType="dijit.form.TextBox" type="hidden" />
		<input name="redirect_url" value="/web/applicat/cbsshelp/thankyou.html" type="hidden" />
			<div style="margin:12px 0;" class="bodytext"><spring:message code="feedback.label.rateoverall" /></div>
			
			<div style="margin-bottom:12px;" id="scale_711" jsId="scale_711" >
				<span style="margin-right:8px;vertical-align:top" class="bodytext"><spring:message code="feedback.label.poor" /></span>
				<input type="radio" id="scale_711_1" dojoType="dijit.form.RadioButton" name="scale_711" value="1" onclick="enableSubmit();"/>
				<input type="radio" id="scale_711_2" dojoType="dijit.form.RadioButton" name="scale_711" value="2" onclick="enableSubmit();"/>
				<input type="radio" id="scale_711_3" dojoType="dijit.form.RadioButton" name="scale_711" value="3" onclick="enableSubmit();"/>
				<input type="radio"	id="scale_711_4" dojoType="dijit.form.RadioButton" name="scale_711" value="4" onclick="enableSubmit();"/>
				<input type="radio" id="scale_711_5" dojoType="dijit.form.RadioButton" name="scale_711" value="5" onclick="enableSubmit();"/>
				<span style="margin-left:8px;vertical-align:top" class="bodytext"><spring:message code="feedback.label.excellent" /></span>
			</div>

			<div style="margin-bottom:10px;"><spring:message code="feedback.label.comment" /></div>
			<textarea id="text_713_1" name="text_713" style="width:500px;height:50px;margin:0;margin-bottom:12px"></textarea>
			<input name="syzygy" value="108" dojoType="dijit.form.TextBox" type="hidden" />
			
			<div style="margin-bottom:12px">
				<input name="choice_7477" id="choice_7477_1" checked="checked" type="checkbox" dojoType="dijit.form.CheckBox" />
				<label for="contactMeCheckbox" class="labelCheckbox"><spring:message code="feedback.label.contactme" /></label>
			</div>
			<div style="margin-bottom:12px">
				<div class="bodytextbold" style="width:55px;display:inline-block"><spring:message code="feedback.label.fullname" /></div>
				<input name="name" id="name_1" dojoType="dijit.form.TextBox" maxlength="80" type="text" 
					value="${sessionScope.USER_SESSION_BEAN.firstName} ${sessionScope.USER_SESSION_BEAN.lastName}" />
			</div>
			<div style="margin-bottom:20px">
				<div class="bodytextbold" style="width:55px;display:inline-block"><spring:message code="feedback.label.mailid" /></div>
				<input name="email" id="email_1" dojoType="dijit.form.TextBox" type="text" maxlength="80" name="email" 
					value="${sessionScope.USER_SESSION_BEAN.emailId}" />
			</div>
			<div>
				<input dojoType="xwt.widget.form.TextButton" id="submitFeedback" jsId="submitFeedback" type="button" 
			 		disabled="true" value="send" name="send" onclick="setChkContact();javascript:submitForm();"
					label="<spring:message code="feedback.button.submit"/>" />
			</div>
	</form>
</div>