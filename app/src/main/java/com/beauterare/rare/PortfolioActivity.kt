package com.beauterare.rare

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class PortfolioActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choose_list_item)

        // Array of portfolios and activities
        val portfolioButtons = listOf(
            R.id.buttonViewPortfolio1 to BretProfActivity::class.java,
            R.id.buttonViewPortfolio2 to AngeProfActivity::class.java,
            R.id.buttonViewPortfolio3 to PaulProfActivity::class.java,
            R.id.buttonViewPortfolio4 to JaniProfActivity::class.java,
            R.id.buttonViewPortfolio5 to JelProfActivity::class.java,
            R.id.buttonViewPortfolio6 to AntProfActivity::class.java
        )

        // Array of booking buttons
        val bookButtons = listOf(
            R.id.buttonBook1,
            R.id.buttonBook2,
            R.id.buttonBook3,
            R.id.buttonBook4,
            R.id.buttonBook5,
            R.id.buttonBook6
        )

        // Set onClickListeners for portfolio buttons
        portfolioButtons.forEach { (buttonId, activityClass) ->
            findViewById<Button>(buttonId).setOnClickListener {
                startActivity(Intent(this, activityClass))
            }
        }

        // Set onClickListeners for booking buttons
        bookButtons.forEach { buttonId ->
            findViewById<Button>(buttonId).setOnClickListener {
                startActivity(Intent(this, AppointmentActivity::class.java))
            }
        }
    }
}
