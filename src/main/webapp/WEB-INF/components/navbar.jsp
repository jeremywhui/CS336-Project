<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.cs336.pkg.*"%>
<%@ page import="com.cs336.pkg.models.ShoesAuction" %>
<%@ page import="com.cs336.pkg.models.SandalsAuction" %>
<%@ page import="com.cs336.pkg.models.BootsAuction" %>
<%@ page import="com.cs336.pkg.models.SneakersAuction" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<div id="navbar">
    <a href="index">Home</a> |
    <a href="auctions">Auctions</a> |
    <a href="alerts">Alerts</a> |
    <a href="questions">Questions</a> |
    <% String role = (String) session.getAttribute("role"); %>
    <% if ("Admin".equals(role)) { %>
        <a href="admin">Admin</a> |
    <% } %>
    <% if ("Customer Representative".equals(role)){ %>
    	<a href="customerrep">Customer Rep</a> |
    <% } %>
    <a href="logout">Logout</a> |
    <span>
        <strong><%= session.getAttribute("username") %></strong>
        <% if (role == null) { %>
            (End User)
        <% } else { %>
            (<%= role %>)
        <% } %>
    </span>
</div>
<%
    AuctionUtil.updateShoesAuction();
%>