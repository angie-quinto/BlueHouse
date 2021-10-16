package com.example.bluehousev3.fragments_client;

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


public class Profile extends Fragment {

  private TextView tvUserId, tvName, tvAge, tvAddress, tvGender, tvPhoneNum,
      tvEmail;
  private ImageView ivPic;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_profile2, container, false);

    tvUserId = view.findViewById(R.id.tv_clientId);
    tvName = view.findViewById(R.id.tv_clientName);
    tvAge = view.findViewById(R.id.tv_clientAge);
    tvAddress = view.findViewById(R.id.tv_clientAdd);
    tvGender = view.findViewById(R.id.tv_client_gender);
    tvPhoneNum = view.findViewById(R.id.tv_clientPhone);
    tvEmail = view.findViewById(R.id.tv_clientEmail);

    ivPic = view.findViewById(R.id.iv_clientProfilePic);

    setProfile();
    return view;
  }
  private void setProfile() {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    if (user != null) {

      String email = user.getEmail();
      tvEmail.setText(email);
      // Check if user's email is verified
      //boolean emailVerified = user.isEmailVerified();

      // The user's ID, unique to the Firebase project. Do NOT use this value to
      // authenticate with your backend server, if you have one. Use
      // FirebaseUser.getIdToken() instead.
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

          tvName.setText(name);
          tvAddress.setText(address);
          tvAge.setText(String.valueOf(age));
          tvGender.setText(gender);
          tvPhoneNum.setText(phoneNum);

         // todo: load client pic

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