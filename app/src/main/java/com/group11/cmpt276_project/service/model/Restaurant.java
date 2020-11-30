package com.group11.cmpt276_project.service.model;

/*
This classes represents a restaurant as an object
 */

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Restaurant {
    String trackingNumber;
    String name;
    String physicalAddress;
    String physicalCity;
    String facilityType;
    double latitude;
    double longitude;
    boolean isFavourite;

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "tracking_number")
    private String trackingNumber;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "physical_address")
    private String physicalAddress;

    @ColumnInfo(name = "physical_city")
    private String physicalCity;

    @ColumnInfo(name = "facility_type")
    private String facilityType;

    @ColumnInfo(name = "latitude")
    private double latitude;

    @ColumnInfo(name = "longitude")
    private double longitude;

    @ColumnInfo(name = "is_favorite", defaultValue = "0")
    private boolean isFavorite;


    public Restaurant() {

    }

    public static class RestaurantBuilder {
        private String trackingNumber;
        private String name;
        private String physicalAddress;
        private String physicalCity;
        private String facilityType;
        private double latitude;
        private double longitude;
        private boolean isFavorite;

        public RestaurantBuilder withTrackingNumber(String trackingNumber) {
            this.trackingNumber = trackingNumber;
            return this;
        }

        public RestaurantBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public RestaurantBuilder withPhysicalAddress(String physicalAddress) {
            this.physicalAddress = physicalAddress;
            return this;
        }

        public RestaurantBuilder withPhysicalCity(String physicalCity) {
            this.physicalCity = physicalCity;
            return this;
        }

        public RestaurantBuilder withFacilityType(String facilityType) {
            this.facilityType = facilityType;
            return this;
        }

        public RestaurantBuilder withLatitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public RestaurantBuilder withLongitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public RestaurantBuilder withIsFavorite(boolean isFavorite) {
            this.isFavorite = isFavorite;
            return this;
        }

        public Restaurant build() {
            Restaurant restaurant = new Restaurant();

            restaurant.trackingNumber = this.trackingNumber;
            restaurant.name = this.name;
            restaurant.physicalAddress = this.physicalAddress;
            restaurant.physicalCity = this.physicalCity;
            restaurant.facilityType = this.facilityType;
            restaurant.latitude = this.latitude;
            restaurant.longitude = this.longitude;
            restaurant.isFavorite = this.isFavorite;
            return restaurant;
        }
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public void setPhysicalCity(String physicalCity) {
        this.physicalCity = physicalCity;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public String getName() {
        return name;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public String getPhysicalCity() {
        return physicalCity;
    }

    public String getFacilityType() {
        return facilityType;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
