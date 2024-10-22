package com.beauterare.rare

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomeSerPrice : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_ser_price)

        val nxtButton = findViewById<Button>(R.id.nextbtn)
        nxtButton.setOnClickListener {
            val intent = Intent(this@HomeSerPrice, ChooseArtistActivity::class.java)
            startActivity(intent)
        }
    }
}