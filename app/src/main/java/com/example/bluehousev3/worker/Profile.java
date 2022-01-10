package com.example.bluehousev3.worker;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
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
import com.example.bluehousev3.worker.transactions.Chat;
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
  private TextView  tvName, tvAge, tvAddress,tvPhoneNum,
      tvEmail, tvSignout, tvServicesOffered;
  private String imgUrl;

  private ImageView ivPic, ivEdit, ivVerified;
  private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
  private final String uid = user.getUid();
  private final DatabaseReference profileRef = FirebaseDatabase.getInstance().getReference().child("users/workers").child(uid);

  private final DatabaseReference photoRef = FirebaseDatabase.getInstance().getReference().child("users/workerIds").child(uid);


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    View view =inflater.inflate(R.layout.fragment_profile, container, false);
    String profilePath = "users/workers/"+uid;
    String imgPath = "users/workerIds/"+uid+"/SelfieUrl";
    Bundle bundle = new Bundle();
    tvName = view.findViewById(R.id.tv_name);
    tvAge = view.findViewById(R.id.tv_age);
    tvAddress = view.findViewById(R.id.tv_address);
    ivVerified = view.findViewById(R.id.iv_verified);

    tvPhoneNum = view.findViewById(R.id.tv_phoneNum);
    tvEmail = view.findViewById(R.id.tv_email);
    ivPic = view.findViewById(R.id.iv_workerPic);


    tvSignout = view.findViewById(R.id.tv_signout);
    tvServicesOffered = view.findViewById(R.id.tv_myServices);

    ivEdit = view.findViewById(R.id.iv_edit_profile);

    setProfile();

    photoRef.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        imgUrl = snapshot.child("SelfieUrl").getValue(String.class);
        Picasso.get()
                .load(imgUrl)
                .resize(350, 350)
                .into(ivPic);
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {

      }
    });


    ivEdit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        bundle.putString("PROFILE_PATH", profilePath);
        if (imgUrl != null) {
          bundle.putString("IMG_URL", imgUrl);
          bundle.putString("IMG_PATH", imgPath);
        }
        Fragment editProfile = new EditProfile();
        editProfile.setArguments(bundle);
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container_worker, editProfile);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();
      }
    });

    tvServicesOffered.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Fragment servicesOffered = new ServicesOffered();
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container_worker, servicesOffered);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();
      }
    });

    tvSignout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        new AlertDialog.Builder(getActivity())
           .setTitle("Sign Out")
           .setMessage("Are you sure you want to sign out?")
           .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int which) {
               FirebaseAuth.getInstance().signOut();
               Toast.makeText(getContext(), "signed out", Toast.LENGTH_LONG).show();
               Intent intent = new Intent(getContext(), LogIn.class);
               startActivity(intent);
               requireActivity().finish();
             }
           })
           .setNegativeButton("No", null)
           .setIcon(R.drawable.ic_logout)
           .show();


      }
    });

  return view;
  }

  @SuppressLint("SetTextI18n")
  private void setProfile() {
    if (user != null) {
      String email = user.getEmail();
      tvEmail.setText(email);
      ValueEventListener eventListener = new ValueEventListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
          String name = dataSnapshot.child("fullName").getValue(String.class);
          String address = dataSnapshot.child("address").getValue(String.class);
          String birthdate = dataSnapshot.child("birthdate").getValue(String.class);
          String phoneNum = dataSnapshot.child("phoneNumber").getValue(String.class);

          String status = dataSnapshot.child("status").getValue(String.class);

          if (status != null) {
            if (status.equals("verified")) {
              ivVerified.setVisibility(View.VISIBLE);
              ivVerified.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  Toast.makeText(getContext(), "Verified User", Toast.LENGTH_SHORT).show();
                }
              });
            }
          }


          tvName.setText(name);
          tvAddress.setText(address);
          tvAge.setText(birthdate);
          tvPhoneNum.setText("+63 " + phoneNum);
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
          Log.d("workerError", "onCancelled: Worker Profile Error");
        }
      };
      profileRef.addValueEventListener(eventListener);
    } else {
      Toast.makeText(getContext(), "user is null", Toast.LENGTH_LONG).show();
    }
  }
  }


