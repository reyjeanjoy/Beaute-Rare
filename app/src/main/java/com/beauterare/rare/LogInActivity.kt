package com.beauterare.rare

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class LogInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var loginEmail: EditText
    private lateinit var loginPassword: EditText
    private lateinit var signupTextView: TextView
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        auth = FirebaseAuth.getInstance()
        loginEmail = findViewById(R.id.SEmail)
        loginPassword = findViewById(R.id.SPass)
        loginButton = findViewById(R.id.button2)
        signupTextView = findViewById(R.id.SignUptextView)

        loginButton.setOnClickListener {
            val email = loginEmail.text.toString().trim()
            val pass = loginPassword.text.toString().trim()

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

            auth.signInWithEmailAndPassword(email, pass)
                .addOnSuccessListener {
                    Toast.makeText(this@LogInActivity, "Login Successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@LogInActivity, MainActivity::class.java))
                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this@LogInActivity, "Login Failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }

        signupTextView.setOnClickListener {
            startActivity(Intent(this@LogInActivity, SignUpActivity::class.java))
        }
    }
}
