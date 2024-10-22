package com.beauterare.rare

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class ChooseArtistActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choose_list_item)

        val buttonViewPortfolio1 = findViewById<Button>(R.id.buttonViewPortfolio1)
        val buttonBook01 = findViewById<Button>(R.id.buttonBook1)
        val buttonViewPortfolio2 = findViewById<Button>(R.id.buttonViewPortfolio2)
        val buttonBook02 = findViewById<Button>(R.id.buttonBook2)
        val buttonViewPortfolio3 = findViewById<Button>(R.id.buttonViewPortfolio3)
        val buttonBook03 = findViewById<Button>(R.id.buttonBook3)
        val buttonViewPortfolio4 = findViewById<Button>(R.id.buttonViewPortfolio4)
        val buttonBook04 = findViewById<Button>(R.id.buttonBook4)
        val buttonViewPortfolio5 = findViewById<Button>(R.id.buttonViewPortfolio5)
        val buttonBook05 = findViewById<Button>(R.id.buttonBook5)
        val buttonViewPortfolio6 = findViewById<Button>(R.id.buttonViewPortfolio6)
        val buttonBook06 = findViewById<Button>(R.id.buttonBook6)

        buttonViewPortfolio1.setOnClickListener {
            startActivity(Intent(this, BretProfActivity::class.java))
        }
        buttonBook01.setOnClickListener {
            startActivity(Intent(this, PaymentActivity::class.java))
        }
        buttonViewPortfolio2.setOnClickListener {
            startActivity(Intent(this, AngeProfActivity::class.java))
        }
        buttonBook02.setOnClickListener {
            startActivity(Intent(this, PaymentActivity::class.java))
        }
        buttonViewPortfolio3.setOnClickListener {
            startActivity(Intent(this, PaulProfActivity::class.java))
        }
        buttonBook03.setOnClickListener {
            startActivity(Intent(this, PaymentActivity::class.java))
        }
        buttonViewPortfolio4.setOnClickListener {
            startActivity(Intent(this, JaniProfActivity::class.java))
        }
        buttonBook04.setOnClickListener {
            startActivity(Intent(this, PaymentActivity::class.java))
        }
        buttonViewPortfolio5.setOnClickListener {
            startActivity(Intent(this, JelProfActivity::class.java))
        }
        buttonBook05.setOnClickListener {
            startActivity(Intent(this, PaymentActivity::class.java))
        }
        buttonViewPortfolio6.setOnClickListener {
            startActivity(Intent(this, AntProfActivity::class.java))
        }
        buttonBook06.setOnClickListener {
            startActivity(Intent(this, PaymentActivity::class.java))
        }
    }
}

