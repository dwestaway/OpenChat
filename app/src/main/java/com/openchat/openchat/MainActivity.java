package com.openchat.openchat;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText editMessage;
    private DatabaseReference mDatabase;

    private RecyclerView messageList;

    public static String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editMessage = findViewById(R.id.editMsg);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("messages");


        messageList = (RecyclerView) findViewById(R.id.messageRecieved);
        messageList.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        messageList.setLayoutManager(linearLayoutManager);


    }

    public void buttonClicked(View view) {
        final String message = editMessage.getText().toString().trim();

        if(!TextUtils.isEmpty(message))
        {
            final DatabaseReference newPost = mDatabase.push();

            newPost.child("content").setValue(message);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter <Message, MessageViewHolder> firebaseRecycler = new FirebaseRecyclerAdapter<Message, MessageViewHolder>(Message.class, R.layout.messagelayout, MessageViewHolder.class, mDatabase) {
            @Override
            protected void populateViewHolder(MessageViewHolder viewHolder, Message model, int position) {
                viewHolder.setContent(model.getContent());
            }
        };
        messageList.setAdapter(firebaseRecycler);
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {

        View view;
        public MessageViewHolder(View itemView) {
            super(itemView);

            view = itemView;
        }

        public void setContent(String content) {
            TextView chatMessage = view.findViewById(R.id.messageText);

            TextView messageTime = view.findViewById(R.id.time);

            TextView nameText = view.findViewById(R.id.nameText);

            chatMessage.setText(content);

            DateFormat df = new SimpleDateFormat("h:mm a");
            String date = df.format(Calendar.getInstance().getTime());

            messageTime.setText(date);

            nameText.setText(name);
        }
    }



}
