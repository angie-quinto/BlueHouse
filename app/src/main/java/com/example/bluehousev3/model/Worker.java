package com.example.bluehousev3.model;

import java.util.ArrayList;

public class Worker extends User {
    private ArrayList<String> servicesOffered;
    private int hourlyRate;
    private String policeClearance;
    private String validId1;
    private String validId2;
    private String cert;
    private String rating;
    private String verified;
    private static int lastWorkerId = 0;


    public Worker(ArrayList<String> servicesOffered, int hourlyRate) {
        this.servicesOffered = servicesOffered;
        this.hourlyRate = hourlyRate;
    }


    public Worker(String fullName, int age, String gender) {
        super(fullName, age, gender);
    }

    public Worker() {}
    public Worker(String fullName) {
        super(fullName);
    }

    public ArrayList<String> getServicesOffered() {
        return servicesOffered;
    }

    public void setServicesOffered(ArrayList<String> servicesOffered) {
        this.servicesOffered = servicesOffered;
    }

    public int getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(int hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public String getPoliceClearance() {
        return policeClearance;
    }

    public void setPoliceClearance(String policeClearance) {
        this.policeClearance = policeClearance;
    }

    public String getValidId1() {
        return validId1;
    }

    public void setValidId1(String validId1) {
        this.validId1 = validId1;
    }

    public String getValidId2() {
        return validId2;
    }

    public void setValidId2(String validId2) {
        this.validId2 = validId2;
    }

    public String getCert() {
        return cert;
    }

    public void setCert(String cert) {
        this.cert = cert;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }
}
