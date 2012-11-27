<%@ include file="/WEB-INF/jsp/includeTest.jsp" %>
<script>
 
function getCheckedValue(radioObj) {
	if(!radioObj)
		return "";
	var radioLength = radioObj.length;
	if(radioLength == undefined)
		if(radioObj.checked)
			return radioObj.value;
		else
			return "";
	for(var i = 0; i < radioLength; i++) {
		if(radioObj[i].checked) {
			return radioObj[i].value;
		}
	}
	return "";
}

function getPollResults(){
		
		if (getCheckedValue(document.forms['pollForm'].pollSelection)==''){
			alert('blank');
			return;
		}
		//$('#ajax-panel2').empty();
		$.ajax({
		  type: 'GET',
		  dataType:'text',
		  url: '<portlet:resourceURL id="renderPoll"/>',
		  data: { selectedPoll: getCheckedValue(document.forms['pollForm'].pollSelection)},
		  beforeSend:function(){
		    //$('#acgc_pollbox').html('<div class="loading"><img src="/images/loading.gif" alt="Loading..." /></div>');
		    acgcHandlePollSubmit();
		  },
		  success:function(data){
		  	acgcRemovePollOverlay();
		    $('#acgc_pollbox').empty();
		    $('#acgc_pollbox').html(data.toString());
		    initPolls();
		   
		    document.getElementById('acgc_pollbox').focus();
		  },
		  error:function(){
		    // failed request; give feedback to user
		    $('#acgc_pollbox').html('<p class="error"><strong>Oops!</strong> Try that again in a few moments.</p>');
		  }
		});	
	}

</script>




<div id="acgc_bottom_content_box_split_3" class="acgc_float_left">
     <div class="acgc_content_box_top_decal acgc_bg_align_right_top">
                     <!-- decal -->
     </div>
     <div class="acgc_content_box_body acgc_relative acgc_poll" style="height: 280px;" id="acgc_pollbox">
              <p>POLL</p>
              <h3><c:out value="${pollForm.poll.question}"/></h3>
              <portlet:actionURL var="submitPoll">
              	<portlet:param name="action" value="submitPoll"/>
              </portlet:actionURL>
              <form:form commandName="pollForm" name="pollForm">
              <c:forEach items="${pollForm.poll.pollOptions}" var="pOpt">
             	<p>
             		<form:radiobutton path="pollSelection" value="${pOpt.key}" />${pOpt.answer}
             	</p>
			  </c:forEach>                                 
              <p>
                  <a class="acgc_green_bttn acgc_radius_5" title="Sumit" href="javascript:void(0);" onclick="getPollResults()">
                    <span class="acgc_green_bttn_inner acgc_radius_3" >
                         Submit
                    </span>
                  </a>
              </p>
             </form:form>

              <div class="acgc_clear"><!-- clear --></div>                                        
     </div>
 </div>
<div class="acgc_clear"><!-- clear --></div> 