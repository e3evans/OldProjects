<%@ include file="/WEB-INF/jsp/include.jsp" %>




<div id="acgc_bottom_content_box_split_3" class="acgc_float_left">
     <div class="acgc_content_box_top_decal acgc_bg_align_right_top">
                     <!-- decal -->
     </div>
     <div class="acgc_content_box_body acgc_relative acgc_poll" style="height: 280px;">
              <p>POLL</p>
              <h3><c:out value="${pollForm.poll.question}"/></h3>
              <portlet:actionURL var="submitPoll">
              	<portlet:param name="action" value="submitPoll"/>
              </portlet:actionURL>
              <form:form commandName="pollForm" action="${submitPoll }" name="pollForm">
              <c:forEach items="${pollForm.poll.pollOptions}" var="pOpt">
             	<p>
             		<form:radiobutton path="pollSelection" value="${pOpt.key}" />${pOpt.answer}---${pOpt.count }
             	</p>
			  </c:forEach>                                 
              <p>
                  <a class="acgc_green_bttn acgc_radius_5" title="Sumit" href="#" onclick="document.forms['pollForm'].submit()">
                    <span class="acgc_green_bttn_inner acgc_radius_3" >
                                    Submit
                    </span>
                  </a>
              </p>
             </form:form>
              <p class="acgc_float_right">
                              <a title="View Results" href="">View results</a> | <a title="View All Polls" href="">View all polls</a>
              </p>
              <div class="acgc_clear"><!-- clear --></div>                                        
     </div>
 </div>
<div class="acgc_clear"><!-- clear --></div> 