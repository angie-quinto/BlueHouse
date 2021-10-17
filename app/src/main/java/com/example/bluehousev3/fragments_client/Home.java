package com.example.bluehousev3.fragments_client;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bluehousev3.R;
import com.example.bluehousev3.controller.RecyclerItemClickListener;
import com.example.bluehousev3.controller.ServicesAdapter;
import com.example.bluehousev3.model.Services;

import java.util.ArrayList;

public class Home extends Fragment {
  ArrayList<Services> services;
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view =  inflater.inflate(R.layout.fragment_home_client, container, false);
    RecyclerView recyclerView = view.findViewById(R.id.rv_clientHome);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    services = Services.serviceList();
    ServicesAdapter adapter = new ServicesAdapter(services);
    recyclerView.setAdapter(adapter);
    recyclerView.setItemAnimator(new DefaultItemAnimator());

    recyclerView.addOnItemTouchListener(
        new RecyclerItemClickListener(getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
          @Override public void onItemClick(View view, int position) {
            // do whatever
            Fragment availableWorkers = new AvailableWorkers();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container_client, availableWorkers);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();
          }

          @Override public void onLongItemClick(View view, int position) {
            // do whatever
          }
        })
    );
    return view;
  }
}