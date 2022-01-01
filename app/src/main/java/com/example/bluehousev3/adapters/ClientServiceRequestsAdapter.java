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
    private ArrayList<String> serviceRequests;

    public ClientServiceRequestsAdapter(ArrayList<String> serviceRequests, OnRequestClickListener onRequestClickListener) {

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
            holder.tvServiceType.setText(serviceRequests.get(position ));

    }

    @Override
    public int getItemCount() {
        return serviceRequests.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvServiceType;
        OnRequestClickListener onRequestClickListener;
        public ViewHolder(@NonNull View itemView, OnRequestClickListener onRequestClickListener) {
            super(itemView);
            tvServiceType = itemView.findViewById(R.id.tv_service_type);
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
