package com.example.bluehousev3.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.bluehousev3.R;
import com.example.bluehousev3.fragments_worker.Home;
import com.example.bluehousev3.fragments_worker.Profile;
import com.example.bluehousev3.fragments_worker.SavedOffers;
import com.example.bluehousev3.fragments_worker.Settings;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarItemView;
import com.google.android.material.navigation.NavigationBarView;

public class WorkerHomePage extends AppCompatActivity {
  BottomNavigationView bottomNavigationView;
  @SuppressLint("NonConstantResourceId")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_worker_home_page2);


    bottomNavigationView = findViewById(R.id.worker_bot_nav);
    bottomNavigationView.setSelectedItemId(R.id.worker_profile_nav);
    bottomNavigationView.setOnItemSelectedListener(item -> {

          switch (item.getItemId()) {
        case R.id.worker_profile_nav:
          getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container1,
              new Profile()).commit();
            break;
        case R.id.worker_popularSer_nav:
          getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container1,
              new Home()).commit();
          break;
        case R.id.worker_indemandSer_nav:
          getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container1,
              new SavedOffers()).commit();
          break;
        case R.id.worker_history_nav:
          getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container1,
              new Settings()).commit();
          break;
      }
      return true;
    });

    // todo: change this later
  }

}