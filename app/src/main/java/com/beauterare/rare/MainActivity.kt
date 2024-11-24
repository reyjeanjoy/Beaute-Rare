package com.beauterare.rare

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.beauterare.rare.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initial fragment load
        replaceFragment(HomeFragment())

        // Fetch users from the API on activity load
        fetchUsers()

        // Bottom navigation setup
        binding.buttomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.message -> startActivity(Intent(this, MessageActivity::class.java))
                R.id.home -> replaceFragment(HomeFragment())
                R.id.profile -> replaceFragment(Profile())
                else -> { }
            }
            true
        }
    }

    // Method to fetch users from the API
    private fun fetchUsers() {
        RetrofitClient.instance.getUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful && response.body() != null) {
                    val users = response.body()
                    // Logging each user’s data for demonstration
                    users?.forEach { user ->
                        Log.d("UserData", "ID: ${user.id}, Name: ${user.first_name} ${user.last_name}, Email: ${user.email}")
                    }
                    Toast.makeText(this@MainActivity, "Users fetched successfully!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Failed to retrieve data: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Method to replace fragments
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}
