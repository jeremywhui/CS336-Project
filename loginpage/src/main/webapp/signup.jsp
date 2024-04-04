<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.cs336.pkg.*, java.sql.*"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title> SignUp </title>
</head>
<body>

	<% out.println("Please sign up below"); %>

	<br>
	    <form method="post" action="">
	        <table>
	            <tr>
	                <td>Email: </td>
	                <td><input type="text" name="email"></td>
	            </tr>
	            <tr>
	                <td>Username: </td>
	                <td><input type="text" name="username"></td>
	            </tr>
	            <tr>
	                <td>Password: </td>
	                <td><input type="password" name="password"></td>
	            </tr>
	            <tr>
	                <td><input type="submit" value="Signup"></td>
	            </tr>
	        </table>
	    </form>


    <%
    if ("POST".equals(request.getMethod())) { // check if the form is submitted
        try {
        	//Get the database connection
            ApplicationDB db = new ApplicationDB();
            Connection con = db.getConnection();
            
          	//Create a SQL statement
            Statement stmt = con.createStatement();

          	// Get email from signup.jsp
            String email = request.getParameter("email");
          	// Get username from signup.jsp
            String username = request.getParameter("username");
          	// Get password from signup.jsp
            String password = request.getParameter("password");

            // Check if email, username, and password are not empty
            if (email != null && !email.isEmpty() && username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
            	// Make a SELECT query
                ResultSet result = stmt.executeQuery("SELECT * FROM end_user WHERE username = '" + username + "' OR email = '" + email + "'");
            	// If exists in database
                if (result.next()) {
                    out.println("duplicate username or email - please try again");
                } else {
                // If doesn't already exist in database
                    int rowsAffected = stmt.executeUpdate("INSERT INTO end_user (username, password, email) VALUES ('" + username + "', '" + password + "', '" + email + "')");
                    if (rowsAffected > 0) { // If insert was successful
                        response.sendRedirect("login.jsp"); // Redirect to login.jsp page
                    } else { // If insert was not successful
                        out.println("Error signing up"); // Show error message
                    }
                }
            } else {
            	// If user didn't fill in all fields
                out.println("Please fill out all fields");
            }

            db.closeConnection(con);
        } catch (Exception e) {
            out.print(e);
        }
    }
    %>
</body>
</html>
