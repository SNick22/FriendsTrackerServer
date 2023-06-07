package com.example.features.authorisation.entity

import com.google.gson.annotations.SerializedName

data class AuthorisationEntity(
    @SerializedName("phone_number")
    val phoneNumber: String,
    @SerializedName("auth_token")
    val authToken: String
)
