package com.cs336.pkg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.cs336.pkg.models.BootsAuction;
import com.cs336.pkg.models.SandalsAuction;
import com.cs336.pkg.models.ShoesAuction;
import com.cs336.pkg.models.SneakersAuction;

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
            pstmt1.setDouble(11, shoesAuction.getCurrentPrice());
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

                    String query = "SELECT DISTINCT username FROM Shoe_Preferences WHERE (name = '' OR name = ?) AND (brand = '' OR brand = ?) AND (color = '' OR LOWER(color) = LOWER(?)) AND (quality IS NULL OR quality = ?) AND (size = -1 OR size = ?) AND (gender IS NULL OR gender = ?) AND (is_open_toed IS NULL OR is_open_toed = ?) AND username <> ? ";
                    PreparedStatement pstmt = con.prepareStatement(query);
                    pstmt.setString(1, sandalsAuction.getName());
                    pstmt.setString(2, sandalsAuction.getBrand());
                    pstmt.setString(3, sandalsAuction.getColor());
                    pstmt.setString(4, sandalsAuction.getQuality());
                    pstmt.setFloat(5, sandalsAuction.getSize());
                    pstmt.setString(6, String.valueOf(sandalsAuction.getGender()));
                    pstmt.setBoolean(7, sandalsAuction.getIsOpenToed());
                    pstmt.setString(8, shoesAuction.getSellerUsername());
                    
                    ResultSet res = pstmt.executeQuery();
                    while (res.next()) {
                        String insertQuery = "INSERT IGNORE INTO Alert_For_Auction (time_of_alert, username, shoes_id, text) VALUES (NOW(), ?, ?, ?)";
                        PreparedStatement insertPstmt = con.prepareStatement(insertQuery);
                        insertPstmt.setString(1, res.getString("username"));
                        insertPstmt.setInt(2, generatedShoesId);
                        insertPstmt.setString(3, "You might be interested in this auction, as it matches at least one of your preferences.");
                        insertPstmt.executeUpdate();
                    }
                } else if (shoesAuction instanceof SneakersAuction) {
                    SneakersAuction sneakersAuction = (SneakersAuction) shoesAuction;
                    String query3 = "INSERT INTO Sneakers_Auction (shoes_id, sport) VALUES (?, ?)";
                    PreparedStatement pstmt3 = con.prepareStatement(query3);
                    pstmt3.setInt(1, generatedShoesId);
                    pstmt3.setString(2, sneakersAuction.getSport());
                    pstmt3.executeUpdate();

                    String query = "SELECT DISTINCT username FROM Shoe_Preferences WHERE (name = '' OR name = ?) AND (brand = '' OR brand = ?) AND (color = '' OR LOWER(color) = LOWER(?)) AND (quality IS NULL OR quality = ?) AND (size = -1 OR size = ?) AND (gender IS NULL OR gender = ?) AND (sport = '' OR LOWER(sport) = LOWER(?)) AND username <> ?";
                    PreparedStatement pstmt = con.prepareStatement(query);
                    pstmt.setString(1, sneakersAuction.getName());
                    pstmt.setString(2, sneakersAuction.getBrand());
                    pstmt.setString(3, sneakersAuction.getColor());
                    pstmt.setString(4, sneakersAuction.getQuality());
                    pstmt.setFloat(5, sneakersAuction.getSize());
                    pstmt.setString(6, String.valueOf(sneakersAuction.getGender()));
                    pstmt.setString(7, sneakersAuction.getSport());
                    pstmt.setString(8, shoesAuction.getSellerUsername());
                    
                    ResultSet res = pstmt.executeQuery();
                    while (res.next()) {
                        String insertQuery = "INSERT IGNORE INTO Alert_For_Auction (time_of_alert, username, shoes_id, text) VALUES (NOW(), ?, ?, ?)";
                        PreparedStatement insertPstmt = con.prepareStatement(insertQuery);
                        insertPstmt.setString(1, res.getString("username"));
                        insertPstmt.setInt(2, generatedShoesId);
                        insertPstmt.setString(3, "You might be interested in this auction, as it matches at least one of your preferences.");
                        insertPstmt.executeUpdate();
                    }
                } else if (shoesAuction instanceof BootsAuction) {
                    BootsAuction bootsAuction = (BootsAuction) shoesAuction;
                    String query4 = "INSERT INTO Boots_Auction (shoes_id, height) VALUES (?, ?)";
                    PreparedStatement pstmt4 = con.prepareStatement(query4);
                    pstmt4.setInt(1, generatedShoesId);
                    pstmt4.setDouble(2, bootsAuction.getHeight());
                    pstmt4.executeUpdate();

                    String query = "SELECT DISTINCT username FROM Shoe_Preferences WHERE (name = '' OR name = ?) AND (brand = '' OR brand = ?) AND (color = '' OR LOWER(color) = LOWER(?)) AND (quality IS NULL OR quality = ?) AND (size = -1 OR size = ?) AND (gender IS NULL OR gender = ?) AND (height = -1 OR height = ?) AND username <> ?";
                    PreparedStatement pstmt = con.prepareStatement(query);
                    pstmt.setString(1, bootsAuction.getName());
                    pstmt.setString(2, bootsAuction.getBrand());
                    pstmt.setString(3, bootsAuction.getColor());
                    pstmt.setString(4, bootsAuction.getQuality());
                    pstmt.setFloat(5, bootsAuction.getSize());
                    pstmt.setString(6, String.valueOf(bootsAuction.getGender()));
                    pstmt.setDouble(7, bootsAuction.getHeight());
                    pstmt.setString(8, shoesAuction.getSellerUsername());
                    
                    ResultSet res = pstmt.executeQuery();
                    while (res.next()) {
                        String insertQuery = "INSERT IGNORE INTO Alert_For_Auction (time_of_alert, username, shoes_id, text) VALUES (NOW(), ?, ?, ?)";
                        PreparedStatement insertPstmt = con.prepareStatement(insertQuery);
                        insertPstmt.setString(1, res.getString("username"));
                        insertPstmt.setInt(2, generatedShoesId);
                        insertPstmt.setString(3, "You might be interested in this auction, as it matches at least one of your preferences.");
                        insertPstmt.executeUpdate();
                    }
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
    public static ArrayList<String[]> displayShoesAuction(boolean viewPastSales, boolean viewOwnSales, String username, String sortBy, String ascDesc,
            String shoeType,
            String sellerUsername, String buyerUsername, String name, String brand, String color, String quality, float size, char gender,
            LocalDateTime deadlineFrom, LocalDateTime deadlineTo,
            String isOpenToed, double height, String sport) {
        ArrayList<String[]> res = new ArrayList<>();
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try (Statement stmt = con.createStatement()) {
            String query = "SELECT shoes_id, seller_username, name, brand, color, quality, size, gender, deadline, min_bid_increment, current_price, IF(shoes_id IN (SELECT shoes_id FROM sandals_auction), 'Sandals', IF(shoes_id IN (SELECT shoes_id FROM sneakers_auction), 'Sneakers', 'Boots')) AS shoe_type, height, is_open_toed, sport FROM Shoes_auction LEFT JOIN Boots_Auction USING (shoes_id) LEFT JOIN Sandals_Auction USING (shoes_id) LEFT JOIN Sneakers_Auction USING (shoes_id) WHERE TRUE ";
            if (!viewOwnSales) {
                query += "AND seller_username <> ? ";
            }
            if (!viewPastSales) {
                query += "AND deadline > ? AND shoes_id NOT IN (SELECT shoes_id FROM Sale) ";
            }
            if (!shoeType.equals("")) {
                query += "AND shoes_id IN (SELECT shoes_id FROM " + shoeType + "_auction) ";
            }
            if (!sellerUsername.equals("")) {
                query += "AND seller_username LIKE '%" + sellerUsername + "%' ";
            }
            if (!buyerUsername.equals("")) {
                query += "AND shoes_id IN (SELECT shoes_id FROM Bid WHERE bidder_username LIKE '%" + buyerUsername + "%') ";
            }
            if (!name.equals("")) {
                query += "AND name LIKE '%" + name + "%' ";
            }
            if (!brand.equals("")) {
                query += "AND brand LIKE '%" + brand + "%' ";
            }
            if (!color.equals("")) {
                query += "AND color LIKE '%" + color + "%' ";
            }
            if (!quality.equals("")) {
                query += "AND quality LIKE '%" + quality + "%' ";
            }
            if (size != -1.0f) {
                query += "AND size = " + Float.toString(size) + " ";
            }
            if (gender != 'N') {
                query += "AND gender = '" + Character.toString(gender) + "' ";
            }
            if (deadlineFrom != null) {
                query += "AND deadline >= '" + deadlineFrom.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "' ";
            }
            if (deadlineTo != null) {
                query += "AND deadline <= '" + deadlineTo.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "' ";
            }
            if (isOpenToed != null) {
                query += "And is_open_toed = " + isOpenToed + " ";
            }
            if (height != -1.0) {
                query += "AND height = " + Double.toString(height) + " ";
            }
            if (!sport.equals("")) {
                query += "AND sport LIKE '%" + sport + "%' ";
            }

            query += "ORDER BY " + sortBy + " " + ascDesc;
            System.out.println(query);
            PreparedStatement pstmt = con.prepareStatement(query);
            int count = 1;
            if (!viewOwnSales) {
                pstmt.setString(count, username);
                count++;
            }
            if (!viewPastSales) {
                pstmt.setString(count, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                count++;
            }
            ResultSet result = pstmt.executeQuery();

            while (result.next()) { // while there are results
                res.add(new String[] {
                        result.getString("shoes_id"),
                        result.getString("seller_username"),
                        result.getString("name"),
                        result.getString("brand"),
                        result.getString("color"),
                        result.getString("quality"),
                        result.getString("size"),
                        result.getString("gender"),
                        result.getString("deadline"),
                        "$" + result.getString("min_bid_increment"),
                        "$" + result.getString("current_price"),
                        result.getString("shoe_type"),
                        result.getString("height") != null ? result.getString("height") : "N/A",
                        result.getString("is_open_toed") != null
                                ? Boolean.toString(result.getBoolean("is_open_toed")).substring(0, 1).toUpperCase()
                                        + Boolean.toString(result.getBoolean("is_open_toed")).substring(1)
                                : "N/A",
                        result.getString("sport") != null ? result.getString("sport") : "N/A" }); // get answer from
                                                                                                  // current row
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

    /**
     * displays all auctions
     *
     * @param void
     * @return true or false based on success
     */
    public static ArrayList<ShoesAuction> showAllAuctions() {
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();
        ArrayList<ShoesAuction> shoesAuctions = new ArrayList<>();
        String username;
        int shoesId;
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
            String querySandals = "SELECT * FROM Shoes_Auction sa JOIN Sandals_Auction sda ON sa.shoes_id = sda.shoes_id";
            String querySneakers = "SELECT * FROM Shoes_Auction sa JOIN Sneakers_Auction sna ON sa.shoes_id = sna.shoes_id";
            String queryBoots = "SELECT * FROM Shoes_Auction sa JOIN Boots_Auction ba ON sa.shoes_id = ba.shoes_id";

            PreparedStatement pstmtSandals = con.prepareStatement(querySandals);
            PreparedStatement pstmtSneakers = con.prepareStatement(querySneakers);
            PreparedStatement pstmtBoots = con.prepareStatement(queryBoots);

            ResultSet rsSandals = pstmtSandals.executeQuery();
            while (rsSandals.next()) {
                username = rsSandals.getString("seller_username");
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
                username = rsSneakers.getString("seller_username");
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
                username = rsBoots.getString("seller_username");
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
     * ensures that shoes_id is in database
     *
     * @param shoes_id
     * @return true or false based on whether shoes_id exists
     */
    public static boolean existsInTable(int shoes_id) {
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try (Statement stmt = con.createStatement()) {
            String insertQuery = "SELECT * FROM shoes_auction WHERE shoes_id = ?";
            PreparedStatement pstmt = con.prepareStatement(insertQuery);
            pstmt.setInt(1, shoes_id);
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

    /**
     * deletes the auction from the database
     *
     * @param shoes_id
     * @return true or false based on success
     */
    public static boolean deleteAuction(int shoes_id) {
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try (Statement stmt = con.createStatement()) {
            String insertQuery = "DELETE FROM shoes_auction WHERE shoes_id = ?";
            PreparedStatement pstmt = con.prepareStatement(insertQuery);
            pstmt.setInt(1, shoes_id);
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

    /**
     * Check if a shoes_id exists in the Sale table
     * 
     * @param shoesId The shoes_id to check
     * @return true if the shoes_id exists in the Sale table, false otherwise
     */
    public static boolean isSale(int shoesId) {
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try (Statement stmt = con.createStatement()) {
            String query = "SELECT 1 FROM Sale WHERE shoes_id = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, shoesId);
            ResultSet result = pstmt.executeQuery();
            return result.next(); // If the result set has at least one row, then the shoes_id exists in the Sale
                                  // table
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
        return false; // If an exception occurred or the shoes_id does not exist in the Sale table,
                      // return false
    }

    /**
     * Check if a shoes_id exists in the Shoes_Auction table
     * 
     * @param shoesId The shoes_id to check
     * @return true if the shoes_id exists in the Shoes_Auction table, false
     *         otherwise
     */
    public static boolean isShoesAuction(int shoesId) {
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try (Statement stmt = con.createStatement()) {
            String query = "SELECT 1 FROM Shoes_Auction WHERE shoes_id = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, shoesId);
            ResultSet result = pstmt.executeQuery();
            return result.next();
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

    /**
     * Get all the details of a shoes auction
     * 
     * @param shoesId The shoes_id of the shoes auction
     * @return A ShoesAuction object
     */
    public static ShoesAuction getShoesAuction(int shoesId) {
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();
        ShoesAuction shoesAuction = null;
        // shoesId is a parameter
        String sellerUsername = null;
        String name = null;
        String brand = null;
        String color = null;
        String quality = null;
        float size = 0.0f;
        char gender = '\0';
        LocalDateTime deadline = null;
        double minBidIncrement = 0.0;
        double secretMinPrice = 0.0;
        double currentPrice = 0.0;
        boolean isOpenToed = false;
        String sport = null;
        double height = 0.0;

        try {
            String queryShoes = "SELECT * FROM Shoes_Auction WHERE shoes_id = ?";
            String querySandals = "SELECT * FROM Sandals_Auction WHERE shoes_id = ?";
            String querySneakers = "SELECT * FROM Sneakers_Auction WHERE shoes_id = ?";
            String queryBoots = "SELECT * FROM Boots_Auction WHERE shoes_id = ?";

            PreparedStatement pstmtShoes = con.prepareStatement(queryShoes);
            PreparedStatement pstmtSandals = con.prepareStatement(querySandals);
            PreparedStatement pstmtSneakers = con.prepareStatement(querySneakers);
            PreparedStatement pstmtBoots = con.prepareStatement(queryBoots);

            pstmtShoes.setInt(1, shoesId);
            pstmtSandals.setInt(1, shoesId);
            pstmtSneakers.setInt(1, shoesId);
            pstmtBoots.setInt(1, shoesId);

            ResultSet rsShoes = pstmtShoes.executeQuery();
            while (rsShoes.next()) {
                sellerUsername = rsShoes.getString("seller_username");
                name = rsShoes.getString("name");
                brand = rsShoes.getString("brand");
                color = rsShoes.getString("color");
                quality = rsShoes.getString("quality");
                size = rsShoes.getFloat("size");
                gender = rsShoes.getString("gender").charAt(0);
                deadline = rsShoes.getTimestamp("deadline").toLocalDateTime();
                minBidIncrement = rsShoes.getDouble("min_bid_increment");
                secretMinPrice = rsShoes.getDouble("secret_min_price");
                currentPrice = rsShoes.getDouble("current_price");
            }

            ResultSet rsSandals = pstmtSandals.executeQuery();
            ResultSet rsSneakers = pstmtSneakers.executeQuery();
            ResultSet rsBoots = pstmtBoots.executeQuery();

            boolean isSandals = rsSandals.next();
            boolean isSneaker = rsSneakers.next();
            boolean isBoots = rsBoots.next();

            if (isSandals) {
                isOpenToed = rsSandals.getBoolean("is_open_toed");
                shoesAuction = new SandalsAuction(shoesId, sellerUsername, name, brand, color, quality, size, gender,
                        deadline, minBidIncrement, secretMinPrice, currentPrice, isOpenToed);
                System.out.println("Sandals");
                return shoesAuction;
            } else if (isSneaker) {
                sport = rsSneakers.getString("sport");
                shoesAuction = new SneakersAuction(shoesId, sellerUsername, name, brand, color, quality, size, gender,
                        deadline, minBidIncrement, secretMinPrice, currentPrice, sport);
                System.out.println("Sneakers");
                return shoesAuction;
            } else if (isBoots) {
                height = rsBoots.getDouble("height");
                shoesAuction = new BootsAuction(shoesId, sellerUsername, name, brand, color, quality, size, gender,
                        deadline, minBidIncrement, secretMinPrice, currentPrice, height);
                System.out.println("Boots");
                return shoesAuction;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println("null");
        return shoesAuction;
    }

    /**
     * Check if this is the username's own shoes auction
     * 
     * @param shoesId  The shoes_id of the shoes auction
     * @param username The username of the user
     * @return true if the username is the seller of the shoes auction, false
     *         otherwise
     */
    public static boolean isOwnShoesAuction(int shoesId, String username) {
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        try (Statement stmt = con.createStatement()) {
            String query = "SELECT 1 FROM Shoes_Auction WHERE shoes_id = ? AND seller_username = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, shoesId);
            pstmt.setString(2, username);
            ResultSet result = pstmt.executeQuery();
            return result.next();
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

    /**
     * Updates the shoes auction by checking for auctions that have passed their
     * deadline and adding a new tuple to the Sale table
     * if there is a valid bid. A valid bid is a bid that is greater than the secret
     * minimum price.
     */
    public static void updateShoesAuction() {
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();
        try {
            con.setAutoCommit(false); // Start transaction

            // Fetch all the shoes auctions that have passed their deadline
            String auctionQuery = "SELECT * FROM Shoes_Auction WHERE deadline < NOW()";
            PreparedStatement auctionStmt = con.prepareStatement(auctionQuery);
            ResultSet auctionResult = auctionStmt.executeQuery();

            while (auctionResult.next()) {
                int shoesId = auctionResult.getInt("shoes_id");
                double secretMinPrice = auctionResult.getDouble("secret_min_price");

                // Get the maximum bid_amount for the shoes_id
                String bidQuery = "SELECT bidder_username, MAX(bid_amount) AS max_bid FROM Bid WHERE shoes_id = ? GROUP BY bidder_username HAVING max_bid > ?";
                PreparedStatement bidStmt = con.prepareStatement(bidQuery);
                bidStmt.setInt(1, shoesId);
                bidStmt.setDouble(2, secretMinPrice);
                ResultSet bidResult = bidStmt.executeQuery();

                if (bidResult.next()) {
                    String buyerUsername = bidResult.getString("bidder_username");
                    double sellPrice = bidResult.getDouble("max_bid");

                    // Add a new tuple to Sale if there is a valid bid
                    String saleQuery = "INSERT INTO Sale (shoes_id, buyer_username, sell_price) VALUES (?, ?, ?)";
                    PreparedStatement saleStmt = con.prepareStatement(saleQuery);
                    saleStmt.setInt(1, shoesId);
                    saleStmt.setString(2, buyerUsername);
                    saleStmt.setDouble(3, sellPrice);
                    saleStmt.executeUpdate();
                }
            }

            con.commit(); // Commit the transaction
        } catch (SQLException e) {
            try {
                if (con != null) {
                    con.rollback(); // Rollback the transaction in case of an error
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
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

    /**
     * Check if a shoes auction is active. A shoes auction is active if the current
     * time is before the deadline.
     * 
     * @param shoesId The shoes_id of the shoes auction
     * @return true if the shoes auction is active, false otherwise.
     */
    public static boolean isActive(int shoesId) {
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();
        boolean isActive = false;
        try {
            String query = "SELECT * FROM Shoes_Auction WHERE shoes_id = ? AND deadline > NOW()";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, shoesId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                isActive = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isActive;
    }

}
