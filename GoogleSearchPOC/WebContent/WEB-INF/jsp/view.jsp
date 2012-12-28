<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script language="JavaScript" src='<%= renderResponse.encodeURL(renderRequest.getContextPath() + "/js/jquery.min.js") %>'></script>
<portlet:actionURL var="actionURL">
	<portlet:param name="action" value="doSearch"/>
</portlet:actionURL>
<script>

function jsonPTest(){
		var urltest = 'http://api.brightcove.com/services/library?command=find_video_by_id&video_id=2036398618001&video_fields=videoStillURL&token=5WKGSuWLq40X64Mspy9GIMfguxGRTbj61XoleDpZgP87tmDzMUGUYA..&callback=response&noCacheIE=1356544456844';
		//$('#ajax-panel2').empty();
		$.ajax({
		  type: 'GET',
		  dataType:'jsonp',
		  async:false,
		  jsonpCallback:'response',
		  url: urltest,
		  contentType:'application/json',
		  beforeSend:function(){
		    //$('#acgc_pollbox').html('<div class="loading"><img src="/images/loading.gif" alt="Loading..." /></div>');
		    //acgcHandlePollSubmit();
		  },
		  success:function(data){
		  	alert(data.videoStillURL);
		  },
		  error:function(){
		  	alert('error');
		  }
		});	
	}


</script>
<input type="button" value="test1" onclick="jsonPTest()"/>
<form:form method="post" action="${actionURL }" commandName="searchForm" id="searchForm">
	<form:label path="searchString" cssClass="input"><span>INPUT SEARCH STRING:  </span><form:input path="searchString"/></form:label>
	<hr/>
	<input type="submit" value="SEARCH"/>
</form:form>


