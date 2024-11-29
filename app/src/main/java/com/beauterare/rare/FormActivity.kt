package com.beauterare.rare


    import android.content.Intent
    import android.os.Bundle
    import android.widget.Button
    import android.widget.CalendarView
    import android.widget.EditText
    import android.widget.TextView
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
            val makeupArtistInput = findViewById<TextView>(R.id.makeup_artist_input)
            makeupArtistInput.textSize = 18f

            val makeupArtist = intent.getStringExtra("makeupArtist")
            makeupArtistInput.text = makeupArtist

            // Add makeupName input field (EditText)
            val makeupNameInput = findViewById<EditText>(R.id.makeup_name_input) // New EditText field for makeup name

            val appointmentCalendar = findViewById<CalendarView>(R.id.appointment_calendar)
            appointmentCalendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
                val calendar = Calendar.getInstance()
                calendar.set(year, month, dayOfMonth)
                appointmentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
            }

            submitButton.setOnClickListener {
                val customerName = customerNameInput.text.toString().trim()
                val makeupName = makeupNameInput.text.toString().trim() // Get makeup name input

                if (customerName.isEmpty() || makeupArtist.isNullOrEmpty() || appointmentDate.isNullOrEmpty() || makeupName.isEmpty()) {
                    Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val appointmentTime = findViewById<TimePicker>(R.id.appointment_time_picker).run {
                    "${hour}:${minute}"
                }

                val intent = Intent(this, BookDetailsActivity::class.java).apply {
                    putExtra("customerName", customerName)
                    putExtra("makeupArtist", makeupArtist)
                    putExtra("makeupName", makeupName) // Pass makeup name to next activity
                    putExtra("appointmentTime", appointmentTime)
                    putExtra("appointmentDate", appointmentDate)
                }
                startActivity(intent)

        val submitButton = findViewById<Button>(R.id.submit_button)
        val customerNameInput = findViewById<EditText>(R.id.customer_name_input)
        val makeupArtistInput = findViewById<TextView>(R.id.makeup_artist_input)
        makeupArtistInput.textSize = 18f

        val makeupArtist = intent.getStringExtra("makeupArtist")
        makeupArtistInput.text = makeupArtist

        // Add makeupName input field (EditText)
        val makeupNameInput = findViewById<EditText>(R.id.makeup_name_input) // New EditText field for makeup name

        val appointmentCalendar = findViewById<CalendarView>(R.id.appointment_calendar)
        appointmentCalendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            appointmentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
        }

        submitButton.setOnClickListener {
            val customerName = customerNameInput.text.toString().trim()
            val makeupName = makeupNameInput.text.toString().trim() // Get makeup name input

            if (customerName.isEmpty() || makeupArtist.isNullOrEmpty() || appointmentDate.isNullOrEmpty() || makeupName.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val appointmentTime = findViewById<TimePicker>(R.id.appointment_time_picker).run {
                "${hour}:${minute}"
            }

            val intent = Intent(this, BookDetailsActivity::class.java).apply {
                putExtra("customerName", customerName)
                putExtra("makeupArtist", makeupArtist)
                putExtra("makeupName", makeupName) // Pass makeup name to next activity
                putExtra("appointmentTime", appointmentTime)
                putExtra("appointmentDate", appointmentDate)
            }
            startActivity(intent)
        }

    }

    }
}

