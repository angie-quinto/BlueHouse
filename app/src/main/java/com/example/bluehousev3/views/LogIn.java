package com.example.bluehousev3.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private String mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        txtForgotPass = findViewById(R.id.edt_forgotPass);
        TextView txtRegister = findViewById(R.id.edt_register);
        Button btnLogin = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.progressBar2);

        mAuth = FirebaseAuth.getInstance();

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogIn.this, Register.class);
                startActivity(intent);
                finish();

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
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
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    assert currentUser != null;
                    String uid = currentUser.getUid();
                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference userRef = rootRef.child("users");
                    DatabaseReference currRef = userRef.child(uid);

                    ValueEventListener eventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String userType = snapshot.child("userType").getValue(String.class);
                             mName = snapshot.child("fullName").getValue(String.class);
                            assert userType != null;

                            if (userType.equals("Worker")) {
                                Intent intent = new Intent(LogIn.this, MainHome.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(LogIn.this, "Welcome back " + mName,
                                    Toast.LENGTH_LONG).show();
                            } else if (userType.equals("Client")) {
                                Intent intent = new Intent(LogIn.this,
                                ClientHomePage.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(LogIn.this, "Welcome back " + mName,
                                    Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(LogIn.this, "database error, please log in again",
                            Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LogIn.this, LogIn.class);
                            startActivity(intent);
                            finish();
                        }
                    };
                    currRef.addValueEventListener(eventListener);

                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                    new AlertDialog.Builder(LogIn.this)
                        .setTitle("Login Failed")
                        .setMessage("email or password is invalid. Please provide a valid email and password.")
                        .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        }).show();
                    edtEmail.requestFocus();
                }
            }
        });
    }
}

