package com.beauterare.rare

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.beauterare.rare.api.ApiService
import com.beauterare.rare.models.UserProfileResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Profile : Fragment() {

    private lateinit var firstNameText: TextView
    private lateinit var lastNameText: TextView
    private lateinit var emailText: TextView
    private lateinit var addressText: TextView
    private lateinit var logoutButton: Button
    private lateinit var editButton: ImageButton
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Initialize the views
        firstNameText = view.findViewById(R.id.Firstname)
        lastNameText = view.findViewById(R.id.Lastname)
        emailText = view.findViewById(R.id.Email)
        addressText = view.findViewById(R.id.Address)
        logoutButton = view.findViewById(R.id.logoutButton)
        editButton = view.findViewById(R.id.editbuttonn)
        saveButton = view.findViewById(R.id.buttonsave)
        cancelButton = view.findViewById(R.id.buttoncancel)

        // Fetch user profile
        fetchUserProfile()

        editButton.setOnClickListener {
            // You could add functionality to allow the user to edit the profile.
        }

        saveButton.setOnClickListener {
            // Save changes (implement saving logic here)
        }

        cancelButton.setOnClickListener {
            // Cancel changes and revert to original data
        }

        logoutButton.setOnClickListener {
            logoutUser()
        }

        return view
    }

    private fun fetchUserProfile() {
        val apiService = RetrofitClient.getClient("https://http://192.168.1.9/").create(ApiService::class.java)

        val call = apiService.getCurrentUserEmail()
        call.enqueue(object : Callback<UserProfileResponse> {
            override fun onResponse(
                call: Call<UserProfileResponse>,
                response: Response<UserProfileResponse>
            ) {
                if (response.isSuccessful) {
                    val user = response.body()?.user
                    if (user != null) {
                        // Set the user data in TextViews
                        firstNameText.text = user.fname
                        lastNameText.text = user.lname
                        emailText.text = user.email
                        addressText.text = user.address
                    }
                } else {
                    // Handle API response error
                    // For example, show a message
                }
            }

            override fun onFailure(call: Call<UserProfileResponse>, t: Throwable) {
                t.printStackTrace() // Log error for debugging
                // Handle network failure
            }
        })
    }

    private fun logoutUser() {
        // Clear user session or token (if stored in SharedPreferences)
        val sharedPreferences = requireActivity().getSharedPreferences("UserSession", 0)
        sharedPreferences.edit().clear().apply()

        // Redirect to login activity
        val intent = Intent(activity, LogInActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}