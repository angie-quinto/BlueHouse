package com.example.bluehousev3.client;
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.core.ui.ChartCredits;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.example.bluehousev3.R;
import com.example.bluehousev3.views.Register2;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PopularServices extends Fragment {
  private static final String TAG = "popular services error";
  private Spinner spDate;

  private ArrayAdapter<CharSequence> dateAdapter;
  private int plumber = 0, beautician = 0, carpentry = 0, computerElectronic = 0, cooking = 0, electrical = 0, gardening = 0, homeApp = 0,
     houseCleaning = 0, jetMatic = 0, laundry = 0, mechanic = 0, pestControl = 0, roofing = 0, septic = 0,upholstery = 0, count = 0;

  @RequiresApi(api = Build.VERSION_CODES.O)
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_popular_workers_client, container, false);


    spDate = view.findViewById(R.id.sp_date);
    dateAdapter = ArrayAdapter.createFromResource(getContext(), R.array.date, android.R.layout.simple_spinner_item);
    dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spDate.setAdapter(dateAdapter);


    @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    Date date = new Date();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("popularServices").child(spDate.getSelectedItem().toString());

    AnyChartView anyChartView = view.findViewById(R.id.any_chart_view);
    anyChartView.setProgressBar(view.findViewById(R.id.progressBar4));

    Pie pie = AnyChart.pie();
    pie.legend().fontSize(12);
    pie.legend().fontColor("black");
    pie.labels().position("outside");
    pie.title("As of: " + formatter.format(date));
    pie.tooltip().format("Number of Requests: {%value}");
    anyChartView.setChart(pie);
    reference.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot snapshot) {
        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
          String key = dataSnapshot.getKey();
          if (key != null) {
            String serviceType = snapshot.child(key).getValue(String.class);
            switch (Objects.requireNonNull(serviceType)) {
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
              case "Computer/Electronic Device Repair":
                computerElectronic++;
                count++;
                break;
              case "Mechanic":
                mechanic++;
                count++;
                break;
              case "Pest Control & Fumigation":
                pestControl++;
                count++;
                break;
              case "Cooking Services":
                cooking++;
                count++;
                break;
            }
          }

        }

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




      }

      @Override
      public void onCancelled(@NonNull DatabaseError error) {
        Log.d(TAG, "onCancelled: popular services error");
      }
    });



    return view;
  }
}

