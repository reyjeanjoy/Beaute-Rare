package com.beauterare.rare

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CreditCardDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credit_card_details)

        val cNumber = intent.getStringExtra("creditCardNumber")
        val cName = intent.getStringExtra("creditCardName")
        val expiryDate = intent.getStringExtra("expiryDate")
        val cvv = intent.getStringExtra("cvv")
        val cTotalAmount = intent.getStringExtra("totalAmount")

        findViewById<TextView>(R.id.cardNumberValueTextView).text = cNumber
        findViewById<TextView>(R.id.cardHolderNameValueTextView).text = cName
        findViewById<TextView>(R.id.expiryDateValueTextView).text = expiryDate
        findViewById<TextView>(R.id.cvvValueTextView).text = cvv
        findViewById<TextView>(R.id.ctotalAmountValueTextView).text = cTotalAmount

        val bookButton = findViewById<Button>(R.id.bookBtn1)
        bookButton.setOnClickListener {
            val intent = Intent(this, ConfirmationActivity::class.java)
            startActivity(intent)
        }
    }
}
