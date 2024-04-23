<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Index</title>
</head>
    <body>
        <%
            if ((session.getAttribute("username") == null)) { // If user is not logged in, redirect to login page
                response.sendRedirect("login");
            }
        %>
        <h1>Index Page</h1>
        <p>Welcome, <%= session.getAttribute("username")%>!</p>
        <form method="post">
            <button type="submit" name="logout">Logout</button>
        </form>
        <%
            if ("POST".equals(request.getMethod()))
            {
                if (request.getParameter("logout") != null) // If user clicks on the logout button, invalidate the session and redirect to login page
                {
                    session.invalidate();
                    response.sendRedirect("login");
                }
            }
        %>
        <h3>Post Shoes for Sale!</h3>
        <form method="post">
        	<label for="shoeName">Name:</label><br>
			<input type = "text" id="shoeName" name = "shoeName"><br>
			<label for="brand">Brand:</label><br>
			<input type = "text" id="brand" name = "brand"><br>
			<label for="color">Color:</label><br>
			<input type = "text" id="color" name = "color"><br>
			<label for="quality">Quality:</label><br>
			<input type = "text" id="quality" name = "quality"><br>
			<label for="size">Size:</label><br>
			<input type = "text" id="size" name = "size"><br>
			<label for="gender">Gender:</label><br>
			<input type = "text" id="gender" name = "gender"><br>
			<label for="closingDateTime">Closing Date/Time (YYYY-MM-DD HH:MI:SS):</label><br>
			<input type = "text" id="closingDateTime" name = "closingDateTime"><br>
			<label for="hiddenMinPrice"></label>Hidden Minimum Price (reserve):<br>
			<input type = "text" id="hiddenMinPrice" name = "hiddenMinPrice"><br>
			<br>
			<input type = "submit" name = "createAuction" value = "Create Auction">
		</form>
    </body>
</html>