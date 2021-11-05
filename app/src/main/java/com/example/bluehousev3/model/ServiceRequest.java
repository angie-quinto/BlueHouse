package com.example.bluehousev3.model;

import java.util.ArrayList;

public class ServiceRequest {
    private String serviceType, description, startDate, endDate, startTime, endTime, assignedAddress,
    proposedRate, status, workerName, workerAddress;

    public ServiceRequest(String serviceType, String description, String startDate, String endDate, String startTime, String endTime, String assignedAddress, String proposedRate, String status, String workerName, String workerAddress) {
        this.serviceType = serviceType;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.assignedAddress = assignedAddress;
        this.proposedRate = proposedRate;
        this.status = status;
        this.workerName = workerName;
        this.workerAddress = workerAddress;
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

    public String getAssignedAddress() {
        return assignedAddress;
    }

    public void setAssignedAddress(String assignedAddress) {
        this.assignedAddress = assignedAddress;
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

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getWorkerAddress() {
        return workerAddress;
    }

    public void setWorkerAddress(String workerAddress) {
        this.workerAddress = workerAddress;
    }

  public ArrayList<ServiceRequest> createServiceRequestList(ServiceRequest serviceRequest, int num) {
        ArrayList<ServiceRequest> serviceRequests = new ArrayList<>();

        for (int i = 1; i <= num; i++) {
            serviceRequests.add(serviceRequest);
            //serviceRequests.add(new ServiceRequest(getServiceType(), getDescription(), getStartDate(), getEndDate(),  getStartTime(), getEndTime(),  getAssignedAddress(), getProposedRate(), getStatus(),  getWorkerName(), getWorkerAddress()));
        }

        return serviceRequests;
    }
}
