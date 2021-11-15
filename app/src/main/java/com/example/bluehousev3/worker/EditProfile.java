package com.example.bluehousev3.worker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bluehousev3.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class EditProfile extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile_worker, container, false);
        EditText edtName = view.findViewById(R.id.edt_edit_name);
        EditText edtAddress = view.findViewById(R.id.edt_edit_address);
        EditText edtRate = view.findViewById(R.id.edt_edit_rate);
        EditText edtMobileNum = view.findViewById(R.id.edt_edit_mobile);
        Button btnSave = view.findViewById(R.id.btn_edit_save);
        Button btnCancel = view.findViewById(R.id.btn_edit_cancel);
        String path = getArguments() != null ? getArguments().getString("PROFILE_PATH") : null;
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(path);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                edtName.setText(snapshot.child("fullName").getValue(String.class));
                edtAddress.setText(snapshot.child("address").getValue(String.class));
                edtRate.setText(snapshot.child("rate").getValue(String.class));
                edtMobileNum.setText(snapshot.child("phoneNumber").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment profile = new Profile();
                FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container_worker, profile);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                    .setTitle("Save Changes")
                    .setMessage("Are you sure you want to save these changes?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            reference.child("fullName").setValue(edtName.getText().toString());
                            reference.child("address").setValue(edtAddress.getText().toString());
                            reference.child("phoneNumber").setValue(edtMobileNum.getText().toString());
                            reference.child("rate").setValue(edtRate.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getActivity(), "Changes Saved", Toast.LENGTH_SHORT).show();
                                    Fragment profile = new Profile();
                                    FragmentTransaction ft = getParentFragmentManager().beginTransaction();
                                    ft.replace(R.id.fragment_container_worker, profile);
                                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                    ft.addToBackStack(null);
                                    ft.commit();
                                }
                            });
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

            }
        });
        return view;
    }
}