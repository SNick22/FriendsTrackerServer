package com.example.features.authorisation.entity

import com.google.gson.annotations.SerializedName

data class UserEntity(
    @SerializedName("phone_number")
    val phoneNumber: String,
    @SerializedName("auth_token")
    val authToken: String,
    @SerializedName("login")
    val login: String,
    @SerializedName("name")
    val name: String
)
