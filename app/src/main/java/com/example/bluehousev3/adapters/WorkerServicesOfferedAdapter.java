package com.example.bluehousev3.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bluehousev3.R;

import java.util.ArrayList;

public class WorkerServicesOfferedAdapter extends RecyclerView.Adapter<WorkerServicesOfferedAdapter.ViewHolder> {
    private ArrayList<String> servicesOffered;

    public WorkerServicesOfferedAdapter(ArrayList<String> servicesOffered) {
        this.servicesOffered = servicesOffered;
    }

    @NonNull
    @Override
    public WorkerServicesOfferedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_worker_services_offered, parent, false);
        return new WorkerServicesOfferedAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkerServicesOfferedAdapter.ViewHolder holder, int position) {
        holder.tvServiceName.setText(servicesOffered.get(position));
    }

    @Override
    public int getItemCount() {
        return servicesOffered.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvServiceName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvServiceName = itemView.findViewById(R.id.tv_service_name);
        }
    }
}
