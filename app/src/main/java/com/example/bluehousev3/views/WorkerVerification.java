package com.example.bluehousev3.views;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bluehousev3.R;
import com.example.bluehousev3.controller.MainHome;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class WorkerVerification extends AppCompatActivity {
  private ImageButton ibSelfie, ibPolice, ibValidId1, ibValidId2, ibCert;
  private Button btnGeVerified;
  private TextView tvSkip;
  private  int imgCount = 0;
  private DatabaseReference mDatabase;
  private  FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
  private String uid = user.getUid();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_worker_verification);

    mDatabase = FirebaseDatabase.getInstance().getReference();

    ibSelfie = findViewById(R.id.ib_selfie);
    ibPolice = findViewById(R.id.ib_policeClear);
    ibValidId1 = findViewById(R.id.ib_validId1);
    ibValidId2 = findViewById(R.id.ib_validId2);
    ibCert = findViewById(R.id.ib_cert);
    tvSkip = findViewById(R.id.tv_skip);
    btnGeVerified = findViewById(R.id.btn_getVerified);

    // request camera permission
    if (ContextCompat.checkSelfPermission(WorkerVerification.this,
        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(WorkerVerification.this,
          new String[]{
              Manifest.permission.CAMERA
          }, 100);

    }

    tvSkip.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(WorkerVerification.this, MainHome.class);
        startActivity(intent);
      }
    });

    ibSelfie.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 100);
      }
    });

    ibPolice.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 20);
      }
    });

    ibValidId1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 10);
      }
    });

    ibValidId2.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 5);
      }
    });
    ibCert.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
      }
    });

    btnGeVerified.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (imgCount == 5) {
          Intent intent = new Intent(WorkerVerification.this, MainHome.class);
          startActivity(intent);
        } else {
          new AlertDialog.Builder(WorkerVerification.this)
              .setTitle("Incomplete Image Upload")
              .setMessage("Please Provide all the requirements being asked")

              // Specifying a listener allows you to take an action before dismissing the dialog.
              // The dialog is automatically dismissed when a dialog button is clicked.
              .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                  // Continue with delete operation
                }
              }).show();

              // A null listener allows the button to dismiss the dialog and take no further action.

        }
      }
    });

  }
  // todo: save the captured image to firebase
  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == 100) {
      Bitmap selfie = (Bitmap) data.getExtras().get("data");

      FirebaseStorage storage = FirebaseStorage.getInstance();
      StorageReference storageRef = storage.getReferenceFromUrl("gs://blue-house-v3.appspot.com");
      StorageReference selfiesRef = storageRef.child("selfies");
      StorageReference selfieImagesRef = storageRef.child("selfies.jpg");

      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      selfie.compress(Bitmap.CompressFormat.JPEG, 100, baos);
      byte[] bData = baos.toByteArray();

      UploadTask uploadTask = selfiesRef.putBytes(bData);
      uploadTask.addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception exception) {
          // Handle unsuccessful uploads
          Toast.makeText(WorkerVerification.this, "File Upload Failed",
              Toast.LENGTH_SHORT).show();
        }
      }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
          String meta = taskSnapshot.getMetadata().toString();

          Toast.makeText(WorkerVerification.this, "Upload Success" + meta,
              Toast.LENGTH_SHORT).show();
          imgCount ++;

        }
      });
    } else if (requestCode == 20) {
      Bitmap policeClearance = (Bitmap) data.getExtras().get("data");

      FirebaseStorage storage = FirebaseStorage.getInstance();
      StorageReference storageRef = storage.getReferenceFromUrl("gs://blue-house-v3.appspot.com");
      StorageReference policeRef = storageRef.child("policeClearance.jpg");
      StorageReference policeImagesRef = storageRef.child("images" +
          "/policeClearance" +
          ".jpg");

      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      policeClearance.compress(Bitmap.CompressFormat.JPEG, 100, baos);
      byte[] bData = baos.toByteArray();

      UploadTask uploadTask = policeRef.putBytes(bData);
      uploadTask.addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception exception) {
          // Handle unsuccessful uploads
          Toast.makeText(WorkerVerification.this, "File Upload Failed",
              Toast.LENGTH_SHORT).show();
        }
      }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
          // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
          // Uri downloadUrl = taskSnapshot.getDownloadUrl();



          Toast.makeText(WorkerVerification.this, "Upload Success",
              Toast.LENGTH_SHORT).show();
          imgCount++;

        }
      });
    } else if (requestCode == 10) {
      Bitmap validId1 = (Bitmap) data.getExtras().get("data");

      FirebaseStorage storage = FirebaseStorage.getInstance();
      StorageReference storageRef = storage.getReferenceFromUrl("gs://blue-house-v3.appspot.com");
      StorageReference validId1Ref = storageRef.child("validId1.jpg");
      StorageReference validId1ImagesRef = storageRef.child("images/validId1.jpg");

      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      validId1.compress(Bitmap.CompressFormat.JPEG, 100, baos);
      byte[] bData = baos.toByteArray();

      UploadTask uploadTask = validId1Ref.putBytes(bData);
      uploadTask.addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception exception) {
          // Handle unsuccessful uploads
          Toast.makeText(WorkerVerification.this, "File Upload Failed",
              Toast.LENGTH_SHORT).show();
        }
      }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
          // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
          // Uri downloadUrl = taskSnapshot.getDownloadUrl();

          Toast.makeText(WorkerVerification.this, "Upload Success",
              Toast.LENGTH_SHORT).show();
          imgCount++;

        }
      });
    } else if (requestCode == 5) {
      Bitmap validId2 = (Bitmap) data.getExtras().get("data");

      FirebaseStorage storage = FirebaseStorage.getInstance();
      StorageReference storageRef = storage.getReferenceFromUrl("gs://blue-house-v3.appspot.com");
      StorageReference validId2Ref = storageRef.child("validId2.jpg");
      StorageReference validId2ImagesRef = storageRef.child("images" +
          "/validId2" +
          ".jpg");

      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      validId2.compress(Bitmap.CompressFormat.JPEG, 100, baos);
      byte[] bData = baos.toByteArray();

      UploadTask uploadTask = validId2Ref.putBytes(bData);
      uploadTask.addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception exception) {
          // Handle unsuccessful uploads
          Toast.makeText(WorkerVerification.this, "File Upload Failed",
              Toast.LENGTH_SHORT).show();
        }
      }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
          // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
          // Uri downloadUrl = taskSnapshot.getDownloadUrl();

          Toast.makeText(WorkerVerification.this, "Upload Success",
              Toast.LENGTH_SHORT).show();
          imgCount++;

        }
      });
    } else if (requestCode == 1) {
      Bitmap cert = (Bitmap) data.getExtras().get("data");

      FirebaseStorage storage = FirebaseStorage.getInstance();
      StorageReference storageRef = storage.getReferenceFromUrl("gs://blue-house-v3.appspot.com");
      StorageReference certRef = storageRef.child("certificate.jpg");
      StorageReference validId2ImagesRef = storageRef.child("images" +
          "/validId2" +
          ".jpg");

      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      cert.compress(Bitmap.CompressFormat.JPEG, 100, baos);
      byte[] bData = baos.toByteArray();

      UploadTask uploadTask = certRef.putBytes(bData);
      uploadTask.addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception exception) {
          // Handle unsuccessful uploads
          Toast.makeText(WorkerVerification.this, "File Upload Failed",
              Toast.LENGTH_SHORT).show();
        }
      }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
          // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
          // Uri downloadUrl = taskSnapshot.getDownloadUrl();

          Toast.makeText(WorkerVerification.this, "Upload Success",
              Toast.LENGTH_SHORT).show();
          imgCount++;

        }
      });
    } else {
      Toast.makeText(WorkerVerification.this, "Error Uploading Images",
          Toast.LENGTH_SHORT).show();
    }

    // todo: set the captured selfie as the users profile picture
  }
}







