package com.example.bluehousev3.views;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.bluehousev3.R;
import com.example.bluehousev3.fragments_client.InDemandServices;
import com.example.bluehousev3.fragments_client.PopularServices;
import com.example.bluehousev3.fragments_client.Profile;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ClientHomePage extends AppCompatActivity {
  @SuppressLint("NonConstantResourceId")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_client_home_page);

    BottomNavigationView bottomNavigationView = findViewById(R.id.client_bot_nav);
    bottomNavigationView.setSelectedItemId(R.id.client_home_nav);
    bottomNavigationView.setOnItemSelectedListener(item -> {

      switch (item.getItemId()) {
        case R.id.client_home_nav:
          getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_client,
              new com.example.bluehousev3.fragments_client.Home()).commit();
          break;
        case R.id.client_profile_nav:
          getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_client,
              new Profile()).commit();
          break;
        case R.id.client_indemandSer_nav:
          getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_client,
              new PopularServices()).commit();
          break;
        case R.id.client_popularSer_nav:
          getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_client,
              new InDemandServices()).commit();
          break;
      }
      return true;
    });
  }
}