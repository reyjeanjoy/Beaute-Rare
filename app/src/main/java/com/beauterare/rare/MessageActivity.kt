package com.beauterare.rare

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MessageActivity : AppCompatActivity() {

    private lateinit var dbHelper: MessageDatabaseHelper
    private lateinit var messagesAdapter: MessagesAdapter
    private lateinit var messagesList: MutableList<Message>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val messageInput: EditText = findViewById(R.id.messageInput)
        val sendButton: ImageButton = findViewById(R.id.sendButton)

        // Initialize database helper and load messages
        dbHelper = MessageDatabaseHelper(this)
        messagesList = dbHelper.getAllMessages().toMutableList()

        // Setup RecyclerView
        messagesAdapter = MessagesAdapter(messagesList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = messagesAdapter

        // Handle sending messages
        sendButton.setOnClickListener {
            val inputText = messageInput.text.toString().trim()
            if (inputText.isNotEmpty()) {
                val newMessage = Message(content = inputText, isSent = true)
                val id = dbHelper.addMessage(newMessage)
                if (id != -1L) {
                    newMessage.id = id // Update ID from database
                    messagesList.add(newMessage)
                    messagesAdapter.notifyItemInserted(messagesList.size - 1)
                    recyclerView.scrollToPosition(messagesList.size - 1)
                    messageInput.text.clear()
                }
            }
        }
    }
}
