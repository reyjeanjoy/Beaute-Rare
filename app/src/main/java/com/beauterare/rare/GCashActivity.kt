package com.beauterare.rare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class GCashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gcash)

        val proceedBtn = findViewById<Button>(R.id.proceedBtn2)

        proceedBtn.setOnClickListener {
            val merchantEditText = findViewById<EditText>(R.id.merchantEditText)
            val amountDueEditText = findViewById<EditText>(R.id.amountDueEditText)
            val payGcashEditText = findViewById<EditText>(R.id.payGcashEditText)

            val merchant = merchantEditText.text.toString().trim()
            val amountDue = amountDueEditText.text.toString().trim()
            val mobileNumber = payGcashEditText.text.toString().trim()

            // Validation
            if (merchant.isEmpty() || amountDue.isEmpty() || mobileNumber.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            } else if (!isNumeric(amountDue) || !isNumeric(mobileNumber)) {
                Toast.makeText(this, "Amount Due and Mobile Number must be numeric", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, GcashDetailsActivity::class.java).apply {
                    putExtra("merchant", merchant)
                    putExtra("amountDue", amountDue)
                    putExtra("mobileNumber", mobileNumber)
                }
                startActivity(intent)
            }
        }
    }

    private fun isNumeric(str: String): Boolean {
        return str.matches("-?\\d+(\\.\\d+)?".toRegex())
    }
}
