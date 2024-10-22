package com.beauterare.rare

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class DisplayAppointmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_appointment)

        val customerName = intent.getStringExtra("customerName")
        val makeupName = intent.getStringExtra("makeupName")
        val appointmentTime = intent.getStringExtra("appointmentTime")
        val appointmentDate = intent.getStringExtra("appointmentDate")

        Log.d("DisplayAppointmentActivity", "Received customerName: $customerName")
        Log.d("DisplayAppointmentActivity", "Received makeupName: $makeupName")
        Log.d("DisplayAppointmentActivity", "Received appointmentTime: $appointmentTime")
        Log.d("DisplayAppointmentActivity", "Received appointmentDate: $appointmentDate")

        val customerNameTextView = findViewById<TextView>(R.id.customer_name)
        val makeupNameTextView = findViewById<TextView>(R.id.makeup_details)
        val appointmentTimeTextView = findViewById<TextView>(R.id.appointment_time)
        val appointmentDateTextView = findViewById<TextView>(R.id.appointment_date)

        if (customerName.isNullOrEmpty() || makeupName.isNullOrEmpty() || appointmentTime.isNullOrEmpty() || appointmentDate.isNullOrEmpty()) {
            customerNameTextView.text = "No appointment"
            makeupNameTextView.text = ""
            appointmentTimeTextView.text = ""
            appointmentDateTextView.text = ""
        } else {
            customerNameTextView.text = "Customer Name: $customerName"
            makeupNameTextView.text = "Makeup Name: $makeupName"

            // Splitting appointment time into hours and minutes
            val parsedTime = appointmentTime.split(":")
            val hour = parsedTime[0].toInt()
            val minute = parsedTime[1].toInt()

            val amOrPm = if (hour < 12) "AM" else "PM"
            val displayHour = if (hour % 12 == 0) 12 else hour % 12

            val formattedTime = String.format(Locale.getDefault(), "%d:%02d %s", displayHour, minute, amOrPm)

            appointmentTimeTextView.text = "Appointment Time: $formattedTime"
            appointmentDateTextView.text = "Appointment Date: $appointmentDate"
        }

        val backtn = findViewById<Button>(R.id.backToMainBtn)
        backtn.setOnClickListener {
            val intent = Intent(this@DisplayAppointmentActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
