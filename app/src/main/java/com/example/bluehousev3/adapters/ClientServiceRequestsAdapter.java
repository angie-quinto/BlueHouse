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
    private ArrayList<String> serviceType, description, startDate, startTime, endDate, endTime, assignedAddress, proposedRate,
    status, workerName, workerAddress;
    private OnRequestClickListener onRequestClickListener;
    private List<ServiceRequest> serviceRequests;

    public ClientServiceRequestsAdapter(ArrayList<String> serviceType, ArrayList<String> description, ArrayList<String> startDate,
                                        ArrayList<String> endDate, ArrayList<String> startTime, ArrayList<String> endTime,
                                        ArrayList<String> assignedAddress, ArrayList<String> proposedRate, ArrayList<String> status, ArrayList<String> workerName,
                                        ArrayList<String> workerAddress, OnRequestClickListener onRequestClickListener) {
        this.serviceType = serviceType;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.assignedAddress = assignedAddress;
        this.proposedRate = proposedRate;
        this.status = status;
        this.workerName = workerName;
        this.workerAddress = workerAddress;
        this.onRequestClickListener = onRequestClickListener;
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
        if (!startDate.isEmpty()) {
            holder.tvServiceType.setText("Service Type: " + serviceType.get(position ));
            holder.tvDescription.setText("Description: " + description.get(position));
            holder.tvStartDate.setText("Start Date: " + startDate.get(position));
            holder.tvEndDate.setText("End Date: " + endDate.get(position));
            holder.tvStartTime.setText("Start Time: " + startTime.get(position));
            holder.tvEndTime.setText("End Time: " + endTime.get(position));
            holder.tvAssignedAddress.setText("Assigned Address: " + assignedAddress.get(position));
            holder.tvProposedRate.setText("Proposed Rate: " + proposedRate.get(position));
            holder.tvStatus.setText("Status: " + status.get(position));
            holder.tvWorkerName.setText("Worker Name: " + workerName.get(position));
            holder.tvWorkerAddress.setText("Worker Address: " + workerAddress.get(position));


        }

    }

    @Override
    public int getItemCount() {
        return workerName.size();
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
