package com.beauterare.rare

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity


class WalkInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_walk_in)

        // Find views
        val salons = listOf<ImageButton>(
            findViewById(R.id.salon1),
            findViewById(R.id.salon2),
            findViewById(R.id.salon3),
            findViewById(R.id.salon4)
        )
        val back = findViewById<ImageButton>(R.id.backBtn)

        // Set up click listeners for each salon button
        salons.forEach { salon ->
            salon.setOnClickListener {
                val intent = Intent(this@WalkInActivity, EMainScreenWalkin::class.java)
                startActivity(intent)
            }

            // Set up click listener for the back button
            back.setOnClickListener {
                finish()
            }
        }
    }
}