<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.cs336.pkg.*"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>

<%
    // Check if a session exists. If it does, invalidate it.
    if (session != null) {
        session.invalidate();
    }

    // Redirect to the login page
    response.sendRedirect("login");
%>