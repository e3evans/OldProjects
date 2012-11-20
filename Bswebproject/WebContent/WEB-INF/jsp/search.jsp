<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/portlet" prefix="portlet"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@page import="java.util.Properties"%>
<%@page import="javax.portlet.PortletRequest"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.cisco.swtg.bss.util.BSSConstants"%>
<%@page import="javax.portlet.PortletSession"%>


<%@page import="java.net.URL"%>
<%@page import="java.io.InputStream"%><portlet:defineObjects />

<link rel="stylesheet" type="text/css" href="<c:out value='${requestPath}'/>/web/fw/tools/bss/xmproot/xwt/themes/masterbrand/xwt/notification/errorMessage.css" />
<%
	String bssBanner="";
	try{
		String ciscoLife = System.getProperty("cisco.life");
		String contextPath = "http://www-dev1.cisco.com";
		if(ciscoLife != null && ciscoLife.equals("prod")){
			contextPath = "http://www.cisco.com";
		}else if(ciscoLife != null && ciscoLife.equals("stage")){
			contextPath = "http://www-stage1.cisco.com";
		}else if(ciscoLife != null && ciscoLife.equals("lt")){
			contextPath = "http://www-stage1.cisco.com";
		}
		String propFile = contextPath + "/web/fw/tools/bss/custom/properties/bugSearchApp.properties";
		URL url = new URL(propFile);
		InputStream fis = url.openStream();
		Properties properties = new Properties();
		properties.load(fis);
		// getting file name from properties 
		PortletRequest pResquest = (PortletRequest)request;
		
		HashMap sessionBean=(HashMap)pResquest.getPortletSession().getAttribute(BSSConstants.USER_SESSION_BEAN,
							PortletSession.APPLICATION_SCOPE);
		Integer accessLevel=(Integer)sessionBean.get("ACCESS_LEVEL");	
		
		if(accessLevel.intValue()==1){
			bssBanner = properties.getProperty("bss.banner.guest");
		}else if(accessLevel.intValue() == 2){
			bssBanner = properties.getProperty("bss.banner.customer");
		}else if(accessLevel.intValue() == 3){
			bssBanner = properties.getProperty("bss.banner.partner");
		}else if(accessLevel.intValue() == 4){
			bssBanner = properties.getProperty("bss.banner.employee");
		}else if(accessLevel.intValue() == 5){
			bssBanner = properties.getProperty("bss.banner.admin");
		}
	}
	catch(Exception e){
	} 
 %>

<portlet:actionURL var="searchAction">
	<portlet:param name="action" value="keywordsSearchAction" />
	<portlet:param name="isResultPage" value="false" />
</portlet:actionURL>
<portlet:actionURL var="bugByIdAction">
	<portlet:param name="action" value="bugByIdAction" />
	<portlet:param name="isResultPage" value="false" />
</portlet:actionURL>
<portlet:actionURL var="multipleBugSearchAction">
	<portlet:param name="action" value="multipleBugSearchAction" />
	<portlet:param name="isResultPage" value="false" />
</portlet:actionURL>

<style type="text/css">
.masterbrand .xwtErrorMessage .xwtErrorMessageWrapper {
    font: 12px Arial,Helvetica !important;
}
.xwtErrorMessage .xwtErrorMessageText {
    color: #E9102A !important;
}
</style>

<script type="text/javascript">
       var errorMessage = "${message}";
       var guestUser = false;
       var userId = '${sessionScope.USER_SESSION_BEAN.USER}';
       
       dojo.provide("ComboBoxReadStore");
		dojo.declare("ComboBoxReadStore", dojox.data.QueryReadStore, {
			_filterResponse: function(data){
				if(data["suggestions"] != null){
		     		data["identifier"] = "name";
		     		data["items"] = data["suggestions"];
		     	}
		     	return data;
		     },
			fetch:function(request) {				
				var lookupKey = dojo.string.trim(autoSuggest.textbox.value);
				//enabling or disabling the search button based on entered length
				if(lookupKey.length > 0){
					searchButton.attr("disabled",false);
				}
				else{
					searchButton.attr("disabled",true);
				}
				//not allowing any autosuggest for guest user
				//& allowing service call from 3 char onwards
				if(guestUser || lookupKey.length < 3){	
					return;
				}
				else{
					//form custom url
					this.url = "<c:url value='/service/requestproxy/get'/>"+"/bss/searchterms/"+lookupKey;
				}
				//all the query parameters
				request.serverQuery = {st:"autocomplete"};
				//call QueryReadStore's fetch function				
				return this.inherited("fetch", arguments);
			}
		});
       
       dojo.addOnLoad(function() {
       		dojo.parser.parse();
       		var rf = "${rf}";
		/*	if(rf == "resultPage"){
				trackEvent.event('view',{'title':'BSS Home Page', 'sitearea':'bss','action':'pageLoad','rf':'http://www.cisco.com/cisco/psn/bssprt/bss/bssSummary','lc':'http://www.cisco.com/cisco/psn/bssprt/bss/bssHome', ntpagetag: {sensors:[ bss_ntpt_imgsrc ]}});
			}else if(rf == "bugDetailPage"){
				trackEvent.event('view',{'title':'BSS Home Page', 'sitearea':'bss','action':'pageLoad','rf':'http://www.cisco.com/cisco/psn/bssprt/bss/bssDetail','lc':'http://www.cisco.com/cisco/psn/bssprt/bss/bssHome', ntpagetag: {sensors:[ bss_ntpt_imgsrc ]}});
			}else{
				trackEvent.event('view',{'title':'BSS Home Page', 'sitearea':'bss','action':'pageLoad','lc':'http://www.cisco.com/cisco/psn/bssprt/bss/bssHome', ntpagetag: {sensors:[ bss_ntpt_imgsrc ]}});
			}*/
			
			<c:if test='${sessionScope.USER_SESSION_BEAN.USER_TYPE eq "GUEST"}'>
				autoSuggest.textbox.defaultValue = '<spring:message code="searchpage.text.examplebug" />';
				guestUser = true;
			</c:if>
			
			<c:if test='${sessionScope.USER_SESSION_BEAN.USER_TYPE != "GUEST"}'>
				autoSuggest.textbox.defaultValue = '<spring:message code="searchpage.text.examplesearch" />';
			</c:if>
			if(autoSuggest.textbox.value == ''){
				autoSuggest.textbox.value = autoSuggest.textbox.defaultValue;
				autoSuggest.textbox.style.color = '#CCCCCC';
			}
			if(autoSuggest.textbox.defaultValue != autoSuggest.textbox.value){
				autoSuggest.textbox.style.color = '#000000';
				searchButton.attr("disabled",false);
			}
			if(errorMessage != ""){
        		showError(errorMessage);	    
		    }
      });

function showError(errorMessage){
		dojo.query ( ".xwtErrorMessageText", xwtErrorMessage.domNode )[0].innerHTML = errorMessage;
       	dojo.removeClass("errorContainer", "errorHidden");
	    dojo.addClass("errorContainer","errorVisible");	
}

function bssClearText(field){
	if (field.defaultValue == dojo.string.trim(field.value)){
		field.value = '';
		field.style.color = '#000000';
	}else if (dojo.string.trim(field.value) == ''){
		field.value = field.defaultValue;
		field.style.color = '#CCCCCC';
	}
}

function checkForKeyPress(e){
   if(!dojo.isIE && dojo.string.trim(autoSuggest.attr('value')).length > 0 && dojo.keys.ENTER == e.charOrCode){
   		dojo.stopEvent(e);
   		submitSearchForm();
   		//setTimeout ('submitSearchForm()', 1000 );
   }
}

function BugbyIdURL(){

window.location = "'<spring:message code="contextURL"/>'?searchType=BugIdSearchURL&page=BugDetail";
}


function submitSearchForm(){
	<c:if test='${not empty errorList}'>
	dojo.addClass("errorListContainerId","errorHidden");
	</c:if>
	var errorFlag = false;
	errorMessage = "";
	var RE_BUGID = /^[cC][sS][cC]+[A-z]{2}[0-9]{5}(\s){0,}(,|;){0,}$/;
	var RE_BUGID_SEP_SPACE = /^((\s){0,}[cC][sS][cC]+[A-z]{2}[0-9]{5}(\s){0,}){2,}$/;
	var RE_BUGID_SEP_SPACE_ALPHANUM = /^((\s){0,}[cC][sS][cC]+[A-z]{2}[0-9]{5}(\s){0,}){1,}((\s){0,}[A-z0-9]{0,}(\s){0,}((\s){0,}[cC][sS][cC]+[A-z]{2}[0-9]{5}(\s){0,})(\s){0,}[A-z0-9]{0,}(\s){0,}){1,}$/;
	var RE_ALPHANUM_SEP_SPACE_BUGID = /^((\s){0,}[A-z0-9](\s){0,}){1,}((\s){0,}[A-z0-9]{0,}(\s){0,}((\s){0,}[cC][sS][cC]+[A-z]{2}[0-9]{5}(\s){0,})(\s){0,}[A-z0-9]{0,}(\s){0,}){1,}$/;
	var RE_MUL_BUGID_ALPHANUM = /^[cC][sS][cC]+[A-z]{2}[0-9]{5}(\s){0,}(,|;){1,}(\s){0,}((\s){0,}([cC][sS][cC]+[A-z]{2}[0-9]{5}|[A-z0-9(\s){0,}]{1,})(\s){0,}(,|;){1,}(\s){0,}){0,}(\s){0,}([cC][sS][cC]+[A-z]{2}[0-9]{5}|[A-z0-9(\s){0,}]{1,})(\s){0,}(,|;){0,}$/;
	var RE_MUL_ALPHANUM_BUGID_2 = /^((\s){0,}[A-z0-9(\s){0,}]{1,}(\s){0,}(,|;){1,}(\s){0,}){1,}((\s){0,}[cC][sS][cC]+[A-z]{2}[0-9]{5}(\s){0,}(,|;){0,}(\s){0,}){1,}$/;
	var RE_MUL_ALPHANUM_BUGID = /^((\s){0,}[A-z0-9(\s){0,}]{1,}(\s){0,}(,|;){1,}(\s){0,}){1,}((\s){0,}[cC][sS][cC]+[A-z]{2}[0-9]{5}(\s){0,}(,|;){1,}(\s){0,}){1,}((\s){0,}([cC][sS][cC]+[A-z]{2}[0-9]{5}|[A-z0-9(\s){0,}]{1,})(\s){0,}(,|;){1,}(\s){0,}){0,}(\s){0,}([cC][sS][cC]+[A-z]{2}[0-9]{5}|[A-z0-9(\s){0,}]{1,})(\s){0,}(,|;){0,}$/;
	
	var searchValue = dojo.string.trim(autoSuggest.attr('value'));
	if (searchValue.search(RE_BUGID) != -1) {
	
		document.searchForm.action = "http://swtg-rtp-dev-2.cisco.com:10040/wps/myportal/bss"
	}else if(guestUser && searchValue.search(RE_BUGID) == -1){
		errorMessage = '<spring:message code="searchpage.guestuser.notification" />';
		errorFlag = true;
	}else if(searchValue.search(RE_BUGID_SEP_SPACE) != -1){
		errorMessage = '<spring:message code="searchpage.commaorsemicolon" />';
		errorFlag = true;
	}else if(searchValue.search(RE_BUGID_SEP_SPACE_ALPHANUM) != -1 || searchValue.search(RE_ALPHANUM_SEP_SPACE_BUGID) != -1){
		errorMessage = '<spring:message code="searchpage.commaorsemicolon.bugidkeyword" />';
		errorFlag = true;
	}else if((searchValue.search(RE_MUL_BUGID_ALPHANUM) != -1 || searchValue.search(RE_MUL_ALPHANUM_BUGID) != -1 
				|| searchValue.search(RE_MUL_ALPHANUM_BUGID_2) != -1) && searchValue.replace(/;/g,',').split(",").length > 10){
		errorMessage = '<spring:message code="searchpage.multiplebugid.limit" />';
		errorFlag = true;
	}else if(searchValue.search(RE_MUL_BUGID_ALPHANUM) != -1 || searchValue.search(RE_MUL_ALPHANUM_BUGID) != -1 
				|| searchValue.search(RE_MUL_ALPHANUM_BUGID_2) != -1){
		document.searchForm.action = "${multipleBugSearchAction}";
	}
	if(errorFlag){
		showError(errorMessage);
		errorFlag = false;
		return;
	}
	document.searchForm.submit();
}
function showAdvSearchForm(){
		//hide basic search
		dojo.removeClass("searchContainer", "searchVisible");
        dojo.addClass("searchContainer","searchHidden");
        //hide error message if it is there
        dojo.removeClass("errorContainer", "errorVisible");
        dojo.addClass("errorContainer","errorHidden");
        //show advanced search
        dojo.removeClass("advSearchDiv", "advSearchHidden");
        dojo.addClass("advSearchDiv","advSearchVisible");
        populateAdvSearchFields();
}
function setHomePage(){
	if(guestUser){
		autoSuggest.textbox.value = autoSuggest.textbox.defaultValue;
	    autoSuggest.textbox.style.color = '#CCCCCC';
	    searchButton.attr("disabled",true);
	}else{
		showBasicSearch();
	}
}

//TODO
var home = '<a href="javascript:setHomePage();" ><spring:message code="searchpage.button.text.searchhome"/></a>';
</script>

<!-- Breadcrumb -->
<div id="breadcrumbContainer">
	<div id="breadcrumb" nested="false" dojoType="xwt.widget.layout.Breadcrumb" jsId="breadcrumb" class="xwtBreadcrumb"
		breadcrumbItems="{items:[{label:home},{label:''}]}"
		spacerString="&nbsp;&nbsp;">
	</div>
</div>

<!-- Banner -->

<%=bssBanner%>
		

<!-- Error -->
<div id="errorContainer" class="errorHidden">
 <div dojoType="xwt.widget.notification.ErrorMessage" jsId="xwtErrorMessage"></div>
 <c:if test='${not empty errorList}'>
	<div id="errorListContainerId" class="errorListContainer">
	<div style="height:23px;"></div>
		<c:forEach items="${errorList}" var="error">
			<div style="height:2px;"></div>
			${error}
		</c:forEach> 
	</div>
 </c:if> 	
</div>

<!-- Basic Search -->
<span dojoType="ComboBoxReadStore" jsId="autoSugStore" url="dummy" requestMethod="get"></span>
<div id="searchContainer" class="searchVisible">
	<form:form name="searchForm" commandName="search" method="get" action="${searchAction}" id="searchForm">
			<c:if test='${sessionScope.USER_SESSION_BEAN.USER_TYPE eq "GUEST"}'>
				<form:input id="searchTextField" jsId="autoSuggest"  
					dojoType="xwt.widget.form.ComboBox"
					path="keywords" size="180" maxlength="120"
					style="float:left;width:410px;padding: 0px !important;align:right;color:#CCCCCC;" 
					hasDownArrow='false'
					store="autoSugStore" onkeypress="checkForKeyPress"
					searchAttr="name" queryExpr="*" 
					onfocus="bssClearText(this.textbox)" onblur="bssClearText(this.textbox)"/>
			</c:if>
			<c:if test='${sessionScope.USER_SESSION_BEAN.USER_TYPE != "GUEST"}'>
				<form:input id="searchTextField" jsId="autoSuggest" 
					dojoType="xwt.widget.form.ComboBox" 
					path="keywords" size="180" maxlength="120"
					style="float:left;width:410px;padding: 0px !important;align:right;color:#CCCCCC;" 
					hasDownArrow='false'
					store="autoSugStore" onkeypress="checkForKeyPress"
					searchAttr="name" queryExpr="*"
					onfocus="bssClearText(this.textbox)" onblur="bssClearText(this.textbox)"/>
			</c:if>
			<input id="searchButton" jsId="searchButton" 
					dojoType="xwt.widget.form.TextButton"
					disabled="true" 
					style="float:left;margin:3px 0 3px 5px;align:right;"  
					onclick="submitSearchForm();" 
					<c:if test='${sessionScope.USER_SESSION_BEAN.USER_TYPE eq "GUEST"}'>
						label='<spring:message code="searchpage.button.searchabug" />'
					</c:if>
					<c:if test='${sessionScope.USER_SESSION_BEAN.USER_TYPE != "GUEST"}'>
						label='<spring:message code="searchpage.button.text.search" />'
					</c:if>	/>
			<c:if test='${sessionScope.USER_SESSION_BEAN.USER_TYPE != "GUEST"}'>	
				<a id="advSearchLink" jsId="advSearchLink" 
					style="float:left;margin:5px 0 5px 20px;align:right;" 
					href="javascript:showAdvSearchForm();"> <spring:message code="advsearchpage.title.advancedsearch" /></a>
			</c:if><br>
	</form:form>
</div>
<c:if test='${sessionScope.USER_SESSION_BEAN.USER_TYPE != "GUEST"}'>	
	<div id="advSearchDiv" class="advSearchHidden">
		<jsp:include page='advancedSearch.jsp' flush="true" />
	</div>
</c:if>