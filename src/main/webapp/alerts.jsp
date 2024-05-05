<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.cs336.pkg.*"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<%@ page import="com.cs336.pkg.models.Alert"%>
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

        <% AlertUtil.checkWinner(); %>
        <% 
        ArrayList<AuctionAlert> alerts = AlertUtil.getAlerts(username); %>
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
                    <td><%=alert.getText()%></td>
                </tr>
            <% } %>
        </table>
        <h2>Set Preferences for Shoe Alerts</h2>
        <form method="POST">
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
				<option value="U">Unisex</optifon>
			</select><br>
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
            <input type="submit" name="createPreference" value="Create Preference">
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

        <%
            ArrayList<String[]> res = AlertUtil.getUserPreferences(username);
            // checks if a form was submitted
            if ("POST".equals(request.getMethod())) {
                if (request.getParameter("createPreference") != null) {
                    String searchName = request.getParameter("name");
                    String searchBrand = request.getParameter("brand");
                    String searchColor = request.getParameter("color");
                    String searchQuality = request.getParameter("quality");
                    float searchSize = request.getParameter("size").equals("") ? -1.0f : Float.parseFloat(request.getParameter("size"));
                    char searchGender = request.getParameter("gender").equals("") ? 'N' : request.getParameter("gender").charAt(0);

                    // checks for shoe type + properties
                    String isOpenToed = request.getParameter("isOpenToed").equals("") ? null : request.getParameter("isOpenToed");
                    double height = request.getParameter("height").equals("") ? -1.0: Double.parseDouble(request.getParameter("height"));
                    String sport = request.getParameter("sport");
                    AlertUtil.setUserPreferences(username, searchName, searchBrand, searchColor, searchQuality, searchSize, searchGender, isOpenToed, height, sport);
                }
            }
            %>

                <h2>Your Preferences (if exists)</h2>
                <% if (res.size() != 0) { %>
                <table>
                    <tr>
                        <td><h3>Name</h3></td>
                        <td><h3>Brand</h3></td>
                        <td><h3>Color</h3></td>
                        <td><h3>Quality</h3></td>
                        <td><h3>Size</h3></td>
                        <td><h3>Gender</h3></td>
                        <td><h3>Is Open Toed</h3></td>
                        <td><h3>Height</h3></td>
                        <td><h3>Sport</h3></td>
                    </tr>
                    <%
                        for (String[] preference : res) {
                    %>
                        <tr>
                    <%
                            for (String value : preference) {
                    %>
                                    <td><%=value == null ? "Any" : value%></td>
                    <%
                            }
                    %>
                        </tr>
                    <%
                        }
                }
        %>
        </table>
        
    </body>
</html>