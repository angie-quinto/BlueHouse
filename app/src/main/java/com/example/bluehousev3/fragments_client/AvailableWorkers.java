package com.example.bluehousev3.fragments_client;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bluehousev3.R;
import com.example.bluehousev3.controller.AvailableWorkersAdapter;
import com.example.bluehousev3.controller.RecyclerItemClickListener;
import com.example.bluehousev3.model.Worker;

import java.util.ArrayList;

public class AvailableWorkers extends Fragment {
  ArrayList<Worker> workers;
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view =  inflater.inflate(R.layout.fragment_availble_workers, container, false);

    RecyclerView rvAvailableWorkers = view.findViewById(R.id.rv_availableWorkers);
    workers = Worker.createWorkersList(20);
    AvailableWorkersAdapter adapter = new AvailableWorkersAdapter(workers);
    rvAvailableWorkers.setAdapter(adapter);
    rvAvailableWorkers.setLayoutManager(new LinearLayoutManager(getActivity()));

    rvAvailableWorkers.addOnItemTouchListener(new RecyclerItemClickListener(getContext(),
        rvAvailableWorkers, new RecyclerItemClickListener.OnItemClickListener() {
      @Override
      public void onItemClick(View view, int position) {

        Fragment serviceRequestForm = new ServiceRequestForm();
        assert getFragmentManager() != null;
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container_client, serviceRequestForm);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();
      }

      @Override
      public void onLongItemClick(View view, int position) {

      }
    }));
    return view;
  }
}