package com.example.bluehousev3.client;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bluehousev3.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class PopularServices extends Fragment {


  private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10;
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_popular_workers_client, container, false);


    tv1 = view.findViewById(R.id.tv1);
    tv2 = view.findViewById(R.id.tv2);
    tv3 = view.findViewById(R.id.tv3);
    tv4 = view.findViewById(R.id.tv4);
    tv5 = view.findViewById(R.id.tv5);
    tv6 = view.findViewById(R.id.tv6);
    tv7 = view.findViewById(R.id.tv7);
    tv8 = view.findViewById(R.id.tv8);
    tv9 = view.findViewById(R.id.tv9);
    tv10 = view.findViewById(R.id.tv10);





    return view;
  }
}