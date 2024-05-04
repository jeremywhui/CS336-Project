package com.cs336.pkg.models;

import java.time.LocalDateTime;

public class AuctionAlert {
    private LocalDateTime timeOfAlert;
    private String alertUsername;
    private int shoesId;
    private boolean isAutomatic;

    public AuctionAlert(LocalDateTime timeOfAlert, String alertUsername, int shoesId, boolean isAutomatic) {
        this.timeOfAlert = timeOfAlert;
        this.alertUsername = alertUsername;
        this.shoesId = shoesId;
        this.isAutomatic = isAutomatic;
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
    public boolean isAutomatic() {
        return isAutomatic;
    }
    public void setAutomatic(boolean isAutomatic) {
        this.isAutomatic = isAutomatic;
    }
}
