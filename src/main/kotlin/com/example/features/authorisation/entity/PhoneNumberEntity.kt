package com.example.features.authorisation.entity

import com.google.gson.annotations.SerializedName

data class PhoneNumberEntity(
    @SerializedName("phone_number")
    val phoneNumber: String
)
