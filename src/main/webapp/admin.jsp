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
    </body>
</html>