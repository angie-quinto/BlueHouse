package com.example.bluehousev3.worker;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bluehousev3.R;
import com.example.bluehousev3.adapters.PendingRequestsAdapter;
import com.example.bluehousev3.client.available_workers.transactions.WorkerProfile;
import com.example.bluehousev3.model.PendingRequest;
import com.example.bluehousev3.worker.transactions.SelectedRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class PendingRequests extends Fragment implements PendingRequestsAdapter.OnPendingRequestClickListener {
    private ArrayList<PendingRequest> pendingRequests;
    private ArrayList<String> reqId;
    private ArrayList<String> clientId;
    private TextView tvNoReq;
    private Bundle bundle;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pending_requests, container, false);

        pendingRequests = new ArrayList<>();
        reqId = new ArrayList<>();
        tvNoReq = view.findViewById(R.id.tv_no_req);
        clientId = new ArrayList<>();
        bundle = new Bundle();
        RecyclerView rv = view.findViewById(R.id.rv_pending);

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        PendingRequestsAdapter adapter = new PendingRequestsAdapter(pendingRequests, PendingRequests.this);
        rv.setAdapter(adapter);


        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users/serviceRequests");
        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    for (DataSnapshot snapshot2 : snapshot1.getChildren()) {
                        if (snapshot2.child("workerId").getValue(String.class).equals(uid)) {
                            String status = snapshot2.child("status").getValue(String.class);
                            if (status.equals("pending")) {
                                clientId.add(snapshot1.getKey());
                                reqId.add(snapshot2.getKey());
                                PendingRequest pending = snapshot2.getValue(PendingRequest.class);
                                pendingRequests.add(pending);
                            }
                        }
                    }
                }

                if (pendingRequests.isEmpty()) {
                    tvNoReq.setText("There are no pending requests right now");
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "pending request error", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onPendingRequestClicked(int position) {
        String req = reqId.get(position);
        bundle.putString("rId", req);
        bundle.putString("cId", clientId.get(position));
        Fragment selected = new SelectedRequest();
        selected.setArguments(bundle);
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container_worker, selected);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();
    }


}