package com.beauterare.rare

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.beauterare.rare.models.Appointment

class AppointmentAdapter(
    private val context: Context,
    private val appointments: MutableList<Appointment>,
    private val databaseHelper: DatabaseHelper
) : android.widget.BaseAdapter() {

    override fun getCount(): Int {
        return appointments.size
    }

    override fun getItem(position: Int): Any {
        return appointments[position]
    }

    override fun getItemId(position: Int): Long {
        return appointments[position].id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_appointment, parent, false)

        // Get the appointment at the current position
        val appointment = appointments[position]

        // Set up the views
        val customerName = view.findViewById<TextView>(R.id.customer_name)
        val makeupDetails = view.findViewById<TextView>(R.id.makeup_details)
        val makeupArtist = view.findViewById<TextView>(R.id.makeup_artist)
        val appointmentTime = view.findViewById<TextView>(R.id.appointment_time)
        val appointmentDate = view.findViewById<TextView>(R.id.appointment_date)
        val deleteButton = view.findViewById<Button>(R.id.deleteButton)

        // Populate the views with data
        customerName.text = appointment.customerName
        makeupDetails.text = appointment.makeupName
        makeupArtist.text = appointment.makeupArtist
        appointmentTime.text = appointment.appointmentTime
        appointmentDate.text = appointment.appointmentDate

        // Handle delete button click
        deleteButton.setOnClickListener {
            showDeleteConfirmationDialog(appointment, position)
        }

        return view
    }

    private fun showDeleteConfirmationDialog(appointment: Appointment, position: Int) {
        android.app.AlertDialog.Builder(context)
            .setTitle("Delete Appointment")
            .setMessage("Are you sure you want to delete this appointment?")
            .setPositiveButton("Yes") { dialog, _ ->
                // Perform deletion
                deleteAppointment(appointment, position)
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun deleteAppointment(appointment: Appointment, position: Int) {
        val isDeleted = databaseHelper.deleteAppointment(appointment.id)
        if (isDeleted) {
            appointments.removeAt(position)
            notifyDataSetChanged()
            Toast.makeText(context, "Appointment deleted successfully", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Failed to delete appointment", Toast.LENGTH_SHORT).show()
        }
    }
}
