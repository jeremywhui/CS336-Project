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
    <a href="logout">Logout</a>
    <span>
        <% if (role == null) { %>
            (End User)
        <% } else { %>
            (<%= role %>)
        <% } %>
    </span>
</div>