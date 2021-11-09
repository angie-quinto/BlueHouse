package com.example.bluehousev3.worker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.bluehousev3.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class WorkerHomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_home_page);

        BottomNavigationView bottomNavigationView = findViewById(R.id.worker_bottom_nav);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setSelectedItemId(R.id.worker_pending);

        bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.worker_pending:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_worker,
                          new PendingRequests()).commit();
                    break;
                case R.id.worker_profile_nav:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_worker,
                            new Profile()).commit();
                    Toast.makeText(getApplicationContext(), "tap", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.worker_accepted_req:
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_client,
//                            new PopularWorkers()).commit();
                    break;
                case R.id.worker_popularServices_nav:
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_client,
//                            new ServiceRequests()).commit();
                    break;
            }
            return true;
        });

    }
}