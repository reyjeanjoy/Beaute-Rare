package com.beauterare.rare

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PaymentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val creditCardTextView = findViewById<TextView>(R.id.creditCardTextView)
        val cashTextView = findViewById<TextView>(R.id.cashTextView)


        creditCardTextView.setOnClickListener {
            navigateToCreditCardScreen()
        }

        cashTextView.setOnClickListener {
            navigateToCashScreen()
        }
    }


    private fun navigateToCreditCardScreen() {
        val intent = Intent(this, CreditCardActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToCashScreen() {
        val intent = Intent(this, CashActivity::class.java)
        startActivity(intent)
    }
}
