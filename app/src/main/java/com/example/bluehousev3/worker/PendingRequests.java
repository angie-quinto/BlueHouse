package com.example.bluehousev3.worker;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private TextView tvNoReq;
    private Bundle bundle;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pending_requests, container, false);

        pendingRequests = new ArrayList<>();
        reqId = new ArrayList<>();
        tvNoReq = view.findViewById(R.id.tv_no_req);
        bundle = new Bundle();
        RecyclerView rv = view.findViewById(R.id.rv_pending);

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        PendingRequestsAdapter adapter = new PendingRequestsAdapter(pendingRequests, PendingRequests.this);
        rv.setAdapter(adapter);


        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users/serviceRequests/").child(uid);
        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    reqId.add(snapshot1.getKey());
                    String status = snapshot1.child("status").getValue(String.class);
                    assert status != null;
                    if (status.equals("pending")) {
                        PendingRequest pending = snapshot1.getValue(PendingRequest.class);
                        assert pending != null;
                        pendingRequests.add(pending);

                    }

                }
                if (pendingRequests.isEmpty()) {
                    tvNoReq.setText("There are no pending requests right now");
                }
//                Toast.makeText(getActivity(), "reqid" + reqId.get(0), Toast.LENGTH_SHORT).show();
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
        Toast.makeText(getActivity(), "reqId: " + req, Toast.LENGTH_SHORT).show();
        bundle.putString("rId", req);
        Fragment selected = new SelectedRequest();
        selected.setArguments(bundle);
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container_worker, selected);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();
    }


}