package com.example.bluehousev3.client;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bluehousev3.R;
import com.example.bluehousev3.model.Client;
import com.example.bluehousev3.worker.WorkerHomePage;
import com.example.bluehousev3.worker.WorkerVerification;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ClientVerification extends AppCompatActivity {
    private Spinner spId1, spId2;
    private final String[] ids = new String[4];
    private ArrayAdapter<CharSequence> adap1, adap2;
    private ImageView ivId1, ivId2;
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private final StorageReference reference = FirebaseStorage.getInstance().getReference();
    private DatabaseReference mDatabase;
    private DatabaseReference empIdsRef;
    private final String uid;
    {
        assert user != null;
        uid = user.getUid();
    }
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_verification);

        spId1 = findViewById(R.id.sp_clientID1);
        spId2 = findViewById(R.id.sp_clientID2);
        ivId1 = findViewById(R.id.iv_clientID1);
        ivId2 = findViewById(R.id.iv_clientID2);
        Button btnSubmit = findViewById(R.id.btn_clientID_submit);
        progressBar = findViewById(R.id.progressBar4);

        adap1 = ArrayAdapter.createFromResource(this, R.array.ids, android.R.layout.simple_spinner_item);
        adap1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spId1.setAdapter(adap1);

        adap2 = ArrayAdapter.createFromResource(this, R.array.ids, android.R.layout.simple_spinner_item);
        adap2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spId2.setAdapter(adap1);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        empIdsRef = mDatabase.child("users/employerIds");

        if (ContextCompat.checkSelfPermission(ClientVerification.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ClientVerification.this,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    }, 200);
        }

        ivId1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 6);
            }
        });

        ivId2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 3);
            }
        });

        DatabaseReference curUserRef = empIdsRef.child(uid);

        curUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ids[0] = snapshot.child(spId1.getSelectedItem().toString()).getValue(String.class);
                ids[1] = snapshot.child(spId2.getSelectedItem().toString()).getValue(String.class);

                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (ids[0] == null || ids[1] == null) {
                            new AlertDialog.Builder(ClientVerification.this)
                                    .setTitle("Incomplete Image Upload")
                                    .setMessage("Please Provide all the requirements being asked")
                                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                                    }).show();
                        } else {
                            Intent intent = new Intent(ClientVerification.this, ClientHomePageActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Client client = new Client();
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == 6) {
                Uri ID1URI = data.getData();
                // create a storage reference and file name and file extension for the
                // image and uploads the image in firebase storage.
                final StorageReference fileRef =
                        reference.child(System.currentTimeMillis() + "." + getFileExtension(ID1URI));
                fileRef.putFile(ID1URI).addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    // gets the url of the uploaded image from the firebase and put
                    // it into the realtime database as a property
                    // of the user(worker) object

                    String id1 = uri.toString();
                    client.setValidId1(id1);
                    empIdsRef.child(uid).child(spId1.getSelectedItem().toString()).setValue(client.getValidId1());
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(ClientVerification.this, "ID Uploaded " +
                                    "Successfully",
                            Toast.LENGTH_SHORT).show();

                })).addOnProgressListener(snapshot -> progressBar.setVisibility(View.VISIBLE)).addOnFailureListener(e -> {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(ClientVerification.this, "ID Upload Failed!", Toast.LENGTH_SHORT).show();
                });
            }
            if (requestCode == 3) {
                Uri ID2URI = data.getData();
                // create a storage reference and file name and file extension for the
                // image and uploads the image in firebase storage.
                final StorageReference fileRef =
                        reference.child(System.currentTimeMillis() + "." + getFileExtension(ID2URI));
                fileRef.putFile(ID2URI).addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    // gets the url of the uploaded image from the firebase and put
                    // it into the realtime database as a property
                    // of the user(worker) object
                    String id2 = uri.toString();
                    client.setValidId1(id2);
                    empIdsRef.child(uid).child(spId2.getSelectedItem().toString()).setValue(client.getValidId1());
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(ClientVerification.this, "ID Uploaded " +
                                    "Successfully",
                            Toast.LENGTH_SHORT).show();

                })).addOnProgressListener(snapshot -> progressBar.setVisibility(View.VISIBLE)).addOnFailureListener(e -> {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(ClientVerification.this, "ID Upload Failed!", Toast.LENGTH_SHORT).show();
                });
            }
        }
    }
    private String getFileExtension(Uri mUri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }
}