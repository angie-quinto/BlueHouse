package com.example.bluehousev3.worker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bluehousev3.R;
import com.example.bluehousev3.adapters.WorkerServicesOfferedAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class ServicesOffered extends Fragment {
private ArrayList<String> servicesOffered;
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private final String uid = user.getUid();
    private final DatabaseReference serviceOfferedRef = FirebaseDatabase.getInstance().getReference().child("users/workerServicesOffered").child(uid);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_services_offered, container, false);

        servicesOffered = new ArrayList<>();

        RecyclerView rv = view.findViewById(R.id.rv_services_offered);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        WorkerServicesOfferedAdapter adapter = new WorkerServicesOfferedAdapter(servicesOffered);
        rv.setAdapter(adapter);

        serviceOfferedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, String> map = (Map<String, String>) snapshot.getValue();
                if (map != null) {
                    servicesOffered.addAll(map.values());
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }
}