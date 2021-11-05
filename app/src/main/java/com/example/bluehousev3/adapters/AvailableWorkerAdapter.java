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

import java.util.ArrayList;

public class AvailableWorkerAdapter extends RecyclerView.Adapter<AvailableWorkerAdapter.ViewHolder> {
    private ArrayList<String> workerNames, workerLocation, workerRating;
    private OnWorkerListener onWorkerListener;

    public AvailableWorkerAdapter() {}
    public AvailableWorkerAdapter(ArrayList<String> workerNames, ArrayList<String> workerLocation, ArrayList<String> workerRating, OnWorkerListener onWorkerListener) {
        this.workerNames = workerNames;
        this.workerLocation = workerLocation;
        this.workerRating = workerRating;
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
        holder.tvName.setText("Name: " + workerNames.get(position));
        holder.tvWorkerLoc.setText("Location: " + workerLocation.get(position));

        if (workerRating.get(position) == null) {
            holder.tvRating.setText("Rating: not yet rated");
        } else {
            holder.tvRating.setText("Rating: " + workerRating.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return workerNames.size();
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
