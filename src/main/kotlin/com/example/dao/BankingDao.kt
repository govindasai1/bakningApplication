package com.example.dao

import com.example.models.*

interface BankingDao {
    suspend fun accountCreation(user: User): Message
    suspend fun login(details:LoginDetails):Id?
    suspend fun retrieveAccBal(id: Int): Message
    suspend fun depositFunds(id: Int, amount: Float): Message
    suspend fun withdrawFunds(id: Int, amount: Float): Message
    suspend fun transferFund(sourceId: Int, destinationId: Int, amount: Float): Message
    suspend fun transactionHistory(id: Int): List<Transactions>
}