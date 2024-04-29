<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.cs336.pkg.*"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
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
        %>
        <jsp:include page="/WEB-INF/components/navbar.jsp" />
        <h1>Auctions Page</h1>
        <style>
		    table {
		        border: 1px solid gray;
		        border-collapse: collapse;
		    }
		    th, td {
		        width: 250px;
		        height: 30px;
		        border: 1px solid gray;
		        padding: 2px;
		    }
		    tr:first-child td{
		        height: 50px;
		    }
		</style>
        
        <table>
            <h2> All Auctions </h2>
            <tr>
                <td><h3>Shoe ID</h3></td>
                <td><h3>Seller Username</h3></td>
                <td><h3>Name</h3></td>
                <td><h3>Brand</h3></td>
                <td><h3>Color</h3></td>
                <td><h3>Quality</h3></td>
                <td><h3>Size</h3></td>
                <td><h3>Gender</h3></td>
                <td><h3>Deadline</h3></td>
                <td><h3>Min Bid Increment</h3></td>
                <td><h3>Current Price</h3></td>
                <td><h3>Height</h3></td>
                <td><h3>Is Open Toed</h3></td>
                <td><h3>Sport</h3></td>
            </tr>
            <%
            ArrayList<String[]> res = AuctionUtil.displayShoesAuction();

            for (int i = 0; i < res.size(); i++) {
                String shoeID = res.get(i)[0];
                String sellerUsername = res.get(i)[1];
                String name = res.get(i)[2];
                String brand = res.get(i)[3];
                String color = res.get(i)[4];
                String quality = res.get(i)[5];
                String size = res.get(i)[6];
                String gender = res.get(i)[7];
                String deadline = res.get(i)[8];
                String minBidIncrement = res.get(i)[9];
                String currentPrice = res.get(i)[10];
                String height = res.get(i)[11];
                String isOpenToed = res.get(i)[12];
                String sport = res.get(i)[13];
            %>
            <tr>
                <td><%=shoeId %></td>
                <td><%=sellerUsername %></td>
                <td><%=name %></td>
                <td><%=brand %></td>
                <td><%=color %></td>
                <td><%=quality %></td>
                <td><%=size %></td>
                <td><%=gender %></td>
                <td><%=deadline %></td>
                <td><%=minBidIncrement %></td>
                <td><%=currentPrice %></td>
                <td><%=height %></td>
                <td><%=isOpenToed %></td>
                <td><%=sport %></td>
            </tr>
            <% } %>
        </table>
    </body>
</html>