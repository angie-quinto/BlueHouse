package com.example.bluehousev3.worker.transactions;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bluehousev3.R;
import com.example.bluehousev3.client.available_workers.transactions.RateWorkerDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SelectedRequest extends Fragment {
    private TextView tvServiceType, tvDescription, tvStartDate, tvEndDate, tvLocation, tvProposedRate,
    tvStartTime, tvEndTime, tvClientName, tvClientRating, tvClientGender, tvClientAge;
    private Button btnViewPhotos, btnRejectRequest, btnAcceptRequest;

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
        btnAcceptRequest = view.findViewById(R.id.btn_accept_selec);
        btnRejectRequest = view.findViewById(R.id.btn_reject_req);
        btnViewPhotos = view.findViewById(R.id.btn_pic_selec);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();
        String reqId = getArguments().getString("rId");


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users/serviceRequests");
        reference.addValueEventListener(new ValueEventListener() {
            String serviceType, description, startDate, endDate, startTime, endTime, location, proposedRate, proposedRateTime, clientId;
            String clientName, clientRating, clientGender, image1, image2;
            long clientAge = 0;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    for (DataSnapshot snapshot2 : snapshot1.getChildren()) {
                        if (snapshot2.getKey().equals(reqId)) {
                            serviceType = snapshot2.child("serviceType").getValue(String.class);
                            description = snapshot2.child("description").getValue(String.class);
                            startDate = snapshot2.child("startDate").getValue(String.class);
                            endDate = snapshot2.child("endDate").getValue(String.class);
                            startTime = snapshot2.child("startTime").getValue(String.class);
                            endTime = snapshot2.child("endTime").getValue(String.class);
                            location = snapshot2.child("location").getValue(String.class);
                            proposedRate = snapshot2.child("proposedRate").getValue(String.class);
                            proposedRateTime = snapshot2.child("proposedRateTime").getValue(String.class);
                            clientId = snapshot1.getKey();
                            image1 = snapshot2.child("img1Url").getValue(String.class);
                            image2 = snapshot2.child("img2Url").getValue(String.class);
                        }
                    }
                }


                DatabaseReference cRef = FirebaseDatabase.getInstance().getReference().child("users/clients").child(clientId);
                    cRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot1) {
                            clientName = snapshot1.child("fullName").getValue(String.class);
                            clientAge = snapshot1.child("age").getValue(Integer.class);
                            clientGender = snapshot1.child("gender").getValue(String.class);
                            clientRating = snapshot1.child("clientRating").getValue(String.class);

                            tvClientName.setText("Client Name: " + clientName);
                            if (clientRating != null) {
                                tvClientRating.setText("Client Rating: " + clientRating);
                            } else {
                                tvClientRating.setText("Client Rating: Not Yet Rated");
                            }
                            tvClientGender.setText("Client Gender: " + clientGender);
                            tvClientAge.setText("Client Age: " + String.valueOf(clientAge));

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                tvServiceType.setText("Service Type: " +serviceType);
                tvDescription.setText("Description: " + description);
                tvStartDate.setText("Start Date: " + startDate);
                tvEndDate.setText("End Date: " + endDate);
                tvStartTime.setText("Start Time: " + startTime);
                tvEndTime.setText("End Time: " + endTime);
                tvLocation.setText("Location: " + location);
                tvProposedRate.setText("Proposed Rate: Php " + proposedRate + ": " + proposedRateTime);

                btnViewPhotos.setOnClickListener(new View.OnClickListener() {
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

        btnRejectRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                       //todo: set status to rejected when clicked
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                reference.child("status").setValue("rejected");

            }
        });


        return view;
    }
}