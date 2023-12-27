package com.example.myapplication.utils


import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.ProductList

class product_adapter(private var ProductList : List<ProductList>, private val onClickListener: OnClickListener) : RecyclerView.Adapter<product_adapter.MyViewHolder>(){

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val name = itemView.findViewById<TextView>(R.id.product_name)
        val desc = itemView.findViewById<TextView>(R.id.product_desc)
        val price = itemView.findViewById<TextView>(R.id.product_price)
        val image = itemView.findViewById<ImageView>(R.id.product_icon)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.activity_product_page, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.e("111", "bind")
        Log.e("2", holder.name.text.toString())
        val list = ProductList[position]
        holder.name.text = list.name.toString()
        holder.desc.text = list.description
        holder.price.text = "$"+list.price.toString()
        val image : Drawable = BitmapDrawable(BitmapFactory.decodeByteArray(list.image, 0, list.image.size))
        holder.image.setImageDrawable(image)
        holder.itemView.setOnClickListener(View.OnClickListener {
            onClickListener.onClick(list)
        })
    }

    class OnClickListener(val clickListener: (listener: ProductList) -> Unit){
        fun onClick(listener: ProductList) = clickListener(listener)
    }

    override fun getItemCount(): Int {
        return ProductList.size
    }

}