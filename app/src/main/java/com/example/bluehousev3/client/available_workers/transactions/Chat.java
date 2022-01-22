package com.example.bluehousev3.client.available_workers.transactions;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bluehousev3.R;
import com.example.bluehousev3.adapters.ChatAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Chat extends Fragment {
    private ArrayList<String> chatList;
    private String clientName;
    private static final String TAG = "Chat client";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        String path = getArguments().getString("PATH");
        String uid = getArguments().getString("CLIENT_ID");
        EditText edtMessage = view.findViewById(R.id.edt_chat);
        Button btnSend = view.findViewById(R.id.btn_send_chat);
        TextView tvChatWith = view.findViewById(R.id.tv_chat_name);
        RecyclerView rv = view.findViewById(R.id.rv_chat);
        chatList = new ArrayList<>();
        String workerNAME = getArguments().getString("WORKER_NAME");
        tvChatWith.setText("Chatting with: " + workerNAME);

        DatabaseReference clientRef = FirebaseDatabase.getInstance().getReference().child("users/employers").child(uid);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(path);
        DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference().child(path).child("chats");
        ChatAdapter adapter = new ChatAdapter(chatList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        rv.setLayoutManager(layoutManager);

        rv.setAdapter(adapter);
        chatRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                chatList.add(snapshot.getValue(String.class));
                rv.smoothScrollToPosition(chatList.size());
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "onCancelled: error retrieving messages");
            }
        });

        clientRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clientName = snapshot.child("fullName").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = edtMessage.getText().toString();
                reference.child("chats").child(String.valueOf(System.currentTimeMillis())).setValue(clientName + ": " + message).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        edtMessage.getText().clear();
                    }
                });

            }
        });

        return view;
    }
}