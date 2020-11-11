package com.group11.cmpt276_project.service.model;

/*
This classes represents a restaurant as an object
 */
public class Restaurant {
    String trackingNumber;
    String name;
    String physicalAddress;
    String physicalCity;
    String facilityType;
    double latitude;
    double longitude;

    public Restaurant() {

    }

    private Restaurant(String trackingNumber, String name, String physicalAddress, String physicalCity, String facilityType, double latitude, double longitude) {
        this.trackingNumber = trackingNumber;
        this.name = name;
        this.physicalAddress = physicalAddress;
        this.physicalCity = physicalCity;
        this.facilityType = facilityType;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static class RestaurantBuilder {
        String trackingNumber;
        String name;
        String physicalAddress;
        String physicalCity;
        String facilityType;
        double latitude;
        double longitude;

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

        public Restaurant build() {
            return new Restaurant(this.trackingNumber,
                    this.name, this.physicalAddress,
                    this.physicalCity,
                    this.facilityType,
                    this.latitude,
                    this.longitude);
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
