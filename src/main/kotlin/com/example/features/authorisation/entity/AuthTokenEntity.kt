package com.example.features.authorisation.entity

import com.google.gson.annotations.SerializedName

data class AuthTokenEntity(
    @SerializedName("auth_token")
    val authToken: String?
)
