package com.example.bluehousev3.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bluehousev3.R;
import com.example.bluehousev3.model.ClientJobPosts;

import java.util.List;

public class JobPostAdapter extends RecyclerView.Adapter<JobPostAdapter.ViewHolder> {

  private List<ClientJobPosts> mServiceRequests;

  public JobPostAdapter(List<ClientJobPosts> serviceRequests) {
    mServiceRequests = serviceRequests;
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    public TextView tvClientName, tvClientLoc, tvServiceReq, tvDesc;
    public Button btnAccept;
    public ImageView ivClientPic, ivJobPic1, ivJobPic2;

    public ViewHolder(View itemView) {
      super(itemView);
      tvClientName = itemView.findViewById(R.id.tv_clientname);
      tvClientLoc = itemView.findViewById(R.id.tv_clientLoc);
      tvServiceReq = itemView.findViewById(R.id.tv_serviceReq);
      tvDesc = itemView.findViewById(R.id.tv_serviceDesc);
      btnAccept = itemView.findViewById(R.id.btn_accept);
      ivClientPic = itemView.findViewById(R.id.iv_clientPic);
      ivJobPic1 = itemView.findViewById(R.id.iv_jobpic1);
      ivJobPic2 = itemView.findViewById(R.id.iv_jobpic2);

    }
  }

  @NonNull
  @Override
  public JobPostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    Context context = parent.getContext();
    LayoutInflater inflater = LayoutInflater.from(context);
    View serviceRequestsView = inflater.inflate(R.layout.job_post_row, parent, false);
    return new ViewHolder(serviceRequestsView);
  }

  @Override
  public void onBindViewHolder(@NonNull JobPostAdapter.ViewHolder holder, int position) {
    ClientJobPosts serviceReq = mServiceRequests.get(position);

    TextView tvName = holder.tvClientName;
    TextView tvLoc = holder.tvClientLoc;
    TextView tvDesc = holder.tvDesc;
    TextView tvServReq = holder.tvServiceReq;
    Button btnAccept = holder.btnAccept;
    ImageView image1 = holder.ivClientPic;
    ImageView image2 = holder.ivJobPic1;
    ImageView image3 = holder.ivJobPic2;
  }

  @Override
  public int getItemCount() {
    return mServiceRequests.size();
  }
}
