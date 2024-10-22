package com.beauterare.rare

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class GcashDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gcash_details)

        val dMerchant = intent.getStringExtra("merchant")
        val dAmountDue = intent.getStringExtra("amountDue")
        val dNumber = intent.getStringExtra("mobileNumber")

        findViewById<TextView>(R.id.display_merchant).text = dMerchant
        findViewById<TextView>(R.id.display_amount_due).text = dAmountDue
        findViewById<TextView>(R.id.display_mobile_number).text = dNumber

        val bookButton2 = findViewById<Button>(R.id.bookBtn2)
        bookButton2.setOnClickListener {
            val intent = Intent(this, FormActivity::class.java)
            startActivity(intent)
        }
    }
}
