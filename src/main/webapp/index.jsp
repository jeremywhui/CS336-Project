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
if ((session.getAttribute("username") == null)) {
	response.sendRedirect("login.jsp");
} else {
%>
<!-- this will display the username that is stored in the session. -->
Welcome <%=session.getAttribute("username")%>
<br>
<a href='logout.jsp'>Log out</a>
<%
}
%>
</body>
</html>