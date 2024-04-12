package com.cs336.pkg;

import java.sql.*;

public class AuthenticationUtil {

    public static boolean validateFormInput(String username, String password) {
        if (username == null || password == null) {
            return false;
        }

        if (username.length() == 0 || password.length() == 0) {
            return false;
        }

        return true;
    }

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