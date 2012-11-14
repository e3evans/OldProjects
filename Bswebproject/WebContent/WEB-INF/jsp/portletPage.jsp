<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/portlet" prefix="portlet"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@page import="com.cisco.swtg.bss.util.BSSConstants"%>
<%@ taglib uri="/WEB-INF/tld/portal.tld" prefix="portal" %> 
<%@ taglib uri="/WEB-INF/tld/engine.tld" prefix="wps" %>

<wps:captureContent contextKey="MyBugWatchUrl">  
	<portal:urlGeneration contentNode='MyBugWatch' > 
			<% wpsURL.write(out);%>
	</portal:urlGeneration>  
</wps:captureContent>
<%String myBugWatchPortletUrl = (String)pageContext.getAttribute("MyBugWatchUrl");%>

<portlet:defineObjects />
<jsp:include page='scriptinclude.jsp' flush="true" />

<jsp:include page='feedback.jsp' flush="true" />
<script type="text/javascript">
	dojo.addOnLoad(function() {
		dojo.parser.parse();
	    //dojo.parser.parse("mbw_tabId");
		if(dojo.isIE == 7){
			dijit.byId("iePopup").show();
		}
		//dojo.style("mainContainer","width",(screen.width-120)+"px");
		//dojo.style("tabContaninerSearchId_tablist","left",(screen.width-1020)/2 +"px");
	});
	
	var bw_homeBread = '<a href="http://www.cisco.com"><font size="3" color="black"><spring:message code="portalpage.home" /></font></a>';
	var bw_supportBread = '<a href="http://www.cisco.com/cisco/web/support/index.html"><font size="3" color="black"><spring:message code="portalpage.support" /></font></a>';
	var bw_toolsBread = '<a href="http://www.cisco.com/en/US/support/tsd_most_requested_tools.html"><font size="3" color="black"><spring:message code="portalpage.toolsresources" /></font></a>';

	function popUpFeedback(){
		resetSubmitForm();
		dijit.byId('feedbackDialog').show();
		dojo.style('feedbackDialog','display','inline-block');
	}	
	function onMouseOverHandler(id){
		dojo.addClass(id,"classHover");
	}
	function onMouseOutHandler(id){
		dojo.removeClass(id,"classHover");
	}
	function mouseClickHandler(){
		window.location='<%=(myBugWatchPortletUrl.trim())%>';
	}
</script>

<div id="mainContainer" class="masterbrand">
	<div class="center">
		<div class="breadTop">
			<div id="breadTop" dojoType="xwt.widget.layout.Breadcrumb" 
				breadcrumbItems="{items:[{label:bw_homeBread},{label:bw_supportBread},{label:bw_toolsBread}]}"
				spacerString="&nbsp;&nbsp;">
			</div>
		</div>
		<div class="bssTitle"><spring:message code="bss.title" /></div>
		<div class="helpFeedback">
	       <span style="padding:0 4px;">
	       		<a style="float:left;" href="http://www.cisco.com/web/applicat/cbsshelp/help.html" target="_blank">
	                 <spring:message code="bss.help" />
	       		</a>
	       		<div style="float:left;" class="separatorIconImage"></div>
	       		<a href="javascript:popUpFeedback()" style="float:left;">
	       			<spring:message code="bss.feedback" />
	       		</a>
	       </span>
		</div>
	</div>
	
	<div style="height:30px;border-bottom:1px solid #C2C2C2;margin-top:27px;">
		<div style="float:left;height:100%"
			class="classSelected">
			<div style="margin:6px 10px 4px;"><spring:message code="portalpage.tab.title.searchbugs" /></div>
		</div>
		<c:if test="${sessionScope.USER_SESSION_BEAN.USER_TYPE != 'GUEST' && pid == null}" >
		<div style="float:left;height:100%"
			class="classUnselected" 
			onMouseOver="javascript:onMouseOverHandler(this)"
			onMouseOut="javascript:onMouseOutHandler(this)"
			onClick="mouseClickHandler()">
			<div style="margin:6px 10px 4px;"><spring:message code="portalpage.tab.title.mybugwatch" /></div>	
		</div>
		</c:if>
	</div>
	
	
			<div id="bss_pageId" class="center">
				<jsp:include page='<%=renderRequest.getAttribute(BSSConstants.PORTLET_VIEW)	+ ".jsp"%>' flush="true" />
				<jsp:include page='relatedLink.jsp' flush="true" /> 
			</div>
	<div dojoType="dijit.Dialog" id="iePopup" title="<spring:message code="portalpage.iepopup.title" />" style="outline:none !important;width:490px;display:none;" class="masterbrand">
		<div style="margin-top:20px;">
			<div class="deleteNotificationIcon"></div>
			<div style="display:inline-block;font-size:13px;vertical-align:top;margin: 0px 10px 15px 40px;">
				<div><spring:message code="portalpage.iepopup.ie7message"/></div>
				<div><spring:message code="portalpage.iepopup.ie8message"/></div><br/>
			</div>
		</div>
		<div class="dijitDialogPaneActionBar" style="margin:10px 0 0 5px;">
			<button id="iePopupOkButtonId" onClick="dijit.byId('iePopup').hide();" dojoType="xwt.widget.form.TextButton" style="margin:0 4px;"><spring:message code="portalpage.iepopup.button.ok"/></button>
			<button id="iePopupCancelButtonId" onClick="dijit.byId('iePopup').hide();" dojoType="xwt.widget.form.TextButton"><spring:message code="portalpage.iepopup.button.cancel"/></button>
		</div>				
	</div>
</div>