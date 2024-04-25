<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.cs336.pkg.*"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title> Register </title>
</head>
<body>
    <%
        if(session.getAttribute("username") != null){ // If user is already logged in, redirect to index
            response.sendRedirect("index");
        }
    %>

	<h1>Registration Page</h1>
    <p>Please enter your username and password to register a new account.</p>
    <form method="post" action="">
        <table>
            <tr>
                <td>Username: </td>
                <td><input type="text" name="username" required></td>
            </tr>
            <tr>
                <td>Password: </td>
                <td><input type="password" name="password" required></td>
            </tr>
            <tr>
                <td><input type="submit" name="register" value="Register"></td>
            </tr>
        </table>
    </form>

    <%
    if ("POST".equals(request.getMethod())) { // check if the form is submitted
        if(request.getParameter("register") != null){
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            if (!AuthenticationUtil.isUsernameUnique(username)){ // Checks if username already exists
                out.println("<p style='color:red;'>User with this username already exists. Try another username.</p>");
            }
            else if (AuthenticationUtil.registerNewUser(username, password)){ // Registers user in database
                response.sendRedirect("login"); // Redirects to login page
            }
            else{
                out.println("<p style='color:red;'>Something went wrong.</p>");
            }
        }
    }
    %>

    <p>Already have an account? <a href="login">Login</a></p>
</body>
</html>
