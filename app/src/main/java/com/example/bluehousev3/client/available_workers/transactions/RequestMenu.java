package com.example.bluehousev3.client.available_workers.transactions;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.bluehousev3.R;
import com.example.bluehousev3.views.RateDialog;
import com.example.bluehousev3.views.ReviewDialog;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class RequestMenu extends Fragment {
    private Button btnCancel, btnMark, btnChat, btnReview;
    private static final String TAG = "Request Menu";
    private Bundle bundle;
    private String workerName;
    private String workerId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request_menu, container, false);

        btnMark = view.findViewById(R.id.btn_mark_menu);
        btnCancel = view.findViewById(R.id.btn_cancel_menu);
        btnChat = view.findViewById(R.id.btn_chat_req_menu);
        btnReview = view.findViewById(R.id.btn_create_review);
        String reqId = getArguments().getString("reqId");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();
        bundle = new Bundle();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users/serviceRequests").child(uid).child(reqId);
        String path = "users/serviceRequests/" + uid + "/" + reqId;

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                workerId = snapshot.child("workerId").getValue(String.class);
                Toast.makeText(getContext(), "wid: " + workerId, Toast.LENGTH_SHORT).show();
                String status = snapshot.child("status").getValue(String.class);
                workerName = snapshot.child("workerName").getValue(String.class);
                if (status != null) {
                    if (status.equals("cancelled") || status.equals("completed") || status.equals("rejected")) {
                        btnCancel.setEnabled(false);
                        btnMark.setEnabled(false);
                        btnChat.setEnabled(false);
                        btnReview.setEnabled(false);

                    } else {
                        btnCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                                builder1.setMessage("Are you sure you want to cancel your service request?");
                                builder1.setCancelable(true);

                                builder1.setPositiveButton(
                                        "Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                reference.child("status").setValue("cancelled").addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Toast.makeText(getActivity(), "Request Cancelled", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                                dialog.cancel();
                                            }
                                        });

                                builder1.setNegativeButton(
                                        "No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });

                                AlertDialog alert11 = builder1.create();
                                alert11.show();
                            }
                        });
                        btnMark.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ReviewDialog reviewDialog = new ReviewDialog(workerId);
                                reviewDialog.show(getParentFragmentManager(), "review worker dialog");
                                RateDialog rateDialog = new RateDialog(path, "workerRatings", workerId, "workers");
                                rateDialog.show(getParentFragmentManager(), "rate worker dialog");
                                reference.child("status").setValue("completed").addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(getActivity(), "Transaction Complete", Toast.LENGTH_SHORT).show();

                                    }
                                });

                            }
                        });

                        btnChat.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bundle.putString("WORKER_NAME", workerName);
                                bundle.putString("PATH", path);
                                bundle.putString("CLIENT_ID", uid);
                                Fragment chat = new Chat();
                                chat.setArguments(bundle);
                                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                                ft.replace(R.id.fragment_container_client, chat);
                                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                ft.addToBackStack(null);
                                ft.commit();
                            }
                        });

                        btnReview.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ReviewDialog reviewDialog = new ReviewDialog(workerId);
                                reviewDialog.show(getParentFragmentManager(), "review worker");
                            }
                        });
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled: Request Menu Error");
            }
        });


        return view;
    }
}