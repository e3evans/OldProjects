<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/portlet" prefix="portlet"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@page import="javax.portlet.PortletRequest"%>

<%	
	PortletRequest pResquest = (PortletRequest)request;
	pageContext.setAttribute("pid", pResquest.getPortletSession().getAttribute("pid"), PageContext.REQUEST_SCOPE);
	pageContext.setAttribute("searchCriteria", pResquest.getPortletSession().getAttribute("searchCriteria"), PageContext.REQUEST_SCOPE);
	pageContext.setAttribute("isAdvSearchPage", pResquest.getPortletSession().getAttribute("isAdvSearchPage"), PageContext.REQUEST_SCOPE);
	pageContext.setAttribute("searchObject", pResquest.getPortletSession().getAttribute("searchObject"), PageContext.REQUEST_SCOPE);
	pageContext.setAttribute("isFilter", pResquest.getPortletSession().getAttribute("isFilter"), PageContext.REQUEST_SCOPE);
	pageContext.setAttribute("filterJSON", pResquest.getPortletSession().getAttribute("filterJSON"), PageContext.REQUEST_SCOPE);
	pageContext.setAttribute("bssSearchType", pResquest.getPortletSession().getAttribute("bssSearchType"), PageContext.REQUEST_SCOPE);
%>

<link rel="stylesheet" type="text/css" href="<c:out value='${requestPath}'/>/web/fw/tools/bss/xmproot/xwt/themes/masterbrand/dojox/ellipsis.css" />
<style type="text/css">
.masterbrand .xwtErrorMessage .xwtErrorMessageWrapper {
    font: 12px Arial,Helvetica !important;
}
.xwtErrorMessage .xwtErrorMessageText {
    color: #E9102A !important;
    margin-top:-11px !important;
}
.xwtErrorMessage {
    margin-left: 0px !important;
}
.xwtErrorMessage .xwtErrorMessageWrapper {
    padding-top: 15px !important;
}
#output {
      margin: 20px auto;
      border: 1px solid #8499a2;
}
.table-head-node-container, .table-container, #table{
	width:896px !important;
}
.table-container{
	height:549px !important;
}
.dijitSelectMenu{
	max-height: 150px !important;
	overflow-y:auto !important;
	overflow-x:hidden !important;
	padding:1px;
}
</style>

<portlet:defineObjects />
<portlet:actionURL var="viewBugDetailAction">
	<portlet:param name="action" value="viewBugDetailAction" />
</portlet:actionURL>

<portlet:renderURL var="searchPageBread">
	<portlet:param name="view" value="breadcrum"/>
	<portlet:param name="page" value="searchPage"/>
	<portlet:param name="rfTag" value="resultPage"/>
</portlet:renderURL>

<portlet:actionURL var="searchAction">
	<portlet:param name="action" value="keywordsSearchAction" />
	<portlet:param name="isResultPage" value="true" />
</portlet:actionURL>
<portlet:actionURL var="bugByIdAction">
	<portlet:param name="action" value="bugByIdAction" />
	<portlet:param name="isResultPage" value="true" />
</portlet:actionURL>
<portlet:actionURL var="multipleBugSearchAction">
	<portlet:param name="action" value="multipleBugSearchAction" />
	<portlet:param name="isResultPage" value="true" />
</portlet:actionURL>

<script type="text/javascript">
	var isSaveSearch = true;
	var saveSearchParam = "";
	var filterBlmd = "";
	var incBugsFilter = "";
	var disableEmailNotification = false;
	var totalRowExport = <spring:message code="excel.totalrecords" />;
	var userId = '<c:out value="${sessionScope.USER_SESSION_BEAN.USER}" />';
 	var customUrl = '<c:url value="${REQUEST_PATH_PROXY}" />';
 	var tempCustomUrl = customUrl;
	var foOfUrl = "";
	var quickFilters = [
			<c:if test="${isFilter}"> 
				${filterJSON}
			</c:if>
		   ];
 	var totalRecord = "";
 	bugIdInMore = "";
 	var sortFlag = '<c:out value="${sessionScope.USER_SESSION_BEAN.SEARCH_RESULT_SORTABLE}"/>';
 	
 	dojo.provide("ComboBoxReadStore");
	dojo.declare("ComboBoxReadStore", dojox.data.QueryReadStore, {
	_filterResponse: function(data){
		if(data["suggestions"] != null){
     		data["identifier"] = "name";
     		data["items"] = data["suggestions"];
     	}
     	return data;
     },
	fetch:function(request) {				
		var lookupKey = dojo.string.trim(autoSuggest.textbox.value);
		if(lookupKey.length > 0){
			searchButton.attr("disabled",false);
		}
		else{
			searchButton.attr("disabled",true);
		}
		//& allowing service call from 3 char onwards
		if(lookupKey.length < 3){	
			return;
		}
		else{
			//form custom url
			this.url = "<c:url value='/service/requestproxy/get'/>"+"/bss/searchterms/"+lookupKey;
		}
		//all the query parameters
		request.serverQuery = {st:"autocomplete"};
		return this.inherited("fetch", arguments);
	}
	});
	
	dojo.extend(xwt.widget.table.Table,{
		_onHeaderClick: function(e){
			//this.log("onHeaderClick(): ", arguments, this.sortAttributes)
			var column = this._columns.getByNode(e.target);
			if(!column){
				return;
			}
	
			if(this._sortable){
				var direction = column.sorted;
				if(!direction && column.attr != "statusGroup" && column.attr != "bugId" ||direction=='ascending'){
					this.sort(column, 'descending');
				}else{
					this.sort(column, 'ascending');
				}
			}
		}
	});

 	dojo.extend(xwt.widget.table.FilteringToolbar, {
		<c:if test="${isFilter}"> 
		_disable:function(disable){
			console.log("disabling filterbar");
			//disable all the filtering elements and buttons
			var filterToolbarDomNode = this.domNode;
			var q = dojo.query(".xwtFilterWidget", filterToolbarDomNode);

			dojo.query(".xwtFilterWidget", filterToolbarDomNode).forEach(function(node) {
				var x = dijit.findWidgets(node);
				for (i = 0; i < x.length; i++){
					x[i].attr("disabled", disable);
				}
				if(!disable && !node.nextElementSibling && !node.previousElementSibling){
					x[3].attr("disabled", true);
				}
			});
			
			this.goButton.attr("disabled", disable);
			this.clearButton.attr("disabled", disable);
			this.saveButton.attr("disabled", disable);
		},
		</c:if>
		onStartup: function(){
			dojo.publish(this.id + "-startup", [ this ]);
			this.clearButton.attr("label", "Remove Filters");
		},
		_clearFilters: function(){
			dojo.forEach(this.getChildren(), function(child){
				child.destroyRecursive();
			});
			this.saveButton.attr('disabled', true);
			 customUrl = tempCustomUrl;
			 saveSearchParam = "";
		},
		_onGo:function(){
			if(!this.table()){ return; }
			foOfUrl = "";
			var f = this.getFilter();
			this.table().filter(f);
			this.clearButton.attr('disabled', false);
			if (this.disableManageFilters === false){
				this.saveButton.attr('disabled', false);
			}else{
				this.saveButton.attr('disabled', true);
			}
		}
	});
  	
  	
  	<c:if test="${!isFilter}"> 
	//ContextualToolbar used when no filter need to apply otherwise use FilteringToolbar
	dojo.extend(xwt.widget.table.ContextualToolbar,	{
		_onQuickFilterChange: function(value){
			value = '<spring:message code="filter.label.advancedfilter" />';
			//xwt.widget.table.ContextualToolbar.prototype._onQuickFilterChange2.apply(this);
			fb = this._filterButton;
			if (this.table()._currentFilter){
				this.table().clearFilter();
			}
			this._lastQuickFilter = value;
			if (fb) {
				fb._hideFilterBar();
				fb._showAdvancedToolbar();
			}
		}
	});
	</c:if>
	
		<c:if test="${isFilter}"> 
		dojo.extend(xwt.widget.table.ContextualToolbar,{
			_onFilterButtonClick: function(value){
				if(!this.table()){ return; }
				//	Find our FilterButton in our children.
				var c = this.getChildren(), fb = this._filterButton;

				var value = this.quickFilterSelect.attr("value");

				//this._lastQuickFilter = value;
				var closed = true, filtered=false;
				//	Get the filter and apply it.
				var f = this.table()._filterEngine.get(value);
				this._lastQuickFilter = value;
				filtered = true;
				//show the filter criteria
				if (fb.isShown) {
					fb._hideFilterBar();
					closed = true;
				}
				else {
					closed = false;
					fb._showAdvancedToolbar(f);
					if (!f.editable) {
						this.filterToolbar._disable(true);
					}
				}
				this._setFilterButtonState(closed, filtered);
			}
		});
	</c:if>
	
    dojo.provide("searchResultReadStore");
	dojo.declare("searchResultReadStore", dojox.data.QueryReadStore, {
	    _filterResponse: function(data){
	     data["identifier"] = "bugId";
	     if(data["bugSearchResults"]){
	     	 data["items"] = data["bugSearchResults"];
	     	 data["numRows"] = Number(data["totalNumberOfResults"]);
	     	 totalRecord = data["totalNumberOfResults"];
	     }else if(data["bugDetailResponse"]){
	    	data["items"] = data["bugDetailResponse"];
	     	data["numRows"] = "1";
	     	totalRecord = "1";
	     }else{
	     	data["numRows"] = "0";
	     	totalRecord = "0";
	     	<c:if test="${sessionScope.USER_SESSION_BEAN.EXPORT_TO_EXCEL}">
	     		dijit.byId("btnExportToExcel").attr("disabled", true);
	     	</c:if>
	     }
	     if(totalRecord == undefined){
	     	totalRecord = 0;
	     }
	     <c:if test="${sessionScope.USER_SESSION_BEAN.USER_TYPE != 'GUEST' && pid == null}" >
	     if(totalRecord != undefined && totalRecord == 0){
	     	dijit.byId("saveSearchPopButtonId").attr("disabled", true);
	     }else{
	     	dijit.byId("saveSearchPopButtonId").attr("disabled", false);
	     	if(totalRecord > totalRowExport){
	    		document.getElementById("emailNofificationId").style.display = "block";
	     		dijit.byId("yemail_ng").disabled = true;
	     		disableEmailNotification = true;
	     	}else{
	    		document.getElementById("emailNofificationId").style.display = "none";
	     		dijit.byId("yemail_ng").disabled = false;
	     		disableEmailNotification = false;
	     	}
	     }
	     </c:if>
	     <c:if test="${sessionScope.USER_SESSION_BEAN.EXPORT_TO_EXCEL}">
	     if(totalRecord > totalRowExport || totalRecord == 0){
	     	dijit.byId("btnExportToExcel").attr("disabled", true);
	     }else{
	     	dijit.byId("btnExportToExcel").attr("disabled", false);
	     }
	     </c:if>
	     document.getElementById("recordId").innerHTML = totalRecord;
	     return data;
	     },
	     fetch:function(/* Object? */ request){
			request.serverQuery = {
				<c:if test="${isAdvSearchPage}">
				sort:"severityCode:asc",
				</c:if>
				rpp:"25"};
			request = request || {};
			if(!request.store){
				request.store = this;
			}
			var self = this;
		
			var _errorHandler = function(errorData, requestObject){
				if(requestObject.onError){
					var scope = requestObject.scope || dojo.global;
					requestObject.onError.call(scope, errorData, requestObject);
				}
			};
		
			var _fetchHandler = function(items, requestObject, numRows){
				var oldAbortFunction = requestObject.abort || null;
				var aborted = false;
				
				var startIndex = requestObject.pageNum?requestObject.pageNum:0;
				if(self.doClientPaging == false){
					// For client paging we dont need no slicing of the result.
					startIndex = 0;
				}
				var endIndex = requestObject.rpp?(startIndex + requestObject.rpp):items.length;
		
				requestObject.abort = function(){
					aborted = true;
					if(oldAbortFunction){
						oldAbortFunction.call(requestObject);
					}
				};
		
				var scope = requestObject.scope || dojo.global;
				if(!requestObject.store){
					requestObject.store = self;
				}
				if(requestObject.onBegin){
					requestObject.onBegin.call(scope, numRows, requestObject);
				}
				if(requestObject.sort && self.doClientSorting){
					items.sort(dojo.data.util.sorter.createSortFunction(requestObject.sort, self));
				}
				if(requestObject.onItem){
					for(var i = startIndex; (i < items.length) && (i < endIndex); ++i){
						var item = items[i];
						if(!aborted){
							requestObject.onItem.call(scope, item, requestObject);
						}
					}
				}
				if(requestObject.onComplete && !aborted){
					var subset = null;
					if(!requestObject.onItem){
						subset = items.slice(startIndex, endIndex);
					}
					requestObject.onComplete.call(scope, subset, requestObject);
				}
			};
			this._fetchItems(request, _fetchHandler, _errorHandler);
			return request;	// Object
		},
		_fetchItems: function(request, fetchHandler, errorHandler){
			var serverQuery = request.serverQuery || request.query || {};
			//Need to add start and count
			if(!this.doClientPaging){
				serverQuery.pageNum = request.pageNum || 0;
				// Count might not be sent if not given.
				if(request.rpp){
					serverQuery.rpp = request.rpp;
				}
			}
			if(!this.doClientSorting){
				if(request.sort){
					var sort = request.sort[0];
					if(sort && sort.attribute){
						var sortStr = sort.attribute;
						if(sort.descending){
							if(sortStr != "severityCode"){
								sortStr = sortStr+":desc";
								}
						}else{
							if(sortStr == "severityCode"){
								sortStr = sortStr+":desc";
							}
						}
						serverQuery.sort = sortStr;
					}
				}
			}
			if(this.doClientPaging && this._lastServerQuery !== null &&
				dojo.toJson(serverQuery) == dojo.toJson(this._lastServerQuery)
				){
				this._numRows = (this._numRows === -1) ? this._items.length : this._numRows;
				fetchHandler(this._items, request, this._numRows);
			}else{
				var xhrFunc = this.requestMethod.toLowerCase() == "post" ? dojo.xhrPost : dojo.xhrGet;
				if(foOfUrl && customUrl.indexOf(foOfUrl)==-1 && table._currentFilter){
					customUrl +=foOfUrl;
				}
				customUrl = customUrl.replace(/&amp;/g, "&");
				var xhrHandler = xhrFunc({
					//url:this.url,
					url:customUrl,
					handleAs:"json",
					content:serverQuery});
				xhrHandler.addCallback(dojo.hitch(this, function(data){
					this._xhrFetchHandler(data, request, fetchHandler, errorHandler);
				}));
				xhrHandler.addErrback(function(error){
					errorHandler(error, request);
				});
				this.lastRequestHash = new Date().getTime()+"-"+String(Math.random()).substring(2);
				this._lastServerQuery = dojo.mixin({}, serverQuery);
			}
		}
   });
   
   //This widget is to be defined by the application
dojo.declare("ExpandableWidget", [ dijit._Widget, dijit._Templated, xwt.widget.table.EditorMixin ], {
	templatePath: dojo.moduleUrl("dojo", "../../custom/template/TableXWTDetailsWidget.html"),
	widgetsInTemplate: true,

	postCreate: function(){
		this.inherited(arguments);
	},

	setValues: function(obj){
	
		var releaseNotes = String(obj["releaseNoteText"]);		
		releaseNotes = releaseNotes.replace(/\n/g, "<br/>");
		
		//Service Request only for employee
		
var srNumbers = "";
		<c:if test="${sessionScope.USER_SESSION_BEAN.USER_TYPE == 'EMPLOYEE'}">
		var srArray = new Array();
		srNumbers = String(obj["srNumbersAssociatedTo"]);
		if(srNumbers == "undefined"){
			srNumbers = "";
		}else{
			srArray = srNumbers.split(' ');
			var srCount = srArray.length;
			if(srCount > 5){
				bugIdInMore = obj["bugId"].toString();
				srNumbers = "";
				for(var i=0;i<5;i++){
		srNumbers = srNumbers +'<a href="http://wwwin-tools.cisco.com/casekwery/getServiceRequest.do?id='+srArray[i]+'" target="_blank">'+srArray[i]+'</a>&nbsp<br/>';
			         }
		 srNumbers= srNumbers + '<a href="javascript:viewMore();">more</a>';
		         }
		          else {
			 srNumbers="";
			 for(var j=0;j<srCount;j++){
				srNumbers = srNumbers +'<a href="http://wwwin-tools.cisco.com/casekwery/getServiceRequest.do?id='+srArray[j]+'" target="_blank">'+srArray[j]+'</a>&nbsp<br/>';
			}
		
		}
		}
	</c:if>
		var fixedIn = String(obj["fixedInVerssions"]);
		//<b>Fixed in:</b><br>
		if(fixedIn == "undefined")
			fixedIn = "";
		else
			fixedIn = '<b><spring:message code="searchresultpage.label.fixedin" />:</b><br/>' + fixedIn.replace(/, /g,"<br/>"); 

		this.bugDescription.setValue(obj["headLine"]);
		this.releaseNotes.setValue(releaseNotes);
		this.serviceRequests.setValue(srNumbers);
		this.bugFixedIn.setValue(fixedIn);
	}
});
dojo.extend(xwt.widget.table.Filters,{
	toDojoQuery: function(/* xwt.widget.table.__FilterExpression[] */expressions, /*filtering on server */server){
	var q = "";
	var keyFlag = true;
	var statusFlag = true;
	var actualStatusFlag = true;
	var techFlag = true;
	var severityFlag = true;
	var dateFlag = true;
	var isFoReq = true;
	var beforeDateFlag = false;
	var isCommaReq = false;
	var keyQuery = "";
	var statusQuery = "";
	var actualStatusQuery = "";
	var severityQuery = "";
	var dateQuery = "";
	var techQuery = "";
	
	var keyParam = "";
	var statusParam = "";
	var actualStatusParam = "";
	var severityParam = "";
	var dateParam = "";
	var techParam = "";
	
	var incBugs = "";
	var formQuery = function(q, attr, val){
		// summary:
		//		Internal function to add to the query object, or make it multivalued
		//		if necessary.
		// q: String
		//		The url String.
		// attr: String
		//		The attribute in the query to update.
		// val: String
		//		The value to insert.
		if(isFoReq && attr != "includeBugs"){
			customUrl += "&fo=";
			isFoReq = false;
		}
		if(attr == "keyword"){
			val = val.replace(/&/g, "&amp;").replace(/#/g, "%23");
			if(keyFlag){
				if(isCommaReq){
					keyQuery += ",kw:"+val.replace(/&amp;/g, "%26");
				}else{
					keyQuery += "kw:"+val.replace(/&amp;/g, "%26");
					isCommaReq = true;
				}
				keyParam += "&kw="+val;
				keyFlag = false;
			}else{
				keyQuery += "|"+val;
				keyParam += ","+val; 
			}
		}
		if(attr == "statusGroup") {
			if (val =='<spring:message code="filter.label.status.open" />' 
			|| val =='<spring:message code="filter.label.status.fixed" />' 
			|| val =='<spring:message code="filter.label.status.terminated" />' 
			|| val =='<spring:message code="filter.label.status.other" />'){
			if(statusFlag){
				if(isCommaReq){
					statusQuery += ",sg:"+val;
				}else{
					statusQuery += "sg:"+val;
					isCommaReq = true;
				}
				statusParam += "&sg="+val;
				statusFlag = false;
			}else{
				statusQuery += "|"+val;
				statusParam += ","+val;
			}
		 }else{
		 	if(val =='<spring:message code="filter.label.status.open.assigned" />'){
		 		val = "A";
		 	}else if(val ==	'<spring:message code="filter.label.status.open.forwarded" />'){
		 		val = "F";
			}else if(val =='<spring:message code="filter.label.status.open.new" />'){
				val = "N";
			}else if(val =='<spring:message code="filter.label.status.fixed.resolved" />'){
				val = "R";
			}else if(val =='<spring:message code="filter.label.status.fixed.verified" />'){
				val = "V";
			}else if(val =='<spring:message code="filter.label.status.terminated.closed" />'){
				val = "C";
			}else if(val =='<spring:message code="filter.label.status.other.duplicate" />'){
				val = "D";
			}else if(val =='<spring:message code="filter.label.status.open.held" />'){
				val = "H";
			}else if(val =='<spring:message code="filter.label.status.open.inforequired" />'){
				val = "I";
			}else if(val =='<spring:message code="filter.label.status.open.more" />'){
				val = "M";
			}else if(val =='<spring:message code="filter.label.status.open.open" />'){
				val = "O";
			}else if(val =='<spring:message code="filter.label.status.open.postponed" />'){
				val = "P";
			}else if(val =='<spring:message code="filter.label.status.open.submitted" />'){
				val = "S";
			}else if(val =='<spring:message code="filter.label.status.open.waiting" />'){
				val = "W";
			}else if(val =='<spring:message code="filter.label.status.terminated.junked" />'){
				val = "J";
			}else if(val =='<spring:message code="filter.label.status.terminated.unreproducible" />'){
				val = "U";
			}
		 	if(actualStatusFlag){
				if(isCommaReq){
					actualStatusQuery += ",as:"+val;
				}else{
					actualStatusQuery += "as:"+val;
					isCommaReq = true;
				}
				actualStatusParam += "&as="+val;
				actualStatusFlag = false;
			}else{
				actualStatusQuery += "|"+val;
				actualStatusParam += ","+val;
			}
		 }
		}
		if(attr == "technology"){
			if(techFlag){
				if(isCommaReq){
					techQuery += ",tn:"+val;
				}else{
					techQuery += "tn:"+val;
					isCommaReq = true;
				}
				techParam += "&tn="+val;
				techFlag = false;
			}else{
				techQuery += "|"+val;
				techParam += ","+val;
			}
		}
		if(attr == "severityCode"){
			if(severityFlag){
				if(isCommaReq){
					severityQuery += ",se:"+val;
				}else{
					severityQuery += "se:"+val;
					isCommaReq = true;
				}
				severityParam += "&se="+val;
				severityFlag = false;
			}else{
				severityQuery += "|"+val;
				severityParam += ","+val;
			}
		}
		if(attr == "includeBugs"){
			if(val == '<spring:message code="filter.label.includebugs.allknown" />' || val == "true"){
				incBugs += "&ib=true";
				incBugsFilter = ",ib:true"; 
			}else if(val == '<spring:message code="filter.label.includebugs.availabletocustomer" />' || val == "2"){
				incBugs += "&acsl=2";
				incBugsFilter = ",ib:2"; 
			}
		}
		if(attr == "modifiedDate"){
			var newDate = "";
			var dateParamVal = "";
			filterBlmd = val;
			if(val == '<spring:message code="filter.label.modifieddate.last7days" />'){
				newDate = dojo.date.add(new Date(), "day", -7);
				dateParamVal = 3;
			}else if(val == '<spring:message code="filter.label.modifieddate.last3months" />'){
				newDate = dojo.date.add(new Date(), "month", -3);
				dateParamVal = 7;
			}else if(val == '<spring:message code="filter.label.modifieddate.last6months" />'){
				newDate = dojo.date.add(new Date(), "month", -6);
				dateParamVal = 8;
			}else if(val == '<spring:message code="filter.label.modifieddate.lastyear" />'){
				newDate = dojo.date.add(new Date(), "year", -1);
				dateParamVal = 6;
			}else if(val == '<spring:message code="filter.label.modifieddate.before1year" />'){
				newDate = dojo.date.add(new Date(), "year", -1);
				dateParamVal = 9;
				beforeDateFlag = true;
			}
			dateParam += "&blmd="+ dateParamVal;
			var modifiedDate = dojo.date.locale.format(newDate, {datePattern: "MM-dd-yyyy", selector: "date"});
			if(dateFlag){
				if(isCommaReq){
					if(beforeDateFlag){
						dateQuery += ",blmd:|"+modifiedDate;
					}else{
						dateQuery += ",blmd:"+modifiedDate;
					}
				}else{
					if(beforeDateFlag){
						dateQuery += "blmd:|"+modifiedDate;
					}else{
						dateQuery += "blmd:"+modifiedDate;
					}
					isCommaReq = true;
				}
				dateFlag = false;
			}else{
				//dateQuery += "|"+val;
			}
		}
		return q;
	}
	customUrl = tempCustomUrl;
	saveSearchParam = "";
	filterBlmd = "";//TODO
	incBugsFilter = "";//TODO
	dojo.forEach(expressions, function(expression){
		var col = expression.attr;
		if(col == "technology" ||
			col == "statusGroup" ||
			col == "severityCode" ||
			col == "includeBugs" ||
			col == "blmd"){
				expression.op = "equals";
		}else if(col == "keyword"){
			expression.op = "contains";
		}
		var fn = expression.op;
		q = formQuery(q, expression.attr, expression.value);
	});
	var foQuery = "";
	var foParam = ""
	if(keyQuery.match(',')){
		foQuery +=keyQuery;
		foParam +=keyParam;
	}else{
		customUrl = customUrl + keyQuery;
		saveSearchParam = saveSearchParam + keyParam;
	}
	if(severityQuery.match(',')){
		foQuery += severityQuery;
		foParam += severityParam;
	}else{
		customUrl = customUrl + severityQuery;
		saveSearchParam = saveSearchParam + severityParam;
	}
	if(statusQuery.match(',')){
		foQuery += statusQuery;
		foParam += statusParam;
	}else{
		customUrl = customUrl + statusQuery;
		saveSearchParam = saveSearchParam + statusParam;
	}
	if(actualStatusQuery.match(',')){
		foQuery += actualStatusQuery;
		foParam += actualStatusParam;
	}else{
		customUrl = customUrl + actualStatusQuery;
		saveSearchParam = saveSearchParam + actualStatusParam;
	}
	if(dateQuery.match(',')){
		foQuery += dateQuery;
		foParam += dateParam;
	}else{
		customUrl = customUrl + dateQuery;
		saveSearchParam = saveSearchParam + dateParam;
	}
	if(techQuery.match(',')){
		foQuery += techQuery;
		foParam += techParam;
	}else{
		customUrl = customUrl + techQuery;
		saveSearchParam = saveSearchParam + techParam;
	}
	customUrl += foQuery + incBugs;
	saveSearchParam += foParam + incBugs;
	return q;
	}
});
dojo.declare("myContentPane", dijit.layout.ContentPane, {
	parseOnLoad: false,
	isContainer:false,
	stopParser: true
	});
//This override used to check Filter data null or not
dojo.extend(xwt.widget.table._FilterWidget,{
	 getFilterExpression:function(){
	  var attr = this.column.attr("value");
	  if(attr != null){
	  	attr = attr.substr(0, attr.indexOf("-"));
	  }
	  return {
	   op: this.operator.attr('value'),
	   attr: attr,
	   value: this.criteria && this.criteria.attr('value')
	  };
	 },
	 
	 postCreate: function(){
		this.inherited("postCreate",arguments);
		this.column.setStore(this.columnStore);
		this.column.emptyLabel = '<spring:message code="filter.label.selectafilter" />';
		this.criteria.textbox.value = '<spring:message code="filter.label.selectafilterattr" />';
	 },
		
	 _setFilterType: function(item){
		 	var id = this.columnStore.getIdentity(item);
			//this.operator.setStore(this.opsStores[id]);
			var disabled = false;
			if(this.criteria){
				disabled = this.criteria.attr("disabled");
				this.criteria.destroyRecursive();
				this.criteria = null;
			}
			if(this.widgetTypeMap[id]){
				var criteria = this.criteria = new this.widgetTypeMap[id]({});
				// See if we're an enum type and if we need to build a store.			
				var type = this.columnStore.getValue(item, "filterType");
				var opts = this.columnStore.getValue(item, "filterOptions");
				if(type == "enumeration" && opts && opts.autoSuggestValues){
					var data = {identifier: "name", label: "name", items: []};
					var i;
					for(i = 0; i < opts.autoSuggestValues.length; i++){
						data.items.push({
							"name": opts.autoSuggestValues[i]
						});
					}
					criteria.searchAttr = "name";
					criteria.store = new dojo.data.ItemFileReadStore({data: data});
				}
				if(opts && ("sortAutoSuggestValues" in opts)){
					if(!opts.sortAutoSuggestValues){
						criteria.sortByLabel = false;
					}
				}
				
				if (disabled) {
					criteria.attr("disabled", disabled);
				}
				criteria.placeAt(this.criteriaCell);
				//change for CSCti23856
				dojo.style(criteria.domNode, {	        	
		        	'width':200+'px',
		        	'height':21 + 'px'
		        });
				if(this._started){
					criteria.startup();
				}
			}
	 }
	});
	
 	var backUpSaveSearchParam = "";
 	dojo.addOnLoad(function() {
 		foOfUrl = customUrl.replace(tempCustomUrl,'');
 		foOfUrl = foOfUrl.replace(/&amp;/g, "&");
 		backUpSaveSearchParam = saveSearchParam.replace(/&%26/g, "&");
 	
 		var errorMessage = "${message}";
 		var rf = "${rf}";
       /*	if(rf == "bugDetailPage"){
       		trackEvent.event('view',{'title':'BSS Summary Page', 'sitearea':'bss','action':'pageLoad','rf':'http://www.cisco.com/cisco/psn/bssprt/bss/bssDetail','lc':'http://www.cisco.com/cisco/psn/bssprt/bss/bssSummary', ntpagetag: {sensors:[ bss_ntpt_imgsrc ]}});
       	}else{
 			trackEvent.event('view',{'title':'BSS Summary Page', 'sitearea':'bss','action':'pageLoad','rf':'http://www.cisco.com/cisco/psn/bssprt/bss/bssHome','lc':'http://www.cisco.com/cisco/psn/bssprt/bss/bssSummary', ntpagetag: {sensors:[ bss_ntpt_imgsrc ]}});
 		}*/
 		<c:if test='${not empty errorList}'>
 			dojo.query ( ".xwtErrorMessageText", xwtErrorMessage.domNode )[0].innerHTML = '<spring:message code="multiplebugsearch.resultpage.message" />';
 		</c:if>
 		
 		if(errorMessage != ""){
        	showError(errorMessage);	    
		}
		
 	});
 	
 	dojo.ready(function(){
 		dojo.parser.parse();
	    document.getElementById("searchNameDivId").style.display = "block";
	    //now open the filter bar to show the filter details .
	    var c = dojo.connect(table, "onLoadEnd", function(){
	    dojo.disconnect(c);
	    dijit.byId("dijit_form_ToggleButton_0").onClick();
	    dijit.byId("context").filterToolbar._disable(false);
	    saveSearchParam = backUpSaveSearchParam;
	    });
   });
   

	function viewBugDetail(bugId){
		alert('bugId  ::::;' + bugId);
		document.bugDetailForm.bugId.value = bugId;
		document.bugDetailForm.filterData.value = getFilterData(); 
			alert('getFilterData  ::::;' + getFilterData());
		document.bugDetailForm.submit();
	}
	function viewMore(){
		document.bugDetailForm.bugId.value = bugIdInMore;
		document.bugDetailForm.filterData.value = getFilterData(); 
		document.bugDetailForm.submit();
	}

	function getFilterData(){
		var filterData = "EMPTY";
		if(foOfUrl && table._currentFilter){
			customUrl +=foOfUrl;
		}
		if(customUrl.indexOf('&fo=') != -1){
			filterData = customUrl.replace('<c:url value="${REQUEST_PATH_PROXY}" />&fo=','');
		}else{
			filterData = customUrl.replace('<c:url value="${REQUEST_PATH_PROXY}" />','');
		}
		if(filterData.indexOf('&ib=true') != -1){
			filterData = filterData.replace('ib=true', '') + incBugsFilter;
		}else if(filterData.indexOf('&acsl=2') != -1){
			filterData = filterData.replace('&acsl=2', '') + incBugsFilter;
		}
		if(filterData.indexOf('blmd:') != -1){
				filterData = filterData + ',filterBlmd:' + filterBlmd;
		}
		if(filterData == ""){
			filterData = "EMPTY";
		}	
		return filterData;
	}

	function bugIdformatter(data,rowIndex){
		var dataStr = data;
		
		var strBug = '<a href="http://swtg-rtp-dev-2.cisco.com:10040/wps/myportal/bss?searchType=bugIdfilterData&bugId='+dataStr+'&filterData='+getFilterData()+'">' + data + '</a>' + ' - ' + rowIndex.i.headLine.replace(/\"/g, "\''");
    
    return strBug;
	}
	
	function srFormatter(data, rowIndex){
		var srCount = "";
		if(data == undefined){
			return "";
		}
		var sr = rowIndex.i.srNumberCount;
		if(sr != "0"){
			srCount = sr;
		}
		return String(srCount);
	}

	function formatterIcon(data,rowIndex) {
	  	var rateIcon_Grid = "";
		var decVal = false;
		if(data == "NOT RATED" || data == undefined){
			data = 0;
		} else {
			data = data;
		}
		if(String(data).indexOf('.') != -1){
	       	decVal = true;
		}
		
		for(var i = 0; i <= 4; i++){
			 if(i + 1 > Math.floor(data)){
			 	var tempVal = Math.floor(data) + 1;
				if(decVal && (tempVal == i + 1)){
				 	rateIcon_Grid += '<div class="halfStarChecked"></div>';
				}else{
				 	rateIcon_Grid += '<div class="emptyStarChecked"></div>';
				}
			}else{
			   		rateIcon_Grid +='<div class="fullStarChecked"></div>';
			}
	   }
	   return rateIcon_Grid;
	}
var tableStruct = [{
				label: '<spring:message code="searchresultpage.grid.bugid" />',
				attr: 'bugId',
				width: 425,
				vAlignment: "middle",
				alignment:'left',
				sortable:sortFlag,
				filterable:false,
				formatter:bugIdformatter
			},{
				label: '<spring:message code="filter.label.keyword" />',
				attr: 'keyword',
				vAlignment: "middle",
				alignment:'left',
				filterable:true,
				hidden:true
			}
			<c:if test="${sessionScope.USER_SESSION_BEAN.FILTER_OPTIONS_INCLUDE_BUG}">
			,{
				label: '<spring:message code="filter.label.includebugs" />',
				attr: 'includeBugs',
				vAlignment: "middle",
				alignment:'left',
				filterable:true,
				hidden:true,
				filterType: "enumeration",
				filterOptions: {
					autoSuggestValues: [//'<spring:message code="filter.label.includebugs.allknown" />',					 					
										'<spring:message code="filter.label.includebugs.availabletocustomer" />'],
					enableAutoSuggest: false
				}
			}
			</c:if>
			,{
				label: '<spring:message code="filter.label.modifieddate" />',
				attr: 'modifiedDate',
				vAlignment: "middle",
				alignment:'left',
				filterable:true,
				hidden:true,
				filterType: "enumeration",
				filterOptions: {
					autoSuggestValues: ['<spring:message code="filter.label.modifieddate.last7days" />',
										'<spring:message code="filter.label.modifieddate.last3months" />',
										'<spring:message code="filter.label.modifieddate.last6months" />',
										'<spring:message code="filter.label.modifieddate.lastyear" />',
										'<spring:message code="filter.label.modifieddate.before1year" />'],
					enableAutoSuggest: false,
					sortAutoSuggestValues: false
				}
			},{
				label: '<spring:message code="searchresultpage.grid.customerreported" />',
				attr: 'srNumberCount',
				width: 90,
				vAlignment: "middle",
				alignment: "center",
				filterable:false,
				sortable:sortFlag,
				formatter:srFormatter
			},{
				label: '<spring:message code="searchresultpage.grid.status" />',
				attr: 'statusGroup',
				width: 90,
				vAlignment: "middle",
				alignment: "center",
				sortable:sortFlag,
				filterable:true,
				filterType: "enumeration",
				filterOptions: {
					autoSuggestValues: [ <c:if test="${sessionScope.USER_SESSION_BEAN.USER_TYPE == 'CUSTOMER'}">
										'<spring:message code="filter.label.status.open" />',
										'<spring:message code="filter.label.status.fixed" />',
										'<spring:message code="filter.label.status.terminated" />',
										'<spring:message code="filter.label.status.other" />'
										</c:if>
										 <c:if test="${sessionScope.USER_SESSION_BEAN.USER_TYPE == 'PARTNER' || sessionScope.USER_SESSION_BEAN.USER_TYPE == 'EMPLOYEE'}">
										 	'<spring:message code="filter.label.status.open.assigned" />',
											'<spring:message code="filter.label.status.open.forwarded" />',
											'<spring:message code="filter.label.status.open.new" />',
											'<spring:message code="filter.label.status.fixed.resolved" />',
											'<spring:message code="filter.label.status.fixed.verified" />',
											'<spring:message code="filter.label.status.terminated.closed" />',
											'<spring:message code="filter.label.status.other.duplicate" />',
											'<spring:message code="filter.label.status.open.held" />',
											'<spring:message code="filter.label.status.open.inforequired" />',
											'<spring:message code="filter.label.status.open.more" />',
											'<spring:message code="filter.label.status.open.open" />',
											'<spring:message code="filter.label.status.open.postponed" />',
											'<spring:message code="filter.label.status.open.submitted" />',
											'<spring:message code="filter.label.status.open.waiting" />',
											'<spring:message code="filter.label.status.terminated.junked" />',
											'<spring:message code="filter.label.status.terminated.unreproducible" />'
										 </c:if>],
					enableAutoSuggest: false
				}
			},{
				label: '<spring:message code="searchresultpage.grid.severity" />',
				attr: 'severityCode',
				width: 100,
				vAlignment: "middle",
				alignment: "center",
				sortable: sortFlag,
				<c:if test="${isAdvSearchPage}">
				sorted:'descending',
				</c:if>
				filterable:true,
				filterType: "enumeration",
				filterOptions: {
					autoSuggestValues: ["1","2","3","4","5","6"],
					enableAutoSuggest: false
				}						
			},{
				label: '<spring:message code="searchresultpage.grid.rating" />',
				attr: 'averageRneRating',
				width: 98,
				vAlignment: "middle",
				alignment: "center",
				filterable:false,
				sortable:true,
				formatter:formatterIcon
			},
			{
				label: '<spring:message code="filter.label.technology" />',
				attr: 'technology',
				vAlignment: "middle",
				hidden: true,
				filterable:true,
				filterType: "enumeration",
				filterOptions: {
					autoSuggestValues: ['<spring:message code="filter.label.wan" />',
										'<spring:message code="filter.label.voice" />',
										'<spring:message code="filter.label.vpn" />',
									<c:if test="${sessionScope.USER_SESSION_BEAN.USER_TYPE == 'EMPLOYEE'}">
										'<spring:message code="filter.label.tbd" />',
										'<spring:message code="filter.label.orphan" />',
									</c:if>
										'<spring:message code="filter.label.storagenetworking" />',
										'<spring:message code="filter.label.tcpip" />',
										'<spring:message code="filter.label.securityvpn" />',
										'<spring:message code="filter.label.broadbandcable" />',
										'<spring:message code="filter.label.man" />',
										'<spring:message code="filter.label.subscriberedge" />',
										'<spring:message code="filter.label.networkmanagement" />',
										'<spring:message code="filter.label.atm" />',
										'<spring:message code="filter.label.availability" />',
										'<spring:message code="filter.label.ip" />',
										'<spring:message code="filter.label.qos" />',
										'<spring:message code="filter.label.access" />',
										'<spring:message code="filter.label.networkprocessing" />',
										'<spring:message code="filter.label.contentnetworking" />',
										'<spring:message code="filter.label.mpls" />',
										'<spring:message code="filter.label.lanswitching" />',
										'<spring:message code="filter.label.wirelessmobility" />',
										'<spring:message code="filter.label.ipst" />',
										'<spring:message code="filter.label.dialaccess" />',
										'<spring:message code="filter.label.nav" />',
										'<spring:message code="filter.label.mixedtechnologies" />',
										'<spring:message code="filter.label.interoperabilitysys" />',
										'<spring:message code="filter.label.optical" />',
										'<spring:message code="filter.label.ibmtech" />',
										'<spring:message code="filter.label.video" />'
										],
					enableAutoSuggest: false
				}						
			}];



//Basic search functions
function showError(errorMessage){
		dojo.query ( ".xwtErrorMessageText", xwtErrorMessage.domNode )[0].innerHTML = errorMessage;
       	//show error
       	dojo.removeClass("errorContainerId", "errorHidden");
	    dojo.addClass("errorContainerId","errorVisible");	
}

function checkForKeyPress(e){
   if(!dojo.isIE && dojo.string.trim(autoSuggest.attr('value')).length > 0 && dojo.keys.ENTER == e.charOrCode){
   		dojo.stopEvent(e);
   		submitSearchForm();
   		//setTimeout ('submitSearchForm()', 1000 );
   }
}

function submitSearchForm(){
	dojo.addClass("errorContainerId","errorHidden");
	var errorFlag = false;
	errorMessage = "";
	var RE_BUGID = /^[cC][sS][cC]+[A-z]{2}[0-9]{5}(\s){0,}(,|;){0,}$/;
	var RE_BUGID_SEP_SPACE = /^((\s){0,}[cC][sS][cC]+[A-z]{2}[0-9]{5}(\s){0,}){2,}$/;
	var RE_BUGID_SEP_SPACE_ALPHANUM = /^((\s){0,}[cC][sS][cC]+[A-z]{2}[0-9]{5}(\s){0,}){1,}((\s){0,}[A-z0-9]{0,}(\s){0,}((\s){0,}[cC][sS][cC]+[A-z]{2}[0-9]{5}(\s){0,})(\s){0,}[A-z0-9]{0,}(\s){0,}){1,}$/;
	var RE_ALPHANUM_SEP_SPACE_BUGID = /^((\s){0,}[A-z0-9](\s){0,}){1,}((\s){0,}[A-z0-9]{0,}(\s){0,}((\s){0,}[cC][sS][cC]+[A-z]{2}[0-9]{5}(\s){0,})(\s){0,}[A-z0-9]{0,}(\s){0,}){1,}$/;
	var RE_MUL_BUGID_ALPHANUM = /^[cC][sS][cC]+[A-z]{2}[0-9]{5}(\s){0,}(,|;){1,}(\s){0,}((\s){0,}([cC][sS][cC]+[A-z]{2}[0-9]{5}|[A-z0-9(\s){0,}]{1,})(\s){0,}(,|;){1,}(\s){0,}){0,}(\s){0,}([cC][sS][cC]+[A-z]{2}[0-9]{5}|[A-z0-9(\s){0,}]{1,})(\s){0,}(,|;){0,}$/;
	var RE_MUL_ALPHANUM_BUGID_2 = /^((\s){0,}[A-z0-9(\s){0,}]{1,}(\s){0,}(,|;){1,}(\s){0,}){1,}((\s){0,}[cC][sS][cC]+[A-z]{2}[0-9]{5}(\s){0,}(,|;){0,}(\s){0,}){1,}$/;
	var RE_MUL_ALPHANUM_BUGID = /^((\s){0,}[A-z0-9(\s){0,}]{1,}(\s){0,}(,|;){1,}(\s){0,}){1,}((\s){0,}[cC][sS][cC]+[A-z]{2}[0-9]{5}(\s){0,}(,|;){1,}(\s){0,}){1,}((\s){0,}([cC][sS][cC]+[A-z]{2}[0-9]{5}|[A-z0-9(\s){0,}]{1,})(\s){0,}(,|;){1,}(\s){0,}){0,}(\s){0,}([cC][sS][cC]+[A-z]{2}[0-9]{5}|[A-z0-9(\s){0,}]{1,})(\s){0,}(,|;){0,}$/;
	
	var searchValue = dojo.string.trim(autoSuggest.attr('value'));
	if (searchValue.search(RE_BUGID) != -1) {
		document.searchForm.action = "${bugByIdAction}";
	}else if(searchValue.search(RE_BUGID_SEP_SPACE) != -1){
		errorMessage = '<spring:message code="searchpage.commaorsemicolon" />';
		errorFlag = true;
	}else if(searchValue.search(RE_BUGID_SEP_SPACE_ALPHANUM) != -1 || searchValue.search(RE_ALPHANUM_SEP_SPACE_BUGID) != -1){
		errorMessage = '<spring:message code="searchpage.commaorsemicolon.bugidkeyword" />';
		errorFlag = true;
	}else if((searchValue.search(RE_MUL_BUGID_ALPHANUM) != -1 || searchValue.search(RE_MUL_ALPHANUM_BUGID) != -1 
				|| searchValue.search(RE_MUL_ALPHANUM_BUGID_2) != -1) && searchValue.replace(/;/g,',').split(",").length > 10){
		errorMessage = '<spring:message code="searchpage.multiplebugid.limit" />';
		errorFlag = true;
	}else if(searchValue.search(RE_MUL_BUGID_ALPHANUM) != -1 || searchValue.search(RE_MUL_ALPHANUM_BUGID) != -1 
				|| searchValue.search(RE_MUL_ALPHANUM_BUGID_2) != -1){
		document.searchForm.action = "${multipleBugSearchAction}";
	}
	if(errorFlag){
		showError(errorMessage);
		errorFlag = false;
		return;
	}
	document.searchForm.submit();
}


function exportData(){
	var query = customUrl + "&rpp="+ totalRecord;
	<c:if test="${isAdvSearchPage}">
		query = query + "&sort=severityCode:asc";
	</c:if>
	var queryUrl = query.replace(/&/g, "%26").replace(/\?/g, "%3F").replace('<c:url value="/service/requestproxy/get"/>', "");
	window.location="<c:url value="/exporttoexcel.xls?queryUrl="/>" + queryUrl;
}
</script>
<div id="breadcrumbContainer">
	
	<div id="breadcrumb" nested="false" dojoType="xwt.widget.layout.Breadcrumb" jsId="breadcrumb" class="xwtBreadcrumb"
		breadcrumbItems="{items:[
			<c:if test='${pid == null}'>
			{label:'<spring:message code="searchpage.button.text.searchhome"/>', destination:'${searchPageBread}'},
			{label:'<spring:message code="breadcrumb.searchresults" />',htmlText:'<spring:message code="breadcrumb.searchresults" />'}
			</c:if>
			<c:if test='${pid != null}'>
			{label:'<spring:message code="breadcrumb.pid" />',htmlText:'<spring:message code="breadcrumb.pid" />${pid}'}
			</c:if>
			]}" spacerString="&nbsp;&nbsp;">
	</div>
</div>


<c:if test='${empty errorList}'>
<div id="errorContainerId" class="errorContainerBasic errorHidden">
 <div dojoType="xwt.widget.notification.ErrorMessage" jsId="xwtErrorMessage"></div>
 </div>
</c:if>
<!-- Error -->
<c:if test='${not empty errorList}'>
	<div id="errorContainerId" class="errorContainer errorContainer_searchResult">
	 <div dojoType="xwt.widget.notification.ErrorMessage" jsId="xwtErrorMessage"></div>
		<div class="errorListContainer_searchResults">
		<div style="height:23px;"></div>
			<c:forEach items="${errorList}" var="error">
				<div style="height:2px;"></div>
				${error}
			</c:forEach> 
		</div>
	</div>
</c:if> 

<c:if test='${pid == null}'>
	<c:if test="${!isAdvSearchPage}">
	<div id="basicSearchContainer" class="basicSearchContainer">
	<span dojoType="ComboBoxReadStore" jsId="autoSugStore" url="dummySearch" requestMethod="get"></span>
	<form:form name="searchForm" commandName="search" method="get" action="${searchAction}"	id="basicSearchForm" class="basicSearchForm">
			<form:input id="searchTextField" jsId="autoSuggest" name="" 
				dojoType="xwt.widget.form.ComboBox" path="keywords" size="180" maxlength="120"
				style="float:left;width:410px;padding: 0px !important;align:right;" 
				hasDownArrow='false' store="autoSugStore" onkeypress="checkForKeyPress" searchAttr="name" queryExpr="*" />

			<input id="searchButton" jsId="searchButton" dojoType="xwt.widget.form.TextButton"
					style="float:left;margin:3px 0 3px 5px;align:right;"  
					onclick="submitSearchForm();" label="<spring:message code="searchpage.button.text.search" />">
	</form:form>
	</div>
	</c:if>
	<c:if test="${isAdvSearchPage}">
		<div id="searchCriteriaBar" class="messageBar">
			<div class="messageText">Search criteria - ${searchCriteria}</div>
		</div>
	</c:if>
</c:if>
<c:if test='${pid != null}'>
	<div id="emptySpace" style="margin:25px"></div>
</c:if>

<div id="tableContainer">
	<c:if test="${sessionScope.USER_SESSION_BEAN.SEARCH_RESULT_EXPANDABLE}">
		<div id="table2Detail" dojoType="ExpandableWidget" style="overflow-y:auto;"></div>
	</c:if>
    <span dojoType="searchResultReadStore" jsId="queryReadStore" url="dummy" requestMethod="get" ></span>
    <div id="context" dojoType="xwt.widget.table.ContextualToolbar" 
    	tableId="table" needFilter="true" style="display:none;width:900px !important;"></div>
	<table dojoType="xwt.widget.table.Table" id="table" jsid="table"
				structure="tableStruct" rowsPerPage="25" rpp="25"
				store="queryReadStore" detailWidget="table2Detail" 
				style="height: 595px;"
				<c:if test="${isFilter}"> quickFilterDefault="prevFilter" </c:if> 
				<c:if test="${sessionScope.USER_SESSION_BEAN.FILTER_OPTIONS}"> filters="quickFilters" </c:if>>
	</table>
	<div class="subFoot">
	 	<c:if test="${sessionScope.USER_SESSION_BEAN.EXPORT_TO_EXCEL}">
	 	<div id="exportExcelBtnContainer">
			<button dojoType="xwt.widget.form.TextButton" id="btnExportToExcel"
	      		onClick="exportData()" >
	      		<spring:message code="searchresultpage.button.exportexcel" />
	     	</button>
	     	<c:if test="${sessionScope.USER_SESSION_BEAN.USER_TYPE != 'GUEST' && pid == null}" >
	     	<button dojoType="xwt.widget.form.TextButton" id="saveSearchPopButtonId" style="margin-left:5px;""
	      		onClick="showSaveSearchDialog()" ><spring:message code="savedialog.label.savesearch" /></button>
	      	</c:if>
     	</div>
     	</c:if>
     	<div id="totalRecords">
       		<spring:message code="searchresultpage.label.totalrecords"/><span id="recordId"></span>
      	</div>
	</div>
</div>
<form:form id="bugDetailForm" name="bugDetailForm" onsubmit="return false;" action="${viewBugDetailAction}" method="get">
			<input type="hidden" name="bugId" value="">
			<input type="hidden" name="filterData" value="">
</form:form>

<div id="saveSearchDivId" style="outline:none !important" title="<spring:message code="savedialog.label.savesearch" />" dojoType="dijit.Dialog" class="masterbrand">
	<jsp:include page="saveDialog.jsp" flush="true"/>
	<div class="dijitDialogPaneActionBar">
		<button id="saveButtonId" onClick="submitSaveSearch();return false;" disabled="true" dojoType="xwt.widget.form.TextButton" jsId="saveButtonId"><spring:message code="savedialog.label.savesearch" /></button>
	</div>
</div>