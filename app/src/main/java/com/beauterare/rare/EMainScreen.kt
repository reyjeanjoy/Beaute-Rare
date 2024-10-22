package com.beauterare.rare

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class EMainScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.evening_listview)

        val back = findViewById<ImageButton>(R.id.backBtn)

        back.setOnClickListener {
           finish()
        }
        val makeupItems = listOf(
            EMakeupItem(R.drawable.ev1, "Glittery Makeup", "3500"),
            EMakeupItem(R.drawable.ev2, "Classic Makeup", "3000"),
            EMakeupItem(R.drawable.ev3, "Soft Glam Makeup", "3000"),
            EMakeupItem(R.drawable.ev4, "Cut Crease Makeup", "5000"),
            EMakeupItem(R.drawable.ev5, "Sultry Makeup", "4500"),
            EMakeupItem(R.drawable.ev6, "Smokey Makeup", "4000"),
            EMakeupItem(R.drawable.ev7, "Simple Makeup", "2500"),
        )

        val adapter = EAdapter(this, makeupItems)
        val listView = findViewById<ListView>(R.id.listView)

        listView?.adapter = adapter
    }
}