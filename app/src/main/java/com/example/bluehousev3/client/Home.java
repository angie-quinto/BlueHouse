package com.example.bluehousev3.client;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.bluehousev3.R;
import com.example.bluehousev3.adapters.ServiceAdapter;
import com.example.bluehousev3.client.available_workers.AvailableWorkers;
//import com.example.bluehousev3.client.available_workers.Carpentry;
//import com.example.bluehousev3.client.available_workers.ComputerElectronicRepair;
//import com.example.bluehousev3.client.available_workers.Cooking;
//import com.example.bluehousev3.client.available_workers.ElectricalMaintenance;
//import com.example.bluehousev3.client.available_workers.Gardening;
//import com.example.bluehousev3.client.available_workers.HomeApplianceMaintenance;
//import com.example.bluehousev3.client.available_workers.HouseCleaning;
//import com.example.bluehousev3.client.available_workers.JetMaticPumpMaintenance;
//import com.example.bluehousev3.client.available_workers.Laundry;
//import com.example.bluehousev3.client.available_workers.Mechanic;
//import com.example.bluehousev3.client.available_workers.PestControlFumigation;
//import com.example.bluehousev3.client.available_workers.Plumbing;
//import com.example.bluehousev3.client.available_workers.Roofing;
//import com.example.bluehousev3.client.available_workers.SepticTankMaintenance;
//import com.example.bluehousev3.client.available_workers.Upholstery;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Home extends Fragment  implements ServiceAdapter.OnServiceListener  {
  ArrayList<String> services;
  ArrayList<Integer> images;
  private Bundle bundle;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View view =  inflater.inflate(R.layout.fragment_home_client, container, false);
      services = new ArrayList<>();
      images = new ArrayList<>();
      bundle = new Bundle();

      images.add(R.mipmap.plumbing_1_round);
      images.add(R.mipmap.carpe_1_round);
      images.add(R.mipmap.jetmatic_round);
      images.add(R.mipmap.upholstery_1_round);
      images.add(R.mipmap.septic_1_round);
      images.add(R.mipmap.gardening_1_round);
      images.add(R.mipmap.homeapp_1_round);
      images.add(R.mipmap.roof_1_round);
      images.add(R.mipmap.hkeep_1_round);
      images.add(R.mipmap.laundry_1_round);
      images.add(R.mipmap.beauty_1_round);
      images.add(R.mipmap.electric_1_round);
      images.add(R.mipmap.electronic_1_round);
      images.add(R.mipmap.mech_1_round);
      images.add(R.mipmap.pest_1_round);
      images.add(R.mipmap.cooking_1_round);


      ServiceAdapter adapter = new ServiceAdapter(services , this, images);
      RecyclerView rvServices = view.findViewById(R.id.rv_clientHome);
      rvServices.setLayoutManager(new LinearLayoutManager(getActivity()));
      rvServices.setAdapter(adapter);

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("services");
    reference.addValueEventListener(new ValueEventListener() {
      @SuppressLint("NotifyDataSetChanged")
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
          services.add(dataSnapshot.getValue(String.class));

        }
        adapter.notifyDataSetChanged();
      }
      @Override
      public void onCancelled(@NonNull DatabaseError error) {

      }
    });
    return view;
  }

  @Override
  public void onServiceClick(int position) {
      String service = services.get(position);

    switch (service) {
      case "Plumbing/Water Pipe Maintenance":
        bundle.putString("serviceType", "plumbingWaterPipeMaintenance");

        break;
      case "Carpentry":
        bundle.putString("serviceType", "carpentry");

        break;
      case "JetMatic Pump Maintenance":
        bundle.putString("serviceType", "jetMaticPumpMaintenance");

        break;
      case "Upholstery":
        bundle.putString("serviceType", "upholstery");

        break;
      case "Septic Tank Maintenance":

        bundle.putString("serviceType", "septicTankMaintenance");
        break;
      case "Gardening":

        bundle.putString("serviceType", "gardening");
        break;
      case "Home Appliance Maintenance":
        bundle.putString("serviceType", "homeApplianceMaintenance");

        break;
      case "Roof Maintenance":
        bundle.putString("serviceType", "roofMaintenance");

        break;
      case "Housekeeping":
        bundle.putString("serviceType", "housekeeping");

        break;
      case "Laundry Services":
        bundle.putString("serviceType", "laundry");
        break;
      case "Beauty Salon Services":
        bundle.putString("serviceType", "beautySalonServices");

        break;
      case "Electrical Maintenance":
        bundle.putString("serviceType", "electricalMaintenance");

        break;
      case "Computer/Electronic Repair":
        bundle.putString("serviceType", "computerAndElectronicRepair");

        break;
      case "Mechanic":
        bundle.putString("serviceType", "mechanic");

        break;
      case "Pest Control & Fumigation":
        bundle.putString("serviceType", "pestControlAndFumigation");

        break;
      case "Cooking Services":
        bundle.putString("serviceType", "cookingServices");

        break;
    }
      bundle.putString("serviceName", service);
      Fragment fragment = new AvailableWorkers();
      fragment.setArguments(bundle);
      FragmentTransaction ft = getParentFragmentManager().beginTransaction();
      ft.replace(R.id.fragment_container_client, fragment);
      ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
      ft.addToBackStack(null);
      ft.commit();

  }
}





