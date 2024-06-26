package com.cs336.pkg;

import java.sql.*;

/**
 * This class provides utility methods for user authentication. This includes
 * both signing in and signing up.
 */
public class AuthenticationUtil {

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
     * Registers a new user in the End_User table in the database. The user is
     * registered if the username is unique. The registration is done by
     * inserting a new record into the End_User table.
     * 
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @return true if the user was successfully registered, false otherwise.
     */
    public static boolean registerNewUser(String username, String password) {
        if (!isUsernameUnique(username)) {
            return false;
        }

        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try (Statement stmt = con.createStatement()) {
            String query = "INSERT INTO End_User (username, password) VALUES (?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
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
     * Register a new customer representative in the End_User table in the database.
     * This is essentially the same as registering a new user, but with a different
     * role.
     * 
     * @param username the username entered by the admin.
     * @param password the password entered by the admin.
     * @return true if the user was successfully registered, false otherwise.
     */
    public static boolean registerNewCustomerRep(String username, String password) {
        if (!isUsernameUnique(username)) {
            return false;
        }

        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try (Statement stmt = con.createStatement()) {
            String query = "INSERT INTO End_User (username, password, role) VALUES (?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, "Customer Representative");
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