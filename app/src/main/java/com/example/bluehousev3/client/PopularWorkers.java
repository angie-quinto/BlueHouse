package com.example.bluehousev3.client;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bluehousev3.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class PopularWorkers extends Fragment {
  private HashMap<String, Integer> popularServiceMap;
  private int plumber = 0, beautician = 0, carpentry = 0, computerElectronic = 0, cooking = 0, delivery = 0, electrical = 0, gardening = 0, homeApp = 0,
          houseCleaning = 0, jetMatic = 0, laundry = 0, mechanic = 0, pestControl = 0, roofing = 0, septic = 0, sewerage = 0,upholstery = 0, waterPipe = 0;
  private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10;
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_popular_workers_client, container, false);
    popularServiceMap = new HashMap<>();

    tv1 = view.findViewById(R.id.tv1);
    tv2 = view.findViewById(R.id.tv2);

    tv3 = view.findViewById(R.id.tv3);
    tv4 = view.findViewById(R.id.tv4);
    tv5 = view.findViewById(R.id.tv5);
    tv6 = view.findViewById(R.id.tv6);
    tv7 = view.findViewById(R.id.tv7);
    tv8 = view.findViewById(R.id.tv8);
    tv9 = view.findViewById(R.id.tv9);
    tv10 = view.findViewById(R.id.tv10);

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users/clientRequests");

    reference.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
          for (DataSnapshot snapshot2 : snapshot1.getChildren()) {
            for (DataSnapshot snapshot3 : snapshot2.getChildren()) {
              if (snapshot3 != null) {
                if (snapshot3.child("serviceType").getValue(String.class).equals("Plumbing")) {
                  plumber++;
                }
                if (snapshot3.child("serviceType").getValue(String.class).equals("Water Pipe Maintenance")) {
                  waterPipe++;
                }
                if (snapshot3.child("serviceType").getValue(String.class).equals("Carpentry")) {
                  carpentry++;
                }
                if (snapshot3.child("serviceType").getValue(String.class).equals("JetMatic Pump Maintenance")) {
                  jetMatic++;
                }
                if (snapshot3.child("serviceType").getValue(String.class).equals("Upholstery")) {
                  upholstery++;
                }
                if (snapshot3.child("serviceType").getValue(String.class).equals("Septic Tank Maintenance")) {
                  septic++;
                }
                if (snapshot3.child("serviceType").getValue(String.class).equals("Gardening")) {
                  gardening++;
                }
                if (snapshot3.child("serviceType").getValue(String.class).equals("Home Appliance Maintenance")) {
                  homeApp++;
                }
                if (snapshot3.child("serviceType").getValue(String.class).equals("Roofing")) {
                  roofing++;
                }
                if (snapshot3.child("serviceType").getValue(String.class).equals("House Cleaning")) {
                  houseCleaning++;
                }
                if (snapshot3.child("serviceType").getValue(String.class).equals("Laundry services")) {
                  laundry++;
                }
                if (snapshot3.child("serviceType").getValue(String.class).equals("Beautician")) {
                  beautician++;
                }
                if (snapshot3.child("serviceType").getValue(String.class).equals("Electrical Maintenance")) {
                  electrical++;
                }
                if (snapshot3.child("serviceType").getValue(String.class).equals("Computer/Electronic Repair")) {
                  computerElectronic++;
                }
                if (snapshot3.child("serviceType").getValue(String.class).equals("Mechanic")) {
                  mechanic++;
                }
                if (snapshot3.child("serviceType").getValue(String.class).equals("Pest Control & Fumigation")) {
                  pestControl++;
                }
                if (snapshot3.child("serviceType").getValue(String.class).equals("Cooking Services")) {
                  cooking++;
                }
                if (snapshot3.child("serviceType").getValue(String.class).equals("Sewerage Cleaning")) {
                  sewerage++;
                }
                if (snapshot3.child("serviceType").getValue(String.class).equals("Deliver Services")) {
                  delivery++;
                }
              }

            }
          }
        }
          popularServiceMap.put("Plumbing", plumber);
          popularServiceMap.put("Beautician", beautician);
          popularServiceMap.put("Carpentry", carpentry);
          popularServiceMap.put("Computer & Electronic Repair", computerElectronic);
          popularServiceMap.put("Cooking", cooking);
          popularServiceMap.put("Delivery Services", delivery);
          popularServiceMap.put("Electrical Maintenance", electrical);
          popularServiceMap.put("Gardening", gardening);
          popularServiceMap.put("Home Appliance Maintenance", homeApp);
          popularServiceMap.put("House Cleaning", houseCleaning);
          popularServiceMap.put("JetMatic Pump Maintenance", jetMatic);
          popularServiceMap.put("Laundry", laundry);
          popularServiceMap.put("Mechanic", mechanic);
          popularServiceMap.put("Pest Control & Fumigation", pestControl);
          popularServiceMap.put("Roofing", roofing);
          popularServiceMap.put("Septic Tank Maintenance", septic);
          popularServiceMap.put("Sewerage Cleaning", sewerage);
          popularServiceMap.put("Upholstery", upholstery);
          popularServiceMap.put("WaterPipe", waterPipe);

        Map<String, Integer> sortedMap = popularServiceMap.entrySet().stream()
                .sorted(Comparator.comparingInt(Map.Entry::getValue))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> { throw new AssertionError(); },
                        LinkedHashMap::new
                ));
        System.out.println(sortedMap.entrySet());
        tv1.setText(sortedMap.keySet().toArray()[18].toString());
        tv2.setText(sortedMap.keySet().toArray()[17].toString());
        tv3.setText(sortedMap.keySet().toArray()[16].toString());
        tv4.setText(sortedMap.keySet().toArray()[15].toString());
        tv5.setText(sortedMap.keySet().toArray()[14].toString());
        tv6.setText(sortedMap.keySet().toArray()[13].toString());
        tv7.setText(sortedMap.keySet().toArray()[12].toString());
        tv8.setText(sortedMap.keySet().toArray()[11].toString());
        tv9.setText(sortedMap.keySet().toArray()[10].toString());
        tv10.setText(sortedMap.keySet().toArray()[9].toString());


      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {

      }
    });


    return view;
  }
}