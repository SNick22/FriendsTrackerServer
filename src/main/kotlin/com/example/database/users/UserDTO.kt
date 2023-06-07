package com.example.database.users

data class UserDTO(
    val phoneNumber: String,
    val authToken: String,
    val login: String,
    val name: String,
    val photoUrl: String?,
    val lastLat: Float?,
    val lastLon: Float?
)
