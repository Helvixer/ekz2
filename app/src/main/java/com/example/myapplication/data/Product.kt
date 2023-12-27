package com.example.myapplication.data

import kotlinx.serialization.Serializable

@Serializable
data class Product(val id : Int, val name : String, val description : String, val price : Float, val image : String) {
}