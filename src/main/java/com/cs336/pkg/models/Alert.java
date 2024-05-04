package com.cs336.pkg.models;

/**
 * Represents an alert for a user.
 */
public class Alert {
    protected char gender;
    protected float size;
    protected String brand;
    protected String quality;
    protected String name;
    protected String color;
    protected String username;
    protected boolean isOpenToed;
    protected double height;
    protected String sport;

    public Alert(char gender, float size, String brand, String quality, String name, String color, String username, boolean isOpenToed, double height, String sport) {
        this.gender = gender;
        this.size = size;
        this.brand = brand;
        this.quality = quality;
        this.name = name;
        this.color = color;
        this.username = username;
        this.isOpenToed = isOpenToed;
        this.height = height;
        this.sport = sport;
    }

    public char getGender() {
        return gender;
    }

    public float getSize() {
        return size;
    }

    public String getBrand() {
        return brand;
    }

    public String getQuality() {
        return quality;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public String getUsername() {
        return username;
    }

    public boolean getIsOpenToed() {
        return isOpenToed;
    }
    
    public double getHeight() {
        return height;
    }

    public String getSport() {
        return sport;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setIsOpenToed(boolean isOpenToed) {
        this.isOpenToed = isOpenToed;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }
}
