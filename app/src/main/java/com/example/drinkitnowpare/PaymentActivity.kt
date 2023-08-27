package com.example.drinkitnowpare

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity


class PaymentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val productList = intent.getSerializableExtra("productList") as? ArrayList<Product>

        var total = 0.00
        fun calculateTotal(){
            total = 0.00
            if (productList != null) {
                if(productList.isEmpty()){
                    val textViewSubTotal = findViewById<TextView>(R.id.textViewSubTotal)
                    textViewSubTotal.setText("P $total")
                    val textViewTotal = findViewById<TextView>(R.id.textViewTotal)
                    textViewTotal.setText("P $total")
                }

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

        val linearLayout = findViewById<LinearLayout>(R.id.cartList) // Your main vertical LinearLayout

        if (productList != null) {
            for(product in productList){
                val id = product.productID
                val name = product.productName
                val price = product.price
                val quantity = product.quantity
                val quantityText = "Quantity: "+quantity.toString()

                val entryView = layoutInflater.inflate(R.layout.entry_layout, null)

                val productNameTextView = entryView.findViewById<TextView>(R.id.productNameTextView)
                val productPriceTextView = entryView.findViewById<TextView>(R.id.productPriceTextView)
                val productQuantityTextView = entryView.findViewById<TextView>(R.id.productQuantityTextView)
                val subButton = entryView.findViewById<Button>(R.id.subButton)
                val addButton = entryView.findViewById<Button>(R.id.addButton)
                val modifyButton = entryView.findViewById<Button>(R.id.modifyButton)

                productNameTextView.text = name
                productPriceTextView.text = price.toString()
                productQuantityTextView.text = quantityText

                subButton.setOnClickListener(){
                    for (product in productList) {
                        if (product.productID == id) {
                            product.quantity = product.quantity?.plus(-1)
                            val quantityText = "Quantity: "+product.quantity.toString()
                            productQuantityTextView.text = quantityText

                            if(product.quantity!! <=0){
                                linearLayout.removeView(entryView)
                                productList.removeIf { product -> product.productID == id }
                            }

                            calculateTotal()
                            break // Exit the loop after finding and updating the product

                        }
                    }
                }

                addButton.setOnClickListener(){
                    for (product in productList) {
                        if (product.productID == id) {
                            product.quantity = product.quantity?.plus(1)
                            val quantityText = "Quantity: "+product.quantity.toString()
                            productQuantityTextView.text = quantityText

                            calculateTotal()
                            break // Exit the loop after finding and updating the product
                        }
                    }
                }

                modifyButton.setOnClickListener(){
                    val dialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog_layout, null)
                    val leftTextView: TextView = dialogView.findViewById(R.id.leftTextView)
                    val rightTextView: TextView = dialogView.findViewById(R.id.rightTextView)
                    val confirmButton: Button = dialogView.findViewById(R.id.confirmButton)
                    val editTextNumber: EditText = dialogView.findViewById(R.id.editTextNumber)

                    if (quantity != null) {
                        editTextNumber.setText(product.quantity.toString())
                    }

                    leftTextView.text = name

                    val dialog = AlertDialog.Builder(this)
                        .setView(dialogView)
                        .create()

                    confirmButton.setOnClickListener {
                        // Handle confirm button click here
                        for (product in productList) {
                            if (product.productID == id) {
                                val inputText = editTextNumber.text.toString()
                                if (inputText.isNotEmpty()) {
                                    val numericValue = inputText.toInt() // Convert the string to a double
                                    product.quantity = numericValue
                                    val quantityText = "Quantity: "+product.quantity.toString()
                                    productQuantityTextView.text = quantityText

                                    if(product.quantity!! <=0){
                                        linearLayout.removeView(entryView)
                                        productList.removeIf { product -> product.productID == id }
                                    }

                                    calculateTotal()
                                    break // Exit the loop after finding and updating the product
                                } else {
                                    println("EditText is empty")
                                }


                            }
                        }
                        dialog.dismiss()
                    }

                    dialog.show()
                }

                linearLayout.addView(entryView)
            }
        }
        val buttonPayment = findViewById<Button>(R.id.button)
        buttonPayment.setOnClickListener {
            val intent = Intent(this, CheckoutActivity::class.java)
            intent.putExtra("productList", productList)
            intent.putExtra("total", total)
            startActivity(intent)
        }

        val myTextView = findViewById<TextView>(R.id.textView6)
        val text = "Add more items"
        val content = SpannableString(text)
        content.setSpan(UnderlineSpan(), 0, text.length, 0)
        myTextView.text = content
        myTextView.setOnClickListener(){
            val intent = Intent(this, CashRegisterActivity::class.java)
            intent.putExtra("productList", productList)
            startActivity(intent)
        }
    }
}