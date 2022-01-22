package com.example.bluehousev3.worker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.bluehousev3.R;
import com.example.bluehousev3.views.RegisterIds;
import com.example.bluehousev3.views.Services;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class EditServicesOffered extends Fragment {
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
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_edit_services_offered, container, false);

    mDatabase = FirebaseDatabase.getInstance().getReference();
    workersUnderServiceRef = FirebaseDatabase.getInstance().getReference().child("workersUnderService");

    chipPlumbing = view.findViewById(R.id.chp_plumbing_e);
    chipJetMatic = view.findViewById(R.id.chp_jetMatic_e);
    chipCarpentry = view.findViewById(R.id.chp_carpentry_e);
    chipUpholstery = view.findViewById(R.id.chp_upholstery_e);
    chipSeptic = view.findViewById(R.id.chp_septic_e);
    chipGardening = view.findViewById(R.id.chp_gardening_e);
    chipHomeAppliance = view.findViewById(R.id.chp_homeAppliance_e);
    chipRoofing = view.findViewById(R.id.chp_roofing_e);
    chipHouseCleaning = view.findViewById(R.id.chp_houseCleaning_e);
    chipLaundry = view.findViewById(R.id.chp_laundry_e);
    chipBeautician = view.findViewById(R.id.chp_beautician_e);
    chipElectricalMaintenance = view.findViewById(R.id.chp_electrical_maintenance_e);
    chipComputerRepair = view.findViewById(R.id.chp_computerRepair_e);
    chipMechanic = view.findViewById(R.id.chp_mechanic_e);
    chipPestControl = view.findViewById(R.id.chp_pestControl_e);
    chipCooking = view.findViewById(R.id.chp_cooking_e);
    Button btnSave = view.findViewById(R.id.btn_save_edit);
    servicesOffered = new HashMap<>();
    Button btnCancel = view.findViewById(R.id.btn_cancel_edit);

    btnSave.setOnClickListener(v -> new AlertDialog.Builder(getContext())
       .setTitle("Save Edit?")
       .setMessage("Are you sure you want to change your services offered?")

       .setPositiveButton("Yes", (dialog, which) -> goToRegister())

       .setNegativeButton("No", null)
       .setIcon(android.R.drawable.ic_dialog_alert)
       .show());

    btnCancel.setOnClickListener(v -> {
      Fragment profile = new Profile();
      FragmentTransaction ft = getParentFragmentManager().beginTransaction();
      ft.replace(R.id.fragment_container_worker, profile);
      ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
      ft.addToBackStack(null);
      ft.commit();
    });
    return view;
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

    mDatabase.child("users").child("workerServicesOffered").child(uid).setValue(servicesOffered).addOnCompleteListener(task -> {
      if (task.isSuccessful()) {
        Toast.makeText(getContext(), "Successfully added to services offered",
           Toast.LENGTH_LONG).show();
        Fragment profile = new Profile();
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container_worker, profile);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();
      } else {
        Toast.makeText(getContext(), "failed to add in the database",
           Toast.LENGTH_LONG).show();
      }
    });
  }




  }

