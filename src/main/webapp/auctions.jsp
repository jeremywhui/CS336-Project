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
                background-color: Grey
		    }
            tr:nth-child(even) { 
                background-color: Lightgreen; 
            } 
		</style>

        <form method="POST">
            <%-- <label for="shoeID">Shoe ID:</label><br>
			<input type="number" id="shoeID" name="shoeID" step="1" onchange="calculateMinBidPrice(this.value)" required><br>
            <label for="bid">Bid:</label><br>
			<input type="number" id="bid" name="bid" step="0.01" min="" max="9999999999.99" required><br> --%>
            <label for="sortBy">Sort by:</label><br>
            <select id="sortBy" name="sortBy" required>
                <option value="">Select a sort</option>
				<option value="shoes_id">Shoe ID</option>
				<option value="name">Name</option>
                <option value="quality">Quality</option>
				<option value="deadline">Deadline</option>
                <option value="current_price">Current Price</option>
			</select><br>
            <label for="ascDesc">Ascending/Descending:</label><br>
            <select id="ascDesc" name="ascDesc" required>
                <option value="">Select Ascending/Descending</option>
				<option value="asc">Ascending</option>
				<option value="desc">Descending</option>
			</select><br>
            <br>
			<input type="submit" name="sort" value="Sort">
        </form>

        <%-- <script>
        function calculateMinBidPrice(shoeID) {
            document.getElementById('bid').min = <%= AuctionUtil.calculateMinBidPrice(shoeID) %>
        }
        </script> --%>

        <h2> All Auctions </h2>

        <table>
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
            if ("POST".equals(request.getMethod())) {
				// If user clicks on the login button
				if(request.getParameter("sort") != null){
					String sortBy = request.getParameter("sortBy");
					String ascDesc = request.getParameter("ascDesc");
                    ArrayList<String[]> res = AuctionUtil.displayShoesAuction(sortBy, ascDesc);
					if (res != null) {
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
                <td><%=shoeID %></td>
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
                <%
                        }
					}
					else{
						out.println("<p style='color:red;'>No shoes found</p>");
					}
				}
			}

            
            %>
        </table>
    </body>
</html>