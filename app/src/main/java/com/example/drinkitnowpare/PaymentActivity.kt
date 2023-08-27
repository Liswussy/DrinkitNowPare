package com.example.drinkitnowpare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity

class PaymentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val productList = intent.getSerializableExtra("productList") as? ArrayList<Product>

        var total = 0.00
        fun calculateTotal(){

            if (productList != null) {
                for (product in productList) {
                    val price = product.price
                    val quantity = product.quantity
                    total +=  price!! * quantity!!
//                    println("Product ID: ${product.productID}")
//                    println("Product Name: ${product.productName}")
//                    println("Price: ${product.price}")
//                    println("------------------------")
                    val textViewSubTotal = findViewById<TextView>(R.id.textViewSubTotal)
                    textViewSubTotal.setText("P $total")
                    val textViewTotal = findViewById<TextView>(R.id.textViewTotal)
                    textViewTotal.setText("P $total")
                }

            } else {
                println("Product list is null.")
            }
        }
        calculateTotal()

        val buttonPayment = findViewById<Button>(R.id.button)
        buttonPayment.setOnClickListener {
            val intent = Intent(this, CheckoutActivity::class.java)
            intent.putExtra("productList", productList)
            intent.putExtra("total", total)
            startActivity(intent)
        }
    }
}