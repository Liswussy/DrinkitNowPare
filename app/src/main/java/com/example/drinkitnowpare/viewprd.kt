package com.example.drinkitnowpare

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class viewprd : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.viewprd)

        showAllProducts();

        val categories = resources.getStringArray(R.array.category_options)
        val dopdown_ctg = findViewById<Spinner>(R.id.drp_categ)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dopdown_ctg.adapter = adapter

        val searchButton = findViewById<Button>(R.id.search_btn)

        searchButton.setOnClickListener {

            val db = FirebaseFirestore.getInstance()
            val productsCollection = db.collection("products")

            val input = findViewById<EditText>(R.id.sb_prdname).text.toString()
            val searchCategory = findViewById<Spinner>(R.id.drp_categ).selectedItem.toString()

            val linearLayout = findViewById<LinearLayout>(R.id.view)
            linearLayout.removeAllViews()

            if (searchCategory == "Category") {
                productsCollection.whereEqualTo("ctg", input).get()
                    .addOnSuccessListener { querySnapshot ->
                        for (document in querySnapshot) {
                            val dpValue = 40 // Desired height in dp
                            val density =
                                resources.displayMetrics.density // Display density in dp per pixel
                            val heightInPixels = (dpValue * density).toInt() // Convert dp to pixels
                            val productName = document.getString("prdnme")
                            val category = document.getString("ctg")
                            val sku = document.getString("sku")

                            val textView = TextView(this)
                            textView.tag = document.id // Set unique ID for each TextView
                            textView.text =
                                "$productName | $category | $sku" // Set text for each TextView
                            textView.width = LinearLayout.LayoutParams.MATCH_PARENT // Set width
                            textView.height = heightInPixels // Set height
                            textView.setTextColor(resources.getColor(R.color.white)) // Set text color

                            // Create a shape drawable for the outline
                            val shapeDrawable = GradientDrawable()
                            shapeDrawable.shape = GradientDrawable.RECTANGLE
                            shapeDrawable.setColor(Color.TRANSPARENT) // Set background color
                            shapeDrawable.setStroke(2, Color.WHITE) // Set border width and color

                            // Set the shape drawable as the background of the TextView
                            textView.background = shapeDrawable

                            textView.setOnClickListener {

                                val dataToSend = it.tag.toString()

                                val intent = Intent(this, EditActivity::class.java)
                                intent.putExtra("prodID", dataToSend)
                                startActivity(intent)
                            }

                            linearLayout.addView(textView) // Add TextView to LinearLayout
                        }
                    }
            } else if (searchCategory == "SKU") {
                productsCollection.whereEqualTo("sku", input).get()
                    .addOnSuccessListener { querySnapshot ->
                        for (document in querySnapshot) {
                            val dpValue = 40 // Desired height in dp
                            val density =
                                resources.displayMetrics.density // Display density in dp per pixel
                            val heightInPixels = (dpValue * density).toInt() // Convert dp to pixels
                            val productName = document.getString("prdnme")
                            val category = document.getString("ctg")
                            val sku = document.getString("sku")

                            val textView = TextView(this)
                            textView.tag = document.id // Set unique ID for each TextView
                            textView.text =
                                "$productName | $category | $sku" // Set text for each TextView
                            textView.width = LinearLayout.LayoutParams.MATCH_PARENT // Set width
                            textView.height = heightInPixels // Set height
                            textView.setTextColor(resources.getColor(R.color.white)) // Set text color

                            // Create a shape drawable for the outline
                            val shapeDrawable = GradientDrawable()
                            shapeDrawable.shape = GradientDrawable.RECTANGLE
                            shapeDrawable.setColor(Color.TRANSPARENT) // Set background color
                            shapeDrawable.setStroke(2, Color.WHITE) // Set border width and color

                            // Set the shape drawable as the background of the TextView
                            textView.background = shapeDrawable

                            textView.setOnClickListener {

                                val dataToSend = it.tag.toString()

                                val intent = Intent(this, EditActivity::class.java)
                                intent.putExtra("prodID", dataToSend)
                                startActivity(intent)
                            }

                            linearLayout.addView(textView) // Add TextView to LinearLayout
                        }
                    }
            } else if (searchCategory == "Name") {
                productsCollection.whereEqualTo("prdnme", input).get()
                    .addOnSuccessListener { querySnapshot ->
                        for (document in querySnapshot) {
                            val dpValue = 40 // Desired height in dp
                            val density =
                                resources.displayMetrics.density // Display density in dp per pixel
                            val heightInPixels = (dpValue * density).toInt() // Convert dp to pixels
                            val productName = document.getString("prdnme")
                            val category = document.getString("ctg")
                            val sku = document.getString("sku")

                            val textView = TextView(this)
                            textView.tag = document.id // Set unique ID for each TextView
                            textView.text =
                                "$productName | $category | $sku" // Set text for each TextView
                            textView.width = LinearLayout.LayoutParams.MATCH_PARENT // Set width
                            textView.height = heightInPixels // Set height
                            textView.setTextColor(resources.getColor(R.color.white)) // Set text color

                            // Create a shape drawable for the outline
                            val shapeDrawable = GradientDrawable()
                            shapeDrawable.shape = GradientDrawable.RECTANGLE
                            shapeDrawable.setColor(Color.TRANSPARENT) // Set background color
                            shapeDrawable.setStroke(2, Color.WHITE) // Set border width and color

                            // Set the shape drawable as the background of the TextView
                            textView.background = shapeDrawable

                            textView.setOnClickListener {

                                val dataToSend = it.tag.toString()

                                val intent = Intent(this, EditActivity::class.java)
                                intent.putExtra("prodID", dataToSend)
                                startActivity(intent)
                            }

                            linearLayout.addView(textView) // Add TextView to LinearLayout
                        }
                    }
            } else if (searchCategory == "Supplier") {
                productsCollection.whereEqualTo("supp", input).get()
                    .addOnSuccessListener { querySnapshot ->
                        for (document in querySnapshot) {
                            val dpValue = 40 // Desired height in dp
                            val density =
                                resources.displayMetrics.density // Display density in dp per pixel
                            val heightInPixels = (dpValue * density).toInt() // Convert dp to pixels
                            val productName = document.getString("prdnme")
                            val category = document.getString("ctg")
                            val sku = document.getString("sku")

                            val textView = TextView(this)
                            textView.tag = document.id // Set unique ID for each TextView
                            textView.text =
                                "$productName | $category | $sku" // Set text for each TextView
                            textView.width = LinearLayout.LayoutParams.MATCH_PARENT // Set width
                            textView.height = heightInPixels // Set height
                            textView.setTextColor(resources.getColor(R.color.white)) // Set text color

                            // Create a shape drawable for the outline
                            val shapeDrawable = GradientDrawable()
                            shapeDrawable.shape = GradientDrawable.RECTANGLE
                            shapeDrawable.setColor(Color.TRANSPARENT) // Set background color
                            shapeDrawable.setStroke(2, Color.WHITE) // Set border width and color

                            // Set the shape drawable as the background of the TextView
                            textView.background = shapeDrawable

                            textView.setOnClickListener {

                                val dataToSend = it.tag.toString()

                                val intent = Intent(this, EditActivity::class.java)
                                intent.putExtra("prodID", dataToSend)
                                startActivity(intent)
                            }

                            linearLayout.addView(textView) // Add TextView to LinearLayout
                        }
                    }
            }

        }
    }

    fun showAllProducts() {
        val db = Firebase.firestore
        val linearLayout = findViewById<LinearLayout>(R.id.view)

        val productsCollection = db.collection("products")

        val dpValue = 40 // Desired height in dp
        val density = resources.displayMetrics.density // Display density in dp per pixel
        val heightInPixels = (dpValue * density).toInt() // Convert dp to pixels

        productsCollection.get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val productName = document.getString("prdnme")
                    val category = document.getString("ctg")
                    val sku = document.getString("sku")

                    val textView = TextView(this)
                    textView.tag = document.id // Set unique ID for each TextView
                    textView.text = "$productName | $category | $sku" // Set text for each TextView
                    textView.width = LinearLayout.LayoutParams.MATCH_PARENT // Set width
                    textView.height = heightInPixels // Set height
                    textView.setTextColor(resources.getColor(R.color.white)) // Set text color

                    // Create a shape drawable for the outline
                    val shapeDrawable = GradientDrawable()
                    shapeDrawable.shape = GradientDrawable.RECTANGLE
                    shapeDrawable.setColor(Color.TRANSPARENT) // Set background color
                    shapeDrawable.setStroke(2, Color.WHITE) // Set border width and color

                    // Set the shape drawable as the background of the TextView
                    textView.background = shapeDrawable

                    textView.setOnClickListener {

                        val dataToSend = it.tag.toString()

                        val intent = Intent(this, EditActivity::class.java)
                        intent.putExtra("prodID", dataToSend)
                        startActivity(intent)
                    }

                    linearLayout.addView(textView) // Add TextView to LinearLayout
                }
            }
            .addOnFailureListener { exception ->
                // Handle errors
            }
    }
}



//
////        db.collection("products")
////            .get()
////            .addOnSuccessListener { result ->
////                for (document in result) {
////                    //Log.d(TAG, "${document.id} => ${document.data}")
////                    val data = document.data
////                    val prdname = data["prdnme"] as? String
////                    val category= data["ctg"] as? String
////                    val supplier  = data["supp"] as? String
////                    val sku = data["sku"] as? String
////                    val quantity = (data["qnty"] ).toString()
////                    val size = (data["size"] ).toString()
////                    val units = data["units"] as? String
////                    val price = (data["price"] ).toString()
////                    val dataString: String = "Product Name: " + prdname + "\nCategory: "+ category +
////                            "\nSupplier: "+ supplier + "\nSKU: "+ sku + "\nQuantity: "+ quantity +
////                            "\nSize: "+ size+ units +"\nPrice: "+price +"\n\n";
////                    myString = myString + dataString;
////                }
////                val textView = findViewById<TextView>(R.id.textView)
////                textView.text = myString
////            }
////
////            .addOnFailureListener { exception ->
////                Toast.makeText(
////                    baseContext,
////                    "Failed to read Documents!",
////                    Toast.LENGTH_SHORT,
////                ).show()
////            }
//}

