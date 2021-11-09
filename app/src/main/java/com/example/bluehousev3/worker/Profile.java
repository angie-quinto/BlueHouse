package com.example.bluehousev3.worker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bluehousev3.R;
import com.example.bluehousev3.views.LogIn;
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


public class Profile extends Fragment {
  private TextView tvUserId, tvName, tvAge, tvAddress, tvGender, tvPhoneNum,
      tvEmail, tvHourlyRate;
  private Button btnMyServicesOffered, btnSignOut;

  private ImageView ivPic;
  private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
  private final String uid = user.getUid();
  private final DatabaseReference profileRef = FirebaseDatabase.getInstance().getReference().child("users/workers").child(uid);
  private final DatabaseReference serviceOfferedRef = FirebaseDatabase.getInstance().getReference().child("users/workerServicesOffered").child(uid);
  private final DatabaseReference photoRef = FirebaseDatabase.getInstance().getReference().child("users/workerIds").child(uid);


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

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
    btnMyServicesOffered = view.findViewById(R.id.btn_serv_offered);
    btnSignOut = view.findViewById(R.id.btn_sign_out_worker);


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
          String rate = dataSnapshot.child("rate").getValue(String.class);
          String imgUrl = dataSnapshot.child("SelfieUrl").getValue(String.class);
          tvName.setText("Full Name:  " + name);
          tvAddress.setText("Address: " + address);
          tvAge.setText("Age: " + String.valueOf(age));
          tvHourlyRate.setText("Rate: " + rate);
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

    btnSignOut.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(getContext(), "signed out", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getContext(), LogIn.class);
        startActivity(intent);
        requireActivity().finish();
      }
    });

    }
  }


