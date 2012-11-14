<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<style type="text/css">
.dijitDialogPaneContentArea {
	border-bottom: 1px solid #C2C2C2;
    margin: 0px;
    padding-bottom: 20px;
    padding-left:15px;
}

.emailNotificationArea {
	margin: 20px 15px;
}

.searchNameInputBox {
	width: 240px;
	height: 18px !important;
	margin: 7px 28px 3px !important;
}

.saveGroupListBox {
	width: 202px;
	margin: 5px 25px 0;
}

.sendEmailLabel {
	margin-top: 5px;
	display: inline-block;
	width: 140px;
	height: 18px !important;
}

.emailTextBox {
	width: 202px;
	height: 21px;
	display: inline-block;
}

.schdulerDiv {
	margin: 8px 0 0;
	height: 22px;
}

.groupNameDiv {
	display: none;
	padding-top: 8px;
}

.groupNameTextBox {
	width: 240px;
	height: 18px !important;
	margin: 7px 33px 10px !important;
}

#saveDialogMessageContainer {
	padding-bottom: 25px;
	height: 20px;
}

.masterbrand .xwtErrorMessage .xwtErrorMessageWrapper {
	font: 12px Arial, Helvetica !important;
}

.xwtErrorMessage .xwtErrorMessageText {
	color: #E9102A !important;
	margin-top: -11px !important;
}

.xwtErrorMessage {
	margin-left: 0px !important;
}

.xwtErrorMessage .xwtErrorMessageWrapper {
	padding-top: 15px !important;
}
.dijitSelectMenu{
	max-height: 150px !important;
	overflow-y:auto !important;
	overflow-x:hidden !important;
	padding:1px;
}
</style>

<script type="text/javascript">
	var isNewGroup = false;
	var notificationFlag = false;
	var showErrorFlag = false;
	var groupNameList = {identifier:"itemId", label: "itemName", items: []};
	dojo.addOnLoad(function(){
		getGroupDetails();
	});
	
	function enableEmailFields(id){
		dijit.byId('emailAddress'+id).attr('disabled',false);
		dijit.byId('schedule'+id).attr('disabled',false);
		notificationFlag = true;
	}
	function disableEmailFields(id){
		dijit.byId('emailAddress'+id).attr('disabled',true);
		dijit.byId('schedule'+id).attr('disabled',true);
		notificationFlag = false;
	}
	function sortByItemName(obj1,obj2) {
      var group1 = obj1.itemName.toLowerCase();
	  var group2 = obj2.itemName.toLowerCase();
	  return (group1 == group2) ? 0 : ((group1 > group2) ? 1 : -1 );
 	}
	function getGroupDetails(){
		groupNameList = {identifier:"itemId", label: "itemName", items: []};
		var groupDetailUri = {
					url: "<c:url value='/service/requestproxy/get'/>"+"/bss/bugwatchsummary",
					preventCache:true,
					handleAs:"json"
		}
		var groupDetailXhr = dojo.xhrGet(groupDetailUri);
		groupDetailXhr.addCallback(function(_jsonObject) {
			var isDefaultGroup = true;
			dojo.forEach(_jsonObject.viewBugWatchData, function(viewBugWatchJsonObject, i){
				var groupDetailJson = {itemName:viewBugWatchJsonObject.groupName, email: viewBugWatchJsonObject.notificationEmail,
											 schedule: viewBugWatchJsonObject.notificationFrequency, itemId: viewBugWatchJsonObject.groupName};
				if(viewBugWatchJsonObject.groupName == "${sessionScope.USER_SESSION_BEAN.USER}"){
					isDefaultGroup = false;
					groupDetailJson = {itemName:viewBugWatchJsonObject.groupName + ' (default)', email: viewBugWatchJsonObject.notificationEmail,
											 schedule: viewBugWatchJsonObject.notificationFrequency, itemId: viewBugWatchJsonObject.groupName};
				}
				groupNameList.items.push(groupDetailJson);
			});
			if(_jsonObject.viewBugWatchData.length == 0 || isDefaultGroup){
				var groupDetailJsonDefault = {itemName:'${sessionScope.USER_SESSION_BEAN.USER} (default)', email: '${sessionScope.USER_SESSION_BEAN.emailId}',
												 schedule: '0', itemId: '${sessionScope.USER_SESSION_BEAN.USER}'};
				groupNameList.items.push(groupDetailJsonDefault);
			}
			groupNameList.items.sort(sortByItemName);
			groupNameList.items.push({itemName:'<spring:message code="savedialog.label.createnewgroup" />', itemId:'dummy@123'});
			savedGroup.store = new dojo.data.ItemFileReadStore({data: groupNameList});
			savedGroup.startup();
		});
		
	}
	function generateRequestParamSaveBug(){
		var requestParam = "requestType=B&bugId=${bugDetail.bugId}&groupName=";
		if(isNewGroup){
			requestParam += dijit.byId("editGroupName_ng").value;
		}else{
			var defaultGroupName = savedGroup._getDisplayedValueAttr();
			if(savedGroup._getDisplayedValueAttr() == "${sessionScope.USER_SESSION_BEAN.USER} (default)"){
				defaultGroupName = "${sessionScope.USER_SESSION_BEAN.USER}";
			}
			requestParam += defaultGroupName;
		}
		if(notificationFlag){ 
			requestParam =	requestParam + "&notifeq=" + dijit.byId("schedule_ng").value 
									+ "&email=" + dijit.byId("emailAddress_ng").textbox.value;
		}else{
			requestParam =	requestParam + "&notifeq=0";
		}
		return requestParam;
	}
	
	function generateRequestParamSaveSearch(){
		var requestParam = "requestType=S&searchName=" + dijit.byId("searchNameId").value + "&groupName=";
		if(isNewGroup){
			requestParam += dijit.byId("editGroupName_ng").value;
		}else{
			var defaultGroupName = savedGroup._getDisplayedValueAttr();
			if(savedGroup._getDisplayedValueAttr() == "${sessionScope.USER_SESSION_BEAN.USER} (default)"){
				defaultGroupName = "${sessionScope.USER_SESSION_BEAN.USER}";
			}
			requestParam += defaultGroupName;
		}
		if(notificationFlag){ 
				requestParam =	requestParam + "&notifeq=" + dijit.byId("schedule_ng").value 
									+ "&email=" + dojo.string.trim(dijit.byId("emailAddress_ng").textbox.value);
		}else if(!disableEmailNotification){
			requestParam =	requestParam + "&notifeq=0";
		}
		<c:if test='${bssSearchType == "kw"}'>
			requestParam = requestParam + "&keywordSearch=${searchCriteria}&searchType=kw";
		</c:if>
		<c:if test='${bssSearchType == "adv"}'>
			requestParam = requestParam 
				+ "&mdfProductConceptId=${searchObject.product}&mdfProductName=${searchObject.productName}&searchType=adv"
				+ "&ngrpVersionName=${searchObject.softwareVersion}&versionType=${searchObject.versionType}&mdfCategoryId=${searchObject.productCategory}";
		</c:if>
		<c:if test='${bssSearchType == "bugId"}'>
			requestParam = requestParam + "&keywordSearch=${searchCriteria}&searchType=bugId";
		</c:if>
		
		if(saveSearchParam != ""){
			requestParam += saveSearchParam.replace(/&amp;/g, "%26");
		}
		return requestParam;
	}
	
	function checkForNewGroup(){
		dijit.byId("editGroupName_ng").textbox.value = '';
		document.getElementById("emailNotificationDiv_ng").style.display = "block";
		var groupName = savedGroup.getValue();
		if(groupName == 'dummy@123'){
			document.getElementById("groupNameDivId").style.display = "block";
			saveButtonId.attr("disabled",true);
			dijit.byId("noemail_ng").attr("checked",true);
			disableEmailFields('_ng');
			dijit.byId("emailAddress_ng").attr("value",'${sessionScope.USER_SESSION_BEAN.emailId}');
			dijit.byId("schedule_ng").attr("value","1");
			isNewGroup = true;
		}else{
			document.getElementById("groupNameDivId").style.display = "none";
			if(isSaveSearch && dojo.string.trim(dijit.byId("searchNameId").textbox.value) == ''){
				saveButtonId.attr("disabled",true);
			}else{
				saveButtonId.attr("disabled",false);
			}
			setNotificationDetails(groupName);
			dojo.removeClass("saveDialogMessageContainer", "errorVisible");
			dojo.addClass("saveDialogMessageContainer", "errorHidden");
			isNewGroup = false;
		}
	}
	function setNotificationDetails(groupNameValue){
		dojo.forEach(groupNameList.items, function(groupName, i){
			if(groupName.itemId == groupNameValue){
				if(groupName.schedule && groupName.schedule != "0"){
					if(!disableEmailNotification){
						dijit.byId("yemail_ng").attr("checked",true);
						enableEmailFields('_ng')
					}	
					dijit.byId("emailAddress_ng").attr("value",groupName.email);
					dijit.byId("schedule_ng").attr("value",groupName.schedule);
				}else{
					dijit.byId("noemail_ng").attr("checked",true);
					disableEmailFields('_ng');
					dijit.byId("emailAddress_ng").attr("value",'${sessionScope.USER_SESSION_BEAN.emailId}');
					dijit.byId("schedule_ng").attr("value","1");
				}
			}
		});
	}
	function showSaveSearchDialog(){
		resetSearchDialog();
		if(disableEmailNotification){
			dijit.byId("noemail_ng").attr("checked",true);
			disableEmailFields('_ng');
		}
		dijit.byId("saveSearchDivId").show();
	}
	function showSaveBug(){
		resetSearchDialog();
		dijit.byId("saveBug").show();
		dojo.style("saveBug","display","inline-block");
	}
	
	function resetSearchDialog(){
		document.getElementById("groupNameDivId").style.display = "none";
		dijit.byId("editGroupName_ng")._setValueAttr('');
		if(isSaveSearch){
			dijit.byId("searchNameId")._setValueAttr('');
			saveButtonId.attr("disabled",true);
		}
		dojo.removeClass("saveDialogMessageContainer", "errorVisible");
		dojo.addClass("saveDialogMessageContainer", "errorHidden");
		savedGroup._setValueAttr('${sessionScope.USER_SESSION_BEAN.USER}');
		savedGroup._setDisplay('${sessionScope.USER_SESSION_BEAN.USER} (default)');
		setNotificationDetails('${sessionScope.USER_SESSION_BEAN.USER}');
		isNewGroup = false;
	}
	
	function checkGroupNameInput(){
		if(isSaveSearch){
			if(dojo.string.trim(dijit.byId("editGroupName_ng").textbox.value) == '' || dojo.string.trim(dijit.byId("searchNameId").textbox.value) == ''){
				saveButtonId.attr("disabled",true);
			}else{
				saveButtonId.attr("disabled",false);
			}
		}else{
			if(isNewGroup && dojo.string.trim(dijit.byId("editGroupName_ng").textbox.value) == ''){
				saveButtonId.attr("disabled",true);
			}else{
				saveButtonId.attr("disabled",false);
			}
		}
	}
	
	function checkSearchNameInput(){
		if(isNewGroup && (dojo.string.trim(dijit.byId("searchNameId").textbox.value) == '' || dojo.string.trim(dijit.byId("editGroupName_ng").textbox.value) == '')){
			saveButtonId.attr("disabled",true);
		}else if(dojo.string.trim(dijit.byId("searchNameId").textbox.value) == ''){
			saveButtonId.attr("disabled",true);
		}else{
			saveButtonId.attr("disabled",false);
		}
	}
	function showSaveError(errorMessage){
		showErrorFlag = true;
		dojo.query ( ".xwtErrorMessageText", xwtErrorMessage_saveDialog.domNode )[0].innerHTML = errorMessage;
       	//show error
       	dojo.removeClass("saveDialogMessageContainer", "errorHidden");
	    dojo.addClass("saveDialogMessageContainer","errorVisible");	
	}
	
	function isSpecialChar(strToValidate){
	   var specialChars = "!@#$%^&*()+=[]\\\';,./{}|\":<>?";
	   for(var i = 0; i < strToValidate.length; i++){
		  if(specialChars.indexOf(strToValidate.charAt(i)) != -1){
		    	return true;
		  	}
		 }
		return false;
	}
	function emailValidation(email){
	   var REG_EMAIL = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
	   if(email.search(REG_EMAIL) == -1) {
	      return true;
	   }else{
		   return false;
		}
	}
	
	function validateNameFields(){
		var nameError = false;
		//validate existing group name
		dojo.forEach(groupNameList.items, function(groupName, i){
			if(groupName.itemId == dijit.byId("editGroupName_ng").attr('value')){
				nameError = true;
				showSaveError('<spring:message code="savedialog.duplicategroup.message" />');
			}
		});
		if(isSpecialChar(dijit.byId("editGroupName_ng").attr('value'))){
			nameError = true;
			showSaveError("<spring:message code="savedialog.group.containsspecialchars"/>");
		}
		//validate email format
		if(dijit.byId("yemail_ng").attr('checked') && emailValidation(dojo.string.trim(dijit.byId("emailAddress_ng").attr('value')))){			
			showSaveError('<spring:message code="savedialog.invalidemail.message"/>');
			isError = true;
		}
		return nameError;
	}
	
	function submitSaveBug(){
		var isSaveError = false;
		showErrorFlag = false;
		isSaveError = validateNameFields();
		if(isSaveError || showErrorFlag){
			return;
		}else{
			var saveBug_requestParam = generateRequestParamSaveBug();
			var saveBugUri = {
						url: "<c:url value='/service/requestproxy/put'/>"+"/bss/bugwatch?" + saveBug_requestParam,
						preventCache:true,
						handleAs:"json"
			}
			var saveBugXhr = dojo.xhrGet(saveBugUri);
			saveBugXhr.addCallback(function(_jsonObject) {
				var saveResponse = _jsonObject.saveBugWatchResponse.bugWatchData.bugIdList[0];
				if(saveResponse.responseCode == "422"){
					showSaveError(saveResponse.responseMessage);
					return;
				} 
				getGroupDetails();
				dijit.byId("saveBug").hide();
				var groupName = savedGroup._getDisplayedValueAttr();
				if(isNewGroup){
					groupName = dijit.byId("editGroupName_ng").value;
				}
				dojo.byId("saveBugSuccessMsg").innerHTML = "<spring:message	code="savedialog.bug.save.msg1" />" + groupName + "<spring:message code="savedialog.bug.save.msg2" />";
				dijit.byId("saveBugSuccessDialogId").show();
			});
		}
	}
	
	function submitSaveSearch(){
		var isSaveError = false;
		showErrorFlag = false;
		isSaveError = validateNameFields();
		if(isSpecialChar(dijit.byId("searchNameId").attr('value'))){
			isSaveError = true;
			showSaveError("<spring:message code="savedialog.search.containsspecialchars"/>");
		}
		if(isSaveError || showErrorFlag){
			return;
		}else{
			var saveSearch_requestParam = generateRequestParamSaveSearch();
			var saveBugUri = {
						url: "<c:url value='/service/requestproxy/put'/>"+"/bss/bugwatch?" + saveSearch_requestParam,
						preventCache:true,
						handleAs:"json"
			}
			var saveBugXhr = dojo.xhrGet(saveBugUri);
			saveBugXhr.addCallback(function(_jsonObject) {
				var saveErrorResponse = _jsonObject.saveBugWatchResponse.errorResponse;
				if(saveErrorResponse != undefined){
					showSaveError(saveErrorResponse.publicErrorMessage);
					return;
				} 
				getGroupDetails();
				dijit.byId("saveSearchDivId").hide();
				var groupName = savedGroup._getDisplayedValueAttr();
				if(isNewGroup){
					groupName = dijit.byId("editGroupName_ng").value;
				}
				dojo.byId("saveSearchSuccessMsg").innerHTML = "<spring:message code="savedialog.search.save.msg1"/>" + dijit.byId("searchNameId").value + "<spring:message code="savedialog.search.save.msg2"/>" + groupName + "<spring:message code="savedialog.search.save.msg3"/>";
				dijit.byId("saveSearchSuccessDialogId").show();
			});
		}
	}
</script>
<!-- Error -->
<div id="saveDialogMessageContainer" class="errorHidden">
	<div dojoType="xwt.widget.notification.ErrorMessage"	jsId="xwtErrorMessage_saveDialog"></div>
</div>

<div class="dijitDialogPaneContentArea">
	<div id="searchNameDivId" style="display: none;">
		<span style="color: #ff9416">*</span>
		<span><spring:message code="savedialog.label.searchname" /></span>
		<input dojoType="dijit.form.TextBox" id="searchNameId" onkeyup="checkSearchNameInput();" class="searchNameInputBox"	maxlength="50"></input>
	</div>
	<div>
		<span style="color: #ff9416">*</span>
		<span><spring:message code="savedialog.label.selectagroup" /></span>
		<input dojoType="dijit.form.Select" sortByLabel="false"	onChange="checkForNewGroup();" style="width: 242px; margin: 8px 25px 0;"
			jsId="savedGroup" searchAttr="itemName"></input>
	</div>
	<div id="groupNameDivId" class="groupNameDiv">
		<span style="color: #ff9416">*</span> 
		<span><spring:message code="savedialog.label.groupname" /></span>
		<input dojoType="dijit.form.TextBox" id="editGroupName_ng" onkeyup="checkGroupNameInput();" class="groupNameTextBox" maxlength="50"></input></div>
	</div>
	<div id="emailNotificationDiv_ng" class="emailNotificationArea">
		<div id="emailNofificationId" class="emailNotification"	style="display: none;height:30px;">
		<div class="emailNotificationIcon" style="float:left"></div>
		<div style="float:left"><spring:message code="savedialog.notification.message" /></div>
	</div>
	<div style="margin: -3px 0 14px">
		<h4><spring:message code="savedialog.label.groupmailnotification" /></h4>
	</div>
	<div>
		<input checked="checked" onClick="disableEmailFields('_ng');" dojoType="dijit.form.RadioButton" name="email" id="noemail_ng"
			value="no"></input>
		 <label for="noemail"><spring:message code="savedialog.label.noemailupdates" /></label>
	</div>
	<div>
		<input onClick="enableEmailFields('_ng');" dojoType="dijit.form.RadioButton" id="yemail_ng" value="yemail" name="email"></input>
			<label class="sendEmailLabel" for="yemail"><spring:message code="savedialog.label.sendemailupdatesto" />:</label>
			<input disabled	dojoType="dijit.form.TextBox" id="emailAddress_ng" maxlength="50" class="emailTextBox" 
				name="emailAddress" style="width:240px"	value="${sessionScope.USER_SESSION_BEAN.emailId}"></input>
	</div>
	<div class="schdulerDiv"> 
		<div style="margin: 0 15px 0 94px; float: left;"><spring:message code="savedialog.label.schedule" />:</div>
		<div disabled dojoType="dijit.form.Select"	style="width: 242px; margin: 0px; float: left;" name="schedule"	id="schedule_ng">
			<span value="1" selected="selected"><spring:message	code="savedialog.label.daily" /></span>
			<span value="2"><spring:message	code="savedialog.label.weekly" /></span>
			<span value="3"><spring:message	code="savedialog.label.monthly" /></span>
		</div>
	</div>
</div>

<div dojoType="dijit.Dialog" id="saveBugSuccessDialogId" style="outline:none !important;height:100px;width:625px;display:none;" title="Save Bug Confirmation">
		<div class="showAlertImage" style="margin:0 10px;float:left;width:16px;"></div>
		<div id="saveBugSuccessMsg" style="float:left;margin:0;max-width:550px;"></div>
</div>
<div dojoType="dijit.Dialog" id="saveSearchSuccessDialogId" style="outline:none !important;height:100px;width:625px;display:none;" title="Save Search Confirmation">
		<div class="showAlertImage" style="margin:0 10px;float:left;width:16px;"></div>
		<div id="saveSearchSuccessMsg" style="float:left;margin:0;max-width:550px;"></div>
</div>