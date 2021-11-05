package com.example.bluehousev3.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.bluehousev3.R;
import com.example.bluehousev3.adapters.ServiceAdapter;
import com.example.bluehousev3.client.Home;
import com.example.bluehousev3.client.ServiceRequests;
import com.example.bluehousev3.client.PopularWorkers;
import com.example.bluehousev3.client.Profile;
import com.example.bluehousev3.client.available_workers.Beautician;
import com.example.bluehousev3.client.available_workers.Carpentry;
import com.example.bluehousev3.client.available_workers.ComputerElectronicRepair;
import com.example.bluehousev3.client.available_workers.Cooking;
import com.example.bluehousev3.client.available_workers.DeliveryService;
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
import com.example.bluehousev3.client.available_workers.SewerageCleaning;
import com.example.bluehousev3.client.available_workers.Upholstery;
import com.example.bluehousev3.client.available_workers.WaterPipeMaintenance;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ClientHomePageActivity extends AppCompatActivity {


  @SuppressLint("NonConstantResourceId")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_client_home_page);



    BottomNavigationView bottomNavigationView = findViewById(R.id.client_bot_nav);
    bottomNavigationView.setItemIconTintList(null);
    bottomNavigationView.setSelectedItemId(R.id.client_home_nav);
    Home home = new Home(); // todo
    Profile profile = new Profile();
    PopularWorkers popularWorkers = new PopularWorkers();
    bottomNavigationView.setOnItemSelectedListener(item -> {

      switch (item.getItemId()) {
        case R.id.client_home_nav:
          getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_client,
              home).commit();
          break;
        case R.id.client_profile_nav:
          getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_client,
              new Profile()).commit();
          break;
        case R.id.client_popularWorkers_nav:
          getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_client,
              new PopularWorkers()).commit();
          break;
        case R.id.client_service_requests:
          getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_client,
              new ServiceRequests()).commit();
          break;
      }
      return true;
    });
  }



}