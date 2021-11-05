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

public class WorkerReviewsAdapter extends RecyclerView.Adapter<WorkerReviewsAdapter.ViewHolder> {
    private ArrayList<String> reviews;

    public WorkerReviewsAdapter(ArrayList<String> reviews) {
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public WorkerReviewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_worker_reviews, parent, false);
        return new WorkerReviewsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkerReviewsAdapter.ViewHolder holder, int position) {
        holder.tvReview.setText(reviews.get(position));
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvReview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvReview = itemView.findViewById(R.id.tv_review);


        }
    }
}
