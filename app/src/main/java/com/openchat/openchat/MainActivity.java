package com.openchat.openchat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText editMessage;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editMessage = findViewById(R.id.editMsg);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("messages");

    }

    public void buttonClicked(View view) {
        final String message = editMessage.getText().toString().trim();

        if(!TextUtils.isEmpty(message))
        {
            final DatabaseReference newPost = mDatabase.push();

            newPost.child("content").setValue(message);
        }
    }

}
