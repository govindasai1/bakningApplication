package com.example.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object AccountTable:IntIdTable("accountId") {
    val userId = reference("UserId",UsersTable.id)
    val accountNumber = varchar("AccountNumber",50)
    val balance = float("Balance")
}