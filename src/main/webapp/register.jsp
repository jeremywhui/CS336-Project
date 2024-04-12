<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.cs336.pkg.*, java.sql.*"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title> Register </title>
</head>
<body>
    <%
        if(session.getAttribute("username") != null){ // If user is already logged in, redirect to index.jsp
            response.sendRedirect("index.jsp");
        }
    %>

	<h1>Registration Page</h1>
    <p>Please enter your email, username, and password to register a new account.</p>
    <form method="post" action="">
        <table>
            <tr>
                <td>Email: </td>
                <td><input type="text" name="email"></td>
            </tr>
            <tr>
                <td>Username: </td>
                <td><input type="text" name="username"></td>
            </tr>
            <tr>
                <td>Password: </td>
                <td><input type="password" name="password"></td>
            </tr>
            <tr>
                <td><input type="submit" name="register" value="Register"></td>
            </tr>
        </table>
    </form>

    <%
    if ("POST".equals(request.getMethod())) { // check if the form is submitted
        if(request.getParameter("register") != null){
            String email = request.getParameter("email");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            if (!AuthenticationUtil.validateRegistrationInput(email, username, password)){ // Checks if email, username, and password are not empty
                out.println("<p style='color:red;'>Please fill out all fields.</p>");
            }
            else if (!AuthenticationUtil.isEmailUnique(email)){ // Checks if email already exists
                out.println("<p style='color:red;'>User with this email already exists. Try another email.</p>");
            }
            else if (!AuthenticationUtil.isUsernameUnique(username)){ // Checks if username already exists
                out.println("<p style='color:red;'>User with this username already exists. Try another username.</p>");
            }
            else if (AuthenticationUtil.registerNewUser(email, username, password)){ // Registers user in database
                response.sendRedirect("login.jsp"); // Redirects to login page
            }
            else{
                out.println("<p style='color:red;'>Something went wrong.</p>");
            }
        }
    }
    %>

    <p>Already have an account? <a href="login.jsp">Login</a></p>
</body>
</html>
