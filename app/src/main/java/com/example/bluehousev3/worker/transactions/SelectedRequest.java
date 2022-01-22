package com.example.bluehousev3.worker.transactions;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bluehousev3.R;
import com.example.bluehousev3.worker.PendingRequests;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SelectedRequest extends Fragment {
    private Button tvReqPhotos;
    private TextView tvServiceType, tvDescription, tvStartDate, tvLocation,
    tvStartTime, tvClientName, tvClientRating, tvClientGender, tvClientAge, tvEmpStat;
    String serviceType, description, startDate, startTime, location;
    String clientName, clientRating, clientGender, image1, image2;
    String clientAge, status;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selected_request, container, false);
        tvServiceType = view.findViewById(R.id.tv_service_type_selected);
        tvDescription = view.findViewById(R.id.tv_desc_selec);
        tvStartDate = view.findViewById(R.id.tv_start_date_selec);
        tvLocation = view.findViewById(R.id.tv_loc_select);

        tvStartTime = view.findViewById(R.id.tv_start_time_selec);
        tvClientName = view.findViewById(R.id.tv_client_name);
        tvClientRating = view.findViewById(R.id.tv_client_rating_selec);
        tvClientGender = view.findViewById(R.id.tv_client_gender_selec);
        tvClientAge = view.findViewById(R.id.tv_client_age);
        tvEmpStat = view.findViewById(R.id.tv_emp_status);
        tvReqPhotos = view.findViewById(R.id.tv_req_photos);
        Button tvAcceptReq = view.findViewById(R.id.tv_accept_req_pend);
        Button tvRejectReq = view.findViewById(R.id.tv_reject_req);

        String reqId = getArguments().getString("rId");

        String clientId = getArguments().getString("cId");


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users/serviceRequests").child(clientId).child(reqId);
        reference.addValueEventListener(new ValueEventListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                serviceType = snapshot.child("serviceType").getValue(String.class);
                description = snapshot.child("description").getValue(String.class);
                startDate = snapshot.child("startDate").getValue(String.class);
                startTime = snapshot.child("startTime").getValue(String.class);
                location = snapshot.child("location").getValue(String.class);
                image1 = snapshot.child("img1Url").getValue(String.class);
                image2 = snapshot.child("img2Url").getValue(String.class);

                tvServiceType.setText(serviceType);
                tvDescription.setText(description);
                tvStartDate.setText(startDate);
                tvStartTime.setText(startTime);
                tvLocation.setText(location);


                tvReqPhotos.setOnClickListener(view1 -> {
                    ViewRequestPhotos viewRequestPhotos = new ViewRequestPhotos(image1, image2);
                    viewRequestPhotos.show(getParentFragmentManager(), "Request Photos");
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
                        clientRating = snapshot1.child("rating").getValue(String.class);

                        tvClientName.setText(clientName);
                        if (clientRating != null) {
                            tvClientRating.setText(clientRating);

                        } else {
                            tvClientRating.setText("Not yet rated");
                        }

                        tvClientGender.setText(clientGender);
                        tvClientAge.setText(clientAge);

                        status = snapshot1.child("status").getValue(String.class);
                        if (status != null) {
                            tvEmpStat.setText(status);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        tvRejectReq.setOnClickListener(view12 -> new AlertDialog.Builder(getActivity())
            .setTitle("Reject request")
            .setMessage("Are you sure you want to reject this request?")
            .setPositiveButton("Yes", (dialog, which) -> reference.child("status").setValue("rejected").addOnSuccessListener(unused -> {
                Toast.makeText(getContext(), "Request Rejected", Toast.LENGTH_SHORT).show();
                Fragment pending = new PendingRequests();
                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container_worker, pending);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }))
            .setNegativeButton(android.R.string.no, null)
            .setIcon(R.drawable.ic_alert)
            .show());

        tvAcceptReq.setOnClickListener(view13 -> {
            if (status == null || status.equals("Not yet verified")) {
                new AlertDialog.Builder(getContext())
                    .setTitle("Employer is not yet verified")
                    .setMessage("Are you sure you want to transact with this employer?")

                    .setPositiveButton(android.R.string.yes, (dialog, which) -> reference.child("status").setValue("accepted").addOnSuccessListener(unused -> {
                        Toast.makeText(getContext(), "Request Accepted", Toast.LENGTH_SHORT).show();
                        Fragment pending = new PendingRequests();
                        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                        ft.replace(R.id.fragment_container_worker, pending);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        ft.addToBackStack(null);
                        ft.commit();
                    }))
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(R.drawable.caution_ic)
                    .show();
            }
        });

        return view;
    }
}