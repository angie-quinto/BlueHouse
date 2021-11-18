package com.example.bluehousev3.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bluehousev3.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {
    private ArrayList<String> serviceList;
    private OnServiceListener onServiceListener;
    private ArrayList<Integer> images;

    public ServiceAdapter(ArrayList<String> serviceList, OnServiceListener onServiceListener, ArrayList<Integer> images) {
        this.serviceList = serviceList;
        this.onServiceListener = onServiceListener;
        this.images = images;

    }

    @NonNull
    @Override
    public ServiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View serviceView = inflater.inflate(R.layout.row_service, parent, false);
        ViewHolder holder = new ViewHolder(serviceView, onServiceListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceAdapter.ViewHolder holder, int position) {
         holder.tvServiceTitle.setText(serviceList.get(position));
         holder.iv.setImageResource(images.get(position));
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvServiceTitle;
        ImageView iv;
        OnServiceListener onServiceListener;

        public ViewHolder(@NonNull View itemView, OnServiceListener onServiceListener) {
            super(itemView);
            tvServiceTitle = itemView.findViewById(R.id.tv_serviceTitle);
            iv = itemView.findViewById(R.id.iv_service);
            this.onServiceListener = onServiceListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onServiceListener.onServiceClick(getBindingAdapterPosition()); // getAdapterPosition()
        }
    }

    public interface OnServiceListener {
        void onServiceClick(int position);
    }
}
