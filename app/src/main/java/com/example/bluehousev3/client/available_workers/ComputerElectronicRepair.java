package com.example.bluehousev3.client.available_workers;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bluehousev3.R;
import com.example.bluehousev3.adapters.AvailableWorkerAdapter;
import com.example.bluehousev3.client.available_workers.transactions.WorkerProfile;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;


public class ComputerElectronicRepair extends Fragment implements AvailableWorkerAdapter.OnWorkerListener{


    private ArrayList<String> workerIds;
    private Map<String, Object> workersMap;
    private ArrayList<String> workersName;
    private ArrayList<String> workersLoc;
    private ArrayList<String> workersRating;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_computer_electronic_repair, container, false);

        workersName = new ArrayList<>();
        workersLoc = new ArrayList<>();
        workersRating = new ArrayList<>();
        workerIds = new ArrayList<>();

        AvailableWorkerAdapter adapter = new AvailableWorkerAdapter(workersName, workersLoc, workersRating, this);
        RecyclerView rvAvailableWorkers = view.findViewById(R.id.rv_computerAndElectronicRepair);
        rvAvailableWorkers.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvAvailableWorkers.setAdapter(adapter);

        DatabaseReference workerRef = FirebaseDatabase.getInstance().getReference().child("users/workers");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("workersUnderService/carpentry");

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
                                String name = snapshot.child(workerIds.get(i)).child("fullName").getValue(String.class);
                                String address = snapshot.child(workerIds.get(i)).child("address").getValue(String.class);
                                String rating = snapshot.child(workerIds.get(i)).child("rating").getValue(String.class);
                                workersName.add(name);
                                workersLoc.add(address);
                                workersRating.add(rating);
                                Log.d("TAG", "name: " + " / " + workersLoc.size());
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