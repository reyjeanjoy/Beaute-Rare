package com.beauterare.rare


import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class HomeSerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_ser)

        val backButton = findViewById<ImageButton>(R.id.backBtn)
        backButton.setOnClickListener {
            val intent = Intent(this@HomeSerActivity, HomeFragment::class.java)
            startActivity(intent)
        }

        val imageViews = listOf<ImageView>(
            findViewById(R.id.picture1),
            findViewById(R.id.picture2),
            findViewById(R.id.picture3),
            findViewById(R.id.picture12),
            findViewById(R.id.picture22),
            findViewById(R.id.picture32),
            findViewById(R.id.picture13),
            findViewById(R.id.picture23),
            findViewById(R.id.picture33)
        )

        val mainScreenIntent = Intent(this@HomeSerActivity, HomeSerPrice::class.java)
        imageViews.forEach { imageView ->
            imageView.setOnClickListener {
                startActivity(mainScreenIntent)
            }
        }
    }
}
