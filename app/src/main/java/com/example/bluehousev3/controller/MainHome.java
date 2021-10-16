package com.example.bluehousev3.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bluehousev3.model.ClientJobPosts;
import com.example.bluehousev3.views.LogIn;
import com.example.bluehousev3.R;
import com.example.bluehousev3.fragments_worker.Home;
import com.example.bluehousev3.fragments_worker.Profile;
import com.example.bluehousev3.fragments_worker.SavedOffers;
import com.example.bluehousev3.fragments_worker.Settings;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
  private DrawerLayout drawerLayout;
  private TextView tvProfileName;
  private ImageView ivProfilePic;
  private FirebaseAuth mAuth;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_worker);

    mAuth = FirebaseAuth.getInstance();

    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    drawerLayout = findViewById(R.id.drawer_layout);

    NavigationView navigationView = findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);

    View headerView = navigationView.getHeaderView(0);
    tvProfileName = headerView.findViewById(R.id.tv_profileName);
    ivProfilePic = headerView.findViewById(R.id.iv_profile);
    setProfile();

    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,
        R.string.nav_open, R.string.nav_close);
    drawerLayout.addDrawerListener(toggle);
    toggle.syncState();

    if (savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
          new Home()).commit();
      navigationView.setCheckedItem(R.id.worker_navHome);
    }
  }
  @Override
  public void onBackPressed() {
    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
      drawerLayout.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }
  @SuppressLint("NonConstantResourceId")
  @Override
  public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
    switch (item.getItemId()) {
      case R.id.worker_navHome:
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
            new Home()).commit();
      break;
      case R.id.worker_settings:
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
            new Settings()).commit();
      break;
      case R.id.worker_profile:
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
            new Profile()).commit();
      break;
      case R.id.worker_savedServices:
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
            new SavedOffers()).commit();
      break;
      case R.id.worker_signOut:
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(MainHome.this, "user signed out", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(MainHome.this, LogIn.class);
        startActivity(intent);
        finish();
    }
    drawerLayout.closeDrawer(GravityCompat.START);
    return true;
}
private void setProfile() {
    FirebaseUser currentUser = mAuth.getCurrentUser();
    if (currentUser != null) {
      String uid = currentUser.getUid();
      DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
      DatabaseReference userRef = rootRef.child("users");
      DatabaseReference currRef = userRef.child(uid);

      ValueEventListener eventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
          String fullName = snapshot.child("fullName").getValue(String.class);
          String imgUrl = snapshot.child("SelfieUrl").getValue(String.class);
          tvProfileName.setText(fullName);

          Picasso.get()
              .load(imgUrl)
              .into(ivProfilePic);
        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {
          Toast.makeText(MainHome.this, "cannot read user's name and profile pic. Please sign out" +
                   "and login again.",
              Toast.LENGTH_SHORT).show();
        }
      };
      currRef.addValueEventListener(eventListener);
    }
  }
}
