package com.example.bluehousev3.client;


import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.bluehousev3.R;

import com.google.android.material.bottomnavigation.BottomNavigationView;
public class ClientHomePageActivity extends AppCompatActivity {


  @SuppressLint("NonConstantResourceId")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_client_home_page);

    BottomNavigationView bottomNavigationView = findViewById(R.id.client_bot_nav);
    bottomNavigationView.setItemIconTintList(null);
    bottomNavigationView.setSelectedItemId(R.id.client_home_nav);

    bottomNavigationView.setOnItemSelectedListener(item -> {

      switch (item.getItemId()) {
        case R.id.client_home_nav:
          getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_client,
              new Home()).commit();
          break;
        case R.id.client_profile_nav:
          getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_client,
              new Profile()).commit();
          break;
        case R.id.client_popularWorkers_nav:
          getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_client,
              new PopularServices()).commit();
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