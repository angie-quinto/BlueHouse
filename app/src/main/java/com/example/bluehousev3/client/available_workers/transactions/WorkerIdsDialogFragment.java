package com.example.bluehousev3.client.available_workers.transactions;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

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


public class WorkerIdsDialogFragment extends DialogFragment {
    private ImageView iv1;
    private Button btnBack;
    private String workerId;
    private static final String TAG = "WorkerIdsDialogFragment";

    public WorkerIdsDialogFragment(String workerId) {
        this.workerId = workerId;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_worker_ids, container, false);
        iv1 = view.findViewById(R.id.iv_id1_dialog);
        btnBack = view.findViewById(R.id.btn_back);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users/workerIds").child(workerId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String id1 = snapshot.child("ValidIdUrl").getValue(String.class);
                    Picasso.get().load(id1).into(iv1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled: WorkerIdsFragment Error");
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
