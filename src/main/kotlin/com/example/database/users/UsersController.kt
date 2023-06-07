package com.example.database.users

import com.example.features.authorisation.entity.AuthorisationEntity
import com.example.features.authorisation.entity.UserEntity
import com.example.utils.toUserDTO

object UsersController {

    fun getUser(phoneNumber: String): UserDTO? {
        return Users.fetchUserByPhoneNumber(phoneNumber)
    }

    fun isUserSignedIn(authorisationEntity: AuthorisationEntity): Boolean {
        val user = Users.fetchUserByPhoneNumber(authorisationEntity.phoneNumber)

        user?.let {
            return it.authToken == authorisationEntity.authToken
        }

        return false
    }

    fun isUserSignedUp(phoneNumber: String): Boolean {
        val user = Users.fetchUserByPhoneNumber(phoneNumber)
        return user != null
    }

    fun registerUser(user: UserEntity) {
        val userDTO = user.toUserDTO()
        Users.insert(userDTO)
    }

    fun isLoginAvailable(login: String): Boolean {
        val user = Users.fetchUserByLogin(login)
        return user == null
    }

    fun updateToken(newToken: String, phoneNumber: String) {
        Users.updateAuthToken(newToken, phoneNumber)
    }
}