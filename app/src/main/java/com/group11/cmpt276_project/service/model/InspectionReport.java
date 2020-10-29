package com.group11.cmpt276_project.service.model;

public class InspectionReport {
    private final String trackingNumber;
    private final int inspectionDate;
    private final String inspectionType;
    private final int numberCritical;
    private final int numberNonCritical;
    private final String hazardRating;
    private final int[] violLump;

    public InspectionReport(String trackingNumber, int inspectionDate, String inspectionType,
                            int numberCritical, int numberNonCritical, String hazardRating,
                            int[] violLump){
        this.trackingNumber = trackingNumber;
        this.inspectionDate = inspectionDate;
        this.inspectionType = inspectionType;
        this.numberCritical = numberCritical;
        this.numberNonCritical = numberNonCritical;
        this.hazardRating = hazardRating;
        this.violLump = violLump;

    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public int getInspectionDate() {
        return inspectionDate;
    }

    public String getInspectionType() {
        return inspectionType;
    }

    public int getNumberCritical() {
        return numberCritical;
    }

    public int getNumberNonCritical() {
        return numberNonCritical;
    }

    public String getHazardRating() {
        return hazardRating;
    }

    public int[] getViolLump() {
        return violLump;
    }
}
