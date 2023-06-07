package com.example.features.authorisation

import com.example.cache.AuthCodesCache
import com.example.database.users.UsersController
import com.example.features.authorisation.entity.*
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import java.util.UUID
import kotlin.random.Random

fun Application.configureLoginRouting() {

    routing {
        get("/check-auth") {
            val phoneNumber = call.request.queryParameters.getOrFail("phone_number")
            val authToken = call.request.queryParameters.getOrFail("auth_token")

            val authorisationEntity = AuthorisationEntity(phoneNumber, authToken)

            if (UsersController.isUserSignedIn(authorisationEntity)) {
                call.respond(StatusEntity(true))
            } else {
                call.respond(StatusEntity(false))
            }
        }

        post("/send-auth-code") {
            val data = call.receive(PhoneNumberEntity::class)
            val code = Random.nextInt(1000, 9999)

            HttpClient().get {
                url("https://smsc.ru/sys/send.php")
                parameter("login", "azat3310977")
                parameter("psw", "snick22API")
                parameter("phones", data.phoneNumber)
                parameter("mes", "Ваш код для авторизации в приложении Friends Tracker: $code")
            }

            AuthCodesCache.putCode(data.phoneNumber, code)
            call.respond(HttpStatusCode.OK)
        }

        get("/check-auth-code") {
            val phoneNumber = call.request.queryParameters.getOrFail("phone_number")
            val authCode = call.request.queryParameters.getOrFail("auth_code").toInt()
            AuthCodesCache.getCode(phoneNumber)?.let {
                if (it == authCode) {
                    AuthCodesCache.deleteCode(phoneNumber)

                    val newToken = UUID.randomUUID().toString()
                    UsersController.updateToken(newToken, phoneNumber)

                    call.respond(AuthTokenEntity(newToken))
                }
            }
            call.respond(AuthTokenEntity(null))
        }

        get("/check-registration") {
            val phoneNumber = call.request.queryParameters.getOrFail("phone_number")
            val result = UsersController.isUserSignedUp(phoneNumber)
            call.respond(StatusEntity(result))
        }

        get("/check-login") {
            val login = call.request.queryParameters.getOrFail("login")
            val result = UsersController.isLoginAvailable(login)
            call.respond(StatusEntity(result))
        }

        post("/register-user") {
            val data = call.receive(UserEntity::class)
            UsersController.registerUser(data)
            call.respond(HttpStatusCode.OK)
        }
    }
}