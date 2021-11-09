package com.example.bluehousev3.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bluehousev3.model.ServiceRequest;

import java.util.ArrayList;


public class AcceptedRequestsAdapter extends RecyclerView.Adapter<AcceptedRequestsAdapter.ViewHolder> {
    ArrayList<ServiceRequest> acceptedRequests;
    public AcceptedRequestsAdapter() {}

    @NonNull
    @Override
    public AcceptedRequestsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AcceptedRequestsAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
