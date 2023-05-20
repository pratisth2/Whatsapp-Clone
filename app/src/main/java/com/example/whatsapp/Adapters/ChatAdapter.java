package com.example.whatsapp.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp.Models.MessagesModel;
import com.example.whatsapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChatAdapter  extends  RecyclerView.Adapter{
    ArrayList<MessagesModel>messageModels;
    Context context;
    String recId;
    int SENDER_VIEW_TYPE=1;
    int RECEIVER_VIEW_TYPE=2;

    public ChatAdapter(ArrayList<MessagesModel>messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
    }

    public ChatAdapter(ArrayList<MessagesModel> messageModels, Context context, String recId) {
        this.messageModels = messageModels;
        this.context = context;
        this.recId = recId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==SENDER_VIEW_TYPE)
        {
            View view= LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
            return new SenderViewVolder(view);
        }


        else
        {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_receiver, parent, false);
            return new ReceiverViewVolder(view);
        }
        }
    

    @Override
    public int getItemViewType(int position) {
       if(messageModels.get(position).getuId().equals(FirebaseAuth.getInstance().getUid()))
        return SENDER_VIEW_TYPE;
        else
            return RECEIVER_VIEW_TYPE;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

     MessagesModel messagesModel=messageModels.get(position);
     holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
         @Override
         public boolean onLongClick(View v) {
             new AlertDialog.Builder(context)
                     .setTitle("Delete")
                     .setMessage("Are you sure you want to delete the message")
                     .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {
                             FirebaseDatabase database=FirebaseDatabase.getInstance();
                             String senderRoom=FirebaseAuth.getInstance().getUid()+recId;
                             database.getReference().child("Chats").child(senderRoom)
                                     .child(messagesModel.getMessagId())
                                     .setValue(null);
                         }
                     }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {
                             dialog.dismiss();
                         }
                     }).show();
             return false;
         }
     });

     if(holder.getClass()==SenderViewVolder.class)
         ((SenderViewVolder)holder).sendermsg.setText(messagesModel.getMessage());
     else
         ((ReceiverViewVolder)holder).receivermsg.setText(messagesModel.getMessage());
    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public static class ReceiverViewVolder extends RecyclerView.ViewHolder{
        TextView receivermsg, receivertime;
        public ReceiverViewVolder(@NonNull View itemView) {
            super(itemView);
            receivermsg=itemView.findViewById(R.id.ReceiverText);
            receivertime=itemView.findViewById(R.id.ReceiverTime);
        }
    }
public  class SenderViewVolder extends RecyclerView.ViewHolder{
    TextView sendermsg, sendertime;
    public SenderViewVolder(@NonNull View itemView) {
        super(itemView);
        sendermsg=itemView.findViewById(R.id.senderText);
        sendertime=itemView.findViewById(R.id.senderTime);

    }
}

}
