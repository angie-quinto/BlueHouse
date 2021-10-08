package com.example.bluehousev3.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bluehousev3.R;
import com.example.bluehousev3.controller.MainHome;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogIn extends AppCompatActivity {
    private EditText edtEmail;
    private EditText edtPassword;
    private TextView txtForgotPass;
    private TextView txtRegister;
    private Button btnLogin;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        txtForgotPass = findViewById(R.id.edt_forgotPass);
        txtRegister = findViewById(R.id.edt_register);
        btnLogin = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.progressBar2);

        mAuth = FirebaseAuth.getInstance();

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegister();

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            String uid = currentUser.getUid();
            DatabaseReference rootRef =
                FirebaseDatabase.getInstance().getReference();
            DatabaseReference userRef = rootRef.child("users");
            DatabaseReference currRef = userRef.child(uid);
            ValueEventListener eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String userType =
                        snapshot.child("userType").getValue(String.class);

                    if (userType.equals("Worker")) {
                        goToMainHome();
                    } else if (userType.equals("Client")) {
                        Intent intent = new Intent(LogIn.this,
                            ClientHomePage.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LogIn.this, "userType is: " + userType
                            , Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };
            currRef.addValueEventListener(eventListener);

        }
    }
    private void goToRegister() {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    private void login() {
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        if (email.isEmpty()) {
            edtEmail.setError("Email is required");
            edtEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            edtPassword.setError("password is required");
            edtPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(LogIn.this, "Welcome back user",
                            Toast.LENGTH_LONG).show();
                        goToMainHome();

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(LogIn.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                        edtEmail.setError("Invalid Email");
                        edtPassword.setError("Invalid Password");
                        edtEmail.requestFocus();
                    }
                }
            });
    }
    private void goToMainHome() {
    Intent intent = new Intent(LogIn.this, MainHome.class);
    startActivity(intent);
}

}