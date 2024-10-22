package com.beauterare.rare

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val people = listOf(
            Contacts("Anthena Buenco"),
            Contacts("Paul Unating"),
            Contacts("Jelly Eugenio"),
            Contacts("Bretman Rock"),
            Contacts("Adelyne Gipgano"),
            Contacts("Reyjean Lumapac"),
            Contacts("Sarah Johnson"),
            Contacts("Michael Chang"),
            Contacts("Emily Patel")
        )

        val adapter = ContactsAdapter(people)
        recyclerView.adapter = adapter
    }
}