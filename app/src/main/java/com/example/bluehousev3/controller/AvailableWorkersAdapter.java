package com.example.bluehousev3.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bluehousev3.R;
import com.example.bluehousev3.model.Worker;

import java.util.List;

public class AvailableWorkersAdapter extends RecyclerView.Adapter<AvailableWorkersAdapter.ViewHolder> {
private List<Worker> workers;

public AvailableWorkersAdapter(List<Worker> workers) {
  this.workers = workers;
}
  public class ViewHolder extends RecyclerView.ViewHolder {
    TextView tvName, tvLoc, tvRating;
    public ViewHolder(View view) {
      super(view);
      tvName = view.findViewById(R.id.tv_workerName);
      tvLoc = view.findViewById(R.id.tv_workerLoc);
      tvRating = view.findViewById(R.id.tv_rating);
    }
  }
  @NonNull
  @Override
  public AvailableWorkersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    Context context = parent.getContext();
    LayoutInflater inflater = LayoutInflater.from(context);
    View contactView = inflater.inflate(R.layout.available_workers_row, parent, false);
    ViewHolder viewHolder = new ViewHolder(contactView);
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(@NonNull AvailableWorkersAdapter.ViewHolder holder, int position) {
    Worker worker = workers.get(position);
    TextView tvName = holder.tvName;
    TextView tvLoc = holder.tvLoc;
    TextView tvRate = holder.tvRating;
  }

  @Override
  public int getItemCount() {
    return workers.size();
  }
}
