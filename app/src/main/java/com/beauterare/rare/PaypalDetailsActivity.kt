package com.beauterare.rare

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class PaypalDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paypal_details)

        val amount = intent.getStringExtra("amount")
        val recipientName = intent.getStringExtra("recipientName")
        val accountNumber = intent.getStringExtra("accountNumber")

        val amountTextView: TextView = findViewById(R.id.pAmountTextView)
        amountTextView.text = amount

        val recipientNameTextView: TextView = findViewById(R.id.recipientNameTextView)
        recipientNameTextView.text = recipientName

        val accountNumberTextView: TextView = findViewById(R.id.pAccountNumberTextView)
        accountNumberTextView.text = accountNumber

        val bookButton3 = findViewById<Button>(R.id.bookBtn3)
        bookButton3.setOnClickListener {
            val intent = Intent(this, FormActivity::class.java)
            startActivity(intent)
        }
    }
}
