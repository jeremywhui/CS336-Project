package com.cs336.pkg.models;

import java.time.LocalDateTime;

/**
 * Class representing a sneakers auction.
 * This class extends the general ShoesAuction class and adds a sport attribute.
 */
public class SneakersAuction extends ShoesAuction {
    // Sport for which the sneakers are designed
    private String sport;

    /**
     * Constructor for SneakersAuction.
     *
     * @param sellerUsername  Username of the seller
     * @param name            Name of the sneakers
     * @param brand           Brand of the sneakers
     * @param color           Color of the sneakers
     * @param quality         Quality of the sneakers
     * @param size            Size of the sneakers
     * @param gender          Gender of the sneakers
     * @param deadline        Deadline for the auction
     * @param minBidIncrement Minimum increment for the bid
     * @param secretMinPrice  Secret minimum price for the auction
     * @param sport           Sport for which the sneakers are designed
     */
    public SneakersAuction(String sellerUsername, String name, String brand, String color, String quality,
            float size, char gender, LocalDateTime deadline, double minBidIncrement, double secretMinPrice,
            String sport) {
        super(sellerUsername, name, brand, color, quality, size, gender, deadline, minBidIncrement,
                secretMinPrice);
        this.sport = sport;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

}