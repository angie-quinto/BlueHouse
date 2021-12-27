package com.example.bluehousev3.views;


import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.app.AlertDialog;


import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;

import android.widget.Button;

import android.widget.ProgressBar;

import android.widget.Toast;


import com.example.bluehousev3.R;

import com.google.android.material.textfield.TextInputEditText;




public class Register extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        new AlertDialog.Builder(this)
            .setTitle("Privacy Policy")
            .setMessage("BlueHouse collects user information to verify if a user can register based on Philippine Labor laws (RA7610 & RA9231). BlueHouse is committed in following ethical practices, protecting the personal information of all users, and will not rent, barter, sell, permit, or give away to anyone unaffiliated with BlueHouse to use their data. \n" +
                    "\n" +
                    "This Privacy Policy explains how your personal information is collected, used, and disclosed by BlueHouse. This Privacy Policy applies to our application named BlueHouse. \n" +
                    "\n" +
                    "By accessing or using our Service, you signify that you agree to our collection, and storage of your personal information.\n" +
                    "\n" +
                    "By clicking Agree, you agree to our Terms and that you have read our Privacy Policy.")

            .setPositiveButton("Agree", new DialogInterface.OnClickListener() {
                @SuppressLint("CutPasteId")
                public void onClick(DialogInterface dialog, int which) {


                    TextInputEditText tietName = findViewById(R.id.tiet_name);
                    TextInputEditText tietEmail = findViewById(R.id.tiet_email);
                    TextInputEditText tietPassword = findViewById(R.id.tiet_password);
                    TextInputEditText tietRetypePass = findViewById(R.id.tiet_retype_pass);
                    TextInputEditText tietMobileNum = findViewById(R.id.tiet_mobile_num);
                    Button btnRegister = findViewById(R.id.btn_register);
                    ProgressBar progressBar = findViewById(R.id.progressBar);


                    btnRegister.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            progressBar.setVisibility(View.VISIBLE);
                            String name = tietName.getText().toString();
                            String email = tietEmail.getText().toString();
                            String password = tietPassword.getText().toString();
                            String retypePass = tietRetypePass.getText().toString();
                            String mobileNum = tietMobileNum.getText().toString();


                            if (password.length() < 8) {
                                progressBar.setVisibility(View.INVISIBLE);
                                tietPassword.setError("Password length must be at least 8 characters long");
                                tietPassword.requestFocus();
                                return;
                            }
                            if (!password.equals(retypePass)) {
                                progressBar.setVisibility(View.INVISIBLE);
                                tietRetypePass.setError("Password did not matched");
                                tietRetypePass.requestFocus();
                                return;
                            }
                            if (name.isEmpty()) {
                                progressBar.setVisibility(View.INVISIBLE);
                                tietName.setError("Please enter your full name");
                                tietName.requestFocus();
                                return;
                            }
                            if (email.isEmpty()) {
                                progressBar.setVisibility(View.INVISIBLE);
                                tietEmail.setError("Please enter your email");
                                tietEmail.requestFocus();
                                return;
                            }

                            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                progressBar.setVisibility(View.INVISIBLE);
                                tietEmail.setError("Please provide a valid email");
                                tietEmail.requestFocus();
                                return;
                            }

                            if (mobileNum.length() != 10) {
                                progressBar.setVisibility(View.INVISIBLE);
                                tietMobileNum.setError("please provide a valid mobile number format");
                                tietMobileNum.requestFocus();
                                return;
                            }

                            Bundle bundle = new Bundle();
                            bundle.putString("userName", name);
                            bundle.putString("userEmail", email);
                            bundle.putString("userPassword", password);
                            bundle.putString("userMobileNum", mobileNum);


                            Intent intent = new Intent(Register.this, Register2.class);
                            intent.putExtras(bundle);
                            progressBar.setVisibility(View.INVISIBLE);
                            startActivity(intent);
                            finish();
                        }
                    });




                }
            })

            .setNegativeButton("Disagree", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(Register.this, "You need to agree in the app's privacy policy in order to use it", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Register.this, LogIn.class);
                    startActivity(intent);
                    finish();
                }
            })
            .setIcon(R.drawable.privacy_policy)
            .show();


    }














}
