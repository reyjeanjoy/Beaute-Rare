package com.beauterare.rare

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.beauterare.rare.models.LoginRequest
import com.beauterare.rare.models.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LogInActivity : AppCompatActivity() {

    private lateinit var loginEmail: EditText
    private lateinit var loginPassword: EditText
    private lateinit var loginButton: Button
    private lateinit var signupTextView: TextView
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        // Initialize views
        loginEmail = findViewById(R.id.SEmail)
        loginPassword = findViewById(R.id.SPass)
        loginButton = findViewById(R.id.button2)
        signupTextView = findViewById(R.id.SignUptextView)
        databaseHelper = DatabaseHelper(this)

        // Login button click listener
        loginButton.setOnClickListener {
            val email = loginEmail.text.toString().trim()
            val password = loginPassword.text.toString().trim()

            // Validate input
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email and Password cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Prepare data for PHP validation
            val loginRequest = LoginRequest(email, password)

            // Call PHP to validate input
            RetrofitInstance.apiService.login(loginRequest).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful && response.body()?.success == true) {
                        // Validate locally using SQLite
                        if (databaseHelper.validateUser(email, password)) {
                            // Save user session in SharedPreferences
                            saveUserSession(email)

                            // Show success message and navigate to MainActivity
                            Toast.makeText(this@LogInActivity, "Login Successful", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@LogInActivity, MainActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this@LogInActivity, "Invalid credentials.", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@LogInActivity, response.body()?.message ?: "Login Failed", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@LogInActivity, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        // Sign-up text click listener
        signupTextView.setOnClickListener {
            startActivity(Intent(this@LogInActivity, SignUpActivity::class.java))
        }
    }

    // Save user session
    private fun saveUserSession(email: String) {
        val sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("userEmail", email) // Save the logged-in user's email
        editor.apply()

        // Debug log to confirm session saving
        Toast.makeText(this, "Session Saved for $email", Toast.LENGTH_SHORT).show()
    }
}
