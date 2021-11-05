package com.example.bluehousev3.worker;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bluehousev3.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;

// display user(worker) info
public class Profile extends Fragment {
  private TextView tvUserId, tvName, tvAge, tvAddress, tvGender, tvPhoneNum,
      tvEmail, tvHourlyRate, tvServices;
  private ImageView ivPic;
  private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
  private final String uid = user.getUid();
  private final DatabaseReference profileRef = FirebaseDatabase.getInstance().getReference().child("users/workers").child(uid);
  private final DatabaseReference serviceOfferedRef = FirebaseDatabase.getInstance().getReference().child("users/workerServicesOffered").child(uid);
  private final DatabaseReference photoRef = FirebaseDatabase.getInstance().getReference().child("users/workerIds").child(uid);
  ArrayAdapter<String> arrayAdapter;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view =inflater.inflate(R.layout.fragment_profile, container, false);

    tvUserId = view.findViewById(R.id.tv_uid);
    tvName = view.findViewById(R.id.tv_name);
    tvAge = view.findViewById(R.id.tv_age);
    tvAddress = view.findViewById(R.id.tv_address);
    tvGender = view.findViewById(R.id.tv_gender);
    tvPhoneNum = view.findViewById(R.id.tv_phoneNum);
    tvEmail = view.findViewById(R.id.tv_email);
    tvHourlyRate = view.findViewById(R.id.tv_rate);
    ivPic = view.findViewById(R.id.iv_workerPic);
    tvServices = view.findViewById(R.id.tvServicesOff);

    setProfile();

  return view;
  }

  private void setProfile() {
    if (user != null) {
      String email = user.getEmail();
      tvEmail.setText("Email: " + email);
      tvUserId.setText("Uid: " + uid);
      ValueEventListener eventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
          String name = dataSnapshot.child("fullName").getValue(String.class);
          String address = dataSnapshot.child("address").getValue(String.class);
          int age = dataSnapshot.child("age").getValue(Integer.class);
          String gender = dataSnapshot.child("gender").getValue(String.class);
          String phoneNum =
              dataSnapshot.child("phoneNumber").getValue(String.class);
          int rate = dataSnapshot.child("hourlyRate").getValue(Integer.class);
          String imgUrl = dataSnapshot.child("SelfieUrl").getValue(String.class);
          tvName.setText("Full Name:  " + name);
          tvAddress.setText("Address: " + address);
          tvAge.setText("Age: " + String.valueOf(age));
          tvHourlyRate.setText("Hourly Rate: " + String.valueOf(rate));
          tvGender.setText("Gender: " + gender);
          tvPhoneNum.setText("Mobile Number: " + phoneNum);



        }
        @Override
        public void onCancelled(DatabaseError databaseError) {}
      };
      profileRef.addListenerForSingleValueEvent(eventListener);
    } else {
      Toast.makeText(getContext(), "user is null", Toast.LENGTH_LONG).show();
    }

    serviceOfferedRef.addValueEventListener(new ValueEventListener() {
      @SuppressLint("SetTextI18n")
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {


        Map<String, String> map = (Map<String, String>) snapshot.getValue();
        assert map != null;
        String service1 = (String) map.get("service1");
        String service2 = (String) map.get("service2");
        String service3 = (String) map.get("service4");
        String service4 = (String) map.get("service5");
        String service5 = (String) map.get("service6");
        String service6 = (String) map.get("service7");
        String service7 = (String) map.get("service8");
        String service8 = (String) map.get("service9");
        String service9 = (String) map.get("service10");
        String service10 = (String) map.get("service11");
        String service11 = (String) map.get("service12");
        String service12 = (String) map.get("service13");
        String service13 = (String) map.get("service14");
        String service14 = (String) map.get("service15");
        String service15 = (String) map.get("service16");
        String service16 = (String) map.get("service17");
        String service17 = (String) map.get("service18");
        String service18 = (String) map.get("service19");
        String service19 = (String) map.get("service20");

        ArrayList<String> servicesOffered = new ArrayList<>();
        if (service1 != null) {
          servicesOffered.add(service1);
        }
        if (service2 != null) {
          servicesOffered.add(service2);
        }
        if (service3 != null) {
          servicesOffered.add(service3);
        }
        if (service4 != null) {
          servicesOffered.add(service4);
        }
        if (service5 != null) {
          servicesOffered.add(service5);
        }
        if (service6 != null) {
          servicesOffered.add(service6);
        }
        if (service7 != null) {
          servicesOffered.add(service7);
        }
        if (service8 != null) {
          servicesOffered.add(service8);
        }
        if (service9 != null) {
          servicesOffered.add(service9);
        }
        if (service10 != null) {
          servicesOffered.add(service10);
        }
        if (service11 != null) {
          servicesOffered.add(service11);
        }
        if (service12 != null) {
          servicesOffered.add(service12);
        }
        if (service13 != null) {
          servicesOffered.add(service13);
        }
        if (service14 != null) {
          servicesOffered.add(service14);
        }
        if (service15 != null) {
          servicesOffered.add(service15);
        }
        if (service16 != null) {
          servicesOffered.add(service16);
        }
        if (service17 != null) {
          servicesOffered.add(service17);
        }
        if (service18 != null) {
          servicesOffered.add(service18);
        }
        if (service19 != null) {
          servicesOffered.add(service19);
        }
        tvServices.setText(" ");
        for (int i = 0; i < servicesOffered.size(); i++){
          tvServices.append(i + 1 +": " + servicesOffered.get(i) + ", ");
        }



      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {

      }
    });

    photoRef.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        String imgUrl = snapshot.child("SelfieUrl").getValue(String.class);
        Picasso.get()
                .load(imgUrl)
                .into(ivPic);
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {

      }
    });

    }
  }


