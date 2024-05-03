<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.cs336.pkg.*"%>
<%@ page import="com.cs336.pkg.models.ShoesAuction" %>
<%@ page import="com.cs336.pkg.models.SandalsAuction" %>
<%@ page import="com.cs336.pkg.models.BootsAuction" %>
<%@ page import="com.cs336.pkg.models.SneakersAuction" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<h2>All Auctions</h2>
<%
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    ArrayList<ShoesAuction> auctions = AuctionUtil.showAllAuctions();
    if(auctions.size() == 0){
        out.println("<p>You have no auctions.</p>");
    } else {
%>
        <table>
            <tr>
                <th>Shoes ID</th>
                <th>Username</th>
                <th>Type</th>
                <th>Name</th>
                <th>Brand</th>
                <th>Deadline</th>
            </tr>
            <% for(ShoesAuction auction : auctions) { %>
                <tr>
                    <td><%= auction.getShoesId() %></td>
                    <td><%= auction.getSellerUsername() %></td>
                    <td><%= auction.getClass().getSimpleName().replace("Auction", "") %></td>
                    <td><%= auction.getName() %></td>
                    <td><%= auction.getBrand() %></td>
                    <td><%= auction.getDeadline().format(formatter) %></td>
                </tr>
            <% } %>
        </table>
<%
    }
%>