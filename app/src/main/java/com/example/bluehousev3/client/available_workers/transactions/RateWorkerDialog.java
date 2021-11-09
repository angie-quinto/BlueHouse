package com.example.bluehousev3.client.available_workers.transactions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.bluehousev3.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RateWorkerDialog extends DialogFragment {
    private Button btnRate, btnCancel;
    private EditText edtRate;
    private String workerId;
    private String id;

    public RateWorkerDialog(String workerId, String id) {
        this.workerId = workerId;
        this.id = id;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rate_worker_dialog, container, false);
        btnRate = view.findViewById(R.id.btn_rate);
        btnCancel = view.findViewById(R.id.btn_cancel_rate);
        edtRate = view.findViewById(R.id.edt_rate_worker);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child("workerRatings").child(workerId);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();
        DatabaseReference statRef = FirebaseDatabase.getInstance().getReference().child("users/clientRequests").child(uid).child(id).child(workerId);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });


        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rate = edtRate.getText().toString();
                int rateInt = Integer.parseInt(rate);
                if (rateInt < 1 || rateInt > 5) {
                    Toast.makeText(getActivity(), "Please provide a value not exceeding 5", Toast.LENGTH_SHORT).show();
                } else {
                    reference.child(String.valueOf(System.currentTimeMillis())).setValue(rate).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            statRef.child("status").setValue("Completed").addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getActivity(), "Successfully rated worker!", Toast.LENGTH_SHORT).show();
                                    getDialog().dismiss();
                                }
                            });

                        }
                    });
                }
            }
        });


        return view;
    }
}
