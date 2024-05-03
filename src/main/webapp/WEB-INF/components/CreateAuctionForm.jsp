<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.cs336.pkg.*"%>
<%@ page import="com.cs336.pkg.models.ShoesAuction" %>
<%@ page import="com.cs336.pkg.models.SandalsAuction" %>
<%@ page import="com.cs336.pkg.models.BootsAuction" %>
<%@ page import="com.cs336.pkg.models.SneakersAuction" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<script>
window.onload = function() {
    var now = new Date();
    now.setDate(now.getDate() + 1);
    var year = now.getFullYear();
    var month = (now.getMonth() + 1).toString().padStart(2, '0');
    var day = now.getDate().toString().padStart(2, '0');
    var hour = now.getHours().toString().padStart(2, '0');
    var minute = now.getMinutes().toString().padStart(2, '0');
    var minDateTime = year + '-' + month + '-' + day + 'T' + hour + ':' + minute;
    document.getElementById('deadline').min = minDateTime;
}
</script>
<h2>Post Shoes for Sale!</h2>
<form method="post">
    <label for="name">Name:</label><br>
    <input type="text" id="name" name="name" maxlength="50" required><br>
    <label for="brand">Brand:</label><br>
    <input type="text" id="brand" name="brand" maxlength="20" required><br>
    <label for="color">Color:</label><br>
    <input type="text" id="color" name="color" maxlength="20" required><br>
    <label for="quality">Quality:</label><br>
    <select id="quality" name="quality" required>
        <option value="">Select a quality</option>
        <option value="New">New</option>
        <option value="Used">Used</option>
        <option value="Refurbished">Refurbished</option>
    </select><br>
    <label for="size">Size:</label><br>
    <input type="number" id="size" name="size" step="0.5" min="0" max="20" required><br>
    <label for="gender">Gender:</label><br>
    <select id="gender" name="gender" required>
        <option value="">Select a gender</option>
        <option value="M">Male</option>
        <option value="F">Female</option>
        <option value="U">Unisex</option>
    </select><br>
    <label for="deadline">Deadline:</label><br>
    <input type="datetime-local" id="deadline" name="deadline" required><br>
    <label for="minBidIncrement">Minimum Bid Increment:</label><br>
    <input type="number" id="minBidIncrement" name="minBidIncrement" step="0.01" min="0.01" max="9999999999.99" required><br>
    <label for="secretMinPrice">Secret Minimum Price:</label><br>
    <input type="number" id="secretMinPrice" name="secretMinPrice" step="0.01" min="0.01" max="9999999999.99"><br>
    <label for="shoeType">Shoe Type:</label><br>
    <select id="shoeType" name="shoeType" onchange="showShoeTypeFields(this.value)" required>
        <option value="">Select a type</option>
        <option value="sandals">Sandals</option>
        <option value="sneakers">Sneakers</option>
        <option value="boots">Boots</option>
    </select><br>
    <div id="sandalsFields" style="display: none;">
        <label for="isOpenToed">Is Open Toed:</label><br>
        <select id="isOpenToed" name="isOpenToed" required>
            <option value="">Select an option</option>
            <option value="true">True</option>
            <option value="false">False</option>
        </select><br>
    </div>
    <div id="sneakersFields" style="display: none;">
        <label for="sport">Sport:</label><br>
        <input type="text" id="sport" name="sport" maxlength="20" required><br>
    </div>
    <div id="bootsFields" style="display: none;">
        <label for="height">Height:</label><br>
        <input type="number" id="height" name="height" min="0.1" step="0.1" required><br>
    </div>
    <br>
    <input type="submit" name="createAuction" value="Create Auction">
</form>

<script>
    function showShoeTypeFields(shoeType) {
        document.getElementById('sandalsFields').style.display = 'none';
        document.getElementById('sneakersFields').style.display = 'none';
        document.getElementById('bootsFields').style.display = 'none';

        document.getElementById('isOpenToed').required = false;
        document.getElementById('sport').required = false;
        document.getElementById('height').required = false;

        if (shoeType === 'sandals') {
            document.getElementById('sandalsFields').style.display = 'block';
            document.getElementById('isOpenToed').required = true;
        } else if (shoeType === 'sneakers') {
            document.getElementById('sneakersFields').style.display = 'block';
            document.getElementById('sport').required = true;
        } else if (shoeType === 'boots') {
            document.getElementById('bootsFields').style.display = 'block';
            document.getElementById('height').required = true;
        }
    }
</script>
<%
    // Check if the request was a POST request
    if ("POST".equals(request.getMethod())) {
        // If user clicks on the createAuction button
        if(request.getParameter("createAuction") != null){
            boolean success = false;
            ShoesAuction shoesAuction;
            String shoeType = request.getParameter("shoeType");
            String sellerUsername = (String)session.getAttribute("username");
            String name = request.getParameter("name");
            String brand = request.getParameter("brand");
            String color = request.getParameter("color");
            String quality = request.getParameter("quality");
            float size = Float.parseFloat(request.getParameter("size"));
            char gender = request.getParameter("gender").charAt(0);
            LocalDateTime deadline = LocalDateTime.parse(request.getParameter("deadline"), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            double minBidIncrement = Double.parseDouble(request.getParameter("minBidIncrement"));
            double secretMinPrice = request.getParameter("secretMinPrice").equals("") ? 0.0 : Double.parseDouble(request.getParameter("secretMinPrice"));
            if(shoeType.equals("sandals")){
                boolean isOpenToed = Boolean.parseBoolean(request.getParameter("isOpenToed"));
                shoesAuction = new SandalsAuction(sellerUsername, name, brand, color, quality, size, gender, deadline, minBidIncrement, secretMinPrice, isOpenToed);
            } else if(shoeType.equals("sneakers")){
                String sport = request.getParameter("sport");
                shoesAuction = new SneakersAuction(sellerUsername, name, brand, color, quality, size, gender, deadline, minBidIncrement, secretMinPrice, sport);
            } else { // if shoeType.equals("boots")
                double height = Double.parseDouble(request.getParameter("height"));
                shoesAuction = new BootsAuction(sellerUsername, name, brand, color, quality, size, gender, deadline, minBidIncrement, secretMinPrice, height);
            }
            success = AuctionUtil.createShoesAuction(shoesAuction);
            if(success){
                out.println("<p style='color:green;'>Auction created successfully!</p>");
            } else {
                out.println("<p style='color:red;'>Failed to create auction. Please try again.</p>");
            }
        }
    }
%>