package com.example.myapplication.data

import kotlinx.serialization.Serializable

@Serializable
data class ProductList(val id : String, val name : String, val description : String, val price : String, val image : ByteArray)