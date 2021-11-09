package com.example.bluehousev3.client.available_workers.transactions;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
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


public class RequestAService2 extends Fragment {
    private ImageView ivImg1, ivImg2;
    private EditText edtRate;
    private Button btnSubmit;
    private Spinner spRate;
    private String serviceType, description, startDate, endDate, startTime, endTime, location, workerId;
    private final StorageReference reference = FirebaseStorage.getInstance().getReference();
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private final String uid = user.getUid();
    private ArrayAdapter<CharSequence> rateAdapter;
    private  DatabaseReference serviceReqRef;
   // private DatabaseReference workerRef;
    private String selectedRate;
    private Uri img1, img2;
    private String imgUrl1, imgUrl2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request_a_service2, container, false);

        ivImg1 = view.findViewById(R.id.iv_img1);
        ivImg2 = view.findViewById(R.id.iv_img2);
        spRate = view.findViewById(R.id.sp_rate);
        edtRate = view.findViewById(R.id.edt_rate);
        btnSubmit = view.findViewById(R.id.btn_submit);

        assert getArguments() != null;
        serviceType = getArguments().getString("serviceType");
        description = getArguments().getString("description");
        startDate = getArguments().getString("startDate");
        endDate = getArguments().getString("endDate");
        startTime = getArguments().getString("startTime");
        endTime = getArguments().getString("endTime");
        location = getArguments().getString("location");
        workerId = getArguments().getString("ID");

        //workerRef = FirebaseDatabase.getInstance().getReference().child("users").child("serviceRequests").child(workerId).child(String.valueOf(System.currentTimeMillis()));

        serviceReqRef = FirebaseDatabase.getInstance().getReference().child("users").child("serviceRequests").child(uid).child(String.valueOf(System.currentTimeMillis()));

        Toast.makeText(getActivity(), "id: " + workerId, Toast.LENGTH_SHORT).show();

        rateAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.rate, android.R.layout.simple_spinner_item);
        rateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRate.setAdapter(rateAdapter);
        spRate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRate = spRate.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



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

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("submit")
                        .setMessage("Are you sure you want to submit service request?")

                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String rate = edtRate.getText().toString();

//                                workerRef.child("serviceType").setValue(serviceType);
//                                workerRef.child("description").setValue(description);
//                                workerRef.child("startDate").setValue(startDate);
//                                workerRef.child("endDate").setValue(endDate);
//                                workerRef.child("startTime").setValue(startTime);
//                                workerRef.child("endTime").setValue(endTime);
//                                workerRef.child("location").setValue(location);
//                                workerRef.child("proposedRate").setValue(rate);
//                                workerRef.child("proposedRateTime").setValue(selectedRate);
//                                workerRef.child("status").setValue("pending");
//                                workerRef.child("img1Url").setValue(imgUrl1);
//                                workerRef.child("img2Url").setValue(imgUrl2);
//                                workerRef.child("clientId").setValue(uid);
                                DatabaseReference workerRef = FirebaseDatabase.getInstance().getReference().child("users/workers").child(workerId);
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
                                serviceReqRef.child("workerId").setValue(workerId);
                                serviceReqRef.child("serviceType").setValue(serviceType);
                                serviceReqRef.child("description").setValue(description);
                                serviceReqRef.child("startDate").setValue(startDate);
                                serviceReqRef.child("endDate").setValue(endDate);
                                serviceReqRef.child("startTime").setValue(startTime);
                                serviceReqRef.child("endTime").setValue(endTime);
                                serviceReqRef.child("location").setValue(location);
                                serviceReqRef.child("proposedRate").setValue(rate);
                                serviceReqRef.child("status").setValue("pending");
                                serviceReqRef.child("img1Url").setValue(imgUrl1);
                                serviceReqRef.child("img2Url").setValue(imgUrl2);
                                serviceReqRef.child("proposedRateTime").setValue(selectedRate).addOnCompleteListener(new OnCompleteListener<Void>() {
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
                        .setIcon(android.R.drawable.ic_dialog_alert)
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












