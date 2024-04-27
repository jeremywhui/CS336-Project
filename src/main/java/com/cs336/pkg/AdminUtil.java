package com.cs336.pkg;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

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
    
}