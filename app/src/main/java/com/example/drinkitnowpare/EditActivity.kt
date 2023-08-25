package com.example.drinkitnowpare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EditActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val db = Firebase.firestore

        val receivedData = intent.getStringExtra("prodID")

        //set product values
        val docRef = db.collection("products").document(receivedData.toString())
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {

                    val prdname = findViewById<EditText>(R.id.prdname)
                    prdname.setText(document.getString("prdnme"))

                    val category = findViewById<Spinner>(R.id.categorySpinner)
                    val spinnerItems = listOf("Imported Beer", "Local Beer", "Gin", "Spirits")
                    val valueToSelect = document.getString("ctg")

                    val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
                    category.adapter = adapter

                    // Get the index of the value to select
                    val selectedIndex = spinnerItems.indexOf(valueToSelect)

                    // Set the selected item by index
                    if (selectedIndex != -1) {
                        category.setSelection(selectedIndex)
                    }

                    val supplier  = findViewById<EditText>(R.id.prd_sup)
                    supplier.setText(document.getString("supp"))
                    val sku = findViewById<EditText>(R.id.prd_sku)
                    sku.setText(document.getString("sku"))
                    val quantity = findViewById<EditText>(R.id.prd_qty)
                    quantity.setText(document.getDouble("qnty")?.toInt().toString())
                    val size = findViewById<EditText>(R.id.prd_size)
                    size.setText(document.getDouble("size").toString())

                    val units = findViewById<Spinner>(R.id.sizeoption)
                    val spinnerItems2 = listOf("Volume", "ml", "Lt")
                    val valueToSelect2 = document.getString("units")

                    val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinnerItems2)
                    units.adapter = adapter2

                    // Get the index of the value to select
                    val selectedIndex2 = spinnerItems.indexOf(valueToSelect2)

                    // Set the selected item by index
                    if (selectedIndex2 != -1) {
                        category.setSelection(selectedIndex2)
                    }

                    val price = findViewById<EditText>(R.id.prd_price)
                    price.setText(document.getDouble("price").toString())

                } else {
                    Toast.makeText(this, "Error retrieving data", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error retrieving data", Toast.LENGTH_SHORT).show()
            }

        //update product
        val btn_update = findViewById<Button>(R.id.btn_update)
        val prodRef = db.collection("products").document(receivedData.toString())
        btn_update.setOnClickListener {
            //get the data
            val prdname = findViewById<EditText>(R.id.prdname).text.toString();
            val category= findViewById<Spinner>(R.id.categorySpinner).selectedItem.toString();
            val supplier  = findViewById<EditText>(R.id.prd_sup).text.toString();
            val sku = findViewById<EditText>(R.id.prd_sku).text.toString();
            val quantity = findViewById<EditText>(R.id.prd_qty).text.toString();
            val size = findViewById<EditText>(R.id.prd_size).text.toString();
            val units = findViewById<Spinner>(R.id.sizeoption).selectedItem.toString()
            val price = findViewById<EditText>(R.id.prd_price).text.toString();

            prodRef
                .update("prdnme", prdname,
                    "ctg", category,
                    "supp", supplier,
                    "sku", sku,
                    "qnty", quantity.toInt(),
                    "size", size.toDouble(),
                    "units", units,
                    "price", price.toDouble())
                .addOnSuccessListener { Toast.makeText(this, "Successfully updated", Toast.LENGTH_SHORT).show() }
                .addOnFailureListener { e -> Toast.makeText(this, "Error updating", Toast.LENGTH_SHORT).show() }
        }



        //delete product
        val btn_delete = findViewById<Button>(R.id.btn_delete)
        btn_delete.setOnClickListener {
            db.collection("products").document(receivedData.toString())
                .delete()
                .addOnSuccessListener { Toast.makeText(this, "Successfully deleted", Toast.LENGTH_SHORT).show() }
                .addOnFailureListener { e -> Toast.makeText(this, "Error deleting", Toast.LENGTH_SHORT).show() }
        }

        }
    }
