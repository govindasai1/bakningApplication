package com.example.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object UsersTable:IntIdTable("UserId") {
    val userName = varchar("UserName",50)
    val password = varchar("Password",50)
    val email = varchar("Email",50)
    val phoneNumber = long("PhoneNumber")
}