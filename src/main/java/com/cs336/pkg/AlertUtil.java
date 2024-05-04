package com.cs336.pkg;

import java.sql.*;
import java.util.ArrayList;
import com.cs336.pkg.models.Alert;

/**
 * Utility class for alerts.
 */
public class AlertUtil {
    /**
     * Creates an alert for a user.
     * @param gender
     * @param size
     * @param brand
     * @param quality
     * @param name
     * @param color
     * @param username
     * @param isOpenToed
     * @param height
     * @param sport
     * @return true if the alert was created successfully, false otherwise
     */
    public static boolean createAlert(Alert alert) {
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try (Statement stmt = con.createStatement()) {
            String query = "INSERT INTO Alerts (gender, size, brand, quality, name, color, username) VALUES (?, ?, ? ,? ,?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, String.valueOf(alert.getGender()));
            pstmt.setFloat(2, alert.getSize());
            pstmt.setString(3, alert.getBrand());
            pstmt.setString(4, alert.getQuality());
            pstmt.setString(5, alert.getName());
            pstmt.setString(6, alert.getColor());
            pstmt.setString(7, alert.getUsername());
            pstmt.executeUpdate();

            con.commit();
            return true;
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static ArrayList<Alert> getAlertsByUsername(String username) {
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();
        char gender;
        float size;
        String brand;
        String quality;
        String name;
        String color;
        boolean isOpenToed;
        double height;
        String sport;
        ArrayList<Alert> alerts = new ArrayList<>();

        try (Statement stmt = con.createStatement()) {
            String query = "SELECT * FROM Alerts WHERE username = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                gender = rs.getString("gender").charAt(0);
                size = rs.getFloat("size");
                brand = rs.getString("brand");
                quality = rs.getString("quality");
                name = rs.getString("name");
                color = rs.getString("color");
                isOpenToed = rs.getBoolean("isOpenToed");
                height = rs.getDouble("height");
                sport = rs.getString("sport");

                alerts.add(new Alert(gender, size, brand, quality, name, color, username, isOpenToed, height, sport));
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
        
        return alerts;
    }

    public static boolean deleteAlert(Alert alert) {
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try (Statement stmt = con.createStatement()) {
            String query = "DELETE FROM Alerts WHERE alert_id = ?";
            PreparedStatement pstmt = con.prepareStatement(query);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}