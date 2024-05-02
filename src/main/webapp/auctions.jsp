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
            String username = (String) session.getAttribute("username");
        %>
        <script>
		window.onload = function() {
			var now = new Date();
			now.setDate(now.getDate() + 1);
			var year = now.getFullYear();
			var month = (now.getMonth() + 1).toString().padStart(2, '0');
			var day = now.getDate().toString().padStart(2, '0');
			var hour = now.getHours().toString().padStart(2, '0');
			var minute = now.getMinutes().toString().padStart(2, '0');
			var minDateTime = year + '-' + month + '-' + day + 'T' + hour + ':' + minute;
			document.getElementById('deadline').min = minDateTime;
		}
		</script>
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
        
        <h2>Sort and Search for Shoes:</h2>
        <form method="POST">
            <b>Sorting (order rows in auction table)</b><br>
            <label for="sortBy">Sort by:</label><br>
            <select id="sortBy" name="sortBy" required>
				<option value="shoes_id">Shoe ID</option>
                <option value="seller_username">Seller Username</option>
                <option value="name">Name</option>
                <option value="brand">Brand</option>
                <option value="color">Color</option>
                <option value="quality">Quality</option>
                <option value="size">Size</option>
                <option value="gedner">Gender</option>
				<option value="deadline">Deadline</option>
                <option value="current_price">Current Price</option>
                <option value="height">Height</option>
                <option value="is_open_toed">Is Open Toed</option>
                <option value="sport">Sport</option>
			</select><br>
            <label for="ascDesc">Ascending/Descending:</label><br>
            <select id="ascDesc" name="ascDesc" required>
				<option value="asc">Ascending</option>
				<option value="desc">Descending</option>
			</select><br>
            <br>
            <b>Searching (looks if keyword is contained in text columns)</b><br>
            <label for="sellerName">Seller Name:</label><br>
            <input type="text" id="sellerName" name="sellerName" maxLength="100"><br>
			<label for="name">Name:</label><br>
			<input type="text" id="name" name="name" maxlength="50"><br>
			<label for="brand">Brand:</label><br>
			<input type="text" id="brand" name="brand" maxlength="20"><br>
			<label for="color">Color:</label><br>
			<input type="text" id="color" name="color" maxlength="20"><br>
			<label for="quality">Quality:</label><br>
			<select id="quality" name="quality">
				<option value="">Select a quality</option>
				<option value="New">New</option>
				<option value="Used">Used</option>
				<option value="Refurbished">Refurbished</option>
			</select><br>
			<label for="size">Size:</label><br>
			<input type="number" id="size" name="size" step="0.5" min="0" max="20"><br>
			<label for="gender">Gender:</label><br>
			<select id="gender" name="gender">
				<option value="">Select a gender</option>
				<option value="M">Male</option>
				<option value="F">Female</option>
				<option value="U">Unisex</option>
			</select><br>
			<label for="deadline">Deadline:</label><br>
			<input type="datetime-local" id="deadline" name="deadline"><br>
			<label for="shoeType">Shoe Type:</label><br>
			<select id="shoeType" name="shoeType" onchange="showShoeTypeFields(this.value)">
				<option value="">Select a type</option>
				<option value="sandals">Sandals</option>
				<option value="sneakers">Sneakers</option>
				<option value="boots">Boots</option>
			</select><br>
			<div id="sandalsFields" style="display: none;">
				<label for="isOpenToed">Is Open Toed:</label><br>
				<select id="isOpenToed" name="isOpenToed">
					<option value="">Select an option</option>
					<option value="true">True</option>
					<option value="false">False</option>
				</select><br>
			</div>
			<div id="sneakersFields" style="display: none;">
				<label for="sport">Sport:</label><br>
				<input type="text" id="sport" name="sport" maxlength="20"><br>
			</div>
			<div id="bootsFields" style="display: none;">
				<label for="height">Height:</label><br>
				<input type="number" id="height" name="height" min="0.1" step="0.1"><br>
			</div>
            <br>
            <input type="submit" name="sortSearch" value="Sort/Search">
        </form>

        <script>
	function showShoeTypeFields(shoeType) {
		document.getElementById('sandalsFields').style.display = 'none';
		document.getElementById('sneakersFields').style.display = 'none';
		document.getElementById('bootsFields').style.display = 'none';

		document.getElementById('isOpenToed').required = false;
		document.getElementById('sport').required = false;
		document.getElementById('height').required = false;

		if (shoeType === 'sandals') {
			document.getElementById('sandalsFields').style.display = 'block';
		} else if (shoeType === 'sneakers') {
			document.getElementById('sneakersFields').style.display = 'block';
		} else if (shoeType === 'boots') {
			document.getElementById('bootsFields').style.display = 'block';
		}
	}
	</script>
            <h2> All Auctions </h2>
            <%
            if ("POST".equals(request.getMethod())) {
				// If user clicks on the login button
				if(request.getParameter("sortSearch") != null){
					String sortBy = request.getParameter("sortBy");
					String ascDesc = request.getParameter("ascDesc");

					String searchShoeType = request.getParameter("shoeType");
					String searchSellerUsername = request.getParameter("sellerName");
					String searchName = request.getParameter("name");
					String searchBrand = request.getParameter("brand");
					String searchColor = request.getParameter("color");
					String searchQuality = request.getParameter("quality");
					float searchSize = request.getParameter("size").equals("") ? -1.0f : Float.parseFloat(request.getParameter("size"));
					char searchGender = request.getParameter("gender").equals("") ? 'N' : request.getParameter("gender").charAt(0);
					LocalDateTime searchDeadline = request.getParameter("deadline").equals("") ? null : LocalDateTime.parse(request.getParameter("deadline"), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    
                    ArrayList<String[]> res = AuctionUtil.displayShoesAuction(username, sortBy, ascDesc, searchShoeType, searchSellerUsername, searchName, searchBrand, searchColor, searchQuality, searchSize, searchGender, searchDeadline);
					if (res != null && res.size() != 0) {
            %>
                        <table>
                            <tr>
                                <td><h3>Shoes ID</h3></td>
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
                                <td><h3>Shoe Type</h3></td>
                                <td><h3>Height</h3></td>
                                <td><h3>Is Open Toed</h3></td>
                                <td><h3>Sport</h3></td>
                            </tr>
                <%
                        for (int i = 0; i < res.size(); i++) {
                            String shoesId = res.get(i)[0];
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
                            String shoeType = res.get(i)[11];
                            String height = res.get(i)[12];
                            String isOpenToed = res.get(i)[13];
                            String sport = res.get(i)[14];
                %>
            <tr>
                <td><a href="auction?shoesId=<%= shoesId %>">Auction #<%= shoesId %></a></td>
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
                <td><%=shoeType %></td>
                <td><%=height %></td>
                <td><%=isOpenToed %></td>
                <td><%=sport %></td>
            </tr>
                <%
                        }
					}
					else{
						out.println("<p style='color:red'>No shoes found.</p>");
					}
				}
			}

            
            %>
        </table>
    </body>
</html>