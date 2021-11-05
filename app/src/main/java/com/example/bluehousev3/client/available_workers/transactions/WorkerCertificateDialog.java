package com.example.bluehousev3.client.available_workers.transactions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.bluehousev3.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class WorkerCertificateDialog extends DialogFragment {
    private String workerId;
    private ImageView iv;
    private Button btnBack;
    private TextView tv;

    public WorkerCertificateDialog(String workerId) {
        this.workerId = workerId;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_worker_cert, container, false);

        iv = view.findViewById(R.id.iv_cert);
        btnBack = view.findViewById(R.id.btn_back_cert);
        tv = view.findViewById(R.id.tv_cert);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users/workerIds");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    if (dataSnapshot.getKey().equals(workerId)) {
                        String cert = dataSnapshot.child("CertificateUrl").getValue(String.class);

                        if (cert == null) {
                            tv.setText("No Certificate Found");
                        } else {
                            Picasso.get().load(cert).into(iv);
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return view;
    }
}
