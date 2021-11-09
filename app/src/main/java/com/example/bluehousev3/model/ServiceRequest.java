package com.example.bluehousev3.model;

public class ServiceRequest {
    private String serviceType;
    private String description;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
    private String location;
    private String proposedRate;
    private String status;
    private String workerName;
    private String workerAddress;
    private String workerId;
    private String proposedRateTime;
    private String img1Url;
    private String img2Url;




    public ServiceRequest(String proposedRateTime, String serviceType,
                          String description, String startDate,
                          String endDate, String startTime, String endTime,
                          String assignedAddress, String proposedRate,
                          String status, String workerName, String workerAddress,
                          String workerId, String img1Url, String img2Url) {
        this.serviceType = serviceType;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = assignedAddress;
        this.proposedRate = proposedRate;
        this.status = status;
        this.workerName = workerName;
        this.workerAddress = workerAddress;
        this.workerId = workerId;
        this.proposedRateTime = proposedRateTime;
        this.img1Url = img1Url;
        this.img2Url = img2Url;

    }

    public ServiceRequest() {}

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProposedRate() {
        return proposedRate;
    }

    public void setProposedRate(String proposedRate) {
        this.proposedRate = proposedRate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String fullName) {
        this.workerName = fullName;
    }

    public String getWorkerAddress() {
        return workerAddress;
    }

    public void setWorkerAddress(String address) {
        this.workerAddress = address;
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public String getProposedRateTime() {
        return proposedRateTime;
    }

    public void setProposedRateTime(String proposedRateTime) {
        this.proposedRateTime = proposedRateTime;
    }

    public String getImg1Url() {
        return img1Url;
    }

    public void setImg1Url(String img1Url) {
        this.img1Url = img1Url;
    }

    public String getImg2Url() {
        return img2Url;
    }

    public void setImg2Url(String img2Url) {
        this.img2Url = img2Url;
    }


}
