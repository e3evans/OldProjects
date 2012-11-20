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

              <c:forEach items="${pollForm.poll.pollOptions}" var="pOpt">
             	<p>
             	<c:set var="pct" value="${(pOpt.count / pollForm.totalResults) * 100}"/>
             	
             		${pOpt.answer}---<fmt:parseNumber value="${pct + 0.5}" integerOnly="true"/>
             	</p>
			  </c:forEach>                                 

              <p class="acgc_float_right">
                              <a title="View Results" href="">View results</a> | <a title="View All Polls" href="">View all polls</a>
              </p>
              <div class="acgc_clear"><!-- clear --></div>                                        
     </div>
 </div>
<div class="acgc_clear"><!-- clear --></div> 

