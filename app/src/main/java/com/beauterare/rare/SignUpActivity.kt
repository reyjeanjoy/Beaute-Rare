package com.beauterare.rare

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var signupEmail: EditText
    private lateinit var signupPassword: EditText
    private lateinit var signUpButton: Button
    private lateinit var loginTextView: TextView
    private lateinit var signupFname:EditText
    private lateinit var signupLname:EditText
    private lateinit var signupaddress:EditText
    private lateinit var fStore:FirebaseFirestore
    private lateinit var userID:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        signupEmail = findViewById(R.id.Email)
        signupPassword = findViewById(R.id.Password)
        signUpButton = findViewById(R.id.SignUpButton)
        loginTextView = findViewById(R.id.LoginTextView)
        signupFname = findViewById(R.id.FirstName)
        signupLname = findViewById(R.id.LastName)
        signupaddress = findViewById(R.id.Address)

        signUpButton.setOnClickListener {
            val user = signupEmail.text.toString().trim()
            val pass = signupPassword.text.toString().trim()
            val fname = signupFname.text.toString().trim()
            val lname = signupLname.text.toString().trim()
            val address = signupaddress.text.toString().trim()

            if (user.isEmpty()) {
                signupEmail.error = "Email cannot be empty"
                return@setOnClickListener
            }
            if (pass.isEmpty()) {
                signupPassword.error = "Password cannot be empty"
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@SignUpActivity, "SignUp Successful", Toast.LENGTH_SHORT).show();
                    val userID = auth.currentUser?.uid
                    val documentReference = fStore.collection("users").document(userID!!)
                    val user = hashMapOf(
                        "fname" to fname,
                        "lname" to lname,
                        "email" to user,
                        "pass" to pass,
                        "address" to address
                    )
                    documentReference.set(user).addOnSuccessListener {
                        Log.d(TAG, "onSuccess: User Profile is created for $userID")
                    }.addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }


                    startActivity(Intent(this@SignUpActivity, LogInActivity::class.java))
                }
                else {
                    Toast.makeText(
                        this@SignUpActivity,
                        "SignUp Failed" + task.exception?.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        loginTextView.setOnClickListener {
            startActivity(Intent(this@SignUpActivity, LogInActivity::class.java))
        }
    }
}
