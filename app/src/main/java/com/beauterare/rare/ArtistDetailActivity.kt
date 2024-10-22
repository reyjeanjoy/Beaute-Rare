package com.beauterare.rare

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.beauterare.rare.databinding.ActivityArtistDetailBinding
import com.bumptech.glide.Glide

class ArtistDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArtistDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtistDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get data passed from previous activity
        val artistName = intent.getStringExtra("ARTIST_NAME")
        val location = intent.getStringExtra("ARTIST_LOCATION")
        val workExperience = intent.getStringExtra("ARTIST_WORK_EXPERIENCE")
        val profileImageUrl = intent.getStringExtra("ARTIST_PROFILE_IMAGE")
        val artistO = intent.getStringExtra("ARTIST_O")
        val artistT = intent.getStringExtra("ARTIST_T")
        val artistTh = intent.getStringExtra("ARTIST_TH")
        val artistFo = intent.getStringExtra("ARTIST_FO")
        val artistFi = intent.getStringExtra("ARTIST_FI")
        val artistS = intent.getStringExtra("ARTIST_S")
        val artistSe = intent.getStringExtra("ARTIST_SE")
        val artistE = intent.getStringExtra("ARTIST_E")
        val artistN = intent.getStringExtra("ARTIST_N")

        // Set artist details
        binding.textViewName.text = artistName
        binding.textViewLocation.text = location
        binding.textViewWorkExperience.text = workExperience

        // Load artist profile image using Glide
        Glide.with(this)
            .load(profileImageUrl)
            .into(binding.imageViewProfile)

        // Load other images
        loadOtherImages(artistO, binding.imageViewO)
        loadOtherImages(artistT, binding.imageViewT)
        loadOtherImages(artistTh, binding.imageViewTh)
        loadOtherImages(artistFo, binding.imageViewFo)
        loadOtherImages(artistFi, binding.imageViewFi)
        loadOtherImages(artistS, binding.imageViewS)
        loadOtherImages(artistSe, binding.imageViewSe)
        loadOtherImages(artistE, binding.imageViewE)
        loadOtherImages(artistN, binding.imageViewN)

        // Handle back button click
        val back = findViewById<ImageButton>(R.id.backButton)
        back.setOnClickListener {
            finish()
        }
    }

    private fun loadOtherImages(imageUrl: String?, imageView: ImageView) {
        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(this)
                .load(imageUrl)
                .into(imageView)
        }
    }
}
