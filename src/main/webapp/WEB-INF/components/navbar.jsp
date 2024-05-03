<!-- navbar.jsp -->
<div id="navbar">
    <a href="index">Home</a> |
    <a href="auctions">Auctions</a> |
    <a href="alerts">Alerts</a> |
    <a href="questions">Questions</a> |
    <% String role = (String) session.getAttribute("role"); %>
    <% if ("Admin".equals(role)) { %>
        <a href="admin">Admin</a> |
    <% } %>
    <% if ("Customer Representative".equals(role)){ %>
    	<a href="customerrep">Customer Rep</a> |
    <% } %>
    <a href="logout">Logout</a> |
    <span>
        <strong><%= session.getAttribute("username") %></strong>
        <% if (role == null) { %>
            (End User)
        <% } else { %>
            (<%= role %>)
        <% } %>
    </span>
</div>