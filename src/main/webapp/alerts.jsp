<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.cs336.pkg.*"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ page import="com.cs336.pkg.models.AuctionAlert" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Alerts</title>
</head>
    <body>
        <%
            if ((session.getAttribute("username") == null)) { // If user is not logged in, redirect to login page
                response.sendRedirect("login");
            }
            String username = (String) session.getAttribute("username");
        %>
        <jsp:include page="/WEB-INF/components/navbar.jsp" />
        <h1>Alerts Page</h1>
        <p>Welcome, <%= username%>!</p>

        <% 
        ArrayList<AuctionAlert> alerts = BidUtil.getAlerts(username); %>
        <h2>Your Alerts for Auctions</h2>
        <table>
            <tr>
                <th>Time of Alert</th>
                <th>Shoes ID</th>
                <th>Information</th>
            </tr>
            <% for (AuctionAlert alert : alerts) { %>
                <tr>
                <td><%= alert.getTimeOfAlert().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) %></td>
                    <% int shoesId = alert.getShoesId(); %>
                    <td><a href="auction?shoesId=<%= shoesId %>">Auction #<%= shoesId %></a></td>
                    <td><%= "A higher bid than your " + (alert.isAutomatic() ?  "automatic" : "manual") + " bid has been placed! " + (alert.isAutomatic() ? "Your automatic bid has been removed." : "")%></td>
                </tr>
            <% } %>
        </table>
    </body>
</html>