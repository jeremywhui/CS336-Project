package com.cs336.pkg.models;

public class AutoBid {
    private int shoesId;
    private String bidderUsername;
    private double bidIncrement;
    private double bidMaximum;

    public AutoBid(int shoesId, String bidderUsername, double bidIncrement, double bidMaximum) {
        this.shoesId = shoesId;
        this.bidderUsername = bidderUsername;
        this.bidIncrement = bidIncrement;
        this.bidMaximum = bidMaximum;
    }

    public int getShoesId() {
        return shoesId;
    }

    public String getBidderUsername() {
        return bidderUsername;
    }

    public double getBidIncrement() {
        return bidIncrement;
    }

    public double getBidMaximum() {
        return bidMaximum;
    }

    public void setShoesId(int shoesId) {
        this.shoesId = shoesId;
    }

    public void setBidderUsername(String bidderUsername) {
        this.bidderUsername = bidderUsername;
    }

    public void setBidIncrement(double bidIncrement) {
        this.bidIncrement = bidIncrement;
    }

    public void setBidMaximum(double bidMaximum) {
        this.bidMaximum = bidMaximum;
    }
}
