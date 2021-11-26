package com.example.bluehousev3.model;

public class AvailableWorkersUnderService {
  private String fullName;
  private String address;
  private String rating;

  public AvailableWorkersUnderService(String fullName, String address, String rating) {
    this.fullName = fullName;
    this.address = address;
    this.rating = rating;
  }

  public AvailableWorkersUnderService() {
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getRating() {
    return rating;
  }

  public void setRating(String rating) {
    this.rating = rating;
  }
}
