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
import com.example.bluehousev3.adapters.AcceptedRequestsAdapter;
import com.example.bluehousev3.model.PendingRequest;
import com.example.bluehousev3.worker.transactions.SelectedAcceptedRequest;
import com.example.bluehousev3.worker.transactions.SelectedRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class AcceptedRequests extends Fragment implements AcceptedRequestsAdapter.OnAcceptedRequestClickListener {
    private ArrayList<PendingRequest> acceptedRequests;
    private ArrayList<String> reqId;
    private ArrayList<String> clientId;

    private Bundle bundle;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accepted_requests, container, false);

        acceptedRequests = new ArrayList<>();
        reqId = new ArrayList<>();
        clientId = new ArrayList<>();
        bundle = new Bundle();
        RecyclerView rv = view.findViewById(R.id.rc_accepted);
        TextView tvNo = view.findViewById(R.id.tv_no);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        AcceptedRequestsAdapter adapter = new AcceptedRequestsAdapter(acceptedRequests, this);
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
                            if (status != null) {
                                if (status.equals("accepted") || status.equals("completed")) {
                                    clientId.add(snapshot1.getKey());
                                    reqId.add(snapshot2.getKey());
                                    PendingRequest pending = snapshot2.getValue(PendingRequest.class);
                                    acceptedRequests.add(pending);
                                }
                            }

                        }
                    }
                }
                if (acceptedRequests.isEmpty()) {
                    tvNo.setText("Accepted request is empty");
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
    public void onAcceptedRequestClicked(int position) {
        String req = reqId.get(position);
        bundle.putString("rIdAccept", req);
        bundle.putString("cIdAccept", clientId.get(position));
        Fragment selected = new SelectedAcceptedRequest();
        selected.setArguments(bundle);
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container_worker, selected);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();
    }
}