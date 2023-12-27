package com.example.myapplication.data

import kotlinx.serialization.Serializable

@Serializable
data class User(val profile_id : String, val username : String) {
}