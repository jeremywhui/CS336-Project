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
		<h1> Login Page </h1>
		<p> Please enter your username and password to login </p>		  
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
					<td><input type = "submit" name = "signin" value = "Sign In"></td>
					<td><input type = "submit" name = "signup" value = "Sign Up"></td>
				</tr>
			</table>
		</form>

		<%
			// If user clicks on the signin button
			if(request.getParameter("signin") != null){
				String username = request.getParameter("username");
				String password = request.getParameter("password");
				if (!AuthenticationUtil.validateFormInput(username, password)){ // Checks if username and password are not empty
					out.println("<p style='color:red;'>Please fill out all fields</p>");
				}
				else if (AuthenticationUtil.authenticate(username, password)){ // Checks if username and password are found in database
					session.setAttribute("username", username);
					response.sendRedirect("index.jsp");
				}
				else{
					out.println("<p style='color:red;'>Incorrect username or password. Try again</p>");
				}
			}
			// If user clicks on the signup button
			else if(request.getParameter("signup") != null){
				response.sendRedirect("signup.jsp");
			}
		%>

</body>
</html>