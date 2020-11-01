package com.group11.cmpt276_project.service.model;

/**
 * Represent an Inspection Report including the TrackingNumber, InspectionDate, InspectionType,
 * NumCritical, NumNonCritical, HazardRating and ViolLump.
 */
<<<<<<< HEAD
=======

>>>>>>> 385250f6bcb4856b0100b75034200561ae01a86d
public class InspectionReport {

    private String trackingNumber;
    private String inspectionDate;
    private String inspectionType;
    private int numberCritical;
    private int numberNonCritical;
    private String hazardRating;
    private int[] violLump;

    public InspectionReport() {};

    public InspectionReport(String trackingNumber, String inspectionDate, String inspectionType,
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

    public String getInspectionDate() {
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
