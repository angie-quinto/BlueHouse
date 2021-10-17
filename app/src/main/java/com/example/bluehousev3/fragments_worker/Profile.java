package com.example.bluehousev3.fragments_worker;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

// display user(worker) info
public class Profile extends Fragment {
  private TextView tvUserId, tvName, tvAge, tvAddress, tvGender, tvPhoneNum,
      tvEmail, tvHourlyRate;
  private ImageView ivPic;


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

    setProfile();

  return view;
  }

  private void setProfile() {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    if (user != null) {

      String email = user.getEmail();
      tvEmail.setText(email);
      String uid = user.getUid();
      tvUserId.setText(uid);
      DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
      DatabaseReference userReference = rootRef.child("users");
      DatabaseReference current_userRef = userReference.child(uid);
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
          tvName.setText(name);
          tvAddress.setText(address);
          tvAge.setText(String.valueOf(age));
          tvHourlyRate.setText(String.valueOf(rate));
          tvGender.setText(gender);
          tvPhoneNum.setText(phoneNum);

          Picasso.get()
              .load(imgUrl)
              .into(ivPic);

        }
        @Override
        public void onCancelled(DatabaseError databaseError) {}
      };
      current_userRef.addListenerForSingleValueEvent(eventListener);
    } else {
      Toast.makeText(getContext(), "user is null", Toast.LENGTH_LONG).show();
    }
    }
  }


