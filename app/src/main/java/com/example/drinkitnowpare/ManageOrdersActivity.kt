package com.example.drinkitnowpare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class ManageOrdersActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_orders)

        val buttonCashRegister = findViewById<Button>(R.id.button_cashRegister)
        buttonCashRegister.setOnClickListener {
            val intent = Intent(this, CashRegisterActivity::class.java)
            startActivity(intent)
        }

        val buttonTrackOrders = findViewById<Button>(R.id.button_trackOrders)
        buttonTrackOrders.setOnClickListener {
            val intent = Intent(this, TransactionHistoryActivity::class.java)
            startActivity(intent)
        }
    }
}