/*
this class asks for the users(worker) selfie, police clearance, 2 valid ids
images and uploads them into Firebase Storage and put the image urls into the
user's realtime database
 */
package com.example.bluehousev3.views;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.bluehousev3.R;
import com.example.bluehousev3.client.ClientHomePageActivity;
import com.example.bluehousev3.worker.WorkerHomePage;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

public class RegisterIds extends AppCompatActivity {
  private DatabaseReference mDatabase;
  private DatabaseReference idsRef;
  private Spinner sid1;
  private ArrayAdapter<CharSequence> adap1;
  private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
  private final String uid;
  private final String[] ids = new String[3];
  private String userType;

  private ImageView ibSelfie, ibValidId1, ibCert;

  {
    assert user != null;
    uid = user.getUid();
  }

  private ProgressBar progressBar;

  private final StorageReference reference = FirebaseStorage.getInstance().getReference();


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_worker_verification);
    mDatabase = FirebaseDatabase.getInstance().getReference();

    userType = getIntent().getExtras().getString("userTypeRegister");
        if (userType.equals("Employer")) {
          idsRef = mDatabase.child("users/employerIds");
        } else {
          idsRef = mDatabase.child("users/workerIds");
        }
    sid1 = findViewById(R.id.sp_valid1);

    adap1 = ArrayAdapter.createFromResource(this, R.array.ids, android.R.layout.simple_spinner_item);
    adap1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    sid1.setAdapter(adap1);


     ibSelfie = findViewById(R.id.ib_selfie);
     ibValidId1 = findViewById(R.id.ib_validId1);
     ibCert = findViewById(R.id.ib_cert);
    Button btnGeVerified = findViewById(R.id.btn_getVerified);
    progressBar = findViewById(R.id.progressBar3);
    progressBar.setVisibility(View.INVISIBLE);

    // request camera permission
    if (ContextCompat.checkSelfPermission(RegisterIds.this,
            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(RegisterIds.this,
              new String[]{
                      Manifest.permission.CAMERA
              }, 200);
    }

    ibSelfie.setOnClickListener(v -> {
      Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
      startActivityForResult(intent, 100);
    });



    ibValidId1.setOnClickListener(v -> {
      Intent intent = new Intent();
      intent.setAction(Intent.ACTION_GET_CONTENT);
      intent.setType("image/*");
      startActivityForResult(intent, 10);
    });


    ibCert.setOnClickListener(v -> {
      Intent intent = new Intent();
      intent.setAction(Intent.ACTION_GET_CONTENT);
      intent.setType("image/*");
      startActivityForResult(intent, 1);
    });
    // condition if the images provided by the worker is complete

    DatabaseReference curUserRef = idsRef.child(uid);

    curUserRef.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        ids[0] = snapshot.child("SelfieUrl").getValue(String.class);
        ids[1] = snapshot.child("ValidIdUrl").getValue(String.class);


        btnGeVerified.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            new AlertDialog.Builder(RegisterIds.this)
                .setTitle("Privacy Policy")
                .setMessage("BlueHouse collects user information to verify if a user can register based on Philippine Labor laws (RA7610 & RA9231). BlueHouse is committed in following ethical practices, protecting the personal information of all users, and will not rent, barter, sell, permit, or give away to anyone unaffiliated with BlueHouse to use their data. \n" +
                        "\n" +
                        "This Privacy Policy explains how your personal information is collected, used, and disclosed by BlueHouse. This Privacy Policy applies to our application named BlueHouse. \n" +
                        "\n" +
                        "By accessing or using our Service, you signify that you agree to our collection, and storage of your personal information.\n" +
                        "\n" +
                        "By clicking Agree, you agree to our Terms and that you have read our Privacy Policy.")
                .setPositiveButton("Agree", new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface dialog, int which) {
                    if (ids[0] == null || ids[1] == null) {
                      new AlertDialog.Builder(RegisterIds.this)
                              .setTitle("Incomplete Image Upload")
                              .setMessage("Please Provide all the requirements being asked")
                              .setPositiveButton(android.R.string.yes, (dialog1, which1) -> {
                              }).show();
                    } else {
                      if (userType.equals("Employer")) {
                        Toast.makeText(RegisterIds.this, "Registration Success", Toast.LENGTH_SHORT).show();
                        Intent empIntent = new Intent(RegisterIds.this, ClientHomePageActivity.class);
                        startActivity(empIntent);
                        finish();
                      } else {
                        Toast.makeText(RegisterIds.this, "Registration Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterIds.this, WorkerHomePage.class);
                        startActivity(intent);
                        finish();
                      }
                    }
                  }
                })
                .setNegativeButton("Disagree", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(RegisterIds.this, "You need to agree on our privacy policy in order to use this app", Toast.LENGTH_LONG).show();
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(RegisterIds.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                  }
                })
                .setIcon(R.drawable.privacy_policy)
                .show();
          }
        });

      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {

      }
    });
  }

  // uploads images of firebase and sets the url of the images as a property
  // of user object in realtime database for later retrieval
  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK && data != null) {
      if (requestCode == 100) {
        // sets the file name of the image in firebase
        StorageReference ref = reference.child(System.currentTimeMillis() +
            ".jpg");

        Bitmap selfie = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        selfie.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bData = baos.toByteArray();

        //uploads the image into firebase storage
        UploadTask uploadTask =
            ref.putBytes(bData);
        uploadTask.addOnFailureListener(exception -> Toast.makeText(RegisterIds.this, "Selfie Image Upload " +
                "Failed",
            Toast.LENGTH_SHORT).show()).addOnSuccessListener(taskSnapshot -> {




          // gets the url of the uploaded image from the firebase and put
          // it into the realtime database as a property of the user
          // (worker) object
          Task<Uri> urlTask = uploadTask.continueWithTask(task -> {
            if (!task.isSuccessful()) {
              throw Objects.requireNonNull(task.getException());
            }
            // Continue with the task to get the download URL
            return ref.getDownloadUrl();
          }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
              Uri downloadUri = task.getResult();
              String selfie1 = downloadUri.toString();
              idsRef.child(uid).child("SelfieUrl").setValue(selfie1);
              progressBar.setVisibility(View.INVISIBLE);
              Picasso.get().load(selfie1).into(ibSelfie);
              new AlertDialog.Builder(this)
                 .setTitle("Photo has been successfully uploaded.")
                 .setPositiveButton(android.R.string.yes, null)
                 .setIcon(R.drawable.ic_check)
                 .show();

            } else {
              Toast.makeText(RegisterIds.this, "Error uploading " +
                      "selfie Image to database",
                  Toast.LENGTH_SHORT).show();
            }
          });
        });
      }


      if (requestCode == 10) {
        Uri validId1Uri = data.getData();
        final StorageReference fileRef =
            reference.child(System.currentTimeMillis() + "." + getFileExtension(validId1Uri));
        fileRef.putFile(validId1Uri).addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
          String valid1 = uri.toString();
          idsRef.child(uid).child("ValidIdUrl").setValue(valid1);
          progressBar.setVisibility(View.INVISIBLE);

          Picasso.get().load(valid1).into(ibValidId1);
          new AlertDialog.Builder(this)
             .setTitle(sid1.getSelectedItem().toString() +  " ID has been successfully uploaded.")
             .setPositiveButton(android.R.string.yes, null)
             .setIcon(R.drawable.ic_check)
             .show();

        })).addOnProgressListener(snapshot -> progressBar.setVisibility(View.VISIBLE)).addOnFailureListener(e -> {
          progressBar.setVisibility(View.INVISIBLE);
          Toast.makeText(RegisterIds.this, "Valid ID 1 Image Upload " +
                  "Failed!",
              Toast.LENGTH_SHORT).show();
        });
      }
      }

    if (requestCode == 1) {

      Uri certUri = data.getData();
        final StorageReference fileRef =
            reference.child(System.currentTimeMillis() + "." + getFileExtension(certUri));
        fileRef.putFile(certUri).addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl().addOnSuccessListener(uri -> {

          String cert = uri.toString();
          idsRef.child(uid).child("CertificateUrl").setValue(cert);
          progressBar.setVisibility(View.INVISIBLE);
          Picasso.get().load(cert).into(ibCert);
          new AlertDialog.Builder(this)
             .setTitle("Certificate has been successfully uploaded.")
             .setPositiveButton(android.R.string.yes, null)
             .setIcon(R.drawable.ic_check)
             .show();

        })).addOnProgressListener(snapshot -> progressBar.setVisibility(View.VISIBLE)).addOnFailureListener(e -> {
          progressBar.setVisibility(View.INVISIBLE);
          Toast.makeText(RegisterIds.this, "Certificate " +
                  "Image Upload Failed!",
              Toast.LENGTH_SHORT).show();
        });
      }
  }

  private String getFileExtension(Uri mUri){
    ContentResolver cr = getContentResolver();
    MimeTypeMap mime = MimeTypeMap.getSingleton();
    return mime.getExtensionFromMimeType(cr.getType(mUri));
  }
  @Override
  public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                         int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    switch (requestCode) {
      case 200:
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        } else {
          Toast.makeText(this, "The app needs your permission to access your gallery, registration failed.", Toast.LENGTH_SHORT).show();
          FirebaseAuth.getInstance().signOut();
          Intent intent = new Intent(this, MainActivity.class);
          startActivity(intent);
          finish();
        }
        return;
    }

  }
}










