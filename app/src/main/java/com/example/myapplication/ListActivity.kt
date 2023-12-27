package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.Product
import com.example.myapplication.data.ProductList
import com.example.myapplication.data.User
import com.example.myapplication.utils.Database
import com.example.myapplication.utils.productlist_adapter
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.lang.StringBuilder

class ListActivity : AppCompatActivity() {

    private val db = Database
    var productItems : ArrayList<ProductList> = ArrayList<ProductList>()
    var product_array : JSONArray = JSONArray()
    var products : RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        val username = findViewById<TextView>(R.id.usernname)
        lifecycleScope.launch {
            val user = db.getDatabase().postgrest["users"].select(){
                User::profile_id eq db.getUser()!!.id
            }.decodeSingle<User>()
            username.text = user.username
        }
        products = findViewById<RecyclerView>(R.id.product_recycler)
        products!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        lifecycleScope.launch {
            val parsing = db.getDatabase().postgrest["product"].select(Columns.ALL)
            val buf = StringBuilder()
            var line = parsing.body.toString()
            Log.e("111", line)
            buf.append(line).append("\n")
            product_array = JSONArray(buf.toString())
            addItems()
        }
    }

    fun addItems(){
        try {
            lifecycleScope.launch {
                for(i in 0..< product_array.length()){
                    val itemObj : JSONObject = product_array.getJSONObject(i)
                    val img = db.getDatabase().storage["product"].downloadPublic(itemObj.getString("image"))
                    var item : ProductList = ProductList(
                        itemObj.getString("id"),
                        itemObj.getString("name"),
                        itemObj.getString("description"),
                        itemObj.getString("price"),
                        img
                    )
                    Log.e("COFFEE", item.toString())
                    productItems += item
                }
                val adapter = productlist_adapter(productItems, productlist_adapter.OnClickListener{product -> goOrder(product.id.toInt())})
                products!!.adapter = adapter
                Log.e("2", adapter.itemCount.toString())
                adapter.notifyDataSetChanged()
            }
        }catch (ex : Exception){
            Log.e("ERROR", ex.message.toString())
        }
    }

    fun goOrder(id: Int){
        db.setSelectedProduct(id)
        startActivity(Intent(this, ProductPage::class.java))
    }

    fun back(view: View) {}
}