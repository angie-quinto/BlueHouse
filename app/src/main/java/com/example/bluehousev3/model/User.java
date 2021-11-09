package com.example.bluehousev3.model;

public class User{
    private String userType;
    private String fullName;
    private String age;
    private String gender;
    private String phoneNumber;
    private String email;
    private String address;
    private String birthdate;
    private String password;
    private String selfieUrl;

  public User() {}

  public User(String fullName) {}

  public User(String fullName, String age, String gender) {
        this.fullName = fullName;
        this.age = age;
        this.gender = gender;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getBirthdate() {
    return birthdate;
  }

  public void setBirthdate(String birthdate) {
    this.birthdate = birthdate;
  }

  public String getPhoneNumber() {
        return phoneNumber;
    }

  public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

  public String getEmail() {
        return email;
    }

  public void setEmail(String email) {
        this.email = email;
    }

  public String getAge() {
        return age;
    }

  public void setAge(String age) {
        this.age = age;
    }

  public String getFullName() {
        return fullName;
    }

  public void setFullName(String fullName) {
        this.fullName = fullName;
    }

  public String getGender() {
        return gender;
    }

  public void setGender(String gender) {
        this.gender = gender;
    }

  public String getUserType() {
        return userType;
    }

  public void setUserType(String userType) {
        this.userType = userType;
    }

  public String getPassword() {
        return password;
    }

  public void setPassword(String password) {
        this.password = password;
    }

  public String getSelfieUrl() {
        return selfieUrl;
    }

  public void setSelfieUrl(String photoUrl) {
        this.selfieUrl = photoUrl;
    }
}
