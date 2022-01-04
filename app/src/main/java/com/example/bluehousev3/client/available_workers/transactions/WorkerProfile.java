package com.example.bluehousev3.client.available_workers.transactions;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.ContactsContract;
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
import com.squareup.picasso.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;


public class WorkerProfile extends Fragment {
    private TextView tvEducAtt,tvName, tvAge, tvRating, tvEmail, tvPhoneNum, tvHourlyRate, tvGender, tvAddress, tvReq, tvReviews,
    tvIds, tvCert;

    private ImageView ivWorkerProfilePic, ivStat;
    private Bundle bundle;
    private String workerId;
    private String status;
    private String rate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_worker_profile, container, false);

        tvName = view.findViewById(R.id.tv_workerProfile_workerName);
        tvAge = view.findViewById(R.id.tv_workerProfile_workerAge);
        tvRating = view.findViewById(R.id.tv_workerProfile_rating);
        tvEducAtt = view.findViewById(R.id.tv_workerProfile_educationalAtt);
        tvEmail = view.findViewById(R.id.tv_workerProfile_email);
        tvPhoneNum = view.findViewById(R.id.tv_workerProfile_mobile);
        tvHourlyRate = view.findViewById(R.id.tv_workerProfile_hourlyRate);
        tvGender = view.findViewById(R.id.tv_workerProfile_gender);
        ivWorkerProfilePic = view.findViewById(R.id.iv_workerProfile_profilePic);
        tvAddress = view.findViewById(R.id.tv_workerProfile_address);
        tvEducAtt = view.findViewById(R.id.tv_workerProfile_educationalAtt);
        tvReq = view.findViewById(R.id.tv_req_service);
        tvCert = view.findViewById(R.id.tv_worker_cert);
        tvIds = view.findViewById(R.id.tv_workerids);
        tvReviews = view.findViewById(R.id.tv_worker_reviews);
        ivStat = view.findViewById(R.id.iv_worker_stat);
        bundle = new Bundle();


        workerId = getArguments().getString("workerId");
        bundle.putString("Wid" , workerId);
        DatabaseReference workerRef = FirebaseDatabase.getInstance().getReference().child("users/workers").child(workerId);
        workerRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tvName.setText(snapshot.child("fullName").getValue(String.class));
                String age = snapshot.child("age").getValue(String.class);
                tvAge.setText(age);
                tvPhoneNum.setText(snapshot.child("phoneNumber").getValue(String.class));
                rate = snapshot.child("rate").getValue(String.class);
                tvHourlyRate.setText(rate);
                tvEmail.setText(snapshot.child("email").getValue(String.class));
                tvGender.setText(snapshot.child("gender").getValue(String.class));
                tvAddress.setText(snapshot.child("address").getValue(String.class));
                tvEducAtt.setText(snapshot.child("highestEducationalAttainment").getValue(String.class));
                String rating = snapshot.child("rating").getValue(String.class);
                status = snapshot.child("status").getValue(String.class);

                if (status != null) {
                    if (status.equals("verified")) {
                        ivStat.setVisibility(View.VISIBLE);
                        ivStat.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getContext(), "Worker is Verified", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }

                if (rating != null) {
                    tvRating.setText(rating);

                } else {
                    tvRating.setText("not yet rated");
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
                                    .resize(350, 350)
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

        tvReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkerReviewsDialog workerReviewsDialog = new WorkerReviewsDialog(workerId);
                workerReviewsDialog.show(getParentFragmentManager(), "Worker Reviews");
            }
        });

        tvCert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkerCertificateDialog workerCertificateDialog = new WorkerCertificateDialog(workerId);
                workerCertificateDialog.show(getParentFragmentManager(), "Worker Certificate");
            }
        });

        tvIds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkerIdsDialogFragment workerIdsDialogFragment = new WorkerIdsDialogFragment(workerId);
                workerIdsDialogFragment.show(getParentFragmentManager(), "Worker Valid Ids");
            }
        });


        tvReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status == null || status.equals("not yet verified")) {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Worker is not yet verified.")
                            .setMessage("Are you sure you want to transact with this worker?")

                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Fragment requestAService = new RequestAService();
                                    requestAService.setArguments(bundle);
                                    FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                                    ft.replace(R.id.fragment_container_client, requestAService);
                                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                    ft.addToBackStack(null);
                                    ft.commit();
                                }
                            })

                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(R.drawable.caution_ic)
                            .show();
                } else {
                    Fragment requestAService = new RequestAService();
                    requestAService.setArguments(bundle);
                    FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_container_client, requestAService);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.addToBackStack(null);
                    ft.commit();
                }

            }
        });
        return view;
    }
}