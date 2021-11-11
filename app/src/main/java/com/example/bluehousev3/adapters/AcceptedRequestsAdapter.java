package com.example.bluehousev3.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bluehousev3.R;
import com.example.bluehousev3.model.PendingRequest;
import com.example.bluehousev3.model.ServiceRequest;

import java.util.ArrayList;


public class AcceptedRequestsAdapter extends RecyclerView.Adapter<AcceptedRequestsAdapter.ViewHolder> {
    private ArrayList<PendingRequest> acceptedRequests;
    private OnAcceptedRequestClickListener onAcceptedRequestClickListener;

    public AcceptedRequestsAdapter(ArrayList<PendingRequest> acceptedRequests, OnAcceptedRequestClickListener onAcceptedRequestClickListener) {
        this.acceptedRequests = acceptedRequests;
        this.onAcceptedRequestClickListener = onAcceptedRequestClickListener;
    }

    @NonNull
    @Override
    public AcceptedRequestsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_accepted, parent, false);
        return new ViewHolder(view, onAcceptedRequestClickListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AcceptedRequestsAdapter.ViewHolder holder, int position) {
        holder.tvServiceType.setText("Service Type: " + acceptedRequests.get(position).getServiceType());
        holder.tvStartTime.setText("Time: " + acceptedRequests.get(position).getStartTime());
        holder.tvStartDate.setText("Date: " + acceptedRequests.get(position).getStartDate());
        holder.tvLocation.setText("Location: " + acceptedRequests.get(position).getLocation());
    }

    @Override
    public int getItemCount() {
        return acceptedRequests.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvServiceType, tvStartDate, tvStartTime, tvLocation;
        OnAcceptedRequestClickListener onAcceptedRequestClickListener;
        public ViewHolder(@NonNull View itemView, OnAcceptedRequestClickListener onAcceptedRequestClickListener) {
            super(itemView);

            tvServiceType = itemView.findViewById(R.id.tv_serviec_type_accep);
            tvStartDate = itemView.findViewById(R.id.tv_start_date_accep);
            tvStartTime = itemView.findViewById(R.id.tv_start_time_accep);
            tvLocation = itemView.findViewById(R.id.tv_location_accepted);
            this.onAcceptedRequestClickListener = onAcceptedRequestClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onAcceptedRequestClickListener.onAcceptedRequestClicked(getBindingAdapterPosition());
        }
    }

    public interface OnAcceptedRequestClickListener {
        void onAcceptedRequestClicked(int position);
    }
}
