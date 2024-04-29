package com.cs336.pkg;

import com.cs336.pkg.models.ShoesAuction;
import com.cs336.pkg.models.SandalsAuction;
import com.cs336.pkg.models.SneakersAuction;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.*;

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

    /**
     * Gets all the shoes auctions in the database of the given seller.
     * 
     * @param username The username of the seller.
     * @return An ArrayList of ShoesAuction objects.
     */
    public static ArrayList<ShoesAuction> getShoesAuctionsBySeller(String username) {
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();
        ArrayList<ShoesAuction> shoesAuctions = new ArrayList<>();
        int shoesId;
        // seller username is a parameter
        String name;
        String brand;
        String color;
        String quality;
        float size;
        char gender;
        LocalDateTime deadline;
        double minBidIncrement;
        double secretMinPrice;
        double currentPrice;
        boolean isOpenToed;
        String sport;
        double height;

        try {
            String querySandals = "SELECT * FROM Shoes_Auction sa JOIN Sandals_Auction sda ON sa.shoes_id = sda.shoes_id WHERE sa.seller_username = ?";
            String querySneakers = "SELECT * FROM Shoes_Auction sa JOIN Sneakers_Auction sna ON sa.shoes_id = sna.shoes_id WHERE sa.seller_username = ?";
            String queryBoots = "SELECT * FROM Shoes_Auction sa JOIN Boots_Auction ba ON sa.shoes_id = ba.shoes_id WHERE sa.seller_username = ?";

            PreparedStatement pstmtSandals = con.prepareStatement(querySandals);
            PreparedStatement pstmtSneakers = con.prepareStatement(querySneakers);
            PreparedStatement pstmtBoots = con.prepareStatement(queryBoots);

            pstmtSandals.setString(1, username);
            pstmtSneakers.setString(1, username);
            pstmtBoots.setString(1, username);

            ResultSet rsSandals = pstmtSandals.executeQuery();
            while (rsSandals.next()) {
                shoesId = rsSandals.getInt("shoes_id");
                name = rsSandals.getString("name");
                brand = rsSandals.getString("brand");
                color = rsSandals.getString("color");
                quality = rsSandals.getString("quality");
                size = rsSandals.getFloat("size");
                gender = rsSandals.getString("gender").charAt(0);
                deadline = rsSandals.getTimestamp("deadline").toLocalDateTime();
                minBidIncrement = rsSandals.getDouble("min_bid_increment");
                secretMinPrice = rsSandals.getDouble("secret_min_price");
                currentPrice = rsSandals.getDouble("current_price");
                isOpenToed = rsSandals.getBoolean("is_open_toed");
                shoesAuctions.add(new SandalsAuction(shoesId, username, name, brand, color, quality, size, gender,
                        deadline, minBidIncrement, secretMinPrice, currentPrice, isOpenToed));
            }

            ResultSet rsSneakers = pstmtSneakers.executeQuery();
            while (rsSneakers.next()) {
                shoesId = rsSneakers.getInt("shoes_id");
                name = rsSneakers.getString("name");
                brand = rsSneakers.getString("brand");
                color = rsSneakers.getString("color");
                quality = rsSneakers.getString("quality");
                size = rsSneakers.getFloat("size");
                gender = rsSneakers.getString("gender").charAt(0);
                deadline = rsSneakers.getTimestamp("deadline").toLocalDateTime();
                minBidIncrement = rsSneakers.getDouble("min_bid_increment");
                secretMinPrice = rsSneakers.getDouble("secret_min_price");
                currentPrice = rsSneakers.getDouble("current_price");
                sport = rsSneakers.getString("sport");
                shoesAuctions.add(new SneakersAuction(shoesId, username, name, brand, color, quality, size, gender,
                        deadline, minBidIncrement, secretMinPrice, currentPrice, sport));
            }

            ResultSet rsBoots = pstmtBoots.executeQuery();
            while (rsBoots.next()) {
                shoesId = rsBoots.getInt("shoes_id");
                name = rsBoots.getString("name");
                brand = rsBoots.getString("brand");
                color = rsBoots.getString("color");
                quality = rsBoots.getString("quality");
                size = rsBoots.getFloat("size");
                gender = rsBoots.getString("gender").charAt(0);
                deadline = rsBoots.getTimestamp("deadline").toLocalDateTime();
                minBidIncrement = rsBoots.getDouble("min_bid_increment");
                secretMinPrice = rsBoots.getDouble("secret_min_price");
                currentPrice = rsBoots.getDouble("current_price");
                height = rsBoots.getDouble("height");
                shoesAuctions.add(new BootsAuction(shoesId, username, name, brand, color, quality, size, gender,
                        deadline, minBidIncrement, secretMinPrice, currentPrice, height));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return shoesAuctions;
    }
    /**
     * 
     * @return
     */
    public static ArrayList<String[]> displayShoesAuction() {
        ArrayList<String[]> res = new ArrayList<>();
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try (Statement stmt = con.createStatement()) {
            String query = "SELECT shoes_id, seller_username, name, brand, color, quality, size, gender, deadline, min_bid_increment, current_price, height, is_open_toed, sport FROM Shoes_auction LEFT JOIN Boots_Auction USING (shoes_id) LEFT JOIN Sandals_Auction USING (shoes_id) LEFT JOIN Sneakers_Auction USING (shoes_id) ORDER BY shoes_id";
            PreparedStatement pstmt = con.prepareStatement(query);
            ResultSet result = pstmt.executeQuery();
 
            while (result.next()) { // while there are results
                res.add(new String[] {Integer.toString(result.getInt("shoes_id")), result.getString("seller_username"),result.getString("brand"), result.getString("color"), result.getString("quality"), Float.toString(result.getFloat("size")), result.getString("gender"), result.getString("deadline"), Double.toString(result.getDouble("min_bid_increment")), Double.toString(result.getDouble("current_price")), Double.toString(result.getDouble("height")), Boolean.toString(result.getBoolean("is_open_toed")), result.getString("sport")}); // get answer from current row
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
        return res;
    }
}
