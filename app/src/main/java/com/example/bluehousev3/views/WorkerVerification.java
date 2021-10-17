/*
this class asks for the users(worker) selfie, police clearance, 2 valid ids
images and uploads them into Firebase Storage and put the image urls into the
user's realtime database
 */
package com.example.bluehousev3.views;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.bluehousev3.R;
import com.example.bluehousev3.model.Worker;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;
import java.util.Objects;

public class WorkerVerification extends AppCompatActivity {
  private  int imgCount = 0;
  private DatabaseReference mDatabase;
  private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
  private final String uid;
  {
    assert user != null;
    uid = user.getUid();
  }

  private ProgressBar progressBar;

  private final StorageReference reference =
      FirebaseStorage.getInstance().getReference();


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_worker_verification);

    mDatabase = FirebaseDatabase.getInstance().getReference();

    ImageButton ibSelfie = findViewById(R.id.ib_selfie);
    ImageButton ibPolice = findViewById(R.id.ib_policeClear);
    ImageButton ibValidId1 = findViewById(R.id.ib_validId1);
    ImageButton ibValidId2 = findViewById(R.id.ib_validId2);
    ImageButton ibCert = findViewById(R.id.ib_cert);
    Button btnGeVerified = findViewById(R.id.btn_getVerified);
    progressBar = findViewById(R.id.progressBar3);
    progressBar.setVisibility(View.INVISIBLE);

    // request camera permission
    if (ContextCompat.checkSelfPermission(WorkerVerification.this,
        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(WorkerVerification.this,
          new String[]{
              Manifest.permission.CAMERA
          }, 200);
    }

    ibSelfie.setOnClickListener(v -> {
      Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
      startActivityForResult(intent, 100);
    });

    ibPolice.setOnClickListener(v -> {
      Intent intent = new Intent();
      intent.setAction(Intent.ACTION_GET_CONTENT);
      intent.setType("image/*");
      startActivityForResult(intent, 20);
    });

    ibValidId1.setOnClickListener(v -> {
      Intent intent = new Intent();
      intent.setAction(Intent.ACTION_GET_CONTENT);
      intent.setType("image/*");
      startActivityForResult(intent, 10);
    });

    ibValidId2.setOnClickListener(v -> {
      Intent intent = new Intent();
      intent.setAction(Intent.ACTION_GET_CONTENT);
      intent.setType("image/*");
      startActivityForResult(intent, 5);
    });
    ibCert.setOnClickListener(v -> {
      Intent intent = new Intent();
      intent.setAction(Intent.ACTION_GET_CONTENT);
      intent.setType("image/*");
      startActivityForResult(intent, 1);
    });

    btnGeVerified.setOnClickListener(v -> {
      // condition if the images provided by the worker is complete
      if (imgCount == 5) {
        Intent intent = new Intent(WorkerVerification.this, WorkerHomePage.class);
        startActivity(intent);
        finish();
      } else {
        new AlertDialog.Builder(WorkerVerification.this)
            .setTitle("Incomplete Image Upload")
            .setMessage("Please Provide all the requirements being asked")
            .setPositiveButton(android.R.string.yes, (dialog, which) -> {
            }).show();

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
        uploadTask.addOnFailureListener(exception -> Toast.makeText(WorkerVerification.this, "Selfie Image Upload " +
                "Failed",
            Toast.LENGTH_SHORT).show()).addOnSuccessListener(taskSnapshot -> {
          Toast.makeText(WorkerVerification.this, "Selfie Image Upload " +
                  "Success",
              Toast.LENGTH_SHORT).show();

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
              Worker worker = new Worker();
              String selfie1 = downloadUri.toString();
              worker.setSelfieUrl(selfie1);
              mDatabase.child("users").child(uid).child("SelfieUrl").setValue(worker.getSelfieUrl());
              progressBar.setVisibility(View.INVISIBLE);
              imgCount++;
            } else {
              Toast.makeText(WorkerVerification.this, "Error uploading " +
                      "selfie Image to database",
                  Toast.LENGTH_SHORT).show();
            }
          });
        });
      }

      if (requestCode == 20) {
        //gets the uri of the image
        Uri policeUri = data.getData();
        // create a storage reference and file name and file extension for the
        // image and uploads the image in firebase storage.
        final StorageReference fileRef =
            reference.child(System.currentTimeMillis() + "." + getFileExtension(policeUri));
        fileRef.putFile(policeUri).addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
          // gets the url of the uploaded image from the firebase and put
          // it into the realtime database as a property
          // of the user(worker) object
          Worker worker = new Worker();
          String pClearance = uri.toString();
          worker.setPoliceClearance(pClearance);
          mDatabase.child("users").child(uid).child("PoliceClearanceUrl").setValue(worker.getPoliceClearance());
          progressBar.setVisibility(View.INVISIBLE);
          Toast.makeText(WorkerVerification.this, "Police " +
                  "Clearance Image Uploaded " +
                  "Successfully",
              Toast.LENGTH_SHORT).show();
          imgCount++;
        })).addOnProgressListener(snapshot -> progressBar.setVisibility(View.VISIBLE)).addOnFailureListener(e -> {
          progressBar.setVisibility(View.INVISIBLE);
          Toast.makeText(WorkerVerification.this, "Police " +
              "Clearance Image Upload Failed!", Toast.LENGTH_SHORT).show();
        });
      }

      if (requestCode == 10) {
        Uri validId1Uri = data.getData();
        final StorageReference fileRef =
            reference.child(System.currentTimeMillis() + "." + getFileExtension(validId1Uri));
        fileRef.putFile(validId1Uri).addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
          Worker worker = new Worker();
          String valid1 = uri.toString();
          worker.setValidId1(valid1);
          mDatabase.child("users").child(uid).child("ValidId1Url").setValue(worker.getValidId1());
          progressBar.setVisibility(View.INVISIBLE);
          Toast.makeText(WorkerVerification.this, "Valid ID 1 " +
                  "Image Uploaded " +
                  "Successfully",
              Toast.LENGTH_SHORT).show();
          imgCount++;
        })).addOnProgressListener(snapshot -> progressBar.setVisibility(View.VISIBLE)).addOnFailureListener(e -> {
          progressBar.setVisibility(View.INVISIBLE);
          Toast.makeText(WorkerVerification.this, "Valid ID 1 Image Upload " +
                  "Failed!",
              Toast.LENGTH_SHORT).show();
        });
      }

      if (requestCode == 5) {
        Uri validId2Uri = data.getData();
        final StorageReference fileRef =
            reference.child(System.currentTimeMillis() + "." + getFileExtension(validId2Uri));
        fileRef.putFile(validId2Uri).addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
          Worker worker = new Worker();
          String valid2 = uri.toString();
          worker.setValidId2(valid2);
          mDatabase.child("users").child(uid).child("ValidId2Url").setValue(worker.getValidId2());
          progressBar.setVisibility(View.INVISIBLE);
          Toast.makeText(WorkerVerification.this, "Valid ID " +
                  "Image Uploaded " +
                  "Successfully",
              Toast.LENGTH_SHORT).show();
          imgCount++;
        })).addOnProgressListener(snapshot -> progressBar.setVisibility(View.VISIBLE)).addOnFailureListener(e -> {
          progressBar.setVisibility(View.INVISIBLE);
          Toast.makeText(WorkerVerification.this, "Valid ID 2 Image Upload " +
                  "Failed!",
              Toast.LENGTH_SHORT).show();
        });
      }
      }

    if (requestCode == 1) {
      assert data != null;
      Uri certUri = data.getData();
        final StorageReference fileRef =
            reference.child(System.currentTimeMillis() + "." + getFileExtension(certUri));
        fileRef.putFile(certUri).addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
          Worker worker = new Worker();
          String cert = uri.toString();
          worker.setCert(cert);
          mDatabase.child("users").child(uid).child("CertificateUrl").setValue(worker.getCert());
          progressBar.setVisibility(View.INVISIBLE);
          Toast.makeText(WorkerVerification.this, "Certificate " +
                  "Image Uploaded Successfully",
              Toast.LENGTH_SHORT).show();
          imgCount++;
        })).addOnProgressListener(snapshot -> progressBar.setVisibility(View.VISIBLE)).addOnFailureListener(e -> {
          progressBar.setVisibility(View.INVISIBLE);
          Toast.makeText(WorkerVerification.this, "Certificate " +
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
}









