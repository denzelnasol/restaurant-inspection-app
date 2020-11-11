package com.group11.cmpt276_project.service.model;

import java.util.List;

/**
 * Represent an Inspection Report including the TrackingNumber, InspectionDate, InspectionType,
 * NumCritical, NumNonCritical, HazardRating and ViolLump.
 */

public class InspectionReport {

    private String trackingNumber;
    private String inspectionDate;
    private String inspectionType;
    private int numberCritical;
    private int numberNonCritical;
    private String hazardRating;
    private List<Integer> violLump;

    public InspectionReport() {

    }

    private InspectionReport(String trackingNumber, String inspectionDate, String inspectionType, int numberCritical, int numberNonCritical, String hazardRating, List<Integer> violLump) {
        this.trackingNumber = trackingNumber;
        this.inspectionDate = inspectionDate;
        this.inspectionType = inspectionType;
        this.numberCritical = numberCritical;
        this.numberNonCritical = numberNonCritical;
        this.hazardRating = hazardRating;
        this.violLump = violLump;
    }

    public static class InspectionReportBuilter {
        private String trackingNumber;
        private String inspectionDate;
        private String inspectionType;
        private int numberCritical;
        private int numberNonCritical;
        private String hazardRating;
        private List<Integer> violLump;

        public InspectionReportBuilter withTrackingNumber(String trackingNumber) {
            this.trackingNumber = trackingNumber;
            return this;
        }

        public InspectionReportBuilter withInspectionDate(String inspectionDate) {
            this.inspectionDate = inspectionDate;
            return this;
        }

        public InspectionReportBuilter withInspectionType(String inspectionType) {
            this.inspectionType = inspectionType;
            return this;
        }

        public InspectionReportBuilter withNumberCritical(int numberCritical) {
            this.numberCritical = numberCritical;
            return this;
        }

        public InspectionReportBuilter withNumberNonCritical(int numberNonCritical) {
            this.numberNonCritical = numberNonCritical;
            return this;
        }

        public InspectionReportBuilter withHazardRating(String hazardRating) {
            this.hazardRating = hazardRating;
            return this;
        }

        public InspectionReportBuilter withViolLump(List<Integer> violLump) {
            this.violLump = violLump;
            return this;
        }

        public InspectionReport build() {
            return new InspectionReport(this.trackingNumber,
                    this.inspectionDate,
                    this.inspectionType,
                    this.numberCritical,
                    this.numberNonCritical,
                    this.hazardRating,
                    this.violLump);
        }
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

    public List<Integer> getViolLump() {
        return violLump;
    }
}
