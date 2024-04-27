<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.cs336.pkg.*"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Customer Rep</title>
</head>
    <body>
        <%
            if (!"Customer Representative".equals((String) session.getAttribute("role"))) { // If user is not an admin, redirect to index
                response.sendRedirect("questions");
            }
        %>
        <jsp:include page="/WEB-INF/components/navbar.jsp" />
        <h1>Customer Rep Page</h1>
        
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
		    }
		</style>
        
        
        <form method="post">
                    <p>Customer Account Username: <span style="color: red;">(not case sensitive)</span></p>
                    <input type = "text" name = "username" required>
                    
                    <table style="border: none;">
                    	<tr>
					        <td colspan="3" style="border: none;"><input type="submit" name="deleteUser" value="Delete User's Account"></td>
					    </tr>
					    <tr>
					        <td style="border: none; width: 105px;">New Username:</td>
					        <td style="border: none; width: 95px;"><input type="text" name="newUsername"></td>
					        <td style="border: none; width: 85px;"><input type="submit" name="changeUser" value="Change Username"></td>
					    </tr>
					    <tr>
					        <td style="border: none; width: 105px;">New Password:</td>
					        <td style="border: none; width: 95px;"><input type="text" name="newPassword"></td>
					        <td style="border: none; width: 85px;"><input type="submit" name="changePassword" value="Change Password"></td>
					    </tr>
					</table>
                </form>    
                <br>
                
                <%if ("POST".equals(request.getMethod())) {
                	String originalUsername = request.getParameter("username");
                	if (AdminUtil.existsInTable(originalUsername)){ // if the username exists
	                	if(request.getParameter("changeUser") != null){ // if change username form was submitted
	                		if (request.getParameter("newUsername") != ""){ // if newUsername field is filled
	                			String newUsername = request.getParameter("newUsername");
	                			if (newUsername.length() <= 100){
	                				if (AdminUtil.updateUsername(originalUsername, newUsername)){
		                				// success
		                			}
		                			else{
		                				out.println("<p style='color:red;'>Error</p>");
		                			}
	                			}
	                			else{
	                				out.println("<p style='color:red;'>Username must be less than 100 characters </p>");
	                			}
	                		}
	                		else{
	                			out.println("<p style='color:red;'>Please fill out a username</p>");
	                		}
	                	}
	                	
	                	else if(request.getParameter("changePassword") != null){ // if change password form was submitted
	                		if (request.getParameter("newPassword") != ""){ // if newPassword field is filled
	                			String newPassword = request.getParameter("newPassword");
	                			if (newPassword.length() <= 100){
	                				if (AdminUtil.updatePassword(originalUsername, newPassword)){
		                				// success
		                			}
		                			else{
		                				out.println("<p style='color:red;'>Error</p>");
		                			}
	                			}
	                			else{
	                				out.println("<p style='color:red;'>Password must be less than 100 characters </p>");
	                			}
	                		}
	                		else{
	                			out.println("<p style='color:red;'>Please fill out a password</p>");
	                		}
	                	}
	                	else if (request.getParameter("deleteUser") != null){ // if deleteUser form was submitted
	                		if (AdminUtil.deleteUser(originalUsername)){
	                			// success
	                		}
	                		else{
	                			out.println("<p style='color:red;'> Error </p>");
	                		}
	                	}
	                	else {
                			out.println("<p style='color:red;'> Error </p>");
	                	}
                	}
                	else {
            			out.println("<p style='color:red;'>Username not registered</p>");
            		}
                }
                %>
                
                

                <table>
                    <tr>
                        <td><h3>Usernames</h3></td>
                        <td><h3>Passwords</h3></td>
                    </tr>
                    <%
                    Map<String, String> usernamePassword = AdminUtil.getUsers();
        
                    for (Map.Entry<String, String> entry : usernamePassword.entrySet()) {
                        String username = entry.getKey();
                        String password = entry.getValue();
                    %>
                    <tr>
                        <td><%=username %></td>
                        <td><%=password %></td>
                    </tr>
                    <% } %>
                </table>
        
    </body>
</html>