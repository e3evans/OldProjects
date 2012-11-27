<%@ include file="/WEB-INF/jsp/include.jsp" %>
 
<div id="acgc_bottom_content_box_split_3" class="acgc_float_left">
     <div class="acgc_content_box_top_decal acgc_bg_align_right_top">
                     <!-- decal -->
     </div>
     <div class="acgc_content_box_body acgc_relative acgc_poll">
     <div id="acgc_polls_canvas_slider" class="acgc_relative">
     	<div id="acgc_polls_canvas_slides">
				<div class="slide">
				<p>POLL RESULT</p>
	              <h3><c:out value="${pollForm.poll.question}"/></h3>
	              <c:forEach items="${pollForm.poll.pollOptions}" var="pOpt">
	             	<p>
	             	<c:choose>
		             	<c:when test="${pollForm.totalResults==0 }">
		             		<span class="acgc_percentage">0%</span>${pOpt.answer}
		             	</c:when>
		             	<c:otherwise>
			             	<c:set var="pct" value="${(pOpt.count / pollForm.totalResults) * 100}"/>
			             		<span class="acgc_percentage"><fmt:parseNumber value="${pct + 0.5}" integerOnly="true"/>%</span>${pOpt.answer}
		             	</c:otherwise>
	             	</c:choose>
	             	</p>
				  </c:forEach> 
				  <c:if test="${not empty pollForm.poll.answer }">  
					  <p>
					  	<strong>The correct answer is:</strong>
								${pollForm.poll.answer} 
					  </p>
				  </c:if> 
				  <div class="acgc_clear"><!-- clear --></div>
	            </div>
	            <c:forEach items="${pollForm.archivePolls }" var="archPoll">
	            <div class="slide">
				<p>POLL RESULT</p>
	            <h3><c:out value="${archPoll.question}"/></h3>
	            	<c:forEach items="${archPoll.pollOptions }" var="archOption" varStatus="status">
		            	<p>
		            		<c:choose>
			            		<c:when test="${archPoll.totalAnswers == 0}">
			            			<span class="acgc_percentage">0%</span>${archOption.answer}
			            		</c:when>
			            		<c:otherwise>
			            			<c:set var="pct" value="${(archOption.count / archPoll.totalAnswers) * 100}"/>
			            			<span class="acgc_percentage"><fmt:parseNumber value="${pct + 0.5}" integerOnly="true"/>%</span>${archOption.answer}
			         			</c:otherwise>
		         			</c:choose>
		         		</p>
		         	
	            	</c:forEach>
	            	<c:if test="${not empty archPoll.answer }">
	            		<p>
				  			<strong>The correct answer is:</strong>
							${archPoll.answer} 
				  		</p> 
				  	</c:if>
	            	<div class="acgc_clear"><!-- clear --></div>
		            </div>
	            </c:forEach>
	         </div>
	         <div id="acgc_polls_canvas_slider_controls">
	         	<div style="padding: 0 10px 0 0; font-style: italic; color: #666666;" class="acgc_float_left">Poll Results:</div>
				<div id="acgc_polls_canvas_slider_left"><!-- left --></div>
				<div class="icon selected"><!-- icon --></div>
				<div class="icon"><!-- icon --></div>
				<div class="icon"><!-- icon --></div>
				<div class="icon"><!-- icon --></div>
				<div class="icon"><!-- icon --></div>
				<div class="icon"><!-- icon --></div>
				<div id="acgc_polls_canvas_slider_right"><!-- left --></div>
			</div> 
		</div>                          
     </div>
 </div>
<div class="acgc_clear"><!-- clear --></div> 
<script>
initPolls();
</script>
