<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet_2_0"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html" isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page import="javax.portlet.PortletRequest"%>
<portlet:defineObjects />
 <head>
 <script type="text/javascript" src="<%=renderRequest.getContextPath()%>/js/jquery.js"></script> 
 <% out.println(renderRequest.getContextPath()); %>
 <style type="text/css">
.u193 {
    font-family: Arial;
    height: 18px;
    left: 100px;
    position: absolute;
    text-align: left;
    top: 627px;
    width: 290px;
    word-wrap: break-word;
}
div {
    background-repeat: no-repeat;
}

.u201 {
    font-family: Arial;
    height: 18px;
    left: 2px;
    position: absolute;
    text-align: left;
    top: 6px;
    width: 111px;
    word-wrap: break-word;
}
div {
    background-repeat: no-repeat;
}
</style>

<script type="text/javascript">
function testService()
                {
                    $.ajax(
                    {
                        dataType: 'json',
                        headers: {
                            Accept:"application/json",
                            "Access-Control-Allow-Origin": "*"
                        },
                        type:'GET',
                        url:'http://localhost:10039/QuickLinksServiceApp/rest/test/userapplist',
                        success: function(data)
                        {
                       var count=0;
						var innrhtml = '';
	                        if(undefined != data.userAppList){
	                        	for(a in data.userAppList){
	                        	var name = new Array();
								if(count == 0){
								innrhtml=innrhtml + '<div class="acgc_megamenu_inner_box"><ul class="acgc_megamenu_inner_box_list acgc_no_underline">';
								}
	                        		var name = data.userAppList[a].appName;
	                        		var url = data.userAppList[a].appUrl;
	                        		innrhtml = innrhtml + '<li><a href="'+url+'" title="Metro Wayfinding Maps">'+name+'</a></li>'

									if(count==8){
										innrhtml = innrhtml + '</ul></div>';
										count = -1;
									}
	                        		console.log(name);
	                        		count++;
	                        	}
	                        }else{
	                        	console.log('no data.userAppList');	
	                        }
                        
                        document.getElementById('slide1').innerHTML=innrhtml;
						alert('innrhtml----'+innrhtml);
                        },
                        error: function(data)
                        {
                            alert("error");
                        }
                    });
                }
                
               

</script>

</head>


<button onclick="testService()">Click me</button>


<div class="slide" id="slide1"></div>



<h3>URL</h3>
Available App list

<table border="1" cellpadding="4">
	<tr>

		
		<td><a href='<portlet:renderURL>
      		<portlet:param name="action" value="editUrl"/>
		</portlet:renderURL>'>Edit Link</a></td>
		
	</tr>


	<tr>
		<th>App Name</th>
		<th>App Description</th>
		
	</tr>
	
   <c:forEach items="${urlList}" var="urlLst">
      <tr>
      
      	<td><c:out value="${urlLst.appName}"/></td>
      	<td><c:out value="${urlLst.appDesc}"/></td>
      	
      	
      </tr>
   </c:forEach>   
	
</table>
