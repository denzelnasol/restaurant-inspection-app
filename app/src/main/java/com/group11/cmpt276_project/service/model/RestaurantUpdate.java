package com.group11.cmpt276_project.service.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class RestaurantUpdate {

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


    public RestaurantUpdate() {

    }

    public static class RestaurantUpdateBuilder {
        private String trackingNumber;
        private String name;
        private String physicalAddress;
        private String physicalCity;
        private String facilityType;
        private double latitude;
        private double longitude;

        public RestaurantUpdateBuilder withTrackingNumber(String trackingNumber) {
            this.trackingNumber = trackingNumber;
            return this;
        }

        public RestaurantUpdateBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public RestaurantUpdateBuilder withPhysicalAddress(String physicalAddress) {
            this.physicalAddress = physicalAddress;
            return this;
        }

        public RestaurantUpdateBuilder withPhysicalCity(String physicalCity) {
            this.physicalCity = physicalCity;
            return this;
        }

        public RestaurantUpdateBuilder withFacilityType(String facilityType) {
            this.facilityType = facilityType;
            return this;
        }

        public RestaurantUpdateBuilder withLatitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public RestaurantUpdateBuilder withLongitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public RestaurantUpdate build() {
            RestaurantUpdate restaurant = new RestaurantUpdate();

            restaurant.trackingNumber = this.trackingNumber;
            restaurant.name = this.name;
            restaurant.physicalAddress = this.physicalAddress;
            restaurant.physicalCity = this.physicalCity;
            restaurant.facilityType = this.facilityType;
            restaurant.latitude = this.latitude;
            restaurant.longitude = this.longitude;
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
}
