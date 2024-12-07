package com.beauterare.rare

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class FormActivity : AppCompatActivity() {
    private var appointmentDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paymentform)

        // Initialize UI components
        val submitButton = findViewById<Button>(R.id.submit_button)
        val customerNameInput = findViewById<EditText>(R.id.customer_name_input)
        val makeupArtistInput = findViewById<TextView>(R.id.makeup_artist_input)
        val makeupNameInput = findViewById<EditText>(R.id.makeup_name_input)
        val appointmentCalendar = findViewById<CalendarView>(R.id.appointment_calendar)
        val appointmentTimePicker = findViewById<TimePicker>(R.id.appointment_time_picker)

        makeupArtistInput.textSize = 18f
        val makeupArtist = intent.getStringExtra("makeupArtist")
        makeupArtistInput.text = makeupArtist

        // Set up CalendarView listener
        appointmentCalendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            appointmentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
        }

        // Handle submit button click
        submitButton.setOnClickListener {
            val customerName = customerNameInput.text.toString().trim()
            val makeupName = makeupNameInput.text.toString().trim()

            // Validate inputs
            if (customerName.isEmpty()) {
                customerNameInput.error = "Customer Name is required"
                customerNameInput.requestFocus()
                return@setOnClickListener
            }

            if (makeupName.isEmpty()) {
                makeupNameInput.error = "Makeup Name is required"
                makeupNameInput.requestFocus()
                return@setOnClickListener
            }

            if (makeupArtist.isNullOrEmpty()) {
                Toast.makeText(this, "Makeup Artist is missing", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (appointmentDate.isNullOrEmpty()) {
                Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val appointmentTime = SimpleDateFormat(
                "hh:mm a",
                Locale.getDefault()
            ).format(Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, appointmentTimePicker.hour)
                set(Calendar.MINUTE, appointmentTimePicker.minute)
            }.time)

            // Pass data to BookDetailsActivity
            val intent = Intent(this, BookDetailsActivity::class.java).apply {
                putExtra("customerName", customerName)
                putExtra("makeupArtist", makeupArtist)
                putExtra("makeupName", makeupName)
                putExtra("appointmentTime", appointmentTime)
                putExtra("appointmentDate", appointmentDate)
            }
            startActivity(intent)
        }
    }
}
