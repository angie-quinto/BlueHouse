package com.example.bluehousev3.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bluehousev3.R;
import com.example.bluehousev3.model.AvailableWorkersUnderService;

import java.util.ArrayList;

public class AvailableWorkerAdapter extends RecyclerView.Adapter<AvailableWorkerAdapter.ViewHolder> {

    private OnWorkerListener onWorkerListener;
    private ArrayList<AvailableWorkersUnderService> availableWorkersUnderServices;
    public AvailableWorkerAdapter() {}
    public AvailableWorkerAdapter(ArrayList<AvailableWorkersUnderService> availableWorkers, OnWorkerListener onWorkerListener) {
        this.availableWorkersUnderServices = availableWorkers;
        this.onWorkerListener = onWorkerListener;

    }

    @NonNull
    @Override
    public AvailableWorkerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View availWorkers = inflater.inflate(R.layout.row_available_workers, parent, false);
        return new AvailableWorkerAdapter.ViewHolder(availWorkers, onWorkerListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AvailableWorkerAdapter.ViewHolder holder, int position) {
        holder.tvName.setText(availableWorkersUnderServices.get(position).getFullName());
        holder.tvWorkerLoc.setText("Location: " + availableWorkersUnderServices.get(position).getAddress());
        if (availableWorkersUnderServices.get(position).getRating() != null) {
            holder.tvRating.setText("Rating: " + availableWorkersUnderServices.get(position).getRating());
        } else {
            holder.tvRating.setText("Rating: not yet rated");
        }


    }

    @Override
    public int getItemCount() {
        return availableWorkersUnderServices.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName, tvWorkerLoc, tvRating;
        ImageView ivPic;
        OnWorkerListener onWorkerListener;
        public ViewHolder(@NonNull View itemView, OnWorkerListener onWorkerListener) {
            super(itemView);
            ivPic = itemView.findViewById(R.id.iv_worker_pic);
            tvName = itemView.findViewById(R.id.tv_worker_name);
            tvWorkerLoc = itemView.findViewById(R.id.tv_worker_location);
            tvRating = itemView.findViewById(R.id.tv_worker_rating);
            this.onWorkerListener = onWorkerListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onWorkerListener.onWorkerClick(getBindingAdapterPosition());
        }
    }

    public interface OnWorkerListener {
        void onWorkerClick(int position);
    }

}
