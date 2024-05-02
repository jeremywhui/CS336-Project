<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.cs336.pkg.*"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Auctions</title>
</head>
    <body>
        <%
            if ((session.getAttribute("username") == null)) { // If user is not logged in, redirect to login page
                response.sendRedirect("login");
            }
            String shoesIdParam = request.getParameter("shoesId");
            if (shoesIdParam == null || shoesIdParam.isEmpty()) {
        %>
                <p>No shoesId provided.</p>
        <%
            } else {
                int shoesId = Integer.parseInt(shoesIdParam);
        %>
                <jsp:include page="/WEB-INF/components/navbar.jsp" />
                <br>
                <a href="auctions">Back</a>
                <h1>Auction #<%= shoesId %></h1>
                <%
                    if (!AuctionUtil.isShoesAuction(shoesId)) {
                %>
                        <p>This is not a valid auction.</p>
                <%
                    } else if (AuctionUtil.isSale(shoesId)) {
                %>
                        <p>This auction has already been sold.</p>
                <%
                    } else {
                        // Display the regular auction content
                %>
                        <!-- Rest of your auction content goes here -->
                <%
                    }
                }
            %>
        </body>
</html>