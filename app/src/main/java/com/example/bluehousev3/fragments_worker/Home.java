package com.example.bluehousev3.fragments_worker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bluehousev3.R;
import com.example.bluehousev3.controller.JobPostAdapter;
import com.example.bluehousev3.model.ClientJobPosts;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Home extends Fragment {

  ArrayList<ClientJobPosts> serviceRequests;
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view =  inflater.inflate(R.layout.fragment_home, container, false);
    RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_serviceReq);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    serviceRequests = ClientJobPosts.jobPostList(20);
    JobPostAdapter adapter = new JobPostAdapter(serviceRequests);
    recyclerView.setAdapter(adapter);
    recyclerView.setItemAnimator(new DefaultItemAnimator());

    return view;
  }
}

