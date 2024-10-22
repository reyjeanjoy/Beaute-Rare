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

        val ViewPortfolio1 = findViewById<Button>(R.id.buttonViewPortfolio1)
        val ViewPortfolio2 = findViewById<Button>(R.id.buttonViewPortfolio2)
        val ViewPortfolio3 = findViewById<Button>(R.id.buttonViewPortfolio3)
        val ViewPortfolio4 = findViewById<Button>(R.id.buttonViewPortfolio4)
        val ViewPortfolio5 = findViewById<Button>(R.id.buttonViewPortfolio5)
        val ViewPortfolio6 = findViewById<Button>(R.id.buttonViewPortfolio6)
        val bookButton1 = findViewById<Button>(R.id.buttonBook1)
        val bookButton2 = findViewById<Button>(R.id.buttonBook2)
        val bookButton3 = findViewById<Button>(R.id.buttonBook3)
        val bookButton4 = findViewById<Button>(R.id.buttonBook4)
        val bookButton5 = findViewById<Button>(R.id.buttonBook5)
        val bookButton6 = findViewById<Button>(R.id.buttonBook6)


        ViewPortfolio1.setOnClickListener{
            val intent = Intent(this,BretProfActivity::class.java)
            startActivity(intent)
        }
        ViewPortfolio2.setOnClickListener{
            val intent = Intent(this,AngeProfActivity::class.java)
            startActivity(intent)
        }
        ViewPortfolio3.setOnClickListener{
            val intent = Intent(this,PaulProfActivity::class.java)
            startActivity(intent)
        }
        ViewPortfolio4.setOnClickListener{
            val intent = Intent(this,JaniProfActivity::class.java)
            startActivity(intent)
        }
        ViewPortfolio5.setOnClickListener{
            val intent = Intent(this,JelProfActivity::class.java)
            startActivity(intent)
        }
        ViewPortfolio6.setOnClickListener{
            val intent = Intent(this,AntProfActivity::class.java)
            startActivity(intent)
        }
        bookButton1.setOnClickListener{
            val intent = Intent(this,AppointmentActivity::class.java)
            startActivity(intent)
        }
        bookButton2.setOnClickListener{
            val intent = Intent(this,AppointmentActivity::class.java)
            startActivity(intent)
        }
        bookButton3.setOnClickListener{
            val intent = Intent(this,AppointmentActivity::class.java)
            startActivity(intent)
        }
        bookButton4.setOnClickListener{
            val intent = Intent(this,AppointmentActivity::class.java)
            startActivity(intent)
        }
        bookButton5.setOnClickListener{
            val intent = Intent(this,AppointmentActivity::class.java)
            startActivity(intent)
        }
        bookButton6.setOnClickListener{
            val intent = Intent(this,AppointmentActivity::class.java)
            startActivity(intent)
        }
    }
    }