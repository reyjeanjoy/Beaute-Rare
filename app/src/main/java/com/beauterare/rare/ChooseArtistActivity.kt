package com.beauterare.rare

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class ChooseArtistActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choose_list_item)

        val portfolioButtons = listOf(
            R.id.buttonViewPortfolio1 to BretProfActivity::class.java,
            R.id.buttonViewPortfolio2 to AngeProfActivity::class.java,
            R.id.buttonViewPortfolio3 to PaulProfActivity::class.java,
            R.id.buttonViewPortfolio4 to JaniProfActivity::class.java,
            R.id.buttonViewPortfolio5 to JelProfActivity::class.java,
            R.id.buttonViewPortfolio6 to AntProfActivity::class.java
        )

        val bookingButtons = listOf(
            R.id.buttonBook1,
            R.id.buttonBook2,
            R.id.buttonBook3,
            R.id.buttonBook4,
            R.id.buttonBook5,
            R.id.buttonBook6
        )

        portfolioButtons.forEach { (buttonId, activityClass) ->
            findViewById<Button>(buttonId).setOnClickListener {
                startActivity(Intent(this, activityClass))
            }
        }

        bookingButtons.forEach { buttonId ->
            findViewById<Button>(buttonId).setOnClickListener {
                startActivity(Intent(this, PaymentActivity::class.java))
            }
        }
    }
}
