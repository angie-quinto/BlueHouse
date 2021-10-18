package com.example.bluehousev3.controller;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bluehousev3.R;
import com.example.bluehousev3.model.Services;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> {
  private List<Services> mServices;
  int size = 0;
  public ServicesAdapter(List<Services> services) {
    mServices = services;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    Context context = parent.getContext();
    LayoutInflater inflater = LayoutInflater.from(context);
    View serviceView = inflater.inflate(R.layout.row_client_home, parent, false);
    ViewHolder viewHolder = new ViewHolder(serviceView);
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Services services = mServices.get(position);

    // todo: get data from database
     TextView tv, tv2;
     tv = holder.tvServiceTitle;
     tv2 = holder.tvWorkerCount;
    ImageButton ib = holder.btnImg;
    tv.setText(services.getServiceName());
    tv2.setText(String.valueOf(size));
  }

  @Override
  public int getItemCount() {
    int limit = 19;
    if (mServices.size() > limit) {
      return limit;
    } else
    return mServices.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    ImageButton btnImg;
    TextView tvServiceTitle, tvWorkerCount;
    public ViewHolder(View v) {
      super(v);
      btnImg = v.findViewById(R.id.ib_service);
      tvServiceTitle = v.findViewById(R.id.tv_serviceTitle);
      tvWorkerCount = v.findViewById(R.id.tv_workerCount);
    }
  }
}
