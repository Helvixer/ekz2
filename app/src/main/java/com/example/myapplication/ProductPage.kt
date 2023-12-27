package com.example.myapplication

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.data.Product
import com.example.myapplication.utils.Database
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.launch

class ProductPage : AppCompatActivity() {

    private val db = Database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_page)
        val name = findViewById<TextView>(R.id.product_name)
        val desc = findViewById<TextView>(R.id.product_desc)
        val price = findViewById<TextView>(R.id.product_price)
        val imageV = findViewById<ImageView>(R.id.product_icon)
        lifecycleScope.launch {
            val item = db.getSelectedProduct()
            name.text = item.name
            desc.text = item.description
            price.text = "$"+item.price.toString()
            val image = db.getDatabase().storage["product"].downloadPublic(item.image)
            imageV.setImageDrawable(BitmapDrawable(BitmapFactory.decodeByteArray(image, 0, image.size)))
        }
    }

    fun back(view: View) {
        finish()
    }
}