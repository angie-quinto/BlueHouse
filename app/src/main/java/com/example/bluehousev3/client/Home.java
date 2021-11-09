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
import com.example.bluehousev3.client.available_workers.Beautician;
import com.example.bluehousev3.client.available_workers.Carpentry;
import com.example.bluehousev3.client.available_workers.ComputerElectronicRepair;
import com.example.bluehousev3.client.available_workers.Cooking;
import com.example.bluehousev3.client.available_workers.ElectricalMaintenance;
import com.example.bluehousev3.client.available_workers.Gardening;
import com.example.bluehousev3.client.available_workers.HomeApplianceMaintenance;
import com.example.bluehousev3.client.available_workers.HouseCleaning;
import com.example.bluehousev3.client.available_workers.JetMaticPumpMaintenance;
import com.example.bluehousev3.client.available_workers.Laundry;
import com.example.bluehousev3.client.available_workers.Mechanic;
import com.example.bluehousev3.client.available_workers.PestControlFumigation;
import com.example.bluehousev3.client.available_workers.Plumbing;
import com.example.bluehousev3.client.available_workers.Roofing;
import com.example.bluehousev3.client.available_workers.SepticTankMaintenance;
import com.example.bluehousev3.client.available_workers.Upholstery;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Home extends Fragment  implements ServiceAdapter.OnServiceListener  {
  ArrayList<String> services;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View view =  inflater.inflate(R.layout.fragment_home_client, container, false);
      services = new ArrayList<>();

      ServiceAdapter adapter = new ServiceAdapter(services , this);
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

      if (service.equals("Plumbing/Water Pipe Maintenance")) {
        Fragment plumbing = new Plumbing();
          FragmentTransaction ft = getParentFragmentManager().beginTransaction();
          ft.replace(R.id.fragment_container_client, plumbing);
          ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
          ft.addToBackStack(null);
          ft.commit();
      }

      if (service.equals("Carpentry")) {
          Fragment carpe = new Carpentry();
          FragmentTransaction ft = getParentFragmentManager().beginTransaction();
          ft.replace(R.id.fragment_container_client, carpe);
          ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
          ft.addToBackStack(null);
          ft.commit();

      }
      if (service.equals("JetMatic Pump Maintenance")) {
          Fragment jet = new JetMaticPumpMaintenance();
          FragmentTransaction ft = getParentFragmentManager().beginTransaction();
          ft.replace(R.id.fragment_container_client, jet);
          ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
          ft.addToBackStack(null);
          ft.commit();

      }
      if (service.equals("Upholstery")) {
          Fragment up = new Upholstery();
          FragmentTransaction ft = getParentFragmentManager().beginTransaction();
          ft.replace(R.id.fragment_container_client, up);
          ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
          ft.addToBackStack(null);
          ft.commit();

      }
      if (service.equals("Septic Tank Maintenance")) {
          Fragment sep = new SepticTankMaintenance();
          FragmentTransaction ft = getParentFragmentManager().beginTransaction();
          ft.replace(R.id.fragment_container_client, sep);
          ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
          ft.addToBackStack(null);
          ft.commit();

      }
      if (service.equals("Gardening")) {
          Fragment gar = new Gardening();
          FragmentTransaction ft = getParentFragmentManager().beginTransaction();
          ft.replace(R.id.fragment_container_client, gar);
          ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
          ft.addToBackStack(null);
          ft.commit();

      }
      if (service.equals("Home Appliance Maintenance")) {
          Fragment h = new HomeApplianceMaintenance();
          FragmentTransaction ft = getParentFragmentManager().beginTransaction();
          ft.replace(R.id.fragment_container_client, h);
          ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
          ft.addToBackStack(null);
          ft.commit();

      }
      if (service.equals("Roof Maintenance")) {
          Fragment r = new Roofing();
          FragmentTransaction ft = getParentFragmentManager().beginTransaction();
          ft.replace(R.id.fragment_container_client, r);
          ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
          ft.addToBackStack(null);
          ft.commit();

      }
      if (service.equals("Housekeeping")) {
          Fragment h = new HouseCleaning();
          FragmentTransaction ft = getParentFragmentManager().beginTransaction();
          ft.replace(R.id.fragment_container_client, h);
          ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
          ft.addToBackStack(null);
          ft.commit();

      }
      if (service.equals("Laundry Services")) {
          Fragment l = new Laundry();
          FragmentTransaction ft = getParentFragmentManager().beginTransaction();
          ft.replace(R.id.fragment_container_client, l);
          ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
          ft.addToBackStack(null);
          ft.commit();

      }
      if (service.equals("Beauty Salon Services")) {
          Fragment b = new Beautician();
          FragmentTransaction ft = getParentFragmentManager().beginTransaction();
          ft.replace(R.id.fragment_container_client, b);
          ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
          ft.addToBackStack(null);
          ft.commit();

      }
      if (service.equals("Electrical Maintenance")) {
          Fragment e = new ElectricalMaintenance();
          FragmentTransaction ft = getParentFragmentManager().beginTransaction();
          ft.replace(R.id.fragment_container_client, e);
          ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
          ft.addToBackStack(null);
          ft.commit();

      }
      if (service.equals("Computer/Electronic Repair")) {
          Fragment c = new ComputerElectronicRepair();
          FragmentTransaction ft = getParentFragmentManager().beginTransaction();
          ft.replace(R.id.fragment_container_client, c);
          ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
          ft.addToBackStack(null);
          ft.commit();

      }
      if (service.equals("Mechanic")) {
          Fragment m = new Mechanic();
          FragmentTransaction ft = getParentFragmentManager().beginTransaction();
          ft.replace(R.id.fragment_container_client, m);
          ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
          ft.addToBackStack(null);
          ft.commit();

      }
      if (service.equals("Pest Control & Fumigation")) {
          Fragment p = new PestControlFumigation();
          FragmentTransaction ft = getParentFragmentManager().beginTransaction();
          ft.replace(R.id.fragment_container_client, p);
          ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
          ft.addToBackStack(null);
          ft.commit();

      }
      if (service.equals("Cooking Services")) {
          Fragment c = new Cooking();
          FragmentTransaction ft = getParentFragmentManager().beginTransaction();
          ft.replace(R.id.fragment_container_client, c);
          ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
          ft.addToBackStack(null);
          ft.commit();

      }
  }
}





