package com.example.bluehousev3.model;
import java.util.ArrayList;

public class Services {
  private String serviceName;
  private static int lastServiceId;
  private static int  numOfService = 19;

  public Services() {}

  public Services(String serviceName){
    this.serviceName = serviceName;
  }

  public static int getNumOfService() {
    return numOfService;
  }

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

  public static ArrayList<Services> serviceList() {
    // add all of this to database
    ArrayList<Services> services = new ArrayList<Services>();
    int size = Services.getNumOfService();
    for (int i = 1; i <= size ; i++) {
      services.add(new Services("Plumbing"));
      services.add(new Services("Water Pipe Maintenance"));
      services.add(new Services("Carpentry"));
      services.add(new Services("JetMatic Pump Maintenance"));
      services.add(new Services("Upholstery"));
      services.add(new Services("Septic Tank Maintenance"));
      services.add(new Services("Gardening"));
      services.add(new Services("Home Appliance Maintenance"));
      services.add(new Services("Roofing"));
      services.add(new Services("House Cleaning"));
      services.add(new Services("Laundry Services"));
      services.add(new Services("Beautician"));
      services.add(new Services("Electrical Maintenance"));
      services.add(new Services("Computer/Electronics Repair"));
      services.add(new Services("Mechanic"));
      services.add(new Services("Pest Control & Fumigation"));
      services.add(new Services("Cooking"));
      services.add(new Services("Sewerage Cleaning"));
      services.add(new Services("Delivery Services"));
    }
    return services;
  }

}
