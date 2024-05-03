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
			String username = (String)session.getAttribute("username");
        %>
        <jsp:include page="/WEB-INF/components/navbar.jsp" />
		<h1>Home Page</h1>
		<p>Welcome, <%= username%>!</p>
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
			<jsp:include page="/WEB-INF/components/DeleteAuctionInterface.jsp" />
		<%        
        }
        	else if (QuestionUtil.checkUser(username) == 1){ 
				// user is an Admin 
		%>        
				<jsp:include page="/WEB-INF/components/GenerateAdminReportInterface.jsp" />
		<%
        	}
			else { // idk how but you messed something up
				out.println("<p style='color:red;'> Error somehow, frankly idk how you made it to this </p>");
			}
        %>
    </body>
</html>