package com.example.bluehousev3.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bluehousev3.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ForgotPassword extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_forgot_password);

    TextInputEditText tietEmail = findViewById(R.id.tiet_forgot);
    Button btnReset = findViewById(R.id.btn_reset_pass);
    TextView tvLogin = findViewById(R.id.tv_login_reset);
    FirebaseAuth auth = FirebaseAuth.getInstance();


    tvLogin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(ForgotPassword.this, LogIn.class);
        startActivity(intent);
        finish();
      }
    });

    btnReset.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        String email = tietEmail.getText().toString().trim();

        if (email.isEmpty()) {
          tietEmail.setError("Email is required!");
          tietEmail.requestFocus();
          return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
          tietEmail.setError("Please provide valid email!");
          tietEmail.requestFocus();
          return;
        }


        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
          @Override
          public void onComplete(@NonNull Task<Void> task) {
            if(task.isSuccessful()){
              Toast.makeText(ForgotPassword.this, "Check your email to for your new password", Toast.LENGTH_LONG).show();
            }else{
              Toast.makeText(ForgotPassword.this, "Error! Please check the email you provided", Toast.LENGTH_LONG).show();
            }
          }
        });

      }
    });
  }
}