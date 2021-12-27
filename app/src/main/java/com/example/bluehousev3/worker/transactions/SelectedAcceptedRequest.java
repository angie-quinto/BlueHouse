package com.example.bluehousev3.worker.transactions;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bluehousev3.R;
import com.example.bluehousev3.client.available_workers.transactions.WorkerIdsDialogFragment;
import com.example.bluehousev3.views.RateDialog;
import com.example.bluehousev3.worker.PendingRequests;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class SelectedAcceptedRequest extends Fragment {
    private TextView tvServiceType, tvDescription, tvStartDate, tvLocation,
            tvStartTime, tvClientName, tvClientRating, tvClientGender, tvClientAge, tvStatus;
    private Button tvChat, tvRate;
    private String clientId;
    String serviceType, description, startDate,  startTime,  location;
    String clientName, clientRating, clientGender, image1, image2;
    String clientAge, status, isEmpRated;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_selected_accepted_request, container, false);

        tvServiceType = view.findViewById(R.id.tv_service_type_selected_accep);
        tvDescription = view.findViewById(R.id.tv_desc_sellec_accep);
        tvStartDate = view.findViewById(R.id.tv_start_date_selected_accep);

        tvLocation = view.findViewById(R.id.tv_location_selec_accep);

        tvStartTime = view.findViewById(R.id.tv_start_time_selec_accep);

        tvClientName = view.findViewById(R.id.tv_client_name_selec_accep);
        tvClientRating = view.findViewById(R.id.tv_client_rating_selec_accep);
        tvClientGender = view.findViewById(R.id.tv_client_sex_selec_accep);
        tvClientAge = view.findViewById(R.id.tv_client_age_selec_accep);

        tvStatus = view.findViewById(R.id.tv_status_selec_accep);
        tvChat = view.findViewById(R.id.tv_chat_emp);
        tvRate = view.findViewById(R.id.tv_rate_emp);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();
        String reqId = getArguments().getString("rIdAccept");

        clientId = getArguments().getString("cIdAccept");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users/serviceRequests").child(clientId).child(reqId);
        String path = "users/serviceRequests/" + clientId + "/" + reqId;
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
                status = snapshot.child("status").getValue(String.class);
                isEmpRated = snapshot.child("isEmployerRated").getValue(String.class);
                tvServiceType.setText(serviceType);
                tvDescription.setText(description);
                tvStartDate.setText(startDate);
                tvStartTime.setText(startTime);
                tvLocation.setText(location);
                tvStatus.setText(status);


                // check if the worker has already rated the employer
                if (isEmpRated != null) {
                  if (isEmpRated.equals("true")) {
                    tvRate.setEnabled(false);
                  }
                }

                if (status.equals("completed")) {
                    tvChat.setEnabled(false);
                }
                if (status.equals("accepted")) {
                    tvRate.setEnabled(false);
                }

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

                tvClientName.setText("Employer Name: " + clientName);
                if (clientRating != null) {
                    tvClientRating.setText("Rating: " + clientRating);
                } else {
                    tvClientRating.setText("Rating: Not Yet Rated");
                }
                tvClientGender.setText("Sex: " + clientGender);
                tvClientAge.setText("Age: " + String.valueOf(clientAge));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        tvChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("empName", clientName);
                bundle.putString("WorkerId",uid);
                bundle.putString("refPath", path);
                Fragment chat = new Chat();
                chat.setArguments(bundle);
                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container_worker, chat);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        tvRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              RateDialog rateDialog = new RateDialog(path, "employerRatings", clientId, "employers");
              rateDialog.show(getParentFragmentManager(), "rate dialog");
            }
        });

        return view;
    }
}