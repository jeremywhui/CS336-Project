package com.cs336.pkg;

import java.sql.*;

/**
 * This class provides utility methods for user authentication. This includes
 * both signing in and signing up.
 */
public class Alert {

    // /**
    //  * Validates the form input for logging in. The input is considered valid if
    //  * both the username and password are not null and not empty.
    //  *
    //  * @param username The username entered by the user.
    //  * @param password The password entered by the user.
    //  * @return true if the username and password are not null and not empty, false
    //  *         otherwise.
    //  */
    // public static boolean validateLoginInput(String username, String password) {
    //     if (username == null || password == null) {
    //         return false;
    //     }

    //     if (username.length() == 0 || password.length() == 0) {
    //         return false;
    //     }

    //     return true;
    // }

    // /**
    //  * Authenticates a user against the End_User table in the database. The user is
    //  * authenticated if the username and password match a record in the End_User
    //  * table.
    //  *
    //  * @param username The username entered by the user.
    //  * @param password The password entered by the user.
    //  * @return true if the username and password match a record in the End_User
    //  *         table, false otherwise.
    //  */
    // public static boolean authenticateLoginAttempt(String username, String password) {
    //     ApplicationDB db = new ApplicationDB();
    //     Connection con = db.getConnection();

    //     try (Statement stmt = con.createStatement()) {
    //         String query = "SELECT * FROM End_User WHERE username = ? AND password = ?";
    //         PreparedStatement pstmt = con.prepareStatement(query);
    //         pstmt.setString(1, username);
    //         pstmt.setString(2, password);
    //         ResultSet result = pstmt.executeQuery();

    //         if (result.next()) {
    //             return true;
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     } finally {
    //         if (con != null) {
    //             try {
    //                 con.close();
    //             } catch (SQLException e) {
    //                 e.printStackTrace();
    //             }
    //         }
    //     }

    //     return false;
    // }

    // /**
    //  * Validates the form input for registering a new user. The input is considered
    //  * valid if the username and password are not null and not empty.
    //  * 
    //  * @param username The username entered by the user.
    //  * @param password The password entered by the user.
    //  * @return true if the username and password are not null and not empty,
    //  *         false otherwise.
    //  */
    // public static boolean validateRegistrationInput(String username, String password) {
    //     if (username == null || password == null) {
    //         return false;
    //     }

    //     if (username.length() == 0 || password.length() == 0) {
    //         return false;
    //     }

    //     return true;
    // }

    // /**
    //  * Registers a new user in the End_User table in the database. The user is
    //  * registered if the username is unique. The registration is done by
    //  * inserting a new record into the End_User table.
    //  * 
    //  * @param username The username entered by the user.
    //  * @param password The password entered by the user.
    //  * @return true if the user was successfully registered, false otherwise.
    //  */
    // public static boolean registerNewUser(String username, String password) {
    //     if (!isUsernameUnique(username)) {
    //         return false;
    //     }

    //     ApplicationDB db = new ApplicationDB();
    //     Connection con = db.getConnection();

    //     try (Statement stmt = con.createStatement()) {
    //         String query = "INSERT INTO End_User (username, password) VALUES (?, ?)";
    //         PreparedStatement pstmt = con.prepareStatement(query);
    //         pstmt.setString(1, username);
    //         pstmt.setString(2, password);
    //         pstmt.executeUpdate();
    //         return true;
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     } finally {
    //         if (con != null) {
    //             try {
    //                 con.close();
    //             } catch (SQLException e) {
    //                 e.printStackTrace();
    //             }
    //         }
    //     }

    //     return false;
    // }

    // /**
    //  * Checks if a username is unique by querying the End_User table in the
    //  * database.
    //  *
    //  * @param username The username to check for uniqueness.
    //  * @return true if the username is unique, false otherwise.
    //  */
    // public static boolean isUsernameUnique(String username) {
    //     ApplicationDB db = new ApplicationDB();
    //     Connection con = db.getConnection();

    //     try (Statement stmt = con.createStatement()) {
    //         String query = "SELECT * FROM End_User WHERE username = ?";
    //         PreparedStatement pstmt = con.prepareStatement(query);
    //         pstmt.setString(1, username);
    //         ResultSet result = pstmt.executeQuery();

    //         if (result.next()) {
    //             return false; // Username already exists
    //         }
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     } finally {
    //         if (con != null) {
    //             try {
    //                 con.close();
    //             } catch (SQLException e) {
    //                 e.printStackTrace();
    //             }
    //         }
    //     }

    //     return true; // Username is unique
    // }

    public static boolean createAlert(char gender, float size, String brand, String quality, String name, String color, String username) {
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try (Statement stmt = con.createStatement()) {
            String query = "INSERT INTO Alerts (gender, size, brand, quality, name, color, username) VALUES (?, ?, ? ,? ,?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);

            String genderString = Character.toString(gender);

            pstmt.setString(1, genderString);
            pstmt.setFloat(2, size);
            pstmt.setString(3, brand);
            pstmt.setString(4, quality);
            pstmt.setString(5, name);
            pstmt.setString(6, color);
            pstmt.setString(7, username);

            ResultSet result = pstmt.executeQuery();
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

        // passes all the checks
        return true;
    }

}