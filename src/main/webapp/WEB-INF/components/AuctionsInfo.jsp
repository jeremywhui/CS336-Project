<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.cs336.pkg.*"%>
<%@ page import="com.cs336.pkg.models.ShoesAuction" %>
<%@ page import="com.cs336.pkg.models.SandalsAuction" %>
<%@ page import="com.cs336.pkg.models.BootsAuction" %>
<%@ page import="com.cs336.pkg.models.SneakersAuction" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<h2>Your Auctions</h2>
<p>Here are the auctions you have posted. They include both past and present auctions:</p>
<%
    String username = request.getParameter("username");
    ArrayList<ShoesAuction> shoesAuctions = AuctionUtil.getShoesAuctionsBySeller(username);
%>

<% if (shoesAuctions.isEmpty()) { %>
    <p>You have not posted any auctions.</p>
<% } else { %>
    <table>
        <tr>
            <th>Shoes ID</th>
            <th>Brand</th>
            <th>Name</th>
            <th>Highest Bid/Sale</th>
            <th>Is Active</th>
            <!-- Add more columns as needed -->
        </tr>
        <% for (ShoesAuction auction : shoesAuctions) { %>
            <tr>
                <td><%= auction.getShoesId() %></td>
                <td><%= auction.getBrand() %></td>
                <td><%= auction.getName() %></td>
                <td><%= BidUtil.getHighestBidAmount(auction.getShoesId()) %></td>
                <td><%= AuctionUtil.isActive(auction.getShoesId()) && !AuctionUtil.isSale(auction.getShoesId()) %></td>
            </tr>
        <% } %>
    </table>
<% } %>