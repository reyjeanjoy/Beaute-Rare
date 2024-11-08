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
    private lateinit var submitButton: Button
    private lateinit var customerNameInput: EditText
    private lateinit var makeupNameInput: EditText
    private lateinit var appointmentTimePicker: TimePicker
    private lateinit var appointmentCalendar: CalendarView
    private var appointmentDate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paymentform)

        initViews()
        setupMakeupNameInput()
        setupAppointmentCalendar()

        submitButton.setOnClickListener { onSubmit() }
    }

    private fun initViews() {
        submitButton = findViewById(R.id.submit_button)
        customerNameInput = findViewById(R.id.customer_name_input)
        makeupNameInput = findViewById(R.id.makeup_name_input)
        appointmentTimePicker = findViewById(R.id.appointment_time_picker)
        appointmentCalendar = findViewById(R.id.appointment_calendar)
    }

    private fun setupMakeupNameInput() {
        val makeupName = intent.getStringExtra("makeupName")
        makeupNameInput.apply {
            setText(makeupName)
            isFocusable = false
            isClickable = false
            isCursorVisible = false
            isLongClickable = false
        }
    }

    private fun setupAppointmentCalendar() {
        appointmentCalendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance().apply {
                set(year, month, dayOfMonth)
            }
            appointmentDate = formatDate(calendar.time)
        }
    }

    private fun formatDate(date: Date): String =
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)

    private fun onSubmit() {
        val customerName = customerNameInput.text.toString().trim()
        val appointmentTime = "${appointmentTimePicker.hour}:${appointmentTimePicker.minute}"

        if (customerName.isEmpty() || appointmentDate.isNullOrEmpty()) {
            showToast("Please fill out all fields")
            return
        }

        navigateToBookDetails(customerName, appointmentTime)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToBookDetails(customerName: String, appointmentTime: String) {
        val intent = Intent(this, BookDetailsActivity::class.java).apply {
            putExtra("customerName", customerName)
            putExtra("makeupName", makeupNameInput.text.toString())
            putExtra("appointmentTime", appointmentTime)
            putExtra("appointmentDate", appointmentDate)
        }
        startActivity(intent)
    }
}
