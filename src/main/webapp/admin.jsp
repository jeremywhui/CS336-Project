<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.cs336.pkg.*"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin</title>
</head>
    <body>
        <%
            if ((session.getAttribute("username") == null)) { // If user is not logged in, redirect to login page
                response.sendRedirect("login");
            }
            if (!"Admin".equals((String) session.getAttribute("role"))) { // If user is not an admin, redirect to index
                response.sendRedirect("index");
            }
        %>
        <jsp:include page="/WEB-INF/components/navbar.jsp" />
        <h1>Admin Page</h1>
        <p>Please enter a username and password to register a new customer representative.</p>
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
                else if (AuthenticationUtil.registerNewCustomerRep(username, password)){ // Registers customer representative in database
                    out.println("<p style='color:green;'>Customer representative '" + username + "' registered successfully.</p>");
                }
                else{
                    out.println("<p style='color:red;'>Something went wrong.</p>");
                }
            }
        }
        %>
    </body>
</html>