package com.example.plugins

import com.example.models.Amount
import com.example.models.User
import com.example.models.UserSession
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

fun Application.configureSerialization() {
    install(RequestValidation){
        validate<Amount> {
            if (it.amount>=0) ValidationResult.Valid
            else ValidationResult.Invalid("AMOUNT CANT BE NEGATIVE")
        }
        validate<User> {
            if (it.email.contains("@gmail.com") && (it.phoneNumber>600000000) && (it.phoneNumber<9999999999)) ValidationResult.Valid
            else ValidationResult.Invalid("INVALID EMAIL OR PHONE NUMBER ")
        }


    }
    install(Sessions){
        cookie<UserSession>("userSession")
    }
    install(ContentNegotiation) {
        json()
    }
    routing {
        get("/json/kotlinx-serialization") {
            call.respond(mapOf("hello" to "world"))
        }
    }
}
