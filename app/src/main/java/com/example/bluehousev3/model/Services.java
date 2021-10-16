package com.example.bluehousev3.model;

import java.util.ArrayList;

public class Services {
  private String serviceName;
  private static int lastServiceId;

  public Services() {
  }

  public Services(String name){}

  public String getServiceName() {
    return serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  public static int getLastServiceId() {
    return lastServiceId;
  }

  public static void setLastServiceId(int lastServiceId) {
    Services.lastServiceId = lastServiceId;
  }

  public static ArrayList<Services> serviceList(int numServices) {
    ArrayList<Services> services = new ArrayList<Services>();

    for (int i = 1; i <= numServices; i++) {
      services.add(new Services("Service: " + ++lastServiceId));
    }

    return services;
  }
}
