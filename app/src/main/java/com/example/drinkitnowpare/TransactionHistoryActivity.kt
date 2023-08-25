package com.example.drinkitnowpare

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Locale


class TransactionHistoryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_history)

        val myRoot = findViewById<View>(R.id.layoutOrders) as LinearLayout

        val db = FirebaseFirestore.getInstance()

        fun showOrders(){
            db.collection("orders")
                .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    for (document in querySnapshot.documents) {
                        val a = LinearLayout(this)
                        a.orientation = LinearLayout.HORIZONTAL
                        a.setLayoutParams(
                            LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT
                            )
                        )
                        val dateFormat = SimpleDateFormat("MM-dd-yyyy", Locale.US)

                        val view1 = TextView(this)
                        view1.setText("Consignor 1")
                        val view2 = TextView(this)
                        view2.setText(dateFormat.format(document.getTimestamp("timestamp")!!.toDate()))
                        val view3 = TextView(this)
                        view3.setText("Paid P"+document.getDouble("total").toString())

                        println("HELLO")

                        a.addView(view1)
                        a.addView(view2)
                        a.addView(view3)
                        myRoot.addView(a)
                    }
                }
                .addOnFailureListener { e ->
                    println("Error querying orders: $e")
                }
        }
        showOrders()

    }
}