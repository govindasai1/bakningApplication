package com.example.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object TransactionTable : IntIdTable("TransactionId") {
    val accountId = reference("AccountId", AccountTable.id)
    val transactionType = varchar("TransactionType", 50)
    val amount = float("Amount")
    val transactionDate = varchar("TransactionDate", 50)
}