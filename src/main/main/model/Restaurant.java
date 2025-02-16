package com.techelevator.model;

import java.util.Objects;

public class Restaurant {

    private int restaurantId;
    private String restaurantName;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String description;
    private long phoneNumber;
    private double rating;
    private String imgSrc;

    public Restaurant() { }

    public Restaurant(int restaurantId, String restaurantName, String address, String city, String state, String zipCode, String description, long phoneNumber, double rating, String imgSrc) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.rating = rating;
        this.imgSrc = imgSrc;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "restaurantId=" + restaurantId +
                ", restaurantName='" + restaurantName + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", description='" + description + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", rating=" + rating +
                ", imgSrc='" + imgSrc + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return restaurantId == that.restaurantId && phoneNumber == that.phoneNumber && Double.compare(that.rating, rating) == 0 && Objects.equals(restaurantName, that.restaurantName) && Objects.equals(address, that.address) && Objects.equals(city, that.city) && Objects.equals(state, that.state) && Objects.equals(zipCode, that.zipCode) && Objects.equals(description, that.description) && Objects.equals(imgSrc, that.imgSrc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurantId, restaurantName, address, city, state, zipCode, description, phoneNumber, rating, imgSrc);
    }
}
