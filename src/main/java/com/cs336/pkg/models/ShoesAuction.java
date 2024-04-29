package com.cs336.pkg.models;

import java.time.LocalDateTime;

/**
 * Abstract class representing a general shoes auction.
 * This class is intended to be extended by specific types of shoes auctions.
 */
public abstract class ShoesAuction {
    // Gender of the shoes (M for male, F for female)
    protected char gender;
    // Size of the shoes
    protected float size;
    // Brand of the shoes
    protected String brand;
    // Quality of the shoes
    protected String quality;
    // Name of the shoes
    protected String name;
    // Color of the shoes
    protected String color;
    // Unique identifier for the shoes
    protected int shoesId;
    // Minimum increment for the bid
    protected double minBidIncrement;
    // Deadline for the auction
    protected LocalDateTime deadline;
    // Secret minimum price for the auction
    protected double secretMinPrice;
    // Current price of the auction
    protected double currentPrice;
    // Username of the seller
    protected String sellerUsername;

    /**
     * Constructor for ShoesAuction.
     *
     * @param sellerUsername  Username of the seller
     * @param name            Name of the shoes
     * @param brand           Brand of the shoes
     * @param color           Color of the shoes
     * @param quality         Quality of the shoes
     * @param size            Size of the shoes
     * @param gender          Gender of the shoes
     * @param deadline        Deadline for the auction
     * @param minBidIncrement Minimum increment for the bid
     * @param secretMinPrice  Secret minimum price for the auction
     */
    public ShoesAuction(String sellerUsername, String name, String brand, String color, String quality,
            float size, char gender, LocalDateTime deadline, double minBidIncrement, double secretMinPrice) {
        this.sellerUsername = sellerUsername;
        this.name = name;
        this.brand = brand;
        this.color = color;
        this.quality = quality;
        this.size = size;
        this.gender = gender;
        this.deadline = deadline;
        this.minBidIncrement = minBidIncrement;
        this.secretMinPrice = secretMinPrice;
        this.currentPrice = 0;
    }

    // Getter methods...

    public int getShoesId() {
        return shoesId;
    }

    public String getSellerUsername() {
        return sellerUsername;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getColor() {
        return color;
    }

    public String getQuality() {
        return quality;
    }

    public float getSize() {
        return size;
    }

    public char getGender() {
        return gender;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public double getMinBidIncrement() {
        return minBidIncrement;
    }

    public double getSecretMinPrice() {
        return secretMinPrice;
    }

    public double getPrice() {
        return currentPrice;
    }

    public void setShoesId(int shoesId) {
        this.shoesId = shoesId;
    }
    
    public void setSellerUsername(String sellerUsername) {
        this.sellerUsername = sellerUsername;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setBrand(String brand) {
        this.brand = brand;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public void setQuality(String quality) {
        this.quality = quality;
    }
    
    public void setSize(float size) {
        this.size = size;
    }
    
    public void setGender(char gender) {
        this.gender = gender;
    }
    
    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }
    
    public void setMinBidIncrement(double minBidIncrement) {
        this.minBidIncrement = minBidIncrement;
    }
    
    public void setSecretMinPrice(double secretMinPrice) {
        this.secretMinPrice = secretMinPrice;
    }
    
    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }
}