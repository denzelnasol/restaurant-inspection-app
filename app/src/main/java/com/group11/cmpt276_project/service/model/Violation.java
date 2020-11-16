package com.group11.cmpt276_project.service.model;

/**
 * Represent a single Violation including the id, status, details, and type.
 */

public class Violation {
    private String id;
    private String status;
    private String details;
    private String type;

    public Violation() {
    }

    ;

    private Violation(String id, String status, String details, String type) {
        this.id = id;
        this.status = status;
        this.details = details;
        this.type = type;
    }

    public static class ViolationBuilder {
        private String id;
        private String status;
        private String details;
        private String type;

        public ViolationBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public ViolationBuilder withStatus(String status) {
            this.status = status;
            return this;
        }

        public ViolationBuilder withDetails(String details) {
            this.details = details;
            return this;
        }

        public ViolationBuilder withType(String type) {
            this.type = type;
            return this;
        }

        public Violation build() {
            return new Violation(this.id, this.status, this.details, this.type);
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
