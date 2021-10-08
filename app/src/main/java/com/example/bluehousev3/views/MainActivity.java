package com.example.bluehousev3.views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bluehousev3.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
   private Button getStarted;

  private FirebaseAuth mAuth;


    // todo: make this activity a splash screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        getStarted = findViewById(R.id.btn_getStarted);
        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogin();
            }
        });
    }

    private void goToLogin() {
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
    }



        @Override
        protected void onPostResume () {
            super.onPostResume();

            int errorCode =
                GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);

            if (errorCode != ConnectionResult.SUCCESS) {
                Dialog errorDialog = GoogleApiAvailability.getInstance().getErrorDialog(this,
                    errorCode, errorCode, new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            Toast.makeText(MainActivity.this, "google play " +
                                    "services not available",
                                Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });
                errorDialog.show();
            } else {
                Toast.makeText(MainActivity.this, "google play services available",
                    Toast.LENGTH_LONG).show();
            }
        }


}

