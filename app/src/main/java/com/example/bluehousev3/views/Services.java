package com.example.bluehousev3.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bluehousev3.R;

import com.example.bluehousev3.worker.WorkerVerification;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Services extends AppCompatActivity {
  private Chip chipPlumbing, chipJetMatic, chipCarpentry,
      chipUpholstery, chipSeptic, chipGardening, chipHomeAppliance,
      chipRoofing, chipHouseCleaning, chipLaundry, chipBeautician,
      chipElectricalMaintenance, chipComputerRepair, chipMechanic,
      chipPestControl, chipCooking;
  private Map<String, String> servicesOffered;
  private DatabaseReference mDatabase;
  private DatabaseReference workersUnderServiceRef;
  private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
  private final String uid;

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
    Button btnProceed = findViewById(R.id.btn_proceed);
    servicesOffered = new HashMap<>();

    btnProceed.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        goToRegister();
      }
    });
}

  private void goToRegister() {
    if (chipPlumbing.isChecked()) {
      servicesOffered.put("service1","Plumbing/Water Pipe Maintenance");
      DatabaseReference ref = workersUnderServiceRef.child("plumbingWaterPipeMaintenance").push();
      ref.setValue(uid);

    }

    if (chipCarpentry.isChecked()) {
      servicesOffered.put("service2","Carpentry");
      DatabaseReference ref = workersUnderServiceRef.child("carpentry").push();
      ref.setValue(uid);

    }
    if (chipJetMatic.isChecked()) {
      servicesOffered.put("service3","JetMatic Pump Maintenance");
      DatabaseReference ref = workersUnderServiceRef.child("jetMaticPumpMaintenance").push();
      ref.setValue(uid);

    }
    if (chipUpholstery.isChecked()) {
      servicesOffered.put("service4","Upholstery");
      DatabaseReference ref = workersUnderServiceRef.child("upholstery").push();
      ref.setValue(uid);

    }
    if (chipSeptic.isChecked()) {
      servicesOffered.put("service5","Septic Tank Maintenance");
      DatabaseReference ref = workersUnderServiceRef.child("septicTankMaintenance").push();
      ref.setValue(uid);

    }
    if (chipGardening.isChecked()) {
      servicesOffered.put("service6","Gardening");
      DatabaseReference ref = workersUnderServiceRef.child("gardening").push();
      ref.setValue(uid);

    }
    if (chipHomeAppliance.isChecked()) {
      servicesOffered.put("service7","Home Appliance Maintenance");
      DatabaseReference ref = workersUnderServiceRef.child("homeApplianceMaintenance").push();
      ref.setValue(uid);

    }
    if (chipRoofing.isChecked()) {
      servicesOffered.put("service8","Roof Maintenance");
      DatabaseReference ref = workersUnderServiceRef.child("roofMaintenance").push();
      ref.setValue(uid);

    }
    if (chipHouseCleaning.isChecked()) {
      servicesOffered.put("service9","Housekeeping");
      DatabaseReference ref = workersUnderServiceRef.child("housekeeping").push();
      ref.setValue(uid);

    }
    if (chipLaundry.isChecked()) {
      servicesOffered.put("service10","Laundry Services");
      DatabaseReference ref = workersUnderServiceRef.child("laundry").push();
      ref.setValue(uid);

    }
    if (chipBeautician.isChecked()) {
      servicesOffered.put("service11","Beauty Salon Services");
      DatabaseReference ref = workersUnderServiceRef.child("beautySalonServices").push();
      ref.setValue(uid);

    }
    if (chipElectricalMaintenance.isChecked()) {
      servicesOffered.put("service12","Electrical Maintenance");
      DatabaseReference ref = workersUnderServiceRef.child("electricalMaintenance").push();
      ref.setValue(uid);

    }
    if (chipComputerRepair.isChecked()) {
      servicesOffered.put("service13","Computer/Electronic Device Repair");
      DatabaseReference ref = workersUnderServiceRef.child("computerAndElectronicRepair").push();
      ref.setValue(uid);

    }
    if (chipMechanic.isChecked()) {
      servicesOffered.put("service14","Mechanic");
      DatabaseReference ref = workersUnderServiceRef.child("mechanic").push();
      ref.setValue(uid);

    }
    if (chipPestControl.isChecked()) {
      servicesOffered.put("service15","Pest Control & Fumigation");
      DatabaseReference ref = workersUnderServiceRef.child("pestControlAndFumigation").push();
      ref.setValue(uid);

    }
    if (chipCooking.isChecked()) {
      servicesOffered.put("service16","Cooking Services");
      DatabaseReference ref = workersUnderServiceRef.child("cookingServices").push();
      ref.setValue(uid);

    }

    mDatabase.child("users").child("workerServicesOffered").child(uid).setValue(servicesOffered).addOnCompleteListener(new OnCompleteListener<Void>() {
      @Override
      public void onComplete(@NonNull Task<Void> task) {
        if (task.isSuccessful()) {
          Toast.makeText(Services.this, "Successfully added to services offered",
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