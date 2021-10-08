package com.example.bluehousev3.model;

import java.util.ArrayList;

public class Worker extends User {
    private ArrayList<String> servicesOffered;
    private int hourlyRate;


    public Worker(ArrayList<String> servicesOffered, int hourlyRate) {
        this.servicesOffered = servicesOffered;
        this.hourlyRate = hourlyRate;
    }

    public Worker(String fullName, int age, String gender) {
        super(fullName, age, gender);
    }

    public Worker() {}

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


}
