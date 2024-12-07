package com.beauterare.rare

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

        // Initialize data from intent
        initDataFromIntent()

        // Set up the text views
        setUpTextViews()

        // Set up the Proceed to Confirmation button
        setUpProceedToConfirmationButton()
    }

    // Initialize data passed through the intent
    private fun initDataFromIntent() {
        customerName = intent.getStringExtra("customerName") ?: "N/A"
        makeupArtist = intent.getStringExtra("makeupArtist") ?: "N/A"
        makeupName = intent.getStringExtra("makeupName") ?: "N/A"
        appointmentTime = intent.getStringExtra("appointmentTime") ?: "N/A"
        appointmentDate = intent.getStringExtra("appointmentDate") ?: "N/A"
    }

    // Set up the text views to display the appointment details
    private fun setUpTextViews() {
        val formattedTime = formatTimeToAmPm(appointmentTime)

        findViewById<TextView>(R.id.customer_name).text = "Customer Name: $customerName"
        findViewById<TextView>(R.id.makeup_details).text = "Makeup Artist: $makeupArtist"
        findViewById<TextView>(R.id.makeup_name).text = "Makeup Name: $makeupName"
        findViewById<TextView>(R.id.appointment_time).text = "Appointment Time: $formattedTime"
        findViewById<TextView>(R.id.appointment_date).text = "Appointment Date: $appointmentDate"
    }

    // Set up the button to proceed to the confirmation screen
    private fun setUpProceedToConfirmationButton() {
        val proceedButton: Button = findViewById(R.id.proceedBtn) // Make sure the ID matches in your layout
        proceedButton.setOnClickListener {
            // Call the addAppointment function when the button is clicked
            addAppointment()
        }
    }

    // Function to add an appointment (makes a network call)
    private fun addAppointment() {
        val databaseHelper = DatabaseHelper(this)

        val success = databaseHelper.insertAppointment(
            customerName,
            makeupName,
            makeupArtist,
            appointmentTime,
            appointmentDate
        )

        if (success) {
            Toast.makeText(this, "Appointment Added!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, ConfirmationActivity::class.java))
        } else {
            Toast.makeText(this, "Failed to add appointment!", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to format the time from 24-hour to 12-hour AM/PM format
    private fun formatTimeToAmPm(time: String): String {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault()) // Input format (24-hour format)
        val output = SimpleDateFormat("hh:mm a", Locale.getDefault()) // Output format (12-hour format with AM/PM)

        return try {
            val date = sdf.parse(time)
            output.format(date ?: Date()) // Return formatted time, defaulting to the current date if parsing fails
        } catch (e: Exception) {
            e.printStackTrace()
            time // Return the original string if parsing fails
        }
    }
}

