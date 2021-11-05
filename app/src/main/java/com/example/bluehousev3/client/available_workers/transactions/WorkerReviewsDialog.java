package com.example.bluehousev3.client.available_workers.transactions;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bluehousev3.R;
import com.example.bluehousev3.adapters.WorkerReviewsAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WorkerReviewsDialog extends DialogFragment {
    private ArrayList<String> reviews;
    private String workerId;
    private TextView tv;
    private Button btnBack;

    public WorkerReviewsDialog(String workerId) {
        this.workerId = workerId;
    }
    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_worker_reviews, container, false);

        reviews = new ArrayList<>();

        tv = view.findViewById(R.id.tv_reviews);
        btnBack = view.findViewById(R.id.btn_back_reviews);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users/workerReviews").child(workerId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1: snapshot.getChildren()) {
                    reviews.add(snapshot1.getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if (!reviews.isEmpty()) {
            Toast.makeText(getActivity(), "whh", Toast.LENGTH_SHORT).show();
            WorkerReviewsAdapter adapter = new WorkerReviewsAdapter(reviews);
            RecyclerView recyclerView = view.findViewById(R.id.rv_worker_reviews);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(adapter);
        } else {
            tv.setText("Worker has no reviews yet");
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return view;
    }
}
