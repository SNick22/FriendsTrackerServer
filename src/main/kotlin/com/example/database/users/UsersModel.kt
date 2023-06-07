package com.example.database.users

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.isNotNull
import org.jetbrains.exposed.sql.transactions.transaction


object Users: Table() {

    private val phoneNumber = Users.varchar("phone_number", 11)
    private val authToken = Users.varchar("auth_token", 36)
    private val login = Users.varchar("login", 30)
    private val name = Users.varchar("name", 30)
    private val photoUrl = Users.varchar("photo_url", 50)
    private val lastLat = Users.float("last_lat")
    private val lastLon = Users.float("last_lon")

    fun insert(userDTO: UserDTO) {
        transaction {
            Users.insertIgnore {
                it[phoneNumber] = userDTO.phoneNumber
                it[authToken] = userDTO.authToken
                it[login] = userDTO.login
                it[name] = userDTO.name
                it[photoUrl] = userDTO.photoUrl ?: ""
                it[lastLat] = userDTO.lastLat ?: 0f
                it[lastLon] = userDTO.lastLon ?: 0f
            }
        }
    }

    fun updateAuthToken(newToken: String, phoneNumber: String) {
        transaction {
            Users.update ({Users.phoneNumber.eq(phoneNumber)}) {
                it[authToken] = newToken
            }
        }
    }

    fun fetchUserByPhoneNumber(phoneNumber: String): UserDTO? {
        val userModel = transaction { Users.select { Users.phoneNumber.eq(phoneNumber) }.singleOrNull() }
        return createUserDTO(userModel)
    }

    fun fetchUserByLogin(login: String): UserDTO? {
        val userModel = transaction { Users.select { Users.login.eq(login) }.singleOrNull() }
        return createUserDTO(userModel)
    }

    private fun createUserDTO(resultRow: ResultRow?): UserDTO? = resultRow?.let {
        UserDTO(
            phoneNumber = it[phoneNumber],
            authToken = it[authToken],
            login = it[login],
            name = it[name],
            photoUrl = it[photoUrl],
            lastLat = it[lastLat],
            lastLon = it[lastLon]
        )
    }
}