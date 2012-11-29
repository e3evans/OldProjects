<!DOCTYPE HTML><%@page import="org.aurora.test.SSORedirectTest"%>
<%@page import="org.aurora.portalSSO.util.SSOManager"%>
<%@page language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<head>
<title>Test</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>


<%
	Cookie[] cookies = request.getCookies();
	
	for (int i = 0;i<cookies.length;i++){
		Cookie temp = cookies[i];
		%><%= temp.getName()%><hr><%
	}

 %>

IREQ --> <a href="/AuroraSSOPOC/SSORedirectTest?testURL=1">Link</a><br>

Learning Center --> <a href="/AuroraSSOPOC/SSORedirectTest?testURL=2">Link</a><br>

E Health Patient ---> <a href="/AuroraSSOPOC/SSORedirectTest?testURL=3">Link</a>

</body>
</html>