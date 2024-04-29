package com.cs336.pkg;

import com.cs336.pkg.models.ShoesAuction;
import com.cs336.pkg.models.SandalsAuction;
import com.cs336.pkg.models.SneakersAuction;

import java.sql.*;

import com.cs336.pkg.models.BootsAuction;

public class AuctionUtil {
    /**
     * Creates a new auction for a pair of shoes. The auction is created if the
     * shoesAuction is an instance of SandalsAuction, SneakersAuction, or
     * BootsAuction.
     * 
     * @param shoesAuction The shoes auction to be created.
     * @return true if the auction was successfully created, false otherwise.
     */
    public static boolean createShoesAuction(ShoesAuction shoesAuction) {
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try {
            con.setAutoCommit(false); // Start transaction

            String query1 = "INSERT INTO Shoes_Auction (seller_username, name, brand, color, quality, size, gender, deadline, min_bid_increment, secret_min_price, current_price) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt1 = con.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
            pstmt1.setString(1, shoesAuction.getSellerUsername());
            pstmt1.setString(2, shoesAuction.getName());
            pstmt1.setString(3, shoesAuction.getBrand());
            pstmt1.setString(4, shoesAuction.getColor());
            pstmt1.setString(5, shoesAuction.getQuality());
            pstmt1.setFloat(6, shoesAuction.getSize());
            pstmt1.setString(7, String.valueOf(shoesAuction.getGender()));
            pstmt1.setTimestamp(8, Timestamp.valueOf(shoesAuction.getDeadline()));
            pstmt1.setDouble(9, shoesAuction.getMinBidIncrement());
            pstmt1.setDouble(10, shoesAuction.getSecretMinPrice());
            pstmt1.setDouble(11, shoesAuction.getPrice());
            pstmt1.executeUpdate();

            ResultSet rs = pstmt1.getGeneratedKeys();
            if (rs.next()) {
                int generatedShoesId = rs.getInt(1);

                if (shoesAuction instanceof SandalsAuction) {
                    SandalsAuction sandalsAuction = (SandalsAuction) shoesAuction;
                    String query2 = "INSERT INTO Sandals_Auction (shoes_id, is_open_toed) VALUES (?, ?)";
                    PreparedStatement pstmt2 = con.prepareStatement(query2);
                    pstmt2.setInt(1, generatedShoesId);
                    pstmt2.setBoolean(2, sandalsAuction.getIsOpenToed());
                    pstmt2.executeUpdate();
                } else if (shoesAuction instanceof SneakersAuction) {
                    SneakersAuction sneakersAuction = (SneakersAuction) shoesAuction;
                    String query3 = "INSERT INTO Sneakers_Auction (shoes_id, sport) VALUES (?, ?)";
                    PreparedStatement pstmt3 = con.prepareStatement(query3);
                    pstmt3.setInt(1, generatedShoesId);
                    pstmt3.setString(2, sneakersAuction.getSport());
                    pstmt3.executeUpdate();
                } else if (shoesAuction instanceof BootsAuction) {
                    BootsAuction bootsAuction = (BootsAuction) shoesAuction;
                    String query4 = "INSERT INTO Boots_Auction (shoes_id, height) VALUES (?, ?)";
                    PreparedStatement pstmt4 = con.prepareStatement(query4);
                    pstmt4.setInt(1, generatedShoesId);
                    pstmt4.setDouble(2, bootsAuction.getHeight());
                    pstmt4.executeUpdate();
                }
            }

            con.commit(); // Commit transaction
            return true;
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback(); // Rollback transaction on error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
