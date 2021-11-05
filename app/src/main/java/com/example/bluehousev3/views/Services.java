package com.example.bluehousev3.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bluehousev3.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Services extends AppCompatActivity {
  private Chip chipPlumbing, chipWaterPipe, chipJetMatic, chipCarpentry,
      chipUpholstery, chipSeptic, chipGardening, chipHomeAppliance,
      chipRoofing, chipHouseCleaning, chipLaundry, chipBeautician,
      chipElectricalMaintenance, chipComputerRepair, chipMechanic,
      chipPestControl, chipCooking, chipSewerage, chipDelivery;
  private Map<String, String> serviceOffered;
  private static final String FIREBASE_URL = "https://blue-house-v3-default-rtdb.asia-southeast1.firebasedatabase.app";
  private DatabaseReference mDatabase;
  private DatabaseReference workersUnderServiceRef;
  private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
  private final String uid;
  private ArrayList<String> plumbing, waterPipe, carpentry, jetMatic, upholstery, septic, gardening, homeAppliance, roofing,
          houseCleaning, laundry, beaut, electrical, computer, mechanic, pestControl, cooking, sewerage, delivery;
  {
    assert user != null;
    uid = user.getUid();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_services);

    mDatabase = FirebaseDatabase.getInstance().getReference();
    workersUnderServiceRef = FirebaseDatabase.getInstance().getReference().child("workersUnderService");

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
    Button btnProceed = findViewById(R.id.btn_proceed);
    serviceOffered = new HashMap<>();

    btnProceed.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        goToRegister();
      }
    });
}

  private void goToRegister() {
    if (chipPlumbing.isChecked()) {
      serviceOffered.put("service1","Plumbing");
      DatabaseReference ref = workersUnderServiceRef.child("plumbing").push();
      ref.setValue(uid);

    }
    if (chipWaterPipe.isChecked()) {
      serviceOffered.put("service2","Water Pipe Maintenance");
      DatabaseReference ref = workersUnderServiceRef.child("waterPipeMaintenance").push();
      ref.setValue(uid);

    }
    if (chipCarpentry.isChecked()) {
      serviceOffered.put("service3","Carpentry");
      DatabaseReference ref = workersUnderServiceRef.child("carpentry").push();
      ref.setValue(uid);

    }
    if (chipJetMatic.isChecked()) {
      serviceOffered.put("service4","JetMatic Pump Maintenance");
      DatabaseReference ref = workersUnderServiceRef.child("jetMaticPumpMaintenance").push();
      ref.setValue(uid);

    }
    if (chipUpholstery.isChecked()) {
      serviceOffered.put("service5","Upholstery");
      DatabaseReference ref = workersUnderServiceRef.child("upholstery").push();
      ref.setValue(uid);

    }
    if (chipSeptic.isChecked()) {
      serviceOffered.put("service6","Septic Tank Maintenance");
      DatabaseReference ref = workersUnderServiceRef.child("septicTankMaintenance").push();
      ref.setValue(uid);

    }
    if (chipGardening.isChecked()) {
      serviceOffered.put("service7","Gardening");
      DatabaseReference ref = workersUnderServiceRef.child("gardening").push();
      ref.setValue(uid);

    }
    if (chipHomeAppliance.isChecked()) {
      serviceOffered.put("service8","Home Appliance Maintenance");
      DatabaseReference ref = workersUnderServiceRef.child("homeApplianceMaintenance").push();
      ref.setValue(uid);

    }
    if (chipRoofing.isChecked()) {
      serviceOffered.put("service9","Roofing");
      DatabaseReference ref = workersUnderServiceRef.child("roofing").push();
      ref.setValue(uid);

    }
    if (chipHouseCleaning.isChecked()) {
      serviceOffered.put("service10","House Keeping");
      DatabaseReference ref = workersUnderServiceRef.child("houseCleaning").push();
      ref.setValue(uid);

    }
    if (chipLaundry.isChecked()) {
      serviceOffered.put("service11","Laundry");
      DatabaseReference ref = workersUnderServiceRef.child("laundry").push();
      ref.setValue(uid);

    }
    if (chipBeautician.isChecked()) {
      serviceOffered.put("service12","Beautician");
      DatabaseReference ref = workersUnderServiceRef.child("beautician").push();
      ref.setValue(uid);

    }
    if (chipElectricalMaintenance.isChecked()) {
      serviceOffered.put("service13","Electrical Maintenance");
      DatabaseReference ref = workersUnderServiceRef.child("electricalMaintenance").push();
      ref.setValue(uid);

    }
    if (chipComputerRepair.isChecked()) {
      serviceOffered.put("service14","Computer/Electronic Device Repair");
      DatabaseReference ref = workersUnderServiceRef.child("computerAndElectronicRepair").push();
      ref.setValue(uid);

    }
    if (chipMechanic.isChecked()) {
      serviceOffered.put("service15","Mechanic");
      DatabaseReference ref = workersUnderServiceRef.child("mechanic").push();
      ref.setValue(uid);

    }
    if (chipPestControl.isChecked()) {
      serviceOffered.put("service16","Pest Control & Fumigation");
      DatabaseReference ref = workersUnderServiceRef.child("pestControlAndFumigation").push();
      ref.setValue(uid);

    }
    if (chipCooking.isChecked()) {
      serviceOffered.put("service17","Cooking Services");
      DatabaseReference ref = workersUnderServiceRef.child("cooking").push();
      ref.setValue(uid);

    }
    if (chipSewerage.isChecked()) {
      serviceOffered.put("service18","Sewerage Cleaning");
      DatabaseReference ref = workersUnderServiceRef.child("sewerageCleaning").push();
      ref.setValue(uid);

    }
    if (chipDelivery.isChecked()) {
      serviceOffered.put("service19","Delivery Services");
      DatabaseReference ref = workersUnderServiceRef.child("deliveryServices").push();
      ref.setValue(uid);

    }

    mDatabase.child("users").child("workerServicesOffered").child(uid).setValue(serviceOffered).addOnCompleteListener(new OnCompleteListener<Void>() {
      @Override
      public void onComplete(@NonNull Task<Void> task) {
        if (task.isSuccessful()) {
          Toast.makeText(Services.this, "Successfully added to database",
              Toast.LENGTH_LONG).show();
          Intent intent = new Intent(Services.this,
              WorkerVerification.class);
          startActivity(intent);
          finish();
        } else {
          Toast.makeText(Services.this, "failed to add in the database",
              Toast.LENGTH_LONG).show();
        }
      }
    });
  }
}