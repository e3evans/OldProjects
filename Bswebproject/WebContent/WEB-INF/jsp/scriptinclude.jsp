<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<head>
<META HTTP-EQUIV="CACHE-CONTROL" content="NO-CACHE"/>
<META HTTP-EQUIV="PRAGMA" content="NO-CACHE"/>
<META HTTP-EQUIV="X-UA-Compatible" content="IE=8"/>

<%
	String bss_ciscoLife = System.getProperty("cisco.life");
	if(bss_ciscoLife == null){
		pageContext.setAttribute("requestPath", request.getContextPath(), PageContext.REQUEST_SCOPE);
	}
%>

<script type="text/javascript">
    	djConfig = {
                isDebug: false,
                debugAtAllCosts: false,
                parseOnLoad: true,
                dojoBlankHtmlUrl:"<c:out value='${requestPath}'/>/web/fw/tools/bss/xmproot/dojo/resources/blank.html"
       };
</script>

<script type="text/javascript" src="<c:out value='${requestPath}'/>/web/fw/tools/bss/custom/bssLayer/dojo.js"></script>
<script type="text/javascript" src="<c:out value='${requestPath}'/>/web/fw/tools/bss/custom/bssLayer/bssLayer.js"></script>
<script type="text/javascript" src="<c:out value='${requestPath}'/>/web/fw/tools/bss/custom/js/localizedErrorMessageFramework.js"></script>
<script type="text/javascript" src="<c:out value='${requestPath}'/>/web/fw/tools/bss/custom/dojo/fx.js"></script>


<script type="text/javascript">
 	var bss_ntpt_imgsrc = "";
 	var bss_ln = "<%= request.getLocale().getLanguage() %>";
 	var bss_ciscoLife = "<%= System.getProperty("cisco.life") %>";
 	console.log("Cisco Life : " + bss_ciscoLife);
 	var errorMessageFramework = "";
 	if(bss_ciscoLife == "null"){
 		errorMessageFramework = new ErrorMessage("<c:url value="/web/fw/tools/bss/custom/js/"/>", bss_ln);
 		console.log("Local Environment");
 	}else{
 		errorMessageFramework = new ErrorMessage("/web/fw/tools/bss/custom/js/", bss_ln);
 		cdc.ut.config.set("send", {dop:true, s_code:true, ntpagetag:true});
 		if(bss_ciscoLife != "prod"){
 			cdc.ut.config.set({rs_map_src: "/web/fw/tools/bss/metrics/rs_map.js"});
 		}
 		if(bss_ciscoLife == "dev"){
 			bss_ntpt_imgsrc = '<spring:message code="ntpagetag.dev" />';
 		}else if(bss_ciscoLife == "stage"){
 			bss_ntpt_imgsrc = '<spring:message code="ntpagetag.stage" />';
 		} else if(bss_ciscoLife == "lt"){
 			bss_ntpt_imgsrc = '<spring:message code="ntpagetag.lt" />';
 		}else if(bss_ciscoLife == "prod"){
 			bss_ntpt_imgsrc = '<spring:message code="ntpagetag.prod" />';
 		}
		try{
    		if(cdc.util.isAuthenticated()){
        		if(bss_ciscoLife == "dev"){
 					bss_ntpt_imgsrc = '<spring:message code="ntpagetag.dev.auth" />';
 				}else if(bss_ciscoLife == "stage"){
 					bss_ntpt_imgsrc = '<spring:message code="ntpagetag.stage.auth" />';
 				} else if(bss_ciscoLife == "lt"){
 					bss_ntpt_imgsrc = '<spring:message code="ntpagetag.lt.auth" />';
 				}else if(bss_ciscoLife == "prod"){
 					bss_ntpt_imgsrc = '<spring:message code="ntpagetag.prod.auth" />';
 				}
    		}
		}catch(e){}
 		console.log("Tagging configuration done in Environment : " + bss_ciscoLife);
 	}
 	
</script>

</head>

<link rel="stylesheet" type="text/css" href="<c:out value='${requestPath}'/>/web/fw/tools/bss/custom/css/bss.css" />
<link rel="stylesheet" type="text/css" href="<c:out value='${requestPath}'/>/web/fw/tools/bss/custom/css/tabContainer.css" />
<link rel="stylesheet" type="text/css" href="<c:out value='${requestPath}'/>/web/fw/tools/bss/xmproot/dojo/resources/dojo.css" />
<link rel="stylesheet" type="text/css" href="<c:out value='${requestPath}'/>/web/fw/tools/bss/xmproot/dijit/themes/dijit.css" />
<link rel="stylesheet" type="text/css" href="<c:out value='${requestPath}'/>/web/fw/tools/bss/xmproot/xwt/themes/masterbrand/Common.css" />

<link rel="stylesheet" type="text/css" href="<c:out value='${requestPath}'/>/web/fw/tools/bss/xmproot/xwt/themes/masterbrand/form/Button.css" />
<link rel="stylesheet" type="text/css" href="<c:out value='${requestPath}'/>/web/fw/tools/bss/xmproot/xwt/themes/masterbrand/xwt/layout/breadcrumb.css" />
<link rel="stylesheet" type="text/css" href="<c:out value='${requestPath}'/>/web/fw/tools/bss/xmproot/xwt/themes/masterbrand/xwt/layout/titlepane.css" />
<link rel="stylesheet" type="text/css" href="<c:out value='${requestPath}'/>/web/fw/tools/bss/xmproot/xwt/themes/masterbrand/xwt/layout/dashlet.css" />

<link rel="stylesheet" type="text/css" href="<c:out value='${requestPath}'/>/web/fw/tools/bss/xmproot/xwt/themes/masterbrand/xwt/form/combobox.css" />
<link rel="stylesheet" type="text/css" href="<c:out value='${requestPath}'/>/web/fw/tools/bss/xmproot/xwt/themes/masterbrand/xwt/form/dropdown.css" />
<link rel="stylesheet" type="text/css" href="<c:out value='${requestPath}'/>/web/fw/tools/bss/xmproot/xwt/themes/masterbrand/xwt/form/textbutton.css" />
<link rel="stylesheet" type="text/css" href="<c:out value='${requestPath}'/>/web/fw/tools/bss/xmproot/xwt/themes/masterbrand/xwt/form/dropdown.css" />
<link rel="stylesheet" type="text/css" href="<c:out value='${requestPath}'/>/web/fw/tools/bss/xmproot/xwt/themes/masterbrand/xwt/table/Table.css" />
<link rel="stylesheet" type="text/css" href="<c:out value='${requestPath}'/>/web/fw/tools/bss/xmproot/xwt/themes/masterbrand/xwt/table/Toolbar.css" />
<link rel="stylesheet" type="text/css" href="<c:out value='${requestPath}'/>/web/fw/tools/bss/xmproot/xwt/themes/masterbrand/Dialog.css" />
<link rel="stylesheet" type="text/css" href="<c:out value='${requestPath}'/>/web/fw/tools/bss/xmproot/xwt/themes/masterbrand/form/RadioButton.css" />
<link rel="stylesheet" type="text/css" href="<c:out value='${requestPath}'/>/web/fw/tools/bss/xmproot/xwt/themes/masterbrand/form/Checkbox.css" />