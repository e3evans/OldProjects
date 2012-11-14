<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/portlet" prefix="portlet"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page import="com.cisco.swtg.bss.delegates.ServiceInterfaceDelegate" %>
<%
	String productAndCategory = ServiceInterfaceDelegate.getProductAndCategory();
%>

<link rel="stylesheet" type="text/css" href="<c:out value='${requestPath}'/>/web/fw/tools/bss/xmproot/xwt/themes/masterbrand/form/RadioButton.css" />
<link rel="stylesheet" type="text/css" href="<c:out value='${requestPath}'/>/web/fw/tools/bss/xmproot/xwt/themes/masterbrand/xwt/form/listbox.css" />

<portlet:defineObjects />
<portlet:actionURL var="advSearchAction">
	<portlet:param name="action" value="advancedSearchAction" />
</portlet:actionURL>

<style type="text/css">
.xwtDropDown{
	width:345px !important;
}
.dj_ie .dijitReadOnly *,  
.dijitReadOnly, .dijitDisabled, 
.dijitDisabledClickableRegion{
	background-color: #E0E0E0 !important;
}
</style>

<script type="text/javascript">
	var bss_CategoryListJson = {identifier:"itemId", label: "itemName", items: [{itemName:'<spring:message code="advsearchpage.category.allproduct"/>', itemId:"allProducts"}]};
    
	dojo.addOnLoad(function() {
		dojo.parser.parse();
		_newProductListJson = {identifier:"itemId", label: "itemName", items: []};
		dijit.byId("advSearchButton").attr("disabled",true);

		var listDataSet = {identifier:"itemId", label: "itemName", items: []};
		listStore = new dojo.data.ItemFileReadStore({data:listDataSet});
		//TODO - check with Matt- teprasad
		var tempStringListBox = '<select size="8" dojoAttachEvent="onchange:changeHandler" dojoAttachPoint="containerNode,focusNode" dojoType="dijit.form.MultiSelect"></select>';
		if(dojo.isIE){
			tempStringListBox = '<select size="10" dojoAttachEvent="onchange:changeHandler" dojoAttachPoint="containerNode,focusNode" dojoType="dijit.form.MultiSelect"></select>';
		}
		var listBoxSeries = new xwt.widget.form.ListBox(
				{id:"productListBox", store: listStore,
					searchAttr: "itemName", style:"width:348px",
					templateString: tempStringListBox,
					changeHandler: fnShowModelDetails},
					"productListAll");
		
	    populateCategory();
		populateProduct();
	    });

var userId = '<c:out value="${sessionScope.USER_SESSION_BEAN.USER}" />';
function populateAdvSearchFields(){
	dijit.byId("productId").textbox.value = "<spring:message code="advsearchpage.product.textbox.text" />";
	dojo.removeClass("productId", "displayList");
	dojo.addClass("productId","displayText");
	softVersionJsId.attr("disabled",true);
	softVersionJsId.textbox.value = "";	
}
		
function populateCategory(){
	var errorCode = "errors.system";
 	var productCategory = {
				url: '<c:url value="/service/requestproxy/get/bss/mdf/hierarchy"/>',
                preventCache:true,
                handleAs: "json"
    }
	var productCatXhr = dojo.xhrGet(productCategory);
	productCatXhr.addCallback(function(_jsonObject) {
		if(_jsonObject['mdfElement'] == null){
       		if(_jsonObject['errorResponse'] != null){
       			errorCode = _jsonObject['errorResponse']['publicErrorCode'];
       			var errorMessage = _jsonObject['errorResponse']['publicErrorMessage'];
       			showError(errorMessage);
       		}else{
       			var errorMessage = errorMessageFramework.getMessage(errorCode);
       			showError(errorMessage);
       		}
       		return;
		}else{
			var categoryListJson = _jsonObject['mdfElement'][0]['childItem'];
			dojo.forEach(categoryListJson, function(categoryObj, i) {
				bss_CategoryListJson.items.push(categoryObj);
			});
			bss_CategoryListJson.items.sort(sortByItemName);
			productCategories.store = new dojo.data.ItemFileReadStore({data: bss_CategoryListJson});
			productCategories.startup();
		}				
	});
}

function populateProduct(){
	var _jsonObject = <%=productAndCategory%>;
	if(_jsonObject['product'] != null && _jsonObject['product'].length > 0){
		var bss_temp_prod = null;
		dojo.forEach(_jsonObject['product'], function(bss_product, i) {
			var iPath = bss_product['categoryId'] +"/" + bss_product['productId'];
			bss_temp_prod = {itemName:bss_product['productName'], itemId: iPath};
			_newProductListJson.items.push(bss_temp_prod);
		});
	}
	refreshProduct();				
}


 function sortByItemName(obj1,obj2) {
      return ((obj1.itemName == obj2.itemName) ? 0 : ((obj1.itemName > obj2.itemName) ? 1 : -1 ));
 }
//This function is used to show list of series when a category get selected
function showSeriesList(){
	//get the value of product category from drop down box
	var selectedProductCat = productCategories.attr('value');
	if(dojo.string.trim(selectedProductCat) == "" || dojo.string.trim(selectedProductCat) == "allProducts"){
		_newProductListJson = {identifier:"itemId", label: "itemName", items: []};
		populateProduct();
		return;
	}
	var _jsonObject=<%=productAndCategory%>;
	var productAvailableFlag = false;
	if(_jsonObject['product'] != null && _jsonObject['product'].length > 0){
		var productObj={itemId:"", itemName:""};
		_newProductListJson = {identifier:"itemId", label: "itemName", items: []};
		dojo.forEach(_jsonObject['product'], function(bss_product, i) {
			productObj={itemId:"", itemName:""};
			if(bss_product.categoryId==selectedProductCat){	
				if(!productAvailableFlag){
					_newProductListJson = {identifier:"itemId", label: "itemName", items: []};
					productAvailableFlag = true;
				}					
				productObj.itemName=bss_product['productName'];
				productObj.itemId=bss_product['productId'];
				_newProductListJson.items.push(productObj);
			}
		});
		_newProductListJson.items.sort(sortByItemName);
		if(_newProductListJson.items.length == 0){
			_newProductListJson = {identifier:"itemId", label: "itemName", items: [{itemName:'<spring:message code="advsearchpage.product.noproduct"/>', itemId:"noProduct"}]};
		}
	}
	refreshProduct();
}

function refreshProduct(){
	dojo.style(dojo.byId("advProdctMissMatchId"),"visibility","hidden");
	var productTxb = dijit.byId("productId");
	productTxb.textbox.value = '<spring:message code="advsearchpage.product.textbox.text" />';
	var productListId = dijit.byId('productListBox');
	productListId.store = new dojo.data.ItemFileReadStore({data: _newProductListJson});
	productListId.refresh();
	
	var seriesInput = document.getElementById('productListId');
	seriesInput.value = "";
	softVersionJsId.attr("disabled",true);
	softVersionJsId.textbox.value = "";
	dijit.byId("advSearchButton").attr("disabled",true);
	refreshModelAndVersion();
}
function refreshModelAndVersion(){
	dijit.byId("advSearchButton").attr("disabled",true);
	dojo.byId("bss_modelId").innerHTML = "";
	
	softVersionJsId.attr("disabled",true);
	softVersionJsId.textbox.value = "";
}


function showModelList(selectedItem, selectedProduct){
	var bss_modelId = "<b><spring:message code="advsearchpage.model.modelfor"/>"+selectedProduct+":<br><br></b>";
	dojo.byId("bss_modelId").innerHTML = bss_modelId;
	var modelListJson = {identifier:"itemId", label: "itemName", items: []};
	var modelsOrVersion = {
			url: "<c:url value='/service/requestproxy/get'/>"+"/bss/mdf/hierarchy/"+selectedItem+"?skipValid=true",
               preventCache:true,
               sync:true,
               handleAs: "json"
    }
 	var modelsXhr = dojo.xhrGet(modelsOrVersion);
	modelsXhr.addCallback(function(_jsonObject) {
		var mdfElement = _jsonObject['mdfElement'];
		if(mdfElement == "" || typeof mdfElement == 'undefined' || mdfElement == null){
			modelListJson = {identifier:"itemId", label: "itemName", items: []};
	 	}else{
			dojo.forEach(mdfElement, function(mdfElementObj, i) {
				var resultType = mdfElementObj['resultType'];
				var modelList = mdfElementObj['childItem'];
				if( resultType == "Model"){
					dojo.forEach(modelList, function(modelObj, i) {
						modelListJson.items.push(modelObj);
					});
				}
			});
	  	}
	});	
	if(modelListJson.items.length != 0){
		var modelMessage = "";
		dojo.forEach(modelListJson.items, function(modelObject, i) {
			if(i == 0){
				modelMessage = modelMessage + modelObject["itemName"];
			}else{
				modelMessage = modelMessage + ", " + modelObject["itemName"];
			}
		});
		dojo.byId("bss_modelId").innerHTML = dojo.byId("bss_modelId").innerHTML + modelMessage;
	}else{
		dojo.byId("bss_modelId").innerHTML = dojo.byId("bss_modelId").innerHTML + "None";
	}
}

function setSelectedSoftVersion(){
	var softVersionInput = document.getElementById("softVersionId");
	softVersionInput.value = softVersionJsId.getValue();
}

function fnShowModelDetails(e){
		//enabling the search button
		var seriesList = dijit.byId('productListBox');
		var selectedItem = seriesList.getSelectedItemsValue();
		if(selectedItem == "noProduct"){
			return;
		}
		dijit.byId("advSearchButton").attr("disabled",false);
		softVersionJsId.textbox.value = "";
		softVersionJsId.attr("disabled",false);
		//show models of product
		var seriesInput = document.getElementById('productListId');
		seriesInput.value = selectedItem;
		var selectedItemText = seriesList.getSelectedItemsText();
		dijit.byId("productId").attr("value", selectedItemText);
		document.getElementById("productName").value = selectedItemText;
		showModelList(selectedItem, selectedItemText);
}
	
function filterProducts(){
	var txtBoxProd = dijit.byId('productId').textbox;
	var listTemp = {identifier:"itemId", label: "itemName", items: []};
	var listProduct = dijit.byId('productListBox');

	for(var i=0;i<_newProductListJson.items.length;i++){
		var prod = _newProductListJson.items[i]["itemName"][0];
		if(prod == "undefined")
			prod = _newProductListJson.items[i]["itemName"];
		var strMain = dojo.string.trim(prod).toLowerCase();
		var str = dojo.string.trim(txtBoxProd.value).toLowerCase();
		if(strMain.indexOf(str) !=-1){
			listTemp.items.push(_newProductListJson.items[i]);
		}
	}
	listStore = new dojo.data.ItemFileReadStore({data:listTemp});
	listProduct.store = listStore;
	listProduct.refresh();
	refreshModelAndVersion();
	if(listTemp.items.length > 0 ){
		if(listTemp.items[0]["itemId"] == "noProduct"){
			dojo.style(dojo.byId("advProdctMissMatchId"),"visibility","hidden");
			return;
		}
		dojo.style(dojo.byId("advProdctMissMatchId"),"visibility","hidden");
	}else{
		dojo.style(dojo.byId("advProdctMissMatchId"),"visibility","visible");
	}
}
function submitAdvSearchForm(){
	document.advSearchForm.submit();
}

function productOnFocusHandler(){
	var productTxb = dijit.byId("productId");
	if(productTxb.textbox.value == '<spring:message code="advsearchpage.product.textbox.text" />'){
		productTxb.textbox.value = "";
		productTxb.focus();
	}
	dojo.removeClass("productId", "displayText");
	dojo.addClass("productId","displayList");	
}

function productOnBlurHandler(){
	var productTxb = dijit.byId("productId");
	if(dojo.string.trim(productTxb.textbox.value) == ""){
		productTxb.textbox.value = "<spring:message code="advsearchpage.product.textbox.text" />";
		dojo.removeClass("productId", "displayList");
		dojo.addClass("productId","displayText");
	}
}

function showBasicSearch(){
	productCategories.store = new dojo.data.ItemFileReadStore({data: bss_CategoryListJson});
	productCategories.startup();
	populateProduct();
	refreshProduct();
	refreshModelAndVersion();
	dijit.byId("foundInId").setValue(null);
	dijit.byId("fixedInId").setValue(null);
	dijit.byId("affectedId").setValue(null);
	//hide advanced search
    dojo.removeClass("advSearchDiv", "advSearchVisible");
    dojo.addClass("advSearchDiv","advSearchHidden");
    
	//hide error message if it is there
    dojo.removeClass("errorContainer", "errorVisible");
    dojo.addClass("errorContainer","errorHidden");
    
    //show basic search
    autoSuggest.textbox.value = autoSuggest.textbox.defaultValue;
    autoSuggest.textbox.style.color = '#CCCCCC';
    searchButton.attr("disabled",true);
	dojo.removeClass("searchContainer", "searchHidden");
    dojo.addClass("searchContainer","searchVisible");
}
</script>
<div id="advSearchDashlet" 
		dojoType="xwt.widget.layout.Dashlet" 
		title="<spring:message code="advsearchpage.title.advancedsearch" />"	
		requireCollapse="false">
	<div id="advSearchDashletBody">
		<form:form name="advSearchForm"
				commandName="search" method="get" action="${advSearchAction}">
			<div style="overflow:hidden;">
				<div class="advSearchRow">
					<div class="advSearchColOne">
						<span>*</span>
						<span class="advSearchMandatory"><spring:message code="advsearchpage.label.productcategory" />:</span>
					</div>
					<div class="advSearchColTwo">
						<div class="advSearchElementContainer" style="padding: 0 2px 0 0;">
							<form:input id="productCategoriesId"
								class="comboBoxSpacing" dojoType="xwt.widget.form.DropDown"
								value="<spring:message code="advsearchpage.category.allproduct"/>" searchAttr="itemName"
								path="productCategory"	jsId="productCategories" onChange="showSeriesList();" />
						</div>
					</div>
					<div class="advSearchColThree">&nbsp;</div>
				</div>
				<div class="advSearchRow" style="margin-top:10px;">
					<div class="advSearchColOne">
						<span>*</span>
						<span class="advSearchMandatory"><spring:message code="advsearchpage.label.selectproduct" />:</span>
					</div>
					<div class="advSearchColTwo">
						<div class="advSearchElementContainer" style="padding: 0 2px 0 0;">
							<input
								id="productId" dojoType="dijit.form.TextBox"
								onKeyUp="filterProducts()"
								onFocus="productOnFocusHandler()" onBlur="productOnBlurHandler()"></input>
						</div>
					</div>
					<div class="advSearchColThree">
						<div id="advProdctMissMatchId"  class="advProductMessageContainer">
							<div class="advProductError"><spring:message code="advsearchpage.product.invalidproduct"/></div>
						</div>
					</div>
				</div>
				<div class="advSearchRow">
					<div class="advSearchColOne">&nbsp;
					</div>
					<div class="advSearchColTwo">
						<div class="advSearchElementContainer">
							<div id="productListAll"></div>
						</div>
						<input id="productListId" type="hidden" value="" name="product"></input>
						<input id="productName" type="hidden" value="" name="productName"></input>
					</div>
					<div class="advSearchColThree">
						<div id="bss_modelId" class="bss_modelContainer advSearchElementContainer">
						</div>
					</div>
				</div>
				<div class="advSearchRow" style="margin-top:10px;">
					<div class="advSearchColOne">
						<spring:message	code="advsearchpage.label.softwareversion" />:
					</div>
					<div class="advSearchColTwo">
						<div class="advSearchElementContainer">
							<form:input id="cbxSoftVersion" class="comboBoxSpacing"	dojoType="dijit.form.TextBox"
							 jsId="softVersionJsId"	path="" style="width:60%" onchange="setSelectedSoftVersion()"/>
						</div>
						<input id="softVersionId" type="hidden" value="" name="softwareVersion"></input>
					</div>
					<div class="advSearchColThree">&nbsp;</div>
				</div>
				<div class="advSearchRow" style="margin-top:20px;">
					<div class="advSearchColOne">
						<spring:message	code="advsearchpage.label.softwareversiontype" />:
					</div>
					<div style="float:left; margin-bottom: -6px !important;">
						<form:radiobutton path="versionType" id="foundInId"
								value="foundIn" dojoType="dijit.form.RadioButton"/>
						<span class="radioLabel"><spring:message
								code="advsearchpage.softversion.foundin" />
						</span> 
						<form:radiobutton id="fixedInId"
								path="versionType" value="fixedIn" dojoType="dijit.form.RadioButton" />
						<span class="radioLabel"><spring:message
								code="advsearchpage.softversion.fixedin" />
						</span> 
						<form:radiobutton id="affectedId"
								path="versionType" value="affected" dojoType="dijit.form.RadioButton" />
						<span class="radioLabel"><spring:message
								code="advsearchpage.softversion.knownaffectedversion" />
						</span>
					</div>
				</div>
				<div class="advSearchRow" style="margin-top:24px;margin-bottom: -4px;">
					<div class="advSearchColOne">&nbsp;
					</div>
					<div class="advSearchColTwo">
						<input id="advSearchButton" style="height: 25px;float:left;"
							type="button" dojoType="xwt.widget.form.TextButton"
							onclick="submitAdvSearchForm();"
							label=<spring:message code="advsearchpage.button.search" />></input>
					</div>
					<div class="advSearchColThree">
						<a id="backBasicSearchButton" style="float:right;margin-right:7px;"
							href="javascript:showBasicSearch();"><spring:message code="advsearchpage.button.backtobasicsearch" /></a>
					</div>
				</div>
			</div>		
		</form:form>
	</div>
</div>