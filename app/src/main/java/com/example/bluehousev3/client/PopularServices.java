package com.example.bluehousev3.client;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import java.util.ArrayList;
import java.util.List;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.core.ui.ChartCredits;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.example.bluehousev3.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class PopularServices extends Fragment {

  private int plumber = 0, beautician = 0, carpentry = 0, computerElectronic = 0, cooking = 0, delivery = 0, electrical = 0, gardening = 0, homeApp = 0,
     houseCleaning = 0, jetMatic = 0, laundry = 0, mechanic = 0, pestControl = 0, roofing = 0, septic = 0, sewerage = 0,upholstery = 0, waterPipe = 0;
//  private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10;
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_popular_workers_client, container, false);



    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("popularServices");







    Pie pie = AnyChart.pie();
    reference.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
          String key = dataSnapshot.getKey();
          if (key != null) {
            switch (snapshot.child(key).getValue(String.class)) {
              case "Plumbing/Water Pipe Maintenance":
                plumber++;
                break;
              case "Carpentry":
                carpentry++;
                break;
              case "JetMatic Pump Maintenance":
                jetMatic++;
                break;
              case "Upholstery":
                upholstery++;
                break;
              case "Septic Tank Maintenance":
                septic++;
                break;
              case "Gardening":
                gardening++;
                break;
              case "Home Appliance Maintenance":
                homeApp++;
                break;
              case "Roof Maintenance":
                roofing++;
                break;
              case "Housekeeping":
                houseCleaning++;
                break;
              case "Laundry Services":
                laundry++;
                break;
              case "Beauty Salon Services":
                beautician++;
                break;
              case "Electrical Maintenance":
                electrical++;
                break;
              case "Computer/Electronic Repair":
                computerElectronic++;
                break;
              case "Mechanic":
                mechanic++;
                break;
              case "Pest Control & Fumigation":
                pestControl++;
                break;
              case "Cooking Services":
                cooking++;
                break;
            }

          }

        }


//        pie.background("#A8D8EA");
        pie.tooltip().format("Number of Requests: {%value}");
        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Plumbing/Water Pipe Maintenance", plumber));
        data.add(new ValueDataEntry("Beauty Salon Services", beautician));
        data.add(new ValueDataEntry("Computer/Electronic Repair", computerElectronic));
        data.add(new ValueDataEntry("Cooking Services", cooking));
        data.add(new ValueDataEntry("Electrical Maintenance", electrical));
        data.add(new ValueDataEntry("Carpentry", carpentry));
        data.add(new ValueDataEntry("Gardening", gardening));
        data.add(new ValueDataEntry("Home Appliance Maintenance", homeApp));
        data.add(new ValueDataEntry("Housekeeping", houseCleaning));
        data.add(new ValueDataEntry("JetMatic Pump Maintenance", jetMatic));
        data.add(new ValueDataEntry("Mechanic", mechanic));
        data.add(new ValueDataEntry("Laundry Services", laundry));
        data.add(new ValueDataEntry("Pest Control & Fumigation", pestControl));
        data.add(new ValueDataEntry("Roof Maintenance", roofing));
        data.add(new ValueDataEntry("Septic Tank Maintenance", septic));
        data.add(new ValueDataEntry("Upholstery", upholstery));
        pie.data(data);
        AnyChartView anyChartView = view.findViewById(R.id.any_chart_view);
        pie.labels().position("outside");
//        pie.title("Popular Services");

        anyChartView.setChart(pie);

      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {

      }
    });



    return view;
  }
}

