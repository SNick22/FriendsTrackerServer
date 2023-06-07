package com.example.utils

import com.example.database.users.UserDTO
import com.example.features.authorisation.entity.UserEntity

fun UserEntity.toUserDTO(): UserDTO = UserDTO(
    phoneNumber = phoneNumber,
    authToken = authToken,
    login = login,
    name = name,
    photoUrl = null,
    lastLat = null,
    lastLon = null
)