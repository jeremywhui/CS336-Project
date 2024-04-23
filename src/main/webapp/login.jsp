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
		<%
			if(session.getAttribute("username") != null){ // If user is already logged in, redirect to index
				response.sendRedirect("index");
			}
		%>

		<h1> Login Page </h1>
		<p> Please enter your username and password to login.</p>		  
		<form method="post">
			<table>
				<tr>
					<td> Username: </td>
					<td><input type = "text" name = "username"></td>
				</tr>
				<tr>
					<td> Password: </td>
					<td><input type = "password" name = "password"></td>
				</tr>
				<tr>
					<td><input type = "submit" name = "login" value = "Login"></td>
				</tr>
			</table>
		</form>

		<%
			// Check if the request was a POST request
			if ("POST".equals(request.getMethod())) {
				// If user clicks on the login button
				if(request.getParameter("login") != null){
					String username = request.getParameter("username");
					String password = request.getParameter("password");
					if (!AuthenticationUtil.validateLoginInput(username, password)){ // Checks if username and password are not empty
						out.println("<p style='color:red;'>Please fill out all fields.</p>");
					}
					else if (AuthenticationUtil.authenticateLoginAttempt(username, password)){ // Checks if username and password are found in database
						session.setAttribute("username", username);
						response.sendRedirect("index");
					}
					else{
						out.println("<p style='color:red;'>Incorrect username or password. Try again.</p>");
					}
				}
			}
		%>

		<p>Don't have an account? <a href="register">Register here!</a></p>

</body>
</html>