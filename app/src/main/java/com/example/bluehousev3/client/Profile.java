package com.example.bluehousev3.client;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class Profile extends Fragment {

  private TextView tvName, tvAge, tvAddress, tvPhoneNum,
      tvEmail, tvSignout, tvRequest;
  private ImageView ivProfile, ivVerfied;


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_profile2, container, false);


    tvName = view.findViewById(R.id.tv_clientName);
    tvAge = view.findViewById(R.id.tv_clientAge);
    tvAddress = view.findViewById(R.id.tv_clientAdd);
    tvPhoneNum = view.findViewById(R.id.tv_clientPhone);
    tvEmail = view.findViewById(R.id.tv_clientEmail);
    ivProfile = view.findViewById(R.id.iv_empPic);
    tvSignout = view.findViewById(R.id.tv_signout_emp);
    tvRequest = view.findViewById(R.id.tv_request);
    ivVerfied = view.findViewById(R.id.iv_verified_emp);

    setProfile();

    tvSignout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
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

    tvRequest.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        RequestAServiceType req = new RequestAServiceType();
        req.show(getParentFragmentManager(), "Request");
      }
    });
    return view;
  }
  private void setProfile() {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    if (user != null) {
      String email = user.getEmail();
      tvEmail.setText(email);
      String uid = user.getUid();

      DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
      DatabaseReference userReference = rootRef.child("users/employers");
      DatabaseReference current_userRef = userReference.child(uid);
      ValueEventListener eventListener = new ValueEventListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
          String name = dataSnapshot.child("fullName").getValue(String.class);
          String address = dataSnapshot.child("address").getValue(String.class);
          String age = dataSnapshot.child("birthdate").getValue(String.class);
          String status = dataSnapshot.child("status").getValue(String.class);
          String phoneNum =
              dataSnapshot.child("phoneNumber").getValue(String.class);

          if (status != null) {
            if (status.equals("verified")) {
              ivVerfied.setVisibility(View.VISIBLE);
              ivVerfied.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  Toast.makeText(getContext(), "Verified User", Toast.LENGTH_SHORT).show();
                }
              });
            }
          }

          tvName.setText(name);
          tvAddress.setText(address);
          tvAge.setText(age);
          tvPhoneNum.setText("+63" + phoneNum);

          DatabaseReference picRef = FirebaseDatabase.getInstance().getReference().child("users/employerIds").child(uid);
          picRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              String url = snapshot.child("SelfieUrl").getValue(String.class);
              Picasso.get().load(url).resize(350, 350).into(ivProfile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
          });

        }
        @Override
        public void onCancelled(DatabaseError databaseError) {}
      };
      current_userRef.addValueEventListener(eventListener);
    } else {
      Toast.makeText(getContext(), "user is null", Toast.LENGTH_LONG).show();
    }
  }
}