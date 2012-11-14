<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/portlet" prefix="portlet"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<link rel="stylesheet" type="text/css" href="<c:out value='${requestPath}'/>/web/fw/tools/bss/xmproot/xwt/themes/masterbrand/xwt/form/starrating.css" />

<style type="text/css">
	.messageHiddenRate{
		display: none;
	}
	.messageShowRate{
		display: block;
		float:left;
	}
</style>

<script type="text/javascript">
var isSubmit = false;
var rangeOffset = 0;
var userId = '<c:out value="${sessionScope.USER_SESSION_BEAN.USER}" />';
var feedbackIdNum = 0;
var commentUpdateRating = "";
var countAvg = "";
			
dojo.addOnLoad(function(){
	setPreviousComment();
});

function setPreviousComment(){
    var commentTrack = userRatingDetail;
    feedbackIdNum = commentTrack.feedbackId;
    if(commentTrack.userId == userId ){
        if(commentTrack.comment == undefined){
        	document.getElementById("bss_ratingComment").innerHTML = "";
        }else{
    		document.getElementById("bss_ratingComment").innerHTML = commentTrack.comment;
    	}
    	commentUpdateRating = commentTrack.comment;
    	countAvg = commentTrack.rating;
    	rangeOffset = countAvg;
    	dijit.byId("starRating").updateTotalRatings(countAvg);
    	dijit.byId("starRating")._renderStars(countAvg);
 		dijit.byId("ratingSubmit").setLabel('<spring:message code="rate.button.updaterating" />');
 		isSubmit = true;
 		if(isSubmit == true){
 			dijit.byId("ratingSubmit").attr("disabled",false);
 		}else{
 			dijit.byId("ratingSubmit").attr("disabled",true);
 		}
    }else{
    	 dijit.byId("ratingSubmit").setLabel('<spring:message code="rate.button.submit" />');
    	 isSubmit = false;
    }
}
		
function getRatingLabel(count){
	var ratingLabel = '<spring:message code="rate.label.selectrating" />';
	if(count == 1){
		ratingLabel = '<spring:message code="rate.label.poor" />';
	}else if (count == 2){
		ratingLabel = '<spring:message code="rate.label.moderate" />';
	}else if (count == 3){
		ratingLabel = '<spring:message code="rate.label.medium" />';
	}else if(count == 4){
		ratingLabel = '<spring:message code="rate.label.good" />';
	}else if(count == 5){
		ratingLabel = '<spring:message code="rate.label.excellent" />';
	}
	return ratingLabel;
}
function checkLabelOfSubmit(){
	if(isSubmit){
   		updateRating();
   	}else{
   		submitRating();
   	}
}
function submitRating(){
	var commentsInput= document.getElementById("bss_ratingComment").value;
    var requestPath = "<c:url value='/service/requestproxy/put'/>" + "/bss/ratings/rne?un=" + userId + "&requestType=R&bugId=${bugDetail.bugId}&action=rate&rating=" + rangeOffset +"&comment=" + commentsInput;
	var submitRatingUrl = {
							url: requestPath,
        					preventCache:true,
        					handleAs: "json"
    					  }
	var submitRatingXhr = dojo.xhrGet(submitRatingUrl);
	submitRatingXhr.addCallback(function(_jsonObject) {
		//var ratingResponse = _jsonObject['ratingResponse'];
		//var responseSubmittedCode = _jsonObject['responseCode'];
		if(_jsonObject['responseCode'] == "Success"){
		    dijit.byId("ratingSubmit").setLabel('<spring:message code="rate.button.updaterating" />');
		    isSubmit = true;
			    dojo.removeClass("showMessageImage","showAlertImageHide");
			    dojo.addClass("showMessageImage","showAlertImageDisplay");
			if(dojo.trim(commentsInput) == ""  && rangeOffset != 0){
				dojo.removeClass("showMessageAfterRating", "messageHiddenRate");
	       		dojo.addClass("showMessageAfterRating","messageShowRate");	
			}
			else{
		    	dojo.removeClass("showMessageAfterSubmit", "messageHiddenRate");
       			dojo.addClass("showMessageAfterSubmit","messageShowRate");
       		}
		}else{
		   dijit.byId("ratingSubmit").setLabel('<spring:message code="rate.button.submit" />');
		}
	});
}

function updateRating(){
	dojo.removeClass("showMessageAfterRating", "messageShowRate");
    dojo.addClass("showMessageAfterRating","messageHiddenRate");	
    
    dojo.removeClass("showMessageAfterSubmit", "messageShowRate");
    dojo.addClass("showMessageAfterSubmit","messageHiddenRate");	
		
	dojo.removeClass("showMessageAfterRating", "messageHiddenRate");
    dojo.addClass("showMessageAfterRating","messageShowRate");
    
    dojo.removeClass("showMessageImage","showAlertImageHide");
    dojo.addClass("showMessageImage","showAlertImageDisplay");
    var commentsInputToUpdate = document.getElementById("bss_ratingComment").value;
	var requestPath = "<c:url value='/service/requestproxy/post'/>" + "/bss/ratings/rne/"+ feedbackIdNum + "?un=" + userId + "&action=edit&rating=" + rangeOffset + "&comment=" + commentsInputToUpdate;
	var bss_updateRating = {
					url: requestPath, 
                	preventCache:true,
                	handleAs: "json"
    				}
	var bss_updateRatingXhr = dojo.xhrPost(bss_updateRating);
	
	bss_updateRatingXhr.addCallback(function(_jsonObject) {
	});
}
	
			
	
dojo.declare("RateTheBugRating", xwt.widget.form.Rating, {	   
	onStarClick:function(event){
 		dijit.byId("ratingSubmit").attr("disabled",false);
		var hoverValue = +dojo.attr(event.target, "value");
		rangeOffset = hoverValue;
 		if(this.disabled){
 			return;
 		}
 		this._renderStars(rangeOffset);
 		this.updateTotalRatings(rangeOffset);
 		
 	},
 	_onMouse : function(/*Event*/ event){
		var mouseNode = event.currentTarget;
		if(mouseNode && mouseNode.getAttribute){
			this.stateModifier = mouseNode.getAttribute("stateModifier") || "";
		}
		if(!this.disabled){
			switch(event.type){
				case "mouseenter":
				case "mouseover":
					this._hovering = true;
					this._active = this._mouseDown;
					break;

				case "mouseout":
				case "mouseleave":
					this._hovering = false;
					this._active = false;
					break;

				case "mousedown" :
					this._active = true;
					this._mouseDown = true;
					var mouseUpConnector = this.connect(dojo.body(), "onmouseup", function(){
						if(this._mouseDown && this.isFocusable()){
							this.focus();
						}
						this._active = false;
						this._mouseDown = false;
						this._setStateClass();
						this.disconnect(mouseUpConnector);
					});
					break;
			}
			this._setStateClass();
		}
		if(this._hovering){
			var hoverValue = +dojo.attr(event.target, "value");
			this._renderStars(hoverValue, true);
			this.onMouseOver(event, hoverValue);
			this.updateTotalRatings(hoverValue);
		}else{
			//this._renderStars(0,true);
			this._renderStars(rangeOffset);
			this.updateTotalRatings(rangeOffset);
		}
	},
 	_formatTotalRatings: function(count){
    	 this.ratingsLabel = getRatingLabel(count);
	 	 this.totalRatings = count;
    	 return "("+this.totalRatings + this.ratingsLabel + ")"; 
    }
});
</script>
<!--
<div id="rateTheBugDashlet" dojoType="xwt.widget.layout.Dashlet" title="<spring:message code="rate.dashlet.title.ratebugcontent" />" requireCollapse="false">

	<div>
		<table style="margin:15px 0 10px 20px;">
			<tr>
				<td colspan="2"  style="margin: 0 0 20px 20px;">
					<div id="showMessageImage" class="showAlertImage showAlertImageHide"></div>

					<div id="showMessageAfterSubmit" class="messageHiddenRate">
						<spring:message code="message.show.aftersubmit.comments"/>
					</div>
					<div id="showMessageAfterRating" class="messageHiddenRate">
						<spring:message code="message.show.aftersubmit.rating"/>
					</div>
				</td>			
			</tr>
			<tr>
				<td style="width:150px;"><spring:message code="rate.label.rateit"/>:</td>
				<td style="width:600px;">
					<div id="starRating" dojoType="RateTheBugRating" numStars="5" totalRatings="0" value="0"></div>
				</td>
			</tr>
			<tr>
				<td style="width:150px; vertical-align:top;">
					<div class="textComments"><spring:message code="rate.lable.comment"/></div>
					</td>
				<td>
					<form id="rateCommentFormId" name="rateCommentForm" class="rateCommentForm">
						<textarea id="bss_ratingComment" name="bss_ratingComment" cols="100" rows="2" wrap="soft" class="bssRatingCommentText" onKeyDown="chracterCount(this.form.bss_ratingComment);" 
						onKeyUp="chracterCount(this.form.bss_ratingComment);"></textarea>
						<input name="syzygy" value="108" dojoType="dijit.form.TextBox" type="hidden" />
						<br>
					</form>	
				</td>
			</tr>
			<tr>
				<td style="width:150px;">&nbsp;</td>
				<td style="width:600px;">
					<input id="ratingSubmit" class="submitRating" dojoType="xwt.widget.form.TextButton" disabled="true" type="submit"
						value="<spring:message code="rate.button.submit"/>" onClick="checkLabelOfSubmit();" label="Submit"></input>
				</td>
			</tr>
		</table>
	</div>
</div>-->