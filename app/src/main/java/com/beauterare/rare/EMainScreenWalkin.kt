package com.beauterare.rare

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class EMainScreenWalkin : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.evening_list_item_walkin)

        val bookButton1 = findViewById<Button>(R.id.buttonBook11)
        val bookButton2 = findViewById<Button>(R.id.buttonBook12)
        val bookButton3 = findViewById<Button>(R.id.buttonBook13)
        val bookButton4 = findViewById<Button>(R.id.buttonBook14)
        val bookButton5 = findViewById<Button>(R.id.buttonBook15)
        val bookButton6 = findViewById<Button>(R.id.buttonBook16)
        val bookButton7 = findViewById<Button>(R.id.buttonBook17)

        bookButton1.setOnClickListener {
            openFormActivity("Glittery Makeup")
        }
        bookButton2.setOnClickListener {
            openFormActivity("Classic Makeup")
        }
        bookButton3.setOnClickListener {
            openFormActivity("Soft Glam Makeup")
        }
        bookButton4.setOnClickListener {
            openFormActivity("Cut Crease Makeup")
        }
        bookButton5.setOnClickListener {
            openFormActivity("Sultry Makeup")
        }
        bookButton6.setOnClickListener {
            openFormActivity("Smokey Makeup")
        }
        bookButton7.setOnClickListener {
            openFormActivity("Simple Makeup")
        }
    }

    private fun openFormActivity(makeupName: String) {
        val intent = Intent(this, ChooseArtistActivity::class.java).apply {
            putExtra("makeupName", makeupName)
        }
        startActivity(intent)
    }
}
