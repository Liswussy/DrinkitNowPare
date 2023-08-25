package com.example.drinkitnowpare

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CashRegisterActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cash_register)

        val parentLinearLayout: LinearLayout = findViewById(R.id.linearLayout)

        val productManager = ProductManager(this)

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
                        val productID = document.id
                        val productName = document.getString("prdnme")
                        val price = document.getDouble("price")

                        val horizontalLayout = createHorizontalLinearLayout(this)
                        val verticalLayout = createVerticalLinearLayout(this)

                        val imageView = createImageView()
                        val textView = createTextView(productName.toString())
                        val textView2 = createTextView("Php " + price.toString())

                        // Add ImageView and TextView to the ConstraintLayout
                        horizontalLayout.addView(imageView)

                        verticalLayout.addView(textView)
                        verticalLayout.addView(textView2)
                        horizontalLayout.addView(verticalLayout)

                        val layoutParams = horizontalLayout.layoutParams as ViewGroup.MarginLayoutParams
                        layoutParams.setMargins(
                            layoutParams.leftMargin,
                            layoutParams.topMargin,
                            layoutParams.rightMargin,
                            resources.getDimensionPixelSize(R.dimen.bottom_margin) // Replace with your desired margin value
                        )

                        horizontalLayout.setOnClickListener {
                            // Handle the click event here
                            // For example, you can show a Toast message
                            val newProduct = Product(productID, productName, price, 1)
                            productManager.addProduct(newProduct)
                        }

                        // Add ConstraintLayout to the parent LinearLayout
                        parentLinearLayout.addView(horizontalLayout)
                    }
                }
                .addOnFailureListener { exception ->
                    // Handle errors
                }
        }

        showAllProducts()

        val buttonPayment = findViewById<Button>(R.id.buttonPayment)
        buttonPayment.setOnClickListener {
            val intent = Intent(this, PaymentActivity::class.java)
            intent.putExtra("productList", ArrayList(productManager.getProductList()))
            startActivity(intent)
        }
        // Create and add multiple ConstraintLayouts with ImageView and TextView
//        for (i in 1..5) {
//            val horizontalLayout = createHorizontalLinearLayout(this)
//            val verticalLayout = createVerticalLinearLayout(this)
//
//            val imageView = createImageView()
//            val textView = createTextView("Item $i")
//            val textView2 = createTextView("Price: $i")
//
//            // Add ImageView and TextView to the ConstraintLayout
//            horizontalLayout.addView(imageView)
//
//            verticalLayout.addView(textView)
//            verticalLayout.addView(textView2)
//            horizontalLayout.addView(verticalLayout)
//
//            val layoutParams = horizontalLayout.layoutParams as ViewGroup.MarginLayoutParams
//            layoutParams.setMargins(
//                layoutParams.leftMargin,
//                layoutParams.topMargin,
//                layoutParams.rightMargin,
//                resources.getDimensionPixelSize(R.dimen.bottom_margin) // Replace with your desired margin value
//            )
//
//            horizontalLayout.setOnClickListener {
//                // Handle the click event here
//                // For example, you can show a Toast message
//                val newProduct = Product(i, "Product $i", 10.99)
//                productManager.addProduct(newProduct)
//            }
//
//            // Add ConstraintLayout to the parent LinearLayout
//            parentLinearLayout.addView(horizontalLayout)
//        }
    }

    private fun createHorizontalLinearLayout(context: Context): LinearLayout {
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        val horizontalLayout = LinearLayout(context)
        horizontalLayout.layoutParams = layoutParams
        horizontalLayout.orientation = LinearLayout.HORIZONTAL
        horizontalLayout.setBackgroundColor(0xFFA6A6A6.toInt()) // Set background color

        return horizontalLayout
    }

    private fun createVerticalLinearLayout(context: Context): LinearLayout {
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        val verticalLayout = LinearLayout(context)
        verticalLayout.layoutParams = layoutParams
        verticalLayout.orientation = LinearLayout.VERTICAL

        return verticalLayout
    }

    private fun createImageView(): ImageView {
        val imageView = ImageView(this)
        imageView.setImageResource(R.drawable.ic_launcher_foreground) // Set your image resource here
        imageView.layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        return imageView
    }

    private fun createTextView(text: String): TextView {
        val textView = TextView(this)
        textView.text = text
        textView.layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        return textView
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}

data class Product(val productID: String?, val productName: String?, val price: Double?, val quantity: Int?) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(productID)
        parcel.writeString(productName)
        parcel.writeValue(price)
        parcel.writeValue(quantity)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }

}

class ProductManager(private val context: Context) {
    private val productList = mutableListOf<Product>()

    fun addProduct(product: Product) {
        // Check if the product entry already exists
        val existingProduct = productList.find { it.productID == product.productID }

        if (existingProduct == null) {
            productList.add(product)
            showToast("Product added: ${product.productName}", 500)
        } else {
            showToast("Product with ID ${product.productID} already exists.", 500)
        }
    }

    fun getProductList(): MutableList<Product> {
        return productList
    }

    private fun showToast(message: String, durationMillis: Int) {
        val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast.duration = durationMillis
        toast.show()
    }
}