package com.example.bluehousev3.client.available_workers.transactions;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bluehousev3.R;
import com.example.bluehousev3.model.Worker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;


public class WorkerProfile extends Fragment {
    private TextView tvName, tvAge, tvRating, tvNumberOfServicesComp, tvEducAtt, tvEmail, tvPhoneNum, tvHourlyRate, tvGender, tvAddress;
    private Button btnReviews, btnCert, btnIds, btnRequestService;
    private ImageView ivWorkerProfilePic;
    private Bundle bundle;
    private String workerId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_worker_profile, container, false);

        tvName = view.findViewById(R.id.tv_workerProfile_workerName);
        tvAge = view.findViewById(R.id.tv_workerProfile_workerAge);
        tvRating = view.findViewById(R.id.tv_workerProfile_rating);
        tvNumberOfServicesComp = view.findViewById(R.id.tv_workerProfile_numberOfServicesCompleted);
        tvEducAtt = view.findViewById(R.id.tv_workerProfile_educationalAtt);
        tvEmail = view.findViewById(R.id.tv_workerProfile_email);
        tvPhoneNum = view.findViewById(R.id.tv_workerProfile_mobile);
        btnReviews = view.findViewById(R.id.btn_reviews);
        btnCert = view.findViewById(R.id.btn_certs);
        btnIds = view.findViewById(R.id.btn_ids);
        btnRequestService = view.findViewById(R.id.btn_requestService);
        tvHourlyRate = view.findViewById(R.id.tv_workerProfile_hourlyRate);
        tvGender = view.findViewById(R.id.tv_workerProfile_gender);
        ivWorkerProfilePic = view.findViewById(R.id.iv_workerProfile_profilePic);
        tvAddress = view.findViewById(R.id.tv_workerProfile_address);

        bundle = new Bundle();


        workerId = getArguments().getString("workerId");
        bundle.putString("Wid" , workerId);
        Worker worker = new Worker();
        DatabaseReference workerRef = FirebaseDatabase.getInstance().getReference().child("users/workers").child(workerId);
        workerRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tvName.setText("Name: " + snapshot.child("fullName").getValue(String.class));

                String age = snapshot.child("age").getValue(String.class);
                tvAge.setText("Age: " + age);
                tvPhoneNum.setText("Mobile Number: " + snapshot.child("phoneNumber").getValue(String.class));
                String rate = snapshot.child("rate").getValue(String.class);
                tvHourlyRate.setText("Rate: " + rate);
                tvEmail.setText("Email: " + snapshot.child("email").getValue(String.class));
                tvGender.setText("Gender: " + snapshot.child("gender").getValue(String.class));
                tvAddress.setText("Address: " + snapshot.child("address").getValue(String.class));

                String rating = snapshot.child("rating").getValue(String.class);
                String educ = snapshot.child("educationalAttainment").getValue(String.class);
                String numComp = snapshot.child("numberOfServicesCompleted").getValue(String.class);

                if (rating == null) {
                    tvRating.setText("Rating: Not yet rated");
                } else {
                    tvRating.setText("Rating: " + rating);
                }

                if (educ == null) {
                    tvEducAtt.setText("Educational Attainment: Not Applicable");
                } else {
                    tvEducAtt.setText("Educational Attainment: " + educ);
                }

                if (numComp == null) {
                    tvNumberOfServicesComp.setText("Number of Services Completed: 0");
                } else {
                    tvNumberOfServicesComp.setText("Number of Services Completed: "+ numComp);
                }

                DatabaseReference picRef = FirebaseDatabase.getInstance().getReference().child("users/workerIds").child(workerId);
                picRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String selfie = snapshot.child("SelfieUrl").getValue(String.class);

                        if (selfie == null) {
                            Toast.makeText(getActivity(), "No Available Image Url", Toast.LENGTH_SHORT).show();
                        } else {
                            Picasso.get()
                                    .load(selfie)
                                    .into(ivWorkerProfilePic);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Database Error", "onCancelled: Error fetching data from database");
            }
        });



        btnReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkerReviewsDialog workerReviewsDialog = new WorkerReviewsDialog(workerId);
                workerReviewsDialog.show(getParentFragmentManager(), "Worker Reviews");
            }
        });

        btnCert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkerCertificateDialog workerCertificateDialog = new WorkerCertificateDialog(workerId);
                workerCertificateDialog.show(getParentFragmentManager(), "Worker Certificate");
            }
        });

        btnIds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkerIdsDialogFragment workerIdsDialogFragment = new WorkerIdsDialogFragment(workerId);
                workerIdsDialogFragment.show(getParentFragmentManager(), "Worker Valid Ids");
            }
        });

        btnRequestService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment requestAService = new RequestAService();
                requestAService.setArguments(bundle);
                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container_client, requestAService);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        return view;
    }
}