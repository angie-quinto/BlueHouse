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
import android.widget.TextView;
import android.widget.Toast;

import com.example.bluehousev3.R;
import com.example.bluehousev3.adapters.ClientServiceRequestsAdapter;
import com.example.bluehousev3.client.available_workers.transactions.RequestMenu;
import com.example.bluehousev3.client.available_workers.transactions.WorkerProfile;
import com.example.bluehousev3.model.ServiceRequest;
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

  private TextView tvNoReq;
  private  ArrayList<String> reqId;
  private ArrayList<ServiceRequest> serviceRequests;
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_service_requests_client, container, false);
    tvNoReq = view.findViewById(R.id.tv_no_req_client);
    serviceRequests = new ArrayList<>();
    reqId = new ArrayList<>();
    RecyclerView rv = view.findViewById(R.id.rv_service_requests_client);
    rv.setLayoutManager(new LinearLayoutManager(getActivity()));
    ClientServiceRequestsAdapter adapter = new ClientServiceRequestsAdapter(serviceRequests, this);
    rv.setAdapter(adapter);

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    String uid = user.getUid();

    DatabaseReference serviceReqRef = FirebaseDatabase.getInstance().getReference().child("users").child("serviceRequests").child(uid);
    serviceReqRef.addValueEventListener(new ValueEventListener() {
      @SuppressLint("NotifyDataSetChanged")
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
          reqId.add(snapshot1.getKey());
          ServiceRequest serviceRequest = snapshot1.getValue(ServiceRequest.class);
          serviceRequests.add(serviceRequest);
        }
        adapter.notifyDataSetChanged();
        if (serviceRequests.isEmpty()) {
          tvNoReq.setText("Service Requests is Empty");
        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {

      }
    });
    return view;
  }

  @Override
  public void onRequestClick(int position) {
    String requestId = reqId.get(position);
    String workerId = serviceRequests.get(position).getWorkerId();
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