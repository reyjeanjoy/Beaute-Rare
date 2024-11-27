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
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signupEmail = findViewById(R.id.Email)
        signupPassword = findViewById(R.id.Password)
        signupFname = findViewById(R.id.FirstName)
        signupLname = findViewById(R.id.LastName)
        signupAddress = findViewById(R.id.Address)
        signUpButton = findViewById(R.id.SignUpButton)
        loginTextView = findViewById(R.id.LoginTextView)

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

            // Check if email already exists
            if (databaseHelper.isEmailExists(email)) {
                Toast.makeText(this, "Email already exists. Please use a different email.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Insert into SQLite after PHP validation
            val isInserted = databaseHelper.insertUser(email, password, "$fname $lname", address)
            if (isInserted) {
                Toast.makeText(this, "SignUp Successful!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LogInActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Error saving data locally.", Toast.LENGTH_SHORT).show()
            }
        }


        loginTextView.setOnClickListener {
            startActivity(Intent(this@SignUpActivity, LogInActivity::class.java))
        }
    }
}
