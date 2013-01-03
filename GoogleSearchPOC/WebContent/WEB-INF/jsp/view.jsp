<%@page import="com.aurora.controllers.ViewController"%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script language="JavaScript" src='<%= renderResponse.encodeURL(renderRequest.getContextPath() + "/js/jquery.1.8.2.js") %>'></script>
<portlet:actionURL var="actionURL">
	<portlet:param name="action" value="doSearch"/>
</portlet:actionURL >

<portlet:resourceURL var="search" id="search"/>
<!-- styles -->
<!-- link type="text/css" rel="stylesheet" href="http://7summitsclient.com/aurora/htmlsite/assets/css/common.css" />
<link type="text/css" rel="stylesheet" href="http://7summitsclient.com/aurora/htmlsite/assets/css/fonts.css" />
<link type="text/css" rel="stylesheet" href="http://7summitsclient.com/aurora/htmlsite/assets/css/styles.css" /--->

<script>

function searchGoogle(qTerm,endNum){
	if (typeof(endNum)=='undefined')endNum = EN;
	if (qTerm==''){
		qTerm = Q;
	}else{
		Q=qTerm;
	}
	if (SN=1)SN=SN-1;
	var rUrl = '<%=search%>';
	$.ajax({
		  type: 'GET',
		  dataType:'text',
		  async:false,
		  url: rUrl,
		  data:{q:qTerm,sn:0,en:endNum},
		  contentType:'text',
		  beforeSend:function(){
		  	$('#<%=ViewController.SEARCH_RESULTS_BOX%>').empty();
		    $('#<%=ViewController.SEARCH_RESULTS_BOX%>').html('<div class="loading"><img src="<%= renderResponse.encodeURL(renderRequest.getContextPath() + "/images/ajax-loader.gif") %>" /></div>');
		  },
		  success:function(data){
		  	$('#<%=ViewController.SEARCH_RESULTS_BOX%>').empty();
		    $('#<%=ViewController.SEARCH_RESULTS_BOX%>').html(data.toString());
		  },
		  error:function(xhr,status,error){
		  	alert(error);
		  }
	});	
	
}
function changePageSize(anchor,endNum){
	EN=endNum;
	var params={q:Q,sn:SN,en:endNum};
	alert(anchor.className);
	//document.getElementById("MyElement").className =
	//	   document.getElementById("MyElement").className.replace
	//	      ( /(?:^|\s)MyClass(?!\S)/g , '' )
	
	//callService(params);
}

function changePageNum(anchor,pageNum){
	SN=(pageNum*EN)-1;
	
	var params={q:Q,sn:pageNum,en:EN};
	callService(params);
}

function callService(params){
	var rUrl = '<%=search%>';
	$.ajax({
		  type: 'GET',
		  dataType:'text',
		  async:false,
		  url: rUrl,
		  data:params,
		  contentType:'text',
		  beforeSend:function(){
		  	$('#<%=ViewController.SEARCH_RESULTS_BOX%>').empty();
		    $('#<%=ViewController.SEARCH_RESULTS_BOX%>').html('<div class="loading"><img src="<%= renderResponse.encodeURL(renderRequest.getContextPath() + "/images/ajax-loader.gif") %>" /></div>');
		  },
		  success:function(data){
		  	$('#<%=ViewController.SEARCH_RESULTS_BOX%>').empty();
		    $('#<%=ViewController.SEARCH_RESULTS_BOX%>').html(data.toString());
		  },
		  error:function(xhr,status,error){
		  	alert(error);
		  }
	});	
	
}

</script>
<div id="<%=ViewController.SEARCH_RESULTS_BOX%>">
<!-- input type="button" value="test" onclick="searchGoogle('news')"/-->
<c:out escapeXml="false" value="${searchForm.searchResults}"/>
</div>


