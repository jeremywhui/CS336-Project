<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.cs336.pkg.*"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title> Login Page </title>
	</head>
	
	<body>
		<!-- prints out sign in below in java --> 
		<% out.println("Please sign in below"); %>
							  
		<br>
		<form method="post">
			<table>
				<tr>
				<!-- input text field labeled username -->
					<td> Username: </td>
					<td><input type = "text" name = "username"></td>
				</tr>
				<tr>
				<!-- input password field labeled password -->
					<td> Password: </td>
					<td><input type = "password" name = "password"></td>
				</tr>
				<tr>
				<!-- 2 options: either signup -> account.jsp page OR signup -> signup.jsp page -->
					<td><input type = "submit" formaction = "checkLoginDetails.jsp" name = "signin" value = "SignIn"></td>
					<td><input type = "submit" formaction = "signup.jsp" name = "signup" value = "SignUp"></td>
				</tr>
			</table>
		</form>

</body>
</html>