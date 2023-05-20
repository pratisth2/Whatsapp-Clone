package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.whatsapp.Adapters.ChatAdapter;
import com.example.whatsapp.Models.MessagesModel;
import com.example.whatsapp.databinding.ActivityGroupChatBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class GroupChatActivity extends AppCompatActivity {
          ActivityGroupChatBinding binding;
          FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityGroupChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();


        binding.LeftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GroupChatActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });
        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        final ArrayList<MessagesModel>messagesModels=new ArrayList<>();
        final String senderId= FirebaseAuth.getInstance().getUid();
        binding.userName.setText("Friends's Group");

                final ChatAdapter adapter=new ChatAdapter(messagesModels,this);
                binding.ChatRecyclierView .setAdapter(adapter);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.ChatRecyclierView.setLayoutManager(layoutManager);


          database.getReference().child("Group Chat").child(senderId)
                          .addValueEventListener(new ValueEventListener() {
                              @Override
                              public void onDataChange(@NonNull DataSnapshot snapshot) {
                                  messagesModels.clear();
                                  for(DataSnapshot datasnapshot:snapshot.getChildren()){
                                      MessagesModel model=datasnapshot.getValue(MessagesModel.class);
                                      messagesModels.add(model);
                                  }
                                  adapter.notifyDataSetChanged();

                              }

                              @Override
                              public void onCancelled(@NonNull DatabaseError error) {

                              }
                          });

        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String message=binding.etMessage.getText().toString();
                final MessagesModel model=new MessagesModel(senderId,message);
                model.setTimestamp(new Date().getTime());
                binding.etMessage.setText("");

                database.getReference().child("Group Chat").child(senderId)
                        .push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });
            }
        });
    }
}