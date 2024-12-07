package com.beauterare.rare

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.beauterare.rare.models.SignUpRequest
import com.beauterare.rare.models.SignUpResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    private lateinit var signupEmail: EditText
    private lateinit var signupPassword: EditText
    private lateinit var signupFname: EditText
    private lateinit var signupLname: EditText
    private lateinit var signupAddress: EditText
    private lateinit var signUpButton: Button
    private lateinit var loginTextView: TextView
    private lateinit var databaseHelper: DatabaseHelper // DatabaseHelper instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Initialize views
        signupEmail = findViewById(R.id.Email)
        signupPassword = findViewById(R.id.Password)
        signupFname = findViewById(R.id.FirstName)
        signupLname = findViewById(R.id.LastName)
        signupAddress = findViewById(R.id.Address)
        signUpButton = findViewById(R.id.SignUpButton)
        loginTextView = findViewById(R.id.LoginTextView)

        // Initialize DatabaseHelper
        databaseHelper = DatabaseHelper(this)

        signUpButton.setOnClickListener {
            val email = signupEmail.text.toString().trim()
            val password = signupPassword.text.toString().trim()
            val fname = signupFname.text.toString().trim()
            val lname = signupLname.text.toString().trim()
            val address = signupAddress.text.toString().trim()

            // Validate input
            if (email.isEmpty() || password.isEmpty() || fname.isEmpty() || lname.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check if the email already exists in SQLite
            if (databaseHelper.isEmailExists(email)) {
                Toast.makeText(this, "Email already exists. Please use a different email.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Insert user into SQLite database
            val name = "$fname $lname"
            val isInserted = databaseHelper.insertUser(email, password, name, address)

            if (isInserted) {
                // Save the user's email in SharedPreferences for future use
                val sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("userEmail", email) // Save the user's email
                editor.apply()

                Toast.makeText(this, "Sign-up successful! Welcome, $name", Toast.LENGTH_SHORT).show()

                // Navigate to login or another activity
                startActivity(Intent(this, LogInActivity::class.java))
                finish()
            }

        }

        loginTextView.setOnClickListener {
            startActivity(Intent(this, LogInActivity::class.java))
        }
    }
}
