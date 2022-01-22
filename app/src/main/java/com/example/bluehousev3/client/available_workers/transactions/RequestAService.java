package com.example.bluehousev3.client.available_workers.transactions;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;


import com.example.bluehousev3.R;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class RequestAService extends Fragment {
    private Button btnNext;
    private Switch swSetCurrAdd;
    private Spinner spinnerServiceType;
    private ArrayList<String> servicesOffered;
    private TextInputEditText tietDescription, tietDateOfAppointment, tietTime, tietLocation;

    Bundle bundle;
    private ArrayAdapter<String> arrayAdapter;
    private final Calendar myCalendar = Calendar.getInstance();
    private String workerId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request_a_service, container, false);

        workerId = getArguments().getString("Wid");
        tietDescription = view.findViewById(R.id.tiet_description);
        tietDateOfAppointment = view.findViewById(R.id.tiet_date_of_appointment);
        tietTime = view.findViewById(R.id.tiet_time_of_appointment);
        tietLocation = view.findViewById(R.id.tiet_location);
        swSetCurrAdd = view.findViewById(R.id.sw_curr_addr);
        btnNext = view.findViewById(R.id.btn_next);
        spinnerServiceType = view.findViewById(R.id.spnnr_serviceType);
        bundle = new Bundle();
        servicesOffered = new ArrayList<>();

        DatabaseReference workerServicesOfferedRef = FirebaseDatabase.getInstance().getReference().child("users/workerServicesOffered").child(workerId);
        workerServicesOfferedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    servicesOffered.add(dataSnapshot.getValue(String.class));
                }
                arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, servicesOffered);
                arrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                arrayAdapter.notifyDataSetChanged();
                spinnerServiceType.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatePickerDialog.OnDateSetListener startDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelForStart();
            }

        };

        tietDateOfAppointment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), startDate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });


      tietTime.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              openTimeFragment(tietTime);
          }
      });

      swSetCurrAdd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                String uid = user.getUid();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users/employers").child(uid);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        tietLocation.setText(snapshot.child("address").getValue(String.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToBundle();

            }
        });

        return view;
    }

    private void addToBundle() {
        String serviceType, description, dateOfAppointment, time, location;
        serviceType = spinnerServiceType.getSelectedItem().toString();
        dateOfAppointment = tietDateOfAppointment.getText().toString().trim();
        description = tietDescription.getText().toString().trim();
        time = tietTime.getText().toString().trim();
        location = tietLocation.getText().toString().trim();

        if (description.isEmpty()) {
            tietDescription.setError("please provide a description for your service request");
            tietDescription.requestFocus();
            return;
        }
        if (dateOfAppointment.isEmpty()) {
            tietDateOfAppointment.setError("please provide a date");
            tietDateOfAppointment.requestFocus();
            return;
        }
        if (time.isEmpty()) {
            tietTime.setError("please provide a time");
            tietTime.requestFocus();
            return;
        }
        if (location.isEmpty()) {
            tietLocation.setError("please provide a location");
            tietLocation.requestFocus();
            return;
        }

        bundle.putString("serviceType", serviceType);
        bundle.putString("description", description);
        bundle.putString("startDate", dateOfAppointment);
        bundle.putString("startTime", time);
        bundle.putString("location", location);
        bundle.putString("ID", workerId);


        Fragment req2 = new RequestAService2();
        req2.setArguments(bundle);
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container_client, req2);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();
    }

private void updateLabelForStart() {
    String myFormat = "E, dd/MM/yy";
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
    tietDateOfAppointment.setText(sdf.format(myCalendar.getTime()));

}

private void openTimeFragment(EditText edt) {
        TimePickerDialog mTimePicker;

        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {



                String time = selectedHour + ":" + selectedMinute;

                SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
                Date date = null;
                try {
                    date = fmt.parse(time );
                } catch (ParseException e) {

                    e.printStackTrace();
                }

                SimpleDateFormat fmtOut = new SimpleDateFormat("hh:mm aa");

                String formattedTime=fmtOut.format(date);

                edt.setText(formattedTime);
            }
        }, hour, minute, false);
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }




}