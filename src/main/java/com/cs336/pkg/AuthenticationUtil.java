package com.cs336.pkg;

import java.sql.*;

/**
 * This class provides utility methods for user authentication. This includes
 * both signing in and signing up.
 */
public class AuthenticationUtil {

    /**
     * Validates the form input for signing in. The input is considered valid if
     * both the username and password are not null and not empty.
     *
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @return true if the username and password are not null and not empty, false otherwise.
     */
    public static boolean validateSignInInput(String username, String password) {
        if (username == null || password == null) {
            return false;
        }

        if (username.length() == 0 || password.length() == 0) {
            return false;
        }

        return true;
    }

    /**
     * Authenticates a user against the end_user table in the database. The user is
     * authenticated if the username and password match a record in the end_user
     * table.
     *
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @return true if the username and password match a record in the end_user table, false otherwise.
     */
    public static boolean authenticate(String username, String password) {
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try (Statement stmt = con.createStatement()) {
            String query = "SELECT * FROM end_user WHERE username = ? AND password = ?";
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
}