package com.group11.cmpt276_project.service.model;

public class RestaurantFilter {

    private final String hazardLevel;
    private final int numberCritical;
    private final boolean isFavorite;

    public RestaurantFilter(String hazardLevel, int numberCritical, boolean isFavorite) {
        this.hazardLevel = hazardLevel;
        this.numberCritical = numberCritical;
        this.isFavorite = isFavorite;
    }

    public String getHazardLevel() {
        return hazardLevel;
    }

    public int getNumberCritical() {
        return numberCritical;
    }

    public boolean isFavorite() {
        return isFavorite;
    }
}
