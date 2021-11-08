package com.example.bluehousev3.model;

public class PendingRequest {
    private String serviceType;
    private String location;
    private String startDate;
    private String startTime;

    public PendingRequest(String serviceType, String location, String startDate, String startTime) {
        this.serviceType = serviceType;
        this.location = location;
        this.startDate = startDate;
        this.startTime = startTime;
    }

    public PendingRequest() {
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
