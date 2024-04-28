package com.cs336.pkg;
// import java.text.ParseException;
// import java.text.SimpleDateFormat;
import java.sql.*;

/**
 * This class provides utility methods for user authentication. This includes
 * both signing in and signing up.
 */
public class CreateAuction {

    /**
     * takes input from auction posting with parameters and adds to database
     * 
     * @param shoeName
     * @param brand
     * @param color
     * @param quality
     * @param size
     * @param gender
     * @param closingDateTime
     * @param midBidIncrement
     * @param hiddenMinPrice
     * @param shoeType
     * @param height
     * @param openToed
     * @param sport
     * @param username
     * @return true if the user was successfully registered, false otherwise.
     */
    public static boolean createShoeListing(String shoeName, String brand, String color, String quality, String size, String gender, 
            String closingDateTime, String minBidIncrement, String hiddenMinPrice, String shoeType, String height, String openToed, String sport, String username) {

        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try (Statement stmt = con.createStatement()) {
            String query = "INSERT INTO Shoe_Listing (gender, size, brand, quality, name, color, list_end_datetime, bid_increment, secret_min_price, price, username) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, gender);
            pstmt.setString(2, size);
            pstmt.setString(3, brand);
            pstmt.setString(4, quality);
            pstmt.setString(5, shoeName);
            pstmt.setString(6, color);
            pstmt.setString(7, closingDateTime);
            pstmt.setString(8, minBidIncrement);
            pstmt.setString(9, hiddenMinPrice);
            pstmt.setString(10, null);
            pstmt.setString(11, username);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    public static boolean createBoots() {
        return false;
    }

    public static boolean createSandals() {
        return false;
    }

    public static boolean createSneakers() {
        return false;
    }


}