package com.cs336.pkg.models;

import java.time.LocalDateTime;

public class Bid {
    private int shoesId;
    private String bidderUsername;
    private LocalDateTime timeOfBid;
    private double bidAmount;

    public Bid(int shoesId, String bidderUsername, LocalDateTime timeOfBid, double bidAmount) {
        this.shoesId = shoesId;
        this.bidderUsername = bidderUsername;
        this.timeOfBid = timeOfBid;
        this.bidAmount = bidAmount;
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
}
