package com.example.bluehousev3.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bluehousev3.R;
import com.example.bluehousev3.model.PendingRequest;

import java.util.ArrayList;

public class PendingRequestsAdapter extends RecyclerView.Adapter<PendingRequestsAdapter.ViewHolder> {
    private ArrayList<PendingRequest> pendingRequests;
    OnPendingRequestClickListener onPendingRequestClickListener;

    public PendingRequestsAdapter(ArrayList<PendingRequest> pendingRequests, OnPendingRequestClickListener onPendingRequestClickListener) {
        this.pendingRequests = pendingRequests;
        this.onPendingRequestClickListener = onPendingRequestClickListener;
    }
    @NonNull
    @Override
    public PendingRequestsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_pending_requests, parent, false);
        return new ViewHolder(view, onPendingRequestClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingRequestsAdapter.ViewHolder holder, int position) {
        holder.tvServiceType.setText("Service Type: " + pendingRequests.get(position).getServiceType());
        holder.tvStartTime.setText("Time: " + pendingRequests.get(position).getStartTime());
        holder.tvStartDate.setText("Date: " + pendingRequests.get(position).getStartDate());
        holder.tvLocation.setText("Location: " + pendingRequests.get(position).getLocation());
    }

    @Override
    public int getItemCount() {
        return pendingRequests.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvServiceType, tvStartDate, tvStartTime, tvLocation;
        OnPendingRequestClickListener onPendingRequestClickListener;
        public ViewHolder(@NonNull View itemView, OnPendingRequestClickListener onPendingRequestClickListener) {
            super(itemView);
            tvServiceType = itemView.findViewById(R.id.tv_serviceType_worker);
            tvStartDate = itemView.findViewById(R.id.tv_date_worker);
            tvStartTime = itemView.findViewById(R.id.tv_time_worker);
            tvLocation = itemView.findViewById(R.id.tv_location_worker);
            this.onPendingRequestClickListener = onPendingRequestClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onPendingRequestClickListener.onPendingRequestClicked(getBindingAdapterPosition());
        }
    }

    public interface OnPendingRequestClickListener{
        void onPendingRequestClicked(int position);
    }
}
