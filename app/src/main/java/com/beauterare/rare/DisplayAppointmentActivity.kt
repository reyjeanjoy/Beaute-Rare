package com.beauterare.rare

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.beauterare.rare.models.Appointment

class DisplayAppointmentActivity : AppCompatActivity() {

    private lateinit var appointmentListView: ListView
    private val appointments = mutableListOf<Appointment>()
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var adapter: AppointmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_appointment)

        appointmentListView = findViewById(R.id.appointmentListView)
        databaseHelper = DatabaseHelper(this)

        // Fetch appointments and initialize the adapter
        fetchAppointments()
    }

    private fun fetchAppointments() {
        // Fetch appointments from SQLite
        appointments.clear()
        appointments.addAll(databaseHelper.getAppointments()) // Ensure `getAppointments` fetches the list from SQLite

        if (appointments.isNotEmpty()) {
            adapter = AppointmentAdapter(this, appointments, databaseHelper)
            appointmentListView.adapter = adapter
        } else {
            Toast.makeText(this, "No appointments found.", Toast.LENGTH_SHORT).show()
        }
    }
}
