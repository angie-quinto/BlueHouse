package com.example.bluehousev3.model;

import java.util.ArrayList;

public class ClientJobPosts {
  private String clientFullName;
  private String location;
  private String serviceRequest;
  private String image1, image, image2;
  private String jobDescription;
  private static int lastJobPostId = 0;

  public ClientJobPosts() {
  }

  public ClientJobPosts(String clientFullName, String location, String serviceRequest,  String jobDescription) {
    this.clientFullName = clientFullName;
    this.location = location;
    this.serviceRequest = serviceRequest;
    this.jobDescription = jobDescription;
  }

  public String getClientFullName() {
    return clientFullName;
  }

  public void setClientFullName(String clientFullName) {
    this.clientFullName = clientFullName;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getServiceRequest() {
    return serviceRequest;
  }

  public void setServiceRequest(String serviceRequest) {
    this.serviceRequest = serviceRequest;
  }

  public String getImage1() {
    return image1;
  }

  public void setImage1(String image1) {
    this.image1 = image1;
  }

  public String getImage2() {
    return image2;
  }

  public void setImage2(String image2) {
    this.image2 = image2;
  }

  public String getJobDescription() {
    return jobDescription;
  }

  public void setJobDescription(String jobDescription) {
    this.jobDescription = jobDescription;
  }

  public static ArrayList<ClientJobPosts> jobPostList (int numOfPosts) {
    ArrayList<ClientJobPosts> clientJobPosts = new ArrayList<>();

    for (int i = 1; i <= numOfPosts; i++) {
      clientJobPosts.add(new ClientJobPosts("Client: " + ++lastJobPostId, " some random address", " service" +
          " request", "job description"));
    }
    return clientJobPosts;
  }
}
