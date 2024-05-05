package com.cs336.pkg;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import com.cs336.pkg.models.Alert;
import com.cs336.pkg.models.AuctionAlert;

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

    public static boolean sendBidAlert(String username, int shoesId, boolean isAutomatic) {
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();
        boolean success = false;

        try {
            String query = "INSERT INTO Alert_For_Auction (time_of_alert, username, shoes_id, text) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setString(2, username);
            pstmt.setInt(3, shoesId);
            pstmt.setString(4, "A higher bid than your " + (isAutomatic ?  "automatic" : "manual") + " bid has been placed! " + (isAutomatic ? "Your automatic bid has been removed." : ""));
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                success = true;
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
        return success;
    }

    public static ArrayList<AuctionAlert> getAlerts(String username) {
        ArrayList<AuctionAlert> alerts = new ArrayList<>();
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try {
            String query = "SELECT * FROM Alert_For_Auction WHERE username = ? ORDER BY time_of_alert DESC";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, username);
            ResultSet result = pstmt.executeQuery();

            while (result.next()) {
                LocalDateTime timeOfAlert = result.getTimestamp("time_of_alert").toLocalDateTime();
                String alertUsername = result.getString("username");
                int shoesId = result.getInt("shoes_id");
                String text = result.getString("text");

                AuctionAlert alert = new AuctionAlert(timeOfAlert, alertUsername, shoesId, text);
                alerts.add(alert);
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

    public static boolean checkWinner() {
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();
        boolean success = false;

        try {
            String query = "SELECT shoes_id, secret_min_price, deadline FROM Shoes_Auction WHERE NOW() >= deadline";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet result = pstmt.executeQuery();
            while (result.next()) {
                int shoesId = result.getInt("shoes_id");
                double secretMinPrice = result.getDouble("secret_min_price");
                LocalDateTime deadline = result.getTimestamp("deadline").toLocalDateTime();
                if (secretMinPrice == 0.0 || BidUtil.getHighestBidAmount(shoesId) >= secretMinPrice) {
                    if (BidUtil.getCurrentWinner(shoesId) != null) {
                        notifyWinner(deadline, BidUtil.getCurrentWinner(shoesId), shoesId, true);
                    }
                } else {
                    if (BidUtil.getCurrentWinner(shoesId) != null) {
                        notifyWinner(deadline, BidUtil.getCurrentWinner(shoesId), shoesId, false);
                    }
                }
            }
            success = true;
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
        return success;

    }

    public static boolean notifyWinner(LocalDateTime deadline, String username, int shoesId, boolean isWinner) {
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();
        boolean success = false;

        try {
            String query = "INSERT IGNORE INTO Alert_For_Auction (time_of_alert, username, shoes_id, text) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setTimestamp(1, Timestamp.valueOf(deadline));
            pstmt.setString(2, username);
            pstmt.setInt(3, shoesId);
            pstmt.setString(4, isWinner ? "Congrats! You won this auction!" : "Although you had the highest bid, the reserve was not met, so no one wins.");
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                success = true;
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
        return success;
    }

    
}