package com.example.bluehousev3.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bluehousev3.R;
import com.example.bluehousev3.model.ServiceRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ClientServiceRequestsAdapter extends RecyclerView.Adapter<ClientServiceRequestsAdapter.ViewHolder> {
    private OnRequestClickListener onRequestClickListener;
    private ArrayList<ServiceRequest> serviceRequests;

    public ClientServiceRequestsAdapter(ArrayList<ServiceRequest> serviceRequests, OnRequestClickListener onRequestClickListener) {

        this.onRequestClickListener = onRequestClickListener;
        this.serviceRequests = serviceRequests;
    }

    @NonNull
    @Override
    public ClientServiceRequestsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_service_requests_client, parent, false);
        return new ClientServiceRequestsAdapter.ViewHolder(view, onRequestClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientServiceRequestsAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.tvServiceType.setText("Service Type: " + serviceRequests.get(position ).getServiceType());
            holder.tvDescription.setText("Description: " + serviceRequests.get(position).getDescription());
            holder.tvStartDate.setText("Start Date: " + serviceRequests.get(position).getStartDate());
            holder.tvEndDate.setText("End Date: " + serviceRequests.get(position).getEndDate());
            holder.tvStartTime.setText("Start Time: " + serviceRequests.get(position).getStartTime());
            holder.tvEndTime.setText("End Time: " + serviceRequests.get(position).getEndTime());
            holder.tvAssignedAddress.setText("Location: " + serviceRequests.get(position).getLocation());
            holder.tvProposedRate.setText("Proposed Rate: " + serviceRequests.get(position).getProposedRate() + " : " + serviceRequests.get(position).getProposedRateTime());
            holder.tvStatus.setText("Status: " + serviceRequests.get(position).getStatus());
            holder.tvWorkerName.setText("Worker Name: " + serviceRequests.get(position).getWorkerName());
            holder.tvWorkerAddress.setText("Worker Address: " + serviceRequests.get(position).getWorkerAddress());
    }

    @Override
    public int getItemCount() {
        return serviceRequests.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvServiceType, tvDescription, tvStartDate, tvStartTime, tvAssignedAddress, tvEndDate, tvEndTime,
        tvProposedRate, tvStatus, tvWorkerName, tvWorkerAddress;
        OnRequestClickListener onRequestClickListener;
        public ViewHolder(@NonNull View itemView, OnRequestClickListener onRequestClickListener) {
            super(itemView);
            tvServiceType = itemView.findViewById(R.id.tv_service_type);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvStartDate = itemView.findViewById(R.id.tv_start_date);
            tvEndDate = itemView.findViewById(R.id.tv_end_date);
            tvStartTime = itemView.findViewById(R.id.tv_start_time);
            tvEndTime = itemView.findViewById(R.id.tv_end_time);
            tvAssignedAddress = itemView.findViewById(R.id.tv_assigned_address);
            tvProposedRate = itemView.findViewById(R.id.tv_proposed_rate);
            tvStatus = itemView.findViewById(R.id.tv_status);
            tvWorkerName = itemView.findViewById(R.id.tv_worker_name_service_requests);
            tvWorkerAddress = itemView.findViewById(R.id.tv_worker_address_service_requests);
            this.onRequestClickListener = onRequestClickListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onRequestClickListener.onRequestClick(getBindingAdapterPosition());
        }


    }
    public interface OnRequestClickListener {
        void onRequestClick(int position);
    }
}
