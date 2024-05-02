<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.cs336.pkg.*"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ page import="com.cs336.pkg.models.ShoesAuction" %>
<%@ page import="com.cs336.pkg.models.SandalsAuction" %>
<%@ page import="com.cs336.pkg.models.BootsAuction" %>
<%@ page import="com.cs336.pkg.models.SneakersAuction" %>
<%@ page import="com.cs336.pkg.models.Bid" %>
<%@ page import="com.cs336.pkg.models.AutoBid" %>
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
            String username = (String) session.getAttribute("username");
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
                    } else if (AuctionUtil.isOwnShoesAuction(shoesId, username)) {
                %>
                        <p>You cannot bid on your own auction.</p>
                <%
                    } else if (AuctionUtil.isSale(shoesId)) {
                %>
                        <p>This auction has already been sold.</p>
                <%
                    } else {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                        ShoesAuction shoesAuction = AuctionUtil.getShoesAuction(shoesId);
                        String sellerUsername = shoesAuction.getSellerUsername();
                        String name = shoesAuction.getName();
                        String brand = shoesAuction.getBrand();
                        String color = shoesAuction.getColor();
                        String quality = shoesAuction.getQuality();
                        float size = shoesAuction.getSize();
                        char gender = shoesAuction.getGender();
                        String deadline = shoesAuction.getDeadline().format(formatter);
                        double minBidIncrement = shoesAuction.getMinBidIncrement();
                        
                        double currentHighestBid = BidUtil.getHighestBidAmount(shoesId);
                        double currentMinNextBid = BidUtil.getMinBidAmount(shoesId);

                        ArrayList<Bid> bidHistory = BidUtil.getBidHistory(shoesId);
                        AutoBid userAutoBid = BidUtil.getAutoBid(username, shoesId);
                %>
                        <h2>Shoes Details</h2>
                        <p>Seller Username: <%= sellerUsername %></p>
                        <p>Name: <%= name %></p>
                        <p>Brand: <%= brand %></p>
                        <p>Color: <%= color %></p>
                        <p>Quality: <%= quality %></p>
                        <p>Size: <%= size %></p>
                        <p>Gender: <%= gender %></p>
                        <p>Deadline: <%= deadline %></p>
                        <p>Current Highest Bid: <%= currentHighestBid %></p>
                        <p>Minimum Bid Increment: <%= minBidIncrement%></p>
                        <p>Minimum Next Bid: <%= currentMinNextBid %></p>

                        <h2>Bid History</h2>
                        <table>
                            <tr>
                                <th>Bidder Username</th>
                                <th>Time of Bid</th>
                                <th>Bid Amount</th>
                            </tr>
                            <% for (Bid bid : bidHistory) { %>
                                <tr>
                                    <td><%= bid.getBidderUsername() %></td>
                                    <td><%= bid.getTimeOfBid().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) %></td>
                                    <td><%= bid.getBidAmount() %></td>
                                </tr>
                            <% } 
                            
                            
                        if (QuestionUtil.checkUser(username) == 3){ %>
                        	</table>
                            <h2>Your Automatic Bid (if exits)</h2>
                        <% if (userAutoBid != null) { %>
                        <table>
                            <tr>
                                <th>Bid Increment</th>
                                <th>Bid Maximum</th>
                            </tr>
                            
                                <tr>
                                    <td><%= userAutoBid.getBidIncrement() %></td>
                                    <td><%= userAutoBid.getBidMaximum() %></td>
                                </tr>
                        </table>
                         <% } %>

                        <h2>Place a Manual Bid</h2>
                            <form method="POST">
                                <label for="bid">Bid:</label><br>
                                <input type="number" id="bid" name="bid" step="0.01" min="<%= currentMinNextBid %>" max="9999999999.99" required><br>
                                <br>
                                <input type="submit" name="placeManualBid" value="Place Bid">
                            </form>

                            <%
                                if ("POST".equals(request.getMethod())) {
                                    if (request.getParameter("placeManualBid") != null) {
                                        float bidAmount = Float.parseFloat(request.getParameter("bid"));
                                        Bid bid = new Bid(shoesId, username, LocalDateTime.now(), bidAmount);
                                        if (bidAmount >= currentMinNextBid) {
                                            if(BidUtil.placeBid(bid)) {
                                                response.sendRedirect("auction?shoesId=" + shoesId);
                                            } else {
                                                out.println("<p style='color: red;'>Failed to place bid.</p>");
                                            }
                                        } else {
                                            out.println("<p style='color: red;'>Bid amount must be greater than or equal to the current minimum next bid amount.</p>"); // should never be displayed
                                        }
                                    } 
                        	
                        		}
                            }
                       else if (QuestionUtil.checkUser(username) == 2){ %>
                    	   </table>
                           <h2>Delete Bid</h2>
                           <form method="POST">
                               Fill in the information for the bid you want to delete <br>
                               Bidder Username: 
                               <input type = "text" name = "bidder_username" required> <br> 
                               Bidding Time:
                               <input type = "text" name = "time" required> <br>
                               <input type="submit" name="deleteBid" value="Delete Bid">
                           </form>

                           <%
                               if ("POST".equals(request.getMethod())) {
                                   if (request.getParameter("deleteBid") != null) {
                                	   String bidder_username = request.getParameter("bidder_username");
                                	   String bidding_time = request.getParameter("time");
                                	   if (!BidUtil.existsInTable(shoesId, bidder_username, bidding_time)){
                                		   out.println("<p style='color: red;'> Error, inputs not valid </p>");
                                	   }
                                	   else if (BidUtil.deleteBid(shoesId, bidder_username, bidding_time)){
                                		   out.println("bid deleted success");
                                	   }
                                	   else{
                                		   out.println("<p style='color: red;'> Some sort of error happened :( </p>");
                                	   }
                                   } 
                       	
                       		}
                    	   
                    	   
                    	   
                       }
                       else if (QuestionUtil.checkUser(username) == 1){
                    	   // admin
                       }
                        %>
                        <h2>Place an Automatic Bid</h2>
                        <form method="POST">
                            <label for="bid">Bid Increment:</label><br>
                            <input type="number" id="bidIncrement" name="bidIncrement" step="0.01" min="<%= minBidIncrement %>" max="9999999999.99" required><br>
                            <label for="bid">Bid Maximum:</label><br>
                            <input type="number" id="bidMaximum" name="bidMaximum" step="0.01" min="<%= currentMinNextBid %>" max="9999999999.99" required><br>
                            <br>
                            <input type="submit" name="setAutoBid" value="Set Automatic Bid">
                        </form>
                        <%
                        if ("POST".equals(request.getMethod())) {
                                if (request.getParameter("setAutoBid") != null) {
                                    float bidIncrement = Float.parseFloat(request.getParameter("bidIncrement"));
                                    float bidMaximum = Float.parseFloat(request.getParameter("bidMaximum"));
                                    AutoBid bid = new AutoBid(shoesId, username, bidIncrement, bidMaximum);
                                    if (bidIncrement >= minBidIncrement && bidMaximum >= currentMinNextBid) {
                                        if(BidUtil.setAutoBid(bid)) {
                                            response.sendRedirect("auction?shoesId=" + shoesId);
                                        } else {
                                            out.println("<p style='color: red;'>Failed to place automatic bid.</p>");
                                        }
                                    } else {
                                        out.println("<p style='color: red;'>Bid increment must be greater than or equal to the minimum bid increment, and the bid maximum must be greater than or equal to the next bid amount.</p>"); // should never be displayed
                                    }
                                } 
                            }
                    }
                }
                %>
        </body>
</html>