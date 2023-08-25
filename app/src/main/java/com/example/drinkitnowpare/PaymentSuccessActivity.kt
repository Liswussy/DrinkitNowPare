package com.example.drinkitnowpare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class PaymentSuccessActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_success)

        val buttonHome = findViewById<Button>(R.id.buttonHome)
        buttonHome.setOnClickListener {
            val intent = Intent(this, dashbord::class.java)
            startActivity(intent)
            finish()

        }

        val trackOrder = findViewById<Button>(R.id.trackOrder)
        trackOrder.setOnClickListener {
            val intent = Intent(this, TransactionHistoryActivity::class.java)
            startActivity(intent)
            finish()

        }
    }
}