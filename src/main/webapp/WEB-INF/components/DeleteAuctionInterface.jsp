<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.cs336.pkg.*"%>
<%@ page import="com.cs336.pkg.models.ShoesAuction" %>
<%@ page import="com.cs336.pkg.models.SandalsAuction" %>
<%@ page import="com.cs336.pkg.models.BootsAuction" %>
<%@ page import="com.cs336.pkg.models.SneakersAuction" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<h2>All Auctions</h2>
<%
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    if (request.getParameter("deleteAuctionSubmit") != null) {
        int shoesId = Integer.parseInt(request.getParameter("shoes_id"));
        boolean isDeleted = AuctionUtil.deleteAuction(shoesId);

        if (isDeleted) {
            session.setAttribute("message", "<p style='color:green;'>Auction deleted successfully.</p>");
        } else {
            session.setAttribute("message", "<p style='color:red;'>Failed to delete auction.</p>");
        }
        response.sendRedirect(request.getRequestURI());
    }

    ArrayList<ShoesAuction> auctions = AuctionUtil.showAllAuctions();
    if(auctions.size() == 0){
        out.println("<p>There are no auctions to delete.</p>");
    } else {
        String message = (String) session.getAttribute("message");
        session.removeAttribute("message");
%>
        <table>
            <tr>
                <th>Shoes ID</th>
                <th>Username</th>
                <th>Type</th>
                <th>Name</th>
                <th>Brand</th>
                <th>Deadline</th>
                <th>Action</th>
            </tr>
            <% for(ShoesAuction auction : auctions) { %>
                <tr>
                    <td><%= auction.getShoesId() %></td>
                    <td><%= auction.getSellerUsername() %></td>
                    <td><%= auction.getClass().getSimpleName().replace("Auction", "") %></td>
                    <td><%= auction.getName() %></td>
                    <td><%= auction.getBrand() %></td>
                    <td><%= auction.getDeadline().format(formatter) %></td>
                    <td>
                        <form method="post">
                            <input type="hidden" name="shoes_id" value="<%= auction.getShoesId() %>">
                            <input type="submit" name="deleteAuctionSubmit" value="Delete Auction">
                        </form>
                    </td>
                </tr>
            <% } %>
        </table>
<%
        if (message != null) {
            out.println(message);
        }
    }
%>