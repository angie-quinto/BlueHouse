package com.example.bluehousev3.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.bluehousev3.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReviewDialog extends DialogFragment {
  private Button btnCancel, btnSubmit;
  private TextInputEditText titReview;
  private String  workerId;

  public ReviewDialog(String workerId) {
    this.workerId = workerId;

  }
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.review_layout, container, false);
    btnSubmit = view.findViewById(R.id.btn_review_submit);
    btnCancel = view.findViewById(R.id.btn_review_cancel);
    titReview = view.findViewById(R.id.tit_review);

    btnCancel.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getDialog().dismiss();
      }
    });

    btnSubmit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        DatabaseReference reviewRef = FirebaseDatabase.getInstance().getReference().child("users/workerReviews").child(workerId);
        String review = titReview.getText().toString();
        reviewRef.child(String.valueOf(System.currentTimeMillis())).setValue(review).addOnSuccessListener(new OnSuccessListener<Void>() {
          @Override
          public void onSuccess(Void unused) {
            Toast.makeText(getActivity(), "Your review has been submitted", Toast.LENGTH_SHORT).show();
            getDialog().dismiss();
          }
        });

      }
    });

    return view;
  }
}
