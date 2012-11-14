<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<div>
	<div dojoType="xwt.widget.layout.Dashlet" title="Related Links" id="relatedLinksDashlet" requireCollapse="false">
		<div id="relatedLinksDashletBody">		
			<div id="relLinkRowOne" >
				<div class="relLinkColOne">
	                    <a style="text-align:left;" href="http://www.cisco.com/cisco/software/navigator.html?a=a&i=rpm" target="_blank">
		                      <spring:message code="portletpage.footer.downloadsoftware" />
	                    </a>
	               </div>
	               <div class="relLinkColTwo">
	                    <a href="http://tools.cisco.com/ServiceRequestTool/create" target="_blank">
	                      <spring:message code="portletpage.footer.tacservicerequesttool" />
	                    </a>
	               </div>
	               <div class="relLinkColOne">
	                   <a style="float:right;" href="http://www.cisco.com/web/tsweb/searchplugins/plugin_homepage.html" target="_blank">
	                     <spring:message code="portletpage.footer.bugidbrowserplugin" /> 
	                </a>
	            </div>
			</div>
			<div id="relLinkRowTwo">
				<div class="relLinkColOne">
	                 <a style="text-align:left;" href="https://supportforums.cisco.com/community/netpro/idea-center/btk" target="_blank">
	                   	<spring:message code="portletpage.footer.ciscosupportcommunity" />
	                 </a>
	        	</div>
			</div>
		</div> 
	</div>
</div>