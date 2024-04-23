package com.cs336.pkg;

import java.sql.*;

/**
 * This class provides utility methods for user authorization. This includes
 * checking if a user is an admin, customer rep, or end user.
 */
public class AuthorizationUtil {

    /**
     * Get the role of a user from the End_User table in the database.
     * @param username The username of the user.
     * @return The role of the user, or null if the user does not exist.
     */
    public static String getRole(String username) {
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try (Statement stmt = con.createStatement()) {
            String query = "SELECT role FROM End_User WHERE username = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, username);
            ResultSet result = pstmt.executeQuery();

            if (result.next()) {
                return result.getString("role");
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

        return null;
    }
}
