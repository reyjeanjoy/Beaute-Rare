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

        bookButton1.setOnClickListener{
            val intent = Intent(this,PaymentActivity::class.java)
            startActivity(intent)
        }
        bookButton2.setOnClickListener{
            val intent = Intent(this,PaymentActivity::class.java)
            startActivity(intent)
        }
        bookButton3.setOnClickListener{
            val intent = Intent(this,PaymentActivity::class.java)
            startActivity(intent)
        }
        bookButton4.setOnClickListener{
            val intent = Intent(this,PaymentActivity::class.java)
            startActivity(intent)
        }
        bookButton5.setOnClickListener{
            val intent = Intent(this,PaymentActivity::class.java)
            startActivity(intent)
        }
        bookButton6.setOnClickListener{
            val intent = Intent(this,PaymentActivity::class.java)
            startActivity(intent)
        }
        bookButton7.setOnClickListener{
            val intent = Intent(this,PaymentActivity::class.java)
            startActivity(intent)
        }

    }
}