package com.group11.cmpt276_project.service.dto;

/**
 * This class represents the violation data transfer object
 */
public class ViolationDto {

    private String id;
    private String status;
    private String details;
    private String type;

    private ViolationDto() {

    }

    public static class ViolationDtoBuilder {
        private String id;
        private String status;
        private String details;
        private String type;

        public ViolationDtoBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public ViolationDtoBuilder withStatus(String status) {
            this.status = status;
            return this;
        }

        public ViolationDtoBuilder withDetails(String details) {
            this.details = details;
            return this;
        }

        public ViolationDtoBuilder withType(String type) {
            this.type = type;
            return this;
        }

        public ViolationDto build() {
            ViolationDto violationDto = new ViolationDto();
            violationDto.details = this.details;
            violationDto.id = this.id;
            violationDto.status = this.status;
            violationDto.type = this.type;

            return violationDto;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
