package com.openchat.openchat;

import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    private EditText editMessage;
    private DatabaseReference mDatabase;

    private RecyclerView messageList;

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

            chatMessage.setText(content);
        }
    }

}
