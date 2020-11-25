package com.group11.cmpt276_project.service.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.group11.cmpt276_project.service.dto.InspectionReportDto;

import java.util.List;

/**
 * Represent an Inspection Report including the TrackingNumber, InspectionDate, InspectionType,
 * NumCritical, NumNonCritical, HazardRating and ViolLump.
 */
@Entity(primaryKeys = {"tracking_number", "inspection_date"})
public class InspectionReport {

    @NonNull
    @ColumnInfo(name = "tracking_number")
    private String trackingNumber;

    @NonNull
    @ColumnInfo(name = "inspection_date")
    private String inspectionDate;

    @ColumnInfo(name = "inspection_type")
    private String inspectionType;

    @ColumnInfo(name = "number_critical")
    private int numberCritical;

    @ColumnInfo(name = "number_noncritical")
    private int numberNonCritical;

    @ColumnInfo(name = "hazard_rating")
    private String hazardRating;

    @ColumnInfo(name = "viol_lump")
    private List<String> violLump;

    public InspectionReport() {

    }

    public static class InspectionReportBuilder {
        private String trackingNumber;
        private String inspectionDate;
        private String inspectionType;
        private int numberCritical;
        private int numberNonCritical;
        private String hazardRating;
        private List<String> violLump;

        public InspectionReportBuilder withTrackingNumber(String trackingNumber) {
            this.trackingNumber = trackingNumber;
            return this;
        }

        public InspectionReportBuilder withInspectionDate(String inspectionDate) {
            this.inspectionDate = inspectionDate;
            return this;
        }

        public InspectionReportBuilder withInspectionType(String inspectionType) {
            this.inspectionType = inspectionType;
            return this;
        }

        public InspectionReportBuilder withNumberCritical(int numberCritical) {
            this.numberCritical = numberCritical;
            return this;
        }

        public InspectionReportBuilder withNumberNonCritical(int numberNonCritical) {
            this.numberNonCritical = numberNonCritical;
            return this;
        }

        public InspectionReportBuilder withHazardRating(String hazardRating) {
            this.hazardRating = hazardRating;
            return this;
        }

        public InspectionReportBuilder withViolLump(List<String> violLump) {
            this.violLump = violLump;
            return this;
        }

        public InspectionReport build() {
            InspectionReport inspectionReport = new InspectionReport();

            inspectionReport.hazardRating = this.hazardRating;
            inspectionReport.inspectionDate = this.inspectionDate;
            inspectionReport.inspectionType = this.inspectionType;
            inspectionReport.numberCritical = this.numberCritical;
            inspectionReport.numberNonCritical = this.numberNonCritical;
            inspectionReport.violLump = this.violLump;
            inspectionReport.trackingNumber = trackingNumber;

            return inspectionReport;
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

    public List<String> getViolLump() {
        return violLump;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public void setInspectionDate(String inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public void setInspectionType(String inspectionType) {
        this.inspectionType = inspectionType;
    }

    public void setNumberCritical(int numberCritical) {
        this.numberCritical = numberCritical;
    }

    public void setNumberNonCritical(int numberNonCritical) {
        this.numberNonCritical = numberNonCritical;
    }

    public void setHazardRating(String hazardRating) {
        this.hazardRating = hazardRating;
    }

    public void setViolLump(List<String> violLump) {
        this.violLump = violLump;
    }

}
