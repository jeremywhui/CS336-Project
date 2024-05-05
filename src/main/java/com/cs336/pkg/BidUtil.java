package com.cs336.pkg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.cs336.pkg.models.AuctionAlert;
import com.cs336.pkg.models.AutoBid;
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

    public static String getCurrentWinner(int shoesId) {
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();
        String winner = null;

        try (Statement stmt = con.createStatement()) {
            String query = "SELECT bidder_username FROM Bid WHERE shoes_id = ? AND bid_amount = (SELECT MAX(bid_amount) FROM Bid WHERE shoes_id = ?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, shoesId);
            pstmt.setInt(2, shoesId);
            ResultSet result = pstmt.executeQuery();
            if (result.next()) {
                winner = result.getString("bidder_username");
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
        return winner;
    }

    public static boolean winnerIsAutomatic(int shoesId) {
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();
        boolean bidType = false;

        try (Statement stmt = con.createStatement()) {
            String query = "SELECT is_automatic FROM Bid WHERE shoes_id = ? AND bid_amount = (SELECT MAX(bid_amount) FROM Bid WHERE shoes_id = ?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, shoesId);
            pstmt.setInt(2, shoesId);
            ResultSet result = pstmt.executeQuery();
            if (result.next()) {
                bidType = result.getBoolean("is_automatic");
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
        return bidType;
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

        int shoesId = bid.getShoesId();
        String username = bid.getBidderUsername();

        if (AuctionUtil.isActive(bid.getShoesId())) {
            try {
                String query = "INSERT INTO Bid (shoes_id, bidder_username, time_of_bid, bid_amount, is_automatic) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setInt(1, shoesId);
                pstmt.setString(2, username);
                pstmt.setTimestamp(3, Timestamp.valueOf(bid.getTimeOfBid()));
                pstmt.setDouble(4, bid.getBidAmount());
                pstmt.setBoolean(5, bid.getIsAutomatic());
                if (!winnerIsAutomatic(shoesId)) {
                    if (getCurrentWinner(shoesId) != null && !getCurrentWinner(shoesId).equals(username)) {
                        sendAlert(getCurrentWinner(shoesId), shoesId, false);
                    }
                }
                ArrayList<AutoBid> allAutoBids = getAllAutoBids(shoesId);
                for (AutoBid autoBid : allAutoBids) {
                    if (bid.getBidAmount() > autoBid.getBidMaximum() - autoBid.getBidIncrement()) {
                        sendAlert(autoBid.getBidderUsername(), shoesId, true);
                        deleteAutoBid(shoesId, autoBid.getBidderUsername());
                    }
                }
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    success = applyAutomaticBid(bid.getBidderUsername(), bid.getShoesId());;
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
        }
        return success;
    }

    public static ArrayList<Bid> getBidHistory(int shoesId) {
        ArrayList<Bid> bidHistory = new ArrayList<>();
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try {
            String query = "SELECT * FROM Bid WHERE shoes_id = ? ORDER BY time_of_bid DESC, bid_amount DESC";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, shoesId);
            ResultSet result = pstmt.executeQuery();

            while (result.next()) {
                String bidderUsername = result.getString("bidder_username");
                LocalDateTime timeOfBid = result.getTimestamp("time_of_bid").toLocalDateTime();
                double bidAmount = result.getDouble("bid_amount");
                boolean isAutomatic = result.getBoolean("is_automatic");

                Bid bid = new Bid(shoesId, bidderUsername, timeOfBid, bidAmount, isAutomatic);
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
    
    public static boolean deleteBid (int shoesId, String bidderUsername, String time) {
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try {
            String query = "DELETE FROM Bid WHERE shoes_id = ? AND bidder_username = ? AND time_of_bid = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, shoesId);
            pstmt.setString(2, bidderUsername);
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

    public static boolean deleteAutoBid(int shoesId, String bidderUsername) {
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try {
            String query = "DELETE FROM Auto_Bid WHERE shoes_id = ? AND bidder_username = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, shoesId);
            pstmt.setString(2, bidderUsername);
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

    public static boolean setAutoBid(AutoBid bid) {
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();
        boolean success = false;

        try {
            String query = "INSERT INTO Auto_Bid (shoes_id, bidder_username, bid_increment, bid_maximum) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, bid.getShoesId());
            pstmt.setString(2, bid.getBidderUsername());
            pstmt.setDouble(3, bid.getBidIncrement());
            pstmt.setDouble(4, bid.getBidMaximum());
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                if (getCurrentWinner(bid.getShoesId()) != null && bid.getBidderUsername().equals(getCurrentWinner(bid.getShoesId())))
                    success = true;
                else
                    success = applyAutomaticBid(null, bid.getShoesId());
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

    public static AutoBid getAutoBid(String username, int shoesId) {
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try {
            String query = "SELECT * FROM Auto_Bid WHERE bidder_username = ? AND shoes_id = ? LIMIT 1";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setInt(2, shoesId);
            System.out.println(pstmt.toString());
            ResultSet result = pstmt.executeQuery();

            if (result.next()) {
                double bidIncrement = result.getDouble("bid_increment");
                double bidMaximum = result.getDouble("bid_maximum");

                return new AutoBid(shoesId, username, bidIncrement, bidMaximum);
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

    public static ArrayList<AutoBid> getAllAutoBids(int shoesId) {
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        ArrayList<AutoBid> allAutoBids = new ArrayList<>();

        try {
            String query = "SELECT * FROM Auto_Bid WHERE shoes_id = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, shoesId);
            ResultSet result = pstmt.executeQuery();

            while (result.next()) {
                String username = result.getString("bidder_username");
                double bidIncrement = result.getDouble("bid_increment");
                double bidMaximum = result.getDouble("bid_maximum");

                allAutoBids.add(new AutoBid (shoesId, username, bidIncrement, bidMaximum));
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

        return allAutoBids;
    }

    public static boolean applyAutomaticBid(String username, int shoesId) {
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try {
            String query = "SELECT * FROM Auto_Bid WHERE shoes_id = ? ";
            if (username != null) {
                query += "AND bidder_username <> ? ";
            }
            query += " LIMIT 1";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, shoesId);
            if (username != null) {
                pstmt.setString(2, username);
            }
            System.out.println(pstmt.toString());
            ResultSet result = pstmt.executeQuery();
            if (result.next()) {
                double bidIncrement = result.getDouble("bid_increment");
                double bidMaximum = result.getDouble("bid_maximum");
                double bidAmount = bidIncrement + getHighestBidAmount(shoesId);
                if (bidAmount <= bidMaximum) {
                    return placeBid(new Bid(shoesId, result.getString("bidder_username"), LocalDateTime.now(), bidAmount, true));
                } else {
                    return true;
                }
            } else {
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

    public static boolean sendAlert(String username, int shoesId, boolean isAutomatic) {
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();
        boolean success = false;

        try {
            String query = "INSERT INTO Alert_For_Auction (time_of_alert, username, shoes_id, is_automatic) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setString(2, username);
            pstmt.setInt(3, shoesId);
            pstmt.setBoolean(4, isAutomatic);
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
                boolean isAutomatic = result.getBoolean("is_automatic");

                AuctionAlert alert = new AuctionAlert(timeOfAlert, alertUsername, shoesId, isAutomatic);
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

}
