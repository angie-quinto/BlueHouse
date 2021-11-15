package com.example.bluehousev3.model;

public class Client extends User{
    private String validId1;
    private String validId2;
    private String status;
    private String rating;

    public Client() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
