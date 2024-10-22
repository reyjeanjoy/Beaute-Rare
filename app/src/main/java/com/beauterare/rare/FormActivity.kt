package com.beauterare.rare

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class FormActivity : AppCompatActivity() {
    private var appointmentDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paymentform)

        val submitButton = findViewById<Button>(R.id.submit_button)
        val customerNameInput = findViewById<EditText>(R.id.customer_name_input)
        val makeupNameInput = findViewById<EditText>(R.id.makeup_name_input)
        val appointmentTimePicker = findViewById<TimePicker>(R.id.appointment_time_picker)
        val appointmentCalendar = findViewById<CalendarView>(R.id.appointment_calendar)

        appointmentCalendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            appointmentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
        }

        submitButton.setOnClickListener {
            val customerName = customerNameInput.text.toString().trim()
            val makeupName = makeupNameInput.text.toString().trim()
            val appointmentTime = "${appointmentTimePicker.hour}:${appointmentTimePicker.minute}"

            // Validation
            if (customerName.isEmpty() || makeupName.isEmpty() || appointmentDate.isNullOrEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, DisplayAppointmentActivity::class.java).apply {
                putExtra("customerName", customerName)
                putExtra("makeupName", makeupName)
                putExtra("appointmentTime", appointmentTime)
                putExtra("appointmentDate", appointmentDate)
            }

            startActivity(intent)
        }
    }
}
