package com.example.bluehousev3.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.bluehousev3.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class RateDialog extends DialogFragment {
  private RatingBar ratingBar;
  private Button btnCancel, btnRate;
  private String userTypeRating;
  private String uid;
  private String userType;
  private static final String TAG = "Rate Dialog";
  private String path;

  public RateDialog(String path, String userTypeRating, String uid, String userTypeWithAnSforDatabase) {
    this.userTypeRating = userTypeRating;
    this.uid = uid;
    this.userType = userTypeWithAnSforDatabase;
    this.path = path;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.rate_dialog_layout, container, false);

      ratingBar = view.findViewById(R.id.ratingBar);
      btnRate = view.findViewById(R.id.btn_rate_dialog);
      btnCancel = view.findViewById(R.id.btn_cancel_rate_dialog);



    btnRate.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        float rating = ratingBar.getRating();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(userTypeRating).child(uid);
        reference.child(String.valueOf(System.currentTimeMillis())).setValue(String.valueOf(rating)).addOnSuccessListener(new OnSuccessListener<Void>() {
          @Override
          public void onSuccess(Void unused) {
            Toast.makeText(getActivity(), "Successfully rated user", Toast.LENGTH_SHORT).show();

            reference.addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot snapshot) {
                // calculates the user rating
                if (snapshot.exists()) {
                  ArrayList<Float> ratings = new ArrayList<>();
                  for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    ratings.add(Float.parseFloat(snapshot1.getValue(String.class)));
                  }
                  float sum = 0f;
                  for (int k = 0; k < ratings.size(); k++) {
                    sum += ratings.get(k);
                  }
                  double total = (double) sum / ratings.size();
                  double roundedT = round(total, 1);
                  DatabaseReference rRef = FirebaseDatabase.getInstance().getReference().child("users").child(userType).child(uid).child("rating");
                  rRef.setValue(String.valueOf(roundedT)).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                      Log.d(TAG, "onSuccess: successfully rated employer");
                      // mark the emp as rated so that the rate emp button will be disabled after successfully rated the emp.
                      if (userTypeRating.equals("employerRatings")) {
                          DatabaseReference ratedRef = FirebaseDatabase.getInstance().getReference().child(path);
                          ratedRef.child("isEmployerRated").setValue("true");
                      }
                    }
                  });
                }
              }

              @Override
              public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled: Rate Dialog Error");
              }
            });



            getDialog().dismiss();
          }
        });
      }
    });

    btnCancel.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Objects.requireNonNull(getDialog()).dismiss();
      }
    });



    return view;
  }

  private static double round (double value, int precision) {
    int scale = (int) Math.pow(10, precision);
    return (double) Math.round(value * scale) / scale;
  }
}
