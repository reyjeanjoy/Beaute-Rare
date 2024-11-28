package com.beauterare.rare

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class BookDetailsActivity : AppCompatActivity() {

    private lateinit var customerName: String
    private lateinit var makeupArtist: String
    private lateinit var makeupName: String
    private lateinit var appointmentTime: String
    private lateinit var appointmentDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_details)

        // Initialize values from intent
        initDataFromIntent()

        // Set the TextViews
        setUpTextViews()

        // Set up the proceed payment button
        setUpProceedPaymentButton()
    }

    private fun initDataFromIntent() {
        customerName = intent.getStringExtra("customerName") ?: "N/A"
        makeupArtist = intent.getStringExtra("makeupArtist") ?: "N/A"
        makeupName = intent.getStringExtra("makeupName") ?: "N/A"
        appointmentTime = intent.getStringExtra("appointmentTime") ?: "N/A"
        appointmentDate = intent.getStringExtra("appointmentDate") ?: "N/A"
    }

    private fun setUpTextViews() {
        val formattedTime = formatTimeToAmPm(appointmentTime)

        findViewById<TextView>(R.id.customer_name).text = "Customer Name: $customerName"
        findViewById<TextView>(R.id.makeup_details).text = "Makeup Artist: $makeupArtist"
        findViewById<TextView>(R.id.makeup_name).text = "Makeup Name: $makeupName"
        findViewById<TextView>(R.id.appointment_time).text = "Appointment Time: $formattedTime"
        findViewById<TextView>(R.id.appointment_date).text = "Appointment Date: $appointmentDate"
    }

    private fun setUpProceedPaymentButton() {
        findViewById<Button>(R.id.proceedPaymentBtn).setOnClickListener {
            val intent = Intent(this, ConfirmationActivity::class.java).apply {
                putExtra("customerName", customerName)
                putExtra("makeupArtist", makeupArtist)
                putExtra("makeupName", makeupName)
                putExtra("appointmentTime", appointmentTime)
                putExtra("appointmentDate", appointmentDate)
            }
            startActivity(intent)
        }
    }

    private fun formatTimeToAmPm(time: String): String {
        return try {
            val format = SimpleDateFormat("HH:mm", Locale.getDefault())
            val date = format.parse(time)
            val amPmFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
            amPmFormat.format(date)
        } catch (e: Exception) {
            time // return original if parsing fails
        }
    }
}