package com.example.features.authorisation.entity

import com.google.gson.annotations.SerializedName

data class StatusEntity(
    @SerializedName("status")
    val status: Boolean
)
