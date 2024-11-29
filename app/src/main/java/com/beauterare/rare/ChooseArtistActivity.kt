package com.beauterare.rare

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class ChooseArtistActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choose_list_item)

        // List of buttons to view portfolios and their corresponding target activities
        val portfolioButtons = listOf(
            R.id.buttonViewPortfolio1 to BretProfActivity::class.java,
            R.id.buttonViewPortfolio2 to AngeProfActivity::class.java,
            R.id.buttonViewPortfolio3 to PaulProfActivity::class.java,
            R.id.buttonViewPortfolio4 to JaniProfActivity::class.java,
            R.id.buttonViewPortfolio5 to JelProfActivity::class.java,
            R.id.buttonViewPortfolio6 to AntProfActivity::class.java
        )

        // List of artist names
        val artistNames = listOf(
            "Bretman Rock",
            "Angeline Dela Cruz",
            "Paul Unating",
            "Janica Cleto",
            "Jelly Eugenio",
            "Anthea Bueno"
        )

        // Set up the click listener for each "View Portfolio" button
        portfolioButtons.forEach { (buttonId, targetActivity) ->
            findViewById<Button>(buttonId).setOnClickListener {
                val intent = Intent(this, targetActivity) // Navigate to the respective artist's portfolio
                startActivity(intent)
            }
        }

        // Set up the click listener for each "Book" button
        val bookingButtons = listOf(
            R.id.buttonBook1,
            R.id.buttonBook2,
            R.id.buttonBook3,
            R.id.buttonBook4,
            R.id.buttonBook5,
            R.id.buttonBook6
        )

        bookingButtons.forEachIndexed { index, buttonId ->
            findViewById<Button>(buttonId).setOnClickListener {
                val artistName = artistNames[index] // Get the full name of the artist
                val intent = Intent(this, FormActivity::class.java).apply {
                    putExtra("makeupArtist", artistName)
                }
                startActivity(intent)
            }
        }
    }
}