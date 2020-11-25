package com.group11.cmpt276_project.service.dto;

import java.util.List;

public class InspectionReportDto {

    private String trackingNumber;
    private String inspectionDate;
    private String inspectionType;
    private int numberCritical;
    private int numberNonCritical;
    private String hazardRating;
    private List<String> violLump;

    private InspectionReportDto() {

    };

    public static class InspectionReportDtoBuilder {
        private String trackingNumber;
        private String inspectionDate;
        private String inspectionType;
        private int numberCritical;
        private int numberNonCritical;
        private String hazardRating;
        private List<String> violLump;

        public InspectionReportDtoBuilder withTrackingNumber(String trackingNumber) {
            this.trackingNumber = trackingNumber;
            return this;
        }

        public InspectionReportDtoBuilder withInspectionDate(String inspectionDate) {
            this.inspectionDate = inspectionDate;
            return this;
        }

        public InspectionReportDtoBuilder withInspectionType(String inspectionType) {
            this.inspectionType = inspectionType;
            return this;
        }

        public InspectionReportDtoBuilder withNumberCritical(int numberCritical) {
            this.numberCritical = numberCritical;
            return this;
        }

        public InspectionReportDtoBuilder withNumberNonCritical(int numberNonCritical) {
            this.numberNonCritical = numberNonCritical;
            return this;
        }

        public InspectionReportDtoBuilder withHazardRating(String hazardRating) {
            this.hazardRating = hazardRating;
            return this;
        }

        public InspectionReportDtoBuilder withViolLump(List<String> violLump) {
            this.violLump = violLump;
            return this;
        }

        public InspectionReportDto build() {
            InspectionReportDto inspectionReportDto = new InspectionReportDto();
            inspectionReportDto.hazardRating = this.hazardRating;
            inspectionReportDto.inspectionDate = this.inspectionDate;
            inspectionReportDto.inspectionType = this.inspectionType;
            inspectionReportDto.numberCritical = this.numberCritical;
            inspectionReportDto.numberNonCritical = this.numberNonCritical;
            inspectionReportDto.violLump = this.violLump;
            inspectionReportDto.trackingNumber = trackingNumber;
            return inspectionReportDto;
        }
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(String inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public String getInspectionType() {
        return inspectionType;
    }

    public void setInspectionType(String inspectionType) {
        this.inspectionType = inspectionType;
    }

    public int getNumberCritical() {
        return numberCritical;
    }

    public void setNumberCritical(int numberCritical) {
        this.numberCritical = numberCritical;
    }

    public int getNumberNonCritical() {
        return numberNonCritical;
    }

    public void setNumberNonCritical(int numberNonCritical) {
        this.numberNonCritical = numberNonCritical;
    }

    public String getHazardRating() {
        return hazardRating;
    }

    public void setHazardRating(String hazardRating) {
        this.hazardRating = hazardRating;
    }

    public List<String> getViolLump() {
        return violLump;
    }

    public void setViolLump(List<String> violLump) {
        this.violLump = violLump;
    }
}
