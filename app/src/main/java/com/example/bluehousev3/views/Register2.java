package com.example.bluehousev3.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import com.example.bluehousev3.R;
import com.example.bluehousev3.model.Client;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.FirebaseDatabase;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class Register2 extends AppCompatActivity implements  AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {
  private FirebaseAuth mAuth;
  public static final String FIREBASE_URL = "https://blue-house-v3-default-rtdb.asia-southeast1.firebasedatabase.app";
  private ProgressBar progressBar;
  // static variables for the location tracking methods
  public static final long DEFAULT_UPDATE_INTERVAL = 1000 * 30;
  public static final long FAST_UPDATE_INTERVAL = 1000 * 5;
  public static final int PERMISSIONS_FINE_LOCATION = 99;
  private String userType;
  private String age;
  private TextInputEditText tietDob, tietAddress;
  private Switch swAutoLoc;
  private Spinner genderSpinner;
  ArrayAdapter<CharSequence> genderAdapter;
  private Spinner userTypeSpinner, educAttSpinner;
  ArrayAdapter<CharSequence> userTypeAdapter, educAttAdapter;
  private LocationRequest locationRequest = new LocationRequest();
  private LocationCallback locationCallback;
  private FusedLocationProviderClient fusedLocationProviderClient;
  private Button btnRegister2;
  private String name, email, password, mobileNumber;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register2);

     name = getIntent().getExtras().getString("userName");
     email = getIntent().getExtras().getString("userEmail");
     password = getIntent().getExtras().getString("userPassword");
     mobileNumber = getIntent().getExtras().getString("userMobileNum");
     progressBar = findViewById(R.id.progressBar5);
      progressBar.setVisibility(View.INVISIBLE);
      tietDob = findViewById(R.id.tiet_birthdate);
      tietAddress = findViewById(R.id.tiet_address);

      swAutoLoc = findViewById(R.id.sw_autoLocate);
      btnRegister2 = findViewById(R.id.btn_register2);

    mAuth = FirebaseAuth.getInstance();

    locationRequest.setInterval(DEFAULT_UPDATE_INTERVAL);
    locationRequest.setFastestInterval(FAST_UPDATE_INTERVAL);
    locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

    genderSpinner = findViewById(R.id.sp_gender);
    genderAdapter = ArrayAdapter.createFromResource(Register2.this, R.array.gender, android.R.layout.simple_spinner_item);
    genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    genderSpinner.setAdapter(genderAdapter);
    genderSpinner.setOnItemSelectedListener(Register2.this);

    userTypeSpinner = findViewById(R.id.sp_usertype);
    userTypeAdapter = ArrayAdapter.createFromResource(Register2.this,R.array.user_type, android.R.layout.simple_spinner_item);
    userTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    userTypeSpinner.setAdapter(userTypeAdapter);
    userTypeSpinner.setOnItemSelectedListener(Register2.this);

    educAttSpinner = findViewById(R.id.sp_educ_att);
    educAttAdapter = ArrayAdapter.createFromResource(Register2.this,R.array.educ_attainment, android.R.layout.simple_spinner_item);
    educAttAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    educAttSpinner.setAdapter(educAttAdapter);
    educAttSpinner.setOnItemSelectedListener(Register2.this);

    locationCallback = new LocationCallback() {
      @Override
      public void onLocationResult(@NonNull LocationResult locationResult) {
        super.onLocationResult(locationResult);
        // save the location
        updateUiValues(locationResult.getLastLocation());
      }
    };

    tietDob.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        showDatePickerDialog(v);
      }
    });

    btnRegister2.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        registerUser();
      }
    });
    swAutoLoc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView,
                                   boolean isChecked) {
        if (isChecked) {
          updateGps();
        }
      }
    });


  }

  public void registerUser() {
    String address = tietAddress.getText().toString().trim();
    String birthDate = tietDob.getText().toString().trim();
    userType = userTypeSpinner.getSelectedItem().toString();

    if (address.isEmpty()) {
      tietAddress.setError("Address is required");
      tietAddress.requestFocus();
      return;
    }
    if (birthDate.isEmpty()) {
      tietDob.setError("Birthdate is required");
      tietDob.requestFocus();
      return;
    }
    if (Integer.parseInt(age) < 18 ) {
      tietDob.setError("");
      Toast.makeText(Register2.this, "Your age must be 18 and above in order to use this app", Toast.LENGTH_SHORT).show();
      tietDob.requestFocus();
      return;
    }


    // creates an account for the new user and put their credential on
    // the database
    if (Integer.parseInt(age) > 18) {
      progressBar.setVisibility(View.VISIBLE);
      mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
          if(task.isSuccessful()) {
            Toast.makeText(Register2.this, "Auth Success", Toast.LENGTH_SHORT).show();

            if(userType.equals("Worker")) {
              com.example.bluehousev3.model.Worker worker = new com.example.bluehousev3.model.Worker();
              worker.setUserType("worker");
              worker.setFullName(name);
              worker.setAge(age);
              worker.setGender(getGender());
              worker.setEmail(email);
              worker.setPhoneNumber(mobileNumber);
              worker.setAddress(address);
              worker.setBirthdate(birthDate);
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
                    String uid = FirebaseAuth.getInstance().getUid();
                    getUserToken(uid);
                    Toast.makeText(Register2.this, "Worker has" +
                       " been registered successfully", Toast.LENGTH_SHORT).show();
                    goToDesignatedActivity();

                  } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Register2.this, "Worker " +
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
              client.setPhoneNumber(mobileNumber);
              client.setAddress(address);
              client.setBirthdate(birthDate);
              client.setStatus("Not yet verified");


              FirebaseDatabase.getInstance(FIREBASE_URL).getReference("users").child("usertype").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue("employer");
              FirebaseDatabase.getInstance(FIREBASE_URL).getReference("users").child("employers")
                 .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                 .setValue(client).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                  if (task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    String uid = FirebaseAuth.getInstance().getUid();
                    getUserToken(uid);
                    Toast.makeText(Register2.this, "Client has been registered successfully", Toast.LENGTH_SHORT).show();
                    goToDesignatedActivity();

                  } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Register2.this, "Client registration failed", Toast.LENGTH_SHORT).show();
                  }
                }
              });
            } else {
              Toast.makeText(Register2.this, "Registration Failed", Toast.LENGTH_SHORT).show();
            }
          } else {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(Register2.this, "Auth Failed", Toast.LENGTH_SHORT).show();
            Toast.makeText(Register2.this, "Invalid Email",
               Toast.LENGTH_LONG).show();
          }
        }


      });

    }

  }

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
       LocationServices.getFusedLocationProviderClient(Register2.this);

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
      requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_FINE_LOCATION);
    }
  }

  private void updateUiValues(Location l ) {
    Geocoder geocoder = new Geocoder(Register2.this);
    try {
      List<Address> addressList =
         geocoder.getFromLocation(l.getLatitude(),
            l.getLongitude(), 1);
      tietAddress.setText(addressList.get(0).getAddressLine(0));
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

    String birthDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(c.getTime());
    tietDob.setText(birthDate);

    // get users age from birthdate
    int currYear = Calendar.getInstance().get(Calendar.YEAR);
    int intAge = currYear - year;
    age = String.valueOf(intAge);



  }

  private void goToDesignatedActivity() {
    if (userType.equals("Worker")) {
      Intent intent = new Intent(Register2.this, Services.class);
      startActivity(intent);
      finish();
    } else if (userType.equals("Employer")) {
      Intent clIntent = new Intent(Register2.this, RegisterIds.class);
      clIntent.putExtra("userTypeRegister", userType);
      startActivity(clIntent);
      finish();
    } else {
      Toast.makeText(Register2.this, "userType is: " + userType, Toast.LENGTH_LONG).show();
    }

  }


  @Override
  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

  }

  @Override
  public void onNothingSelected(AdapterView<?> parent) {

  }

  private void getUserToken(String uid) {
    FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    mUser.getIdToken(true)
       .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
         public void onComplete(@NonNull Task<GetTokenResult> task) {
           if (task.isSuccessful()) {
             String idToken = task.getResult().getToken();
             FirebaseDatabase.getInstance().getReference().child("userTokens").child(uid).setValue(idToken);
           } else {
             task.getException();
           }
         }
       });
  }
}