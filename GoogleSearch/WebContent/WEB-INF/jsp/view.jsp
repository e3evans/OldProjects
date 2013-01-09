<%@page import="com.aurora.controllers.ViewController"%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<!-- script language="JavaScript" src='<%= renderResponse.encodeURL(renderRequest.getContextPath() + "/js/jquery.1.8.2.js") %>'></script-->
<portlet:actionURL var="actionURL">
	<portlet:param name="action" value="doSearch"/>
</portlet:actionURL >

<portlet:resourceURL var="search" id="search"/>
<portlet:resourceURL var="googleClick" id="googleClick"/>
<script>
var clickUrl = '<%=googleClick%>';
function changePageSize(perPage){
	PP=perPage;
	SN=0;
	var params={q:Q,sn:SN,en:PP,page:1};
	callService(params);
}

function changePageNum(anchor,pageNum){
	SN=(pageNum*PP)-1;
	if(pageNum==1)SN=0;
	var params={q:Q,sn:SN,en:PP,page:pageNum};
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
		    loadSortByHoverState();
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


