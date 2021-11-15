package com.example.bluehousev3.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.DatePickerDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;


import com.example.bluehousev3.R;
import com.example.bluehousev3.client.ClientVerification;
import com.example.bluehousev3.model.Client;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;

import java.util.Calendar;

import java.util.List;




public class Register extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {
    private EditText edtName;
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtRetypePass;
    private EditText edtMobileNum, edtRate;
    private ProgressBar progressBar;
    private String age;
    private EditText edtAddress, edtBirthdate;
    private Switch swAutoLocate;
    public static String userType;

    private FirebaseAuth mAuth;
    public static final String FIREBASE_URL = "https://blue-house-v3-default-rtdb.asia-southeast1.firebasedatabase.app";

    // static variables for the location tracking methods
    public static final long DEFAULT_UPDATE_INTERVAL = 1000 * 30;
    public static final long FAST_UPDATE_INTERVAL = 1000 * 5;
    public static final int PERMISSIONS_FINE_LOCATION = 99;

    private Spinner genderSpinner;
    ArrayAdapter<CharSequence> genderAdapter;

    private Spinner userTypeSpinner, educAttSpinner;
    ArrayAdapter<CharSequence> userTypeAdapter, educAttAdapter;



    private LocationRequest locationRequest = new LocationRequest();
    private LocationCallback locationCallback;
    private FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        new AlertDialog.Builder(this)
                .setTitle("Privacy Policy")
                .setMessage("BlueHouse is committed to protecting your privacy. This Privacy Policy explains how your personal information is collected, used, and disclosed by BlueHouse. This Privacy Policy applies to our application named BlueHouse , and its associated subdomains (collectively, our \"Service\"). By accessing or using our Service, you signify that you have read, understood, and agree to our collection, storage, use, and disclosure of your personal information as described in this Privacy Policy. \nBy clicking Agree, you agree to our Terms and that you have read our Privacy Policy")

                .setPositiveButton("Agree", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mAuth = FirebaseAuth.getInstance();

                        locationRequest.setInterval(DEFAULT_UPDATE_INTERVAL);
                        locationRequest.setFastestInterval(FAST_UPDATE_INTERVAL);
                        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

                        edtName = findViewById(R.id.edt_name);
                        edtRate = findViewById(R.id.edt_rate_register);
                        edtEmail = findViewById(R.id.edt_email_register);
                        edtPassword = findViewById(R.id.edt_password_register);
                        edtRetypePass = findViewById(R.id.edt_retypePass);
                        edtMobileNum = findViewById(R.id.edt_phoneNum);
                        Button btnRegister = findViewById(R.id.btn_register);
                        progressBar = findViewById(R.id.progressBar);
                        edtBirthdate = findViewById(R.id.edt_birthdate);
                        edtAddress = findViewById(R.id.edt_address);
                        swAutoLocate = findViewById(R.id.sw_autoLocate);

                        genderSpinner = findViewById(R.id.sp_gender);
                        genderAdapter = ArrayAdapter.createFromResource(Register.this, R.array.gender, android.R.layout.simple_spinner_item);
                        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        genderSpinner.setAdapter(genderAdapter);
                        genderSpinner.setOnItemSelectedListener(Register.this);

                        userTypeSpinner = findViewById(R.id.sp_usertype);
                        userTypeAdapter = ArrayAdapter.createFromResource(Register.this,R.array.user_type, android.R.layout.simple_spinner_item);
                        userTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        userTypeSpinner.setAdapter(userTypeAdapter);
                        userTypeSpinner.setOnItemSelectedListener(Register.this);

                        educAttSpinner = findViewById(R.id.sp_educ_att);
                        educAttAdapter = ArrayAdapter.createFromResource(Register.this,R.array.educ_attainment, android.R.layout.simple_spinner_item);
                        educAttAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        educAttSpinner.setAdapter(educAttAdapter);
                        educAttSpinner.setOnItemSelectedListener(Register.this);

                        locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(@NonNull LocationResult locationResult) {
                                super.onLocationResult(locationResult);
                                // save the location
                                updateUiValues(locationResult.getLastLocation());
                            }
                        };
                        edtBirthdate.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showDatePickerDialog(v);
                            }
                        });

                        btnRegister.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                registerUser();
                            }
                        });
                        swAutoLocate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView,
                                                         boolean isChecked) {
                                if (isChecked) {
                                    updateGps();
                                }
                            }
                        });
                    }
                })

                .setNegativeButton("Disagree", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Register.this, "You need to agree on our privacy policy in order to use this app", Toast.LENGTH_SHORT).show();
                        System.exit(0);
                    }
                })
                .setIcon(R.drawable.privacy_policy)
                .show();


    }

    public void registerUser() {
        String name = edtName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String retypePass = edtRetypePass.getText().toString().trim();
        String mobileNum = edtMobileNum.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();
        String birthDate = edtBirthdate.getText().toString().trim();
        String rate = edtRate.getText().toString();
        userType = userTypeSpinner.getSelectedItem().toString();

        if (name.isEmpty()) {
            edtName.setError("Full Name is required");
            edtName.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            edtEmail.setError("Email is required");
            edtEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            edtPassword.setError("Password is required");
            edtPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            edtPassword.setError("Password must contain at least 6 characters");
            edtPassword.requestFocus();
            return;
        }
        if (!password.equals(retypePass)) {
            edtRetypePass.setError("Password did not match");
            edtRetypePass.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("Please provide a valid email");
            edtEmail.requestFocus();
            return;
        }
        if (mobileNum.isEmpty()) {
            edtMobileNum.setError("Mobile number is required");
            edtMobileNum.requestFocus();
            return;
        }
        if (address.isEmpty()) {
            edtAddress.setError("Address is required");
            edtAddress.requestFocus();
            return;
        }
        if (birthDate.isEmpty()) {
            edtBirthdate.setError("Birthdate is required");
            edtBirthdate.requestFocus();
            return;
        }
        if (Integer.parseInt(age) < 18 ) {
            edtBirthdate.setError("you must be 18 and above to register");
            edtBirthdate.requestFocus();
        }



        // creates an account for the new user and put their credential on
        // the database
        if (Integer.parseInt(age) > 18) {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        Toast.makeText(Register.this, "Auth Success", Toast.LENGTH_SHORT).show();
                        if(userType.equals("Worker")) {
                            com.example.bluehousev3.model.Worker worker = new com.example.bluehousev3.model.Worker();
                            worker.setUserType("worker");
                            worker.setFullName(name);
                            worker.setAge(age);
                            worker.setRate(rate);
                            worker.setGender(getGender());
                            worker.setEmail(email);
                            worker.setPhoneNumber(mobileNum);
                            worker.setAddress(address);
                            worker.setBirthdate(birthDate);
                            worker.setRating("0");
                            worker.setStatus("not yet verified");
                            worker.setHighestEducationalAttainment(educAttSpinner.getSelectedItem().toString());

                            FirebaseDatabase.getInstance(FIREBASE_URL).getReference("users").child("usertype").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue("worker");

                            FirebaseDatabase.getInstance(FIREBASE_URL).getReference("users").child("workers")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(worker).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(Register.this, "Worker has" +
                                                " been registered successfully", Toast.LENGTH_SHORT).show();
                                        goToDesignatedActivity();

                                    } else {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(Register.this, "Worker " +
                                                "registration failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        } else if (userType.equals("Employer")) {
                            Client client = new Client();
                            client.setUserType("employer");
                            client.setFullName(name);
                            client.setAge(age);
                            client.setGender(getGender());
                            client.setEmail(email);
                            client.setPhoneNumber(mobileNum);
                            client.setAddress(address);
                            client.setBirthdate(birthDate);
                            client.setStatus("not yet verified");
                            client.setRating("0");

                            FirebaseDatabase.getInstance(FIREBASE_URL).getReference("users").child("usertype").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue("employer");
                            FirebaseDatabase.getInstance(FIREBASE_URL).getReference("users").child("employers")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(client).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(Register.this, "Client has been registered successfully", Toast.LENGTH_SHORT).show();
                                        goToDesignatedActivity();

                                    } else {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(Register.this, "Client registration failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(Register.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(Register.this, "Auth Failed", Toast.LENGTH_SHORT).show();
                        Toast.makeText(Register.this, "Invalid Email",
                                Toast.LENGTH_LONG).show();
                    }
                }


            });

        }

    }


    // methods for getting the user's address

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSIONS_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    updateGps();
                } else {
                    Toast.makeText(this, "this app needs permissions to be granted in " +
                        "order to work properly", Toast.LENGTH_LONG).show();
                    finish();
                }
        }
    }

    public void updateGps() {
        // get permission from the user to track gps
        // get the current location from the fused client
        // update the ui set all properties in their associated textview

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(Register.this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // user provided the permission

            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    // we got permissions. Put the values of location xxx into the
                    // UI components

                    updateUiValues(location);



                }
            });
        } else {
            // permission not granted yet
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_FINE_LOCATION);
            }
        }
    }

    private void updateUiValues(Location l ) {
        Geocoder geocoder = new Geocoder(Register.this);
        try {
            List<Address> addressList =
                geocoder.getFromLocation(l.getLatitude(),
                    l.getLongitude(), 1);
            edtAddress.setText(addressList.get(0).getAddressLine(0));
        } catch (Exception e) {
            Toast.makeText(this, "Unable to get location",
                Toast.LENGTH_LONG).show();
        }
    }
    public String getGender() {
        return genderSpinner.getSelectedItem().toString();
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    // method for getting the users date of birth and setting the view based
    // on the users input.
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String birthDate = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        edtBirthdate.setText(birthDate);

        // get users age from birthdate
        int currYear = Calendar.getInstance().get(Calendar.YEAR);
        int intAge = currYear - year;
        age = String.valueOf(intAge);



    }
    // Todo: find a way to remove this unused methods.
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void goToDesignatedActivity() {
        if (userType.equals("Worker")) {
            Intent intent = new Intent(Register.this, Services.class);
            startActivity(intent);
            finish();
        } else if (userType.equals("Employer")) {
            Intent clIntent = new Intent(Register.this, ClientVerification.class);
            startActivity(clIntent);
            finish();
        } else {
            Toast.makeText(Register.this, "userType is: " + userType,
                Toast.LENGTH_LONG).show();
        }

    }
}
