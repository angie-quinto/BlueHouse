package com.example.bluehousev3.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bluehousev3.R;
import com.example.bluehousev3.controller.MainHome;
import com.example.bluehousev3.model.Worker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Services extends AppCompatActivity {
  private Chip chipPlumbing, chipWaterPipe, chipJetMatic, chipCarpentry,
      chipUpholstery, chipSeptic, chipGardening, chipHomeAppliance,
      chipRoofing, chipHouseCleaning, chipLaundry, chipBeautician,
      chipElectricalMaintenance, chipComputerRepair, chipMechanic,
      chipPestControl, chipCooking, chipSewerage, chipDelivery;
  private Button btnProceed;
  public static ArrayList<String> servicesOffered;

  private DatabaseReference mDatabase;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_services);

    mDatabase = FirebaseDatabase.getInstance().getReference();

    chipPlumbing = findViewById(R.id.chp_plumbing);
    chipWaterPipe = findViewById(R.id.chp_waterPipe);
    chipJetMatic = findViewById(R.id.chp_jetMatic);
    chipCarpentry = findViewById(R.id.chp_carpentry);
    chipUpholstery = findViewById(R.id.chp_upholstery);
    chipSeptic = findViewById(R.id.chp_septic);
    chipGardening = findViewById(R.id.chp_gardening);
    chipHomeAppliance = findViewById(R.id.chp_homeAppliance);
    chipRoofing = findViewById(R.id.chp_roofing);
    chipHouseCleaning = findViewById(R.id.chp_houseCleaning);
    chipLaundry = findViewById(R.id.chp_laundry);
    chipBeautician = findViewById(R.id.chp_beautician);
    chipElectricalMaintenance = findViewById(R.id.chp_electrical_maintenance);
    chipComputerRepair = findViewById(R.id.chp_computerRepair);
    chipMechanic = findViewById(R.id.chp_mechanic);
    chipPestControl = findViewById(R.id.chp_pestControl);
    chipCooking = findViewById(R.id.chp_cooking);
    chipSewerage = findViewById(R.id.chp_sewerageCleaning);
    chipDelivery = findViewById(R.id.chp_delivery);
    btnProceed = findViewById(R.id.btn_proceed);

    servicesOffered = new ArrayList<>();

    btnProceed.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        goToRegister();
      }
    });
}

  private void goToRegister() {
    if (chipPlumbing.isChecked()) {
      servicesOffered.add("Plumbing");
    }
    if (chipWaterPipe.isChecked()) {
      servicesOffered.add("Water Pipe Maintenance");
    }
    if (chipCarpentry.isChecked()) {
      servicesOffered.add("Carpentry");
    }
    if (chipJetMatic.isChecked()) {
      servicesOffered.add("JetMatic Pump Maintenance");
    }
    if (chipUpholstery.isChecked()) {
      servicesOffered.add("Upholstery");
    }
    if (chipSeptic.isChecked()) {
      servicesOffered.add("Septic Tank Maintenance");
    }
    if (chipGardening.isChecked()) {
      servicesOffered.add("Gardening");
    }
    if (chipHomeAppliance.isChecked()) {
      servicesOffered.add("Home Appliance Maintenance");
    }
    if (chipRoofing.isChecked()) {
      servicesOffered.add("Roofing");
    }
    if (chipHouseCleaning.isChecked()) {
      servicesOffered.add("House Keeping");
    }
    if (chipLaundry.isChecked()) {
      servicesOffered.add("Laundry");
    }
    if (chipBeautician.isChecked()) {
      servicesOffered.add("Beautician");
    }
    if (chipElectricalMaintenance.isChecked()) {
      servicesOffered.add("Electrical Maintenance");
    }
    if (chipComputerRepair.isChecked()) {
      servicesOffered.add("Computer/Electronic Device Repair");
    }
    if (chipMechanic.isChecked()) {
      servicesOffered.add("Mechanic");
    }
    if (chipPestControl.isChecked()) {
      servicesOffered.add("Pest Control and Fumigation");
    }
    if (chipCooking.isChecked()) {
      servicesOffered.add("Cooking Services");
    }
    if (chipSewerage.isChecked()) {
      servicesOffered.add("Sewerage Cleaning");
    }
    if (chipDelivery.isChecked()) {
      servicesOffered.add("Delivery Services");
    }
      Worker worker = new Worker();
      worker.setServicesOffered(servicesOffered);

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();

    mDatabase.child("users").child(uid).child("servicesOffered").setValue(worker.getServicesOffered()).addOnCompleteListener(new OnCompleteListener<Void>() {
      @Override
      public void onComplete(@NonNull Task<Void> task) {
        if (task.isSuccessful()) {
          Toast.makeText(Services.this, "Successfully added to database",
              Toast.LENGTH_LONG).show();
          Intent intent = new Intent(Services.this,
              WorkerVerification.class);
          startActivity(intent);
        } else {
          Toast.makeText(Services.this, "failed to add in the database",
              Toast.LENGTH_LONG).show();
        }
      }
    });

  }
}