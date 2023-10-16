package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Message(val message:String)
@Serializable
data class User(val userName:String,val password:String,val email:String,val phoneNumber:Long)
@Serializable
data class Id(val id:Int)
@Serializable
data class LoginDetails(val userName:String,val password: String)
@Serializable
data class Balance(val balance:Float)
@Serializable
data class Amount(val amount:Float)
@Serializable
data class UserSession(val id:Int)
@Serializable
data class Transactions(val accountId:Int,val transactionType:String,val amount:Float,val transactionDate:String)
@Serializable
data class Transfer(val destinationId:Int,val amount:Float)