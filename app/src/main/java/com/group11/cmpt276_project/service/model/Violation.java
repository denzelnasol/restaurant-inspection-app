package com.group11.cmpt276_project.service.model;

public class Violation {
    private int id;
    private String status;
    private String details;
    private String type;
    
    public Violation(int id, String status, String details, String type) {
        this.id = id;
        this.status = status;
        this.details = details;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
