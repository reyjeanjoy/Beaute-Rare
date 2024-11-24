package com.beauterare.rare

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LogInActivity : AppCompatActivity() {

    private lateinit var loginEmail: EditText
    private lateinit var loginPassword: EditText
    private lateinit var signupTextView: TextView
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        // Initialize UI elements
        loginEmail = findViewById(R.id.SEmail)
        loginPassword = findViewById(R.id.SPass)
        loginButton = findViewById(R.id.button2)
        signupTextView = findViewById(R.id.SignUptextView)

        // Handle login button click
        loginButton.setOnClickListener {
            val email = loginEmail.text.toString().trim()
            val pass = loginPassword.text.toString().trim()

            // Validate email and password inputs
            if (email.isEmpty()) {
                loginEmail.error = "Email cannot be empty"
                return@setOnClickListener
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                loginEmail.error = "Please enter a valid email"
                return@setOnClickListener
            }

            if (pass.isEmpty()) {
                loginPassword.error = "Password cannot be empty"
                return@setOnClickListener
            }

            // Attempt login via PHP API
            loginUser(email, pass)
        }

        // Navigate to Signup Activity
        signupTextView.setOnClickListener {
            startActivity(Intent(this@LogInActivity, SignUpActivity::class.java))
        }
    }

    // Function to handle login via Retrofit
    private fun loginUser(email: String, password: String) {
        // Create a LoginRequest object
        val loginRequest = LoginRequest(username = email, password = password)

        // Make API call to login endpoint
        RetrofitClient.instance.loginUser(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    Toast.makeText(this@LogInActivity, response.body()?.message, Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@LogInActivity, MainActivity::class.java))
                    finish()
                } else {
                    Log.d("LoginError", "Server Response: ${response.body()}")
                    Toast.makeText(this@LogInActivity, response.body()?.message ?: "Login failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("LoginError", "Network Error: ${t.message}")
                Toast.makeText(this@LogInActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

    }
}
