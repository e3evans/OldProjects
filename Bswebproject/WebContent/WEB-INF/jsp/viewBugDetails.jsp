<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/portlet" prefix="portlet"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@page import="javax.portlet.PortletRequest"%>

<%	
	PortletRequest pResquest = (PortletRequest)request;
	pageContext.setAttribute("pid", pResquest.getPortletSession().getAttribute("pid"), PageContext.REQUEST_SCOPE);
%>

<link rel="stylesheet" type="text/css" href="<c:out value='${requestPath}'/>/web/fw/tools/bss/xmproot/xwt/themes/masterbrand/xwt/anchoredoverlay/anchoredoverlay.css" />
<link rel="stylesheet" type="text/css" href="<c:out value='${requestPath}'/>/web/fw/tools/bss/xmproot/xwt/themes/masterbrand/xwt/quickview/quickview.css" />

<portlet:defineObjects />
<portlet:renderURL var="searchPageBread">
	<portlet:param name="view" value="breadcrum"/>
	<portlet:param name="page" value="searchPage"/>
	<portlet:param name="rfTag" value="bugDetailPage"/>
</portlet:renderURL>

<portlet:renderURL var="resultPageBread">
	<portlet:param name="view" value="breadcrum"/>
	<portlet:param name="page" value="resultPage"/>
	<portlet:param name="rfTag" value="bugDetailPage"/>
</portlet:renderURL>

<style type="text/css">
.floatcontent {
	overflow:hidden;
}
html {
	overflow:auto;
}
.textAlign {
	vertical-align: right;
}
.qvAnchor {
	width: 12px;
	margin: 0 2px 0 2px;
}

#bss_barChartId a:hover{
	text-decoration:none !important;
}
.hideDetailsOfRating{
	display:none;
}

.showDetailsOfRating{
	display:block;
}
.totalRatings{
	color:#000000 !important;
}
#creditOverlay .tooltiptitle{
	height:16px;
	padding-bottom:0 !important;
}
</style>
<style>
/*rating distribution*/
	.masterbrand .xwtRating{
		margin: 0 8px 15px 0;
	}
	#breadcrumbContainer{
		margin:0 !important;
		padding:0;
	}
	.xwtBreadcrumb {
		margin: 0 !important;
		padding-left: 0 !important;
	}
</style>	

<script type="text/javascript">
	var isSaveSearch = false;
	var saveSearchParam = "";
	var disableEmailNotification = false;
	var tc, cp0, cp1, cp2, cp3;
	var userId = '<c:out value="${sessionScope.USER_SESSION_BEAN.USER}" />';
	var userType = '<c:out value="${sessionScope.USER_SESSION_BEAN.USER_TYPE}" />';
	var bugRatingDetail = null;
	var userRatingDetail = null;
	var desContentHeight = "0px";
	var isSubmit = false;
	var rangeOffset = 0;
	var feedbackIdNum = 0;
	var commentUpdateRating = "";
	var countAvg = 0;

	dojo.require("dojo.fx");
	
	dojo.addOnLoad(function(){
	 //	trackEvent.event('view',{'title':'Bug Detail Page', 'sitearea':'bss','action':'pageLoad','rf':'http://www.cisco.com/cisco/psn/bssprt/bss/bssSummary','lc':'http://www.cisco.com/cisco/psn/bssprt/bss/bssDetail', ntpagetag: {sensors:[ bss_ntpt_imgsrc ]}});
	 	dojo.parser.parse();
	 	initDetailPage();
	});
		function getBssRatingDetails(){
			    var requestPath = "<c:url value='/service/requestproxy/get'/>" + "/bss/ratings/rne/${bugDetail.bugId}?&rt=bugId";
				var ratingDetailUri = {
									url: requestPath,
					                preventCache:true,
					                sync: true,
					                handleAs: "json"
			    				  }
				var ratingDetailXhr = dojo.xhrGet(ratingDetailUri);
				ratingDetailXhr.addCallback(function(_jsonObject) {
					if(_jsonObject['bugRatings'].length == 0){
							userRatingDetail = {comment:"", userId:"",createdDate:"", rating:[], feedbackId:0 };
					    	bugRatingDetail = {averageRating:0,feedbackCount:0,comment:"", userId:"", createdDate:"", rating:[],numberOfFeedback1:0,numberOfFeedback2:0,numberOfFeedback3:0,numberOfFeedback4:0,numberOfFeedback5:0};
				    }else{
				    	bugRatingDetail = _jsonObject['bugRatings'][0];
				    	if(bugRatingDetail['userRating'] == undefined){
		    				userRatingDetail = {comment:"", userId:"",createdDate:"", rating:[], feedbackId:0 };
		    			}else{
		    				userRatingDetail = bugRatingDetail['userRating'];
		    			}
						document.getElementById("bss_totalRatings").innerHTML = "Total " + _jsonObject['bugRatings'][0]['feedbackCount'] + " Ratings";
					}
				});
			setPreviousComment();
			return bugRatingDetail;
		}
    
		function toggleArrowImage(id,contentId,link,rest){
			if(dojo.hasClass(dojo.byId(id),"arrowImageDown")){
				var wipeArgs = {
                       node: contentId
                   };
                dojo.fx.wipeOut(wipeArgs).play();
				dojo.removeClass(dojo.byId(id),"arrowImageDown");
				dojo.addClass(dojo.byId(id),"arrowImageRight");
			}
			else if(dojo.hasClass(dojo.byId(id),"arrowImageRight")){
				dojo.style(contentId, "display", "inline-block");
				var wipeArgs = {
                       node: contentId
                   };
                dojo.fx.wipeIn(wipeArgs).play();
				dojo.addClass(dojo.byId(id),"arrowImageDown");
				dojo.removeClass(dojo.byId(id),"arrowImageRight");
			}
			else if(dojo.hasClass(dojo.byId(id),"arrowImageSmallDown")){
				var wipeArgs = {
                       node: contentId
                	};
                dojo.fx.wipeIn(wipeArgs).play();
                var parentId = contentId.substr(0,contentId.length-5);
				if(rest != "rest"){
					dojo.style(parentId + "_arrowMore", "display","none");
					dojo.style(parentId + "_arrowLess", "display","inline-block");
				}
				else{
					dojo.style(parentId + "_arrowMore", "display","inline-block");
					dojo.style(parentId + "_arrowLess", "display","inline-block");
				}
			}
			else if(dojo.hasClass(dojo.byId(id),"arrowImageSmallTop")){
				dojo.style(contentId, "height", "");
                dojo.style(contentId, "display", "inline-block");
				var wipeArgs = {
                       node: contentId
                   };
                dojo.fx.wipeOut(wipeArgs).play();
					var parentId = contentId.substr(0,contentId.length-5);
					dojo.style(parentId + "_arrowMore", "display","inline-block");
					dojo.style(parentId + "_arrowLess", "display","none");
			}
		}
			
		function showRatingComments(value){
			if(userRatingDetail.comment == undefined){
	        	dojo.byId("bss_ratingComment").value = "";
	        }else{
	    		dojo.byId("bss_ratingComment").value = userRatingDetail.comment;
	    	}
			dijit.byId("rcOverlay").openAtNode(dijit.byId("feedbackRating").domNode);
			dojo.style("rcOverlay","display","inline-block");
			dijit.byId("contentRatingOvl").setValue(this.value);
			dojo.attr(dijit.byId("contentRatingOvl").totalRatingsSpan,"innerHTML","("+ getRatingLabel(this.value)+")");
			dijit.byId("contentRatingOvl")._renderStars(this.value);
			if(this.value == 0){
				dijit.byId("ratingSubmit").attr("disabled",true);
			}
			else{
				dijit.byId("ratingSubmit").attr("disabled",false);
			}
		}
		function contentRatingChanged(){
			dojo.attr(dijit.byId("feedbackRating").totalRatingsSpan,"innerHTML","(" + getRatingLabel(this.value)+")");
			dijit.byId("feedbackRating").setValue(this.value);
			dijit.byId("feedbackRating")._renderStars(this.value);
			if(this.value == 0){
				dijit.byId("ratingSubmit").attr("disabled",true);
			}
			else{
				dijit.byId("ratingSubmit").attr("disabled",false);
			}
		}
		function onContentRatingMo(e,v){
			dojo.attr(this.totalRatingsSpan,"innerHTML","(" + getRatingLabel(v)+")");
		}
		function onContentRatingMOver(e,v){
			dojo.attr(this.totalRatingsSpan,"innerHTML","(" + getRatingLabel(v)+")");
		}
		function onContentRatingMOut(){
			var x = dijit.byId("contentRatingOvl");
			dojo.attr(x.totalRatingsSpan,"innerHTML","(" + getRatingLabel(x.value)+")");
		}
		function onFeedbackRatingMOut(){
			var x = dijit.byId("feedbackRating");
			dojo.attr(x.totalRatingsSpan,"innerHTML","(" + getRatingLabel(x.value)+")");
		}
		
		function getRatingLabel(count){
			var ratingLabel = '<spring:message code="rate.label.selectrating" />';
			if(count == 1){
				ratingLabel = count + " " + '<spring:message code="rate.label.poor" />';
			}else if (count == 2){
				ratingLabel = count + " " + '<spring:message code="rate.label.moderate" />';
			}else if (count == 3){
				ratingLabel = count + " " + '<spring:message code="rate.label.medium" />';
			}else if(count == 4){
				ratingLabel = count + " " + '<spring:message code="rate.label.good" />';
			}else if(count == 5){
				ratingLabel = count + " " + '<spring:message code="rate.label.excellent" />';
			}
			return ratingLabel;
		}
		function convertData(strData,splitChar){
			var dataArray = new Array();
			if(strData){
				dataArray = strData.split(splitChar);
			}
			return dataArray; 
		}
		function showDetailNormalPart(divContainer, divData){
			if(!divData){
				dojo.style(divContainer, "display", "none");
			}
			else{
				dojo.style(divContainer, "display", "block");
				dojo.style(dojo.byId(divContainer).parentNode,"display", "inline-block");
			}
		}
		function showDetailMorePart(divContainer, divData){
			if(divData.length == 0){
				dojo.style(divContainer + "_more", "display", "none");
				dojo.style(divContainer + "_arrowMore", "display", "none");
				dojo.style(divContainer + "_arrowLess", "display", "none");
				dojo.style(divContainer, "display", "none");
			}
			else{
				dojo.style(divContainer, "display", "inline-block");
				dojo.style(dojo.byId(divContainer).parentNode,"display", "inline-block");
				dojo.byId(divContainer + "_label").innerHTML += "("+divData.length+")";
				var dataRow = 1;
				for(var i=0;i<divData.length;i++){
					var tempDivData = divData[i];
					for(var j=0;j<4;j++){
						if(divData[i+1]){
							tempDivData += "," + divData[i+1];
							i++;
						}
						else{
							break;
						}
					}
					if(dataRow <= 2){
						i = createDataDiv(divContainer,dataRow,tempDivData,i,"_data");
					}
					else if(dataRow <= 50){
						i = createDataDiv(divContainer,dataRow,tempDivData,i,"_more");
					}
					else{
						i = createDataDiv(divContainer,dataRow,tempDivData,i,"_rest");
					}
					dataRow++;
				}
				dojo.style(divContainer + "_more","display","none");
				dojo.style(divContainer + "_rest","display","none");
				if(dataRow >3){
					dojo.style(divContainer + "_arrowMore", "display", "inline-block");
				}
			}	
		}
		function createDataDiv(divContainer,dataRow,tempDivData,i,divType){
			var rowDiv = dojo.create("div", 
				{id: divContainer+"_div_"+(dataRow - 1), 
					innerHTML:tempDivData,
					style:"margin-bottom:5px;word-wrap:break-word;"}, 
				divContainer + divType);
			dojo.addClass(rowDiv,"bodytext");
			while(rowDiv.offsetHeight > 20){
				tempDivData = tempDivData.substring(0,tempDivData.lastIndexOf(","));
				i--;
				rowDiv.innerHTML = tempDivData;
			}
			return i;
		}
		function createRatingChart(){
			var strTotalRatings = bugRatingDetail['feedbackCount'] + " Total Ratings";
			document.getElementById("TotalRatingHead").innerHTML = strTotalRatings;
			document.getElementById("TotalRatingSubHead").innerHTML = "Rating Distribution - " + strTotalRatings;

			var max = 0;
			var data;
			var divWidth = 0;
			
			for(var i=5;i>0;i--){
				max = max>bugRatingDetail["numberOfFeedback"+i]?max:bugRatingDetail["numberOfFeedback"+i];
			}
			
			for(var i=5;i>0;i--){
				data = bugRatingDetail["numberOfFeedback"+i];
				if(max != 0)
					divWidth = (300 * data) /max;
				dojo.create("div", 
					{id: "Chart_Star_"+i,
						innerHTML:
								"<div style='float:left'>"+i+" Stars</div>"+
								"<div style='margin:0 5px;width:"+divWidth+"px;background-color:#66AAEE;float:left' class='chartBar'>&nbsp;</div>"+
								"<div style='float:left'>("+data+")</div>",
						style:"margin-top:10px;width:100%;height:15px;"}, 
						"RatingChartContainer");
			}
			
		}
		/*
		This function is used to show comments for bugId and display with rating
		in rate distribution section
		*/
		function addCommentDivs(commentsDetails, start, containerDiv){
			for(var i=start; i < commentsDetails.length; i++){
			    if(commentsDetails[i].comment == undefined){
				    	commentsDetails[i].comment = "";
			    }
			    var evenOddClass = i%2 == 0?"oddSummaryRow":"evenSummaryRow";
			    var createdDate = commentsDetails[i].createdDate || "";
			    var modifiedCommentedDate = "";
			    if(createdDate != ""){
				    var tPosition = createdDate.indexOf('T');
					var arrayOfDate=createdDate.substring(0,tPosition).split('-');
					var modifiedDate = new Date(arrayOfDate[0],arrayOfDate[1]-1,arrayOfDate[2]) || "";
					modifiedCommentedDate = " (Date " + dojo.date.locale.format(modifiedDate, {datePattern: "MM/dd/yy", selector: "date"}) + ")";
				}
				var strComments = '<div class="'+evenOddClass+'">'+
									'<div style="height:30px">'+
										'<div style="float:left"><div id="rateCountId"></div></div>'+
										'<div style="float:left">'+' By '+commentsDetails[i].userFirstName+ ' ' + commentsDetails[i].userLastName+
										modifiedCommentedDate +
										'</div>'+
									'</div>'+
									'<div>'+commentsDetails[i].comment+'</div>'+
							 	'</div>';
	       	    document.getElementById(containerDiv).innerHTML += strComments;
	       	    var showStarComment = new xwt.widget.form.Rating(
					{id:"rateStar"+i, 
						numStars:"5", 
						ratingsLabel:"",
						value:commentsDetails[i].rating,
						disabled:true
						},"rateCountId");
   			 }
		}
		function createCommentSection(){
		    var commentsDetails = bugRatingDetail['rating'];
		    if(commentsDetails.length == undefined){
		    	commentsDetails.length = -1;
		    }
		    
		  	if(bugRatingDetail.feedbackCount > 10){
				dojo.style("ratingDistribution_arrowM", "display", "inline-block");
			}
		  	addCommentDivs(commentsDetails,0,"ratingDistribution_comments");
		  	
		}
		function fetchMoreComments(){
			if(dojo.byId("ratingDistribution_more").innerHTML == ""){
				var commentsDetailsMore = null;
				var requestPath = "<c:url value='/service/requestproxy/get'/>" + "/bss/ratings/rne/${bugDetail.bugId}?rt=bugId&rpp=" + bugRatingDetail.feedbackCount;
				var allBugCommentsUri = {
					url: requestPath,
		            preventCache:true,
		            sync: true,
		            handleAs: "json"
	   			};
				var allBugCommentsXhr = dojo.xhrGet(allBugCommentsUri);
				allBugCommentsXhr.addCallback(function(_jsonObject) {
						commentsDetailsMore = _jsonObject['bugRatings'][0]['rating'];
				});
	
				addCommentDivs(commentsDetailsMore,10,"ratingDistribution_more");
			}
			var wipeArgs = {
                       node: "ratingDistribution_more"
                	};
            dojo.fx.wipeIn(wipeArgs).play();
			//dojo.style("ratingDistribution_more", "display", "block");
			dojo.style("ratingDistribution_arrowM", "display", "none");
			dojo.style("ratingDistribution_arrowL", "display", "inline-block");
			//dojo.style("ratingDistribution_arrow","display","none");		   	   
		}
		function hideMoreComments(){
			var wipeArgs = {
                      node: "ratingDistribution_more"
                  };
            dojo.fx.wipeOut(wipeArgs).play();
			//dojo.style("ratingDistribution_more", "display", "none");
			dojo.style("ratingDistribution_arrowM", "display", "inline-block");
			dojo.style("ratingDistribution_arrowL", "display", "none");
		}

		function initDetailPage(){
			bugRatingDetail = getBssRatingDetails();
			//to display image based on visibility
			<c:if test="${sessionScope.USER_SESSION_BEAN.USER_TYPE == 'EMPLOYEE'}">
				<c:if test='${bugDetail.visible}'> 
					dojo.addClass("creditIcon","statusBannerImageAllow");
				</c:if>
				<c:if test='${!bugDetail.visible}'>
					dojo.addClass("creditIcon","statusBannerImageBlock");
				</c:if>
			</c:if>
			//to display description block	
			var relText = '<c:out value="${bugDetail.releaseNoteText}" />'.replace(/\n/g, "<br/>").replace(/&lt;/g, "<").replace(/&gt;/g, ">");
			
			dojo.style("desContent","display","inline-block");
			if(relText == ""){
				dojo.style("bugInfoSubDetail","display","none");
				desContentHeight = "16px";
			}
			else{
				dojo.byId("bugInfoSubDetail").innerHTML = relText;
				desContentHeight = (dojo.byId("bugInfoSubDetail").offsetHeight + 66) + "px";
			}
			
			
			var contentRatingWid = new xwt.widget.form.Rating(
					{id:"contentRating", 
						numStars:"5", 
						ratingsLabel:"",
						value:bugRatingDetail.averageRating,
						totalRatings:bugRatingDetail.feedbackCount,
						disabled:true
						},"contentRatingC");
			
		    dojo.style("desContent", "height", desContentHeight);
		    dojo.style("desContent", "min-height", desContentHeight);
			
			
			
			//to display sections of detail section
			var foundInData = convertData(dojo.string.trim('${bugDetail.foundIn}'),',');
			showDetailMorePart("FirstFoundDiv", foundInData);
			
			showDetailNormalPart("StatusDiv", '${bugDetail.statusName}');
			showDetailNormalPart("LastModifiedDiv", '${bugDetail.bugLastModifiedDate}');
			
			showDetailNormalPart("ProductDiv", '${bugDetail.productSoftwareFamily}');
			showDetailNormalPart("PlatformDiv", '${bugDetail.platformName}');
			showDetailNormalPart("SeverityDiv", '${bugDetail.severityCode}');
			var KnownAffData = convertData(dojo.string.trim('${bugDetail.versionAffectedNames}'),' ');
			showDetailMorePart("KnownAffVerDiv", KnownAffData);
			var fixedInData = convertData(dojo.string.trim('${bugDetail.fixedInVersions}'),',');
			showDetailMorePart("FixedInDiv", fixedInData);
			
			<c:if test="${sessionScope.USER_SESSION_BEAN.USER_TYPE == 'EMPLOYEE'}">
				var srNumbers = '${bugDetail.srNumbersAssociatedTo}';
				var srArray = convertData(dojo.string.trim('${bugDetail.srNumbersAssociatedTo}'),' ');
				if(srArray){
					var custRepData = new Array();
					for(i=0;i < srArray.length;i++){
						custRepData[i] = '<a href="http://wwwin-tools.cisco.com/casekwery/getServiceRequest.do?id='+srArray[i]+'" target="_blank">'+srArray[i]+'</a>&nbsp';
					}
					showDetailMorePart("CustRepDiv",custRepData);
				}
			</c:if>
			<c:if test="${sessionScope.USER_SESSION_BEAN.USER_TYPE != 'EMPLOYEE'}">
				var custRepData = convertData(dojo.string.trim('${bugDetail.srNumbersAssociatedTo}'),' ');
				if(custRepData.length > 0){
					dojo.style("CustRepDiv", "display", "block");
					dojo.style(dojo.byId("CustRepDiv").parentNode,"display", "inline-block");
					dojo.byId("CustRepDiv_label").innerHTML += "("+custRepData.length+")";
				}
			</c:if>
			//to display or not related bugs	
	    	showDetailNormalPart("relatedBugsContainer",'${bugDetail.relatedBugList}');
	  		
	  		//to display or not rating distribution and comments
	  		<c:if test="${sessionScope.USER_SESSION_BEAN.USER_TYPE == 'EMPLOYEE'}">
		  		if(bugRatingDetail['feedbackCount'] != 0){
		  			dojo.style("ratingDistContainer", "display", "block");
					createRatingChart();
					createCommentSection();
				}
			</c:if>
		}
		
		function chracterCount(inputData){
			if (inputData.value.length > 255) {
				inputData.value = inputData.value.substring(0, 255);
			} 
		}
		
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
		    	dijit.byId("contentRatingOvl").setValue(countAvg);
		    	dijit.byId("contentRatingOvl").updateTotalRatings(countAvg);
		    	dijit.byId("contentRatingOvl")._renderStars(countAvg);
		    	
		    	dijit.byId("feedbackRating").setValue(countAvg);
		    	dijit.byId("feedbackRating").updateTotalRatings(countAvg);
		    	dijit.byId("feedbackRating")._renderStars(countAvg);
		    	
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
		    dojo.attr(dijit.byId("feedbackRating").totalRatingsSpan,"innerHTML","(" + getRatingLabel(countAvg)+")");
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
			rangeOffset = dojo.byId("contentRatingOvl").value;
		    var requestPath = "<c:url value='/service/requestproxy/put'/>" + "/bss/ratings/rne?requestType=R&bugId=${bugDetail.bugId}&action=rate&rating=" + rangeOffset +"&comment=" + commentsInput;
			var submitRatingUrl = {
									url: requestPath,
		        					preventCache:true,
		        					handleAs: "json"
		    					  }
			var submitRatingXhr = dojo.xhrGet(submitRatingUrl);
			submitRatingXhr.addCallback(function(_jsonObject) {
				if(_jsonObject['responseCode'] == "Success"){
				    dijit.byId("ratingSubmit").setLabel('<spring:message code="rate.button.updaterating" />');
				    isSubmit = true;
		       		dojo.style("rcOverlay","display","none");
		       		if(dojo.trim(commentsInput) == ""  && rangeOffset != 0){
		       			dojo.byId("ratingSuccessMsg").innerHTML = '<spring:message code="message.show.aftersubmit.rating"/>';
					}
					else{
						dojo.byId("ratingSuccessMsg").innerHTML = '<spring:message code="message.show.aftersubmit.comments"/>';
		       		}
		       		dijit.byId("ratingSuccessId").show();
				}else{
				   dijit.byId("ratingSubmit").setLabel('<spring:message code="rate.button.submit" />');
				}
			});
		}
		
		function updateRating(){
		    var commentsInputToUpdate = document.getElementById("bss_ratingComment").value;
			rangeOffset = dojo.byId("contentRatingOvl").value;
			var requestPath = "<c:url value='/service/requestproxy/post'/>" + "/bss/ratings/rne/"+ feedbackIdNum + "?action=edit&rating=" + rangeOffset + "&comment=" + commentsInputToUpdate;
			var bss_updateRating = {
							url: requestPath, 
		                	preventCache:true,
		                	handleAs: "json"
		    				}
			var bss_updateRatingXhr = dojo.xhrPost(bss_updateRating);
			
			bss_updateRatingXhr.addCallback(function(_jsonObject) {
				dojo.style("rcOverlay","display","none");
				dojo.byId("ratingSuccessMsg").innerHTML = '<spring:message code="message.show.aftersubmit.rating"/>';
		       	dijit.byId("ratingSuccessId").show();
			});
		}
		function showMoreData(arrow,divContainer,link){
			if(dojo.byId(link).innerHTML == "More"){
				if(dojo.byId(divContainer + '_rest').innerHTML == ""){
					toggleArrowImage(arrow,divContainer+'_more',link);
				}
				else if(dojo.byId(divContainer + '_rest').innerHTML != "" && dojo.style(divContainer + '_more','display')=="none"){
					toggleArrowImage(arrow,divContainer+'_more',link,'rest');
				}
				else{
					toggleArrowImage(arrow,divContainer+'_rest',link);
				}
			}
			else{
				if(dojo.style(divContainer + '_rest',"display") != "none"){
					toggleArrowImage(arrow,divContainer+'_rest',link);
				}
				toggleArrowImage(arrow,divContainer+'_more',link);
			}
		}
		function onOverlayHide(){
			var feedbackRate = dijit.byId("feedbackRating");
			if(countAvg != feedbackRate.value){			
				dijit.byId("feedbackRating").setValue(countAvg);
				dojo.attr(dijit.byId("feedbackRating").totalRatingsSpan,"innerHTML",
					"(" + getRatingLabel(countAvg)+")");
				dijit.byId("feedbackRating")._renderStars(countAvg);
			}
		}
</script>
<div dojoType="xwt.widget.quickview.QuickView" id="rcOverlay" title="Rate Bug Description"
	style="display:none;" onHide="onOverlayHide">
	<div style="margin-bottom:12px;height:22px">
		<div style="display:inline-block;padding-right:15px" class="bodytext"><spring:message code="rate.label.contentrating" /></div>
		<div style="display:inline-block;" onMouseOut="onContentRatingMOut();">
			<div style="display:inline-block;padding:0px;width:190px;" 
				id="contentRatingOvl" dojoType="xwt.widget.form.Rating" 
				numStars="5"
				onChange="contentRatingChanged" onMouseOver="onContentRatingMOver"></div>
		</div>
	</div>
	<div>
		<div class="bodytext" style="margin-bottom:5px;height:15px;">
			<div style="float:left"><spring:message code="rate.label.comment"/></div>
			<div style="float:right"><spring:message code="rate.label.charlimit"/></div>
		</div>
		<textarea id="bss_ratingComment" name="bss_ratingComment" wrap="soft" class="bssRatingCommentText" 
			onKeyDown="chracterCount(document.getElementById('bss_ratingComment'));" 
			onKeyUp="chracterCount(document.getElementById('bss_ratingComment'));"></textarea>
	</div>
	<div style="margin-top:10px">
		<input id="ratingSubmit" class="submitRating" dojoType="xwt.widget.form.TextButton" type="submit"
						value="<spring:message code="rate.button.submit"/>" onClick="checkLabelOfSubmit();" label="Submit"></input>
	</div>
</div> 
<div dojoType="xwt.widget.quickview.QuickView" id="creditOverlay" title="" style="display:none;">
	<!-- 	style="margin-left:-10px;margin-top:25px;padding-left:0 !important;border:1px solid #C2C2C2;display:none;">
	<div style="background-color: #FFFFAA;padding: 8px;"> -->
	<div>
		<c:if test='${bugDetail.visible}'>
			<spring:message code="bugdetail.credit.allusers" />(<a onclick="dijit.byId('creditOverlay').hide();" href="http://cdetsweb-prd.cisco.com/apps/dumpcr?identifier=${bugDetail.bugId}" target="_blank"><spring:message code="bugdetail.credit.viewdetails" /></a>)
		</c:if>
		<c:if test='${!bugDetail.visible}'>
			<spring:message code="bugdetail.credit.employee" />(<a onclick="dijit.byId('creditOverlay').hide();" href="http://cdetsweb-prd.cisco.com/apps/dumpcr?identifier=${bugDetail.bugId}" target="_blank"><spring:message code="bugdetail.credit.viewdetails" /></a>)
		</c:if>
		
	</div>
</div>
<div dojoType="dijit.Dialog" id="ratingSuccessId" 
	style="outline:none !important;height:100px;width:625px;display:none;" title="<spring:message code="message.show.title.ratingconfirmation" />">
		<div class="showAlertImage" style="margin:0 10px;float:left;"></div>
		<div id="ratingSuccessMsg" style="float:left;margin:0"></div>
</div>
<div style="margin:20px;">
	<!-- Breadcrumb -->
	<div id="breadcrumbContainer">
		<div id="breadcrumb" nested="false" dojoType="xwt.widget.layout.Breadcrumb" jsId="breadcrumb" class="xwtBreadcrumb"
			breadcrumbItems="{items:[
			<c:if test='${pid == null}'>
			{label:'<spring:message code="searchpage.button.text.searchhome"/>', destination:'${searchPageBread}'},
				<c:if test='${isSearchResultNav != false}'>
				{label:'<spring:message code="breadcrumb.searchresults" />',destination:'${resultPageBread}'},
				</c:if>
			</c:if>
			<c:if test='${pid != null}'>
				{label:'<spring:message code="breadcrumb.pid" />${pid}',destination:'${resultPageBread}'},
			</c:if>
			{label:'<spring:message code="breadcrumb.bugdetail" />',htmlText:'<spring:message code="breadcrumb.bugdetail" />'}
			]}" spacerString="&nbsp;&nbsp;">
		</div>
		<c:if test="${sessionScope.USER_SESSION_BEAN.USER_TYPE != 'GUEST'}">
			<div id="saveImage" class="saveImage" onClick="showSaveBug()" title="Save and watch this bug"> </div>
		</c:if>
	</div>
	<!-- Breadcrumb End -->
	
	<!--Info Banner-->
	<c:if test="${ bugDetail.duplicateBugMessage != ''}">
		<div class="duplicateBugMessageBar">
			<div class="duplicateBug"></div>
			<div class="duplicateBugText">
				<span>${bugDetail.duplicateBugMessage}</span>
			</div>
		</div>
	</c:if>
	<!--Info Banner End-->
	
	<!--Status Banner-->
	<div class="statusBannerBar">
		<div id="creditIcon"
			onMouseOver='dojo.style("creditOverlay","display","block");dijit.byId("creditOverlay").openAtNode(dojo.byId("creditIcon"));'>
		</div>
		<div class="statusBannerText" >
			${bugDetail.bugId}&nbsp;- ${bugDetail.headLine}
		</div>
	</div>
	<!--Status Banner End-->
	
	
	<!--Description-->
		<div style="width:100%;height:30px;">
			<div style="display:inline-block;vertical-align: top;" id="desArrImg" class="arrowImageDown" onclick="toggleArrowImage('desArrImg','desContent');"></div>
			<div style="display:inline-block;padding-bottom:5px;width:880px;" class="xltext divider"><spring:message code="bugdetail.label.description" /></div>
		</div>
	
		<div id="desContent" cellpadding="0" style="margin: 15px 0 30px; width: 100%;display:none;" cellspacing="0">
			<div style="display:inline-block;float:left;width:580px;margin-right:50px;">
				<div id="bugInfoSubDetail"></div>
				<div style="margin-left:20px;height:16px;display:inline-block;">
					<div style="float:left;display:inline-block;padding-right:8px" class="bodytextbold"><spring:message code="rate.label.description.helpful" /></div>
					<div id="feedbackRatingC" style="display:inline-block;" onMouseOut="onFeedbackRatingMOut();">
						<div style="float:left;display:inline-block;padding:0px;" 
							id="feedbackRating" dojoType="xwt.widget.form.Rating" 
							numStars="5"
							onChange="showRatingComments" onMouseOver="onContentRatingMOver"></div>
					</div>
				</div>
			</div>
			<div style="display:inline-block;float:right;height:16px;">
				<div style="display:inline-block;padding-right:8px;vertical-align: top;" class="bodytextbold"><spring:message code="bugdetail.label.contentrating" />:</div>
				<div id="contentRatingC"></div>
			</div>	
		</div>
	<!--Description End-->
	<!--Details-->
		<div style="width:100%;height:30px">
			<div style="display:inline-block;vertical-align: top;" id="detArrImg" class="arrowImageDown" onclick="toggleArrowImage('detArrImg','detContent');"></div>
			<div style="display:inline-block;width:880px;padding-bottom:5px" class="xltext divider"><spring:message code="bugdetail.label.details" /></div>
		</div>
		<div id="detContent" style="margin:15px 0 30px 20px;">
			<div id="detContent_1" style="display:none;margin-right:75px;vertical-align:top;width:210px;">
				<div id="FirstFoundDiv" style="margin-bottom:10px;display:none;width:210px;">
					<div id="FirstFoundDiv_label" style="margin-bottom:5px" class="bodytextbold">
						<spring:message code="bugdetail.label.1stfoundin" />:
					</div>
					<div id="FirstFoundDiv_data"></div>
					<div style="margin-bottom:5px;" id="FirstFoundDiv_more"></div>
					<div style="margin-bottom:5px;" id="FirstFoundDiv_rest"></div>
					<div>
						<div style="width:60px;display:none" id="FirstFoundDiv_arrowMore" 
							onclick="showMoreData('ffmorearrowMore','FirstFoundDiv','ffmorelinkMore');">
							<div style="vertical-align:top;display:inline-block" id="ffmorearrowMore" class="arrowImageSmallDown"></div>
							<div id="ffmorelinkMore" class="link" style="display:inline-block"><spring:message code="bugdetail.label.more" /></div>
						</div>
						<div style="width:60px;display:none" id="FirstFoundDiv_arrowLess" 
							onclick="showMoreData('ffmorearrowLess','FirstFoundDiv','ffmorelinkLess');">
							<div style="vertical-align:top;display:inline-block" id="ffmorearrowLess" class="arrowImageSmallTop"></div>
							<div id="ffmorelinkLess" class="link" style="display:inline-block"><spring:message code="bugdetail.label.less" /></div>
						</div>
					</div>
				</div>
				<div id="StatusDiv" style="margin-bottom:10px;display:none">
					<div style="display:inline-block;margin-right:5px" class="bodytextbold"><spring:message code="bugdetail.label.status" />:</div>
					<div style="display:inline-block" class="bodytext"><c:out value="${bugDetail.statusName}" /></div>
				</div>
				<div id="LastModifiedDiv" style="margin-bottom:10px;display:none">
					<div style="display:inline-block;margin-right:5px" class="bodytextbold"><spring:message code="bugdetail.label.lastmodified" />:</div>
					<div style="display:inline-block" class="bodytext"><c:out value="${bugDetail.bugLastModifiedDate}" /></div>
				</div>
			</div>
			<div id="detContent_2" style="display:none;margin-right:75px;vertical-align:top;width:290px;">
				<div id="KnownAffVerDiv" style="margin-bottom:10px;display:none;max-width:290px;">
					<div id="KnownAffVerDiv_label" style="margin-bottom:5px" class="bodytextbold">
						<spring:message code="bugdetail.label.knownaffversions" />:
					</div>
					<div id="KnownAffVerDiv_data"></div>
					<div style="margin-bottom:5px;" id="KnownAffVerDiv_more"></div>
					<div style="margin-bottom:5px;" id="KnownAffVerDiv_rest"></div>
					<div>
						<div style="width:60px;display:none" id="KnownAffVerDiv_arrowMore" 
							onclick="showMoreData('kavmorearrowMore','KnownAffVerDiv','kavmorelinkMore');">
							<div style="vertical-align:top;display:inline-block" id="kavmorearrowMore" class="arrowImageSmallDown"></div>
							<div id="kavmorelinkMore" class="link" style="display:inline-block"><spring:message code="bugdetail.label.more" /></div>
						</div>
						<div style="width:60px;display:none" id="KnownAffVerDiv_arrowLess" 
							onclick="showMoreData('kavmorearrowLess','KnownAffVerDiv','kavmorelinkLess');">
							<div style="vertical-align:top;display:inline-block" id="kavmorearrowLess" class="arrowImageSmallTop"></div>
							<div id="kavmorelinkLess" class="link" style="display:inline-block"><spring:message code="bugdetail.label.less" /></div>
						</div>
					</div>
				</div>
				
				<div id="FixedInDiv" style="margin-bottom:10px;display:none;width:290px;">
					<div id="FixedInDiv_label" style="margin-bottom:5px" class="bodytextbold">
						<spring:message code="bugdetail.label.fixedin" />:
					</div>
					<div id="FixedInDiv_data"></div>
					<div style="margin-bottom:5px;" id="FixedInDiv_more"></div>
					<div style="margin-bottom:5px;" id="FixedInDiv_rest"></div>
					<div>
						<div style="width:60px;display:none" id="FixedInDiv_arrowMore" 
							onclick="showMoreData('fimorearrowMore','FixedInDiv','fimorelinkMore');">
							<div style="vertical-align:top;display:inline-block" id="fimorearrowMore" class="arrowImageSmallDown"></div>
							<div id="fimorelinkMore" class="link" style="display:inline-block"><spring:message code="bugdetail.label.more" /></div>
						</div>
						<div style="width:60px;display:none" id="FixedInDiv_arrowLess" 
							onclick="showMoreData('fimorearrowLess','FixedInDiv','fimorelinkLess');">
							<div style="vertical-align:top;display:inline-block" id="fimorearrowLess" class="arrowImageSmallTop"></div>
							<div id="fimorelinkLess" class="link" style="display:inline-block"><spring:message code="bugdetail.label.less" /></div>
						</div>
					</div>
				</div>
			</div>
			<div id="detContent_3" style="display:none;vertical-align:top;width:210px;">
				<div id="ProductDiv" style="margin-bottom:10px;display:none">
					<div style="display:inline-block;margin-right:5px" class="bodytextbold"><spring:message code="bugdetail.label.product" />:</div>
					<div style="display:inline-block" class="bodytext"><c:out value="${bugDetail.productSoftwareFamily}" /></div>
				</div>
				<div id="PlatformDiv" style="margin-bottom:10px;display:none">
					<div style="display:inline-block;margin-right:5px" class="bodytextbold"><spring:message code="bugdetail.label.platform" />:</div>
					<div style="display:inline-block" class="bodytext"><c:out value="${bugDetail.platformName}" /></div>
				</div>
				<div id="SeverityDiv" style="margin-bottom:10px;display:none">
					<div style="display:inline-block;margin-right:5px" class="bodytextbold"><spring:message code="bugdetail.label.severity" />:</div>
					<div style="display:inline-block" class="bodytext"><c:out value="${bugDetail.severityCode}" /></div>
				</div>
				<div id="CustRepDiv" style="margin-bottom:10px;display:none;width:210px;">
					<div id="CustRepDiv_label" style="margin-bottom:5px" class="bodytextbold">
						<spring:message code="bugdetail.label.associatedsr" />:
					</div>
					<div id="CustRepDiv_data"></div>
					<div style="margin-bottom:5px;" id="CustRepDiv_more"></div>
					<div style="margin-bottom:5px;" id="CustRepDiv_rest"></div>
					<div>
						<div style="width:60px;display:none" id="CustRepDiv_arrowMore" 
							onclick="showMoreData('crmorearrowMore','CustRepDiv','crmorelinkMore');">
							<div style="vertical-align:top;display:inline-block" id="crmorearrowMore" class="arrowImageSmallDown"></div>
							<div id="crmorelinkMore" class="link" style="display:inline-block"><spring:message code="bugdetail.label.more" /></div>
						</div>
						<div style="width:60px;display:none" id="CustRepDiv_arrowLess" 
							onclick="showMoreData('crmorearrowLess','CustRepDiv','crmorelinkLess');">
							<div style="vertical-align:top;display:inline-block" id="crmorearrowLess" class="arrowImageSmallTop"></div>
							<div id="crmorelinkLess" class="link" style="display:inline-block"><spring:message code="bugdetail.label.less" /></div>
						</div>
					</div>
				</div>
			</div>		
		</div>
		<!--Details End-->
		
		<!--Related Bugs-->
		<div id="relatedBugsContainer" style="display:none">
			<div style="width:100%;height:30px">
				<div style="display:inline-block;vertical-align: top;" id="rbArrImg"
					class="arrowImageDown" onclick="toggleArrowImage('rbArrImg','rbContent');"></div>
				<div style="display:inline-block;width:880px;padding-bottom:5px" class="xltext divider"><spring:message code="bugdetail.label.relatedbug" /></div>
			</div>
			<div id="rbContent" style="margin:15px 0 10px 20px;display:inline-block;">
				<c:forEach items="${bugDetail.relatedBugList}" var="relatedBug">
					<div style="margin:0 0 20px;"> 
						<div class="rbTitle" style="margin:0 0 12px">
							${relatedBug.bugId} : ${relatedBug.headLine}
						</div>
						<div style="margin:12px 0 0 20px" class="bodytext">
							${relatedBug.releaseNoteText}
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
		<!--Related Bugs End-->

		<!--Rating Distribution and Comments-->
		<div id="ratingDistContainer" style="display:none">
			<div style="width:100%;height:30px">
				<div style="display:inline-block;vertical-align: top;" id="rdcArrImg"
					class="arrowImageDown" onclick="toggleArrowImage('rdcArrImg','rdcContent');"></div>
				<div style="display:inline-block;width:880px;padding-bottom:5px" class="xltext divider"><spring:message code="bugdetail.label.ratingdistributioncomments" /></div>
			</div>
			<div id="rdcContent" style="margin:15px 0 30px 20px;">
				<div id="TotalRatingHead" class="bodytextbold" style="font-size:13px;margin-bottom:15px"></div>
				<div id="TotalRatingSubHead" class="bodytextbold" style="font-size:11px"></div>
				<div id="RatingChartContainer" style="max-width:400px;margin-bottom:20px"></div>
				<div id="ratingDistribution_comments" style="" class="rdsummary"></div>
				<div id="ratingDistribution_more" style="" class="rdsummary"></div>
				<div style="width:60px;display:none" id="ratingDistribution_arrowM" onclick="fetchMoreComments();">
					<div style="vertical-align:top;display:inline-block" id="rdmorearrow" class="arrowImageSmallDown"></div>
					<div id="rdmorelink" class="link" style="display:inline-block">More</div>
				</div>
				<div style="width:60px;display:none" id="ratingDistribution_arrowL" onclick="hideMoreComments();">
					<div style="vertical-align:top;display:inline-block" id="rdLessarrow" class="arrowImageSmallTop"></div>
					<div id="rdlesslink" class="link" style="display:inline-block">Less</div>
				</div>
			</div>
		</div>
		<!--Rating Distribution and Comments End-->
</div>

<div id="saveBug" style="outline:none !important;display:none;" title="<spring:message code="savedialog.label.savebug" />" dojoType="dijit.Dialog" class="masterbrand">
	<jsp:include page="saveDialog.jsp" flush="true"/>
	<div class="dijitDialogPaneActionBar">
		<button id="saveButtonId" onClick="submitSaveBug(); return false;" dojoType="xwt.widget.form.TextButton"	
			jsId="saveButtonId"><spring:message code="savedialog.label.savebug" /></button>
	</div>
</div>