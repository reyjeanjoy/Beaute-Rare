package com.beauterare.rare

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BookDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_details)

        // Retrieve appointment details from intent
        val customerName = intent.getStringExtra("customerName") ?: "N/A"
        val makeupName = intent.getStringExtra("makeupName") ?: "N/A"
        val appointmentTime = intent.getStringExtra("appointmentTime") ?: "N/A"
        val appointmentDate = intent.getStringExtra("appointmentDate") ?: "N/A"

        // Set text views with received details
        val customerNameTextView = findViewById<TextView>(R.id.customer_name)
        val makeupNameTextView = findViewById<TextView>(R.id.makeup_details)
        val appointmentTimeTextView = findViewById<TextView>(R.id.appointment_time)
        val appointmentDateTextView = findViewById<TextView>(R.id.appointment_date)

        customerNameTextView.text = "Customer Name: $customerName"
        makeupNameTextView.text = "Makeup Name: $makeupName"
        appointmentTimeTextView.text = "Appointment Time: $appointmentTime"
        appointmentDateTextView.text = "Appointment Date: $appointmentDate"

        // Button to proceed to PaymentActivity
        val proceedPaymentButton = findViewById<Button>(R.id.proceedPaymentBtn)
        proceedPaymentButton.setOnClickListener {
            val intent = Intent(this, PaymentActivity::class.java).apply {
                putExtra("customerName", customerName)
                putExtra("makeupName", makeupName)
                putExtra("appointmentTime", appointmentTime)
                putExtra("appointmentDate", appointmentDate)
            }
            startActivity(intent)
        }
    }
}
