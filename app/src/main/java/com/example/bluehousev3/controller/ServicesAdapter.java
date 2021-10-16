package com.example.bluehousev3.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bluehousev3.R;
import com.example.bluehousev3.model.Services;

import org.w3c.dom.Text;

import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> {
  private List<Services> mServices;
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

   // TextView textView = holder.nameTextView;
   // textView.setText(contact.getName());
   // Button button = holder.messageButton;
   // button.setText(contact.isOnline() ? "Message" : "Offline");
   // button.setEnabled(contact.isOnline());
    // todo: get data from database
    TextView tv = holder.tvServiceTitle;
    TextView tv2 = holder.tvWorkerCount;
    ImageButton ib = holder.btnImg;

  }

  @Override
  public int getItemCount() {
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
