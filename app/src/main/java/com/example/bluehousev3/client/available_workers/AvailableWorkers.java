package com.example.bluehousev3.client.available_workers;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bluehousev3.R;
import com.example.bluehousev3.adapters.AvailableWorkerAdapter;
import com.example.bluehousev3.client.available_workers.transactions.WorkerProfile;
import com.example.bluehousev3.model.AvailableWorkersUnderService;
import com.example.bluehousev3.model.PendingRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;


public class AvailableWorkers extends Fragment implements AvailableWorkerAdapter.OnWorkerListener{


    private ArrayList<String> workerIds;
    private Map<String, Object> workersMap;
    private ArrayList<AvailableWorkersUnderService> availableWorkersUnderServices;
    private TextView tvLabel;
    private String rating;


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_available_workers, container, false);


        workerIds = new ArrayList<>();
        tvLabel = view.findViewById(R.id.tv_available_workers_label);

        availableWorkersUnderServices = new ArrayList<>();
        assert getArguments() != null;
        String serviceType = getArguments().getString("serviceType");
        String serviceName = getArguments().getString("serviceName");
        tvLabel.setText("Available workers under " + serviceName);

        DatabaseReference workerRef = FirebaseDatabase.getInstance().getReference().child("users/workers");
        AvailableWorkerAdapter adapter = new AvailableWorkerAdapter(availableWorkersUnderServices, this);
        RecyclerView rvAvailableWorkers = view.findViewById(R.id.rv_beautician);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("workersUnderService").child(serviceType);


        rvAvailableWorkers.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvAvailableWorkers.setAdapter(adapter);

        ref.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds : snapshot.getChildren()) {
                    workerIds.add(ds.getValue(String.class));
                    adapter.notifyDataSetChanged();
                }

                workerRef.addValueEventListener(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        workersMap = (Map<String, Object>) snapshot.getValue();
                        for (int i = 0; i < workerIds.size(); i++) {
                            if (workersMap.containsKey(workerIds.get(i))) {

                                AvailableWorkersUnderService aw = snapshot.child(workerIds.get(i)).getValue(AvailableWorkersUnderService.class);
                                availableWorkersUnderServices.add(aw);
                                adapter.notifyDataSetChanged();
                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }

    @Override
    public void onWorkerClick(int position) {
        String workerId = workerIds.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("workerId", workerId);
        Fragment workerProfile = new WorkerProfile();
        workerProfile.setArguments(bundle);
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container_client, workerProfile);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();
    }
}