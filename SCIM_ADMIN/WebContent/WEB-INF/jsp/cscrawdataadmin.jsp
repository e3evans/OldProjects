<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SCIM - CSC rawdata admin</title>
</head>
<body>
	<div class="heading1">
		<center><h1>SCIM - Fetch rawdata from CAM/PORTKEY and store into SCIM csc rawdata table</h1></center>
	</div>
	<!-- message starts here -->
	<div class="message">
		${message}
	</div>
	<!--  message ends here -->
	
	<!-- content starts here -->
	<div>
		<form method="POST">
		<br><br>
			<center>
			<table>
				<tr><td><b>From Date :</b></td><td><input type="text" name="fromDate"/> (DD-MM-YYYY)</td></tr>
				<tr><td><b>To Date :</b></td><td> <input type="text" name="toDate"/> (DD-MM-YYYY)</td></tr>
			</table>
			<br><br>
			<div class="button">
				<input type="submit" value="Extract Rawdata" />
			</div>
			</center>
		</form>
	</div>
	<!-- content ends here -->    
</body>
</html>