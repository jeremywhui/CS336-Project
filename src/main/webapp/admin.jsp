<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.cs336.pkg.*"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin</title>
</head>
    <body>
        <%
            if ((session.getAttribute("username") == null)) { // If user is not logged in, redirect to login page
                response.sendRedirect("login");
            }
            if (!"Admin".equals((String) session.getAttribute("role"))) { // If user is not an admin, redirect to index
                response.sendRedirect("index");
            }
        %>
        <jsp:include page="/WEB-INF/components/navbar.jsp" />
        <h1>Admin Page</h1>
        <h3>Please enter a username and password to register a new customer representative.</h3>
        <form method="post" action="">
            <table>
                <tr>
                    <td>Username: </td>
                    <td><input type="text" name="username" required></td>
                </tr>
                <tr>
                    <td>Password: </td>
                    <td><input type="password" name="password" required></td>
                </tr>
                <tr>
                    <td><input type="submit" name="register" value="Register"></td>
                </tr>
            </table>
        </form>
        
        <h3> Generate Reports </h3>
        <form method = "post" action="">
        	<table>
        		<tr>
        			<td> <input type = "submit" name = "generateReports" value = "Generate Total Earnings Reports"></td>
        			<td> <input type = "submit" name = "bestSellingItemReport" value = "Generate Best Selling Items Report"> 
        		</tr>
        		<tr>
        			<td> <select name = "itemReport">
        					<option value = ""></option>
        					<option value = "shoeName">Shoe Name</option>
        					<option value = "shoeBrand">Shoe Brand</option>
        				</select>
        			</td>
        			<td> <input type = "text" name = "itemReportText"> </td>
        			<td> <input type = "submit" name = "itemReportSubmit" value = "Generate Earnings per Item"> </td>
        		</tr>
        		<tr>
        			<td> <select name = "itemTypeReportSubmit">
        					<option value = ""></option>
        					<option value = "boots"> Boots </option>
        					<option value = "sneakers"> Sneakers </option>
        					<option value = "sandals"> Sandals </option>
        				</select>
        			</td>
        			<td> <input type = "submit" name = "itemTypeReportSubmit" value = "Generate Earnings per Item Type"></td>
        		</tr>
        		<tr>
        			<td> <input type = "text" name = "userReport"></td>
        			<td> <input type = "submit" name = "userReportSubmit" value = "Generate Earnings per User"></td>
        		</tr>
        	</table>
        </form>

        <%
        if ("POST".equals(request.getMethod())) { // check if the form is submitted
            if(request.getParameter("register") != null){
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                if (!AuthenticationUtil.isUsernameUnique(username)){ // Checks if username already exists
                    out.println("<p style='color:red;'>User with this username already exists. Try another username.</p>");
                }
                else if (AuthenticationUtil.registerNewCustomerRep(username, password)){ // Registers customer representative in database
                    out.println("<p style='color:green;'>Customer representative '" + username + "' registered successfully.</p>");
                }
                else{
                    out.println("<p style='color:red;'>Something went wrong.</p>");
                }
            }
        
            else if (request.getParameter("generateReports") != null){
            	if (AdminUtil.generateTotalEarnings() != -1){ %>
            		<h3> Total sales Report </h3>
            		<%if (!(AdminUtil.getSales().isEmpty())){
            			ArrayList<String[]> salesReport = AdminUtil.getSales();
            			%>
            			
            			<table>
                        <tr>
                            <td><h3>Shoes ID</h3></td>
                            <td><h3>Seller</h3></td>
                            <td><h3>Buyer</h3></td>
                            <td><h3>Name</h3></td>
                            <td><h3>Brand</h3></td>
                            <td><h3>Selling Price</h3></td>
                        </tr>
                        <%
                        for (int i = 0; i < salesReport.size(); i++) {
            				String [] salesDetails = salesReport.get(i);
                        %>
                        <tr>
                            <td><%=salesDetails[0] %></td>
                            <td><%=salesDetails[1] %></td>
                            <td><%=salesDetails[2] %></td>
                            <td><%=salesDetails[3] %></td>
                            <td><%=salesDetails[4] %></td>
                            <td><%=salesDetails[5] %></td>
                        </tr>
                        <% } %>
                        </table>
            		<%}
            		else {
            			out.println("<p style='color:red;'> Error report can't be generated</p>");
            		}
            		out.println("");
            		out.println("Total Earnings: $" + AdminUtil.generateTotalEarnings());
            		
            	}
            	else {
            		out.println("<p style='color:red;'> Error or no earnings yet</p>");
            	}
            }
        
            else if (request.getParameter("bestSellingItemReport") != null){
            	if (AdminUtil.getHighestEarning() != null){ %>
            		<h3> Best Selling Report </h3>
            		<%if (!(AdminUtil.getSales().isEmpty())){
            			ArrayList<String[]> salesReport = AdminUtil.getSales();
            			%>
            			
            			<table>
                        <tr>
                            <td><h3>Shoes ID</h3></td>
                            <td><h3>Seller</h3></td>
                            <td><h3>Buyer</h3></td>
                            <td><h3>Name</h3></td>
                            <td><h3>Brand</h3></td>
                            <td><h3>Selling Price</h3></td>
                        </tr>
                        <%
                        double sum = Double.parseDouble(salesReport.get(0)[5]);
                        for (int i = 0; i < salesReport.size(); i++) {
            				String [] salesDetails = salesReport.get(i);
                        %>
                        <tr>
                            <td><%=salesDetails[0] %></td>
                            <td><%=salesDetails[1] %></td>
                            <td><%=salesDetails[2] %></td>
                            <td><%=salesDetails[3] %></td>
                            <td><%=salesDetails[4] %></td>
                            <td><%=salesDetails[5] %></td>
                        </tr>
                        <% } %>
                        </table>
            		<%
            		out.println("Revenue from highest grossing product: $" + sum);
            		}
            		else {
            			out.println("<p style='color:red;'> Error report can't be generated</p>");
            		}
           		}
            }
        
            else if (request.getParameter("itemReportSubmit") != null){
            	String itemReportDropdown = request.getParameter("itemReport");
            	String itemReportText = request.getParameter("itemReportText"); %>
            	<h3> Item Report </h3>
            	<% if (itemReportDropdown.equals("")){
            		out.println("<p style='color:red;'> Please choose an item </p>");
            	}
            	else if (itemReportText.equals("") || itemReportText == null){
            		out.println("<p style='color:red;'> Please fill in item </p>");
            	}
            	else if (itemReportDropdown.equals("shoeName")){
            		ArrayList<String[]> shoeNameReport = AdminUtil.getShoeNameReport(itemReportText);
            		if (shoeNameReport.size() != 0){ %>
            			<table>
                        <tr>
                            <td><h3>Shoes ID</h3></td>
                            <td><h3>Seller</h3></td>
                            <td><h3>Buyer</h3></td>
                            <td><h3>Name</h3></td>
                            <td><h3>Brand</h3></td>
                            <td><h3>Selling Price</h3></td>
                        </tr>
                        <%
                        double sum = 0;
                        for (int i = 0; i < shoeNameReport.size(); i++) {
            				String [] salesDetails = shoeNameReport.get(i);
            				sum += Double.parseDouble(salesDetails[5]);
                        %>
                        <tr>
                            <td><%=salesDetails[0] %></td>
                            <td><%=salesDetails[1] %></td>
                            <td><%=salesDetails[2] %></td>
                            <td><%=salesDetails[3] %></td>
                            <td><%=salesDetails[4] %></td>
                            <td><%=salesDetails[5] %></td>
                        </tr>
                        <% } %>
                        </table>
            		<%
            		out.println("Revenue from " + itemReportDropdown + " : $" + sum);
            		}
            		else{
            			out.println("<p style='color:red;'> There are no items sold that match this description </p>");
            		}
            	}
            	else if (itemReportDropdown.equals("shoeBrand")){
            		ArrayList<String[]> shoeBrandReport = AdminUtil.getBrandNameReport(itemReportText);
            		if (shoeBrandReport.size() != 0){ %>
            			<table>
                        <tr>
                            <td><h3>Shoes ID</h3></td>
                            <td><h3>Seller</h3></td>
                            <td><h3>Buyer</h3></td>
                            <td><h3>Name</h3></td>
                            <td><h3>Brand</h3></td>
                            <td><h3>Selling Price</h3></td>
                        </tr>
                        <%
                        double sum = 0;
                        for (int i = 0; i < shoeBrandReport.size(); i++) {
            				String [] salesDetails = shoeBrandReport.get(i);
            				sum += Double.parseDouble(salesDetails[5]);
                        %>
                        <tr>
                            <td><%=salesDetails[0] %></td>
                            <td><%=salesDetails[1] %></td>
                            <td><%=salesDetails[2] %></td>
                            <td><%=salesDetails[3] %></td>
                            <td><%=salesDetails[4] %></td>
                            <td><%=salesDetails[5] %></td>
                        </tr>
                        <% } %>
                        </table>
            		<%
            		out.println("Revenue from " + itemReportDropdown + " : $" + sum);
            		}
            		else {
            			out.println("<p style='color:red;'> There are no items sold that match this description </p>");
            		}
            	}
            	else {
            		out.println("<p style='color:red;'> Error somewhere around here </p>");
            	}
            }
        	
            else if (request.getParameter("userReportSubmit") != null){
            	String user = request.getParameter("userReport");
            	if (user.equals("") || user == null){
            		out.println("<p style='color:red;'> Fill in user! </p>");
            	}
           		else if (!AdminUtil.existsInTable(user)){
            		out.println("<p style='color:red;'> User not valid </p>");
            	}
            	else if (AdminUtil.existsInTable(user)){
            		ArrayList<String[]> userReportList = AdminUtil.getUserReports(user);
            		if (userReportList != null && !userReportList.isEmpty()){%>
                        <table>
                        <tr>
                            <td><h3>Shoes ID</h3></td>
                            <td><h3>Seller</h3></td>
                            <td><h3>Buyer</h3></td>
                            <td><h3>Name</h3></td>
                            <td><h3>Brand</h3></td>
                            <td><h3>Selling Price</h3></td>
                        </tr>
                        <%
                        double sum = 0;
                        for (int i = 0; i < userReportList.size(); i++) {
            				String [] salesDetails = userReportList.get(i);
            				sum += Double.parseDouble(salesDetails[5]);
                        %>
                        <tr>
                            <td><%=salesDetails[0] %></td>
                            <td><%=salesDetails[1] %></td>
                            <td><%=salesDetails[2] %></td>
                            <td><%=salesDetails[3] %></td>
                            <td><%=salesDetails[4] %></td>
                            <td><%=salesDetails[5] %></td>
                        </tr>
                        <% } %>
                        </table>
            		<%
            		out.println("Total earnings for "+ user +": $" + sum);
            		}
            		else{
            			out.println("<p style='color:red;'> Error </p>");
            		}
            	}
            	else {
            		out.println("<p style='color:red;'> Error somehow </p>");
            	}
            }
        
            else if (request.getParameter("itemTypeReportSubmit") != null){
            	String selectedOption = request.getParameter("itemTypeReportSubmit");
            	ArrayList<String[]> itemTypeAL = AdminUtil.getItemTypeReports(selectedOption);%>
            	<h3> Item Type Report </h3>
            	<%if (selectedOption.equals("")){
            		out.println("<p style='color:red;'> Please select an option </p>");
            	}
            	else if (!itemTypeAL.isEmpty() && itemTypeAL != null){%>
            		<table>
                        <tr>
                            <td><h3>Shoes ID</h3></td>
                            <td><h3>Seller</h3></td>
                            <td><h3>Buyer</h3></td>
                            <td><h3>Name</h3></td>
                            <td><h3>Brand</h3></td>
                            <td><h3>Selling Price</h3></td>
                        </tr>
                        <%
                        int sum = 0;
                        for (int i = 0; i < itemTypeAL.size(); i++) {
            				String [] salesDetails = itemTypeAL.get(i);
            				sum += Double.parseDouble(salesDetails[5]);
                        %>
                        <tr>
                            <td><%=salesDetails[0] %></td>
                            <td><%=salesDetails[1] %></td>
                            <td><%=salesDetails[2] %></td>
                            <td><%=salesDetails[3] %></td>
                            <td><%=salesDetails[4] %></td>
                            <td><%=salesDetails[5] %></td>
                        </tr>
                        <% } %>
                        </table>
            	<%
            	out.println("Total Earnings per " + selectedOption + ": $" + sum);
            	}
            	else{
            		out.println("<p style='color:red;'> There are no sales under this item type </p>");
            	}
            }
            else{
            	out.println("<p style='color:red;'> Error </p>");
            }
        }
        %>
    </body>
</html>