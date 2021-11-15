package com.example.bluehousev3.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.example.bluehousev3.R;
import com.example.bluehousev3.client.ClientHomePageActivity;
import com.example.bluehousev3.worker.WorkerHomePage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
  private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    FirebaseUser currentUser = mAuth.getCurrentUser();
    if (currentUser != null) {
        String uid = currentUser.getUid();
       DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users/usertype");
        ValueEventListener eventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
          String userType = snapshot.child(uid).getValue(String.class);
          if (userType != null) {
              if (userType.equals("worker")) {
                  Intent intent = new Intent(MainActivity.this, WorkerHomePage.class);
                  startActivity(intent);
                  finish();
              } else if (userType.equals("employer")) {
                  Intent intent = new Intent(MainActivity.this,
                          ClientHomePageActivity.class);
                  startActivity(intent);
                  finish();
              } else {
                  Intent intent = new Intent(MainActivity.this, LogIn.class);
                  startActivity(intent);
                  finish();
              }
          }

        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {
          Toast.makeText(MainActivity.this, "database error, please log in again",
              Toast.LENGTH_SHORT).show();
          Intent intent = new Intent(MainActivity.this, LogIn.class);
          startActivity(intent);
          finish();
        }
      };
      reference.addValueEventListener(eventListener);
    } else {
      Intent intent = new Intent(MainActivity.this, LogIn.class);
      startActivity(intent);
      finish();
    }
  }
}

