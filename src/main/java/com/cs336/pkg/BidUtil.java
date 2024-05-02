package com.cs336.pkg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.cs336.pkg.models.Bid;

public class BidUtil {
    /**
     * Get the highest bid amount for a shoes auction. This is the same as the
     * current price.
     * 
     * @param shoesId The shoes_id of the shoes auction
     * @return The maximum bid amount
     */
    public static double getHighestBidAmount(int shoesId) {
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();
        double maxBidAmount = 0;

        try (Statement stmt = con.createStatement()) {
            String query = "SELECT MAX(bid_amount) as max_bid FROM Bid WHERE shoes_id = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, shoesId);
            ResultSet result = pstmt.executeQuery();
            if (result.next()) {
                maxBidAmount = result.getDouble("max_bid");
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
        return maxBidAmount;
    }

    /**
     * Get the minimum bid amount for a shoes auction. This is the same as the
     * current price plus the minimum bid increment.
     * 
     * @param shoesId
     * @return The minimum bid amount
     */
    public static double getMinBidAmount(int shoesId) {
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();
        double maxBidAmount = 0;
        double minBidIncrement = 0;

        try {
            String queryMaxBid = "SELECT MAX(bid_amount) as max_bid FROM Bid WHERE shoes_id = ?";
            PreparedStatement pstmtMaxBid = con.prepareStatement(queryMaxBid);
            pstmtMaxBid.setInt(1, shoesId);
            ResultSet resultMaxBid = pstmtMaxBid.executeQuery();
            if (resultMaxBid.next()) {
                maxBidAmount = resultMaxBid.getDouble("max_bid");
            }

            String queryMinBidIncrement = "SELECT min_bid_increment FROM Shoes_Auction WHERE shoes_id = ?";
            PreparedStatement pstmtMinBidIncrement = con.prepareStatement(queryMinBidIncrement);
            pstmtMinBidIncrement.setInt(1, shoesId);
            ResultSet resultMinBidIncrement = pstmtMinBidIncrement.executeQuery();
            if (resultMinBidIncrement.next()) {
                minBidIncrement = resultMinBidIncrement.getDouble("min_bid_increment");
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

        return maxBidAmount + minBidIncrement;
    }

    /**
     * Place a bid on a shoes auction.
     * 
     * @param bid The bid to place
     * @return True if the bid was placed successfully, false otherwise
     */
    public static boolean placeBid(Bid bid) {
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();
        boolean success = false;

        try {
            String query = "INSERT INTO Bid (shoes_id, bidder_username, time_of_bid, bid_amount) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, bid.getShoesId());
            pstmt.setString(2, bid.getBidderUsername());
            pstmt.setTimestamp(3, Timestamp.valueOf(bid.getTimeOfBid()));
            pstmt.setDouble(4, bid.getBidAmount());
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

    public static ArrayList<Bid> getBidHistory(int shoesId) {
        ArrayList<Bid> bidHistory = new ArrayList<>();
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try {
            String query = "SELECT * FROM Bid WHERE shoes_id = ? ORDER BY time_of_bid DESC";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, shoesId);
            ResultSet result = pstmt.executeQuery();

            while (result.next()) {
                String bidderUsername = result.getString("bidder_username");
                LocalDateTime timeOfBid = result.getTimestamp("time_of_bid").toLocalDateTime();
                double bidAmount = result.getDouble("bid_amount");

                Bid bid = new Bid(shoesId, bidderUsername, timeOfBid, bidAmount);
                bidHistory.add(bid);
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

        return bidHistory;
    }
    
    public static boolean deleteBid (int shoes_id, String bidder_username, String time) {
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try {
            String query = "DELETE FROM Bid WHERE shoes_id = ? AND bidder_username = ? AND time_of_bid = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, shoes_id);
            pstmt.setString(2, bidder_username);
            pstmt.setString(3,  time);
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
    
    public static boolean existsInTable (int shoes_id, String bidder_username, String time) {
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try {
            String query = "SELECT * FROM Bid WHERE shoes_id = ? AND bidder_username = ? AND time_of_bid = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, shoes_id);
            pstmt.setString(2, bidder_username);
            pstmt.setString(3,  time);
            
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
