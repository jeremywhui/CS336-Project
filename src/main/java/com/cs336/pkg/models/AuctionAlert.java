package com.cs336.pkg.models;

import java.time.LocalDateTime;

public class AuctionAlert {
    private LocalDateTime timeOfAlert;
    private String alertUsername;
    private int shoesId;
    private String text;

    public AuctionAlert(LocalDateTime timeOfAlert, String alertUsername, int shoesId, String text) {
        this.timeOfAlert = timeOfAlert;
        this.alertUsername = alertUsername;
        this.shoesId = shoesId;
        this.text = text;
    }
    public LocalDateTime getTimeOfAlert() {
        return timeOfAlert;
    }
    public void setTimeOfAlert(LocalDateTime timeOfAlert) {
        this.timeOfAlert = timeOfAlert;
    }
    public String getAlertUsername() {
        return alertUsername;
    }
    public void setAlertUsername(String alertUsername) {
        this.alertUsername = alertUsername;
    }
    public int getShoesId() {
        return shoesId;
    }
    public void setShoesId(int shoesId) {
        this.shoesId = shoesId;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    
}
