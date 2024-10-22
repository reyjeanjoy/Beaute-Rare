package com.beauterare.rare

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class AppointmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paymentform)

        val checkButton = findViewById<Button>(R.id.submit_button)
        checkButton?.setOnClickListener {
            val intent = Intent(this, DisplayAppointmentActivity::class.java)
            startActivity(intent)
        }
    }
}
