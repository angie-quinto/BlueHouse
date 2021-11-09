package com.example.bluehousev3.model;

import java.util.ArrayList;

public class Worker extends User {

    private String rate;
    private String policeClearance;
    private String validId1;
    private String validId2;
    private String cert;
    private String rating;
    private String verified;

    public Worker(String fullName, int age, String gender) {
        super(fullName, age, gender);
    }

    public Worker() {}
    public Worker(String fullName) {
        super(fullName);
    }


    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
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
