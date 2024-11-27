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

            // Check if email already exists in SQLite
            if (databaseHelper.isEmailExists(email)) {
                Toast.makeText(this, "Email already exists. Please use a different email.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Prepare data for API request
            val signUpRequest = SignUpRequest(fname, lname, email, password, address)

            // Call the backend API
            RetrofitInstance.apiService.signUp(signUpRequest).enqueue(object : Callback<SignUpResponse> {
                override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                    if (response.isSuccessful && response.body()?.success == true) {
                        // Insert into SQLite after successful backend validation
                        val isInserted = databaseHelper.insertUser(email, password, "$fname $lname", address)
                        if (isInserted) {
                            Toast.makeText(this@SignUpActivity, "SignUp Successful!", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@SignUpActivity, LogInActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this@SignUpActivity, "Error saving data locally.", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@SignUpActivity, response.body()?.message ?: "Signup Failed", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                    Toast.makeText(this@SignUpActivity, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        loginTextView.setOnClickListener {
            startActivity(Intent(this@SignUpActivity, LogInActivity::class.java))
        }
    }
}
