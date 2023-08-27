package com.example.drinkitnowpare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class mngprd : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    public override fun onStart() {
        super.onStart()
        auth = FirebaseAuth.getInstance()
        auth.addAuthStateListener(authStateListener);

        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser == null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mngprd)

        val categories = resources.getStringArray(R.array.category_options)
        val  categorySpinner = findViewById<Spinner>(R.id.categorySpinner)

        val cat_size = resources.getStringArray(R.array.category_size)
        val size_opt = findViewById<Spinner>(R.id.sizeoption)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = adapter

        val secondadapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cat_size)
        secondadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        size_opt.adapter = secondadapter






        val db = Firebase.firestore

        val redirectButton = findViewById<Button>(R.id.btn_view)
        redirectButton.setOnClickListener {
            val intent = Intent(this, viewprd::class.java)
            startActivity(intent)
            finish()

        }

        val logoutButton = findViewById<Button>(R.id.btn_logout);

        logoutButton.setOnClickListener {
            Firebase.auth.signOut();
        }

        val addButton = findViewById<Button>(R.id.btn_add);

        addButton.setOnClickListener {
            val prdname = findViewById<EditText>(R.id.prdname).text.toString();
            val category= findViewById<Spinner>(R.id.categorySpinner).selectedItem.toString();
            val supplier  = findViewById<EditText>(R.id.prd_sup).text.toString();
            val sku = findViewById<EditText>(R.id.prd_sku).text.toString();
            val quantity = findViewById<EditText>(R.id.prd_qty).text.toString();
            val size = findViewById<EditText>(R.id.prd_size).text.toString();
            val units = findViewById<Spinner>(R.id.sizeoption).selectedItem.toString()
            val price = findViewById<EditText>(R.id.prd_price).text.toString();

            // string to integer
            val intQty: Int = quantity.toIntOrNull() ?: 0
            val intSize: Int = size.toIntOrNull() ?: 0
            // string to float
            val floatPrice: Float = price.toFloatOrNull() ?: 0.0f
            if (prdname.isNotEmpty()  && supplier.isNotEmpty() && sku.isNotEmpty() && size.isNotEmpty()) {
            val data = hashMapOf(
                "prdnme" to prdname,
                "ctg" to category,
                "supp" to supplier,
                "sku" to sku,
                "qnty" to intQty,
                "units" to units,
                "size" to intSize,
                "price" to floatPrice,

            )

            db.collection("products")
                .add(data)
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(
                        baseContext,
                        "Added Product!",
                        Toast.LENGTH_SHORT
                    ).show()

                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        baseContext,
                        "Failed to add Product!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                }else{
                    Toast.makeText(this, "Please fill in all product information fields", Toast.LENGTH_SHORT).show()

                }

//            Toast.makeText(
//                baseContext,
//                prdname + category + supplier+ sku + quantity + size+price,
//                Toast.LENGTH_SHORT,
//            ).show()
        }

    }


    val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth: FirebaseAuth ->
        val user: FirebaseUser? = firebaseAuth.currentUser
        if (user == null) {
            // User is signed in
            // Perform actions when user is signed in
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            // User is signed out
            // Perform actions when user is signed out
        }
    }

}
