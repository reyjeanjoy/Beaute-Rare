package com.beauterare.rare

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

class Profile : Fragment() {

    private lateinit var firstNameText: EditText
    private lateinit var lastNameText: EditText
    private lateinit var emailText: EditText
    private lateinit var addressText: EditText
    private lateinit var logoutButton: Button
    private lateinit var editButton: Button
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var sharedPreferences: android.content.SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Initialize views
        firstNameText = view.findViewById(R.id.Firstname)
        lastNameText = view.findViewById(R.id.Lastname)
        emailText = view.findViewById(R.id.Email)
        addressText = view.findViewById(R.id.Address)
        logoutButton = view.findViewById(R.id.logoutButton)
        editButton = view.findViewById(R.id.editButton)
        saveButton = view.findViewById(R.id.buttonsave)
        cancelButton = view.findViewById(R.id.buttoncancel)

        // Initialize DatabaseHelper and SharedPreferences
        databaseHelper = DatabaseHelper(requireContext())
        sharedPreferences = requireActivity().getSharedPreferences("UserSession", Context.MODE_PRIVATE)

        // Fetch and display user profile
        fetchUserProfile()

        editButton.setOnClickListener {
            toggleEditMode(true)
        }

        saveButton.setOnClickListener {
            saveProfileChanges()
        }

        cancelButton.setOnClickListener {
            toggleEditMode(false)
            fetchUserProfile()
        }

        logoutButton.setOnClickListener {
            logoutUser()
        }

        return view
    }

    private fun fetchUserProfile() {
        // Get the logged-in user's email from SharedPreferences
        val userEmail = sharedPreferences.getString("userEmail", null)

        if (userEmail != null) {
            // Fetch user details from SQLite database
            val user = databaseHelper.getUserByEmail(userEmail)

            if (user != null) {
                // Update UI with user details
                firstNameText.setText(user.fname)
                lastNameText.setText(user.lname)
                emailText.setText(user.email)
                addressText.setText(user.address)

                // Disable editing by default
                toggleEditMode(false)
            } else {
                Toast.makeText(requireContext(), "User data not found.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "No user session found. Please log in.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun toggleEditMode(enable: Boolean) {
        firstNameText.isEnabled = enable
        lastNameText.isEnabled = enable
        emailText.isEnabled = false // Keep email non-editable
        addressText.isEnabled = enable

        saveButton.visibility = if (enable) View.VISIBLE else View.GONE
        cancelButton.visibility = if (enable) View.VISIBLE else View.GONE
        editButton.visibility = if (!enable) View.VISIBLE else View.GONE
    }

    private fun saveProfileChanges() {
        val userEmail = sharedPreferences.getString("userEmail", null)
        if (userEmail != null) {
            val updatedFirstName = firstNameText.text.toString().trim()
            val updatedLastName = lastNameText.text.toString().trim()
            val updatedAddress = addressText.text.toString().trim()

            if (updatedFirstName.isEmpty() || updatedLastName.isEmpty() || updatedAddress.isEmpty()) {
                Toast.makeText(requireContext(), "All fields are required.", Toast.LENGTH_SHORT).show()
                return
            }

            // Update user in SQLite database
            val isUpdated = databaseHelper.updateUserProfile(userEmail, updatedFirstName, updatedLastName, updatedAddress)

            if (isUpdated) {
                Toast.makeText(requireContext(), "Profile updated successfully.", Toast.LENGTH_SHORT).show()
                toggleEditMode(false)
            } else {
                Toast.makeText(requireContext(), "Error updating profile.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun logoutUser() {
        // Clear user session
        sharedPreferences.edit().clear().apply()

        // Redirect to login activity
        val intent = Intent(activity, LogInActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}
