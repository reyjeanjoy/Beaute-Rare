package com.beauterare.rare

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Initialize UI elements
        signupEmail = findViewById(R.id.Email)
        signupPassword = findViewById(R.id.Password)
        signupFname = findViewById(R.id.FirstName)
        signupLname = findViewById(R.id.LastName)
        signupAddress = findViewById(R.id.Address)
        signUpButton = findViewById(R.id.SignUpButton)
        loginTextView = findViewById(R.id.LoginTextView)
        progressBar = findViewById(R.id.progressBar) // Add a ProgressBar in the layout

        // Handle sign-up button click
        signUpButton.setOnClickListener {
            val email = signupEmail.text.toString().trim()
            val password = signupPassword.text.toString().trim()
            val firstName = signupFname.text.toString().trim()
            val lastName = signupLname.text.toString().trim()
            val address = signupAddress.text.toString().trim()

            // Validate inputs
            if (!validateInputs(firstName, lastName, email, password, address)) return@setOnClickListener

            // Show ProgressBar and disable the button
            progressBar.visibility = View.VISIBLE
            signUpButton.isEnabled = false

            // Prepare API request
            val signUpRequest = SignUpRequest(firstName, lastName, email, password, address)

            // Make API call using Retrofit
            RetrofitClient.instance.registerUser(signUpRequest).enqueue(object : Callback<SignUpResponse> {
                override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                    // Hide ProgressBar and re-enable the button
                    progressBar.visibility = View.GONE
                    signUpButton.isEnabled = true

                    if (response.isSuccessful && response.body() != null) {
                        val responseBody = response.body()
                        if (responseBody?.success == true) {
                            Toast.makeText(this@SignUpActivity, responseBody.message, Toast.LENGTH_SHORT).show()
                            // Navigate to LoginActivity
                            startActivity(Intent(this@SignUpActivity, LogInActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this@SignUpActivity, responseBody?.message ?: "Sign-up failed.", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Log.d("SignUpActivity", "Unexpected response: ${response.errorBody()?.string()}")
                        Toast.makeText(this@SignUpActivity, "Sign-up failed. Please try again.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                    // Hide ProgressBar and re-enable the button
                    progressBar.visibility = View.GONE
                    signUpButton.isEnabled = true

                    Log.e("SignUpActivity", "Network error: ${t.message}")
                    Toast.makeText(this@SignUpActivity, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        // Navigate to LoginActivity
        loginTextView.setOnClickListener {
            startActivity(Intent(this@SignUpActivity, LogInActivity::class.java))
        }
    }

    // Function to validate inputs
    private fun validateInputs(firstName: String, lastName: String, email: String, password: String, address: String): Boolean {
        if (firstName.isEmpty()) {
            signupFname.error = "First name cannot be empty"
            return false
        }
        if (lastName.isEmpty()) {
            signupLname.error = "Last name cannot be empty"
            return false
        }
        if (email.isEmpty()) {
            signupEmail.error = "Email cannot be empty"
            return false
        }
        if (password.isEmpty()) {
            signupPassword.error = "Password cannot be empty"
            return false
        }
        if (address.isEmpty()) {
            signupAddress.error = "Address cannot be empty"
            return false
        }
        return true
    }
}
