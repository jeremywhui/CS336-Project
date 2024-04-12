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
            }
        %>
        <h1>Index Page</h1>
        <p>Welcome, <%= session.getAttribute("username") %></p>
        <form method="post">
            <button type="submit" name="logout">Logout</button>
        </form>
        <%
            if ("POST".equals(request.getMethod()))
            {
                if (request.getParameter("logout") != null)
                {
                    session.invalidate();
                    response.sendRedirect("login.jsp");
                }
            }
        %>
    </body>
</html>