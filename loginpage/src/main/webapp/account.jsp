<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="com.cs336.pkg.*"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title> Welcome </title>
	</head>
	<body>
		<% try {
	
			//Get the database connection
			ApplicationDB db = new ApplicationDB();	
			Connection con = db.getConnection();		

			//Create a SQL statement
			Statement stmt = con.createStatement();
			//Get username from login.jsp
			String username = request.getParameter("username");
			// Get password from login.jsp
			String password = request.getParameter("password");
			//Make a SELECT query
			String str = "SELECT * FROM end_user WHERE username = '" + username + "' AND password = '" + password + "'";
			//Run the query against the database
			ResultSet result = stmt.executeQuery(str);
			
			// Check if username, and password are not empty
			if (username != null && !username.isEmpty() && password != null && !password.isEmpty()){
				if (result.next()){ // Checks if rows are found
					username = result.getString("username");
				%>
				<!-- successful login shows welcome username -->
					<p> Welcome <%= username %> </p>
				<%
				}
				else{
				%>
				<!-- unsuccessful login shows error AND signup button option -> signup.jsp page -->
					<p> Incorrect username or password </p>
					<br>
					<form method="post" action="signup.jsp">
						<input type="submit" value="SignUp Page">
					</form>
				<%
				}
			}
			else{
				// If user didn't fill in all fields
                out.println("Please fill out all fields");
			}
			
			db.closeConnection(con);
			}
			catch (Exception e) {
			out.print(e);
		}%>
	

	</body>
</html>