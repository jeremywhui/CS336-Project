<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.cs336.pkg.*"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
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
        %>
        <jsp:include page="/WEB-INF/components/navbar.jsp" />
        <h1>Home Page</h1>
        <p>Welcome, <%= session.getAttribute("username")%>!</p>
        <h3>Post Shoes for Sale!</h3>
        <form method="post">
        	<label for="shoeName">Name:</label><br>
			<input type = "text" id="shoeName" name = "shoeName" required maxlength="50"><br>
			<label for="brand">Brand:</label><br>
			<input type = "text" id="brand" name = "brand" required maxlength="20"><br>
			<label for="color">Color:</label><br>
			<input type = "text" id="color" name = "color" required maxlength="20"><br>
			<label for="quality">Quality:</label><br>
			<select name="quality" required>
			<option value=""></option>
			<option value="new"> New </option>
			<option value="veryGood"> Very Good </option>
			<option value="good"> Good </option>
			<option value="acceptable"> Acceptable </option>
  			</select><br>
			<label for="size">Size:</label><br>
			<input type = "number" min="1" max="20" step="0.5" id="size" name = "size" required><br>
			<label for="gender">Gender:</label><br>
			<select name="gender" required>
			<option value=""></option>
			<option value="male"> Male </option>
			<option value="female"> Female </option>                 
  			</select><br>
			<label for="closingDateTime">Closing Date/Time (YYYY-MM-DD HH:MI:SS):</label><br>
			<input type = "datetime-local" id="closingDateTime" name = "closingDateTime" required><br>
			<label for="minBidIncrement"></label>Minimum Bid Increment:<br>
			<input type = "number" min="0.01" max="9999999.99" step="0.01" id="hiddenMinPrice" name = "hiddenMinPrice" required><br>
			<label for="hiddenMinPrice"></label>Hidden Minimum Price (reserve):<br>
			<input type = "number" min="0.01" max="9999999.99" step="0.01" id="hiddenMinPrice" name = "hiddenMinPrice" required><br>
			<label for="shoeType">Shoe Type</label><br>
			<select name="shoeType" id="shoeType" onchange="showDiv(this)" required>
			<option value=""></option>
			<option value="boots">Boots</option>
			<option value="sandals">Sandals</option>
			<option value="sneakers">Sneakers</option>
			</select><br>
			<div id="boots" style="display: none">
				<label for="height">Height (inches):</label><br>
				<input type="number" min="0" max="100" step="any"><br>
			</div>
			<div id="sandals" style="display: none">
				<label for="openToed">Open Toed:</label><br>
				<select>
				<option value=""></option>
				<option value="true"> Yes </option>
				<option value="false"> No </option>
				</select><br>
			</div>
			<div id="sneakers" style="display: none">
				<label for="sport">Sport:</label><br>
				<input type="text"><br>
			</div>
			<br>
			<input type = "submit" name = "createAuction" value = "Create Auction">
		</form>

		<script>
		<%-- Functionality to show shoe specific options --%>
		function showDiv(element)
		{
			document.getElementById("boots").style.display = element.value == "boots" ? 'block' : 'none';
			document.getElementById("sandals").style.display = element.value == "sandals" ? 'block' : 'none';
			document.getElementById("sneakers").style.display = element.value == "sneakers" ? 'block' : 'none';
		}
		</script>

		<%
			// Check if the request was a POST request
			if ("POST".equals(request.getMethod())) {
				// If user clicks on the login button
				if(request.getParameter("createAuction") != null){
					boolean allFieldsFilled = true;

					String shoeName = request.getParameter("shoeName");
					String brand = request.getParameter("brand");
					String color = request.getParameter("color");
					String quality = request.getParameter("quality");
					String size = request.getParameter("size");
					String gender = request.getParameter("gender").substring(0, 1);
					String closingDateTime = request.getParameter("closingDateTime");
					String minBidIncrement = request.getParameter("minBidIncrement");
					String hiddenMinPrice = request.getParameter("hiddenMinPrice");
					String shoeType = request.getParameter("shoeType");
					String height = "", sport = "", openToed = "";
					if (shoeType.equals("boots")) {
						out.println("<p style='color:black;'>Boots!</p>");
						height = (String) request.getParameter("height");
						out.println(request.getParameter("height"));
						if (height == null || height.equals("")) allFieldsFilled = false;
					} else if (shoeType.equals("sandals")) {
						out.println("<p style='color:black;'>Sandals!</p>");
						openToed = request.getParameter("openToed");
						if (openToed == null || openToed.equals("")) allFieldsFilled = false;
					} else {
						out.println("<p style='color:black;'>Sneakers!</p>");
						sport = request.getParameter("sport");
						if (sport == null || sport.equals("")) allFieldsFilled = false;
					}
					if (allFieldsFilled) {
						if (!CreateAuction.createShoeListing(shoeName, brand, color, quality, size, gender, closingDateTime, minBidIncrement, hiddenMinPrice, shoeType, height, openToed, sport, (String) session.getAttribute("username"))){
							out.println("<p style='color:black;'>Auction created.</p>");
						}
						else{
							out.println("<p style='color:black;'>Cannot create auction. Try again.</p>");
						}
					} else {
						out.println("<p style='color:red;'>Please fill in empty fields.</p>");
					}
				}
			}
		%>
    </body>
</html>