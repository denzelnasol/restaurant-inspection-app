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
    int icon;

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

    public int getIcon() { return icon; }

    public void setIcon(int icon) { this.icon = icon; }

}
