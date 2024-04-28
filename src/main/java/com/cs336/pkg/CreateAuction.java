package com.cs336.pkg;

import java.sql.*;

/**
 * This class provides utility methods for user authentication. This includes
 * both signing in and signing up.
 */
public class CreateAuction {

    /**
     * Authenticates a user against the End_User table in the database. The user is
     * authenticated if the username and password match a record in the End_User
     * table.
     *
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @return true if the username and password match a record in the End_User
     *         table, false otherwise.
     */
    public static boolean authenticateLoginAttempt(String username, String password) {
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try (Statement stmt = con.createStatement()) {
            String query = "SELECT * FROM End_User WHERE username = ? AND password = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
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
     * Validates the form input for registering a new user. The input is considered
     * valid if the username and password are not null and not empty.
     * 
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @return true if the username and password are not null and not empty,
     *         false otherwise.
     */
    public static boolean validateRegistrationInput(String username, String password) {
        if (username == null || password == null) {
            return false;
        }

        if (username.length() == 0 || password.length() == 0) {
            return false;
        }

        return true;
    }

    /**
     * Registers a new user in the End_User table in the database. The user is
     * registered if the username is unique. The registration is done by
     * inserting a new record into the End_User table.
     * 
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @return true if the user was successfully registered, false otherwise.
     */
    public static boolean createShoeListing(String shoeName, String brand, String color, String quality, String size, String gender, String closingDateTime, String minBidIncrement, String hiddenMinPrice, String shoeType, String height, String openToed, String sport, String username) {

        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try (Statement stmt = con.createStatement()) {
            String query = "INSERT INTO Shoe_Listing (gender, size, brand, quality, name, color, list_end_datetime, bid_increment, secret_min_price, price, username) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0, ?)";
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
            pstmt.setString(10, username);
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

    /**
     * Checks if a username is unique by querying the End_User table in the
     * database.
     *
     * @param username The username to check for uniqueness.
     * @return true if the username is unique, false otherwise.
     */
    public static boolean isUsernameUnique(String username) {
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try (Statement stmt = con.createStatement()) {
            String query = "SELECT * FROM End_User WHERE username = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, username);
            ResultSet result = pstmt.executeQuery();

            if (result.next()) {
                return false; // Username already exists
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

        return true; // Username is unique
    }

}