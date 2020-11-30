package com.group11.cmpt276_project.service.dto;

public class RestaurantDto {

    private String trackingNumber;
    private String name;
    private String physicalAddress;
    private String physicalCity;
    private String facilityType;
    private double latitude;
    private double longitude;

    private RestaurantDto() {

    }


    public static class RestaurantDtoBuilder {
        private String trackingNumber;
        private String name;
        private String physicalAddress;
        private String physicalCity;
        private String facilityType;
        private double latitude;
        private double longitude;

        public RestaurantDtoBuilder withTrackingNumber(String trackingNumber) {
            this.trackingNumber = trackingNumber;
            return this;
        }

        public RestaurantDtoBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public RestaurantDtoBuilder withPhysicalAddress(String physicalAddress) {
            this.physicalAddress = physicalAddress;
            return this;
        }

        public RestaurantDtoBuilder withPhysicalCity(String physicalCity) {
            this.physicalCity = physicalCity;
            return this;
        }

        public RestaurantDtoBuilder withFacilityType(String facilityType) {
            this.facilityType = facilityType;
            return this;
        }

        public RestaurantDtoBuilder withLatitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public RestaurantDtoBuilder withLongitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public RestaurantDto build() {
            RestaurantDto restaurantDto = new RestaurantDto();

            restaurantDto.trackingNumber = this.trackingNumber;
            restaurantDto.name = this.name;
            restaurantDto.physicalAddress = this.physicalAddress;
            restaurantDto.physicalCity = this.physicalCity;
            restaurantDto.facilityType = this.facilityType;
            restaurantDto.latitude = this.latitude;
            restaurantDto.longitude = this.longitude;
            return restaurantDto;
        }
    }


    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public String getPhysicalCity() {
        return physicalCity;
    }

    public void setPhysicalCity(String physicalCity) {
        this.physicalCity = physicalCity;
    }

    public String getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
