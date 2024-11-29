package com.beauterare.rare

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.beauterare.rare.models.Appointment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class DisplayAppointmentActivity : AppCompatActivity() {

    private lateinit var appointmentListView: ListView
    private val appointments = mutableListOf<Appointment>()
    private lateinit var adapter: ArrayAdapter<Appointment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_appointment)

        appointmentListView = findViewById(R.id.appointmentListView)

        adapter = ArrayAdapter(this, R.layout.item_appointment, appointments)
        appointmentListView.adapter = adapter

        // Fetch appointments from the server
        fetchAppointments()
    }

    private fun fetchAppointments() {
        // Use Coroutine to fetch data asynchronously in the background
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Call the API to fetch appointments using RetrofitInstance
                val response: Response<List<Appointment>> = RetrofitInstance.appointmentApi.getAppointments()

                // Handle the response on the main thread
                if (response.isSuccessful) {
                    val fetchedAppointments = response.body()
                    runOnUiThread {
                        appointments.clear()
                        if (fetchedAppointments != null) {
                            appointments.addAll(fetchedAppointments)
                        }
                        // Notify the adapter that the data has been updated
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@DisplayAppointmentActivity, "Failed to fetch appointments", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@DisplayAppointmentActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
