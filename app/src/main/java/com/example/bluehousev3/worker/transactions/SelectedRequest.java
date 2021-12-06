package com.example.bluehousev3.worker.transactions;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bluehousev3.R;
import com.example.bluehousev3.client.available_workers.transactions.RateWorkerDialog;
import com.example.bluehousev3.worker.PendingRequests;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SelectedRequest extends Fragment {
    private TextView tvServiceType, tvDescription, tvStartDate, tvEndDate, tvLocation, tvProposedRate,
    tvStartTime, tvEndTime, tvClientName, tvClientRating, tvClientGender, tvClientAge, tvEmpStat, tvReqPhotos, tvAcceptReq, tvRejectReq;
    private String clientId;
    String serviceType, description, startDate, endDate, startTime, endTime, location, proposedRate, proposedRateTime;
    String clientName, clientRating, clientGender, image1, image2;
    String clientAge, status;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selected_request, container, false);
        tvServiceType = view.findViewById(R.id.tv_service_type_selected);
        tvDescription = view.findViewById(R.id.tv_desc_selec);
        tvStartDate = view.findViewById(R.id.tv_start_date_selec);
        tvEndDate = view.findViewById(R.id.tv_end_date_selec);
        tvLocation = view.findViewById(R.id.tv_loc_select);
        tvProposedRate = view.findViewById(R.id.tv_proposed_rate_selec);
        tvStartTime = view.findViewById(R.id.tv_start_time_selec);
        tvEndTime = view.findViewById(R.id.tv_end_time_selec);
        tvClientName = view.findViewById(R.id.tv_client_name);
        tvClientRating = view.findViewById(R.id.tv_client_rating_selec);
        tvClientGender = view.findViewById(R.id.tv_client_gender_selec);
        tvClientAge = view.findViewById(R.id.tv_client_age);
        tvEmpStat = view.findViewById(R.id.tv_emp_status);
        tvReqPhotos = view.findViewById(R.id.tv_req_photos);
        tvAcceptReq = view.findViewById(R.id.tv_accept_req);
        tvRejectReq = view.findViewById(R.id.tv_reject_req);

        String reqId = getArguments().getString("rId");

       clientId = getArguments().getString("cId");


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users/serviceRequests").child(clientId).child(reqId);
        reference.addValueEventListener(new ValueEventListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                serviceType = snapshot.child("serviceType").getValue(String.class);
                description = snapshot.child("description").getValue(String.class);
                startDate = snapshot.child("startDate").getValue(String.class);
                endDate = snapshot.child("endDate").getValue(String.class);
                startTime = snapshot.child("startTime").getValue(String.class);
                endTime = snapshot.child("endTime").getValue(String.class);
                location = snapshot.child("location").getValue(String.class);
                proposedRate = snapshot.child("proposedRate").getValue(String.class);
                proposedRateTime = snapshot.child("proposedRateTime").getValue(String.class);
                image1 = snapshot.child("img1Url").getValue(String.class);
                image2 = snapshot.child("img2Url").getValue(String.class);

                tvServiceType.setText(serviceType);
                tvDescription.setText("Description: " + description);
                tvStartDate.setText("Start Date: " + startDate);
                tvEndDate.setText("End Date: " + endDate);
                tvStartTime.setText("Start Time: " + startTime);
                tvEndTime.setText("End Time: " + endTime);
                tvLocation.setText("Location: " + location);
                tvProposedRate.setText("Proposed Rate: Php " + proposedRate + ": " + proposedRateTime);

                tvReqPhotos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ViewRequestPhotos viewRequestPhotos = new ViewRequestPhotos(image1, image2);
                        viewRequestPhotos.show(getParentFragmentManager(), "Request Photos");
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

            DatabaseReference cRef = FirebaseDatabase.getInstance().getReference().child("users/employers").child(clientId);
                cRef.addValueEventListener(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot1) {
                        clientName = snapshot1.child("fullName").getValue(String.class);
                        clientAge = snapshot1.child("age").getValue(String.class);
                        clientGender = snapshot1.child("gender").getValue(String.class);
                        clientRating = snapshot1.child("clientRating").getValue(String.class);

                        tvClientName.setText("Employer Name: " + clientName);
                        if (clientRating != null) {
                            tvClientRating.setText("Rating: " + clientRating);
                        } else {
                            tvClientRating.setText("Rating: Not Yet Rated");
                        }
                        tvClientGender.setText("Sex: " + clientGender);
                        tvClientAge.setText("Age: " + String.valueOf(clientAge));

                        status = snapshot1.child("status").getValue(String.class);
                        if (status != null) {
                            tvEmpStat.setText("Status: " + status);
                        } else {
                            tvEmpStat.setText("Status: not yet verified");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        tvRejectReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(getActivity())
                    .setTitle("Reject request")
                    .setMessage("Are you sure you want to reject this request?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {


                            reference.child("status").setValue("rejected").addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getContext(), "Request Rejected", Toast.LENGTH_SHORT).show();
                                    Fragment pending = new PendingRequests();
                                    FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                                    ft.replace(R.id.fragment_container_worker, pending);
                                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                    ft.addToBackStack(null);
                                    ft.commit();
                                }
                            });
                        }

                    })
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            }
        });

        tvAcceptReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status == null || status.equals("not yet verified")) {
                    new AlertDialog.Builder(getContext())
                        .setTitle("Employer is not yet verified")
                        .setMessage("Are you sure you want to transact with this employer?")

                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                reference.child("status").setValue("accepted").addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(getContext(), "Request Accepted", Toast.LENGTH_SHORT).show();
                                        Fragment pending = new PendingRequests();
                                        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                                        ft.replace(R.id.fragment_container_worker, pending);
                                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                        ft.addToBackStack(null);
                                        ft.commit();
                                    }
                                });
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(R.drawable.caution_ic)
                        .show();
                }
            }
        });

        return view;
    }
}