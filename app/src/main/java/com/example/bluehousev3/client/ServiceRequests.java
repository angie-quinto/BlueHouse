package com.example.bluehousev3.client;

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
import android.widget.Toast;

import com.example.bluehousev3.R;
import com.example.bluehousev3.adapters.ClientServiceRequestsAdapter;
import com.example.bluehousev3.client.available_workers.transactions.RequestMenu;
import com.example.bluehousev3.client.available_workers.transactions.WorkerProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;


public class ServiceRequests extends Fragment implements ClientServiceRequestsAdapter.OnRequestClickListener {
  private ArrayList<String> serviceType, description, startDate, startTime, endDate, endTime, assignedAddress, proposedRate,
          status, workerName, workerAddress, workerIds, reqId;

  private static final String TAG = "ServiceRequests";
  private Map<String, Object> workersMap;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_service_requests_client, container, false);


    serviceType = new ArrayList<>();
    description = new ArrayList<>();
    startDate = new ArrayList<>();
    startTime = new ArrayList<>();
    endDate = new ArrayList<>();
    endTime = new ArrayList<>();
    assignedAddress = new ArrayList<>();
    proposedRate = new ArrayList<>();
    status = new ArrayList<>();
    workerName = new ArrayList<>();
    workerAddress = new ArrayList<>();
    workerIds = new ArrayList<>();
    reqId = new ArrayList<>();



    ClientServiceRequestsAdapter adapter = new ClientServiceRequestsAdapter(serviceType, description,
            startDate, endDate, startTime, endTime, assignedAddress, proposedRate, status, workerName, workerAddress, this);
    RecyclerView recyclerView = view.findViewById(R.id.rv_service_requests_client);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    recyclerView.setAdapter(adapter);

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    String uid = user.getUid();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users/clientRequests").child(uid);

    ref.addValueEventListener(new ValueEventListener() {
      @SuppressLint("NotifyDataSetChanged")
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        for (DataSnapshot snapshots: snapshot.getChildren()) {
            reqId.add(snapshots.getKey());
          for (DataSnapshot dataSnapshot: snapshots.getChildren()) {
              workerIds.add(dataSnapshot.getKey());
            for (DataSnapshot snapshot1: dataSnapshot.getChildren()) {
              if (snapshot1.getKey().equals("serviceType")) {
                serviceType.add(snapshot1.getValue(String.class));
              }
              if (snapshot1.getKey().equals("description")) {
                description.add(snapshot1.getValue(String.class));
              }
              if (snapshot1.getKey().equals("startDate")) {
                startDate.add(snapshot1.getValue(String.class));
              }
              if (snapshot1.getKey().equals("endDate")) {
                endDate.add(snapshot1.getValue(String.class));
              }
              if (snapshot1.getKey().equals("startTime")) {
                startTime.add(snapshot1.getValue(String.class));
              }
              if (snapshot1.getKey().equals("endTime")) {
                endTime.add(snapshot1.getValue(String.class));
              }
              if (snapshot1.getKey().equals("location")) {
                assignedAddress.add(snapshot1.getValue(String.class));
              }
              if (snapshot1.getKey().equals("proposedRate")) {
                proposedRate.add(snapshot1.getValue(String.class));
              }
              if (snapshot1.getKey().equals("status")) {
                status.add(snapshot1.getValue(String.class));
              }
            }
          }
          adapter.notifyDataSetChanged();
        }
        DatabaseReference workerRef = FirebaseDatabase.getInstance().getReference().child("users/workers");
        workerRef.addValueEventListener(new ValueEventListener() {
          @SuppressLint("NotifyDataSetChanged")
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
            workersMap = (Map<String, Object>) snapshot.getValue();
            for (int i = 0; i < workerIds.size(); i++) {
              if (workersMap.containsKey(workerIds.get(i))) {
                String name = snapshot.child(workerIds.get(i)).child("fullName").getValue(String.class);
                String address = snapshot.child(workerIds.get(i)).child("address").getValue(String.class);
                workerName.add(name);
                workerAddress.add(address);
                adapter.notifyDataSetChanged();
              }

            }
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {
            Log.d(TAG, "Error on: workerRef" );
          }
        });


      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {
        Log.d(TAG, "Error on: ref" );
      }
    });



    return view;
  }

  @Override
  public void onRequestClick(int position) {
    String requestId = reqId.get(position);
    String workerId = workerIds.get(position);
    Bundle bundle = new Bundle();
    bundle.putString("workerId2", workerId);
    bundle.putString("reqId", requestId);

    Fragment menu = new RequestMenu();
    menu.setArguments(bundle);
    FragmentTransaction ft = getParentFragmentManager().beginTransaction();
    ft.replace(R.id.fragment_container_client, menu);
    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
    ft.addToBackStack(null);
    ft.commit();

  }
}