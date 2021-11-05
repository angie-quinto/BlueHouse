package com.example.bluehousev3.client;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.bluehousev3.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.BatchUpdateException;

public class RequestAServiceType extends DialogFragment {
    private EditText edtRequest;
    private Button btnSubmit, btnCancel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_request_a_new_type_of_service, container, false);

        edtRequest = view.findViewById(R.id.edt_new_request);
        btnSubmit = view.findViewById(R.id.btn_submit_request);
        btnCancel = view.findViewById(R.id.btn_cancel_req);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    String request = edtRequest.getText().toString();
                    if (!request.isEmpty()) {
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("usersRequest");
                        databaseReference.child(String.valueOf(System.currentTimeMillis())).setValue(request).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getActivity(), "Request Sent...", Toast.LENGTH_SHORT).show();
                                getDialog().dismiss();
                            }
                        });
                    }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });


        return view;
    }
}
