package com.example.bluehousev3.fragments_client;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bluehousev3.R;
import com.example.bluehousev3.controller.JobPostAdapter;
import com.example.bluehousev3.controller.ServicesAdapter;
import com.example.bluehousev3.model.Services;

import java.util.ArrayList;


public class Home extends Fragment {

  ArrayList<Services> services;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view =  inflater.inflate(R.layout.fragment_home_client, container, false);
    RecyclerView recyclerView = view.findViewById(R.id.rv_clientHome);

    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    services = Services.serviceList(20);
    ServicesAdapter adapter = new ServicesAdapter(services);
    recyclerView.setAdapter(adapter);
    recyclerView.setItemAnimator(new DefaultItemAnimator());

    return view;
  }
}