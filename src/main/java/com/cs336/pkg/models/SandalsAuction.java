package com.cs336.pkg.models;

import java.time.LocalDateTime;

/**
 * Class representing a sandals auction.
 * This class extends the general ShoesAuction class and adds an isOpenToed
 * attribute.
 */
public class SandalsAuction extends ShoesAuction {
    // Boolean indicating if the sandals are open-toed
    private boolean isOpenToed;

    /**
     * Constructor for SandalsAuction.
     *
     * @param sellerUsername  Username of the seller
     * @param name            Name of the sandals
     * @param brand           Brand of the sandals
     * @param color           Color of the sandals
     * @param quality         Quality of the sandals
     * @param size            Size of the sandals
     * @param gender          Gender of the sandals
     * @param deadline        Deadline for the auction
     * @param minBidIncrement Minimum increment for the bid
     * @param secretMinPrice  Secret minimum price for the auction
     * @param isOpenToed      Boolean indicating if the sandals are open-toed
     */
    public SandalsAuction(String sellerUsername, String name, String brand, String color, String quality,
            float size, char gender, LocalDateTime deadline, double minBidIncrement, double secretMinPrice,
            boolean isOpenToed) {
        super(sellerUsername, name, brand, color, quality, size, gender, deadline, minBidIncrement,
                secretMinPrice);
        this.isOpenToed = isOpenToed;
    }

    /**
     * Constructor for SandalsAuction. This constructor is used when fetching the
     * data from the database.
     * 
     * @param shoesId         Unique identifier for the sandals
     * @param sellerUsername  Username of the seller
     * @param name            Name of the sandals
     * @param brand           Brand of the sandals
     * @param color           Color of the sandals
     * @param quality         Quality of the sandals
     * @param size            Size of the sandals
     * @param gender          Gender of the sandals
     * @param deadline        Deadline for the auction
     * @param minBidIncrement Minimum increment for the bid
     * @param secretMinPrice  Secret minimum price for the auction
     * @param currentPrice    Current price of the auction
     * @param isOpenToed      Boolean indicating if the sandals are open-toed
     */
    public SandalsAuction(int shoesId, String sellerUsername, String name, String brand, String color, String quality,
            float size, char gender, LocalDateTime deadline, double minBidIncrement, double secretMinPrice,
            double currentPrice,
            boolean isOpenToed) {
        super(shoesId, sellerUsername, name, brand, color, quality, size, gender, deadline, minBidIncrement,
                secretMinPrice, currentPrice);
        this.isOpenToed = isOpenToed;

    }

    public boolean getIsOpenToed() {
        return isOpenToed;
    }

    public void setIsOpenToed(boolean isOpenToed) {
        this.isOpenToed = isOpenToed;
    }
}