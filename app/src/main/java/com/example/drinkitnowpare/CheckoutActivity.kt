package com.example.drinkitnowpare

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CheckoutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val db = Firebase.firestore

        val productList = intent.getSerializableExtra("productList") as? ArrayList<Product>
        val total = intent.getDoubleExtra("total", 0.0)

        val buttonConfirm = findViewById<Button>(R.id.button)
        buttonConfirm.setOnClickListener {

            val data = hashMapOf(
                "products" to productList,
                "total" to total,
                "timestamp" to com.google.firebase.Timestamp.now()
            )

            db.collection("orders")
                .add(data)
                .addOnSuccessListener {
                    val intent = Intent(this, PaymentSuccessActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener { e ->
                    println("Error adding document: $e") }


        }
    }
}