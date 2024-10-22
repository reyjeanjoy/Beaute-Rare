package com.beauterare.rare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class PayPalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_pal)

        val proceedBtn = findViewById<Button>(R.id.proceedBtn3)

        proceedBtn.setOnClickListener {
            val amountEditText: EditText = findViewById(R.id.pAmountEdiText)
            val recipientNameEditText: EditText = findViewById(R.id.recipientNameEditText)
            val accountNumberEditText: EditText = findViewById(R.id.pAccountNumberEditText)

            val amount = amountEditText.text.toString().trim()
            val recipientName = recipientNameEditText.text.toString().trim()
            val accountNumber = accountNumberEditText.text.toString().trim()

            // Validation
            if (amount.isEmpty() || recipientName.isEmpty() || accountNumber.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            } else if (!isNumeric(amount) || !isNumeric(accountNumber)) {
                Toast.makeText(this, "Amount and Account Number must be numeric", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, PaypalDetailsActivity::class.java).apply {
                    putExtra("amount", amount)
                    putExtra("recipientName", recipientName)
                    putExtra("accountNumber", accountNumber)
                }
                startActivity(intent)
            }
        }
    }

    private fun isNumeric(str: String): Boolean {
        return str.all { it.isDigit() || it == '.' }
    }
}
