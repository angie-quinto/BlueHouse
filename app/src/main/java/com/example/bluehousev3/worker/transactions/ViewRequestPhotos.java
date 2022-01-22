package com.example.bluehousev3.worker.transactions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.bluehousev3.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ViewRequestPhotos extends DialogFragment {

    private TextView tvClose;
    private ImageView ivPhoto1, ivPhoto2;
    private String image1, image2;

    public ViewRequestPhotos(String image, String image1) {
        this.image1 = image;
        this.image2 = image1;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_view_request_photos, container, false);
        ivPhoto1 = view.findViewById(R.id.iv_req_photo1);
        ivPhoto2 = view.findViewById(R.id.iv_req_photo2);

        tvClose = view.findViewById(R.id.tv_close);

        if (image1 != null && image2 != null) {
            Picasso.get().load(image1).resize(350,350).into(ivPhoto1);
            Picasso.get().load(image2).resize(350,350).into(ivPhoto2);
        }

        tvClose.setOnClickListener(view1 -> getDialog().dismiss());

        return view;
    }
}
