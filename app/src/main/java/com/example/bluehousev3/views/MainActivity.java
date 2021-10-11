package com.example.bluehousev3.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.example.bluehousev3.R;
import com.example.bluehousev3.controller.MainHome;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    if (currentUser != null) {
      String uid = currentUser.getUid();
      DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
      DatabaseReference userRef = rootRef.child("users");
      DatabaseReference currRef = userRef.child(uid);
      ValueEventListener eventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
          String userType = snapshot.child("userType").getValue(String.class);
          assert userType != null;

          if (userType.equals("Worker")) {
            Intent intent = new Intent(MainActivity.this, MainHome.class);
            startActivity(intent);
            finish();
          } else if (userType.equals("Client")) {
            Intent intent = new Intent(MainActivity.this,
                ClientHomePage.class);
            startActivity(intent);
            finish();
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
      currRef.addValueEventListener(eventListener);
    } else {
      Intent intent = new Intent(MainActivity.this, LogIn.class);
      startActivity(intent);
      finish();
    }
  }
}

