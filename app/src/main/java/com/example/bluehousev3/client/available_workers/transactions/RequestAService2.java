package com.example.bluehousev3.client.available_workers.transactions;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.bluehousev3.R;
import com.example.bluehousev3.client.Home;


import com.google.android.gms.tasks.OnCompleteListener;
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
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class RequestAService2 extends Fragment {
    private ImageView ivImg1, ivImg2;
    private Button btnSubmit;
    private String serviceType, description, startDate, startTime, location, workerId;
    private final StorageReference reference = FirebaseStorage.getInstance().getReference();
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private final String uid = user.getUid();
    private  DatabaseReference serviceReqRef;
    private Uri img1, img2;
    private String imgUrl1, imgUrl2, month;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request_a_service2, container, false);

        ivImg1 = view.findViewById(R.id.iv_img1);
        ivImg2 = view.findViewById(R.id.iv_img2);

        btnSubmit = view.findViewById(R.id.btn_submit);

        serviceType = getArguments().getString("serviceType");
        description = getArguments().getString("description");
        startDate = getArguments().getString("startDate");
        startTime = getArguments().getString("startTime");
        location = getArguments().getString("location");
        workerId = getArguments().getString("ID");


        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int monthInt = localDate.getMonthValue();

        switch (monthInt) {
            case 1:
                month = "January";
                break;
            case 2:
                month = "February";
                break;
            case 3:
                month = "March";
                break;
            case 4:
                month = "April";
                break;
            case 5:
                month = "May";
                break;
            case 6:
                month = "June";
                break;
            case 7:
                month = "July";
                break;
            case 8:
                month = "August";
                break;
            case 9:
                month = "September";
                break;
            case 10:
                month = "October";
                break;
            case 11:
                month = "November";
                break;
            case 12:
                month = "December";
                break;
            default:
                month = "empty";
        }

        serviceReqRef = FirebaseDatabase.getInstance().getReference().child("users").child("serviceRequests").child(uid).child(String.valueOf(System.currentTimeMillis()));
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    }, 200);
        }


        ivImg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);

            }
        });

        ivImg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 2);
            }
        });
        DatabaseReference workerRef = FirebaseDatabase.getInstance().getReference().child("users/workers").child(workerId);
        DatabaseReference pReference = FirebaseDatabase.getInstance().getReference();


        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Submit")
                        .setMessage("Are you sure you want to submit service request?")

                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                workerRef.addValueEventListener(new ValueEventListener() {
                                     @Override
                                     public void onDataChange(@NonNull DataSnapshot snapshot) {
                                         serviceReqRef.child("workerName").setValue(snapshot.child("fullName").getValue(String.class));
                                         serviceReqRef.child("workerAddress").setValue(snapshot.child("address").getValue(String.class));
                                     }

                                     @Override
                                     public void onCancelled(@NonNull DatabaseError error) {

                                     }
                                 }) ;

                                pReference.child("popularServices").child(month).child(String.valueOf(System.currentTimeMillis())).setValue(serviceType);
                                serviceReqRef.child("workerId").setValue(workerId);
                                serviceReqRef.child("serviceType").setValue(serviceType);
                                serviceReqRef.child("description").setValue(description);
                                serviceReqRef.child("startDate").setValue(startDate);
                                serviceReqRef.child("startTime").setValue(startTime);
                                serviceReqRef.child("location").setValue(location);
                                serviceReqRef.child("status").setValue("pending");
                                serviceReqRef.child("img1Url").setValue(imgUrl1);
                                serviceReqRef.child("img2Url").setValue(imgUrl2).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Fragment home = new Home();
                                        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                                        ft.replace(R.id.fragment_container_client, home);
                                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                        ft.addToBackStack(null);
                                        ft.commit();
                                        Toast.makeText(getActivity(), "successfully added service request", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })

                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(R.drawable.ic_alert)
                        .show();
            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                img1 = data.getData();
                final StorageReference fileRef = reference.child(System.currentTimeMillis() + ".jpg");
                fileRef.putFile(img1).addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    imgUrl1 = uri.toString();
                    Picasso.get()
                            .load(img1)
                            .into(ivImg1);
                }));
                }
            }
        if (requestCode == 2) {
            img2 = data.getData();
                final StorageReference fileRef = reference.child(System.currentTimeMillis() + ".jpg");
                fileRef.putFile(img2).addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                imgUrl2 = uri.toString();
                Picasso.get()
                        .load(img2)
                        .into(ivImg2);
                }));

                }

            }
        }












