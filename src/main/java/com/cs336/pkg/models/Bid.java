package com.cs336.pkg.models;

import java.time.LocalDateTime;

public class Bid {
    private int shoesId;
    private String bidderUsername;
    private LocalDateTime timeOfBid;
    private double bidAmount;
    private boolean isAutomatic;

    public Bid(int shoesId, String bidderUsername, LocalDateTime timeOfBid, double bidAmount, boolean isAutomatic) {
        this.shoesId = shoesId;
        this.bidderUsername = bidderUsername;
        this.timeOfBid = timeOfBid;
        this.bidAmount = bidAmount;
        this.isAutomatic = isAutomatic;
    }

    public int getShoesId() {
        return shoesId;
    }

    public String getBidderUsername() {
        return bidderUsername;
    }

    public LocalDateTime getTimeOfBid() {
        return timeOfBid;
    }

    public double getBidAmount() {
        return bidAmount;
    }

    public boolean getIsAutomatic() {
        return isAutomatic;
    }

    public void setShoesId(int shoesId) {
        this.shoesId = shoesId;
    }

    public void setBidderUsername(String bidderUsername) {
        this.bidderUsername = bidderUsername;
    }

    public void setTimeOfBid(LocalDateTime timeOfBid) {
        this.timeOfBid = timeOfBid;
    }

    public void setBidAmount(double bidAmount) {
        this.bidAmount = bidAmount;
    }

    public void setIsAutomatic(boolean isAutomatic) {
        this.isAutomatic = isAutomatic;
    }
}
