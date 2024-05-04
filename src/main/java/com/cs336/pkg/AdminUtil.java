package com.cs336.pkg;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.*;

/**
 * This class provides utility methods for admins and their permissions
 */
public class AdminUtil {

    /**
     * Authenticates a user against the End_User table in the database. The user is
     * authenticated if the username and password match a record in the End_User
     * table.
     *
     * @param void
     * @return ArrayList of usernames
     */
    public static Map<String, String> getUsers() {
        Map<String, String> usernamePassword = new HashMap<>();
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try (Statement stmt = con.createStatement()) {
            String query = "SELECT username, password from end_user ORDER BY username";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet result = pstmt.executeQuery();

            while (result.next()) { // while there are results
                String username = result.getString("username");
                String password = result.getString("password");
                usernamePassword.put(username, password);
            }

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

        return usernamePassword;
    }

    /**
     * checks if username exists in table
     *
     * @param username
     * @return true or false based whether it exists already
     */
    public static boolean existsInTable(String username) {
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try (Statement stmt = con.createStatement()) {
            String insertQuery = "SELECT * FROM end_user WHERE username = ?";
            PreparedStatement pstmt = con.prepareStatement(insertQuery);
            pstmt.setString(1, username);
            ResultSet result = pstmt.executeQuery();
            if (result.next()) {
                return true;
            }
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
    
    /**
     * changes the username of the specified username
     * 
     * @param originalUsername
     * @param newUsername
     * @return true or false based on success
     */
    public static boolean updateUsername (String originalUsername, String newUsername){
    	ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try (Statement stmt = con.createStatement()) {
        	String insertQuery = "UPDATE end_user SET username = ? WHERE username = ?";
            PreparedStatement pstmt = con.prepareStatement(insertQuery);
            pstmt.setString(1, newUsername);
            pstmt.setString(2, originalUsername);
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
    
    /**
     * deletes the user from the database
     *
     * @param username
     * @return true or false based on success
     */
    public static boolean deleteUser (String username){
    	ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try (Statement stmt = con.createStatement()) {
        	String insertQuery = "DELETE FROM end_user WHERE username = ?";
            PreparedStatement pstmt = con.prepareStatement(insertQuery);
            pstmt.setString(1, username);
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

    /**
     * changes the password of the specified username
     *
     * @param username
     * @return true or false based on success
     */
    public static boolean updatePassword(String username, String newPassword) {
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try (Statement stmt = con.createStatement()) {
            String insertQuery = "UPDATE end_user SET password = ? WHERE username = ?";
            PreparedStatement pstmt = con.prepareStatement(insertQuery);
            pstmt.setString(1, newPassword);
            pstmt.setString(2, username);
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
    
    /**
     * generate report for total earnings
     *
     * @param void
     * @return int of total earnings
     */
    public static int generateTotalEarnings () {
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try (Statement stmt = con.createStatement()) {
            String insertQuery = "SELECT SUM(current_price) totalSum FROM sale s, shoes_auction sa WHERE s.shoes_id = sa.shoes_id";
            PreparedStatement pstmt = con.prepareStatement(insertQuery);
            ResultSet result = pstmt.executeQuery();
            if (result.next()) { // Move cursor to the first row
                return result.getInt("totalSum");
            }
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
        return -1;
    }

    /**
     * generate report for total earnings
     *
     * @param void
     * @return String[] arraylist of total earnings
     */
    public static ArrayList<String[]> getSales() {
        ArrayList <String[]> sales = new ArrayList<String[]>();
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try (Statement stmt = con.createStatement()) {
            String insertQuery = "SELECT * FROM sale s, shoes_auction sa WHERE s.shoes_id = sa.shoes_id ORDER BY sa.current_price DESC";
            PreparedStatement pstmt = con.prepareStatement(insertQuery);
            ResultSet result = pstmt.executeQuery();

            while (result.next()) { // Move cursor to the first row
                String[] saleDetails = new String[6];
                saleDetails[0] = result.getString("shoes_id");
                saleDetails[1] = result.getString("seller_username");
                saleDetails[2] = result.getString("buyer_username");
                saleDetails[3] = result.getString("name");
                saleDetails[4] = result.getString("brand");
                saleDetails[5] = result.getString("current_price");
                sales.add(saleDetails);
            }
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
        return sales;
    }

    /** 
     * generate report for total earnings
     *
     * @param void
     * @return String[] of the highest earning sale
     */
    public static ArrayList<String[]> getHighestEarning() {
        ArrayList<String[]> highestEarningAL = new ArrayList<String[]>();
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try (Statement stmt = con.createStatement()) {
            String insertQuery = "SELECT * FROM sale s, shoes_auction sa WHERE s.shoes_id = sa.shoes_id ORDER BY DESC";
            PreparedStatement pstmt = con.prepareStatement(insertQuery);
            ResultSet result = pstmt.executeQuery();

            if (result.next()) { // Move cursor to the first row
            	String[] highestEarning = new String[6];
                highestEarning[0] = result.getString("shoes_id");
                highestEarning[1] = result.getString("seller_username");
                highestEarning[2] = result.getString("buyer_username");
                highestEarning[3] = result.getString("name");
                highestEarning[4] = result.getString("brand");
                highestEarning[5] = result.getString("current_price");
                highestEarningAL.add(highestEarning);                
            }
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
        return highestEarningAL;
    }

    /**
     * generate report for itemTypes
     *
     * @param itemtype
     * @return String[] arraylist of that item type's sales
     */
    public static ArrayList<String[]> getItemTypeReports (String itemType) {
        ArrayList<String[]> itemReport = new ArrayList<String[]> ();
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();
        String insertQuery = "";

        if (itemType.equals("boots")){
            insertQuery = "SELECT * FROM sale s, boots_auction ba, shoes_auction sa WHERE s.shoes_id = ba.shoes_id AND s.shoes_id = sa.shoes_id";
        }
        else if (itemType.equals("sneakers")){
            insertQuery = "SELECT * FROM sale s, sneakers_auction sna, shoes_auction sa WHERE s.shoes_id = sna.shoes_id AND s.shoes_id = sa.shoes_id";
        }
        else if (itemType.equals("sandals")){
            insertQuery = "SELECT * FROM sale s, sandals_auction saa, shoes_auction sa WHERE s.shoes_id = saa.shoes_id AND s.shoes_id = sa.shoes_id";
        }
        else {
            return null;
        }

        try (Statement stmt = con.createStatement()) {
            PreparedStatement pstmt = con.prepareStatement(insertQuery);
            ResultSet result = pstmt.executeQuery();

            while (result.next()) { // Move cursor to the first row
                String[] item = new String[6];
                item[0] = result.getString("shoes_id");
                item[1] = result.getString("seller_username");
                item[2] = result.getString("buyer_username");
                item[3] = result.getString("name");
                item[4] = result.getString("brand");
                item[5] = result.getString("current_price");
                itemReport.add(item);
            }
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
        return itemReport;
    }
    
    /**
     * generate report for username
     *
     * @param username
     * @return String[] arraylist of that username's sales
     */
    public static ArrayList<String[]> getUserReports (String username) {
        ArrayList<String[]> itemReport = new ArrayList<String[]> ();
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try (Statement stmt = con.createStatement()) {
            String insertQuery = " SELECT * FROM sale s, shoes_auction sa WHERE s.shoes_id = sa.shoes_id AND sa.seller_username = ?";
            PreparedStatement pstmt = con.prepareStatement(insertQuery);
            pstmt.setString(1, username);
            ResultSet result = pstmt.executeQuery();

            while (result.next()) { // Move cursor to the first row
                String[] item = new String[6];
                item[0] = result.getString("shoes_id");
                item[1] = result.getString("seller_username");
                item[2] = result.getString("buyer_username");
                item[3] = result.getString("name");
                item[4] = result.getString("brand");
                item[5] = result.getString("current_price");
                itemReport.add(item);
            }
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
        return itemReport;
    }
    
    /**
     * generate report for item type shoe name
     *
     * @param shoe name
     * @return String[] arraylist of that shoe name sales
     */
    public static ArrayList<String[]> getShoeNameReport (String shoename) {
        ArrayList<String[]> itemReport = new ArrayList<String[]> ();
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try (Statement stmt = con.createStatement()) {
            String insertQuery = " SELECT * FROM sale s, shoes_auction sa WHERE s.shoes_id = sa.shoes_id AND sa.name = ?";
            PreparedStatement pstmt = con.prepareStatement(insertQuery);
            pstmt.setString(1, shoename);
            ResultSet result = pstmt.executeQuery();

            while (result.next()) { // Move cursor to the first row
                String[] item = new String[6];
                item[0] = result.getString("shoes_id");
                item[1] = result.getString("seller_username");
                item[2] = result.getString("buyer_username");
                item[3] = result.getString("name");
                item[4] = result.getString("brand");
                item[5] = result.getString("current_price");
                itemReport.add(item);
            }
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
        return itemReport;
    }
    
    /**
     * generate report for item type shoe name
     *
     * @param brand name
     * @return String[] arraylist of that shoe name sales
     */
    public static ArrayList<String[]> getBrandNameReport (String brandname) {
        ArrayList<String[]> itemReport = new ArrayList<String[]> ();
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try (Statement stmt = con.createStatement()) {
            String insertQuery = " SELECT * FROM sale s, shoes_auction sa WHERE s.shoes_id = sa.shoes_id AND sa.brand = ?";
            PreparedStatement pstmt = con.prepareStatement(insertQuery);
            pstmt.setString(1, brandname);
            ResultSet result = pstmt.executeQuery();

            while (result.next()) { // Move cursor to the first row
                String[] item = new String[6];
                item[0] = result.getString("shoes_id");
                item[1] = result.getString("seller_username");
                item[2] = result.getString("buyer_username");
                item[3] = result.getString("name");
                item[4] = result.getString("brand");
                item[5] = result.getString("current_price");
                itemReport.add(item);
            }
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
        return itemReport;
    }
    
}