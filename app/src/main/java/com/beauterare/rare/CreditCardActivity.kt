package com.beauterare.rare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class CreditCardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credit_card)

        val proceedButton = findViewById<Button>(R.id.proceedBtn1)
        proceedButton.setOnClickListener {
            val cNumber = findViewById<EditText>(R.id.cNumberEditText).text.toString().trim()
            val cName = findViewById<EditText>(R.id.cNameEditText).text.toString().trim()
            val expiryDate = findViewById<EditText>(R.id.expiryDateEditText).text.toString().trim()
            val cvv = findViewById<EditText>(R.id.cvvEditText).text.toString().trim()
            val cTotalAmount = findViewById<EditText>(R.id.cTotalAmountEditText).text.toString().trim()

            // Validation
            if (cNumber.isEmpty() || cName.isEmpty() || expiryDate.isEmpty() || cvv.isEmpty() || cTotalAmount.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            } else if (!isNumeric(cNumber) || !isNumeric(expiryDate) || !isNumeric(cvv) || !isNumeric(cTotalAmount)) {
                Toast.makeText(this, "Credit Card Number, Expiry Date, CVV, and Total Amount must be numeric", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this@CreditCardActivity, CreditCardDetailsActivity::class.java)
                intent.putExtra("creditCardNumber", cNumber)
                intent.putExtra("creditCardName", cName)
                intent.putExtra("expiryDate", expiryDate)
                intent.putExtra("cvv", cvv)
                intent.putExtra("totalAmount", cTotalAmount)
                startActivity(intent)
            }
        }
    }

    private fun isNumeric(str: String): Boolean {
        return str.matches("-?\\d+(\\.\\d+)?".toRegex())
    }
}
