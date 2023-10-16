package com.example.route

import com.example.models.*
import com.example.services.BankingLogin
import com.example.services.BankingRegistation
import com.example.services.BankingServices
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import org.koin.ktor.ext.inject


fun Route.bankingRoute(){
    val register by inject<BankingRegistation>()
    val login by inject<BankingLogin>()
    val service by inject<BankingServices>()
    route("/Register"){
        post {
            val user = call.receive<User>()
            call.respond(register.creatingAccount(user))
        }
    }
    route("/Login"){
        post {
            val userLogin = call.receive<LoginDetails>()
            val result = login.loggingIn(userLogin)
            if (result==null) call.respond("INCORRECT USER CREDENTIALS GIVEN ")
            else call.sessions.set(UserSession(result.id)); call.respond(Message("${userLogin.userName} LOGGED IN SUCCESSFULLY"))

        }
    }
    route("/Services"){
        get ("/balance"){
            val session = call.sessions.get<UserSession>()
            if (session==null) call.respond(Message("TO RETRIEVE BALANCE LOGIN FIRST"))
            else{
                val id = session.id
                call.respond(service.gettingBalance(id))
            }
        }
        post("/deposit") {
            val session = call.sessions.get<UserSession>()
            val amount = call.receive<Amount>()
            if (session==null) call.respond(Message("TO DEPOSIT FUNDS LOGIN FIRST"))
            else{
                val id = session.id
                call.respond(service.fundsDeposit(id,amount.amount))
            }
        }
        post("/withdraw") {
            val session = call.sessions.get<UserSession>()
            val amount = call.receive<Amount>()
            if (session==null) call.respond(Message("TO WITHDRAW FUNDS LOGIN FIRST"))
            else{
                val id = session.id
                call.respond(service.fundsWithdraw(id,amount.amount))
            }
        }
        post("/fundTransfer"){
            val session = call.sessions.get<UserSession>()
            val details = call.receive<Transfer>()
            if (session==null) call.respond(Message("TO WITHDRAW FUNDS LOGIN FIRST"))
            else{
                val id = session.id
                call.respond(service.fundTransfer(id,details.destinationId,details.amount))
            }
        }
        get ("transactionHistory"){
            val session = call.sessions.get<UserSession>()
            if (session==null) call.respond(Message("TO RETRIEVE BALANCE LOGIN FIRST"))
            else{
                val id = session.id
                call.respond(service.historyOfTransaction(id))
            }
        }
    }
    route("/Logout"){
        get {
            call.sessions.clear<UserSession>()
            call.respond(Message("LOGGED OUT SUCCESSFULLY "))
        }
    }
}