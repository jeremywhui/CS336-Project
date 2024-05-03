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
<!--<%@ page import="java.text.ParseException, java.text.SimpleDateFormat" %>-->
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home</title>
</head>
    <body>
        <%
		if ((session.getAttribute("username") == null)) { // If user is not logged in, redirect to login page
			response.sendRedirect("login");
		}
		String username = (String)session.getAttribute("username");
        %>
        <jsp:include page="/WEB-INF/components/navbar.jsp" />
        <%
		if (QuestionUtil.checkUser(username) == 3){ 
			// user is an End User
		%>
			<jsp:include page="/WEB-INF/components/CreateAuctionForm.jsp" />
		<%
		}
        
        else if (QuestionUtil.checkUser(username) == 2) { 
			// user is a customer representative 
		%>
        	<br>
        	<form method="post">
                <h4>Shoes ID Number:</h4>
                <input type = "number" name = "shoes_id" pattern="\d+" required>
                <br>
                <br><input type="submit" name="deleteAuctionSubmit" value="Delete Auction">
                <br>
            </form>    
            <br>
        	
		<%
			if ("POST".equals(request.getMethod())) {
				int shoes_id = Integer.parseInt(request.getParameter("shoes_id"));
				if (!AuctionUtil.existsInTable(shoes_id)){ // Checks shoes_id is a valid shoes_id
					out.println("<p style='color:red;'>Not a valid Auction</p>");
				}
				else if (AuctionUtil.deleteAuction(shoes_id)){ // deletes auction from databse
					response.sendRedirect("index");
					out.println("success");
				}
				else{
						out.println("<p style='color:red;'>Error Try again.</p>");
				}
			}
		%>
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
        }
        
        else if (QuestionUtil.checkUser(username) == 1){ 
			// user is an Admin 
		%>        
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
        }
        else { // idk how but you messed something up
        	out.println("<p style='color:red;'> Error somehow, frankly idk how you made it to this </p>");
        }
        %>
    </body>
</html>