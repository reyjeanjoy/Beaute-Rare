package com.beauterare.rare


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.beauterare.rare.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        binding.bottomNavView.setOnItemSelectedListener{

            when(it.itemId){
                R.id.message -> startActivity(Intent(this, MessageActivity::class.java))
                R.id.home -> replaceFragment(HomeFragment())
                R.id.profile -> replaceFragment(Profile())
                else ->{

                }

            }
            true
        }

    }

    private fun replaceFragment( fragment : Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }

}
