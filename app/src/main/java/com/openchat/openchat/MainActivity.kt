package com.openchat.openchat

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private var editMessage: EditText? = null
    private var username: EditText? = null

    private var mDatabase: DatabaseReference? = null

    private var messageList: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editMessage = findViewById(R.id.editMsg)
        username = findViewById(R.id.username)

        mDatabase = FirebaseDatabase.getInstance().reference.child("messages")


        messageList = findViewById<View>(R.id.messageRecieved) as RecyclerView
        messageList!!.setHasFixedSize(true)

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.stackFromEnd = true
        messageList!!.layoutManager = linearLayoutManager

    }

    fun buttonClicked(view: View) {
        val message = editMessage!!.text.toString().trim { it <= ' ' }
        val name = username!!.text.toString().trim { it <= ' ' }

        if (!TextUtils.isEmpty(message)) {
            if (!TextUtils.isEmpty(name)) {
                val newPost = mDatabase!!.push()

                newPost.child("content").setValue(message)
                newPost.child("name").setValue(name)

                //get current time in time and day
                val df = SimpleDateFormat("hh:mm a dd.MM.yyyy ")
                val currentTime = df.format(Calendar.getInstance().time)

                newPost.child("time").setValue(currentTime)

                //Close keyboard when message is sent
                val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

                //Clear textbox when message is sent
                //editMessage.setText("")

            } else {
                Toast.makeText(applicationContext, "Please enter a username", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(applicationContext, "Please enter a message", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onStart() {
        super.onStart()

        val firebaseRecycler = object : FirebaseRecyclerAdapter<Message, MessageViewHolder>(Message::class.java, R.layout.messagelayout, MessageViewHolder::class.java, mDatabase) {
            override fun populateViewHolder(viewHolder: MessageViewHolder, model: Message, position: Int) {
                viewHolder.setContent(model.content)
                viewHolder.setName(model.name)
                viewHolder.setTime(model.time)
            }
        }
        messageList!!.adapter = firebaseRecycler
    }

    class MessageViewHolder(private var view: View) : RecyclerView.ViewHolder(view) {

        fun setContent(content: String?) {
            val chatMessage = view.findViewById<TextView>(R.id.messageText)

            chatMessage.text = content
        }

        fun setName(name: String?) {
            val textName = view.findViewById<TextView>(R.id.nameText)

            textName.text = name
        }

        fun setTime(time: String?) {
            val textTime = view.findViewById<TextView>(R.id.time)

            textTime.text = time
        }
    }

}
