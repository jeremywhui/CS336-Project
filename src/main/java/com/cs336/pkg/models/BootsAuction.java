package com.cs336.pkg.models;

import java.time.LocalDateTime;

/**
 * Class representing a boots auction.
 * This class extends the general ShoesAuction class and adds a height
 * attribute.
 */
public class BootsAuction extends ShoesAuction {
    // Height of the boots
    private double height;

    /**
     * Constructor for BootsAuction.
     *
     * @param sellerUsername  Username of the seller
     * @param name            Name of the boots
     * @param brand           Brand of the boots
     * @param color           Color of the boots
     * @param quality         Quality of the boots
     * @param size            Size of the boots
     * @param gender          Gender of the boots
     * @param deadline        Deadline for the auction
     * @param minBidIncrement Minimum increment for the bid
     * @param secretMinPrice  Secret minimum price for the auction
     * @param height          Height of the boots
     */
    public BootsAuction(String sellerUsername, String name, String brand, String color, String quality,
            float size, char gender, LocalDateTime deadline, double minBidIncrement, double secretMinPrice,
            double height) {
        super(sellerUsername, name, brand, color, quality, size, gender, deadline, minBidIncrement,
                secretMinPrice);
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}